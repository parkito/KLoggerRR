package ru.siksmfp.kloggerrr.api;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
@FunctionalInterface
public interface RawRequestFilter {

    RawHttpRequest filter(final RawHttpRequest request);

    static RawRequestFilter none() {
        return request -> request;
    }

    static RawRequestFilter merge(final RawRequestFilter left, final RawRequestFilter right) {
        return request ->
                left.filter(right.filter(request));
    }

}
