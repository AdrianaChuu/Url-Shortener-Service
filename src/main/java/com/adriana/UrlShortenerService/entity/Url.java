package com.adriana.UrlShortenerService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Url {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private String originalUrl;
    private String shortenUrl;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;

}
