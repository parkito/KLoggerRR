package ru.siksmfp.kloggerrr.controller;

import org.springframework.web.filter.OncePerRequestFilter;
import ru.siksmfp.kloggerrr.controller.wrapper.HttpRequestWrapper;
import ru.siksmfp.kloggerrr.controller.wrapper.HttpResponseWrapper;
import ru.siksmfp.kloggerrr.core.ResponseRequestLogger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Artem Karnov @date 11.04.2017.
 * artem.karnov@t-systems.com
 */
public class LoggingFilter extends OncePerRequestFilter {
    private static final AtomicInteger REQUEST_COUNTER = new AtomicInteger();

    private int maxByteBodyToLog;

    public LoggingFilter(int maxByteBodyToLog) {
        super();
        this.maxByteBodyToLog = maxByteBodyToLog;
    }

    /**
     * Logging and farther throwing request/response to executing
     * <p>
     * We have to log request/response data and continue farther executing.
     * The main problem of this process we receive data of request/response in IOStreams.
     * We can read IOStreams only once. So if we log data we can't throw it farther.
     * For resolving this problem we use caching.
     * We read data request/response IOStreams, cache this data. Cached data we log and throw
     * it farther to next executor.
     * <p>
     * Caching is being happened under hood of <code>HttpRequestWrapper<code/> and <code>HttpResponseWrapper<code/>.
     * After processing wrappers contain cached byte representation of streams. This cache we simply log
     * and wrap it by new IOStreams for farther processing.
     *
     * @param request     request for processing
     * @param response    response for processing
     * @param filterChain filterChain for processing
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        int requestId = REQUEST_COUNTER.incrementAndGet();

        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
        HttpResponseWrapper responseWrapper = new HttpResponseWrapper(response);

        logRequest(requestWrapper, requestId);
        filterChain.doFilter(requestWrapper, responseWrapper);
        logResponse(responseWrapper, requestId);
    }

    private void logRequest(HttpRequestWrapper request, int requestId) {
        String requestHeaders = formatRequestHeaders(request);
        String address = formatAddress(request);
        String requestBody = ResponseRequestLogger.getBodyAsString(request.toByteArray(), maxByteBodyToLog);

        ResponseRequestLogger.formatAndLogRequest(
                String.valueOf(requestId),
                address,
                request.getMethod(),
                requestHeaders,
                requestBody);
    }

    private void logResponse(HttpResponseWrapper response, int responseId) {
        String headers = formatResponseHeaders(response);
        String responseBody = ResponseRequestLogger.getBodyAsString(response.toByteArray(), maxByteBodyToLog);

        ResponseRequestLogger.formatAndLogResponse(
                String.valueOf(responseId),
                String.valueOf(response.getStatus()),
                headers,
                responseBody);
    }

    public static String formatRequestHeaders(HttpServletRequest request) {
        StringBuilder requestHeaders = new StringBuilder();
        for (String header : Collections.list(request.getHeaderNames())) {
            requestHeaders
                    .append(header)
                    .append(": ")
                    .append(request.getHeader(header))
                    .append("\n");
        }
        return requestHeaders.toString();
    }

    public static String formatResponseHeaders(HttpServletResponse response) {
        StringBuilder responseHeaders = new StringBuilder();
        for (String header : response.getHeaderNames()) {
            responseHeaders
                    .append(header)
                    .append(": ")
                    .append(response.getHeader(header))
                    .append("\n");
        }
        return responseHeaders.toString();
    }

    public static String formatAddress(HttpServletRequest request) {
        StringBuilder fullUrl = new StringBuilder();
        fullUrl.append(request.getRequestURL().toString());
        if (request.getQueryString() != null) {
            fullUrl.append("?");
            fullUrl.append(request.getQueryString());
        }
        return fullUrl.toString();
    }
}