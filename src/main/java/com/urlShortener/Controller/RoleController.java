package com.urlShortener.Controller;

import com.urlShortener.Config.SwaggerConfig;
import com.urlShortener.Controller.Validation.RoleValidation;
import com.urlShortener.DTO.RoleDTO.RoleDTO;
import com.urlShortener.Exception.BaseException.BaseNotFoundException;
import com.urlShortener.Exception.RoleException.RoleCreateException;
import com.urlShortener.Model.Role;
import com.urlShortener.Service.Interface.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {SwaggerConfig.TAG_3})
@RestController
public class RoleController {

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private RoleValidation roleValidation;

    @ApiOperation(value = "This method returns a list of roles")
    @RequestMapping(produces = "application/json", value = "/role", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<RoleDTO>> getAll(){


        List<RoleDTO> roles = iRoleService.findAll();


        return new ResponseEntity<List<RoleDTO>>(roles, HttpStatus.OK);

    }

    @ApiOperation(value = "This method creates a new role")
    @RequestMapping(produces = "application/json", value = "/role", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<RoleDTO> create(@RequestBody Role role) throws RoleCreateException {

        roleValidation.validationCreate(role);

        RoleDTO newRole = iRoleService.create(role);

        return new ResponseEntity<RoleDTO>(newRole, HttpStatus.CREATED);

    }

    @ApiOperation(value = "This method returns a role")
    @RequestMapping(produces = "application/json", value = "/role/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<RoleDTO> getById(@PathVariable short id) throws BaseNotFoundException {


        RoleDTO role = iRoleService.findById(id);

        if (role == null){

            throw new BaseNotFoundException("Role not found!");
        }

        return new ResponseEntity<RoleDTO>(role, HttpStatus.OK);

    }

    @ApiOperation(value = "This method remove a role")
    @RequestMapping(produces = "application/json", value = "/role/{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<RoleDTO> remove(@PathVariable short id) throws BaseNotFoundException {


        RoleDTO role = iRoleService.remove(id);

        if (role == null){

            throw new BaseNotFoundException("Role not found!");
        }

        return new ResponseEntity<RoleDTO>(role, HttpStatus.OK);

    }

}
