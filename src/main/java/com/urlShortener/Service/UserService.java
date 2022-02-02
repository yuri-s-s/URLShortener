package com.urlShortener.Service;

import com.urlShortener.DTO.RoleDTO;
import com.urlShortener.DTO.UserDTO;
import com.urlShortener.DTO.UserRoleDTO;
import com.urlShortener.Exception.UserException.UserRoleAlreadyExistsException;
import com.urlShortener.Model.Role;
import com.urlShortener.Model.User;
import com.urlShortener.Repository.UserRepository;
import com.urlShortener.Service.Interface.IRoleService;
import com.urlShortener.Service.Interface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService, UserDetailsService {

    private String passwordEncoder(String password) {

        return new BCryptPasswordEncoder().encode(password);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IRoleService iRoleService;

    @Override
    public List<UserDTO> findAll() {
        Iterable<User> users = userRepository.findAll();

        ArrayList<UserDTO> usersDTO = new ArrayList<>();

        for (User u : users) {

            UserDTO userDTO = new UserDTO(u.getId(), u.getName(), u.getEmail());

            usersDTO.add(userDTO);
        }

        return usersDTO;
    }

    @Override
    public UserDTO findById(long id) {

        User u = userRepository.getById(id);

        if (u == null) {

            return null;
        }

        UserDTO userDTO = new UserDTO(u.getId(), u.getName(), u.getEmail());

        return userDTO;
    }

    @Override
    public User getById(long id) {

        User user = userRepository.getById(id);

        return user;
    }

    @Override
    public UserRoleDTO findByIdWithRoles(long id) {

        User u = userRepository.getById(id);

        if (u == null) {

            return null;
        }

        List<RoleDTO> roles = iRoleService.getRolesByUserId(u.getId());

        UserRoleDTO userRolesDTO = new UserRoleDTO(u.getId(), u.getName(), u.getEmail(), roles);

        return userRolesDTO;
    }

    @Override
    public User findByEmail(String email) {

        User u = userRepository.getByEmail(email);

        if (u == null) {

            return null;
        }

        return u;
    }

    @Override
    public UserDTO create(User user) {

        user.setPassword(passwordEncoder(user.getPassword()));

        Role role = iRoleService.findByName("ROLE_USER");

        user.addRole(role);

        userRepository.save(user);

        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail());

        return userDTO;
    }

    @Override
    public UserDTO remove(long id) {

        User user = userRepository.getById(id);

        if (user == null) {

            return null;
        }

        userRepository.delete(user);

        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail());

        return userDTO;
    }

    @Override
    public UserRoleDTO addRole(long id, short role_id) {

        UserRoleDTO userRole = findByIdWithRoles(id);

        if (userRole == null) {

            return null;
        }

        Role role = iRoleService.getById(role_id);

        Optional<Integer> check = userRole.getRoles().stream()
                .map(s -> Integer.valueOf(s.getId()))
                .filter(s -> s == role.getId()).findFirst();

        if(!check.isEmpty()) {

            throw new UserRoleAlreadyExistsException("Role already exists in this user!");
        }

        User user = userRepository.getById(id);

        user.addRole(role);

        userRepository.save(user);

        return findByIdWithRoles(id);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.getByEmail(username);

        if (user == null) {

            return null;
        }
        return user;
    }


}