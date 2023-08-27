package com.adriana.UrlShortenerService.controller;

import com.adriana.UrlShortenerService.dto.UrlDto;
import com.adriana.UrlShortenerService.entity.Url;
import com.adriana.UrlShortenerService.service.UrlService;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.ArgumentMatchers.any;
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
    public void createShortenLink_ValidUrl_Success() {
        UrlDto urlDto = new UrlDto();
        urlDto.setUrl("https://www.example.com");
        Url url = new Url();
        url.setShortenUrl("shortLink");
        url.setOriginalUrl("https://www.example.com");
        when(service.createShortenLink(any(UrlDto.class))).thenReturn(url);

        client.post().uri("/url")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(urlDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.shortenUrl").isEqualTo("shortLink")
                .jsonPath("$.originalUrl").isEqualTo("https://www.example.com");
    }

    @Test
    public void createShortenLink_ValidUrlWithSpace_Success() {
        UrlDto urlDto = new UrlDto();
        urlDto.setUrl("   https://www.ggg.com   ");
        Url url = new Url();
        url.setShortenUrl("shortLink");
        url.setOriginalUrl("https://www.ggg.com");
        when(service.createShortenLink(any(UrlDto.class))).thenReturn(url);

        client.post().uri("/url")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(urlDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.shortenUrl").isEqualTo("shortLink")
                .jsonPath("$.originalUrl").isEqualTo("https://www.ggg.com");
    }

    @Test
    public void createShortenLink_InvalidUrl_BadRequest() {
        UrlDto urlDto = new UrlDto();
        urlDto.setUrl("invalid-url");

        client.post().uri("/url")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(urlDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void redirectToOriginalUrl_NonExistentShortUrl_ShouldReturn404NotFound() {
        when(service.getUrl("nonExistentLink")).thenReturn(null);

        client.get().uri("/url/nonExistentLink")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void redirect_ShortUrlFoundSuccess_ShouldRedirect() {
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
//    public void redirectToOriginalUrl_ExpiredShortUrl_ShouldReturnNotFound() {
//        Url url = new Url();
//        url.setExpirationDate(LocalDateTime.now().minusDays(1));
//        when(service.getUrl("expiredLink")).thenReturn(url);
//
//        client.get().uri("/url/expiredLink")
//                .exchange()
//                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
//    }

}