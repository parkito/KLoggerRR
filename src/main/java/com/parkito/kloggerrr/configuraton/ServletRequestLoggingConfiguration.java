package com.parkito.kloggerrr.configuraton;

import com.parkito.kloggerrr.implementation.RequestLogger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Artem Karnov @date 10/31/2017.
 * artem.karnov@t-systems.com
 */
public class ServletRequestLoggingConfiguration implements Filter {
    private RequestLogger requestLogger = new RequestLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        requestLogger.doLogging((HttpServletRequest) request, (HttpServletResponse) response, filterChain);
    }
}
