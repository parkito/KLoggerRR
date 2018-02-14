package ru.siksmfp.kloggerrr.api;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
public final class KLogger {

    private static Logbook create(
            final Predicate<RawHttpRequest> condition,
            final List<RawRequestFilter> rawRequestFilters,
            final List<RawResponseFilter> rawResponseFilters,
            final List<QueryFilter> queryFilters,
            final List<HeaderFilter> headerFilters,
            final List<BodyFilter> bodyFilters,
            final List<RequestFilter> requestFilters,
            final List<ResponseFilter> responseFilters,
            final HttpLogFormatter formatter,
            final HttpLogWriter writer) {

        final RawRequestFilter rawRequestFilter = rawRequestFilters.stream()
                .reduce(RawRequestFilter::merge)
                .orElse(null);

        final RawResponseFilter rawResponseFilter = rawResponseFilters.stream()
                .reduce(RawResponseFilter::merge)
                .orElse(null);

        final QueryFilter queryFilter = queryFilters.stream()
                .reduce(QueryFilter::merge)
                .orElse(null);

        final HeaderFilter headerFilter = headerFilters.stream()
                .reduce(HeaderFilter::merge)
                .orElse(null);

        final BodyFilter bodyFilter = bodyFilters.stream()
                .reduce(BodyFilter::merge)
                .orElse(null);

        final RequestFilter requestFilter = requestFilters.stream()
                .reduce(RequestFilter::merge)
                .orElse(null);

        final ResponseFilter responseFilter = responseFilters.stream()
                .reduce(ResponseFilter::merge)
                .orElse(null);

        final KLoggerFactory factory = KLoggerFactory.INSTANCE;

        return factory.create(
                condition,
                rawRequestFilter,
                rawResponseFilter,
                queryFilter,
                headerFilter,
                bodyFilter,
                requestFilter,
                responseFilter,
                formatter,
                writer);
    }
}
