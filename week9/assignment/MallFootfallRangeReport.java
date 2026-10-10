import java.util.ArrayList;
import java.util.List;

public class MallFootfallRangeReport {
    public static int[] buildPrefix(int[] visitors) {
        int[] prefix = new int[visitors.length + 1];
        for (int i = 0; i < visitors.length; i++) {
            prefix[i + 1] = prefix[i] + visitors[i];
        }
        return prefix;
    }

    public static List<Integer> footfallReport(int[] visitors, int[][] queries) {
        int[] prefix = buildPrefix(visitors);
        List<Integer> result = new ArrayList<>();

        for (int[] query : queries) {
            int start = query[0];
            int end = query[1];
            int total = prefix[end + 1] - prefix[start];
            result.add(total);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] visitors = {12, 7, 3, 9, 15, 4, 8};
        int[][] queries = {{0, 2}, {2, 5}, {4, 6}, {3, 3}};

        System.out.println(footfallReport(visitors, queries));
    }
}
