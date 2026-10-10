public class ExamScoreBandCounter {
    public static int firstGreaterOrEqual(int[] scores, int target) {
        int low = 0;
        int high = scores.length;

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (scores[mid] >= target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    public static int firstGreater(int[] scores, int target) {
        int low = 0;
        int high = scores.length;

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (scores[mid] > target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    public static int countInBand(int[] scores, int low, int high) {
        int start = firstGreaterOrEqual(scores, low);
        int end = firstGreater(scores, high);
        return end - start;
    }

    public static void main(String[] args) {
        int[] scores = {35, 42, 42, 50, 58, 58, 58, 63, 71, 88};
        System.out.println("Sample 1: " + countInBand(scores, 42, 58));
        System.out.println("Sample 2: " + countInBand(scores, 90, 100));
    }
}
