package com.parkito.configuraton;

import com.parkito.implementation.FilterImplementation;

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
public class ServletConfiguration implements Filter {
    private FilterImplementation filterImplementation = new FilterImplementation();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        filterImplementation.doFilter((HttpServletRequest) request, (HttpServletResponse) response, filterChain);
    }
}
