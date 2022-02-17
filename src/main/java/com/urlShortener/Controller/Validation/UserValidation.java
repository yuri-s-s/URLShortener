package com.urlShortener.Controller.Validation;

import com.urlShortener.DTO.UserDTO.UserEditDTO;
import com.urlShortener.DTO.UserDTO.UserEditPasswordDTO;
import com.urlShortener.DTO.UserDTO.UserRoleDTO;
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

        if (userName == null || userName.trim().length() == 0) {
            throw new UserCreateException("UserName is required!");
        } else if (email == null || userName.trim().length() == 0) {
            throw new UserCreateException("Email is required!");
        } else if (password == null) {
            throw new UserCreateException("Password is required!");
        } else if (password.length() < 8) {
            throw new UserCreateException("Password must contain more than 8 characters!");
        }

        UserRoleDTO u = iUserService.findByEmail(email);

        if(u != null){
            throw new UserCreateException("Email already exists!");
        }

    }

    public void validationEdit(UserEditDTO user) throws UserCreateException {

        String userName = user.getName();

        if (userName != null && userName.trim().length() == 0) {
            throw new UserCreateException("UserName is required!");
        }

    }

    public void validationEditPassword(UserEditPasswordDTO user) throws UserCreateException {

        String password = user.getPassword();
        String newPassword = user.getNewPassword();

        if (password == null) {
            throw new UserCreateException("Password is required!");
        }else if (newPassword == null) {
            throw new UserCreateException("New password is required!");
        } else if (newPassword.length() < 8) {
            throw new UserCreateException("The new password must contain more than 8 characters!");
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
