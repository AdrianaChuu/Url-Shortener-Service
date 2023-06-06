package com.adriana.UrlShortenerService.controller;

import com.adriana.UrlShortenerService.dto.ErrorResponseDto;
import com.adriana.UrlShortenerService.dto.ResponseDto;
import com.adriana.UrlShortenerService.dto.UrlDto;
import com.adriana.UrlShortenerService.entity.Url;
import com.adriana.UrlShortenerService.service.UrlService;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
        if(urlValidator.isValid(urlDto.getUrl())){
            Url urlToSave = urlService.createShortenLink(urlDto);
            if(urlToSave != null){
                ResponseDto responseDto = new ResponseDto();
                responseDto.setShortenUrl(urlToSave.getShortenUrl());
                responseDto.setOriginalUrl(urlToSave.getOriginalUrl());
                responseDto.setExpirationDate(urlToSave.getExpirationDate());
                return new ResponseEntity<>(responseDto, HttpStatus.OK);
            }
        }
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatus("404");
        errorResponseDto.setError("The requested resource was not found.");
        return new ResponseEntity<>(errorResponseDto, HttpStatus.OK);
    }
    @GetMapping("url/{shortLink}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortLink){
        if (!StringUtils.hasText(shortLink)) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setError("The request is malformed or missing required parameters.");
            errorResponseDto.setStatus("400");
            return new ResponseEntity<>(errorResponseDto, HttpStatus.OK);
        }

        Url urlToGet = urlService.getUrl(shortLink);
        if(urlToGet == null){
            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setError("URL may expired");
            errorResponseDto.setStatus("400");
            return new ResponseEntity<>(errorResponseDto, HttpStatus.OK);
        }

        if(urlToGet.getExpirationDate().isBefore(LocalDateTime.now())){
            //deleteShortLink
            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setError("URL expired, please generate a new URL");
            errorResponseDto.setStatus("200");
            return new ResponseEntity<>(errorResponseDto, HttpStatus.OK);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(urlToGet.getOriginalUrl()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }
}
