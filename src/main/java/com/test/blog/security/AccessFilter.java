package com.test.blog.security;


import com.test.blog.model.User;
import com.test.blog.service.AuthService;
import com.test.blog.service.exceptions.InvalidAccessTokenException;
import com.test.blog.util.TokenUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class AccessFilter  extends BasicAuthenticationFilter {
	
    public AccessFilter(AuthenticationManager authManager) {
        super(authManager);
    }
    
    private static final String accessTokenHeaderName = "Access-Token";
    
    
    private AuthService authService = new AuthService();


    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String accessTokenBase64 = ((HttpServletRequest) servletRequest).getHeader(accessTokenHeaderName);
        String accessToken = TokenUtils.decodeBase64(accessTokenBase64);
        UsernamePasswordAuthenticationToken authentication = createAuthByAccessToken(accessToken);

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
            return;

        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private UsernamePasswordAuthenticationToken createAuthByAccessToken(String accessToken) {
        UsernamePasswordAuthenticationToken authenticationToken = null;

        try {
            User user = authService.getUserByValidAccessToken(accessToken);
            authenticationToken = new UsernamePasswordAuthenticationToken(user, user.getPassword(), new ArrayList<>());
        } catch (InvalidAccessTokenException ex) {
            // ignore exception
        }

        return authenticationToken;
    }

}
