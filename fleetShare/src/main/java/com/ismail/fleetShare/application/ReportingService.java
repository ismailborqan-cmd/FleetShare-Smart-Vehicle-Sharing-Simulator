package com.ismail.fleetShare.application;

import com.ismail.fleetShare.domain.common.Money;
import com.ismail.fleetShare.domain.trip.Trip;

import java.util.List;

/**
 * Service responsible for generating reports related to trips,
 * such as completed trips and total revenue.
 */
public class ReportingService {
    private final List<Trip> trips;

    /**
     * Creates a new ReportingService with the given list of trips.
     *
     * @param trips the list of trips to be used for reporting
     */
    public ReportingService(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * Retrieves all trips that have been completed.
     *
     * @return a list of completed trips
     */
    public List<Trip> getCompletedTrips() {
        return trips.stream()
                .filter(t -> t.getState().name().equals("COMPLETED"))
                .toList();
    }

    /**
     * Calculates the total revenue earned from all completed trips.
     *
     * @return the total revenue as a {@link Money} object
     */
    public Money getTotalRevenue() {
        return trips.stream()
                .filter(t -> t.getState().name().equals("COMPLETED"))
                .map(Trip::getPrice) // assumes Trip has a getPrice() method
                .reduce(Money.of(0), Money::add);
    }
}
