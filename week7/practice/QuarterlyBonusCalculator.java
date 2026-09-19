public class QuarterlyBonusCalculator {
    interface Auditable {
        String auditRecord();
    }

    static abstract class StaffMember {
        private double baseSalary;
        protected double bonusRate;

        StaffMember(double baseSalary) {
            this(baseSalary, 0.10);
        }

        StaffMember(double baseSalary, double bonusRate) {
            if (baseSalary < 0) {
                throw new IllegalArgumentException("Salary cannot be negative");
            }
            this.baseSalary = baseSalary;
            this.bonusRate = bonusRate;
        }

        abstract double calculateBonus();

        double getSalary() {
            return baseSalary;
        }

        void setSalary(double baseSalary) {
            if (baseSalary >= 0) {
                this.baseSalary = baseSalary;
            }
        }
    }

    static class TeamLead extends StaffMember implements Auditable {
        private int teamSize;

        TeamLead(double baseSalary, int teamSize) {
            super(baseSalary);
            this.teamSize = teamSize;
        }

        TeamLead(double baseSalary, double bonusRate, int teamSize) {
            super(baseSalary, bonusRate);
            this.teamSize = teamSize;
        }

        @Override
        double calculateBonus() {
            return getSalary() * bonusRate;
        }

        @Override
        public String auditRecord() {
            return "TeamLead audit: " + teamSize + " team members, salary $" + getSalary();
        }
    }

    static String getAuditIfApplicable(StaffMember staffMember) {
        if (staffMember instanceof Auditable auditable) {
            return auditable.auditRecord();
        }
        return "No audit required";
    }

    public static void main(String[] args) {
        TeamLead defaultLead = new TeamLead(60000, 5);
        TeamLead customLead = new TeamLead(60000, 0.20, 5);
        System.out.println(defaultLead.calculateBonus());
        System.out.println(customLead.calculateBonus());
        defaultLead.setSalary(-5000);
        System.out.println(getAuditIfApplicable(defaultLead));
    }
}