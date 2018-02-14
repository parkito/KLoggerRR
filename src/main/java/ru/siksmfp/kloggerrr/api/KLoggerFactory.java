package ru.siksmfp.kloggerrr.api;

import java.util.function.Predicate;

import static java.util.ServiceLoader.load;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
interface KLoggerFactory {

    KLoggerFactory INSTANCE = load(KLoggerFactory.class).iterator().next();

    KLoggerFactory(final Predicate<RawHttpRequest> condition,
                   final RawRequestFilter rawRequestFilter,
                   final RawResponseFilter rawResponseFilter,
                   final QueryFilter queryFilter,
                   final HeaderFilter headerFilter,
                   final BodyFilter bodyFilter,
                   final RequestFilter requestFilter,
                   final ResponseFilter responseFilter,
                   final HttpLogFormatter formatter,
                   final HttpLogWriter writer);

}
