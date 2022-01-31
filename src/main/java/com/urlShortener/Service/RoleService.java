package com.urlShortener.Service;

import com.urlShortener.DTO.RoleDTO;
import com.urlShortener.Model.Role;
import com.urlShortener.Model.User;
import com.urlShortener.Repository.RoleRepository;
import com.urlShortener.Service.Interface.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleDTO> findAll() {

        Iterable<Role> roles = roleRepository.findAll();

        ArrayList<RoleDTO> rolesDTO = new ArrayList<>();

        for (Role r : roles) {

            RoleDTO roleDTO = new RoleDTO(r.getId(), r.getName());

            rolesDTO.add(roleDTO);
        }

        return rolesDTO;
    }

    @Override
    public RoleDTO create(Role role) {

        roleRepository.save(role);

        RoleDTO roleDTO = new RoleDTO(role.getId(), role.getName());

        return roleDTO;
    }

    @Override
    public RoleDTO remove(short id) {
        Role role = roleRepository.getById(id);

        if (role == null) {

            return null;
        }

        roleRepository.delete(role);

        RoleDTO roleDTO = new RoleDTO(role.getId(), role.getName());

        return roleDTO;
    }

    @Override
    public RoleDTO findById(short id) {

        Role r = roleRepository.getById(id);

        if (r == null) {

            return null;
        }

        RoleDTO roleDTO = new RoleDTO(r.getId(), r.getName());

        return roleDTO;
    }

    @Override
    public Role getById(short id) {

        Role role = roleRepository.getById(id);

        if (role == null) {

            return null;
        }

        return role;
    }

    @Override
    public Role findByName(String name) {

        Role r = roleRepository.getByName(name);

        if (r == null) {

            return null;
        }

        return r;
    }

    @Override
    public List<RoleDTO> getRolesByUserId(long id) {

        List<Role> roles = roleRepository.getRolesByUserId(id);

        ArrayList<RoleDTO> rolesDTO = new ArrayList<>();

        for (Role r : roles) {

            RoleDTO roleDTO = new RoleDTO(r.getId(), r.getName());

            rolesDTO.add(roleDTO);
        }

        return rolesDTO;
    }
}
