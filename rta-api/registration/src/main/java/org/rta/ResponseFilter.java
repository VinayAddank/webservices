package org.rta;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.rta.security.utills.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ResponseFilter extends OncePerRequestFilter {

    private static final Logger log = Logger.getLogger(ResponseFilter.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader(tokenHeader);
        if (200 == response.getStatus() && !StringUtils.isEmpty(token)) {
            String oldTokenUserName = jwtTokenUtil.getUsernameFromToken(token);
            Long oldTokenUserId = jwtTokenUtil.getUserIdFromToken(token);
            String refToken = jwtTokenUtil.refreshToken(token);
            String newTokenUserName = jwtTokenUtil.getUsernameFromToken(refToken);
            Long newTokenUserId = jwtTokenUtil.getUserIdFromToken(refToken);
            
            log.debug(oldTokenUserName+"::"+oldTokenUserId+"::: OLD TOKEN and NEW TOKEN:::"+newTokenUserName+"::"+newTokenUserId);
            response.addHeader("token", refToken);
        }
        filterChain.doFilter(request, response);
    }

}
