package com.urlShortener.Service.Interface;

import com.urlShortener.DTO.RoleDTO;
import com.urlShortener.Model.Role;

import java.util.List;

public interface IRoleService {

    List<RoleDTO> findAll();

    RoleDTO create(Role role);

    RoleDTO remove (short id);

    RoleDTO findById(short id);

    Role getById(short id);

    Role findByName(String name);

    List<RoleDTO> getRolesByUserId(long id);

}
