package ru.siksmfp.kloggerrr.api;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
@FunctionalInterface
public interface QueryFilter {

    String filter(final String query);

    static QueryFilter none() {
        return query -> query;
    }

    static QueryFilter merge(final QueryFilter left, final QueryFilter right) {
        return query ->
                left.filter(right.filter(query));
    }

}
