package com.maxpri.light_test_task.model.exceptions;

import com.maxpri.light_test_task.model.dto.MessageResponse;
import com.maxpri.light_test_task.model.entity.ERoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

/**
 * @author max_pri
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    private ResponseEntity<MessageResponse> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        MessageResponse errorDTO = new MessageResponse("User already exists!");
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RoleNotFoundException.class)
    private ResponseEntity<MessageResponse> handleRoleNotFoundException(RoleNotFoundException e) {
        MessageResponse errorDTO = new MessageResponse("Role not found! Choose one of " + Arrays.toString(ERoles.values()));
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}
