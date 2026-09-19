import java.util.Arrays;

public class LateRegistrationPenaltyAudit {
    static class EventTicket {
        private double basePrice;
        private double amountPaid;
        private double[] lateFeeHistory = new double[10];
        private int lateFeeCount;

        EventTicket(double basePrice) {
            this.basePrice = basePrice;
        }

        void pay(double amount) {
            if (amount > 0) {
                amountPaid += amount;
            }
        }

        protected void applyLateFee(double amount) {
            if (amount > 0 && lateFeeCount < lateFeeHistory.length) {
                lateFeeHistory[lateFeeCount++] = amount;
                basePrice += amount;
            }
        }

        double getBalanceDue() {
            return basePrice - amountPaid;
        }

        double[] getLateFeeHistory() {
            return Arrays.copyOf(lateFeeHistory, lateFeeCount);
        }
    }

    static class WorkshopTicket extends EventTicket {
        WorkshopTicket(double basePrice) {
            super(basePrice);
        }

        @Override
        protected void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);
        }
    }

    public static void main(String[] args) {
        WorkshopTicket workshop = new WorkshopTicket(1200);
        workshop.pay(1200);
        workshop.applyLateFee(100);
        System.out.println(workshop.getBalanceDue());
        double[] history = workshop.getLateFeeHistory();
        System.out.println(Arrays.toString(history));
        history[0] = 999;
        System.out.println(Arrays.toString(workshop.getLateFeeHistory()));
    }
}