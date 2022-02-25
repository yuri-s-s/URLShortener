package com.urlShortener.Util;

import com.google.gson.Gson;
import com.urlShortener.DTO.ErrorDTO.ErrorDTO;
import com.urlShortener.Service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        try {

            String authorization = httpServletRequest.getHeader("Authorization");
            String token = null;
            String userName = null;

            if (null != authorization && authorization.startsWith("Bearer ")) {
                token = authorization.substring(7);
                userName = jwtUtility.getUsernameFromToken(token);

            } else if (null != authorization && !authorization.startsWith("Bearer ")){

                JWTError(httpServletResponse, HttpStatus.UNAUTHORIZED, "No token provide!");
                return;
            }

            if (null != userName && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userService.loadUserByUsername(userName);

                if (userDetails == null) {

                    JWTError(httpServletResponse, HttpStatus.UNAUTHORIZED, "User not found!");
                    return;

                }
                if (jwtUtility.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                    );

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }

            }

            filterChain.doFilter(httpServletRequest, httpServletResponse);

        }
        catch (ExpiredJwtException e) {
            String path = httpServletRequest.getRequestURI();

            if(!path.equals("/api/authenticate") && !path.equals("/api/register")) {
                JWTError(httpServletResponse, HttpStatus.UNAUTHORIZED, "Expired token!");

            }else{
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        }
        catch (SignatureException e) {
            String path = httpServletRequest.getRequestURI();

            if(!path.equals("/api/authenticate") && !path.equals("/api/register")) {

                JWTError(httpServletResponse, HttpStatus.UNAUTHORIZED, "Signature exception!");

            }else{
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }

        }
    }


    private void JWTError( HttpServletResponse httpServletResponse, HttpStatus status, String message) throws IOException {

        httpServletResponse.setStatus(status.value());

        ErrorDTO errorDTO = new ErrorDTO(status, message);

        String json = new Gson().toJson(errorDTO);

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");

        httpServletResponse.getWriter().write(json);

    }

}