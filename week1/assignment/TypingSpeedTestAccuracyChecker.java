import java.util.Scanner;

public class TypingSpeedTestAccuracyChecker {
    public static void checkTypingAccuracy(String original, String typed) {
        int comparedLength = Math.min(original.length(), typed.length());
        int matchedCharacters = 0;
        int firstMismatch = -1;

        for (int index = 0; index < comparedLength; index++) {
            if (original.charAt(index) == typed.charAt(index)) {
                matchedCharacters++;
            } else if (firstMismatch == -1) {
                firstMismatch = index;
            }
        }

        if (original.length() != typed.length() && firstMismatch == -1) {
            firstMismatch = comparedLength;
        }

        double accuracy = original.length() == 0 ? 100.0
                : matchedCharacters * 100.0 / original.length();
        System.out.printf("Matched: %d/%d | Accuracy: %.2f%%", matchedCharacters, original.length(), accuracy);

        if (firstMismatch == -1) {
            System.out.println(" | No Mismatches");
        } else if (firstMismatch >= original.length()) {
            System.out.println(" | First Mismatch at position " + (firstMismatch + 1) + " (extra character in typed text)");
        } else if (firstMismatch >= typed.length()) {
            System.out.println(" | First Mismatch at position " + (firstMismatch + 1) + " (missing character; expected '"
                    + original.charAt(firstMismatch) + "')");
        } else {
            System.out.println(" | First Mismatch at position " + (firstMismatch + 1) + " ('"
                    + original.charAt(firstMismatch) + "' vs '" + typed.charAt(firstMismatch) + "')");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter original passage: ");
        String original = scanner.nextLine();
        System.out.print("Enter typed text: ");
        String typed = scanner.nextLine();

        checkTypingAccuracy(original, typed);
        scanner.close();
    }
}