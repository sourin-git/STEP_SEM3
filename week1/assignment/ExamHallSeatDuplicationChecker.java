import java.util.Scanner;

public class ExamHallSeatDuplicationChecker {
    public static void checkDuplicateSeats(int[] seatNumbers) {
        boolean duplicateFound = false;

        for (int index = 0; index < seatNumbers.length; index++) {
            boolean alreadyPrinted = false;
            for (int previous = 0; previous < index; previous++) {
                if (seatNumbers[previous] == seatNumbers[index]) {
                    alreadyPrinted = true;
                    break;
                }
            }

            if (alreadyPrinted) {
                continue;
            }

            for (int next = index + 1; next < seatNumbers.length; next++) {
                if (seatNumbers[index] == seatNumbers[next]) {
                    System.out.println("Duplicate Seat Number Found: " + seatNumbers[index]);
                    duplicateFound = true;
                    break;
                }
            }
        }

        if (!duplicateFound) {
            System.out.println("No Duplicate Seats Found");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of seats: ");
        int numberOfSeats = scanner.nextInt();
        int[] seatNumbers = new int[numberOfSeats];

        for (int index = 0; index < seatNumbers.length; index++) {
            System.out.print("Enter seat number " + (index + 1) + ": ");
            seatNumbers[index] = scanner.nextInt();
        }

        checkDuplicateSeats(seatNumbers);
        scanner.close();
    }
}