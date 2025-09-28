package com.ismail.fleetShare.tests;

import com.ismail.fleetShare.application.VehicleService;
import com.ismail.fleetShare.domain.common.Distance;
import com.ismail.fleetShare.domain.common.Money;
import com.ismail.fleetShare.domain.price.*;
import com.ismail.fleetShare.domain.trip.Trip;
import com.ismail.fleetShare.domain.trip.TripId;
import com.ismail.fleetShare.domain.trip.TripState;
import com.ismail.fleetShare.domain.user.MembershipTier;
import com.ismail.fleetShare.domain.user.User;
import com.ismail.fleetShare.domain.vehicle.Car;
import com.ismail.fleetShare.domain.vehicle.EBike;
import com.ismail.fleetShare.domain.vehicle.Vehicle;
import com.ismail.fleetShare.infrastructure.InMemoryUserRepository;
import com.ismail.fleetShare.infrastructure.InMemoryVehicleRepository;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * PricingTest يحتوي على اختبارات لجميع استراتيجيات التسعير
 * بالإضافة لاختبارات Repositories وTrip lifecycle وUser.
 */
public class PricingTest {

    /**
     * مثال عملي لتشغيل VehicleService من main
     */
    public static void main(String[] args) {
        InMemoryVehicleRepository repo = new InMemoryVehicleRepository();
        VehicleService vehicleService = new VehicleService(repo);

        // إضافة مركبات
        vehicleService.addVehicle(new Car("car1", "Tesla","Diecel", Money.of(1.0)));
        vehicleService.addVehicle(new EBike("ebike1", "EbikeX","100%", Money.of(0.5)));

        // استعراض المركبات المتاحة
        for (Vehicle v : vehicleService.getAvailableVehicles()) {
            System.out.println(v.getId() + " - " + v.getClass().getSimpleName());
        }
    }


    @Test
    public void testTimeBasedPricing() {
        PricingStrategy basePricing = new TimeBasedPricing(Money.of(0.5));
        PricingStrategy surgePricing = new SurgeDecorator(basePricing, 1.5); //

        User user = new User("ah","Ahmad",MembershipTier.STANDARD);
        Vehicle ebike = new EBike("Ebike1","Samsung", "100%",Money.of(29));
        Trip trip = new Trip(new TripId(), user, ebike);

        Money price = surgePricing.calculatePrice(trip);
        System.out.println("Surge pricing: " + price);

        // اختبار VehicleRepository
        InMemoryVehicleRepository vehicleRepo = new InMemoryVehicleRepository();
        vehicleRepo.save(new Car("car1", "Tesla", "Diecel",Money.of(1.0)));
        vehicleRepo.save(new EBike("ebike1", "EbikeX", "100%",Money.of(0.5)));
        for (Vehicle v : vehicleRepo.findAvailable()) {
            System.out.println("Available vehicle: " + v.getId() + ", Type: " + v.getClass().getSimpleName());
        }

        // اختبار UserRepository
        InMemoryUserRepository userRepo = new InMemoryUserRepository();
        user = new User("u1", "Ismail", MembershipTier.PREMIUM);
        userRepo.save(user);
        User foundUser = userRepo.findById("u1");
        System.out.println("Found user: " + foundUser.getName());

        // اختبار حساب السعر على رحلة مدتها 10 دقائق
        user = new User("user1", "Ismail", MembershipTier.STANDARD);
        Vehicle car = new Car("car1", "Tesla","Diecel", Money.of(0.5));
        trip = new Trip(new TripId(), user, car);
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusMinutes(10);
        trip.setStartTime(startTime);
        trip.setEndTime(endTime);

        TimeBasedPricing pricing = new TimeBasedPricing(Money.of(0.5));
        Money expected = Money.of(5.0); // 10 دقائق * 0.5$/دقيقة
        Money actual = pricing.calculatePrice(trip);

        assertEquals(expected.getAmount(), actual.getAmount());
    }

    /**
     * اختبار DistanceBasedPricing
     */
    @Test
    public void testDistanceBasedPricing() {
        User user = new User("user1", "Ismail", MembershipTier.STANDARD);
        Vehicle car = new Car("car1", "Tesla", "Diecel",Money.of(1.0));
        Trip trip = new Trip(new TripId(), user, car);
        trip.setDistance(Distance.of(10)); // 10 كم

        DistanceBasedPricing pricing = new DistanceBasedPricing(Money.of(0.5));
        Money actual = pricing.calculatePrice(trip);

        assertEquals(5.0, actual.getAmount()); // 10 km * 0.5
    }

    /**
     * اختبار HybridPricing (Time + Distance)
     */
    @Test
    public void testHybridPricing() {
        User user = new User("user1", "Ismail", MembershipTier.STANDARD);
        Vehicle car = new Car("car1", "Tesla", "Diecel",Money.of(1.0));
        Trip trip = new Trip(new TripId(), user, car);
        trip.setDistance(Distance.of(10));
        trip.setStartTime(LocalDateTime.now());
        trip.setEndTime(trip.getStartTime().plusMinutes(10));

        HybridPricing pricing = new HybridPricing(
                new TimeBasedPricing(Money.of(0.5)),
                new DistanceBasedPricing(Money.of(0.2))
        );
        Money actual = pricing.calculatePrice(trip);

        System.out.println("Hybrid price: " + actual);
    }

    /**
     * اختبار findById في VehicleRepository
     */
    @Test
    public void testFindByIdVehicle() {
        InMemoryVehicleRepository vehicleRepo = new InMemoryVehicleRepository();
        Car car = new Car("car1", "Tesla", "Diecel",Money.of(1.0));
        vehicleRepo.save(car);

        Vehicle found = vehicleRepo.findById("car1");
        assertNotNull(found);
        assertEquals("car1", found.getId());
    }

    /**
     * اختبار عملة Money
     */
    @Test
    public void testMoneyCurrency() {
        Money money = Money.of(10.0);
        assertEquals("USD", money.getCurrency());
    }

    /**
     * اختبار تعديل MembershipTier للمستخدم
     */
    @Test
    public void testSetMembershipTier() {
        User user = new User("u1", "Ismail", MembershipTier.STANDARD);
        assertEquals(MembershipTier.STANDARD, user.getMembershipTier());

        user.setMembershipTier(MembershipTier.PREMIUM);
        assertEquals(MembershipTier.PREMIUM, user.getMembershipTier());
    }

    /**
     * اختبار إلغاء الرحلة وتغيير حالتها
     */
    @Test
    public void testCancelTrip() {
        Trip trip = new Trip(new TripId(),
                new User("IS","Ismail",MembershipTier.PREMIUM),
                new Car("car1","BMW","Diecel",Money.of(20)));
        trip.cancel(); // تأكد من وجود دالة cancel() في Trip

        assertEquals(TripState.CANCELED, trip.getState());
    }
}
