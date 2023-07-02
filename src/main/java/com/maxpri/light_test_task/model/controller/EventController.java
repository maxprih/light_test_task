package com.maxpri.light_test_task.model.controller;

import com.maxpri.light_test_task.model.dto.CreateEventRequest;
import com.maxpri.light_test_task.model.dto.GetEventsDto;
import com.maxpri.light_test_task.model.dto.MessageResponse;
import com.maxpri.light_test_task.model.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author max_pri
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/event")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create-event")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createEvent(@RequestBody CreateEventRequest createEventRequest,
                                         Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        MessageResponse result = eventService.createEvent(createEventRequest, userDetails);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/apply-for-event/{eventId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('PARTICIPANT')")
    public ResponseEntity<MessageResponse> applyForEvent(@PathVariable(name = "eventId") Long eventId,
                                                         Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        MessageResponse result = eventService.applyForEvent(eventId, userDetails);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-events")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('PARTICIPANT')")
    public ResponseEntity<GetEventsDto> getAllEvents() {
        GetEventsDto result = eventService.getEvents();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
