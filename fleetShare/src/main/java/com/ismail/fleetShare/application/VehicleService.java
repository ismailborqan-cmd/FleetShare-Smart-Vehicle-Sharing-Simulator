package com.ismail.fleetShare.application;

import com.ismail.fleetShare.domain.vehicle.Vehicle;
import com.ismail.fleetShare.infrastructure.InMemoryVehicleRepository;

import java.util.List;

/**
 * Service responsible for managing vehicles in the system.
 * Uses {@link InMemoryVehicleRepository} to store and retrieve vehicles.
 */
public class VehicleService {

    private final InMemoryVehicleRepository repository;

    /**
     * Creates a new VehicleService with the given repository.
     *
     * @param repository the repository used to store and retrieve vehicles
     */
    public VehicleService(InMemoryVehicleRepository repository) {
        this.repository = repository;
    }

    /**
     * Adds a new vehicle to the system.
     *
     * @param vehicle the vehicle to add
     */
    public void addVehicle(Vehicle vehicle) {
        repository.save(vehicle);
    }

    /**
     * Retrieves all available vehicles.
     *
     * @return a list of available vehicles
     */
    public List<Vehicle> getAvailableVehicles() {
        return repository.findAvailable();
    }
}
