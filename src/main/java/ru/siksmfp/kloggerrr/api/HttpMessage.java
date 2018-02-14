package ru.siksmfp.kloggerrr.api;

import java.io.IOException;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
public interface HttpMessage extends BaseHttpMessage {

    byte[] getBody() throws IOException;

    default String getBodyAsString() throws IOException {
        return new String(getBody(), getCharset());
    }

}
