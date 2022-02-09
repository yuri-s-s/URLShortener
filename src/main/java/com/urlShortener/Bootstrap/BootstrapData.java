package com.urlShortener.Bootstrap;

import com.urlShortener.Model.Role;
import com.urlShortener.Model.User;
import com.urlShortener.Repository.RoleRepository;
import com.urlShortener.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BootstrapData implements CommandLineRunner {

    private String passwordEncoder(String password) {

        return new BCryptPasswordEncoder().encode(password);
    }

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Value("${admin.password}")
    private String password;

    public BootstrapData(RoleRepository roleRepository, UserRepository userRepository) {

        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {


        Role user = new Role("ROLE_USER");
        Role admin = new Role("ROLE_ADMIN");


        User u1 = new User("Yuri", "yuri.souza@email.com", passwordEncoder(password));

        roleRepository.saveAll(List.of(user, admin));

        u1.addRole(admin);

        userRepository.save(u1);

    }
}