import java.util.Scanner;

public class PalindromeChecker {
    public static boolean isPalindromeIterative(String text) {
        int left = 0;
        int right = text.length() - 1;

        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static boolean isPalindromeRecursive(String text) {
        return isPalindromeRecursive(text, 0, text.length() - 1);
    }

    private static boolean isPalindromeRecursive(String text, int left, int right) {
        if (left >= right) {
            return true;
        }
        if (text.charAt(left) != text.charAt(right)) {
            return false;
        }
        return isPalindromeRecursive(text, left + 1, right - 1);
    }

    public static boolean isPalindromeArrayReversal(String text) {
        char[] characters = text.toCharArray();
        for (int left = 0, right = characters.length - 1; left < right; left++, right--) {
            char temporary = characters[left];
            characters[left] = characters[right];
            characters[right] = temporary;
        }
        return text.equals(new String(characters));
    }

    private static String formatResult(boolean palindrome) {
        return palindrome ? "Palindrome" : "Not Palindrome";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter text: ");
        String text = scanner.nextLine();

        boolean iterativeResult = isPalindromeIterative(text);
        boolean recursiveResult = isPalindromeRecursive(text);
        boolean arrayReversalResult = isPalindromeArrayReversal(text);

        System.out.println("Iterative: " + formatResult(iterativeResult));
        System.out.println("Recursive: " + formatResult(recursiveResult));
        System.out.println("Array Reversal: " + formatResult(arrayReversalResult));
        System.out.println("All approaches agree: "
                + (iterativeResult == recursiveResult && recursiveResult == arrayReversalResult));
        scanner.close();
    }
}