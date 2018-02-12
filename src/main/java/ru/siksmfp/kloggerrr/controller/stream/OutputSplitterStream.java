package ru.siksmfp.kloggerrr.controller.stream;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Artem Karnov @date 1/22/2018.
 * @email artem.karnov@t-systems.com
 * <p>
 * Classic splitter of OutputStream.
 * The main reason of this class is we get data from output stream
 * and write it to two streams simultaneously (<code>branch</code> and <code>original</code>).
 * After that one stream (<code>branch</code>) we read for caching second
 * (<code>original</code>) we throw farther to response executor.
 */
public class OutputSplitterStream extends OutputStream {
    private OutputStream branch;
    private OutputStream original;

    /**
     * Constructs a OutputSplitterStream.
     *
     * @param original stream to farther executing
     * @param branch   stream for logging
     */
    public OutputSplitterStream(final OutputStream original, final OutputStream branch) {
        this.original = original;
        this.branch = branch;
    }

    /**
     * Write the bytes to both streams.
     *
     * @param b the bytes to write
     * @throws IOException if an I/O error occurs
     */
    @Override
    public synchronized void write(final byte[] b) throws IOException {
        super.write(b);
        this.branch.write(b);
    }

    /**
     * Write the specified bytes to both streams.
     *
     * @param b   the bytes to write
     * @param off The start offset
     * @param len The number of bytes to write
     * @throws IOException if an I/O error occurs
     */
    @Override
    public synchronized void write(final byte[] b, final int off, final int len) throws IOException {
        super.write(b, off, len);
        this.branch.write(b, off, len);
    }

    /**
     * Write a byte to both streams.
     *
     * @param b the byte to write
     * @throws IOException if an I/O error occurs
     */
    @Override
    public synchronized void write(final int b) throws IOException {
        this.original.write(b);
        this.branch.write(b);
    }

    /**
     * Flushes both streams.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void flush() throws IOException {
        super.flush();
        this.branch.flush();
    }

    /**
     * Closes both output streams.
     * <p>
     * If closing the main output stream throws an exception, attempt to close the branch output stream.
     * <p>
     * If closing the main and branch output streams both throw exceptions, which exceptions is thrown by this method is
     * currently unspecified and subject to change.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        try {
            super.close();
        } finally {
            this.branch.close();
        }
    }
}

