package com.adriana.UrlShortenerService.service;

import com.adriana.UrlShortenerService.dto.UrlDto;
import com.adriana.UrlShortenerService.entity.Url;
import org.springframework.stereotype.Service;

@Service
public interface UrlService {

    Url createShortenLink(UrlDto urlDto);

    Url saveCreatedUrl(Url url);

    Url getUrl(String url);

    Url getFromExistingUrl(String originalUrl);

    void deleteURL(Url url);
}
