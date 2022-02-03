package com.urlShortener.DTO.UrlDTO;

public class UrlRequestDTO {

    private String originalUrl;

    public UrlRequestDTO() {
    }

    public UrlRequestDTO(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
