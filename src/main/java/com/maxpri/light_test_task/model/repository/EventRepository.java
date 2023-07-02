package com.maxpri.light_test_task.model.repository;

import com.maxpri.light_test_task.model.dto.EventDto;
import com.maxpri.light_test_task.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author max_pri
 */
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findEventById(Long id);

    @Query("SELECT new com.maxpri.light_test_task.model.dto.EventDto(e.id, e.name, e.cost, e.admin.id) FROM Event e")
    List<EventDto> findAllEvents();
}
