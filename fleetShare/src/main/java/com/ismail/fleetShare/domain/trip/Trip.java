package com.ismail.fleetShare.domain.trip;

import com.ismail.fleetShare.domain.common.Distance;
import com.ismail.fleetShare.domain.common.Money;
import com.ismail.fleetShare.domain.user.User;
import com.ismail.fleetShare.domain.vehicle.Vehicle;
import com.ismail.fleetShare.domain.vehicle.VehicleState;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Represents a trip taken by a user with a specific vehicle.
 * Stores trip details such as start and end times, distance, price, and state.
 */
public class Trip {

    private final TripId id;
    private final Vehicle vehicle;
    private TripState state;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Distance distance;
    private Money price;

    /**
     * Creates a new trip for a user and vehicle.
     * Initial state is CREATED, distance and price start at zero.
     *
     * @param id      the unique identifier of the trip
     * @param user    the user taking the trip
     * @param vehicle the vehicle used for the trip
     */
    public Trip(TripId id, User user, Vehicle vehicle) {
        this.id = id;
        this.vehicle = vehicle;
        this.state = TripState.CREATED;
        this.distance = Distance.of(0);
        this.price = Money.of(0);
    }

    /**
     * Gets the duration of the trip.
     *
     * @return the duration as {@link Duration}, or zero if not started/completed
     */
    public Duration getDuration() {
        if (startTime != null && endTime != null) {
            return Duration.between(startTime, endTime);
        }
        return Duration.ZERO;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public TripId getId() {
        return id;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    /**
     * Starts the trip and sets the vehicle state to IN_USE.
     *
     * @param startTime optional start time; if null, uses current time
     */
    public void setStartTime(LocalDateTime startTime) {
        if (state == TripState.CREATED) {
            this.startTime = startTime != null ? startTime : LocalDateTime.now();
            this.state = TripState.IN_PROGRESS;
            vehicle.setState(VehicleState.IN_USE);
        }
    }

    /**
     * Ends the trip and sets the vehicle state to AVAILABLE.
     *
     * @param endTime optional end time; if null, uses current time
     */
    public void setEndTime(LocalDateTime endTime) {
        if (state == TripState.IN_PROGRESS) {
            this.endTime = endTime != null ? endTime : LocalDateTime.now();
            this.state = TripState.COMPLETED;
            vehicle.setState(VehicleState.AVAILABLE);
        }
    }

    public TripState getState() {
        return state;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Cancels the trip and makes the vehicle available.
     */
    public void cancel() {
        this.state = TripState.CANCELED;
        this.endTime = LocalDateTime.now();
        vehicle.setState(VehicleState.AVAILABLE);
    }
}
