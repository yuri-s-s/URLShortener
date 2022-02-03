package com.urlShortener.Service.Interface;

import com.urlShortener.DTO.UrlDTO.UrlRequestDTO;
import com.urlShortener.DTO.UrlDTO.UrlResponseDTO;
import com.urlShortener.DTO.UrlDTO.UrlStatisticsDTO;
import com.urlShortener.Model.Url;
import com.urlShortener.Model.User;

import java.util.List;

public interface IUrlService {

    List<UrlResponseDTO> findAll();

    UrlResponseDTO create(UrlRequestDTO url, User user);

    List<Url> getUrlWithUserByOriginalUrl(String originalUrl, long userId);

    UrlResponseDTO findByShortenedUrl(String shortenedUrl);

    UrlStatisticsDTO statisticsByShortenedUrl(String shortenedUrl);

}
