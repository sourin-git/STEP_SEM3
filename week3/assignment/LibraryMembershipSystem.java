public class LibraryMembershipSystem {
    static class BrokenLibraryMember {
        static String name;
        static String memberId;
        static int booksIssued;

        BrokenLibraryMember(String name, String memberId, int booksIssued) {
            BrokenLibraryMember.name = name;
            BrokenLibraryMember.memberId = memberId;
            BrokenLibraryMember.booksIssued = booksIssued;
        }
    }

    static class LibraryMember {
        private String name;
        private String memberId;
        private int booksIssued;
        private static String libraryName = "Central Library";
        private static int memberCount;

        LibraryMember(String name, int booksIssued) {
            this.name = name;
            this.booksIssued = booksIssued;
            memberCount++;
            this.memberId = String.format("LM-%04d", 1000 + memberCount);
        }

        void printMemberCard() {
            System.out.println(name + " | " + memberId + " | " + libraryName
                    + " | Books issued: " + booksIssued);
        }

        static void printTotalMembers() {
            System.out.println("Total members: " + memberCount);
        }
    }

    public static void main(String[] args) {
        // Static name, memberId, and booksIssued are wrong because all members would share each value.
        System.out.println("Broken version:");
        BrokenLibraryMember brokenAditi = new BrokenLibraryMember("Aditi", "LM-1001", 2);
        BrokenLibraryMember brokenRohan = new BrokenLibraryMember("Rohan", "LM-1002", 4);
        System.out.println(brokenAditi.name);
        System.out.println(brokenRohan.name);

        System.out.println("Fixed version:");
        LibraryMember aditi = new LibraryMember("Aditi", 2);
        LibraryMember rohan = new LibraryMember("Rohan", 4);
        aditi.printMemberCard();
        rohan.printMemberCard();
        LibraryMember.printTotalMembers();
    }
}