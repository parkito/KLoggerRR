package ru.siksmfp.kloggerrr.api;

import java.io.IOException;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
public interface RawHttpRequest extends BaseHttpRequest {

    HttpRequest withBody() throws IOException;

    default void withoutBody() throws IOException {

    }

}
