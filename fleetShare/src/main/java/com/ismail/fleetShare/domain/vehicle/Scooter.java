package com.ismail.fleetShare.domain.vehicle;

import com.ismail.fleetShare.domain.common.Money;

/**
 * Represents a scooter vehicle in the system.
 * Extends the base {@link Vehicle} class.
 */
public class Scooter extends Vehicle {
     private String weightLimit;
    /**
     * Creates a new Scooter with the given ID, model, and price per minute.
     *
     * @param id the unique identifier of the scooter
     * @param model the model name of the scooter
     * @param pricePerMinute the rental price per minute as {@link Money}
     */
    public Scooter(String id, String model,String weightLimit ,Money pricePerMinute) {
        super(id, model, pricePerMinute);
        this.weightLimit=weightLimit ;
    }
}
