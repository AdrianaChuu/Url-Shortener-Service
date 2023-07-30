package com.adriana.UrlShortenerService;

import com.adriana.UrlShortenerService.dto.UrlDto;
import com.adriana.UrlShortenerService.entity.Url;
import com.adriana.UrlShortenerService.repository.UrlRepository;
import com.adriana.UrlShortenerService.service.UrlServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceTest {
    @Mock
    private UrlRepository dao;
    @InjectMocks
    private UrlServiceImpl service;
    private UrlDto urlDto = new UrlDto();
    private Url newUrl =  new Url();

    public Url transformDTOtoEntity(UrlDto url){
        newUrl.setOriginalUrl(url.getUrl());
        return newUrl;
    }
    @Test
    public void shouldSaveURLWithShortUrlAndCreationDate() {
        urlDto.setUrl("https://www.chevalcollection.com");
        dao = mock(UrlRepository.class);
        service = new UrlServiceImpl(dao);
        when(dao.save(any(Url.class))).thenReturn(newUrl);
//        when(dao.save(transformDTOtoEntity(urlDto))).thenReturn(newUrl);
        Url savedUrl = service.createShortenLink(urlDto);

        assertNotNull(savedUrl);
        assertNotNull(savedUrl.getShortenUrl());
        assertNotNull(savedUrl.getExpirationDate());
        assertEquals("https://www.chevalcollection.com", savedUrl.getOriginalUrl());
        verify(dao, times(1)).save(any());
    }

}
