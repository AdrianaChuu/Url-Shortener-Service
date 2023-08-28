package com.adriana.UrlShortenerService.service;

import com.adriana.UrlShortenerService.dto.UrlDto;
import com.adriana.UrlShortenerService.entity.Url;
import com.adriana.UrlShortenerService.repository.UrlRepository;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UrlServiceImpl implements UrlService {
    private static final Logger LOG = LoggerFactory.getLogger(UrlServiceImpl.class);
    private final UrlRepository urlRepository;

    @Autowired
    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public Url createShortenLink(UrlDto urlDto) {
        String encodedUrl = encodeUrl(urlDto.getUrl());
        Url existingUrl = getFromExistingUrl(urlDto.getUrl());
        //if original url already exist
        if(existingUrl!=null){
            existingUrl.setExpirationDate(LocalDateTime.now().plusDays(1));
            return saveCreatedUrl(existingUrl);
        }

        //there's no such url in db, create one and save in db
        Url urlToSave = Url.builder()
                .creationDate(LocalDateTime.now())
                .shortenUrl(encodedUrl)
                .originalUrl(urlDto.getUrl())
                .expirationDate(urlDto.getExpirationDate())
                .build();

        return saveCreatedUrl(urlToSave);
    }

    static LocalDateTime getExpirationDate(String expirationDate, LocalDateTime creationDate) {
        if (StringUtils.isBlank(expirationDate)) {
            return creationDate.plusHours(12);
        }
        return LocalDateTime.parse(expirationDate);
    }

    static String encodeUrl(String url) {
        LocalDateTime time = LocalDateTime.now();
        return Hashing.crc32().hashString(url.concat(time.toString()), StandardCharsets.UTF_8).toString();
    }

    @Override
    public Url saveCreatedUrl(Url url) {
        LOG.info("saveCreatedUrl{}", url);
        return urlRepository.save(url);
    }

    @Override
    public Url getUrl(String url) {
        return urlRepository.findByShortenUrl(url);
    }

    @Override
    public Url getFromExistingUrl(String originalUrl) {
        return urlRepository.findByOriginalUrl(originalUrl);
    }

    @Override
    public void deleteURL(Url url) {
        urlRepository.delete(url);
    }
}
