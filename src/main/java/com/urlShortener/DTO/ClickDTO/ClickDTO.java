package com.urlShortener.DTO.ClickDTO;

import java.util.Date;

public class ClickDTO {

    private Date createdAt;

    public ClickDTO() {
    }

    public ClickDTO(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
