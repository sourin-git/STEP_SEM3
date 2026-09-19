import java.util.Scanner;

public class AttendanceSystem {
    static class SrmStudent {
        private String name;
        private String regNo;
        private int attendance;

        SrmStudent(String name, String regNo, int attendance) {
            this.name = name;
            this.regNo = regNo;
            this.attendance = attendance;
        }

        void addAttendanceUpdate(int newAttendance) {
            attendance = newAttendance;
        }

        boolean isEligible() {
            return attendance >= 75;
        }

        // classAverage uses the complete student array, while isEligible depends on one student's state.
        static double classAverage(SrmStudent[] students) {
            int total = 0;
            for (SrmStudent student : students) {
                total += student.attendance;
            }
            return students.length == 0 ? 0 : (double) total / students.length;
        }

        void printAttendance() {
            System.out.printf("%s - %d%% - %s%n", name, attendance,
                    isEligible() ? "Eligible" : "Detained");
        }
    }

    public static void main(String[] args) {
        SrmStudent[] students = {
                new SrmStudent("Ravi", "RA231100301011", 82),
                new SrmStudent("Anitha", "RA231100301012", 68),
                new SrmStudent("Karthik", "RA231100301013", 91),
                new SrmStudent("Meera", "RA231100301014", 74),
                new SrmStudent("Suresh", "RA231100301015", 60)
        };

        students[3].addAttendanceUpdate(74);
        for (SrmStudent student : students) {
            student.printAttendance();
        }
        System.out.printf("Class average: %.1f%%%n", SrmStudent.classAverage(students));
    }
}