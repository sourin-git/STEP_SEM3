import java.util.Scanner;

public class ReverseCustomerName {
    public static String reverseCustomerName(String customerName) {
        char[] characters = customerName.toCharArray();
        for (int left = 0, right = characters.length - 1; left < right; left++, right--) {
            char temporary = characters[left];
            characters[left] = characters[right];
            characters[right] = temporary;
        }
        return new String(characters);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        System.out.println("Original Name: " + customerName);
        System.out.println("Reversed Name: " + reverseCustomerName(customerName));
        scanner.close();
    }
}