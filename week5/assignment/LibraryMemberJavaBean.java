public class LibraryMemberJavaBean {
    public static class LibraryMember {
        private String membershipId;
        private String name;
        private boolean premiumMember;
        private String securityAnswerHash;

        public LibraryMember() {
            this(null, null);
        }

        public LibraryMember(String name) {
            this(null, name);
        }

        public LibraryMember(String membershipId, String name) {
            this.membershipId = membershipId;
            this.name = name;
        }

        public String getMembershipId() {
            return membershipId;
        }

        public void setMembershipId(String id) {
            if (membershipId == null) {
                membershipId = id;
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isPremiumMember() {
            return premiumMember;
        }

        public void setPremiumMember(boolean premium) {
            premiumMember = premium;
        }

        public void setSecurityAnswer(String answer) {
            if (answer != null) {
                securityAnswerHash = Integer.toHexString(answer.hashCode());
            }
        }
    }

    public static void main(String[] args) {
        LibraryMember nameOnly = new LibraryMember("Priya Nair");
        System.out.println(nameOnly.getMembershipId());
        LibraryMember identified = new LibraryMember("LIB-8841", "Priya Nair");
        System.out.println(identified.getMembershipId());
        LibraryMember writeOnce = new LibraryMember();
        writeOnce.setMembershipId("LIB-8841");
        writeOnce.setMembershipId("FAKE-0000");
        System.out.println(writeOnce.getMembershipId());
    }
}