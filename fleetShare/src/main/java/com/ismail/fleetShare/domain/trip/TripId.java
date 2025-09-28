package com.ismail.fleetShare.domain.trip;

import java.util.UUID;

/**
 * Represents a unique identifier for a Trip.
 * Encapsulates a UUID string.
 */
public class TripId {

    private final String value;

    /**
     * Creates a new TripId with a randomly generated UUID.
     */
    public TripId() {
        this.value = UUID.randomUUID().toString();
    }

    /**
     * Returns the string value of the TripId.
     *
     * @return the UUID as a string
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TripId{" +
                "value='" + value + '\'' +
                '}';
    }
}
