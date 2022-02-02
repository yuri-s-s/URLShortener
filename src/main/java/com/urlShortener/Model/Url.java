package com.urlShortener.Model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table( name = "urls")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String originalUrl;

    @NotNull
    private String shortenedUrl;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public Url() {
    }

    public Url(String originalUrl, String shortenedUrl) {
        this.originalUrl = originalUrl;
        this.shortenedUrl = shortenedUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setUser(User user) {
        this.user = user;
    }
}
