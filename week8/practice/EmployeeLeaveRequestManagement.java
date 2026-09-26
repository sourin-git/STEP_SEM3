import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EmployeeLeaveRequestManagement {
    interface LeavePolicy {
        boolean allows(long requestedDays);
    }

    static class MaximumDaysPolicy implements LeavePolicy {
        private final long maximumDays;
        MaximumDaysPolicy(long maximumDays) { this.maximumDays = maximumDays; }
        public boolean allows(long requestedDays) { return requestedDays > 0 && requestedDays <= maximumDays; }
    }

    static abstract class Employee {
        private final String name;
        private final LeavePolicy leavePolicy;
        Employee(String name, LeavePolicy leavePolicy) {
            this.name = name;
            this.leavePolicy = leavePolicy;
        }
        String getName() { return name; }
        boolean canRequest(long days) { return leavePolicy.allows(days); }
        LeaveRequest submitLeave(LocalDate start, LocalDate end) {
            long days = ChronoUnit.DAYS.between(start, end) + 1;
            if (!canRequest(days)) throw new IllegalArgumentException("Leave policy does not allow this request");
            LeaveRequest request = new LeaveRequest(this, start, end);
            System.out.printf("Leave request submitted by %s for %s to %s. Status: %s.%n",
                    name, start, end, request.status);
            return request;
        }
    }

    static class FullTimeEmployee extends Employee {
        FullTimeEmployee(String name) { super(name, new MaximumDaysPolicy(30)); }
    }

    static class PartTimeEmployee extends Employee {
        PartTimeEmployee(String name) { super(name, new MaximumDaysPolicy(14)); }
    }

    static class ContractEmployee extends Employee {
        ContractEmployee(String name) { super(name, new MaximumDaysPolicy(7)); }
    }

    enum Status { PENDING, APPROVED, REJECTED }

    static class LeaveRequest {
        private final Employee employee;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private Status status = Status.PENDING;
        LeaveRequest(Employee employee, LocalDate startDate, LocalDate endDate) {
            this.employee = employee;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    static class ApprovalService {
        void review(LeaveRequest request, Status decision) {
            if (request.status != Status.PENDING || decision == Status.PENDING) {
                System.out.println("Cannot change status: " + request.status + " request cannot revert to Pending.");
                return;
            }
            request.status = decision;
            System.out.printf("Leave request for %s %s. Status: %s.%n", request.employee.getName(),
                    decision == Status.APPROVED ? "approved" : "rejected", request.status);
        }
    }

    public static void main(String[] args) {
        ApprovalService approvalService = new ApprovalService();
        Employee john = new FullTimeEmployee("John Doe");
        Employee jane = new PartTimeEmployee("Jane Smith");
        LeaveRequest johnRequest = john.submitLeave(LocalDate.parse("2024-10-10"), LocalDate.parse("2024-10-12"));
        approvalService.review(johnRequest, Status.APPROVED);
        jane.submitLeave(LocalDate.parse("2024-11-01"), LocalDate.parse("2024-11-05"));
        approvalService.review(johnRequest, Status.PENDING);
    }
}