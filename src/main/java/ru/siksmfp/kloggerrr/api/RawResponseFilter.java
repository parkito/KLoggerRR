package ru.siksmfp.kloggerrr.api;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
@FunctionalInterface
public interface RawResponseFilter {

    RawHttpResponse filter(final RawHttpResponse response);

    static RawResponseFilter none() {
        return response -> response;
    }

    static RawResponseFilter merge(final RawResponseFilter left, final RawResponseFilter right) {
        return response ->
                left.filter(right.filter(response));
    }

}
