package com.adriana.UrlShortenerService.exception;


import java.io.Serial;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ShortenUrlExpiredOrNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ShortenUrlExpiredOrNotFoundException(String message) {
        super(message);
    }
}
