package com.urlShortener.Service.Interface;

import com.urlShortener.DTO.ClickDTO.ClickDTO;
import com.urlShortener.Model.Click;
import com.urlShortener.Model.Url;

import java.util.List;

public interface IClickService {

    List<ClickDTO> findAll();

    Click create(Url url);

    List<ClickDTO> getClicksByOriginalUrl(long urlId);
}
