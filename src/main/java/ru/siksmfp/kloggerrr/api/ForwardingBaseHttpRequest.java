package ru.siksmfp.kloggerrr.api;

import java.util.Optional;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
public interface ForwardingBaseHttpRequest extends ForwardingBaseHttpMessage, BaseHttpRequest {

    @Override
    BaseHttpRequest delegate();

    @Override
    default String getRemote() {
        return delegate().getRemote();
    }

    @Override
    default String getMethod() {
        return delegate().getMethod();
    }

    @Override
    default String getRequestUri() {
        return delegate().getRequestUri();
    }

    @Override
    default String getScheme() {
        return delegate().getScheme();
    }

    @Override
    default String getHost() {
        return delegate().getHost();
    }

    @Override
    default Optional<Integer> getPort() {
        return delegate().getPort();
    }

    @Override
    default String getPath() {
        return delegate().getPath();
    }

    @Override
    default String getQuery() {
        return delegate().getQuery();
    }

}
