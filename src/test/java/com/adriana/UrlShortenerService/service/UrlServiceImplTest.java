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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlServiceImplTest {

    @Mock
    private UrlRepository urlRepository;
    private UrlServiceImpl undertest;

    @BeforeEach
    void setUp() {
        undertest = new UrlServiceImpl(urlRepository);
    }

    @Test
    void createShortenLink() {
        UrlDto urlDto = new UrlDto();
        urlDto.setUrl("https://www.chevalcollection.com");
        Url urlToSave = new Url();
        urlToSave.setCreationDate(LocalDateTime.now());
        urlToSave.setShortenUrl(encodeUrl(urlDto.getUrl()));
        urlToSave.setOriginalUrl(urlDto.getUrl());
        urlToSave.setExpirationDate(getExpirationDate(urlDto.getExpirationDate(), urlToSave.getCreationDate()));

        when(urlRepository.save(any(Url.class))).thenReturn(urlToSave);

        Url actual = undertest.createShortenLink(urlDto);
        assertNotNull(actual);
        assertNotNull(actual.getShortenUrl());
        assertNotNull(actual.getExpirationDate());
        assertEquals("https://www.chevalcollection.com", actual.getOriginalUrl());
        verify(urlRepository, times(1)).save(any());
    }

    @Test
    void saveCreatedUrl() {
    }

    @Test
    void getUrl() {
    }

    @Test
    void deleteURL() {
    }
}
