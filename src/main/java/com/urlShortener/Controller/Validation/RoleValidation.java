package com.urlShortener.Controller.Validation;

import com.urlShortener.Exception.BaseException.BaseNotFoundException;
import com.urlShortener.Exception.RoleException.RoleCreateException;
import com.urlShortener.Model.Role;
import com.urlShortener.Service.Interface.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleValidation {

    @Autowired
    private IRoleService iRoleService;

    public void validationCreate(Role role){

        String name = role.getName();

        if (name == null) {
            throw new RoleCreateException("roleName is required!");
        }

        Role r = iRoleService.findByName(name);

        if(r != null){
            throw new RoleCreateException("Role already exists!");
        }


    }

    public void validationRoleExists(short role_id){

        Role role = iRoleService.getById(role_id);

        if (role == null) {
            throw new BaseNotFoundException("Role not exists!");
        }

    }

}
