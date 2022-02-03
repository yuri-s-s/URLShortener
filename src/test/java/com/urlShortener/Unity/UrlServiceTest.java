package com.urlShortener.Unity;

import com.urlShortener.DTO.UrlRequestDTO;
import com.urlShortener.DTO.UrlResponseDTO;
import com.urlShortener.Model.Url;
import com.urlShortener.Model.User;
import com.urlShortener.Repository.UrlRepository;
import com.urlShortener.Service.Interface.IClickService;
import com.urlShortener.Service.UrlService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UrlServiceTest {

    @TestConfiguration
    static class UserServiceTestConfiguration{

        @Bean
        public UrlService urlService() {
            return new UrlService();
        }
    }

    @Autowired
    UrlService urlService;

    @MockBean
    UrlRepository urlRepository;

    @MockBean
    IClickService iClickService;

    @Test
    public void UrlTestCreate(){

        String originalUrl = "Teste";
        String shortenedUrl = "teste@teste.com";

        Url newUrl = new Url(originalUrl, shortenedUrl);

        String name = "Teste";
        String email = "teste@teste.com";
        String password = new BCryptPasswordEncoder().encode("user12345");

        User user = new User(name, email, password);

        when(urlRepository.save(newUrl))
                .thenReturn(newUrl);

        when(urlRepository.getById(newUrl.getId()))
                .thenReturn(newUrl);

        UrlRequestDTO urlRequestDTO = new UrlRequestDTO(originalUrl);

        UrlResponseDTO url = urlService.create(urlRequestDTO, user);

        Assertions.assertEquals(url.getOriginalUrl(), originalUrl);
    }

    @Test
    public void UserTestGetAll(){

        List<UrlResponseDTO> urls = urlService.findAll();

        assertThat(urls.size(), equalTo(2));


    }

    @Before
    public void setup(){

        when(urlRepository.findAll()).thenReturn(Arrays.asList(
                new Url("testando.com.br", "testando.com.br"),
                new Url("testando2.com.br", "testando2.com.br")
                ));
    }
}
