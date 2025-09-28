package com.ismail.fleetShare.ui;

import com.ismail.fleetShare.application.TripService;
import com.ismail.fleetShare.application.VehicleService;
import com.ismail.fleetShare.domain.common.Money;
import com.ismail.fleetShare.domain.price.*;
import com.ismail.fleetShare.domain.trip.Trip;
import com.ismail.fleetShare.domain.user.MembershipTier;
import com.ismail.fleetShare.domain.user.User;
import com.ismail.fleetShare.domain.vehicle.*;
import com.ismail.fleetShare.infrastructure.InMemoryUserRepository;
import com.ismail.fleetShare.infrastructure.InMemoryVehicleRepository;

import java.util.*;

/**
 * Provides a simple menu to:
 * - Add vehicles
 * - List available vehicles
 * - Register users
 * - Start and end trips
 * Pricing is calculated using a HybridPricing strategy (Time + Distance).
 * All data is stored in-memory using simple repositories.
*/
public class ConsoleApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //  Repositories
        InMemoryVehicleRepository vehicleRepo = new InMemoryVehicleRepository();
        InMemoryUserRepository userRepo = new InMemoryUserRepository();

        // Services
        VehicleService vehicleService = new VehicleService(vehicleRepo);

        // Pricing Strategy
        PricingStrategy pricingStrategy = new HybridPricing(
                new TimeBasedPricing(Money.of(0.5)),      // $0.5 per minute
                new DistanceBasedPricing(Money.of(0.2))   // $0.2 per km
        );

        TripService tripService = new TripService(vehicleRepo, userRepo, pricingStrategy);

        Map<String, Trip> ongoingTrips = new HashMap<>();
        boolean running = true;


        while (running) {
            System.out.println("\n=== Welcome to FleetShare ===");
            System.out.println("1. Add Vehicle");
            System.out.println("2. List Available Vehicles");
            System.out.println("3. Register User");
            System.out.println("4. Start Trip");
            System.out.println("5. End Trip");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                //  Add Vehicle
                case 1:
                    System.out.print("Enter Vehicle ID: ");
                    String vid = scanner.nextLine();
                    System.out.print("Enter Vehicle Type (Car/EBike/Scooter): ");
                    String type = scanner.nextLine().trim();
                    System.out.print("Enter Brand: ");
                    String brand = scanner.nextLine();

                    Vehicle vehicle;
                    if (type.equalsIgnoreCase("Car")) {
                        vehicle = new Car(vid, brand,"Diecel", Money.of(1.0));
                    } else if (type.equalsIgnoreCase("EBike")) {
                        vehicle = new EBike(vid, brand, "100%",Money.of(0.5));
                    } else if (type.equalsIgnoreCase("Scooter")) {
                        vehicle = new Scooter(vid, brand, "150 KG",Money.of(0.3));
                    } else {
                        System.out.println("Invalid vehicle type! Defaulting to Car.");
                        vehicle = new Car(vid, brand, "Diecel",Money.of(1.0));
                    }

                    vehicleService.addVehicle(vehicle);
                    System.out.println("Vehicle added successfully!");
                    break;

                //Available Vehicles
                case 2:
                    List<Vehicle> available = vehicleService.getAvailableVehicles();
                    if (available.isEmpty()) {
                        System.out.println("No available vehicles.");
                    } else {
                        System.out.println("Available Vehicles:");
                        available.forEach(v ->
                                System.out.println(v.getId() + " - " + v.getClass().getSimpleName() + " (" + v.getModel() + ")")
                        );
                    }
                    break;

                //  Register User
                case 3:
                    System.out.print("Enter User ID: ");
                    String uid = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Tier (STANDARD/PREMIUM/VIP): ");
                    String tierInput = scanner.nextLine().trim().toUpperCase();

                    try {
                        MembershipTier tier = MembershipTier.valueOf(tierInput);
                        User user = new User(uid, name, tier);
                        userRepo.save(user);
                        System.out.println("User registered successfully!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid tier! User registration failed.");
                    }
                    break;

                //  Start
                case 4:
                    System.out.print("Enter User ID: ");
                    String userId = scanner.nextLine();
                    System.out.print("Enter Vehicle ID: ");
                    String vehicleId = scanner.nextLine();

                    User u = userRepo.findById(userId);
                    Vehicle v = vehicleRepo.findById(vehicleId);

                    if (u != null && v != null && v.getState() == VehicleState.AVAILABLE) {
                        Trip trip = tripService.startTrip(u, v);
                        ongoingTrips.put(trip.getId().getValue(), trip);
                        System.out.println("Trip started! Trip ID: " + trip.getId().getValue());
                    } else {
                        System.out.println("User or Vehicle not found / Vehicle not available.");
                    }
                    break;

                // End
                case 5:
                    System.out.print("Enter Trip ID: ");
                    String tripId = scanner.nextLine();
                    Trip tripToEnd = ongoingTrips.get(tripId);

                    if (tripToEnd != null) {
                        Money price = tripService.endTrip(tripId);

                        java.time.Duration duration = tripToEnd.getDuration();

                        ongoingTrips.remove(tripId);

                        System.out.println("Trip ended successfully!");
                        System.out.println("Duration: " + duration.toMinutes() + " minutes");
                        System.out.println("Price: $" + price);
                    } else {
                        System.out.println("No ongoing trip found for this ID.");
                    }
                    break;

                //  Exit
                case 6:
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }

        scanner.close();
    }
}
