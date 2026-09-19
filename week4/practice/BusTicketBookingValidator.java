import java.util.HashSet;
import java.util.Set;

public class BusTicketBookingValidator {
    static class BusTicket {
        private String passengerName;
        private String destination;
        private boolean checkedIn;

        BusTicket(String passengerName, String destination) {
            if (passengerName == null || passengerName.trim().isEmpty()
                    || !passengerName.matches("[A-Za-z ]+")
                    || destination == null || destination.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid booking");
            }
            this.passengerName = passengerName.trim();
            this.destination = destination.trim();
        }

        void markCheckedIn() {
            if (!checkedIn) {
                checkedIn = true;
            }
        }

        String bookingKey() {
            return passengerName.toLowerCase() + "|" + destination.toLowerCase();
        }
    }

    public static void processBatch(String[][] rawBookings) {
        int valid = 0;
        int rejected = 0;
        int duplicates = 0;
        Set<String> acceptedPairs = new HashSet<>();

        for (String[] rawBooking : rawBookings) {
            if (rawBooking == null || rawBooking.length < 2) {
                rejected++;
                continue;
            }

            try {
                BusTicket ticket = new BusTicket(rawBooking[0], rawBooking[1]);
                if (!acceptedPairs.add(ticket.bookingKey())) {
                    duplicates++;
                } else {
                    valid++;
                }
            } catch (IllegalArgumentException exception) {
                rejected++;
            }
        }

        System.out.printf("Valid: %d | Rejected: %d | Duplicates skipped: %d%n",
                valid, rejected, duplicates);
    }

    public static void main(String[] args) {
        String[][] rawBookings = {
                {"Divya", "Chennai"}, {"", "Bangalore"}, {"Ravi123", "Pune"},
                {"Divya", "Chennai"}, {" ", " "}
        };
        processBatch(rawBookings);
    }
}