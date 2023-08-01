package com.adriana.UrlShortenerService.controller;

import com.adriana.UrlShortenerService.entity.Url;
import com.adriana.UrlShortenerService.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("test")
class UrlShortenControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private UrlService service;

    @BeforeEach
    public void setUp() {
        client = client.mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();
    }

    @Test
    void createShortenLink() {
    }

    @Test
    public void redirect_ShortUrlFound_ShouldRedirect() {
        Url url = new Url();
        url.setOriginalUrl("https://example.com");
        url.setShortenUrl("abc123");
        url.setCreationDate(LocalDateTime.now());
        url.setExpirationDate(LocalDateTime.now().plusDays(1));
        when(service.getUrl("abc123")).thenReturn(url);

        client.get().uri("/url/abc123")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "https://example.com");
    }

    @Test
    public void redirect_ShortUrlNotFound_ShouldReturn404NotFound() {
        when(service.getUrl("abc1234")).thenReturn(null);

        client.get().uri("/url/abc1234")
                .exchange()
                .expectStatus().isNotFound();
    }

//    @Test
//    public void createdURL_ShouldReturnSuccessfully() {
//        LocalDateTime time = LocalDateTime.now();
//        Url url = new Url("123","https://example.com","abc000", time, null);
//        //when(service.saveURL(url)).thenReturn(new URL(1,"https://example.com", time, "abc123"));
//        ///when(service.saveURL(url)).thenReturn(url);
////        when(service.saveCreatedUrl(any(Url.class)).thenReturn(url));
//        when(service.saveCreatedUrl(url)).thenReturn(url);
//
//        client.post().uri("/url/abc000")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(url))
//                .exchange()
//                .expectStatus().is2xxSuccessful()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON)
//                .expectBody(Url.class)
//                .consumeWith(result -> {
//                    assertEquals("https://example.com", result.getResponseBody().getOriginalUrl());
//                    assertEquals("abc000", result.getResponseBody().getShortenUrl());
//                });
//    }

}