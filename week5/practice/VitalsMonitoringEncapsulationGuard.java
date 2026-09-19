import java.util.Arrays;

public class VitalsMonitoringEncapsulationGuard {
    static class PatientVitals {
        private double[] readings;
        private int readingCount;

        PatientVitals(double[] initialReadings) {
            readings = new double[initialReadings == null ? 0 : initialReadings.length];
            if (initialReadings != null) {
                for (double reading : initialReadings) {
                    recordReading(reading);
                }
            }
        }

        void recordReading(double reading) {
            if (reading <= 0 || reading > 45) {
                return;
            }
            if (readingCount == readings.length) {
                readings = Arrays.copyOf(readings, Math.max(1, readings.length * 2));
            }
            readings[readingCount++] = reading;
        }

        double getAverage() {
            if (readingCount == 0) {
                return 0;
            }
            double total = 0;
            for (int index = 0; index < readingCount; index++) {
                total += readings[index];
            }
            return total / readingCount;
        }

        double[] getAllReadings() {
            return Arrays.copyOf(readings, readingCount);
        }
    }

    public static void main(String[] args) {
        PatientVitals vitals = new PatientVitals(new double[]{36.5, -2, 37.1});
        System.out.println(Arrays.toString(vitals.getAllReadings()));
        double[] copy = vitals.getAllReadings();
        copy[0] = 999;
        System.out.println(Arrays.toString(vitals.getAllReadings()));
    }
}