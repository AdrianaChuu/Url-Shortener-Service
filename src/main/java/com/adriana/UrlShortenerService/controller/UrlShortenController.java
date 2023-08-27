package com.adriana.UrlShortenerService.controller;

import com.adriana.UrlShortenerService.dto.ResponseDto;
import com.adriana.UrlShortenerService.dto.UrlDto;
import com.adriana.UrlShortenerService.entity.Url;
import com.adriana.UrlShortenerService.exception.InternalServerException;
import com.adriana.UrlShortenerService.exception.MalformedRequestException;
import com.adriana.UrlShortenerService.exception.ShortenUrlExpiredOrNotFoundException;
import com.adriana.UrlShortenerService.exception.UrlNotFoundException;
import com.adriana.UrlShortenerService.service.UrlService;
import com.adriana.UrlShortenerService.validateURL.Validator;
import java.net.URI;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlShortenController {

    private static final Logger LOG = LoggerFactory.getLogger(UrlShortenController.class);
    private UrlService urlService;

    @Autowired
    public UrlShortenController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/url")
    public ResponseEntity<?> createShortenLink(@RequestBody UrlDto urlDto) {
        LOG.info("createShortenLink input{}", urlDto);
        urlDto.setUrl(urlDto.getUrl().trim());
        Validator validator = new Validator();
        if (!validator.isValidUrl(urlDto.getUrl())) {
            throw new MalformedRequestException("The URL is invalid");
        }

        Url urlToSave = urlService.createShortenLink(urlDto);
        if (urlToSave == null) {
            throw new InternalServerException("An unexpected error occurred on the server.");
        }

        ResponseDto responseDto = new ResponseDto();
        responseDto.setShortenUrl(urlToSave.getShortenUrl());
        responseDto.setOriginalUrl(urlToSave.getOriginalUrl());
        responseDto.setExpirationDate(urlToSave.getExpirationDate());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @GetMapping("url/{shortLink}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortLink) {
        Url urlToGet = urlService.getUrl(shortLink);
        if (urlToGet == null) {
            throw new UrlNotFoundException("The URL is not found");
        }

        if (urlToGet.getExpirationDate()!=null && urlToGet.getExpirationDate().isBefore(LocalDateTime.now())) {
            urlService.deleteURL(urlToGet);
            throw new ShortenUrlExpiredOrNotFoundException("URL Expired. Please generate a new one.");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(urlToGet.getOriginalUrl()));
        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .headers(httpHeaders)
                .build();
    }
}
