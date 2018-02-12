package ru.siksmfp.kloggerrr.rest.template;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.siksmfp.kloggerrr.core.ResponseRequestLogger.DEFAULT_CHARACTER_ENCODING;
import static ru.siksmfp.kloggerrr.core.ResponseRequestLogger.EMPTY_BODY;
import static ru.siksmfp.kloggerrr.core.ResponseRequestLogger.calculateBodySize;
import static ru.siksmfp.kloggerrr.core.ResponseRequestLogger.formatAndLogRequest;
import static ru.siksmfp.kloggerrr.core.ResponseRequestLogger.formatAndLogResponse;
import static ru.siksmfp.kloggerrr.core.ResponseRequestLogger.getBodyAsString;

/**
 * @author Artem Karnov @date 12/26/2017.
 * artem.karnov@t-systems.com
 */
public class LoggingInterceptor implements ClientHttpRequestInterceptor {
    private static final int MAX_BODY_SIZE = Integer.MAX_VALUE - 8;
    private static final AtomicInteger REQUEST_COUNTER = new AtomicInteger();

    private int maxByteBodyToLog;

    public LoggingInterceptor(int maxByteBodyToLog) {
        this.maxByteBodyToLog = maxByteBodyToLog;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        int requestId = REQUEST_COUNTER.incrementAndGet();
        logRequest(request, body, requestId);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response, requestId);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] bodyContent, int requestId) {
        int loggedBodySize = calculateBodySize(MAX_BODY_SIZE, maxByteBodyToLog);
        String body = getBodyAsString(bodyContent, loggedBodySize);
        formatAndLogRequest(
                String.valueOf(requestId),
                request.getURI().toString(),
                request.getMethod().toString(),
                request.getHeaders().toString(),
                body);
    }

    private void logResponse(ClientHttpResponse response, int requestId) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), DEFAULT_CHARACTER_ENCODING));) {

            String line = bufferedReader.readLine();
            if (line != null && line.length() > maxByteBodyToLog) {
                line = line.substring(0, maxByteBodyToLog - 1);
            }

            for (int i = 0; i < maxByteBodyToLog && line != null; ) {
                i += line.length();
                inputStringBuilder.append(line);
                inputStringBuilder.append('\n');
                line = bufferedReader.readLine();
            }
        }

        String body = inputStringBuilder.length() > 0 ? inputStringBuilder.toString() : EMPTY_BODY;

        formatAndLogResponse(
                String.valueOf(requestId),
                String.valueOf(response.getStatusCode()),
                response.getHeaders().toString(),
                body);
    }
}