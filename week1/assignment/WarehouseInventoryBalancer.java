import java.util.Scanner;

public class WarehouseInventoryBalancer {
    public static void analyzeInventory(int[] sectionA, int[] sectionB) {
        int sectionATotal = 0;
        int sectionBTotal = 0;
        int highestQuantity = Integer.MIN_VALUE;
        String highestSection = "";
        int highestIndex = -1;

        for (int index = 0; index < sectionA.length; index++) {
            sectionATotal += sectionA[index];
            if (sectionA[index] > highestQuantity) {
                highestQuantity = sectionA[index];
                highestSection = "Section A";
                highestIndex = index;
            }
        }

        for (int index = 0; index < sectionB.length; index++) {
            sectionBTotal += sectionB[index];
            if (sectionB[index] > highestQuantity) {
                highestQuantity = sectionB[index];
                highestSection = "Section B";
                highestIndex = index;
            }
        }

        String status = sectionATotal == sectionBTotal ? "Balanced" : "Not Balanced";
        System.out.printf("Section A Total: %d | Section B Total: %d | Status: %s | Highest Quantity: %d (%s, Item %d)%n",
                sectionATotal, sectionBTotal, status, highestQuantity, highestSection, highestIndex + 1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of items in each section: ");
        int numberOfItems = scanner.nextInt();
        int[] sectionA = new int[numberOfItems];
        int[] sectionB = new int[numberOfItems];

        System.out.println("Enter quantities for Section A:");
        for (int index = 0; index < numberOfItems; index++) {
            sectionA[index] = scanner.nextInt();
        }

        System.out.println("Enter quantities for Section B:");
        for (int index = 0; index < numberOfItems; index++) {
            sectionB[index] = scanner.nextInt();
        }

        analyzeInventory(sectionA, sectionB);
        scanner.close();
    }
}