package com.parkito.wrappers;

import java.io.PrintWriter;

/**
 * @author Artem Karnov @date 10/29/2017.
 * artem.karnov@t-systems.com
 */
public class TeePrintWriterWrapper extends PrintWriter {

    private PrintWriter branch;

    public TeePrintWriterWrapper(PrintWriter main, PrintWriter branch) {
        super(main, true);
        this.branch = branch;
    }

    @Override
    public void write(char buf[], int off, int len) {
        super.write(buf, off, len);
        super.flush();
        branch.write(buf, off, len);
        branch.flush();
    }

    @Override
    public void write(String s, int off, int len) {
        super.write(s, off, len);
        super.flush();
        branch.write(s, off, len);
        branch.flush();
    }

    @Override
    public void write(int c) {
        super.write(c);
        super.flush();
        branch.write(c);
        branch.flush();
    }

    @Override
    public void flush() {
        super.flush();
        branch.flush();
    }
}
