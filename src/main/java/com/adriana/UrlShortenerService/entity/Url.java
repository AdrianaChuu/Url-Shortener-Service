package com.adriana.UrlShortenerService.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "url")
public class Url {

    @Id
    private String id;
    private String originalUrl;
    private String shortenUrl;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;

}
