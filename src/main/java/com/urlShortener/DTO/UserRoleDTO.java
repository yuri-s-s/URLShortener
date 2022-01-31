package com.urlShortener.DTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UserRoleDTO {

    private Long id;

    private String name;

    private String email;

    private List<RoleDTO> roles = new ArrayList<>();

    public UserRoleDTO(Long id, String name, String email, List<RoleDTO> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    public UserRoleDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addRoles(List<RoleDTO> roles){
        this.roles = roles;
    }

    public List<RoleDTO> getRoles(){
        return this.roles;
    }

}
