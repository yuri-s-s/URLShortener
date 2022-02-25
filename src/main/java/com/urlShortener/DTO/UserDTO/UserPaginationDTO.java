package com.urlShortener.DTO.UserDTO;

import java.util.List;

public class UserPaginationDTO {

    private Integer page;
    private Integer pageSize;
    private int elements;
    private Long pages;

    private List<UserDTO> users;

    public UserPaginationDTO(Integer page, Integer pageSize, List<UserDTO> users, long count) {
        this.page = page;
        this.pageSize = pageSize;
        this.users = users;
        this.elements = users.size();
        this.pages = this.pageSize != null ? (long) Math.ceil((double) count / this.pageSize): null;
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

    public Long getPages() {
        return pages;
    }

    public void setPages(long count) {
        this.pages = this.pageSize != null ? count / this.pageSize : null;
    }
}
