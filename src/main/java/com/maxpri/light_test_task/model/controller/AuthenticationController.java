package com.maxpri.light_test_task.model.controller;

import com.maxpri.light_test_task.model.dto.LoginRequest;
import com.maxpri.light_test_task.model.dto.MessageResponse;
import com.maxpri.light_test_task.model.dto.RegisterRequest;
import com.maxpri.light_test_task.model.dto.UserInfoResponse;
import com.maxpri.light_test_task.model.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author max_pri
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UserInfoResponse result = authenticationService.loginUser(loginRequest);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, result.getJwtCookie())
                .body(result);
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> singUp(@RequestBody RegisterRequest registerRequest) {
        MessageResponse result = authenticationService.signUpUser(registerRequest);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie result = authenticationService.logOutUser();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, result.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}
