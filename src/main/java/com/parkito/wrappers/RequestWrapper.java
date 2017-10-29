package com.parkito.wrappers;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Artem Karnov @date 10/29/2017.
 * artem.karnov@t-systems.com
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();

    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStreamWrapper(RequestWrapper.super.getInputStream(), bos);
    }

    public byte[] toByteArray() {
        return bos.toByteArray();
    }

}
