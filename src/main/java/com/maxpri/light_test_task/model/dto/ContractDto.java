package com.maxpri.light_test_task.model.dto;

import com.maxpri.light_test_task.model.entity.ContractStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author max_pri
 */
@Data
@AllArgsConstructor
public class ContractDto {
    private Long id;
    private Integer number;
    private ContractStatus status;
    private Long adminId;
}
