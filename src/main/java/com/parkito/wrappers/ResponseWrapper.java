package com.parkito.wrappers;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Artem Karnov @date 10/29/2017.
 * artem.karnov@t-systems.com
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
    private PrintWriter writer = new PrintWriter(bos);

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletResponse getResponse() {
        return this;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStreamWrapper(ResponseWrapper.super.getOutputStream(), bos);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new TeePrintWriterWrapper(super.getWriter(), writer);
    }

    public byte[] toByteArray() {
        return bos.toByteArray();
    }
}
