package com.adriana.UrlShortenerService.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
