package io.sphere.client.filters;

import io.sphere.client.filters.Filter;
import io.sphere.client.filters.expressions.FilterExpression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FilterParser {
    /** Translates current query string into backend filter queries. */
    public static List<FilterExpression> parse(Map<String,String[]> queryString, Collection<Filter> filters) {
        List<FilterExpression> filterQueries = new ArrayList<FilterExpression>();
        for (Filter filter: filters) {
            filterQueries.add(filter.parse(queryString));
        }
        return filterQueries;
    }
}
