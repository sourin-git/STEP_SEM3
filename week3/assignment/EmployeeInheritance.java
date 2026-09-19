public class EmployeeInheritance {
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

        String getEmpName() {
            return empName;
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

    static class InternEmployee extends Employee {
        private double stipendCap;

        InternEmployee(String empId, String empName, double salary, double stipendCap) {
            super(empId, empName, salary);
            this.stipendCap = stipendCap;
        }

        double effectiveSalary() {
            return Math.min(getSalary(), stipendCap);
        }
    }

    public static void main(String[] args) {
        Employee[] employees = {
                new Employee("E101", "Plain", 40000),
                new ManagerEmployee("E102", "Manager", 70000, 8000),
                new InternEmployee("E103", "Intern", 12000, 10000)
        };

        for (Employee employee : employees) {
            if (employee instanceof ManagerEmployee manager) {
                System.out.printf("Manager effective pay: Rs %.1f%n", manager.effectiveSalary());
            } else if (employee instanceof InternEmployee intern) {
                System.out.printf("Intern effective pay: Rs %.1f%n", intern.effectiveSalary());
            } else {
                System.out.printf("Plain employee pay: Rs %.1f%n", employee.getSalary());
            }
        }
    }
}