package com.maxpri.light_test_task.model.service;

import com.maxpri.light_test_task.model.config.JwtUtils;
import com.maxpri.light_test_task.model.config.UserDetailsImpl;
import com.maxpri.light_test_task.model.dto.LoginRequest;
import com.maxpri.light_test_task.model.dto.MessageResponse;
import com.maxpri.light_test_task.model.dto.RegisterRequest;
import com.maxpri.light_test_task.model.dto.UserInfoResponse;
import com.maxpri.light_test_task.model.entity.Role;
import com.maxpri.light_test_task.model.entity.User;
import com.maxpri.light_test_task.model.exceptions.RoleNotFoundException;
import com.maxpri.light_test_task.model.exceptions.UserAlreadyExistsException;
import com.maxpri.light_test_task.model.repository.RoleRepository;
import com.maxpri.light_test_task.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author max_pri
 */
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public UserInfoResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(), loginRequest.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new UserInfoResponse(jwtCookie.toString(), userDetails.getUsername(), roles);
    }

    public MessageResponse signUpUser(RegisterRequest registerRequest) {
        if (userRepository.existsByLogin(registerRequest.getLogin())) {
            throw new UserAlreadyExistsException();
        }
        Role role = roleRepository.findByName(registerRequest.getRole())
                .orElseThrow(RoleNotFoundException::new);

        User user = User
                .builder()
                .login(registerRequest.getLogin())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Set.of(role))
                .build();
        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }

    public ResponseCookie logOutUser() {
        return jwtUtils.getCleanJwtCookie();
    }
}
