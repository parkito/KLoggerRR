package ru.siksmfp.kloggerrr.api;

import java.io.IOException;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
public interface LogFormatter {

    String format(final Precorrelation<HttpRequest> precorrelation) throws IOException;

    String format(final Correlation<HttpRequest, HttpResponse> correlation) throws IOException;

}
