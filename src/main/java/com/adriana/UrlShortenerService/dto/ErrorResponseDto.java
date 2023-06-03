package com.adriana.UrlShortenerService.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponseDto {
    private String status;
    private String error;
}
