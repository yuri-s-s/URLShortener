package com.urlShortener.Controller.HandleException;

import com.urlShortener.DTO.ErrorDTO.ErrorDTO;
import com.urlShortener.Exception.UserException.UserCreateException;
import com.urlShortener.Exception.UserException.UserRoleAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserHandleException {

    @ExceptionHandler(UserCreateException.class)
    public ResponseEntity<ErrorDTO> handleUserCreateException(UserCreateException e) {
        ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(error, error.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleUserNotFoundException(Exception e) {
        ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(error, error.getHttpStatus());
    }

    @ExceptionHandler(UserRoleAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleUserRoleAlreadyExistsException(UserRoleAlreadyExistsException e) {
        ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(error, error.getHttpStatus());
    }

}
