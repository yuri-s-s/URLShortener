package com.urlShortener.Controller.HandleException;

import com.urlShortener.DTO.ErrorDTO;
import com.urlShortener.Exception.BaseException.BaseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseNotFoundHandleException {

    @ExceptionHandler(BaseNotFoundException.class)
    public ResponseEntity<ErrorDTO> notFoundException(BaseNotFoundException e) {
        ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(error, error.getHttpStatus());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleException(UsernameNotFoundException e) {
        ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND, "User not found!");
        return new ResponseEntity<>(error, error.getHttpStatus());
    }

}
