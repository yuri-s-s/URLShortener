package com.urlShortener.Controller;

import com.urlShortener.Config.SwaggerConfig;
import com.urlShortener.DTO.UrlDTO.UrlResponseDTO;
import com.urlShortener.DTO.UrlDTO.UrlStatisticsDTO;
import com.urlShortener.Exception.BaseException.BaseNotFoundException;
import com.urlShortener.Service.Interface.IUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@Api(tags = {SwaggerConfig.TAG_4})
@RestController
public class ShortUrlController {

    @Autowired
    private IUrlService iUrlService;

    @ApiOperation(value = "This method redirect a shortened url to a original url")
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

    @ApiOperation(value = "This method shows the statistics of a url")
    @RequestMapping(produces = "application/json", value = "/short/{shortenedUrl}/statistics", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<UrlStatisticsDTO> statisticsByShortenedUrl(@PathVariable String shortenedUrl){

        UrlStatisticsDTO url = iUrlService.statisticsByShortenedUrl(shortenedUrl);

        if(url == null){
            throw new BaseNotFoundException("Url not found!");
        }

        return new ResponseEntity<UrlStatisticsDTO>(url, HttpStatus.SEE_OTHER);
    }

}
