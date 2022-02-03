package com.urlShortener.Controller;

import com.urlShortener.Config.SwaggerConfig;
import com.urlShortener.Controller.Validation.UrlValidation;
import com.urlShortener.Controller.Validation.UserValidation;
import com.urlShortener.DTO.UrlDTO.UrlPaginationDTO;
import com.urlShortener.DTO.UrlDTO.UrlRequestDTO;
import com.urlShortener.DTO.UrlDTO.UrlResponseDTO;
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
    public @ResponseBody ResponseEntity<UrlPaginationDTO> getAll(@RequestParam(required = false) String page, @RequestParam(required = false) String pageSize, @RequestParam(required = false) String sort, @RequestParam(required = false) String order){

        List<UrlResponseDTO> urls;

        String newSort = sort == null ? "id" : sort;
        String newOrder = order == null ? "ASC" : order;

        Integer newPage = page != null ? Integer.valueOf(page) : null;
        Integer newPageSize = page != null ? Integer.valueOf(pageSize) : null;

        if (page != null && pageSize != null) {

            urls = iUrlService.findAllPaginated(newPage, newPageSize, newSort, newOrder);

        } else {
            urls = iUrlService.findAll(newSort, newOrder);
        }

        UrlPaginationDTO urlPaginationDTO = new UrlPaginationDTO(newPage, newPageSize, urls);

        return new ResponseEntity<UrlPaginationDTO>(urlPaginationDTO, HttpStatus.OK);

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
