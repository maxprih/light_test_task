package com.maxpri.light_test_task.model.dto;

import com.maxpri.light_test_task.model.entity.ERoles;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author max_pri
 */
@Data
@AllArgsConstructor
public class ParticipantRegisterRequest {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String fatherName;
    private Integer age;
    private Boolean hasCovidTest;
    private ERoles role;
}
