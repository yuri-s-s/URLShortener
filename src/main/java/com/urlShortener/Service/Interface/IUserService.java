package com.urlShortener.Service.Interface;

import com.urlShortener.DTO.UserDTO.UserDTO;
import com.urlShortener.DTO.UserDTO.UserEditDTO;
import com.urlShortener.DTO.UserDTO.UserEditPasswordDTO;
import com.urlShortener.DTO.UserDTO.UserRoleDTO;
import com.urlShortener.Model.User;

import java.util.List;

public interface IUserService {

    List<UserDTO> findAll(String sort, String order);

    List<UserDTO> findAllPaginated(int page, int pageSize, String sort, String order);

    long countAll();

    UserDTO findById(long id);

    UserDTO update(long id, UserEditDTO userEditDTO);

    UserDTO updatePassword(long id, UserEditPasswordDTO userEditDTO);

    User getById(long id);

    UserRoleDTO findByIdWithRoles(long id);

    UserRoleDTO findByEmail(String email);

    UserRoleDTO create(User user);

    UserDTO remove (long id);

    UserRoleDTO addRole(long id, short role_id);
}