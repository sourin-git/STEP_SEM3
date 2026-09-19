public class FieldVisibilityIntakeValidator {
    static class PatientRecord {
        private String patientId;
        String wardCode;
        protected double vitalsScore;
        public String facilityName;

        PatientRecord(String patientId, String wardCode, double vitalsScore, String facilityName) {
            if (patientId == null || patientId.trim().length() < 4) {
                throw new IllegalArgumentException("Patient ID must contain at least 4 characters");
            }
            this.patientId = patientId.trim();
            this.wardCode = wardCode;
            this.vitalsScore = vitalsScore;
            this.facilityName = facilityName;
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

    static String summarizeBatch(String[][] attempts) {
        int allowed = 0;
        for (String[] attempt : attempts) {
            if ("ALLOWED".equals(classifyAccess(attempt[0], attempt[1]))) {
                allowed++;
            }
        }
        return "Allowed: " + allowed + " | Denied: " + (attempts.length - allowed);
    }

    public static void main(String[] args) {
        System.out.println(classifyAccess("private", "SAME_CLASS"));
        System.out.println(classifyAccess("default", "DIFFERENT_PACKAGE"));
        System.out.println(summarizeBatch(new String[][]{
                {"protected", "SAME_PACKAGE"}, {"protected", "DIFFERENT_PACKAGE"},
                {"public", "DIFFERENT_PACKAGE"}
        }));
        try {
            new PatientRecord("MT9", "W3", 98.2, "MediTrack Central");
        } catch (IllegalArgumentException exception) {
            System.out.println("construction rejected");
        }
    }
}