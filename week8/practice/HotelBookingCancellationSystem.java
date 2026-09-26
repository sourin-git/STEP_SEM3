import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelBookingCancellationSystem {
    interface RoomPricing {
        double pricePerNight();
    }

    static class StandardPricing implements RoomPricing {
        public double pricePerNight() { return 150; }
    }

    static class DeluxePricing implements RoomPricing {
        public double pricePerNight() { return 200; }
    }

    static class SuitePricing implements RoomPricing {
        public double pricePerNight() { return 350; }
    }

    static class Customer {
        private final String name;
        Customer(String name) { this.name = name; }
    }

    static class Room {
        private final String roomName;
        private final RoomPricing pricing;
        private final List<Reservation> reservations = new ArrayList<>();

        Room(String roomName, RoomPricing pricing) {
            this.roomName = roomName;
            this.pricing = pricing;
        }

        boolean isAvailable(LocalDate start, LocalDate end) {
            for (Reservation reservation : reservations) {
                if (reservation.active && start.isBefore(reservation.endDate)
                        && reservation.startDate.isBefore(end)) return false;
            }
            return true;
        }
    }

    static class Reservation {
        private final Customer customer;
        private final Room room;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final LocalDate cancellationDeadline;
        private final double totalPrice;
        private boolean active = true;

        Reservation(Customer customer, Room room, LocalDate startDate, LocalDate endDate) {
            this.customer = customer;
            this.room = room;
            this.startDate = startDate;
            this.endDate = endDate;
            cancellationDeadline = startDate.minusDays(1);
            totalPrice = ChronoUnit.DAYS.between(startDate, endDate) * room.pricing.pricePerNight();
        }
    }

    static class BookingManager {
        private final Map<String, Room> rooms = new HashMap<>();
        private final Clock clock;

        BookingManager(Clock clock) { this.clock = clock; }
        void addRoom(Room room) { rooms.put(room.roomName, room); }

        Reservation book(Customer customer, String roomName, LocalDate start, LocalDate end) {
            Room room = rooms.get(roomName);
            if (room == null || !start.isBefore(end) || !room.isAvailable(start, end)) {
                System.out.printf("Booking failed: %s is not available for %s to %s.%n", roomName, start, end);
                return null;
            }
            Reservation reservation = new Reservation(customer, room, start, end);
            room.reservations.add(reservation);
            System.out.printf("%s booked from %s to %s. Total price: $%.2f%n",
                    roomName, start, end, reservation.totalPrice);
            return reservation;
        }

        boolean cancel(Reservation reservation) {
            if (reservation == null || !reservation.active
                    || !clock.instant().isBefore(reservation.cancellationDeadline.atStartOfDay(clock.getZone()).toInstant())) {
                return false;
            }
            reservation.active = false;
            System.out.println("Reservation for " + reservation.room.roomName + " cancelled successfully.");
            return true;
        }
    }

    public static void main(String[] args) {
        Clock clock = Clock.fixed(java.time.Instant.parse("2024-11-20T12:00:00Z"), java.time.ZoneOffset.UTC);
        BookingManager manager = new BookingManager(clock);
        manager.addRoom(new Room("Deluxe Room 101", new DeluxePricing()));
        manager.addRoom(new Room("Standard Room 205", new StandardPricing()));
        Customer customer = new Customer("Taylor");
        LocalDate d1 = LocalDate.parse("2024-12-01");
        LocalDate d2 = LocalDate.parse("2024-12-05");
        LocalDate d3 = LocalDate.parse("2024-12-03");
        LocalDate d4 = LocalDate.parse("2024-12-07");
        Reservation deluxe = manager.book(customer, "Deluxe Room 101", d1, d2);
        manager.book(customer, "Standard Room 205", d3, d4);
        manager.book(customer, "Deluxe Room 101", d3, d4);
        manager.cancel(deluxe);
    }
}