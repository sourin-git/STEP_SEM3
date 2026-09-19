public class NightlyMultiKitchenReconciliation {
    static class DeliveryAccount {
        private static String campusName;
        private String studentId;
        private double orderValue;

        static {
            campusName = "SRM Campus Kitchens";
        }

        DeliveryAccount(String studentId, double orderValue) {
            if (orderValue < 0) {
                throw new IllegalArgumentException("Order value cannot be negative");
            }
            this.studentId = studentId;
            this.orderValue = orderValue;
        }

        DeliveryAccount(String studentId) {
            this(studentId, 0);
        }

        final double calculateSurgeFee(int delayMinutes) {
            if (delayMinutes < 0) {
                throw new IllegalArgumentException("Delay cannot be negative");
            }
            int firstTier = Math.min(delayMinutes, 5);
            int secondTier = Math.min(Math.max(delayMinutes - 5, 0), 10);
            int thirdTier = Math.max(delayMinutes - 15, 0);
            return orderValue * (firstTier * 0.005 + secondTier * 0.01 + thirdTier * 0.02);
        }

        void processAccount(DeliveryAccount account, double amount, int delayMinutes) {
            if (amount < 0) {
                throw new IllegalArgumentException("Payment amount cannot be negative");
            }
        }
    }

    static class PremiumDeliveryAccount extends DeliveryAccount {
        PremiumDeliveryAccount(String studentId, double orderValue) {
            super(studentId, orderValue);
        }

        double premiumSurgeFee(int delayMinutes) {
            return calculateSurgeFee(delayMinutes) * 0.5;
        }
    }

    static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        if (accounts == null || amounts == null || delayMinutesArray == null
                || accounts.length != amounts.length || accounts.length != delayMinutesArray.length) {
            throw new IllegalArgumentException("Batch arrays must be non-null and have matching lengths");
        }

        int processed = 0;
        int nullSkipped = 0;
        int premium = 0;
        double totalFees = 0;
        for (int index = 0; index < accounts.length; index++) {
            DeliveryAccount account = accounts[index];
            if (account == null) {
                nullSkipped++;
                continue;
            }
            account.processAccount(account, amounts[index], delayMinutesArray[index]);
            totalFees += account instanceof PremiumDeliveryAccount premiumAccount
                    ? premiumAccount.premiumSurgeFee(delayMinutesArray[index])
                    : account.calculateSurgeFee(delayMinutesArray[index]);
            processed++;
            if (account instanceof PremiumDeliveryAccount) {
                premium++;
            }
        }

        System.out.printf("%d processed | %d null skipped | %d premium | %d regular | grand total surge fees = Rs %.1f%n",
                processed, nullSkipped, premium, processed - premium, totalFees);
    }

    public static void main(String[] args) {
        DeliveryAccount[] accounts = {
                new PremiumDeliveryAccount("STU001", 500), null,
                new DeliveryAccount("STU002", 300)
        };
        processBatch(accounts, new double[]{500, 400, 300}, new int[]{10, 5, 0});
    }
}