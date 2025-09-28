package com.ismail.fleetShare.application;

import com.ismail.fleetShare.domain.common.Money;
import com.ismail.fleetShare.domain.price.PricingStrategy;
import com.ismail.fleetShare.domain.trip.Trip;
import com.ismail.fleetShare.domain.trip.TripId;
import com.ismail.fleetShare.domain.trip.TripState;
import com.ismail.fleetShare.domain.user.User;
import com.ismail.fleetShare.domain.vehicle.Vehicle;
import com.ismail.fleetShare.domain.vehicle.VehicleState;
import com.ismail.fleetShare.infrastructure.InMemoryUserRepository;
import com.ismail.fleetShare.infrastructure.InMemoryVehicleRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Service responsible for managing trips:
 * - starting trips
 * - ending trips (and calculating price)
 * - retrieving active trips
 */
public class TripService {

    private final InMemoryVehicleRepository vehicleRepo;
    private final InMemoryUserRepository userRepo;
    private final PricingStrategy pricingStrategy;
    private final Map<String, Trip> activeTrips = new HashMap<>();

    /**
     * Creates a new TripService with repositories and a pricing strategy.
     *
     * @param vehicleRepo      repository for vehicles
     * @param userRepo         repository for users
     * @param pricingStrategy  strategy to calculate trip price
     */
    public TripService(InMemoryVehicleRepository vehicleRepo,
                       InMemoryUserRepository userRepo,
                       PricingStrategy pricingStrategy) {
        this.vehicleRepo = vehicleRepo;
        this.userRepo = userRepo;
        this.pricingStrategy = pricingStrategy;
    }

    /**
     * Starts a new trip if the vehicle is available.
     *
     * @param user    the user who starts the trip
     * @param vehicle the vehicle to be used
     * @return the created {@link Trip}
     * @throws IllegalStateException if the vehicle is not available
     */
    public Trip startTrip(User user, Vehicle vehicle) {
        if (vehicle.getState() != VehicleState.AVAILABLE) {
            throw new IllegalStateException("Vehicle is not available!");
        }

        Trip trip = new Trip(new TripId(), user, vehicle);
        trip.setStartTime(LocalDateTime.now());
        activeTrips.put(trip.getId().getValue(), trip);

        return trip;
    }

    /**
     * Ends an active trip and calculates the price using the pricing strategy.
     *
     * @param tripId the id of the trip
     * @return the calculated price as {@link Money}
     * @throws IllegalArgumentException if the trip does not exist
     * @throws IllegalStateException if the trip is not in progress
     */
    public Money endTrip(String tripId) {
        Trip trip = activeTrips.get(tripId);
        if (trip == null) {
            throw new IllegalArgumentException("Trip not found: " + tripId);
        }

        if (trip.getState() != TripState.IN_PROGRESS) {
            throw new IllegalStateException("Trip is not in progress!");
        }

        trip.setEndTime(LocalDateTime.now());
        Money price = pricingStrategy.calculatePrice(trip);

        // remove from active trips after ending
        activeTrips.remove(tripId);

        return price;
    }

    /**
     * Retrieves an active trip by its id.
     *
     * @param tripId the id of the trip
     * @return the {@link Trip}, or null if not found
     */
    public Trip getTrip(String tripId) {
        return activeTrips.get(tripId);
    }
}
