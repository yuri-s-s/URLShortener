package com.urlShortener.Unity;

import com.urlShortener.DTO.UserDTO.UserDTO;
import com.urlShortener.DTO.UserDTO.UserRoleDTO;
import com.urlShortener.Model.User;
import com.urlShortener.Repository.RoleRepository;
import com.urlShortener.Repository.UserRepository;
import com.urlShortener.Service.Interface.IRoleService;
import com.urlShortener.Service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @TestConfiguration
    static class UserServiceTestConfiguration{

        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    IRoleService iRoleService;

    @Test
    public void UserTestCreate(){

        String name = "Teste";
        String email = "teste@teste.com";
        String password = new BCryptPasswordEncoder().encode("user12345");

        User newUser = new User(name, email, password);

        when(userRepository.save(newUser))
                .thenReturn(newUser);

        when(userRepository.getById(newUser.getId()))
                .thenReturn(newUser);

        UserRoleDTO user = userService.create(newUser);

        UserDTO userDTO = userService.findById(user.getId());

        Assertions.assertEquals(userDTO.getEmail(), email);
        Assertions.assertEquals(userDTO.getName(), name);
    }

    @Test
    public void UserTestGetAll(){

        List<UserDTO> users = userService.findAll("id", "ASC");

        assertThat(users.size(), equalTo(4));

    }

    @Test
    public void UserTestDelete(){

        String name = "Teste";
        String email = "teste@teste.com";
        String password = new BCryptPasswordEncoder().encode("user12345");

        User newUser = new User(name, email, password);

        when(userRepository.save(newUser))
                .thenReturn(newUser);

        when(userRepository.getById(newUser.getId()))
                .thenReturn(newUser);

        UserDTO userDTO = userService.findById(newUser.getId());

        userService.remove(userDTO.getId());

        verify(userRepository).delete(newUser);

    }

    @Before
    public void setup(){

        Sort s = Sort.by("id").ascending();

        when(userRepository.findAll(s)).thenReturn(Arrays.asList(
                new User("testando", "testando@email.com", new BCryptPasswordEncoder().encode("user12345")),
                new User("testando2", "testando2@email.com", new BCryptPasswordEncoder().encode("user12345")),
                new User("testando3", "testando3@email.com", new BCryptPasswordEncoder().encode("user12345")),
                new User("testando4", "testando4@email.com", new BCryptPasswordEncoder().encode("user12345"))

        ));
    }
}