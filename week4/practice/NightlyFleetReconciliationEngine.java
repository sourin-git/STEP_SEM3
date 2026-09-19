public class NightlyFleetReconciliationEngine {
    static class BusTicketAccount {
        private static String depotName;
        private String bookingId;
        private double ticketFare;
        private double amountPaid;

        static {
            depotName = "Central Depot";
        }

        BusTicketAccount(String bookingId, double ticketFare) {
            if (ticketFare < 0) {
                throw new IllegalArgumentException("Ticket fare cannot be negative");
            }
            this.bookingId = bookingId;
            this.ticketFare = ticketFare;
        }

        BusTicketAccount(String bookingId) {
            this(bookingId, 0);
        }

        final double calculatePenalty(int minutesLate) {
            if (minutesLate < 0) {
                throw new IllegalArgumentException("Late minutes cannot be negative");
            }
            return ticketFare * minutesLate * 0.01;
        }

        void processAccount(BusTicketAccount account, double amount, int minutesLate) {
            if (amount >= 0) {
                amountPaid += amount;
            }
        }
    }

    static class SleeperTicketAccount extends BusTicketAccount {
        SleeperTicketAccount(String bookingId, double ticketFare) {
            super(bookingId, ticketFare);
        }

        double sleeperPenalty(int minutesLate) {
            return calculatePenalty(minutesLate) * 0.5;
        }
    }

    static void processBatch(BusTicketAccount[] accounts, double[] amounts, int[] minutesLateArray) {
        if (accounts == null || amounts == null || minutesLateArray == null
                || accounts.length != amounts.length || accounts.length != minutesLateArray.length) {
            throw new IllegalArgumentException("Batch arrays must be non-null and have matching lengths");
        }

        int processed = 0;
        int nullSkipped = 0;
        int sleeperCount = 0;
        double grandTotalPenalties = 0;

        for (int index = 0; index < accounts.length; index++) {
            BusTicketAccount account = accounts[index];
            if (account == null) {
                nullSkipped++;
                continue;
            }
            account.processAccount(account, amounts[index], minutesLateArray[index]);
            grandTotalPenalties += account instanceof SleeperTicketAccount sleeper
                    ? sleeper.sleeperPenalty(minutesLateArray[index])
                    : account.calculatePenalty(minutesLateArray[index]);
            processed++;
            if (account instanceof SleeperTicketAccount) {
                sleeperCount++;
            }
        }

        System.out.printf("%d processed | %d null skipped | %d sleeper | %d regular | grand total penalties = Rs %.1f%n",
                processed, nullSkipped, sleeperCount, processed - sleeperCount, grandTotalPenalties);
    }

    public static void main(String[] args) {
        BusTicketAccount[] accounts = {
                new SleeperTicketAccount("BK001", 2000), null,
                new BusTicketAccount("BK002", 1200)
        };
        processBatch(accounts, new double[]{1200, 900, 700}, new int[]{10, 5, 0});
    }
}