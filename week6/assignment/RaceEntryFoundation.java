public class RaceEntryFoundation {
    static class RaceEntry {
        protected String bibNumber;
        protected double entryFee;
        private double amountPaid;

        RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < 4 || entryFee <= 0) {
                throw new IllegalArgumentException("Invalid race entry");
            }
            this.bibNumber = bibNumber.trim();
            this.entryFee = entryFee;
        }

        void pay(double amount) {
            if (amount > 0) {
                amountPaid += amount;
            }
        }

        double getBalanceDue() {
            return Math.max(0, entryFee - amountPaid);
        }
    }

    static class RunnerEntry extends RaceEntry {
        private String category;

        RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }
    }

    static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;
        for (String bibNumber : bibNumbers) {
            try {
                new RaceEntry(bibNumber, entryFee);
                registered++;
            } catch (IllegalArgumentException exception) {
                rejected++;
            }
        }
        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {
        try {
            new RaceEntry("B1", 50);
        } catch (IllegalArgumentException exception) {
            System.out.println("construction rejected");
        }
        RunnerEntry runner = new RunnerEntry("BIB2001", 80, "Open 10K");
        runner.pay(30);
        System.out.println(runner.getBalanceDue());
        System.out.println(registerBatch(new String[]{"BIB1", "B1", "BIB2"}, 80));
    }
}