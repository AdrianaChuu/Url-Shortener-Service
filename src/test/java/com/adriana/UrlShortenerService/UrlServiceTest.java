//package com.adriana.UrlShortenerService;
//
//import com.adriana.UrlShortenerService.dto.UrlDto;
//import com.adriana.UrlShortenerService.entity.Url;
//import com.adriana.UrlShortenerService.repository.UrlRepository;
//import com.adriana.UrlShortenerService.service.UrlService;
//import com.adriana.UrlShortenerService.service.UrlServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class UrlServiceTest {
//
//    @Mock
//    private UrlRepository dao;
//    @InjectMocks
//    private UrlServiceImpl service;
//    private UrlDto urlDto = new UrlDto();
//    private Url newUrl = new Url();
//
//    private UrlService undertest;
//
//    @BeforeEach
//    void setUp() {
//        undertest = new UrlService(service);
//    }
//
//    public Url transformDTOtoEntity(UrlDto url) {
//        newUrl.setOriginalUrl(url.getUrl());
//        return newUrl;
//    }
//
//    @Test
//    public void shouldSaveURLWithShortUrlAndCreationDate() {
//        urlDto.setUrl("https://www.chevalcollection.com");
//        dao = mock(UrlRepository.class);
//        service = new UrlServiceImpl(dao);
//        when(dao.save(any(Url.class))).thenReturn(newUrl);
////        when(dao.save(transformDTOtoEntity(urlDto))).thenReturn(newUrl);
//        Url savedUrl = service.createShortenLink(urlDto);
//
//        assertNotNull(savedUrl);
//        assertNotNull(savedUrl.getShortenUrl());
//        assertNotNull(savedUrl.getExpirationDate());
//        assertEquals("https://www.chevalcollection.com", savedUrl.getOriginalUrl());
//        verify(dao, times(1)).save(any());
//    }
////
//}
