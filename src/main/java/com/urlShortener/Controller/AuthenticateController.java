package com.urlShortener.Controller;

import com.urlShortener.Config.SwaggerConfig;
import com.urlShortener.DTO.JWTRequest;
import com.urlShortener.DTO.JWTResponse;
import com.urlShortener.Service.UserService;
import com.urlShortener.Util.JWTUtility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {SwaggerConfig.TAG_5})
@RestController
public class AuthenticateController {

    @Autowired
    private JWTUtility jwtUtility;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    @ApiOperation(value = "This method authenticates a user")
    @PostMapping("/authenticate")
    public JWTResponse authenticate(@RequestBody JWTRequest jwtRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getEmail(),
                            jwtRequest.getPassword()
                    )
            );

        } catch (BadCredentialsException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

        final UserDetails userDetails
                = userService.loadUserByUsername(jwtRequest.getEmail());

        final String token =
                jwtUtility.generateToken(userDetails);

        return new JWTResponse(token);
    }


}