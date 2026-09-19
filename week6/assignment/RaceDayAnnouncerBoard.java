public class RaceDayAnnouncerBoard {
    static class RaceEntry {
        protected String bibNumber;
        protected double entryFee;

        RaceEntry(String bibNumber, double entryFee) {
            this.bibNumber = bibNumber;
            this.entryFee = entryFee;
        }

        String announce() {
            return "Standard | Bib: " + bibNumber + " | Balance: " + entryFee;
        }
    }

    static class RunnerEntry extends RaceEntry {
        private String category;

        RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        @Override
        String announce() {
            return "Runner | Bib: " + bibNumber + " | Category: " + category + " | Balance: " + entryFee;
        }
    }

    static class RelayTeamEntry extends RaceEntry {
        private int teamSize;

        RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            this.teamSize = teamSize;
        }

        @Override
        String announce() {
            return "Relay Team | Bib: " + bibNumber + " | Team Size: " + teamSize
                    + " | Balance: " + entryFee;
        }

        int getTeamSize() {
            return teamSize;
        }
    }

    static String announceAll(RaceEntry[] entries) {
        StringBuilder report = new StringBuilder();
        for (RaceEntry entry : entries) {
            report.append(entry.announce());
            if (entry instanceof RelayTeamEntry relay) {
                report.append(" [Team size via downcast: ").append(relay.getTeamSize()).append("]");
            }
            report.append(" | ");
        }
        return report.toString();
    }

    public static void main(String[] args) {
        RaceEntry[] entries = {
                new RunnerEntry("BIB2001", 90, "Open 10K"),
                new RelayTeamEntry("BIB4001", 300, 4)
        };
        System.out.println(announceAll(entries));
    }
}