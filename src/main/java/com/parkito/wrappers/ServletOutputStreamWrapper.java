package com.parkito.wrappers;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Artem Karnov @date 10/29/2017.
 * artem.karnov@t-systems.com
 */
public class ServletOutputStreamWrapper extends ServletOutputStream {

    private TeeOutputStream tee;

    public ServletOutputStreamWrapper(ServletOutputStream servletOutputStream, ByteArrayOutputStream bos) {
        tee = new TeeOutputStream(servletOutputStream, bos);
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
    }

    @Override
    public void write(int b) throws IOException {
        tee.write(b);
    }
}
