package ru.siksmfp.kloggerrr.api;

import java.util.List;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
public interface LogWriter {

    void write(LogWriter logWriter);

    void writeAll(List<LogWriter> logWriters);

}
