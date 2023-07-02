package com.maxpri.light_test_task.model.repository;

import com.maxpri.light_test_task.model.entity.ERoles;
import com.maxpri.light_test_task.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author max_pri
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERoles name);
}
