package com.parkito.wrappers;


import com.parkito.proxy.ProxyOutputStream;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Artem Karnov @date 10/30/2017.
 * artem.karnov@t-systems.com
 * <p>
 * Classic splitter of OutputStream. Named after the unix 'tee'
 * command. It allows a stream to be branched off so there
 * are now two streams.
 * @version $Id: TeeOutputStream.java 1686503 2015-06-19 21:32:13Z sebb $
 */
public class TeeOutputStream extends ProxyOutputStream {

    /**
     * the second OutputStream to write to
     */
    private OutputStream branch;

    /**
     * Constructs a TeeOutputStream.
     *
     * @param out    the main OutputStream
     * @param branch the second OutputStream
     */
    public TeeOutputStream(final OutputStream out, final OutputStream branch) {
        super(out);
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
        super.write(b);
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

