package com.urlShortener.Service.Interface;

import com.urlShortener.DTO.UserDTO.UserDTO;
import com.urlShortener.DTO.UserDTO.UserRoleDTO;
import com.urlShortener.Model.User;

import java.util.List;

public interface IUserService {

    List<UserDTO> findAll(String sort, String order);

    List<UserDTO> findAllPaginated(int page, int pageSize, String sort, String order);

    UserDTO findById(long id);

    User getById(long id);

    UserRoleDTO findByIdWithRoles(long id);

    User findByEmail(String email);

    UserDTO create(User user);

    UserDTO remove (long id);

    UserRoleDTO addRole(long id, short role_id);
}