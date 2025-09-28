package com.ismail.fleetShare.domain.price;

import com.ismail.fleetShare.domain.common.Money;
import com.ismail.fleetShare.domain.trip.Trip;

/**
 * Decorator that adds surge pricing to any pricing strategy.
 * Multiplies the base price by a surge multiplier.
 */
public class SurgeDecorator implements PricingStrategy {

    private final PricingStrategy wrapped;
    private final double surgeMultiplier;

    /**
     * Creates a SurgeDecorator with a base strategy and a surge multiplier.
     *
     * @param wrapped the base pricing strategy
     * @param surgeMultiplier the multiplier to increase the price
     */
    public SurgeDecorator(PricingStrategy wrapped, double surgeMultiplier) {
        this.wrapped = wrapped;
        this.surgeMultiplier = surgeMultiplier;
    }

    /**
     * Calculates the trip price after applying surge.
     *
     * @param trip the trip to calculate price for
     * @return the final price as a {@link Money} object
     */
    @Override
    public Money calculatePrice(Trip trip) {
        Money basePrice = wrapped.calculatePrice(trip);
        return basePrice.multiply(surgeMultiplier);
    }
}
