package com.urlShortener.Controller;

import com.urlShortener.Controller.Validation.UserValidation;
import com.urlShortener.DTO.UserDTO;
import com.urlShortener.Exception.UserException.UserCreateException;
import com.urlShortener.Model.User;
import com.urlShortener.Service.Interface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private UserValidation userValidation;

    @RequestMapping(produces = "application/json", value = "/users", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<UserDTO>> getAll(){


        List<UserDTO> users = iUserService.findAll();


        return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);

    }

    @RequestMapping(produces = "application/json", value = "/users/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<UserDTO> getById(@PathVariable long id) throws Exception {


        UserDTO user = iUserService.findById(id);

        if (user == null){

            throw new Exception("User not found!");
        }

        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);

    }

    @RequestMapping(produces = "application/json", value = "/users", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<UserDTO> create(@RequestBody User user) throws UserCreateException {

        userValidation.validationCreate(user);

        UserDTO newUser = iUserService.create(user);

        return new ResponseEntity<UserDTO>(newUser, HttpStatus.CREATED);

    }

    @RequestMapping(produces = "application/json", value = "/users/{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<UserDTO> remove(@PathVariable long id) throws Exception {


        UserDTO user = iUserService.remove(id);

        if (user == null){

            throw new Exception("User not found!");
        }

        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);

    }

}