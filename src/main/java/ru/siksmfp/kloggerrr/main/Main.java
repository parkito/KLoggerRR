package ru.siksmfp.kloggerrr.main;

import ru.siksmfp.kloggerrr.api.KLogger;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
public class Main {
    public static void main(String[] args) {
        KLogger kLogger = new KLogger.KLoggerBuilder()
                .addBodyFilter(null)
                .build();

        kLogger.makeRestTemplate();

    }
}
