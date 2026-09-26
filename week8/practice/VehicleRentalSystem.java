import java.util.HashMap;
import java.util.Map;

public class VehicleRentalSystem {
    interface PricingStrategy {
        double calculateCharge(int days);
    }

    static class DailyRatePricing implements PricingStrategy {
        private final double dailyRate;
        DailyRatePricing(double dailyRate) { this.dailyRate = dailyRate; }
        public double calculateCharge(int days) { return dailyRate * days; }
    }

    static abstract class Vehicle {
        private final String name;
        private final PricingStrategy pricingStrategy;

        Vehicle(String name, PricingStrategy pricingStrategy) {
            this.name = name;
            this.pricingStrategy = pricingStrategy;
        }

        String getName() { return name; }
        double calculateCharge(int days) { return pricingStrategy.calculateCharge(days); }
    }

    static class StandardCar extends Vehicle {
        StandardCar(String name) { super(name, new DailyRatePricing(50)); }
    }

    static class LuxuryCar extends Vehicle {
        LuxuryCar(String name) { super(name, new DailyRatePricing(100)); }
    }

    static class SUV extends Vehicle {
        SUV(String name) { super(name, new DailyRatePricing(75)); }
    }

    static class Customer {
        private final String name;
        Customer(String name) { this.name = name; }
    }

    static class Rental {
        private final Customer customer;
        private final Vehicle vehicle;
        private final int days;
        private final double totalCharge;
        private boolean active = true;

        Rental(Customer customer, Vehicle vehicle, int days) {
            this.customer = customer;
            this.vehicle = vehicle;
            this.days = days;
            totalCharge = vehicle.calculateCharge(days);
        }

        void returnVehicle() { active = false; }
    }

    static class RentalService {
        private final Map<String, Rental> activeRentals = new HashMap<>();

        Rental rent(Customer customer, Vehicle vehicle, int days) {
            if (days <= 0) throw new IllegalArgumentException("Rental duration must be positive");
            if (activeRentals.containsKey(vehicle.getName())) {
                throw new IllegalStateException(vehicle.getName() + " already has an active rental");
            }
            Rental rental = new Rental(customer, vehicle, days);
            activeRentals.put(vehicle.getName(), rental);
            System.out.printf("%s rented for %d days. Total charge: $%.2f%n",
                    vehicle.getName(), days, rental.totalCharge);
            return rental;
        }

        void returnVehicle(Vehicle vehicle) {
            Rental rental = activeRentals.remove(vehicle.getName());
            if (rental == null) throw new IllegalStateException("Vehicle has no active rental");
            rental.returnVehicle();
            System.out.println(vehicle.getName() + " returned. Now available.");
        }
    }

    public static void main(String[] args) {
        RentalService service = new RentalService();
        Customer customer = new Customer("Alex");
        Vehicle luxury = new LuxuryCar("Luxury Car A");
        Vehicle standard = new StandardCar("Standard Car B");
        service.rent(customer, luxury, 3);
        service.rent(customer, standard, 5);
        service.returnVehicle(luxury);
    }
}