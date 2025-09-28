package com.ismail.fleetShare.domain.common;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Value object representing a monetary amount.
 * Uses {@link BigDecimal} for precision and supports basic arithmetic
 * operations such as addition, subtraction, and multiplication.

 * Default currency is USD if not specified.
 */
public class Money {
    private final BigDecimal amount;
    private final String currency;

    public Money(BigDecimal amount) {
        this(amount, "USD");
    }

    public Money(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money of(double value) {
        return new Money(BigDecimal.valueOf(value));
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO, "USD");
    }

    /**
     * Adds this money to another, ensuring both have the same currency.
     *
     * @param other the money to add
     * @return a new {@link Money} representing the sum
     * @throws IllegalArgumentException if the currencies do not match
     */
    public Money add(Money other) {
        checkCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    /**
     * Subtracts another money from this one, ensuring both have the same currency.
     *
     * @param other the money to subtract
     * @return a new {@link Money} representing the difference
     * @throws IllegalArgumentException if the currencies do not match
     */
    public Money subtract(Money other) {
        checkCurrency(other);
        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    public Money multiply(double factor) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(factor)), this.currency);
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    private void checkCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException(
                    "Currency mismatch: " + this.currency + " vs " + other.currency
            );
        }
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return amount.equals(money.amount) && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
}
