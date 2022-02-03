package com.urlShortener.DTO.UrlDTO;

import com.urlShortener.DTO.ClickDTO.ClickDTO;

import java.util.ArrayList;
import java.util.List;

public class UrlStatisticsDTO {

    private String originalUrl;

    private String shortenedUrl;

    private List<ClickDTO> clicks = new ArrayList<>();

    private long clicksQtd;

    public UrlStatisticsDTO() {

    }

    public UrlStatisticsDTO( String originalUrl, String shortenedUrl) {

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

    public List<ClickDTO> getClicks() {
        return clicks;
    }

    public void setClicks(List<ClickDTO> clicks) {
        this.clicks = clicks;
        this.clicksQtd = clicks.size();
    }

    public long getClicksQtd() {
        return clicksQtd;
    }
}
