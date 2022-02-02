package com.urlShortener.Repository;

import com.urlShortener.Model.Click;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClickRepository extends JpaRepository<Click, Long> {

    @Query(value = "SELECT * FROM clicks c Where c.url_id = :urlId", nativeQuery = true)
    public List<Click> getClicksByOriginalUrl(long urlId);

}
