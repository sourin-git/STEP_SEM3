public class HrParkingMiniSystem {
    static class Employee {
        private String empId;
        private String empName;
        private double salary;

        Employee(String empId, String empName, double salary) {
            this.empId = empId;
            this.empName = empName;
            this.salary = salary;
        }

        double getSalary() {
            return salary;
        }
    }

    static class ManagerEmployee extends Employee {
        private double teamBonus;

        ManagerEmployee(String empId, String empName, double salary, double teamBonus) {
            super(empId, empName, salary);
            this.teamBonus = teamBonus;
        }

        double effectiveSalary() {
            return getSalary() + teamBonus;
        }
    }

    static class ParkingSlot {
        private String slotNo;
        private int capacity;
        private int occupiedCount;

        ParkingSlot(String slotNo, int capacity) {
            this.slotNo = slotNo;
            this.capacity = capacity;
        }

        boolean allot() {
            if (occupiedCount >= capacity) {
                return false;
            }
            occupiedCount++;
            return true;
        }
    }

    static class CompanyEmployeeRecord {
        static int totalRecords;
        private String name;
        private String empId;
        private Employee employee;
        private ParkingSlot slot;

        CompanyEmployeeRecord(String name, String empId, Employee employee) {
            this.name = name;
            this.empId = empId;
            this.employee = employee;
            totalRecords++;
        }

        void assignSlot(ParkingSlot slot) {
            if (slot != null && slot.allot()) {
                this.slot = slot;
            }
        }

        String fullProfile() {
            double pay = employee instanceof ManagerEmployee manager
                    ? manager.effectiveSalary() : employee.getSalary();
            String slotStatus = slot == null ? "no parking assigned" : slot.slotNo;
            return String.format("%s | Pay: Rs %.1f | Slot: %s", name, pay, slotStatus);
        }
    }

    public static void main(String[] args) {
        ParkingSlot firstSlot = new ParkingSlot("A1", 1);
        ParkingSlot secondSlot = new ParkingSlot("A2", 1);
        CompanyEmployeeRecord divya = new CompanyEmployeeRecord("Divya", "E101",
                new ManagerEmployee("E101", "Divya", 70000, 8000));
        CompanyEmployeeRecord karan = new CompanyEmployeeRecord("Karan", "E102",
                new Employee("E102", "Karan", 40000));
        CompanyEmployeeRecord meera = new CompanyEmployeeRecord("Meera", "E103",
                new Employee("E103", "Meera", 10000));

        divya.assignSlot(firstSlot);
        karan.assignSlot(secondSlot);

        System.out.println(divya.fullProfile());
        System.out.println(karan.fullProfile());
        System.out.println(meera.fullProfile());
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}