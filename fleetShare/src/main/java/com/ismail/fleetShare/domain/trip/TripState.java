package com.ismail.fleetShare.domain.trip;

/**
 * Represents the state of a trip.
 * Possible values:
 * - CREATED: the trip has been created but not started
 * - IN_PROGRESS: the trip is currently ongoing
 * - COMPLETED: the trip has finished
 * - CANCELED: the trip was canceled
 */
public enum TripState {
    CREATED,
    IN_PROGRESS,
    COMPLETED,
    CANCELED
}
