package com.maxpri.light_test_task.model.repository;

import com.maxpri.light_test_task.model.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author max_pri
 */
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findParticipantById(Long id);
}
