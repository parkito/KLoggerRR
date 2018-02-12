package ru.siksmfp.kloggerrr.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * @author Artem Karnov @date 1/24/2018.
 * @email artem.karnov@t-systems.com
 */
public class ResponseRequestLogger {
    private static final Logger LOG = LoggerFactory.getLogger(ResponseRequestLogger.class);

    public static final String EMPTY_BODY = "[NONE]";
    public static final String DEFAULT_CHARACTER_ENCODING = "UTF-8";

    public static String getBodyAsString(byte[] body, int maxByteBodyToLog) {
        try {
            if (body.length > 0) {
                int end = calculateBodySize(body.length, maxByteBodyToLog);
                return new String(body, 0, end, DEFAULT_CHARACTER_ENCODING);
            } else {
                return EMPTY_BODY;
            }
        } catch (UnsupportedEncodingException ex) {
            LOG.error("Failed to parse request body", ex);
            return EMPTY_BODY;
        }
    }

    /**
     * If in microservice log level is DEBUG we log entire request body (@bodyLength bytes)
     * <p>
     * If in microservice log level is NOT DEBUG we log only first @maxByteBodyToLog bytes
     * <p>
     * * @maxByteBodyToLog is being set in REGISTRY
     * * default value of @maxByteBodyToLog is 1024
     *
     * @param bodyLength       length of body
     * @param maxByteBodyToLog max length value
     * @return body size for logging
     */
    public static int calculateBodySize(int bodyLength, int maxByteBodyToLog) {
        return LOG.isDebugEnabled() ? bodyLength : (bodyLength > maxByteBodyToLog ? maxByteBodyToLog : bodyLength);
    }

    public static void formatAndLogRequest(String id, String address, String httpMethod, String headers, String body) {
        LOG.info("Request Message \n" +
                        "---------------------------- \n" +
                        "RequestID:     {} \n" +
                        "Address:       {} \n" +
                        "Http method:   {} \n" +
                        "Headers:     \n{} \n" +
                        "Request body:  \n {} \n" +
                        "----------------------------- \n",
                id, address, httpMethod, headers, body);
    }

    public static void formatAndLogResponse(String id, String code, String headers, String body) {
        LOG.info("Response Message \n" +
                        "---------------------------- \n" +
                        "ResponseID  :   {} \n" +
                        "Response-Code:  {} \n" +
                        "Headers:      \n{} \n" +
                        "Response body: \n {} \n" +
                        "----------------------------- \n",
                id, code, headers, body);
    }
}
