package com.maxpri.light_test_task.model.dto;

import com.maxpri.light_test_task.model.entity.ERoles;
import lombok.Data;

/**
 * @author max_pri
 */
@Data
public class RegisterRequest {
    private String login;
    private String password;
    private ERoles role;
}
