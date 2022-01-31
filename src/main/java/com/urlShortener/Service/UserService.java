package com.urlShortener.Service;

import com.urlShortener.DTO.UserDTO;
import com.urlShortener.Model.User;
import com.urlShortener.Repository.UserRepository;
import com.urlShortener.Service.Interface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {


    @Autowired
    private UserRepository userRepository;


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
    public User findByEmail(String email) {

        User u = userRepository.getByEmail(email);

        if (u == null) {

            return null;
        }

        return u;
    }

    @Override
    public UserDTO create(User user) {

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

}