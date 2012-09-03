package de.commercetools.sphere.client.model;

import org.codehaus.jackson.annotate.JsonProperty;

/** Count of resources for an individual range of a range facet ({@link RangeFacetResult}). */
public class RangeCount {
    private double from;
    private double to;
    private int count;
    private int totalCount;
    @JsonProperty("total")
    private double sum;
    private double mean;

    // for JSON deserializer
    private RangeCount() {}

    /** The lower bound of this range. */
    public double getFrom() {
        return from;
    }

    /** The upper bound of this range. */
    public double getTo() {
        return to;
    }

    /** Count of resources that fall into this range. */
    public int getCount() {
        return count;
    }

    // TODO meaning?
    public int getTotalCount() {
        return totalCount;
    }

    /** Sum of values that fall into this range. */
    public double getSum() {
        return sum;
    }

    /** Arithmetic mean of values that fall into this range. */
    public double getMean() {
        return mean;
    }
}