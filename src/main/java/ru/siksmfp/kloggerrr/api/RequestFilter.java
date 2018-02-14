package ru.siksmfp.kloggerrr.api;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
@FunctionalInterface
public interface RequestFilter {

    HttpRequest filter(final HttpRequest request);

    static RequestFilter none() {
        return request -> request;
    }

    static RequestFilter merge(final RequestFilter left, final RequestFilter right) {
        return request ->
                left.filter(right.filter(request));
    }

}
