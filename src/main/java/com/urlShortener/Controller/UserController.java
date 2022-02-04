package com.urlShortener.Controller;

import com.urlShortener.Config.SwaggerConfig;
import com.urlShortener.Controller.Validation.RoleValidation;
import com.urlShortener.Controller.Validation.UserValidation;
import com.urlShortener.DTO.UserDTO.*;
import com.urlShortener.Exception.BaseException.BaseNotFoundException;
import com.urlShortener.Exception.UserException.UserCreateException;
import com.urlShortener.Model.User;
import com.urlShortener.Service.Interface.IUserService;
import com.urlShortener.Service.UserService;
import com.urlShortener.Util.JWTUtility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {SwaggerConfig.TAG_1})
@RestController
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private UserValidation userValidation;

    @Autowired
    private RoleValidation roleValidation;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "This method returns a list of users")
    @RequestMapping(produces = "application/json", value = "/user", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<UserPaginationDTO> getAll(@RequestParam(required = false) String page, @RequestParam(required = false) String pageSize, @RequestParam(required = false) String sort, @RequestParam(required = false) String order) {

        List<UserDTO> users;

        String newSort = sort == null ? "id" : sort;
        String newOrder = order == null ? "ASC" : order;

        Integer newPage = page != null ? Integer.valueOf(page) : null;
        Integer newPageSize = page != null ? Integer.valueOf(pageSize) : null;

        if (page != null && pageSize != null) {

            users = iUserService.findAllPaginated(newPage, newPageSize, newSort, newOrder);

        } else {
            users = iUserService.findAll(newSort, newOrder);
        }

        UserPaginationDTO userPaginationDTO = new UserPaginationDTO(newPage, newPageSize, users);

        return new ResponseEntity<UserPaginationDTO>(userPaginationDTO, HttpStatus.OK);

    }

    @ApiOperation(value = "This method returns a user")
    @RequestMapping(produces = "application/json", value = "/user/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<UserDTO> getById(@PathVariable long id) throws BaseNotFoundException {


        UserDTO user = iUserService.findById(id);

        if (user == null) {

            throw new BaseNotFoundException("User not found!");
        }

        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);

    }

    @ApiOperation(value = "This method returns a user with their roles")
    @RequestMapping(produces = "application/json", value = "/user/roles/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<UserRoleDTO> findByIdWithRoles(@PathVariable long id) throws BaseNotFoundException {


        UserRoleDTO user = iUserService.findByIdWithRoles(id);

        if (user == null) {

            throw new BaseNotFoundException("User not found!");
        }

        return new ResponseEntity<UserRoleDTO>(user, HttpStatus.OK);

    }

    @ApiOperation(value = "This method creates a new user")
    @RequestMapping(produces = "application/json", value = "/user", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<UserAuthenticateDTO> create(@RequestBody User user) throws UserCreateException {

        userValidation.validationCreate(user);

        UserDTO newUser = iUserService.create(user);

        UserDetails userDetails
                = userService.loadUserByUsername(newUser.getEmail());

        String token =
                jwtUtility.generateToken(userDetails);

        UserAuthenticateDTO userAuthenticateDTO = new UserAuthenticateDTO(newUser.getId(), newUser.getName(), newUser.getEmail(), token);

        return new ResponseEntity<UserAuthenticateDTO>(userAuthenticateDTO, HttpStatus.CREATED);
    }

    @ApiOperation(value = "This method updates a user")
    @RequestMapping(produces = "application/json", value = "/user/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<UserDTO> update(@PathVariable long id, @RequestBody UserEditDTO user) throws UserCreateException {

        userValidation.validationEdit(user);

        UserDTO newUser = iUserService.update(id, user);

        if (newUser == null){

            throw new BaseNotFoundException("User not found!");
        }

        return new ResponseEntity<UserDTO>(newUser, HttpStatus.CREATED);

    }

    @ApiOperation(value = "This method remove a user")
    @RequestMapping(produces = "application/json", value = "/user/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseEntity<UserDTO> remove(@PathVariable long id) throws BaseNotFoundException {


        UserDTO user = iUserService.remove(id);

        if (user == null) {

            throw new BaseNotFoundException("User not found!");
        }

        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);

    }

    @ApiOperation(value = "This method adds user roles")
    @RequestMapping(produces = "application/json", value = "/user/{id}/role/{role_id}", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<UserRoleDTO> addRole(@PathVariable long id, @PathVariable short role_id) throws UserCreateException {

        roleValidation.validationRoleExists(role_id);

        UserRoleDTO user = iUserService.addRole(id, role_id);

        if (user == null) {

            throw new BaseNotFoundException("User not Found!");
        }

        return new ResponseEntity<UserRoleDTO>(user, HttpStatus.ACCEPTED);

    }


}