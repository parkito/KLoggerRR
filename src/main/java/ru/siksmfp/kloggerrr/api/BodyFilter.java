package ru.siksmfp.kloggerrr.api;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
@FunctionalInterface
public interface BodyFilter {

    String filter(final String contentType, final String body);

    static BodyFilter none() {
        return (contentType, body) -> body;
    }

    static BodyFilter merge(final BodyFilter left, final BodyFilter right) {
        return (contentType, body) ->
                left.filter(contentType, right.filter(contentType, body));
    }
}
