package com.urlShortener.Controller.HandleException;

import com.urlShortener.DTO.ErrorDTO.ErrorDTO;
import com.urlShortener.Exception.RoleException.RoleCreateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RoleHandleException {

    @ExceptionHandler(RoleCreateException.class)
    public ResponseEntity<ErrorDTO> handleRoleCreateException(RoleCreateException e) {
        ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(error, error.getHttpStatus());
    }
}
