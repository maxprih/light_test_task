package com.maxpri.light_test_task.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author max_pri
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private String jwtCookie;
    private String username;
    private List<String> roles;
}
