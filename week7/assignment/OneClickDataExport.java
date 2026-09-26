public class OneClickDataExport {
    interface Exportable {
        String exportData();
    }

    static class ExportCounter {
        private static int totalExports;
    }

    static class ReportGenerator implements Exportable {
        private String reportName;

        ReportGenerator(String reportName) {
            if (reportName == null || reportName.trim().isEmpty()) {
                throw new IllegalArgumentException("Report name is required");
            }
            this.reportName = reportName;
        }

        @Override
        public String exportData() {
            ExportCounter.totalExports++;
            return "Exported report: " + reportName;
        }
    }

    static class UserProfile implements Exportable {
        private String username;

        UserProfile(String username) {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username is required");
            }
            this.username = username;
        }

        @Override
        public String exportData() {
            ExportCounter.totalExports++;
            return "Exported profile: " + username;
        }
    }

    static int getTotalExports() {
        return ExportCounter.totalExports;
    }

    static void exportAll(Exportable[] items) {
        for (Exportable item : items) {
            System.out.println(item.exportData());
        }
    }

    public static void main(String[] args) {
        ReportGenerator report = new ReportGenerator("Sales Q1");
        UserProfile profile = new UserProfile("jane_doe");
        exportAll(new Exportable[]{report, profile});
        System.out.println("Total exports: " + getTotalExports());
    }
}