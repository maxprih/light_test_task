package com.maxpri.light_test_task.model.controller;

import com.maxpri.light_test_task.model.dto.*;
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

    @PostMapping("/signup-admin")
    public ResponseEntity<MessageResponse> singUpAdmin(@RequestBody AdminRegisterRequest adminRegisterRequest) {
        MessageResponse result = authenticationService.signUpAdmin(adminRegisterRequest);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/signup-participant")
    public ResponseEntity<MessageResponse> singUpParticipant(@RequestBody ParticipantRegisterRequest participantRegisterRequest) {
        MessageResponse result = authenticationService.signUpParticipant(participantRegisterRequest);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie result = authenticationService.logOutUser();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, result.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}
