public class LibraryFineSystem {
    static class BookIssue {
        private String title;
        private String borrowerName;
        private int daysOverdue;

        BookIssue(String title, String borrowerName, int daysOverdue) {
            this.title = title;
            this.borrowerName = borrowerName;
            this.daysOverdue = daysOverdue;
        }

        double fineAmount() {
            return daysOverdue > 0 ? daysOverdue * 5.0 : 0;
        }

        boolean isSeverelyOverdue() {
            return daysOverdue > 14;
        }

        // totalFineCollected combines many issues, while fineAmount describes one issue.
        static double totalFineCollected(BookIssue[] issues) {
            double total = 0;
            for (BookIssue issue : issues) {
                total += issue.fineAmount();
            }
            return total;
        }

        void printStatus() {
            System.out.printf("%s - %d days - %s%n", title, daysOverdue,
                    isSeverelyOverdue() ? "Severely overdue" : "OK");
        }
    }

    public static void main(String[] args) {
        BookIssue[] issues = {
                new BookIssue("Clean Code", "Ravi", 18),
                new BookIssue("Effective Java", "Anitha", 5),
                new BookIssue("Refactoring", "Karthik", 0),
                new BookIssue("DSA Handbook", "Meera", 21),
                new BookIssue("Design Patterns", "Suresh", 9)
        };

        for (BookIssue issue : issues) {
            issue.printStatus();
        }
        System.out.printf("Total fine collected: Rs %.1f%n", BookIssue.totalFineCollected(issues));
    }
}