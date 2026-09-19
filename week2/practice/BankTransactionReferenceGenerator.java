import java.util.Scanner;

public class BankTransactionReferenceGenerator {
    public static String normalizeReference(String raw) {
        String reference = raw.trim();
        if (reference.length() < 3) {
            return reference.toUpperCase();
        }
        return reference.substring(0, 3).toUpperCase() + reference.substring(3);
    }

    public static String validateAndFormat(String reference) {
        if (reference.length() != 14) {
            return "Invalid: wrong length";
        }

        for (int index = 0; index < 3; index++) {
            if (!Character.isLetter(reference.charAt(index))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        for (int index = 3; index < reference.length(); index++) {
            if (!Character.isDigit(reference.charAt(index))) {
                return "Invalid: reference body must contain only digits";
            }
        }

        String bankCode = reference.substring(0, 3);
        String date = reference.substring(3, 9);
        String sequence = reference.substring(9);
        StringBuilder formatted = new StringBuilder();
        formatted.append("[").append(bankCode).append("] DATE: ")
                .append(date, 0, 2).append("/")
                .append(date, 2, 4).append("/")
                .append(date, 4, 6).append(" | SEQ: ")
                .append(sequence);
        return formatted.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter transaction reference: ");
        String normalizedReference = normalizeReference(scanner.nextLine());
        System.out.println(validateAndFormat(normalizedReference));
        scanner.close();
    }
}