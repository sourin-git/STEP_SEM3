public class TicketHierarchyFoundation {
    static class EventTicket {
        protected String attendeeId;
        protected double basePrice;
        private double amountPaid;

        EventTicket(String attendeeId, double basePrice) {
            if (attendeeId == null || attendeeId.trim().length() < 4 || basePrice <= 0) {
                throw new IllegalArgumentException("Invalid ticket details");
            }
            this.attendeeId = attendeeId.trim();
            this.basePrice = basePrice;
        }

        void pay(double amount) {
            if (amount > 0) {
                amountPaid += amount;
            }
        }

        double getBalanceDue() {
            return Math.max(0, basePrice - amountPaid);
        }
    }

    static class WorkshopTicket extends EventTicket {
        private String track;

        WorkshopTicket(String attendeeId, double basePrice, String track) {
            super(attendeeId, basePrice);
            this.track = track;
        }
    }

    static String registerBatch(String[] attendeeIds, double basePrice) {
        int registered = 0;
        int rejected = 0;
        for (String attendeeId : attendeeIds) {
            try {
                new EventTicket(attendeeId, basePrice);
                registered++;
            } catch (IllegalArgumentException exception) {
                rejected++;
            }
        }
        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {
        try {
            new EventTicket("ST1", 500);
        } catch (IllegalArgumentException exception) {
            System.out.println("construction rejected");
        }
        WorkshopTicket workshop = new WorkshopTicket("STU2", 1200, "AI/ML");
        workshop.pay(500);
        System.out.println(workshop.getBalanceDue());
        System.out.println(registerBatch(new String[]{"STU1", "ST1", "STU2", " ", "STU3"}, 500));
    }
}