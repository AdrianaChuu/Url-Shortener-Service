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
        if(StringUtils.isNotEmpty(urlDto.getUrl())){
            String encodedUrl = encodeUrl(urlDto.getUrl());
            Url urlToSave = new Url();
            urlToSave.setCreationDate(LocalDateTime.now());
            urlToSave.setShortenUrl(encodedUrl);
            urlToSave.setOriginalUrl(urlDto.getUrl());
            urlToSave.setExpirationDate(getExpirationDate(urlDto.getExpirationDate(), urlToSave.getCreationDate()));
            Url urlToPersist = persistShortenLink(urlToSave);

            if(urlToPersist!=null){
                return urlToPersist;
            }
        }
        return null;
    }

    private LocalDateTime getExpirationDate(String expirationDate, LocalDateTime creationDate) {
        if(StringUtils.isBlank(expirationDate)){
            return creationDate.plusDays(1);
        }
        LocalDateTime expirationDateToSet = LocalDateTime.parse(expirationDate);
        return expirationDateToSet;
    }

    private String encodeUrl(String url) {
        String encodedUrl = "";
        LocalDateTime time = LocalDateTime.now();
        encodedUrl = Hashing.crc32()
                .hashString(url.concat(time.toString()), StandardCharsets.UTF_8)
                .toString();

        return encodedUrl;
    }

    @Override
    public Url persistShortenLink(Url url) {
        Url urlToSave = urlRepository.save(url);
        return urlToSave;
    }

    @Override
    public Url getUrl(String url) {
        Url urlToGet = urlRepository.findByShortenUrl(url);
        return urlToGet;
    }
}
