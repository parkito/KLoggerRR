package ru.siksmfp.kloggerrr.controller.wrapper;

import ru.siksmfp.kloggerrr.controller.stream.CacheStream;
import ru.siksmfp.kloggerrr.controller.stream.OutputSplitterStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Artem Karnov @date 11.04.2017.
 * artem.karnov@t-systems.com
 * <p>
 * Wrapper for caching response body.
 * <p>
 * In filter output we have OutputStream with outgoing data. Stream is binary data flow structure.
 * We can read it only once. But we need to 1)Log outgoing data
 *                                          2)Forward outgoing data farther to filter chain
 * So we have to cache data for multiple access.
 * This wrapper caches data from OutputStream and saves it in @CacheStream.
 * It allows us read data several times.
 * For logging we have byte representation of InputStream. For farther processing
 * we wrap cache with new IOStreams and throw it farther to next executor
 * In difference form other implementations this one has advantage in once stream data copying.
 */
public class HttpResponseWrapper extends HttpServletResponseWrapper {
    private final CacheStream cacheStream = new CacheStream();

    public HttpResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletResponse getResponse() {
        return this;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStreamWrapper(super.getOutputStream(), cacheStream);
    }

    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(cacheStream);
    }

    /**
     * Getting body content in bytes
     *
     * @return body's bytes
     */
    public byte[] toByteArray() {
        return cacheStream.getByteArray();
    }

    private class ServletOutputStreamWrapper extends ServletOutputStream {

        private OutputSplitterStream splitterStream;

        public ServletOutputStreamWrapper(ServletOutputStream servletOutputStream, OutputStream bos) {
            splitterStream = new OutputSplitterStream(servletOutputStream, bos);
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
            splitterStream.write(b);
        }
    }
}
