package com.ismail.fleetShare.domain.vehicle;

/**
 * Represents the state of a vehicle in the system.
 * Possible values:
 * - AVAILABLE: the vehicle is ready for use
 * - IN_USE: the vehicle is currently being used
 * - MAINTENANCE: the vehicle is under maintenance
 * - RESERVED: the vehicle is reserved but not yet in use
 */
public enum VehicleState {
    AVAILABLE,
    IN_USE,
    MAINTENANCE,
    RESERVED
}
