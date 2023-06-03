package com.adriana.UrlShortenerService.repository;

import com.adriana.UrlShortenerService.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    public Url findByShortenUrl(String shortenLink);
}
