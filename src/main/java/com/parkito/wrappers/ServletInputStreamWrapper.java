package com.parkito.wrappers;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Artem Karnov @date 10/29/2017.
 * artem.karnov@t-systems.com
 */
public class ServletInputStreamWrapper extends ServletInputStream {

    private TeeInputStream tee;

    public ServletInputStreamWrapper(ServletInputStream inputStream, ByteArrayOutputStream bos) {
        tee = new TeeInputStream(inputStream, bos);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
    }

    @Override
    public int read() throws IOException {
        return tee.read();
    }
}
