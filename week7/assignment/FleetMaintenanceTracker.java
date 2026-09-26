public class FleetMaintenanceTracker {
    interface Insurable {
        String getInsuranceInfo();
    }

    static abstract class ServiceableVehicle {
        private double mileage;

        abstract String performMaintenance();

        double getMileage() {
            return mileage;
        }

        void addMileage(double km) {
            if (km >= 0) {
                mileage += km;
            }
        }
    }

    static class Forklift extends ServiceableVehicle implements Insurable {
        protected String assetTag;

        Forklift(String assetTag) {
            if (assetTag == null || assetTag.trim().isEmpty()) {
                throw new IllegalArgumentException("Asset tag is required");
            }
            this.assetTag = assetTag;
        }

        @Override
        String performMaintenance() {
            return "Forklift " + assetTag + ": hydraulic and fork inspection complete";
        }

        @Override
        public String getInsuranceInfo() {
            return "Insured under fleet policy - Asset " + assetTag;
        }
    }

    static class HeavyDutyForklift extends Forklift {
        HeavyDutyForklift(String assetTag) {
            super(assetTag);
        }

        @Override
        String performMaintenance() {
            return super.performMaintenance() + " | high-pressure hydraulic check complete";
        }
    }

    static String getInsuranceIfApplicable(ServiceableVehicle vehicle) {
        if (vehicle instanceof Insurable insurable) {
            return insurable.getInsuranceInfo();
        }
        return "No insurance record exists";
    }

    public static void main(String[] args) {
        Forklift forklift = new Forklift("FL-22");
        forklift.addMileage(120);
        System.out.println(forklift.getMileage());
        System.out.println(forklift.performMaintenance());
        System.out.println(getInsuranceIfApplicable(forklift));
        HeavyDutyForklift heavyDuty = new HeavyDutyForklift("HD-9");
        System.out.println(heavyDuty.performMaintenance());
    }
}