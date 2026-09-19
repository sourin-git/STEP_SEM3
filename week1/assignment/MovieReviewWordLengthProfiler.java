import java.util.Scanner;

public class MovieReviewWordLengthProfiler {
    public static void classifyWordLengths(String review) {
        int shortWords = 0;
        int mediumWords = 0;
        int longWords = 0;
        String trimmedReview = review.trim();

        if (!trimmedReview.isEmpty()) {
            String[] words = trimmedReview.split("\\s+");
            for (String word : words) {
                String lettersOnly = word.replaceAll("[^a-zA-Z]", "");
                int wordLength = lettersOnly.length();

                if (wordLength <= 4) {
                    shortWords++;
                } else if (wordLength <= 8) {
                    mediumWords++;
                } else {
                    longWords++;
                }
            }
        }

        System.out.println("Short: " + shortWords + " | Medium: " + mediumWords + " | Long: " + longWords);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter movie review: ");
        String review = scanner.nextLine();
        classifyWordLengths(review);
        scanner.close();
    }
}