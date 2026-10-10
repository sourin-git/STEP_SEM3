public class LongestBudgetFriendlyStreak {
    public static int[] longestStreak(int[] costs, int budget) {
        int left = 0;
        int currentSum = 0;
        int bestLength = 0;
        int bestStart = -1;

        for (int right = 0; right < costs.length; right++) {
            currentSum += costs[right];

            while (currentSum > budget && left <= right) {
                currentSum -= costs[left];
                left++;
            }

            int length = right - left + 1;
            if (currentSum <= budget && length > bestLength) {
                bestLength = length;
                bestStart = left;
            }
        }

        if (bestLength == 0) {
            return new int[] {0, -1};
        }

        return new int[] {bestLength, bestStart};
    }

    public static void main(String[] args) {
        int[] costs1 = {4, 2, 1, 7, 3, 1, 2, 1, 5};
        int budget1 = 8;
        int[] result1 = longestStreak(costs1, budget1);
        System.out.println("Sample 1: " + result1[0] + ", " + result1[1]);

        int[] costs2 = {9, 10};
        int budget2 = 8;
        int[] result2 = longestStreak(costs2, budget2);
        System.out.println("Sample 2: " + result2[0] + ", " + result2[1]);
    }
}
