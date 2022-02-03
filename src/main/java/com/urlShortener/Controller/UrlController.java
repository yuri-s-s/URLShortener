package com.urlShortener.Controller;

import com.urlShortener.Config.SwaggerConfig;
import com.urlShortener.Controller.Validation.UrlValidation;
import com.urlShortener.Controller.Validation.UserValidation;
import com.urlShortener.DTO.UrlRequestDTO;
import com.urlShortener.DTO.UrlResponseDTO;
import com.urlShortener.Exception.UserException.UserCreateException;
import com.urlShortener.Model.User;
import com.urlShortener.Service.Interface.IUrlService;
import com.urlShortener.Util.JWTUtility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = {SwaggerConfig.TAG_2})
@RestController
public class UrlController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private IUrlService iUrlService;

    @Autowired
    private UserValidation userValidation;

    @Autowired
    private UrlValidation urlValidation;

    @ApiOperation(value = "This method returns a list of urls")
    @RequestMapping(produces = "application/json", value = "/url", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<UrlResponseDTO>> getAll(){

        List<UrlResponseDTO> urls = iUrlService.findAll();

        return new ResponseEntity<List<UrlResponseDTO>>(urls, HttpStatus.OK);

    }

    @ApiOperation(value = "This method creates a new url")
    @RequestMapping(produces = "application/json", value = "/url/user/{userId}", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<UrlResponseDTO> create(@RequestBody UrlRequestDTO url, @PathVariable long userId) throws UserCreateException {

        String authorization = httpServletRequest.getHeader("Authorization");

        String email = jwtUtility.getUsernameFromToken(authorization.substring(7));

        User user = userValidation.UserExists(userId);

        userValidation.IsUserAuthenticateEmail(email, user.getEmail());

        urlValidation.validationCreate(url, userId);

        UrlResponseDTO newUrl = iUrlService.create(url, user);

        return new ResponseEntity<UrlResponseDTO>(newUrl, HttpStatus.CREATED);

    }

}
