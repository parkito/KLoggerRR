package ru.siksmfp.kloggerrr.api;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
public interface ForwardingHttpRequest extends ForwardingHttpMessage, ForwardingBaseHttpRequest, HttpRequest {

    @Override
    HttpRequest delegate();

}
