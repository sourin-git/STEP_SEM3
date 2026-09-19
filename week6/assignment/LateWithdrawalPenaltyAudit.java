import java.util.Arrays;

public class LateWithdrawalPenaltyAudit {
    static class RaceEntry {
        private double entryFee;
        private double amountPaid;
        private double[] lateFeeHistory = new double[10];
        private int lateFeeCount;

        RaceEntry(double entryFee) {
            this.entryFee = entryFee;
        }

        void pay(double amount) {
            if (amount > 0) {
                amountPaid += amount;
            }
        }

        protected void applyLateFee(double amount) {
            if (amount > 0 && lateFeeCount < lateFeeHistory.length) {
                lateFeeHistory[lateFeeCount++] = amount;
                entryFee += amount;
            }
        }

        double getBalanceDue() {
            return entryFee - amountPaid;
        }

        double[] getLateFeeHistory() {
            return Arrays.copyOf(lateFeeHistory, lateFeeCount);
        }
    }

    static class RunnerEntry extends RaceEntry {
        RunnerEntry(double entryFee) {
            super(entryFee);
        }

        @Override
        protected void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);
        }
    }

    public static void main(String[] args) {
        RunnerEntry runner = new RunnerEntry(80);
        runner.pay(30);
        runner.applyLateFee(20);
        System.out.println(runner.getBalanceDue());
        double[] history = runner.getLateFeeHistory();
        System.out.println(Arrays.toString(history));
        history[0] = 999;
        System.out.println(Arrays.toString(runner.getLateFeeHistory()));
    }
}