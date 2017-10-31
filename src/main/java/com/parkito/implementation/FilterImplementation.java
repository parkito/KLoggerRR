package com.parkito.implementation;

import com.parkito.configuraton.SpringConfiguration;
import com.parkito.wrappers.RequestWrapper;
import com.parkito.wrappers.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Artem Karnov @date 10/31/2017.
 * artem.karnov@t-systems.com
 */
public class FilterImplementation {
    private static final Logger logger = LoggerFactory.getLogger(SpringConfiguration.class);

    public static final String EMPTY_RESPONSE_BODY = "[NONE]";

    private static AtomicInteger atomicInteger = new AtomicInteger();
    private final String CHARACTER_ENCODING = "UTF-8";

    public void doFilter(HttpServletRequest request, HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        int requestId = atomicInteger.incrementAndGet();
        request = new RequestWrapper(request);
        response = new ResponseWrapper(response);
        try {
            filterChain.doFilter(request, response);
        } finally {
            logRequest(request, requestId);
            logResponse(response, requestId);
        }
    }

    private void logRequest(HttpServletRequest httpServletRequest, int requestId) {
        RequestWrapper request = (RequestWrapper) httpServletRequest;
        StringBuilder requestHeaders = new StringBuilder();
        for (String header : Collections.list(request.getHeaderNames())) {
            requestHeaders
                    .append(header)
                    .append(": ")
                    .append(request.getHeader(header))
                    .append("\n");
        }

        StringBuilder fullUrl = new StringBuilder();
        fullUrl.append(request.getRequestURL().toString());
        if (request.getQueryString() != null) {
            fullUrl.append("?");
            fullUrl.append(request.getQueryString());
        }

        String requestBody = "[NONE]";

        try {
            String charEncoding = request.getCharacterEncoding() != null ? request.getCharacterEncoding() : "UTF-8";
            requestBody = (new String(request.toByteArray(), charEncoding));
        } catch (UnsupportedEncodingException e) {
            logger.warn("Failed to parse request body", e);
        }

        logger.info("Request Message \n" +
                        "---------------------------- \n" +
                        "RequestID:    {} \n" +
                        "Address:   {} \n" +
                        "Encoding:  {} \n" +
                        "Content type:  {} \n" +
                        "Http method:    {} \n" +
                        "Headers:    \n{}" +
                        "Request body:  \n {} \n" +
                        "----------------------------- \n",
                requestId, fullUrl, CHARACTER_ENCODING, request.getContentType(),
                request.getMethod(), requestHeaders.toString(), requestBody);
    }

    private void logResponse(HttpServletResponse httpServletResponse, int responseId) {
        ResponseWrapper response = (ResponseWrapper) httpServletResponse;
        StringBuilder responseHeaders = new StringBuilder();
        for (String header : response.getHeaderNames()) {
            responseHeaders
                    .append(header)
                    .append(": ")
                    .append(response.getHeader(header))
                    .append("\n");
        }

        String responseBody = EMPTY_RESPONSE_BODY;

        try {
            responseBody = new String(response.toByteArray(), response.getCharacterEncoding());
        } catch (UnsupportedEncodingException e) {
            logger.warn("Failed to parse response body", e);
        }
        logger.info("Response Message \n" +
                        "---------------------------- \n" +
                        "ResponseID:    {} \n" +
                        "Response-Code:   {} \n" +
                        "Encoding:  {} \n" +
                        "Content type:  {} \n" +
                        "Status text:    {} \n" +
                        "Headers:    \n{}" +
                        "Response body:  \n {} \n" +
                        "----------------------------- \n",
                responseId, response.getStatus(), CHARACTER_ENCODING, response.getContentType(),
                response.getStatus(), responseHeaders.toString(), responseBody);
    }
}
