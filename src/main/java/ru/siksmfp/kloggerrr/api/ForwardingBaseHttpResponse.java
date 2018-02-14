package ru.siksmfp.kloggerrr.api;
/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
public interface ForwardingBaseHttpResponse extends ForwardingBaseHttpMessage, BaseHttpResponse {

    @Override
    BaseHttpResponse delegate();

    @Override
    default int getStatus() {
        return delegate().getStatus();
    }

}
