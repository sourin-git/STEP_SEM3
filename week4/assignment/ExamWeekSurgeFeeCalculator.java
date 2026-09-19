public final class ExamWeekSurgeFeeCalculator {
    private final double minimumSurgePercent;

    public ExamWeekSurgeFeeCalculator(double minimumSurgePercent) {
        if (minimumSurgePercent < 0) {
            throw new IllegalArgumentException("Minimum surge percent cannot be negative");
        }
        this.minimumSurgePercent = minimumSurgePercent;
    }

    public final double calculateSurgeFee(double orderValue, int delayMinutes) {
        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("Order value and delay cannot be negative");
        }
        if (delayMinutes == 0) {
            return 0;
        }

        int firstTier = Math.min(delayMinutes, 5);
        int secondTier = Math.min(Math.max(delayMinutes - 5, 0), 10);
        int thirdTier = Math.max(delayMinutes - 15, 0);
        double tieredFee = orderValue * (firstTier * 0.005 + secondTier * 0.01 + thirdTier * 0.02);
        double minimumFee = orderValue * minimumSurgePercent / 100;
        return Math.max(tieredFee, minimumFee);
    }

    public static void main(String[] args) {
        ExamWeekSurgeFeeCalculator calculator = new ExamWeekSurgeFeeCalculator(1);
        System.out.println("0 minutes: Rs " + calculator.calculateSurgeFee(500, 0));
        System.out.println("1 minute: Rs " + calculator.calculateSurgeFee(500, 1));
        System.out.println("16 minutes: Rs " + calculator.calculateSurgeFee(500, 16));
    }
}