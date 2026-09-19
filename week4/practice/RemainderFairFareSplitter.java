import java.util.Arrays;

public class RemainderFairFareSplitter {
    static class FareSplitter {
        private String tripId;
        private double totalFare;
        private int passengerCount;

        FareSplitter(String tripId, double totalFare, int passengerCount) {
            if (totalFare < 0 || passengerCount <= 0) {
                throw new IllegalArgumentException("Fare and passenger count must be valid");
            }
            this.tripId = tripId;
            this.totalFare = totalFare;
            this.passengerCount = passengerCount;
        }

        FareSplitter(String tripId, double totalFare) {
            this(tripId, totalFare, 1);
        }

        FareSplitter(String tripId) {
            this(tripId, 0, 2);
        }

        double[] fareBreakdown() {
            double[] shares = new double[passengerCount];
            long totalCents = Math.round(totalFare * 100);
            long baseCents = totalCents / passengerCount;
            long remainderCents = totalCents % passengerCount;

            for (int index = 0; index < shares.length; index++) {
                long cents = baseCents + (index == shares.length - 1 ? remainderCents : 0);
                shares[index] = cents / 100.0;
            }
            return shares;
        }

        boolean isConfirmationOverdue(int confirmed, int expected) {
            return confirmed < expected;
        }
    }

    public static void main(String[] args) {
        FareSplitter fullSplit = new FareSplitter("TRIP001", 100000, 3);
        FareSplitter provisionalSplit = new FareSplitter("TRIP003");
        System.out.println(Arrays.toString(fullSplit.fareBreakdown()));
        System.out.println(Arrays.toString(provisionalSplit.fareBreakdown()));
    }
}