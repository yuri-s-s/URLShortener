package com.urlShortener.Repository;

import com.urlShortener.Model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    @Query(value = "SELECT * FROM urls r Where r.original_url = :originalUrl and r.user_id = :userId", nativeQuery = true)
    public List<Url> getUrlWithUserByOriginalUrl(String originalUrl, long userId);


    @Query(value = "SELECT * FROM urls r Where r.shortened_url = :shortenedUrl LIMIT 1", nativeQuery = true)
    public Url findByShortenedUrl(String shortenedUrl);
}
