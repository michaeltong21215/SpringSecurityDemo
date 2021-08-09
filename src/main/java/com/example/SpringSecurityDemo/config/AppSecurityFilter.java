package com.example.SpringSecurityDemo.config;

import com.example.SpringSecurityDemo.service.AuthUserDetailService;
import com.example.SpringSecurityDemo.utils.AppTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AppSecurityFilter extends OncePerRequestFilter {
    @Autowired
    AuthUserDetailService authUserDetailService;

    @Autowired
    AppTokenUtil appTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //preprocessing
        String requestToken = httpServletRequest.getHeader("Authorization");
        String username = "";
        String token = "";
        try {
            if(StringUtils.isNotEmpty(requestToken) && requestToken.startsWith("Bearer ")) {
                token = requestToken.substring(7);
                username = appTokenUtil.getUserNameFromToken(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(StringUtils.isNotEmpty(username) && ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetails userDetails = authUserDetailService.loadUserByUsername(username);
            if(appTokenUtil.validateAppToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
        //postprocessing
    }

}
