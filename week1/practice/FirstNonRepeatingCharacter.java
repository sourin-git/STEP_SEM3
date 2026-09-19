import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class FirstNonRepeatingCharacter {
    public static Character findFirstNonRepeatingChar(String text) {
        Map<Character, Integer> frequencies = new LinkedHashMap<>();
        for (char character : text.toCharArray()) {
            frequencies.put(character, frequencies.getOrDefault(character, 0) + 1);
        }

        for (char character : text.toCharArray()) {
            if (frequencies.get(character) == 1) {
                return character;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter text: ");
        String text = scanner.nextLine();
        Character result = findFirstNonRepeatingChar(text);

        if (result == null) {
            System.out.println("No Non-Repeating Character Found");
        } else {
            System.out.println("First Non-Repeating Character: '" + result + "'");
        }
        scanner.close();
    }
}