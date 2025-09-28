package com.ismail.fleetShare.infrastructure;

import com.ismail.fleetShare.domain.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory repository for storing and managing vehicles.
 * Uses a HashMap internally to store vehicles by their unique ID.
 */
public class InMemoryVehicleRepository {

    private final Map<String, Vehicle> vehicles = new HashMap<>();

    /**
     * Saves a vehicle to the repository.
     * If a vehicle with the same ID exists, it will be replaced.
     *
     * @param vehicle the vehicle to save
     */
    public void save(Vehicle vehicle) {
        vehicles.put(vehicle.getId(), vehicle);
    }

    /**
     * Finds a vehicle by its unique ID.
     *
     * @param id the ID of the vehicle to find
     * @return the vehicle with the given ID, or null if not found
     */
    public Vehicle findById(String id) {
        return vehicles.get(id);
    }

    /**
     * Returns all vehicles stored in the repository.
     *
     * @return a list of all vehicles
     */
    public List<Vehicle> findAll() {
        return new ArrayList<>(vehicles.values());
    }

    /**
     * Returns all vehicles that are currently available.
     *
     * @return a list of available vehicles
     */
    public List<Vehicle> findAvailable() {
        List<Vehicle> available = new ArrayList<>();
        for (Vehicle v : vehicles.values()) {
            if (v.getState() == com.ismail.fleetShare.domain.vehicle.VehicleState.AVAILABLE) {
                available.add(v);
            }
        }
        return available;
    }
}
