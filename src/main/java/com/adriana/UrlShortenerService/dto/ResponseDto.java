package com.adriana.UrlShortenerService.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseDto {

    private String originalUrl;
    private String shortenUrl;
    private LocalDateTime expirationDate;
}
