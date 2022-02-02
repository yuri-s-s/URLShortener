package com.urlShortener.Controller;

import com.urlShortener.DTO.UrlResponseDTO;
import com.urlShortener.Exception.BaseException.BaseNotFoundException;
import com.urlShortener.Model.Url;
import com.urlShortener.Service.Interface.IUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UrlPathHelper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

@RestController
public class ShortUrlController {

    @Autowired
    private IUrlService iUrlService;

    @RequestMapping(produces = "application/json", value = "/short/{shortenedUrl}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<URI> findByShortenedUrl(@PathVariable String shortenedUrl) throws URISyntaxException {

        UrlResponseDTO url = iUrlService.findByShortenedUrl(shortenedUrl);

        if(url == null){
            throw new BaseNotFoundException("Url not found!");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        URI uri = new URI(url.getOriginalUrl());
        httpHeaders.setLocation(uri);

        return new ResponseEntity<URI>(httpHeaders, HttpStatus.SEE_OTHER);
    }

}
