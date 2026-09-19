import java.util.Arrays;

public class ImmutableDischargeSummaryLedger {
    static class DischargeSummary {
        private final String patientId;
        private final String[] medicationCodes;

        DischargeSummary(String patientId, String[] medicationCodes) {
            if (patientId == null || medicationCodes == null) {
                throw new IllegalArgumentException("Patient ID and medications are required");
            }
            for (String code : medicationCodes) {
                if (code == null || !code.matches("MED-[A-Z]")) {
                    throw new IllegalArgumentException("Invalid medication code");
                }
            }
            this.patientId = patientId;
            this.medicationCodes = medicationCodes.clone();
        }

        String[] getMedicationCodes() {
            return medicationCodes.clone();
        }

        DischargeSummary withCorrectedMedication(int index, String newCode) {
            String[] corrected = medicationCodes.clone();
            corrected[index] = newCode;
            return new DischargeSummary(patientId, corrected);
        }
    }

    static class CriticalCareDischargeSummary extends DischargeSummary {
        private final int icuDays;

        CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays) {
            super(patientId, medicationCodes);
            this.icuDays = icuDays;
        }
    }

    static String processNightlyBatch(DischargeSummary[] summaries) {
        int processed = 0;
        int nullSkipped = 0;
        int criticalCare = 0;
        for (DischargeSummary summary : summaries) {
            if (summary == null) {
                nullSkipped++;
            } else {
                processed++;
                if (summary instanceof CriticalCareDischargeSummary) {
                    criticalCare++;
                }
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | "
                + criticalCare + " critical-care | " + (processed - criticalCare) + " routine";
    }

    public static void main(String[] args) {
        try {
            new DischargeSummary("MT2026-0142", new String[]{"MED-A", "bad"});
        } catch (IllegalArgumentException exception) {
            System.out.println("construction rejected");
        }
        DischargeSummary summary = new DischargeSummary("MT2026-0142", new String[]{"MED-A", "MED-B"});
        String[] codes = summary.getMedicationCodes();
        codes[0] = "TAMPERED";
        System.out.println(Arrays.toString(summary.getMedicationCodes()));
        System.out.println(processNightlyBatch(new DischargeSummary[]{
                new CriticalCareDischargeSummary("MT001", new String[]{"MED-X"}, 4),
                null, new DischargeSummary("MT002", new String[]{"MED-Y"})
        }));
    }
}