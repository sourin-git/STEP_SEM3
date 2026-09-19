import java.util.Scanner;

public class ProductInventoryCsvParser {
    public static void parseInventoryRecord(String csvLine) {
        String[] fields = csvLine.split(",", -1);
        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }

        System.out.println("Product: " + fields[0].trim() + " | SKU: "
                + fields[1].trim() + " | Qty: " + fields[2].trim());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter inventory record: ");
        parseInventoryRecord(scanner.nextLine());
        scanner.close();
    }
}