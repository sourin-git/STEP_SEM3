import java.util.Scanner;

public class VowelConsonantCounter {
    public static void countVowelsAndConsonants(String text) {
        int vowels = 0;
        int consonants = 0;

        for (int index = 0; index < text.length(); index++) {
            char character = Character.toLowerCase(text.charAt(index));
            if (character == 'a' || character == 'e' || character == 'i'
                    || character == 'o' || character == 'u') {
                vowels++;
            } else if (character >= 'a' && character <= 'z') {
                consonants++;
            }
        }

        System.out.println("Vowels: " + vowels + " | Consonants: " + consonants);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter text: ");
        countVowelsAndConsonants(scanner.nextLine());
        scanner.close();
    }
}