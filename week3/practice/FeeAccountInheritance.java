import java.util.Scanner;

public class FeeAccountInheritance {
    static class FeeAccount {
        private String regNo;
        private double totalFee;
        private double amountPaid;

        FeeAccount(String regNo, double totalFee) {
            this.regNo = regNo;
            this.totalFee = totalFee;
        }

        void pay(double amount) {
            if (amount > 0) {
                amountPaid += amount;
            }
        }

        double getDue() {
            return totalFee - amountPaid;
        }

        String getRegNo() {
            return regNo;
        }
    }

    static class HostelFeeAccount extends FeeAccount {
        HostelFeeAccount(String regNo, double totalFee) {
            super(regNo, totalFee);
        }

        void payInTwoInstallments(double amount) {
            pay(amount / 2);
            pay(amount / 2);
        }
    }

    static class ScholarshipFeeAccount extends FeeAccount {
        private double scholarshipPercent;

        ScholarshipFeeAccount(String regNo, double totalFee, double scholarshipPercent) {
            super(regNo, totalFee);
            this.scholarshipPercent = scholarshipPercent;
        }

        double effectiveDue() {
            return getDue() * (1 - scholarshipPercent / 100);
        }
    }

    public static void main(String[] args) {
        FeeAccount plain = new FeeAccount("RA101", 150000);
        HostelFeeAccount hostel = new HostelFeeAccount("RA102", 200000);
        ScholarshipFeeAccount scholarship = new ScholarshipFeeAccount("RA103", 180000, 20);

        plain.pay(150000);
        hostel.payInTwoInstallments(60000);

        FeeAccount[] accounts = {plain, hostel, scholarship};
        for (FeeAccount account : accounts) {
            if (account instanceof HostelFeeAccount hostelAccount) {
                System.out.printf("Hostel account due: Rs %.1f%n", hostelAccount.getDue());
            } else if (account instanceof ScholarshipFeeAccount scholarshipAccount) {
                System.out.printf("Scholarship account effective due: Rs %.1f%n",
                        scholarshipAccount.effectiveDue());
            } else {
                System.out.printf("Plain account due: Rs %.1f%n", account.getDue());
            }
        }
    }
}