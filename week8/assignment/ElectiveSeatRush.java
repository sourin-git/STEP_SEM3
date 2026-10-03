import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class ElectiveSeatRush {
    abstract static class Student {
        private final String name;
        private int currentCredits;

        Student(String name, int currentCredits) {
            this.name = name;
            this.currentCredits = currentCredits;
        }

        public String getName() {
            return name;
        }

        public int getCurrentCredits() {
            return currentCredits;
        }

        public void addCredits(int credits) {
            this.currentCredits += credits;
        }

        public void removeCredits(int credits) {
            this.currentCredits -= credits;
        }

        public abstract int getCreditLimit();
    }

    static class RegularStudent extends Student {
        RegularStudent(String name, int currentCredits) {
            super(name, currentCredits);
        }

        @Override
        public int getCreditLimit() {
            return 24;
        }
    }

    static class HonorsStudent extends Student {
        HonorsStudent(String name, int currentCredits) {
            super(name, currentCredits);
        }

        @Override
        public int getCreditLimit() {
            return 28;
        }
    }

    static class ExchangeStudent extends Student {
        ExchangeStudent(String name, int currentCredits) {
            super(name, currentCredits);
        }

        @Override
        public int getCreditLimit() {
            return 20;
        }
    }

    static class Elective {
        private final String name;
        private final int creditValue;
        private final int capacity;
        private final List<Student> enrolled = new ArrayList<>();
        private final Deque<Student> waitlist = new ArrayDeque<>();

        Elective(String name, int creditValue, int capacity) {
            this.name = name;
            this.creditValue = creditValue;
            this.capacity = capacity;
        }

        public boolean enrollStudent(Student student) {
            if (isEnrolled(student) || isWaitlisted(student)) {
                System.out.println("Enrollment failed: " + student.getName() + " is already enrolled in or waiting for " + name + ".");
                return false;
            }
            if (student.getCurrentCredits() + creditValue > student.getCreditLimit()) {
                System.out.println("Enrollment failed: " + student.getName() + " would exceed the "
                        + student.getClass().getSimpleName().replace("Student", "") + " credit limit ("
                        + (student.getCurrentCredits() + creditValue) + "/" + student.getCreditLimit() + ").");
                return false;
            }
            if (enrolled.size() < capacity) {
                enrolled.add(student);
                student.addCredits(creditValue);
                System.out.println(student.getName() + " enrolled in " + name + " (credits: " + student.getCurrentCredits() + "/" + student.getCreditLimit() + ").");
                return true;
            }
            waitlist.addLast(student);
            System.out.println(student.getName() + " added to waitlist (position " + waitlist.size() + ").");
            return true;
        }

        public void dropStudent(Student student) {
            if (!enrolled.remove(student)) {
                System.out.println(student.getName() + " is not enrolled in " + name + ".");
                return;
            }
            student.removeCredits(creditValue);
            System.out.println(student.getName() + " dropped " + name + " (credits: " + student.getCurrentCredits() + "/" + student.getCreditLimit() + ").");
            promoteNextEligibleStudent();
        }

        private boolean isEnrolled(Student student) {
            return enrolled.contains(student);
        }

        private boolean isWaitlisted(Student student) {
            return waitlist.contains(student);
        }

        private void promoteNextEligibleStudent() {
            if (enrolled.size() >= capacity || waitlist.isEmpty()) {
                return;
            }
            Student next = null;
            for (Student student : waitlist) {
                if (student.getCurrentCredits() + creditValue <= student.getCreditLimit()) {
                    next = student;
                    break;
                }
            }
            if (next == null) {
                return;
            }
            waitlist.remove(next);
            enrolled.add(next);
            next.addCredits(creditValue);
            System.out.println(next.getName() + " promoted from waitlist and enrolled in " + name + " (credits: " + next.getCurrentCredits() + "/" + next.getCreditLimit() + ").");
        }
    }

    public static void main(String[] args) {
        Elective elective = new Elective("Cloud Computing", 4, 2);

        Student asha = new RegularStudent("Asha", 20);
        Student ravi = new HonorsStudent("Ravi", 22);
        Student neha = new ExchangeStudent("Neha", 12);
        Student kiran = new RegularStudent("Kiran", 22);

        elective.enrollStudent(asha);
        elective.enrollStudent(ravi);
        elective.enrollStudent(neha);
        elective.enrollStudent(kiran);
        elective.dropStudent(asha);
    }
}
