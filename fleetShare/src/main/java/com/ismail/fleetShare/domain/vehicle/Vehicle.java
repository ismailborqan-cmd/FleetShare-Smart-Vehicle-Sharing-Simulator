package com.ismail.fleetShare.domain.vehicle;

import com.ismail.fleetShare.domain.common.Money;

/**
 * Represents a generic vehicle in the system.
 * Stores vehicle ID, model, state, and price per minute.
 * Can be extended by specific vehicle types like Car, EBike, or Scooter.
 */
public abstract class Vehicle {

    private final String id;
    private String model;
    private VehicleState state;
    private Money pricePerMinute;

    /**
     * Creates a new vehicle with the given ID, model, and price per minute.
     * The initial state is set to AVAILABLE.
     *
     * @param id the unique identifier of the vehicle
     * @param model the model name of the vehicle
     * @param pricePerMinute the rental price per minute as {@link Money}
     */
    public Vehicle(String id, String model, Money pricePerMinute) {
        this.id = id;
        this.model = model;
        this.state = VehicleState.AVAILABLE;
        this.pricePerMinute = pricePerMinute;
    }

    /**
     * Returns the unique ID of the vehicle.
     *
     * @return the vehicle ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the model name of the vehicle.
     *
     * @return the model name
     */
    public String getModel() {
        return model;
    }

    /**
     * Returns the current state of the vehicle.
     *
     * @return the vehicle state
     */
    public VehicleState getState() {
        return state;
    }

    /**
     * Sets the state of the vehicle.
     *
     * @param state the new vehicle state
     */
    public void setState(VehicleState state) {
        this.state = state;
    }

    /**
     * Returns the rental price per minute of the vehicle.
     *
     * @return the price per minute as {@link Money}
     */
    public Money getPricePerMinute() {
        return pricePerMinute;
    }

    /**
     * Sets the rental price per minute of the vehicle.
     *
     * @param pricePerMinute the new price per minute
     */
    public void setPricePerMinute(Money pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }
}
