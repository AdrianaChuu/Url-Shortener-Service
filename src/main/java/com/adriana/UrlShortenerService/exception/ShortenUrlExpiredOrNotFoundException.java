package com.adriana.UrlShortenerService.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShortenUrlExpiredOrNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ShortenUrlExpiredOrNotFoundException(String message) {
        super(message);
    }
}
