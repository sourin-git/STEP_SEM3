public class FestWideTicketSettlement {
    static class EventTicket {
        private static int ticketsIssued;
        private final String ticketId;
        private double basePrice;
        private double amountPaid;

        EventTicket(double basePrice) {
            if (basePrice <= 0) {
                throw new IllegalArgumentException("Base price must be positive");
            }
            ticketsIssued++;
            ticketId = String.format("TCK-%04d", 1000 + ticketsIssued);
            this.basePrice = basePrice;
        }

        void pay(double amount) {
            if (amount > 0) {
                amountPaid += amount;
            }
        }

        void pay(double amount, String mode) {
            System.out.println("Payment mode: " + mode);
            pay(amount);
        }

        double getBalanceDue() {
            return Math.max(0, basePrice - amountPaid);
        }

        static boolean isValidPromoCode(String code) {
            if (code == null || code.length() != 5 || code.charAt(0) != 'F'
                    || !Character.isDigit(code.charAt(1))
                    || !Character.isDigit(code.charAt(2))
                    || !Character.isDigit(code.charAt(3))
                    || !Character.isUpperCase(code.charAt(4))) {
                return false;
            }
            return true;
        }

        static int getTicketsIssued() {
            return ticketsIssued;
        }
    }

    static class GroupTicket extends EventTicket {
        private int groupSize;

        GroupTicket(double basePrice, int groupSize) {
            super(basePrice);
            if (groupSize <= 0) {
                throw new IllegalArgumentException("Group size must be positive");
            }
            this.groupSize = groupSize;
        }
    }

    static String processNightlySettlement(EventTicket[] tickets) {
        int processed = 0;
        int nullSkipped = 0;
        int groups = 0;
        for (EventTicket ticket : tickets) {
            if (ticket == null) {
                nullSkipped++;
            } else {
                processed++;
                if (ticket instanceof GroupTicket) {
                    groups++;
                }
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | "
                + groups + " group | " + (processed - groups) + " individual";
    }

    public static void main(String[] args) {
        EventTicket ticket = new EventTicket(500);
        System.out.println(ticket.ticketId);
        System.out.println(EventTicket.getTicketsIssued());
        System.out.println(EventTicket.isValidPromoCode("F123A"));
        System.out.println(EventTicket.isValidPromoCode("F12A"));
        System.out.println(EventTicket.isValidPromoCode("X123A"));
        ticket.pay(200);
        ticket.pay(200, "UPI");
        System.out.println(ticket.getBalanceDue());
        System.out.println(processNightlySettlement(new EventTicket[]{
                new GroupTicket(2000, 5), null, new EventTicket(500)
        }));
    }
}