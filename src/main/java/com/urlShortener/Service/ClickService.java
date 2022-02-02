package com.urlShortener.Service;

import com.urlShortener.DTO.ClickDTO;
import com.urlShortener.DTO.UrlResponseDTO;
import com.urlShortener.Model.Click;
import com.urlShortener.Model.Url;
import com.urlShortener.Repository.ClickRepository;
import com.urlShortener.Service.Interface.IClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClickService implements IClickService {

    @Autowired
    private ClickRepository clickRepository;

    @Override
    public List<ClickDTO> findAll() {
        List<Click> clicks = clickRepository.findAll();

        ArrayList<ClickDTO> clicksDTO = new ArrayList<>();

        for (Click c : clicks) {

            ClickDTO clickDTO = new ClickDTO(c.getCreatedAt());

            clicksDTO.add(clickDTO);
        }

        return clicksDTO;
    }

    @Override
    public Click create(Url url) {

        Click click = new Click();

        click.setUrl(url);

        clickRepository.save(click);

        return click;
    }

    @Override
    public List<ClickDTO> getClicksByOriginalUrl(long urlId) {
        List<Click> clicks = clickRepository.getClicksByOriginalUrl(urlId);

        ArrayList<ClickDTO> clicksDTO = new ArrayList<>();

        for (Click c : clicks) {

            ClickDTO clickDTO = new ClickDTO(c.getCreatedAt());

            clicksDTO.add(clickDTO);
        }

        return clicksDTO;
    }

}
