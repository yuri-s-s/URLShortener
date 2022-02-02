package com.urlShortener.Controller.Validation;

import com.urlShortener.DTO.UrlRequestDTO;
import com.urlShortener.Exception.UserException.UserCreateException;
import com.urlShortener.Model.Url;
import com.urlShortener.Service.Interface.IUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UrlValidation {

    @Autowired
    private IUrlService iUrlService;

    public void validationCreate(UrlRequestDTO urlRequestDTO, long userId) throws UserCreateException {

        String originalUrl = urlRequestDTO.getOriginalUrl();

        if (originalUrl == null) {
            throw new UserCreateException("OriginalUrl is required!");
        }
        List<Url> url = iUrlService.getUrlWithUserByOriginalUrl(originalUrl, userId);

        if(!url.isEmpty()){
            throw new UserCreateException("Email already exists!");
        }

    }
}
