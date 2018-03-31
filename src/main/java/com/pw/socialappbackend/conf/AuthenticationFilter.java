package com.pw.socialappbackend.conf;

import com.pw.socialappbackend.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if(request.getHeader("Token").isEmpty()) {

            if( (("/auth/register".equals(request.getRequestURI()))) || ("/auth/login".equals(request.getRequestURI())) ) {
                chain.doFilter(req, res);
            } else {
                ((HttpServletResponse) res).setStatus(401);
            }
        } else {
            if(authenticationService.isTokenInRequestIsValidForUser(request.getHeader("Token"))){
                chain.doFilter(req, res);

            } else {
                logger.info("Problem durning checking a token");
                ((HttpServletResponse)res).setStatus(401);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
