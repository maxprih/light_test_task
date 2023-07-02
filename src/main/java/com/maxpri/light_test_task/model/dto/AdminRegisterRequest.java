package com.maxpri.light_test_task.model.dto;

import com.maxpri.light_test_task.model.entity.ERoles;
import lombok.Data;

/**
 * @author max_pri
 */
@Data
public class AdminRegisterRequest {
    private String login;
    private String password;
    private String orgName;
    private String inn;
    private ERoles role;
}
