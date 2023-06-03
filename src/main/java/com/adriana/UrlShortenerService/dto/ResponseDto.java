package com.adriana.UrlShortenerService.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class ResponseDto {
    private String originalUrl;
    private String shortenUrl;
    private LocalDateTime expirationDate;
}
