package com.urlShortener.DTO.UserDTO;

public class UserEditDTO {

    private String name;

    public UserEditDTO(String name) {

        this.name = name;
    }

    public UserEditDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
