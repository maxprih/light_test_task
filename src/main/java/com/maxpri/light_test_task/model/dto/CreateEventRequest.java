package com.maxpri.light_test_task.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author max_pri
 */
@Data
@AllArgsConstructor
public class CreateEventRequest {
    private String name;
    private Integer cost;
}
