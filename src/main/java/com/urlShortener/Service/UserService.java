package com.urlShortener.Service;

import com.urlShortener.DTO.RoleDTO.RoleDTO;
import com.urlShortener.DTO.UserDTO.UserDTO;
import com.urlShortener.DTO.UserDTO.UserEditDTO;
import com.urlShortener.DTO.UserDTO.UserEditPasswordDTO;
import com.urlShortener.DTO.UserDTO.UserRoleDTO;
import com.urlShortener.Exception.UserException.UserRoleAlreadyExistsException;
import com.urlShortener.Model.Role;
import com.urlShortener.Model.User;
import com.urlShortener.Repository.UserRepository;
import com.urlShortener.Service.Interface.IRoleService;
import com.urlShortener.Service.Interface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public List<UserDTO> findAll(String sort, String order) {

        Sort s = order.equals("ASC") ? Sort.by(sort).ascending() : Sort.by(sort).descending();

        Iterable<User> users = userRepository.findAll(s);

        ArrayList<UserDTO> usersDTO = new ArrayList<>();

        for (User u : users) {

            UserDTO userDTO = new UserDTO(u.getId(), u.getName(), u.getEmail());

            usersDTO.add(userDTO);
        }

        return usersDTO;
    }

    @Override
    public List<UserDTO> findAllPaginated(int page, int pageSize, String sort, String order) {

        Sort s;

        s = order.equals("ASC") ? Sort.by(sort).ascending() : Sort.by(sort).descending();

        Pageable pageable = PageRequest.of(page - 1, pageSize, s);

        Iterable<User> users = userRepository.findAllPaginated(pageable);

        ArrayList<UserDTO> usersDTO = new ArrayList<>();

        for (User u : users) {

            UserDTO userDTO = new UserDTO(u.getId(), u.getName(), u.getEmail());

            usersDTO.add(userDTO);
        }

        return usersDTO;
    }

    @Override
    public long countAll() {
        return userRepository.count();
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
    public UserDTO update(long id, UserEditDTO userEditDTO) {
        User user = userRepository.getById(id);

        if (user == null) {
            return null;
        }

        if (userEditDTO.getName() != null) {
            user.setName(userEditDTO.getName());
        }
        userRepository.save(user);

        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail());

        return userDTO;
    }

    @Override
    public UserDTO updatePassword(long id, UserEditPasswordDTO userEditDTO) {
        User user = userRepository.getById(id);

        if (user == null) {
            return null;
        }

        user.setPassword(passwordEncoder(userEditDTO.getNewPassword()));

        userRepository.save(user);

        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail());

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
    public UserRoleDTO findByEmail(String email) {

        User u = userRepository.getByEmail(email);

        if (u == null) {

            return null;
        }

        List<RoleDTO> roles = iRoleService.getRolesByUserId(u.getId());

        UserRoleDTO userRolesDTO = new UserRoleDTO(u.getId(), u.getName(), u.getEmail(), roles);

        return userRolesDTO;
    }

    @Override
    public UserRoleDTO create(User user) {

        user.setPassword(passwordEncoder(user.getPassword()));

        Role role = iRoleService.findByName("ROLE_USER");

        user.addRole(role);

        userRepository.save(user);

        List<RoleDTO> roles = iRoleService.getRolesByUserId(user.getId());

        UserRoleDTO userRolesDTO = new UserRoleDTO(user.getId(), user.getName(), user.getEmail(), roles);

        return userRolesDTO;
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

        if (!check.isEmpty()) {

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