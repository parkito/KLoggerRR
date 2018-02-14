package ru.siksmfp.kloggerrr.api;

import java.util.Optional;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
public interface BaseHttpRequest extends BaseHttpMessage {

    String getRemote();

    String getMethod();

    /**
     * Absolute Request URI including scheme, host, port (unless http/80 or https/443), path and query string.
     * <p>
     * <p>Note that the URI may be invalid if the client issued an HTTP request using a malformed URL.</p>
     *
     * @return the requested URI
     */
    default String getRequestUri() {
        return RequestURI.reconstruct(this);
    }

    String getScheme();

    String getHost();

    Optional<Integer> getPort();

    String getPath();

    String getQuery();

}
