package com.adriana.UrlShortenerService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "url")
public class Url {

    @Id
    @GeneratedValue
    private String id;

    @Lob
    private String originalUrl;
    private String shortenUrl;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;

}
