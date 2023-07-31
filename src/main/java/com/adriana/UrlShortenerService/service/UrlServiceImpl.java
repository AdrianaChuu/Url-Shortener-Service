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
        Url urlToSave = new Url();
        urlToSave.setCreationDate(LocalDateTime.now());
        urlToSave.setShortenUrl(encodedUrl);
        urlToSave.setOriginalUrl(urlDto.getUrl());
        urlToSave.setExpirationDate(getExpirationDate(urlDto.getExpirationDate(), urlToSave.getCreationDate()));
        Url urlToPersist = saveCreatedUrl(urlToSave);

        return urlToPersist;
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
    public void deleteURL(Url url) {
        urlRepository.delete(url);
    }
}
