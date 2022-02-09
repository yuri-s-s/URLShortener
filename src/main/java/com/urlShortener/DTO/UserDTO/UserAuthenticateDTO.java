package com.urlShortener.DTO.UserDTO;

import com.urlShortener.DTO.RoleDTO.RoleDTO;

import java.util.ArrayList;
import java.util.List;

public class UserAuthenticateDTO {

    private Long id;

    private String name;

    private String email;

    private String token;

    private List<String> roles = new ArrayList<>();

    public UserAuthenticateDTO(Long id, String name, String email, String token, List<String> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.token = token;
        this.roles = roles;
    }

    public UserAuthenticateDTO() {

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
