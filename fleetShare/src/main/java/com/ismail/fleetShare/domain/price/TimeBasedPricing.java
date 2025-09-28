package com.ismail.fleetShare.domain.price;

import com.ismail.fleetShare.domain.common.Money;
import com.ismail.fleetShare.domain.trip.Trip;

import java.time.Duration;

/**
 * Time-based pricing strategy.
 * Calculates the price by multiplying the trip duration (in minutes) by the unit price per minute.
 */
public class TimeBasedPricing implements PricingStrategy {

    private final Money pricePerMinute;

    /**
     * Creates a time-based pricing strategy.
     *
     * @param pricePerMinute the price per minute
     */
    public TimeBasedPricing(Money pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }

    /**
     * Calculates the trip price based on the duration.
     *
     * @param trip the trip to calculate price for
     * @return the total price as a {@link Money} object
     */
    @Override
    public Money calculatePrice(Trip trip) {
        if (trip.getStartTime() == null || trip.getEndTime() == null) {
            return Money.zero(); // zero if start or end time is not set
        }

        long minutes = Duration.between(trip.getStartTime(), trip.getEndTime()).toMinutes();
        return pricePerMinute.multiply(minutes);
    }
}
