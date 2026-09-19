public class ThreeShapesOneRaceFamily {
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

        void announce() {
            System.out.println("Standard Race Entry | Balance: " + getBalanceDue());
        }
    }

    static class RunnerEntry extends RaceEntry {
        protected String category;

        RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        @Override
        void announce() {
            System.out.println("Runner Entry | Bib: " + bibNumber + " | Category: " + category
                    + " | Balance: " + getBalanceDue());
        }
    }

    static class EliteRunnerEntry extends RunnerEntry {
        private double sponsorBonus;

        EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
            super(bibNumber, entryFee, category);
            this.sponsorBonus = sponsorBonus;
        }

        @Override
        void announce() {
            System.out.println("Elite Runner | Bib: " + bibNumber + " | Category: " + category
                    + " | Sponsor Bonus: " + sponsorBonus + " | Balance: " + getBalanceDue());
        }
    }

    static class RelayTeamEntry extends RaceEntry {
        private int teamSize;

        RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            this.teamSize = teamSize;
        }

        @Override
        void announce() {
            System.out.println("Relay Team | Bib: " + bibNumber + " | Team Size: " + teamSize
                    + " | Balance: " + getBalanceDue());
        }
    }

    static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        }
        return entry instanceof RunnerEntry ? "Direct runner descendant" : "Base race entry";
    }

    static double getTotalBalanceDue(RaceEntry[] entries) {
        double total = 0;
        for (RaceEntry entry : entries) {
            total += entry.getBalanceDue();
        }
        return total;
    }

    public static void main(String[] args) {
        RunnerEntry runner = new RunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry elite = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relay = new RelayTeamEntry("BIB4001", 300, 4);
        RaceEntry[] entries = {runner, elite, relay};
        for (RaceEntry entry : entries) {
            entry.announce();
        }
        System.out.println(classifyGeneration(elite));
        System.out.println(classifyGeneration(relay));
        System.out.println(getTotalBalanceDue(entries));
    }
}