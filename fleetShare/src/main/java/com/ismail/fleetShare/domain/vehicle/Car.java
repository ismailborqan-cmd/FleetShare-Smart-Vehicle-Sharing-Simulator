package com.ismail.fleetShare.domain.vehicle;

import com.ismail.fleetShare.domain.common.Money;

/**
 * Represents a car vehicle in the system.
 * Extends the base {@link Vehicle} class.
 */
public class Car extends Vehicle  {
 private String fuelType;
    /**
     * Creates a new Car with the given ID, model, and price per minute.
     *
     * @param id the unique identifier of the car
     * @param model the model name of the car
     * @param pricePerMinute the rental price per minute as {@link Money}
     */
    public Car(String id, String model,String fuelType, Money pricePerMinute) {
        super(id, model, pricePerMinute);
        this.fuelType=fuelType;
    }
}
