package com.ismail.fleetShare.domain.price;

import com.ismail.fleetShare.domain.common.Distance;
import com.ismail.fleetShare.domain.common.Money;
import com.ismail.fleetShare.domain.trip.Trip;

/**
 * Pricing strategy based on trip distance.

 * The total price is calculated by multiplying
 * the distance traveled by the price per kilometer.
 */
public class DistanceBasedPricing implements PricingStrategy {

    private final Money pricePerKm;

    /**
     * Creates a distance-based pricing strategy.
     *
     * @param pricePerKm the unit price per kilometer
     */
    public DistanceBasedPricing(Money pricePerKm)
    {
        this.pricePerKm = pricePerKm;
    }

    /**
     * Calculates the trip price using distance.
     *
     * @param trip the trip to calculate price for
     * @return the total price as {@link Money}
     */
    @Override
    public Money calculatePrice(Trip trip) {
        Distance distance = trip.getDistance();

        if (distance == null) {
            return Money.zero(); // no distance means zero cost
        }

        return pricePerKm.multiply(distance.getValue());
    }
}
