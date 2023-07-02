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
        MessageResponse response = new MessageResponse("User already exists!");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RoleNotFoundException.class)
    private ResponseEntity<MessageResponse> handleRoleNotFoundException(RoleNotFoundException e) {
        MessageResponse response = new MessageResponse("Role not found! Choose one of " + Arrays.toString(ERoles.values()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IncorrectRoleException.class)
    private ResponseEntity<MessageResponse> handleIncorrectRoleException(IncorrectRoleException e) {
        MessageResponse response = new MessageResponse("Incorrect role");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AdminNotFoundException.class)
    private ResponseEntity<MessageResponse> handleAdminNotFound(AdminNotFoundException e) {
        MessageResponse response = new MessageResponse("Not found admin for given authenticated user");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ContractAlreadyExistsException.class)
    private ResponseEntity<MessageResponse> handleContractAlreadyExistsException(ContractAlreadyExistsException e) {
        MessageResponse response = new MessageResponse("Every admin can have only one contract");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NoSuchContractException.class)
    private ResponseEntity<MessageResponse> handleNoSuchContractException(NoSuchContractException e) {
        MessageResponse response = new MessageResponse("Not found contract with given id");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ContractNotSignedException.class)
    private ResponseEntity<MessageResponse> handleContractNotSignedException(ContractNotSignedException e) {
        MessageResponse response = new MessageResponse("You need to sign contract before creating event");
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = NoSuchEventException.class)
    private ResponseEntity<MessageResponse> handleNoSuchEventException(NoSuchEventException e) {
        MessageResponse response = new MessageResponse("Not found event with given id");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ParticipantNotFoundException.class)
    private ResponseEntity<MessageResponse> handleParticipantNotFoundException(ParticipantNotFoundException e) {
        MessageResponse response = new MessageResponse("Not found participant with given id");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
