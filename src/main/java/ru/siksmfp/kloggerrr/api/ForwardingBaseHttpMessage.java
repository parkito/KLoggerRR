package ru.siksmfp.kloggerrr.api;

import com.sun.management.VMOption;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
public interface ForwardingBaseHttpMessage extends BaseHttpMessage {

    BaseHttpMessage delegate();

    @Override
    default String getProtocolVersion() {
        return delegate().getProtocolVersion();
    }

    @Override
    default VMOption.Origin getOrigin() {
        return delegate().getOrigin();
    }

    @Override
    default Map<String, List<String>> getHeaders() {
        return delegate().getHeaders();
    }

    @Override
    default String getContentType() {
        return delegate().getContentType();
    }

    @Override
    default Charset getCharset() {
        return delegate().getCharset();
    }

}
