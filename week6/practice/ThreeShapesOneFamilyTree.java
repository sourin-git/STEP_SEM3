public class ThreeShapesOneFamilyTree {
    static class EventTicket {
        protected double basePrice;
        private double amountPaid;

        EventTicket(String attendeeId, double basePrice) {
            if (attendeeId == null || attendeeId.trim().length() < 4 || basePrice <= 0) {
                throw new IllegalArgumentException("Invalid ticket details");
            }
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

        void printTicket() {
            System.out.println("Standard Event Ticket | Balance Due: " + getBalanceDue());
        }
    }

    static class WorkshopTicket extends EventTicket {
        protected String track;

        WorkshopTicket(String attendeeId, double basePrice, String track) {
            super(attendeeId, basePrice);
            this.track = track;
        }

        @Override
        void printTicket() {
            System.out.println("Workshop Ticket | Track: " + track + " | Balance Due: " + getBalanceDue());
        }
    }

    static class PremiumWorkshopTicket extends WorkshopTicket {
        private double kitFee;

        PremiumWorkshopTicket(String attendeeId, double basePrice, String track, double kitFee) {
            super(attendeeId, basePrice, track);
            this.kitFee = kitFee;
        }

        @Override
        void printTicket() {
            System.out.println("Premium Workshop Ticket | Track: " + track + " | Kit Fee: "
                    + kitFee + " | Balance Due: " + getBalanceDue());
        }
    }

    static class HackathonTicket extends EventTicket {
        private String teamName;

        HackathonTicket(String attendeeId, double basePrice, String teamName) {
            super(attendeeId, basePrice);
            this.teamName = teamName;
        }

        @Override
        void printTicket() {
            System.out.println("Hackathon Ticket | Team: " + teamName + " | Balance Due: " + getBalanceDue());
        }
    }

    static String classifyGeneration(EventTicket ticket) {
        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        }
        return ticket instanceof WorkshopTicket ? "Direct workshop descendant" : "Base event ticket";
    }

    static double getTotalBalanceDue(EventTicket[] tickets) {
        double total = 0;
        for (EventTicket ticket : tickets) {
            total += ticket.getBalanceDue();
        }
        return total;
    }

    public static void main(String[] args) {
        EventTicket standard = new EventTicket("STU1", 500);
        WorkshopTicket workshop = new WorkshopTicket("STU2", 1200, "AI/ML");
        PremiumWorkshopTicket premium = new PremiumWorkshopTicket("STU3", 2000, "Cloud Native", 300);
        HackathonTicket hackathon = new HackathonTicket("STU4", 800, "Byte Force");
        EventTicket[] tickets = {standard, workshop, premium, hackathon};
        for (EventTicket ticket : tickets) {
            ticket.printTicket();
        }
        System.out.println(classifyGeneration(premium));
        System.out.println(classifyGeneration(hackathon));
        System.out.println(getTotalBalanceDue(tickets));
    }
}