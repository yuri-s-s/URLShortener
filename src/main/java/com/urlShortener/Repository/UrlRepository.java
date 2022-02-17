package com.urlShortener.Repository;

import com.urlShortener.Model.Url;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    @Query(value = "SELECT * FROM urls r Where r.original_url = :originalUrl and r.user_id = :userId LIMIT 1", nativeQuery = true)
    public Url getUrlWithUserByOriginalUrl(String originalUrl, long userId);

    @Query(value = "SELECT * FROM urls r", nativeQuery = true)
    public List<Url> findAllPaginated(Pageable pageable);

    @Query(value = "SELECT * FROM urls r where user_id = :userId", nativeQuery = true)
    public List<Url> findAllPaginatedByUser(long userId, Pageable pageable);

    @Query(value = "SELECT u FROM Url u where u.user.id = :userId", nativeQuery = false)
    public List<Url> findAllByUser(long userId, Sort s);

    @Query(value = "SELECT count(*) FROM urls u where user_id = :userId", nativeQuery = true)
    public Integer countAllByUser(long userId);

    @Query(value = "SELECT * FROM urls r Where r.shortened_url = :shortenedUrl LIMIT 1", nativeQuery = true)
    public Url findByShortenedUrl(String shortenedUrl);
}
