package com.urlShortener.Exception.UserException;

public class UserRoleAlreadyExistsException extends RuntimeException{

    public UserRoleAlreadyExistsException(String msg) {
        super(msg);
    }
}
