import java.util.Scanner;

public class CsvStudentRecordParser {
    public static void parseStudentRecord(String csvLine) {
        String[] fields = csvLine.split(",", -1);
        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }

        System.out.println("Name: " + fields[0].trim() + " | Roll No: "
                + fields[1].trim() + " | Dept: " + fields[2].trim());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter CSV student record: ");
        parseStudentRecord(scanner.nextLine());
        scanner.close();
    }
}