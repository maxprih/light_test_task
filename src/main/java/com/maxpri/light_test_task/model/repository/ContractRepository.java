package com.maxpri.light_test_task.model.repository;

import com.maxpri.light_test_task.model.dto.ContractDto;
import com.maxpri.light_test_task.model.entity.Contract;
import com.maxpri.light_test_task.model.entity.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author max_pri
 */
@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Boolean existsByAdmin_Id(Long id);
    Boolean existsByAdmin_IdAndStatus(Long id, ContractStatus status);

    @Query("SELECT new com.maxpri.light_test_task.model.dto.ContractDto(c.id, c.number, c.status, c.admin.id) FROM Contract c")
    List<ContractDto> findAllContracts();

    @Modifying
    @Query("update Contract contract set contract.status = ?1 where contract.id = ?2")
    void setContractStatus(ContractStatus status, Long id);
}
