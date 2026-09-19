public class StaticInstanceBoundary {
    static class BrokenSrmStudent {
        static String name;
        static String regNo;
        static int attendance;

        BrokenSrmStudent(String name, String regNo, int attendance) {
            BrokenSrmStudent.name = name;
            BrokenSrmStudent.regNo = regNo;
            BrokenSrmStudent.attendance = attendance;
        }
    }

    static class SrmStudent {
        private String name;
        private String regNo;
        private int attendance;
        private static String university = "SRM Institute";
        private static int admissionCount;

        SrmStudent(String name, int attendance) {
            this.name = name;
            this.attendance = attendance;
            admissionCount++;
            this.regNo = String.format("RA231100301%03d", admissionCount + 10);
        }

        void printIdCard() {
            System.out.println(name + " | " + regNo + " | " + university + " | Attendance: " + attendance + "%");
        }

        static void printTotalAdmissions() {
            System.out.println("Students admitted so far: " + admissionCount);
        }
    }

    public static void main(String[] args) {
        // Static name, regNo, and attendance are wrong because every student shares one value for each field.
        System.out.println("Broken version:");
        BrokenSrmStudent brokenRavi = new BrokenSrmStudent("Ravi", "RA231100301011", 82);
        BrokenSrmStudent brokenMeera = new BrokenSrmStudent("Meera", "RA231100301012", 74);
        System.out.println(brokenRavi.name);
        System.out.println(brokenMeera.name);

        System.out.println("Fixed version:");
        SrmStudent ravi = new SrmStudent("Ravi", 82);
        SrmStudent meera = new SrmStudent("Meera", 74);
        ravi.printIdCard();
        meera.printIdCard();
        SrmStudent.printTotalAdmissions();
    }
}