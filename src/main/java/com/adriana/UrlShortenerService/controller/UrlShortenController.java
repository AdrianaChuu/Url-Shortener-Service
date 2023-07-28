package com.adriana.UrlShortenerService.controller;

import com.adriana.UrlShortenerService.dto.ResponseDto;
import com.adriana.UrlShortenerService.dto.UrlDto;
import com.adriana.UrlShortenerService.entity.Url;
import com.adriana.UrlShortenerService.exception.InternalServerException;
import com.adriana.UrlShortenerService.exception.ShortenUrlExpiredOrNotFoundException;
import com.adriana.UrlShortenerService.exception.UrlNotFoundException;
import com.adriana.UrlShortenerService.service.UrlService;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
public class UrlShortenController {
    private UrlService urlService;
    @Autowired
    public UrlShortenController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/url")
    public ResponseEntity<?> createShortenLink(@RequestBody UrlDto urlDto){
        String[] schemes = {"http","https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        if(!urlValidator.isValid(urlDto.getUrl())) {
            throw new UrlNotFoundException("The URL is invalid");
        }

        Url urlToSave = urlService.createShortenLink(urlDto);
        if(urlToSave == null){
            throw new InternalServerException("An unexpected error occurred on the server.");
        }

        ResponseDto responseDto = new ResponseDto();
        responseDto.setShortenUrl(urlToSave.getShortenUrl());
        responseDto.setOriginalUrl(urlToSave.getOriginalUrl());
        responseDto.setExpirationDate(urlToSave.getExpirationDate());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @GetMapping("url/{shortLink}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortLink ){
        Url urlToGet = urlService.getUrl(shortLink);
        if(urlToGet == null){
            throw new InternalServerException("The shorten URL not found or expired");
        }

        if(urlToGet.getExpirationDate().isBefore(LocalDateTime.now())){
            urlService.deleteURL(urlToGet);
            throw new ShortenUrlExpiredOrNotFoundException("URL Expired. Please generate a new one.");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(urlToGet.getOriginalUrl()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }
}
