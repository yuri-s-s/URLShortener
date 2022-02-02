package com.urlShortener.Controller.Validation;

import com.urlShortener.Exception.BaseException.BaseNotFoundException;
import com.urlShortener.Exception.UserException.UserCreateException;
import com.urlShortener.Model.User;
import com.urlShortener.Service.Interface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidation {

    @Autowired
    private IUserService iUserService;

    public void validationCreate(User user) throws UserCreateException {

        String userName = user.getName();
        String email = user.getEmail();
        String password = user.getPassword();

        if (userName == null) {
            throw new UserCreateException("UserName is required!");
        } else if (email == null) {
            throw new UserCreateException("Email is required!");
        } else if (password == null) {
            throw new UserCreateException("Password is required!");
        } else if (password.length() < 8) {
            throw new UserCreateException("Password must contain more than 8 characters!");
        }

        User u = iUserService.findByEmail(email);

        if(u != null){
            throw new UserCreateException("Email already exists!");
        }

    }

    public User UserExists(long userId) throws UserCreateException {

        User user = iUserService.getById(userId);

        if (user == null) {
            throw new BaseNotFoundException("User not found!");
        }

        return user;

    }

    public Boolean IsUserAuthenticateEmail(String authUser, String username) throws UserCreateException {

        if (!authUser.equals(username)) {
            throw new BaseNotFoundException("This token does not belong to this user");
        }

        return true;

    }
}
