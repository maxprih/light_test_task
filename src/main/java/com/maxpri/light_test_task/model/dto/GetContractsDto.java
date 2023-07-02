package com.maxpri.light_test_task.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author max_pri
 */
@Data
@AllArgsConstructor
public class GetContractsDto {
    private List<ContractDto> contracts;
}
