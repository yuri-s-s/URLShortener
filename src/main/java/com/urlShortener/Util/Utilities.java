package com.urlShortener.Util;

import com.urlShortener.DTO.RoleDTO.RoleDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Utilities {

    public List<String> rolesToString(List<RoleDTO> rolesDto){

        List<String> roles = new ArrayList<>();

        for (RoleDTO r: rolesDto
        ) {
            roles.add(r.getName());
        }

        return roles;
    }

}
