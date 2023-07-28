package com.adriana.UrlShortenerService.repository;

import com.adriana.UrlShortenerService.entity.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends MongoRepository<Url, String> {

    public Url findByShortenUrl(String shortenLink);
}
