package ru.siksmfp.kloggerrr.controller.wrapper;

import ru.siksmfp.kloggerrr.controller.stream.CacheStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Artem Karnov @date 1/16/2018.
 * @email artem.karnov@t-systems.com
 * <p>
 * Wrapper for caching request body.
 * <p>
 * In filter input we have InputStream with incoming data. Stream is binary data flow structure.
 * We can read it only once. But we need to 1)Log input data
 *                                          2)Forward input data farther to filter chain
 * So we have to cache data for multiple access.
 * This wrapper caches data from InputStream and saves it in @CacheStream.
 * It allows us read data several times.
 * For logging we have byte representation of InputStream. For farther processing
 * we wrap cache with new IOStreams and throw it farther to next executor
 * In difference form other implementations this one has advantage in once stream data copying.
 */
public class HttpRequestWrapper extends HttpServletRequestWrapper {
    private static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 4096;

    private CacheStream cachedBytes;

    /**
     * Initialization of HttpServletRequestWrapper and data caching
     *
     * @param request request for initialization
     * @throws IOException
     */
    public HttpRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        cachedBytes = new CacheStream();
        copyStream(super.getInputStream(), cachedBytes);
    }

    @Override
    public ServletInputStream getInputStream() {
        return new ServletInputStreamWrapper(cachedBytes.getByteArray());
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * Copies bytes from InputStream to an OutputStream
     *
     * @param input  the InputStream to read from
     * @param output the OutputStream to write to
     * @throws NullPointerException if the input or output is null
     * @throws IOException          if an I/O error occurs
     */
    private void copyStream(final InputStream input, final CacheStream output) throws IOException {
        final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, n);
        }
    }

    /**
     * Getting body content in bytes
     *
     * @return body's bytes
     */
    public byte[] toByteArray() {
        return cachedBytes.getByteArray();
    }

    private class ServletInputStreamWrapper extends ServletInputStream {
        private ByteArrayInputStream input;

        ServletInputStreamWrapper(byte[] requestBody) {
            input = new ByteArrayInputStream(requestBody);
        }

        @Override
        public int read() {
            return input.read();
        }

        @Override
        public boolean isFinished() {
            return input.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }
}
