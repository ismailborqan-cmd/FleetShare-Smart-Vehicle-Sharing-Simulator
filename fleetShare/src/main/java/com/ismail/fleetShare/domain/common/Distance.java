package com.ismail.fleetShare.domain.common;

import java.util.Objects;

/**
 * Value object representing a distance in kilometers.
 * Immutable and provides basic operations such as addition.
 */
public class Distance {
    private final double value;

    /**
     * Creates a new Distance instance.
     *
     * @param value the distance value in kilometers
     */
    public Distance(double value) {
        this.value = value;
    }

    /**
     * Factory method to create a new Distance instance.
     *
     * @param value the distance value in kilometers
     * @return a new {@link Distance} object
     */
    public static Distance of(double value) {
        return new Distance(value);
    }

    /**
     * Gets the distance value in kilometers.
     *
     * @return the distance value
     */
    public double getValue() {
        return value;
    }

    /**
     * Adds this distance to another distance.
     *
     * @param other the other distance
     * @return a new {@link Distance} representing the sum
     */
    public Distance add(Distance other) {
        return new Distance(this.value + other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Distance distance = (Distance) o;
        return Double.compare(getValue(), distance.getValue()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

    @Override
    public String toString() {
        return "{Km=" + value + '}';
    }
}
