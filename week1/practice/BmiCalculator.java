import java.util.Scanner;

public class BmiCalculator {
    public static String getBmiStatus(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Normal";
        } else if (bmi < 30) {
            return "Overweight";
        }
        return "Obese";
    }

    public static void printWellnessReport(double[] heights, double[] weights) {
        System.out.printf("%-10s %-15s %-15s %-10s %-15s%n",
                "Person", "Height (m)", "Weight (kg)", "BMI", "Status");

        for (int index = 0; index < heights.length; index++) {
            double bmi = weights[index] / (heights[index] * heights[index]);
            System.out.printf("%-10d %-15.2f %-15.2f %-10.2f %-15s%n",
                    index + 1, heights[index], weights[index], bmi, getBmiStatus(bmi));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of people: ");
        int numberOfPeople = scanner.nextInt();
        double[] heights = new double[numberOfPeople];
        double[] weights = new double[numberOfPeople];

        for (int index = 0; index < numberOfPeople; index++) {
            do {
                System.out.print("Enter height in meters for person " + (index + 1) + ": ");
                heights[index] = scanner.nextDouble();
            } while (heights[index] <= 0);

            do {
                System.out.print("Enter weight in kilograms for person " + (index + 1) + ": ");
                weights[index] = scanner.nextDouble();
            } while (weights[index] <= 0);
        }

        System.out.println("\nWellness Report");
        printWellnessReport(heights, weights);
        scanner.close();
    }
}