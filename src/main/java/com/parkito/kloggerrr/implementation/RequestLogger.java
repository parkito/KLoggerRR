package com.parkito.kloggerrr.implementation;

import com.parkito.kloggerrr.wrappers.RequestWrapper;
import com.parkito.kloggerrr.wrappers.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Artem Karnov @date 10/31/2017.
 * artem.karnov@t-systems.com
 */
public class RequestLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLogger.class);

    private static final String EMPTY_RESPONSE_BODY = "[NONE]";
    private static final String DEFAULT_CHARACTER_ENCODING = "UTF-8";
    private static AtomicLong atomicLong = new AtomicLong();

    public void doLogging(HttpServletRequest request, HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        long requestId = atomicLong.incrementAndGet();
        request = new RequestWrapper(request);
        response = new ResponseWrapper(response);
        try {
            filterChain.doFilter(request, response);
        } finally {
            logRequest(request, requestId);
            logResponse(response, requestId);
        }
    }

    private void logRequest(HttpServletRequest httpServletRequest, long requestId) {
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
        String charEncoding = request.getCharacterEncoding() != null ? request.getCharacterEncoding() : DEFAULT_CHARACTER_ENCODING;
        String requestBody = EMPTY_RESPONSE_BODY;

        try {
            requestBody = (new String(request.toByteArray(), charEncoding));
        } catch (UnsupportedEncodingException e) {
            LOGGER.warn("Failed to parse request body", e);
        }

        LOGGER.info("Request Message \n" +
                        "---------------------------- \n" +
                        "RequestID:    {} \n" +
                        "Address:   {} \n" +
                        "Encoding:  {} \n" +
                        "Content type:  {} \n" +
                        "Http method:    {} \n" +
                        "Headers:    \n{}" +
                        "Request body:  \n {} \n" +
                        "----------------------------- \n",
                requestId, fullUrl, charEncoding, request.getContentType(),
                request.getMethod(), requestHeaders.toString(), requestBody);
    }

    private void logResponse(HttpServletResponse httpServletResponse, long responseId) {
        ResponseWrapper response = (ResponseWrapper) httpServletResponse;
        StringBuilder responseHeaders = new StringBuilder();
        for (String header : response.getHeaderNames()) {
            responseHeaders
                    .append(header)
                    .append(": ")
                    .append(response.getHeader(header))
                    .append("\n");
        }
        String charEncoding = response.getCharacterEncoding() != null ? response.getCharacterEncoding() : DEFAULT_CHARACTER_ENCODING;
        String responseBody = EMPTY_RESPONSE_BODY;

        try {
            responseBody = new String(response.toByteArray(), response.getCharacterEncoding());
        } catch (UnsupportedEncodingException e) {
            LOGGER.warn("Failed to parse response body", e);
        }
        LOGGER.info("Response Message \n" +
                        "---------------------------- \n" +
                        "ResponseID:    {} \n" +
                        "Response-Code:   {} \n" +
                        "Encoding:  {} \n" +
                        "Content type:  {} \n" +
                        "Status text:    {} \n" +
                        "Headers:    \n{}" +
                        "Response body:  \n {} \n" +
                        "----------------------------- \n",
                responseId, response.getStatus(), charEncoding, response.getContentType(),
                response.getStatus(), responseHeaders.toString(), responseBody);
    }
}
