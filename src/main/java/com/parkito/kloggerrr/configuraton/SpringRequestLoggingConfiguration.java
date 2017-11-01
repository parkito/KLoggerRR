package com.parkito.kloggerrr.configuraton;

import com.parkito.kloggerrr.implementation.RequestLogger;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Artem Karnov @date 10/31/2017.
 * artem.karnov@t-systems.com
 */
public class SpringRequestLoggingConfiguration extends OncePerRequestFilter {
    private RequestLogger requestLogger = new RequestLogger();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        requestLogger.doLogging(request, response, filterChain);
    }
}
