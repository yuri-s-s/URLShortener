package com.urlShortener.DTO.UrlDTO;

import org.springframework.beans.factory.annotation.Value;

public class UrlResponseDTO {

    private String originalUrl;

    private String shortenedUrl;

    public UrlResponseDTO() {

    }

    public UrlResponseDTO( String originalUrl, String shortenedUrl) {

        this.originalUrl = originalUrl;
        this.shortenedUrl = shortenedUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortenedUrl() {

        return shortenedUrl;
    }

    public void setShortenedUrl(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }

}
