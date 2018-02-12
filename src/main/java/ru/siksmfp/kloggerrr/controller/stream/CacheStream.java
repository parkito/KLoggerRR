package ru.siksmfp.kloggerrr.controller.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * @author Artem Karnov @date 1/17/2018.
 * @email artem.karnov@t-systems.com
 * <p>
 * Custom implementation of @ByteArrayOutputStream
 * for cutting initial buffer length through constructor
 * and direct access to buffer without unnecessary copying.
 */
public class CacheStream extends OutputStream {
    /**
     * The buffer where data is stored.
     */
    private byte buf[];

    /**
     * The number of valid bytes in the buffer.
     */
    private int count;

    /**
     * Constructor which sets to buffer zero length
     * <p>
     * We need to create stream with zero-length buffer for
     * correct byte to String converting.
     */
    public CacheStream() {
        buf = new byte[0];
    }

    /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     *
     * @param newCapacity the desired newCapacity capacity
     */
    private void grow(int newCapacity) {
        //We can't store more than MAX_ARRAY_SIZE elements in array
        if (newCapacity > MAX_ARRAY_SIZE) {
            throw new ArrayStoreException("Size can't be greater than MAX_ARRAY_SIZE");
        }
        buf = Arrays.copyOf(buf, newCapacity);
    }

    /**
     * Writes the specified byte to this byte array output stream.
     *
     * @param b the byte to be written.
     */
    public synchronized void write(int b) {
        grow(count + 1);
        buf[count] = (byte) b;
        count += 1;
    }

    /**
     * Writes <code>len</code> bytes from the specified byte array
     * starting at offset <code>off</code> to this byte array output stream.
     *
     * @param b   the data.
     * @param len the number of bytes to write.
     */
    public synchronized void write(byte b[], int len) {
        if ((len < 0) || (len > b.length)) {
            throw new IndexOutOfBoundsException();
        }
        grow(count + len);
        System.arraycopy(b, 0, buf, count, len);
        count += len;
    }

    /**
     * Converts the buffer's contents into a string decoding bytes using the
     * platform's default character set. The length of the new <tt>String</tt>
     * is a function of the character set, and hence may not be equal to the
     * size of the buffer.
     * <p>
     * <p> This method always replaces malformed-input and unmappable-character
     * sequences with the default replacement string for the platform's
     * default character set. The {@linkplain java.nio.charset.CharsetDecoder}
     * class should be used when more control over the decoding process is
     * required.
     *
     * @return String decoded from the buffer's contents.
     */
    public String toString() {
        return new String(buf, 0, count);
    }

    /**
     * Getting  byte content of @ByteArrayOutputStream
     * <p>
     * toByteArray() of ByteArrayOutputStream uses
     *
     * @return byte content of @ByteArrayOutputStream
     */
    public byte[] getByteArray() {
        return buf;
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
            buf = null;
            count = 0;
        }
    }
}
