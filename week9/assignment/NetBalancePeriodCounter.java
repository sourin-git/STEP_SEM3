import java.util.HashMap;
import java.util.Map;

public class NetBalancePeriodCounter {
    public static int countPeriods(int[] transactions, int k) {
        Map<Integer, Integer> prefixCount = new HashMap<>();
        prefixCount.put(0, 1);

        int prefixSum = 0;
        int count = 0;

        for (int value : transactions) {
            prefixSum += value;
            int needed = prefixSum - k;
            count += prefixCount.getOrDefault(needed, 0);
            prefixCount.put(prefixSum, prefixCount.getOrDefault(prefixSum, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        int[] transactions1 = {3, 4, -7, 1, 3, 3, 1, -4};
        System.out.println("Sample 1: " + countPeriods(transactions1, 7));

        int[] transactions2 = {1, 2, 3};
        System.out.println("Sample 2: " + countPeriods(transactions2, 10));
    }
}
