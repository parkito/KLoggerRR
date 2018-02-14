package ru.siksmfp.kloggerrr.api;

import java.io.IOException;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
public interface RawHttpResponse extends BaseHttpResponse {

    HttpResponse withBody() throws IOException;

    default void withoutBody() throws IOException {

    }

}
