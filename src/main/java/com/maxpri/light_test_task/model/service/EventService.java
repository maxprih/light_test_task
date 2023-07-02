package com.maxpri.light_test_task.model.service;

import com.maxpri.light_test_task.model.dto.CreateEventRequest;
import com.maxpri.light_test_task.model.dto.GetEventsDto;
import com.maxpri.light_test_task.model.dto.MessageResponse;
import com.maxpri.light_test_task.model.entity.*;
import com.maxpri.light_test_task.model.exceptions.AdminNotFoundException;
import com.maxpri.light_test_task.model.exceptions.ContractNotSignedException;
import com.maxpri.light_test_task.model.exceptions.NoSuchEventException;
import com.maxpri.light_test_task.model.exceptions.ParticipantNotFoundException;
import com.maxpri.light_test_task.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author max_pri
 */
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ContractRepository contractRepository;
    private final ParticipantRepository participantRepository;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository, AdminRepository adminRepository, ContractRepository contractRepository, ParticipantRepository participantRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.contractRepository = contractRepository;
        this.participantRepository = participantRepository;
    }

    @Transactional
    public MessageResponse createEvent(CreateEventRequest createEventRequest, UserDetails userDetails) {
        User user = userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetails.getUsername()));
        Admin admin = adminRepository.findAdminById(user.getId())
                .orElseThrow(() -> new AdminNotFoundException());
        if (!contractRepository.existsByAdmin_IdAndStatus(user.getId(), ContractStatus.OK)) {
            throw new ContractNotSignedException();
        }
        Event event = Event
                .builder()
                .name(createEventRequest.getName())
                .cost(createEventRequest.getCost())
                .admin(admin)
                .participants(Collections.emptyList())
                .build();
        eventRepository.save(event);

        return new MessageResponse("Event successfully created");
    }

    @Transactional
    public MessageResponse applyForEvent(Long eventId, UserDetails userDetails) {
        User user = userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetails.getUsername()));
        Participant participant = participantRepository.findParticipantById(user.getId())
                .orElseThrow(() -> new ParticipantNotFoundException());

        if (!eventRepository.existsById(eventId)) {
            throw new NoSuchEventException();
        }

        Event event = eventRepository.findEventById(eventId).get(); // always not-null after if

        List<Participant> currentParticipants = event.getParticipants();
        currentParticipants.add(participant);
        event.setParticipants(currentParticipants);

        List<Event> currentEvents = participant.getEvents();
        currentEvents.add(event);
        participant.setEvents(currentEvents);

        eventRepository.save(event);
        participantRepository.save(participant);

        return new MessageResponse("Successfully applied for event");
    }

    public GetEventsDto getEvents() {
        return new GetEventsDto(
                eventRepository.findAllEvents()
        );
    }
}
