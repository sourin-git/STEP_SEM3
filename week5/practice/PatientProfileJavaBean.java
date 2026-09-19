public class PatientProfileJavaBean {
    public static class PatientProfile {
        private String patientId;
        private String name;
        private boolean discharged;
        private String lockerPinHash;

        public PatientProfile() {
            this(null, null);
        }

        public PatientProfile(String name) {
            this(null, name);
        }

        public PatientProfile(String patientId, String name) {
            this.patientId = patientId;
            this.name = name;
        }

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String id) {
            if (patientId == null) {
                patientId = id;
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isDischarged() {
            return discharged;
        }

        public void setDischarged(boolean discharged) {
            this.discharged = discharged;
        }

        public void setLockerPin(String pin) {
            if (pin != null && pin.matches("\\d{4,6}")) {
                lockerPinHash = Integer.toHexString(pin.hashCode());
            }
        }
    }

    public static void main(String[] args) {
        PatientProfile nameOnly = new PatientProfile("Arjun Iyer");
        System.out.println(nameOnly.getPatientId());
        PatientProfile identified = new PatientProfile("MT2026-0142", "Arjun Iyer");
        System.out.println(identified.getPatientId());
        PatientProfile writeOnce = new PatientProfile();
        writeOnce.setPatientId("MT2026-0142");
        writeOnce.setPatientId("HACKED-0000");
        System.out.println(writeOnce.getPatientId());
    }
}