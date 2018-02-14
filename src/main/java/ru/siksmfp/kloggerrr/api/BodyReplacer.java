package ru.siksmfp.kloggerrr.api;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
@FunctionalInterface
public interface BodyReplacer<T extends BaseHttpMessage> {

    String replace(final T message);

    @SafeVarargs
    static <T extends BaseHttpMessage> BodyReplacer<T> compound(final BodyReplacer<T>... replacers) {
        return message -> Arrays.stream(replacers)
                .map(replacer -> replacer.replace(message))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

}
