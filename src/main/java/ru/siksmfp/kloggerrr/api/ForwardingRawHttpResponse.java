package ru.siksmfp.kloggerrr.api;
/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */

import java.io.IOException;

public interface ForwardingRawHttpResponse extends ForwardingBaseHttpResponse, RawHttpResponse {

    @Override
    RawHttpResponse delegate();

    @Override
    default HttpResponse withBody() throws IOException {
        return delegate().withBody();
    }

    @Override
    default void withoutBody() throws IOException {
        delegate().withoutBody();
    }

}
