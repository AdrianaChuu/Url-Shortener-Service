package com.adriana.UrlShortenerService.service;

import com.adriana.UrlShortenerService.entity.Url;
import com.adriana.UrlShortenerService.dto.UrlDto;
import org.springframework.stereotype.Service;

@Service
public interface UrlService {
    public Url createShortenLink(UrlDto urlDto);
    public Url persistShortenLink(Url url);
    public Url getUrl(String url);
}
