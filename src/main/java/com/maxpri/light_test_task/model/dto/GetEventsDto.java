package com.maxpri.light_test_task.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author max_pri
 */
@Data
@AllArgsConstructor
public class GetEventsDto {
    private List<EventDto> events;
}
