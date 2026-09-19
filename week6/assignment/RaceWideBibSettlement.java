public class RaceWideBibSettlement {
    static class RaceEntry {
        private static int bibCounter;
        private final String entryCode;
        private double entryFee;
        private double amountPaid;

        RaceEntry(double entryFee) {
            if (entryFee <= 0) {
                throw new IllegalArgumentException("Entry fee must be positive");
            }
            bibCounter++;
            entryCode = String.format("RACE-%04d", 1000 + bibCounter);
            this.entryFee = entryFee;
        }

        void pay(double amount) {
            if (amount > 0) {
                amountPaid += amount;
            }
        }

        void pay(double amount, String mode) {
            System.out.println("Paying via " + mode);
            pay(amount);
        }

        static boolean isValidDiscountCode(String code) {
            if (code == null || code.length() != 5 || code.charAt(0) != 'M') {
                return false;
            }
            return Character.isDigit(code.charAt(1))
                    && Character.isDigit(code.charAt(2))
                    && Character.isDigit(code.charAt(3))
                    && Character.isUpperCase(code.charAt(4));
        }

        static int getBibCounter() {
            return bibCounter;
        }

        static double getBalanceDue() {
            return 0;
        }
    }

    static class RelayTeamEntry extends RaceEntry {
        private int teamSize;

        RelayTeamEntry(double entryFee, int teamSize) {
            super(entryFee);
            if (teamSize <= 0) {
                throw new IllegalArgumentException("Team size must be positive");
            }
            this.teamSize = teamSize;
        }
    }

    static String settleNight(RaceEntry[] entries) {
        int processed = 0;
        int nullSkipped = 0;
        int relay = 0;
        for (RaceEntry entry : entries) {
            if (entry == null) {
                nullSkipped++;
            } else {
                processed++;
                if (entry instanceof RelayTeamEntry) {
                    relay++;
                }
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | "
                + relay + " relay | " + (processed - relay) + " individual";
    }

    public static void main(String[] args) {
        RaceEntry first = new RaceEntry(500);
        System.out.println(first.entryCode);
        System.out.println(RaceEntry.isValidDiscountCode("M123A"));
        System.out.println(RaceEntry.isValidDiscountCode("M12A"));
        System.out.println(RaceEntry.isValidDiscountCode("X123A"));
        first.pay(10, "UPI");
        System.out.println(settleNight(new RaceEntry[]{
                new RelayTeamEntry(200, 4), null, new RaceEntry(500)
        }));
        System.out.println("Bib counter: " + RaceEntry.getBibCounter());
    }
}