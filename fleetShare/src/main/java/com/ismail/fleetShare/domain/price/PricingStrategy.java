package com.ismail.fleetShare.domain.price;

import com.ismail.fleetShare.domain.common.Money;
import com.ismail.fleetShare.domain.trip.Trip;

/**
 * Pricing strategy interface.
 * Any pricing strategy (time-based, distance-based, hybrid, surge)
 * should implement this interface.
 */
public interface PricingStrategy {

    /**
     * Calculates the price for the given trip.
     *
     * @param trip the trip to calculate the price for
     * @return the total price as a {@link Money} object
     */
    Money calculatePrice(Trip trip);
}
