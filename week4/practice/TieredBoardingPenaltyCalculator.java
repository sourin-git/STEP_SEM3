public final class TieredBoardingPenaltyCalculator {
    private final double minimumPenaltyPercent;

    public TieredBoardingPenaltyCalculator(double minimumPenaltyPercent) {
        if (minimumPenaltyPercent < 0) {
            throw new IllegalArgumentException("Minimum penalty cannot be negative");
        }
        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }

    public final double calculatePenalty(double ticketFare, int minutesLate) {
        if (ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException("Fare and late minutes cannot be negative");
        }
        if (minutesLate == 0) {
            return 0;
        }

        int firstTierMinutes = Math.min(minutesLate, 5);
        int secondTierMinutes = Math.min(Math.max(minutesLate - 5, 0), 10);
        int thirdTierMinutes = Math.max(minutesLate - 15, 0);
        double tieredPenalty = ticketFare * (firstTierMinutes * 0.005
                + secondTierMinutes * 0.01 + thirdTierMinutes * 0.02);
        double flatFloor = ticketFare * minimumPenaltyPercent / 100;
        return Math.max(tieredPenalty, flatFloor);
    }

    public static void main(String[] args) {
        TieredBoardingPenaltyCalculator calculator = new TieredBoardingPenaltyCalculator(1);
        System.out.println("0 minutes: Rs " + calculator.calculatePenalty(1000, 0));
        System.out.println("1 minute: Rs " + calculator.calculatePenalty(1000, 1));
        System.out.println("16 minutes: Rs " + calculator.calculatePenalty(1000, 16));
    }
}