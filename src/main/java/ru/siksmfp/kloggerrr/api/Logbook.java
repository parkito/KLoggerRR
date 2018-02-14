package ru.siksmfp.kloggerrr.api;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
public interface KLogger {

    Optional<Correlator> write(final RawHttpRequest request) throws IOException;

    static KLogger create() {
        return builder().build();
    }

    static KLogger.Builder builder() {
        return KLogger.builder();
    }

}
