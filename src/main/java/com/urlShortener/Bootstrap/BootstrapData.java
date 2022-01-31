package com.urlShortener.Bootstrap;

import com.urlShortener.Model.Role;
import com.urlShortener.Repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BootstrapData implements CommandLineRunner {


    private final RoleRepository roleRepository;


    public BootstrapData(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {


        Role user = new Role("ROLE_USER");
        Role admin = new Role("ROLE_ADMIN");
        Role master = new Role("ROLE_MANAGER");

        roleRepository.saveAll(List.of(user, admin, master));

    }
}