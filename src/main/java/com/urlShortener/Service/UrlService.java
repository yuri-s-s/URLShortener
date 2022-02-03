package com.urlShortener.Service;

import com.urlShortener.DTO.ClickDTO.ClickDTO;
import com.urlShortener.DTO.UrlDTO.UrlRequestDTO;
import com.urlShortener.DTO.UrlDTO.UrlResponseDTO;
import com.urlShortener.DTO.UrlDTO.UrlStatisticsDTO;
import com.urlShortener.Model.Url;
import com.urlShortener.Model.User;
import com.urlShortener.Repository.UrlRepository;
import com.urlShortener.Service.Interface.IClickService;
import com.urlShortener.Service.Interface.IUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UrlService implements IUrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private IClickService iClickService;

    @Value("${url.short}")
    private String baseUrl;

    @Override
    public List<UrlResponseDTO> findAll(String sort, String order) {

        Sort s = order.equals("ASC") ? Sort.by(sort).ascending() : Sort.by(sort).descending();

        List<Url> urls = urlRepository.findAll(s);

        ArrayList<UrlResponseDTO> urlsDTO = new ArrayList<>();

        for (Url u : urls) {

            UrlResponseDTO urlDto = new UrlResponseDTO(u.getOriginalUrl(), baseUrl + u.getShortenedUrl());

            urlsDTO.add(urlDto);
        }

        return urlsDTO;
    }

    @Override
    public List<UrlResponseDTO> findAllPaginated(int page, int pageSize, String sort, String order) {

        Sort s;

        s = order.equals("ASC") ? Sort.by(sort).ascending() : Sort.by(sort).descending();

        Pageable pageable = PageRequest.of(page - 1, pageSize, s);

        List<Url> urls = urlRepository.findAllPaginated(pageable);

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

        iClickService.create(url);

        UrlResponseDTO urlResponse = new UrlResponseDTO(url.getOriginalUrl(), baseUrl + url.getShortenedUrl());

        return urlResponse;
    }

    @Override
    public UrlStatisticsDTO statisticsByShortenedUrl(String shortenedUrl) {

        Url url = urlRepository.findByShortenedUrl(shortenedUrl);

        if (url == null){

            return null;
        }

        UrlStatisticsDTO urlResponse = new UrlStatisticsDTO(url.getOriginalUrl(), baseUrl + url.getShortenedUrl());

        List<ClickDTO> clicks = iClickService.getClicksByOriginalUrl(url.getId());

        urlResponse.setClicks(clicks);

        return urlResponse;
    }

}
