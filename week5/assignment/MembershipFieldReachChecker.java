public class MembershipFieldReachChecker {
    static class LibraryMember {
        private String membershipId;
        String branchCode;
        protected double finesOwed;
        public String displayName;

        LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName) {
            if (membershipId == null || membershipId.trim().length() < 4) {
                throw new IllegalArgumentException("Membership ID must contain at least 4 characters");
            }
            this.membershipId = membershipId.trim();
            this.branchCode = branchCode;
            this.finesOwed = finesOwed;
            this.displayName = displayName;
        }
    }

    static String classifyAccess(String fieldModifier, String accessorContext) {
        if ("public".equals(fieldModifier)) {
            return "ALLOWED";
        }
        if ("private".equals(fieldModifier)) {
            return "SAME_CLASS".equals(accessorContext) ? "ALLOWED" : "DENIED";
        }
        return "DIFFERENT_PACKAGE".equals(accessorContext) ? "DENIED" : "ALLOWED";
    }

    static String summarizeByModifier(String[][] attempts) {
        String[] modifiers = {"private", "default", "protected", "public"};
        StringBuilder summary = new StringBuilder();
        for (String modifier : modifiers) {
            int allowed = 0;
            int denied = 0;
            for (String[] attempt : attempts) {
                if (modifier.equals(attempt[0])) {
                    if ("ALLOWED".equals(classifyAccess(attempt[0], attempt[1]))) {
                        allowed++;
                    } else {
                        denied++;
                    }
                }
            }
            if (summary.length() > 0) {
                summary.append(" | ");
            }
            summary.append(modifier).append(": ").append(allowed)
                    .append(" allowed / ").append(denied).append(" denied");
        }
        return summary.toString();
    }

    public static void main(String[] args) {
        System.out.println(classifyAccess("private", "SAME_CLASS"));
        System.out.println(classifyAccess("protected", "DIFFERENT_PACKAGE"));
        System.out.println(summarizeByModifier(new String[][]{
                {"private", "SAME_CLASS"}, {"private", "SAME_PACKAGE"},
                {"default", "SAME_PACKAGE"}, {"default", "DIFFERENT_PACKAGE"},
                {"protected", "SAME_PACKAGE"}, {"protected", "SAME_CLASS"},
                {"public", "DIFFERENT_PACKAGE"}
        }));
        try {
            new LibraryMember("LB9", "BR1", 0, "Priya Nair");
        } catch (IllegalArgumentException exception) {
            System.out.println("construction rejected");
        }
    }
}