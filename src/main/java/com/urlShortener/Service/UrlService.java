package com.urlShortener.Service;

import com.urlShortener.DTO.UrlRequestDTO;
import com.urlShortener.DTO.UrlResponseDTO;
import com.urlShortener.DTO.UserDTO;
import com.urlShortener.Model.Url;
import com.urlShortener.Model.User;
import com.urlShortener.Repository.UrlRepository;
import com.urlShortener.Repository.UserRepository;
import com.urlShortener.Service.Interface.IUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UrlService implements IUrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${url.short}")
    private String baseUrl;

    @Override
    public List<UrlResponseDTO> findAll() {
        List<Url> urls = urlRepository.findAll();

        ArrayList<UrlResponseDTO> urlsDTO = new ArrayList<>();

        for (Url u : urls) {

            UrlResponseDTO urlDto = new UrlResponseDTO(u.getOriginalUrl(), baseUrl + u.getShortenedUrl());

            urlsDTO.add(urlDto);
        }

        return urlsDTO;
    }

    @Override
    public UrlResponseDTO create(UrlRequestDTO url, User user) {

        String date = Long.toString(new Date().getTime());

        Url newUrl = new Url(url.getOriginalUrl(), date);

        newUrl.setUser(user);

        urlRepository.save(newUrl);

        UrlResponseDTO urlResponse = new UrlResponseDTO(newUrl.getOriginalUrl(), baseUrl + date);

        return urlResponse;
    }

    @Override
    public List<Url> getUrlWithUserByOriginalUrl(String originalUrl, long userId) {
        return urlRepository.getUrlWithUserByOriginalUrl(originalUrl, userId);
    }

    @Override
    public UrlResponseDTO findByShortenedUrl(String shortenedUrl) {
        Url url = urlRepository.findByShortenedUrl(shortenedUrl);

        if (url == null){

            return null;
        }

        UrlResponseDTO urlResponse = new UrlResponseDTO(url.getOriginalUrl(), baseUrl + url.getShortenedUrl());

        return urlResponse;
    }

}
