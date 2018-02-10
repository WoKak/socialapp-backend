package com.pw.socialappbackend.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String tmp = request.getRequestURI();

        if(request.getHeader("Token").isEmpty()) {

            if("/auth/login".equals(request.getRequestURI())) {
                chain.doFilter(req, res);
            } else {
                ((HttpServletResponse) res).setStatus(401);
            }
        } else {
            //TODO: here should be some logic which checks this token - and token needs to be connected with user on db level
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}
