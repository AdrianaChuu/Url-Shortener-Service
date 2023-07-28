package com.adriana.UrlShortenerService.service;

import com.adriana.UrlShortenerService.dto.UrlDto;
import com.adriana.UrlShortenerService.entity.Url;
import com.adriana.UrlShortenerService.repository.UrlRepository;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class UrlServiceImpl implements UrlService{

    private UrlRepository urlRepository;

    @Autowired
    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public Url createShortenLink(UrlDto urlDto) {
        String encodedUrl = encodeUrl(urlDto.getUrl());
        Url urlToSave = new Url();
        urlToSave.setCreationDate(LocalDateTime.now());
        urlToSave.setShortenUrl(encodedUrl);
        urlToSave.setOriginalUrl(urlDto.getUrl());
        urlToSave.setExpirationDate(getExpirationDate(urlDto.getExpirationDate(), urlToSave.getCreationDate()));
        Url urlToPersist = saveCreatedUrl(urlToSave);

        return urlToPersist;
    }

    private LocalDateTime getExpirationDate(String expirationDate, LocalDateTime creationDate) {
        if(StringUtils.isBlank(expirationDate)){
            return creationDate.plusHours(12);
        }
        return LocalDateTime.parse(expirationDate);
    }

    private String encodeUrl(String url) {
        LocalDateTime time = LocalDateTime.now();
        return Hashing.crc32().hashString(url.concat(time.toString()), StandardCharsets.UTF_8).toString();
    }

    @Override
    public Url saveCreatedUrl(Url url) {
        return urlRepository.save(url);
    }

    @Override
    public Url getUrl(String url) {
        return urlRepository.findByShortenUrl(url);
    }

    @Override
    public void deleteURL(Url url) {
        urlRepository.delete(url);
    }
}
