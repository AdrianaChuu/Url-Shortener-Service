package com.adriana.UrlShortenerService.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
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
