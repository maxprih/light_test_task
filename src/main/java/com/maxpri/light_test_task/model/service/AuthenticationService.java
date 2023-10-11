package com.maxpri.light_test_task.model.service;

import com.maxpri.light_test_task.model.config.JwtUtils;
import com.maxpri.light_test_task.model.config.UserDetailsImpl;
import com.maxpri.light_test_task.model.dto.*;
import com.maxpri.light_test_task.model.entity.*;
import com.maxpri.light_test_task.model.exceptions.IncorrectRoleException;
import com.maxpri.light_test_task.model.exceptions.RoleNotFoundException;
import com.maxpri.light_test_task.model.exceptions.UserAlreadyExistsException;
import com.maxpri.light_test_task.model.repository.AdminRepository;
import com.maxpri.light_test_task.model.repository.ParticipantRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author max_pri
 */
//bebra service
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AdminRepository adminRepository;
    private final ParticipantRepository participantRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, AdminRepository adminRepository, ParticipantRepository participantRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.adminRepository = adminRepository;
        this.participantRepository = participantRepository;
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

    @Transactional
    public MessageResponse signUpAdmin(AdminRegisterRequest adminRegisterRequest) {
        if (userRepository.existsByLogin(adminRegisterRequest.getLogin())) {
            throw new UserAlreadyExistsException();
        }
        Role role = roleRepository.findByName(adminRegisterRequest.getRole())
                .orElseThrow(RoleNotFoundException::new);
        if (role.getName() != ERoles.ADMIN) {
            throw new IncorrectRoleException();
        }
        User user = User
                .builder()
                .login(adminRegisterRequest.getLogin())
                .password(passwordEncoder.encode(adminRegisterRequest.getPassword()))
                .roles(Set.of(role))
                .build();
        Admin admin = Admin
                .builder()
                .inn(adminRegisterRequest.getInn())
                .orgName(adminRegisterRequest.getOrgName())
                .user(user)
                .build();
        adminRepository.save(admin);

        return new MessageResponse("Admin registered successfully!");
    }

    @Transactional
    public MessageResponse signUpParticipant(ParticipantRegisterRequest participantRegisterRequest) {
        if (userRepository.existsByLogin(participantRegisterRequest.getLogin())) {
            throw new UserAlreadyExistsException();
        }
        Role role = roleRepository.findByName(participantRegisterRequest.getRole())
                .orElseThrow(RoleNotFoundException::new);
        if (role.getName() != ERoles.PARTICIPANT) {
            throw new IncorrectRoleException();
        }
        User user = User
                .builder()
                .login(participantRegisterRequest.getLogin())
                .password(passwordEncoder.encode(participantRegisterRequest.getPassword()))
                .roles(Set.of(role))
                .build();
        Participant participant = Participant
                .builder()
                .age(participantRegisterRequest.getAge())
                .firstName(participantRegisterRequest.getFirstName())
                .lastName(participantRegisterRequest.getLastName())
                .fatherName(participantRegisterRequest.getFatherName())
                .hasCovidTest(participantRegisterRequest.getHasCovidTest())
                .events(Collections.emptyList())
                .user(user)
                .build();
        participantRepository.save(participant);

        return new MessageResponse("Participant registered successfully!");
    }

    public ResponseCookie logOutUser() {
        return jwtUtils.getCleanJwtCookie();
    }


//lol

}
