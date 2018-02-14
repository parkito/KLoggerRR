package ru.siksmfp.kloggerrr.api;

import java.time.Duration;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
public interface Correlation<Request, Response> {

    String getId();

    Duration getDuration();

    Request getRequest();

    Response getResponse();

    HttpRequest getOriginalRequest();

    HttpResponse getOriginalResponse();

}
