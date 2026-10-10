public class MaximumSumSubarrayFixedSizeK {
    public static int maxSumSubarray(int[] sales, int k) {
        int windowSum = 0;

        for (int i = 0; i < k; i++) {
            windowSum += sales[i];
        }

        int maxSum = windowSum;

        for (int i = k; i < sales.length; i++) {
            windowSum += sales[i] - sales[i - k];
            if (windowSum > maxSum) {
                maxSum = windowSum;
            }
        }

        return maxSum;
    }

    public static void main(String[] args) {
        int[] sales = {2, 1, 5, 1, 3, 2};
        int k = 3;
        System.out.println("Maximum sum for k = " + k + " is " + maxSumSubarray(sales, k));
    }
}
