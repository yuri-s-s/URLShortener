package com.urlShortener.DTO.UserDTO;

public class UserEditPasswordDTO {

    private String password;

    private String newPassword;

    public UserEditPasswordDTO() {
    }

    public UserEditPasswordDTO(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
