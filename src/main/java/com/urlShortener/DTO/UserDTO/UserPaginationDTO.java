package com.urlShortener.DTO.UserDTO;

import com.urlShortener.DTO.UserDTO.UserDTO;

import java.util.List;

public class UserPaginationDTO {

    private Integer page;
    private Integer pageSize;
    private int elements;

    private List<UserDTO> users;

    public UserPaginationDTO(Integer page, Integer pageSize, List<UserDTO> users) {
        this.page = page;
        this.pageSize = pageSize;
        this.users = users;
        this.elements = users.size();
    }

    public UserPaginationDTO() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public int getElements() {
        return elements;
    }

    public void setElements(int elements) {
        this.elements = elements;
    }
}
