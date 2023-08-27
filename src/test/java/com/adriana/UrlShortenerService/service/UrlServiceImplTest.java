package com.adriana.UrlShortenerService.service;

import com.adriana.UrlShortenerService.dto.UrlDto;
import com.adriana.UrlShortenerService.entity.Url;
import com.adriana.UrlShortenerService.repository.UrlRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.adriana.UrlShortenerService.service.UrlServiceImpl.encodeUrl;
import static com.adriana.UrlShortenerService.service.UrlServiceImpl.getExpirationDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlServiceImplTest {

    @Mock
    private UrlRepository urlRepository;
    private UrlServiceImpl urlServiceImpl;

    @BeforeEach
    void setUp() {
        urlServiceImpl = new UrlServiceImpl(urlRepository);
    }

    @Test
    void createShortenLink_shouldSuccessfullySaved() {
        UrlDto urlDto = new UrlDto();
        urlDto.setUrl("https://www.chevalcollection.com");
        LocalDateTime currentTime = LocalDateTime.now();
        Url urlToSave = Url.builder()
                .creationDate(currentTime)
                .shortenUrl(encodeUrl(urlDto.getUrl()))
                .originalUrl(urlDto.getUrl())
                .expirationDate(getExpirationDate(urlDto.getExpirationDate(), currentTime))
                .build();

        when(urlRepository.save(any(Url.class))).thenReturn(urlToSave);

        Url actual = urlServiceImpl.createShortenLink(urlDto);
        assertNotNull(actual);
        assertNotNull(actual.getShortenUrl());
        assertNotNull(actual.getExpirationDate());
        assertEquals("https://www.chevalcollection.com", actual.getOriginalUrl());
        verify(urlRepository, times(1)).save(any());
    }

    @Test
    void createShortenLink_ExistingUrl_ShouldUpdateExpirationDate() {
        UrlDto urlDto = new UrlDto();
        urlDto.setUrl("https://www.example.com");
        LocalDateTime currentTime = LocalDateTime.now();
        Url existingUrl = Url.builder()
                .creationDate(currentTime.minusDays(1))
                .shortenUrl("existingShortLink") // Provide an existing shortened URL here
                .originalUrl(urlDto.getUrl())
                .expirationDate(currentTime.plusDays(1))
                .build();

        when(urlRepository.findByOriginalUrl(urlDto.getUrl())).thenReturn(existingUrl);
        when(urlRepository.save(any(Url.class))).thenReturn(existingUrl);

        Url actual = urlServiceImpl.createShortenLink(urlDto);
        assertNotNull(actual);
        assertEquals("existingShortLink", actual.getShortenUrl());
        assertEquals(currentTime.plusDays(1).toLocalDate(), actual.getExpirationDate().toLocalDate());
        verify(urlRepository, times(1)).save(any());
    }

    @Test
    void getUrl_ShouldReturnNullForNonExistentShortLink() {
        when(urlRepository.findByShortenUrl("nonExistentShortLink")).thenReturn(null);
        assertNull(urlServiceImpl.getUrl("nonExistentShortLink"));
    }

    @Test
    void getUrl_shouldSuccessfullyGetUrl() {
        UrlDto urlDto = new UrlDto();
        urlDto.setUrl("https://google.com");
        LocalDateTime currentTime = LocalDateTime.now();
        Url urlToSave = Url.builder()
                .creationDate(currentTime)
                .shortenUrl(encodeUrl(urlDto.getUrl()))
                .originalUrl(urlDto.getUrl())
                .expirationDate(getExpirationDate(urlDto.getExpirationDate(), currentTime))
                .build();

        when(urlRepository.save(any(Url.class))).thenReturn(urlToSave);
        when(urlRepository.findByShortenUrl(urlToSave.getShortenUrl())).thenReturn(urlToSave);
        Url url = urlServiceImpl.saveCreatedUrl(urlToSave);
        assertEquals("https://google.com", urlServiceImpl.getUrl(url.getShortenUrl()).getOriginalUrl());
    }

    @Test
    void deleteURL_ShouldDeleteUrl() {
        Url url = new Url();
        url.setShortenUrl("shortLink"); // Provide a shortened URL to delete
        urlServiceImpl.deleteURL(url);
        verify(urlRepository, times(1)).delete(url);
    }

    @Test
    void deleteURL_valueShouldBeDeleted() {
        UrlDto urlDto = new UrlDto();
        urlDto.setUrl("https://gov.com");
        LocalDateTime currentTime = LocalDateTime.now();
        Url urlToSave = Url.builder()
                .creationDate(currentTime)
                .shortenUrl(encodeUrl(urlDto.getUrl()))
                .originalUrl(urlDto.getUrl())
                .expirationDate(getExpirationDate(urlDto.getExpirationDate(), currentTime))
                .build();

        when(urlRepository.save(any(Url.class))).thenReturn(urlToSave);
        Url url = urlServiceImpl.saveCreatedUrl(urlToSave);
        urlServiceImpl.deleteURL(url);
        verify(urlRepository, times(1)).delete(url);
    }


}
