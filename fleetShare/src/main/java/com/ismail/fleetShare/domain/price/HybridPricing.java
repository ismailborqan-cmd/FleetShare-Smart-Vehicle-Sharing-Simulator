package com.ismail.fleetShare.domain.price;

import com.ismail.fleetShare.domain.common.Money;
import com.ismail.fleetShare.domain.trip.Trip;

/**
 * Hybrid pricing strategy combining time-based and distance-based pricing.
 *
 * The final price is calculated by applying both the time strategy
 * and the distance strategy, then summing the results.
 */
public class HybridPricing implements PricingStrategy {

    private final PricingStrategy timeStrategy;
    private final PricingStrategy distanceStrategy;

    /**
     * Creates a hybrid pricing strategy using the provided time and distance strategies.
     *
     * @param timeStrategy     the strategy based on trip duration
     * @param distanceStrategy the strategy based on trip distance
     */
    public HybridPricing(PricingStrategy timeStrategy, PricingStrategy distanceStrategy) {
        this.timeStrategy = timeStrategy;
        this.distanceStrategy = distanceStrategy;
    }

    /**
     * Calculates the trip price by combining time-based and distance-based pricing.
     *
     * @param trip the trip to calculate price for
     * @return the total price as {@link Money}
     */
    @Override
    public Money calculatePrice(Trip trip) {
        Money timePrice = timeStrategy.calculatePrice(trip);
        Money distancePrice = distanceStrategy.calculatePrice(trip);

        return timePrice.add(distancePrice);
    }
}
