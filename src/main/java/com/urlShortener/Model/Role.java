package com.urlShortener.Model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table( name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public short getId() {
        return this.id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }
}