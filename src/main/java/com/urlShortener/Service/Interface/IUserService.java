package com.urlShortener.Service.Interface;

import com.urlShortener.DTO.UserDTO;
import com.urlShortener.DTO.UserRoleDTO;
import com.urlShortener.Model.User;

import java.util.List;

public interface IUserService {

    List<UserDTO> findAll();

    UserDTO findById(long id);

    UserRoleDTO findByIdWithRoles(long id);

    User findByEmail(String email);

    UserDTO create(User user);

    UserDTO remove (long id);

    UserRoleDTO addRole(long id, short role_id);
}