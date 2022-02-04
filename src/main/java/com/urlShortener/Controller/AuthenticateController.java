package com.urlShortener.Controller;

import com.urlShortener.Config.SwaggerConfig;
import com.urlShortener.DTO.JWTDTO.JWTRequest;
import com.urlShortener.DTO.UserDTO.UserAuthenticateDTO;
import com.urlShortener.Model.User;
import com.urlShortener.Service.Interface.IUserService;
import com.urlShortener.Service.UserService;
import com.urlShortener.Util.JWTUtility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private IUserService iUserService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    @ApiOperation(value = "This method authenticates a user")
    @PostMapping("/authenticate")
    public ResponseEntity<UserAuthenticateDTO> authenticate(@RequestBody JWTRequest jwtRequest) throws Exception {

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

        User user = iUserService.findByEmail(jwtRequest.getEmail());

        final UserDetails userDetails
                = userService.loadUserByUsername(jwtRequest.getEmail());

        final String token =
                jwtUtility.generateToken(userDetails);

        UserAuthenticateDTO userAuthenticateDTO = new UserAuthenticateDTO(user.getId(), user.getName(), user.getEmail(), token);

        return new ResponseEntity<UserAuthenticateDTO>(userAuthenticateDTO, HttpStatus.CREATED);
    }
    
}