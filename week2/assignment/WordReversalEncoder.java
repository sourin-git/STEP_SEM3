import java.util.Scanner;

public class WordReversalEncoder {
    public static String reverseEachWord(String sentence) {
        String[] words = sentence.split(" ");
        StringBuilder result = new StringBuilder();

        for (int index = 0; index < words.length; index++) {
            StringBuilder reversedWord = new StringBuilder();
            for (int characterIndex = words[index].length() - 1; characterIndex >= 0; characterIndex--) {
                reversedWord.append(words[index].charAt(characterIndex));
            }
            result.append(reversedWord);
            if (index < words.length - 1) {
                result.append(" ");
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter sentence: ");
        System.out.println(reverseEachWord(scanner.nextLine()));
        scanner.close();
    }
}