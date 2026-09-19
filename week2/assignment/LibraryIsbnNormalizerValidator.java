import java.util.Scanner;

public class LibraryIsbnNormalizerValidator {
    public static String normalizeCode(String raw) {
        String code = raw.trim();
        if (code.length() < 3) {
            return code.toUpperCase();
        }
        return code.substring(0, 3).toUpperCase() + code.substring(3);
    }

    public static String validateAndFormat(String code) {
        if (code.length() != 13) {
            return "Invalid: wrong length";
        }

        for (int index = 0; index < 3; index++) {
            if (!Character.isLetter(code.charAt(index))) {
                return "Invalid: publisher code must be 3 letters";
            }
        }

        for (int index = 3; index < code.length(); index++) {
            if (!Character.isDigit(code.charAt(index))) {
                return "Invalid: code body must contain only digits";
            }
        }

        String publisherCode = code.substring(0, 3);
        String year = code.substring(3, 7);
        String catalog = code.substring(7);
        StringBuilder formatted = new StringBuilder();
        formatted.append("[").append(publisherCode).append("] YEAR: ")
                .append(year).append(" | CATALOG: ").append(catalog);
        return formatted.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ISBN-style code: ");
        String normalizedCode = normalizeCode(scanner.nextLine());
        System.out.println(validateAndFormat(normalizedCode));
        scanner.close();
    }
}