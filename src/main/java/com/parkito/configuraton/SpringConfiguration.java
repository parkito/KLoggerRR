package com.parkito.configuraton;

import com.parkito.implementation.FilterImplementation;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SpringConfiguration extends OncePerRequestFilter {
    @Autowired
    private FilterImplementation filterImplementation;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        filterImplementation.doFilter(request, response, filterChain);
    }

}
