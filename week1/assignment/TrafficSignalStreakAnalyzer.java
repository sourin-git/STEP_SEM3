import java.util.Scanner;

public class TrafficSignalStreakAnalyzer {
    public static void findLongestStreak(String signalLog) {
        if (signalLog.isEmpty()) {
            System.out.println("No Signal Readings Found");
            return;
        }

        char longestColor = signalLog.charAt(0);
        int longestLength = 1;
        char currentColor = signalLog.charAt(0);
        int currentLength = 1;

        for (int index = 1; index < signalLog.length(); index++) {
            if (signalLog.charAt(index) == currentColor) {
                currentLength++;
            } else {
                currentColor = signalLog.charAt(index);
                currentLength = 1;
            }

            if (currentLength > longestLength) {
                longestColor = currentColor;
                longestLength = currentLength;
            }
        }

        System.out.println("Longest Streak: '" + longestColor + "' repeated " + longestLength + " times");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter signal log: ");
        String signalLog = scanner.nextLine().trim().toUpperCase();
        findLongestStreak(signalLog);
        scanner.close();
    }
}