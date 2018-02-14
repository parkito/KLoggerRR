package ru.siksmfp.kloggerrr.api;

import java.io.IOException;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
public interface ForwardingHttpMessage extends ForwardingBaseHttpMessage, HttpMessage {

    @Override
    HttpMessage delegate();

    @Override
    default byte[] getBody() throws IOException {
        return delegate().getBody();
    }

    @Override
    default String getBodyAsString() throws IOException {
        return delegate().getBodyAsString();
    }

}
