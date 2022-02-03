package com.urlShortener.DTO.ErrorDTO;

import org.springframework.http.HttpStatus;

public class ErrorDTO {

    private HttpStatus httpStatus;
    private String message;

    public ErrorDTO() {
    }

    public ErrorDTO(HttpStatus httpStatus, String message) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}