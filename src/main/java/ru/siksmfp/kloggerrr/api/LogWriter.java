package ru.siksmfp.kloggerrr.api;

import java.io.IOException;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
public interface LogWriter {

    default boolean isActive(final RawHttpRequest request) throws IOException {
        return true;
    }

    void writeRequest(final Precorrelation<String> precorrelation) throws IOException;

    void writeResponse(final Correlation<String, String> correlation) throws IOException;

}
