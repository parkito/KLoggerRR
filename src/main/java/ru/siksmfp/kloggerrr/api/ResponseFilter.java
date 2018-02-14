package ru.siksmfp.kloggerrr.api;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
@FunctionalInterface
public interface ResponseFilter {

    HttpResponse filter(final HttpResponse response);

    static ResponseFilter none() {
        return response -> response;
    }

    static ResponseFilter merge(final ResponseFilter left, final ResponseFilter right) {
        return response ->
                left.filter(right.filter(response));
    }
}
