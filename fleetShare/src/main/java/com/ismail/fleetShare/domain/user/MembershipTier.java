package com.ismail.fleetShare.domain.user;

/**
 * Represents the membership tier of a user.
 * Each tier can have a discount multiplier applied to pricing.
 *
 * Possible values:
 * - STANDARD: no discount (multiplier 1.0)
 * - PREMIUM: 20% discount (multiplier 0.8)
 */
public enum MembershipTier {
    STANDARD(1.0),
    PREMIUM(0.8),
    VIP(0.0);
    private final double discountMultiplier;

    /**
     * Creates a membership tier with a specific discount multiplier.
     *
     * @param discountMultiplier the multiplier to apply for pricing (1.0 = no discount)
     */
    MembershipTier(double discountMultiplier) {
        this.discountMultiplier = discountMultiplier;
    }

    /**
     * Returns the discount multiplier for this membership tier.
     *
     * @return the discount multiplier as a double
     */
    public double getDiscountMultiplier() {
        return discountMultiplier;
    }
}
