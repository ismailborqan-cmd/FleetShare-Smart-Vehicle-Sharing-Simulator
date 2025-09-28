package com.ismail.fleetShare.domain.vehicle;

import com.ismail.fleetShare.domain.common.Money;

/**
 * Represents an electric bike (e-bike) vehicle in the system.
 * Extends the base {@link Vehicle} class.
 */
public class EBike extends Vehicle {
  private String BatteryLevel;
    /**
     * Creates a new EBike with the given ID, model, and price per minute.
     *
     * @param id the unique identifier of the e-bike
     * @param model the model name of the e-bike
     * @param pricePerMinute the rental price per minute as {@link Money}
     */
    public EBike(String id, String model, String BatteryLevel,Money pricePerMinute) {
        super(id, model, pricePerMinute);
        this.BatteryLevel=BatteryLevel;
    }
}
