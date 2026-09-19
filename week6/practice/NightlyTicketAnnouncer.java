public class NightlyTicketAnnouncer {
    static class EventTicket {
        protected double basePrice;

        EventTicket(double basePrice) {
            this.basePrice = basePrice;
        }

        double getBalanceDue() {
            return basePrice;
        }

        String printTicket() {
            return "Standard | Balance: " + getBalanceDue();
        }
    }

    static class WorkshopTicket extends EventTicket {
        private String track;

        WorkshopTicket(double basePrice, String track) {
            super(basePrice);
            this.track = track;
        }

        @Override
        String printTicket() {
            return "Workshop | Track: " + track + " | Balance: " + getBalanceDue();
        }

        String getTrack() {
            return track;
        }
    }

    static String batchPrint(EventTicket[] tickets) {
        StringBuilder report = new StringBuilder();
        for (EventTicket ticket : tickets) {
            report.append(ticket.printTicket());
            if (ticket instanceof WorkshopTicket workshop) {
                report.append(" [Track via downcast: ").append(workshop.getTrack()).append("]");
            }
            report.append(" | ");
        }
        return report.toString();
    }

    public static void main(String[] args) {
        System.out.println(batchPrint(new EventTicket[]{
                new EventTicket(500), new WorkshopTicket(1200, "AI/ML")
        }));
    }
}