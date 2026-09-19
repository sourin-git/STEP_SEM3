public class CollegeFeeHostelSystem {
    static class FeeAccount {
        private double totalFee;
        private double amountPaid;

        FeeAccount(double totalFee) {
            this.totalFee = totalFee;
        }

        void pay(double amount) {
            if (amount > 0) {
                amountPaid += amount;
            } else {
                System.out.println("Payment rejected: amount must be positive.");
            }
        }

        double getDue() {
            return totalFee - amountPaid;
        }
    }

    static class HostelFeeAccount extends FeeAccount {
        HostelFeeAccount(double totalFee) {
            super(totalFee);
        }

        void payInTwoInstallments(double amount) {
            pay(amount / 2);
            pay(amount / 2);
        }
    }

    static class HostelRoom {
        private String roomNo;
        private int beds;
        private int occupied;

        HostelRoom(String roomNo, int beds) {
            this.roomNo = roomNo;
            this.beds = beds;
        }

        boolean allot() {
            if (occupied >= beds) {
                return false;
            }
            occupied++;
            return true;
        }
    }

    static class SrmStudent {
        private static int totalStudents;
        private String name;
        private String regNo;
        private HostelFeeAccount feeAccount;
        private HostelRoom room;

        SrmStudent(String name, String regNo, HostelFeeAccount feeAccount) {
            this.name = name;
            this.regNo = regNo;
            this.feeAccount = feeAccount;
            totalStudents++;
        }

        void assignRoom(HostelRoom room) {
            if (room != null && room.allot()) {
                this.room = room;
            }
        }

        String fullStatus() {
            String roomStatus = room == null ? "unallotted" : room.roomNo;
            return String.format("%s | Due: Rs %.1f | Room: %s", name, feeAccount.getDue(), roomStatus);
        }
    }

    public static void main(String[] args) {
        HostelRoom firstRoom = new HostelRoom("C-214", 1);
        HostelRoom secondRoom = new HostelRoom("C-507", 1);
        SrmStudent ravi = new SrmStudent("Ravi", "RA231100301011", new HostelFeeAccount(200000));
        SrmStudent anitha = new SrmStudent("Anitha", "RA231100301012", new HostelFeeAccount(200000));
        SrmStudent karthik = new SrmStudent("Karthik", "RA231100301013", new HostelFeeAccount(200000));

        ravi.assignRoom(firstRoom);
        anitha.assignRoom(secondRoom);
        ravi.feeAccount.pay(60000);
        anitha.feeAccount.pay(20000);
        karthik.feeAccount.pay(-1000);

        System.out.println(ravi.fullStatus());
        System.out.println(anitha.fullStatus());
        System.out.println(karthik.fullStatus());
        System.out.println("Total students: " + SrmStudent.totalStudents);
    }
}