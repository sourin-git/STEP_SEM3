public class ConnectedHomeControlPanel {
    interface RemoteControllable {
        String connect(String appId);
    }

    interface EnergyTrackable {
        double getConsumptionWatts();
    }

    static abstract class HomeDevice {
        private static int deviceCount;
        private final String serialNumber;

        HomeDevice() {
            deviceCount++;
            serialNumber = String.format("HD-%04d", 1000 + deviceCount);
        }

        abstract String activate();

        String getSerialNumber() {
            return serialNumber;
        }
    }

    static class WashingMachine extends HomeDevice implements RemoteControllable, EnergyTrackable {
        private double consumptionWatts;

        WashingMachine(double consumptionWatts) {
            if (consumptionWatts <= 0) {
                throw new IllegalArgumentException("Consumption must be positive");
            }
            this.consumptionWatts = consumptionWatts;
        }

        @Override
        String activate() {
            return "Washing machine " + getSerialNumber() + " started a cycle";
        }

        @Override
        public String connect(String appId) {
            return getSerialNumber() + " connected to " + appId;
        }

        @Override
        public double getConsumptionWatts() {
            return consumptionWatts;
        }
    }

    static class Refrigerator extends HomeDevice implements EnergyTrackable {
        private double consumptionWatts;

        Refrigerator(double consumptionWatts) {
            if (consumptionWatts <= 0) {
                throw new IllegalArgumentException("Consumption must be positive");
            }
            this.consumptionWatts = consumptionWatts;
        }

        @Override
        String activate() {
            return "Refrigerator " + getSerialNumber() + " is cooling";
        }

        @Override
        public double getConsumptionWatts() {
            return consumptionWatts;
        }
    }

    static class MobileApp implements RemoteControllable {
        private String appName;

        MobileApp(String appName) {
            if (appName == null || appName.trim().isEmpty()) {
                throw new IllegalArgumentException("App name is required");
            }
            this.appName = appName;
        }

        @Override
        public String connect(String appId) {
            return appName + " connected to " + appId;
        }
    }

    static void connectAll(RemoteControllable[] items, String appId) {
        for (RemoteControllable item : items) {
            System.out.println(item.connect(appId));
        }
    }

    static double getConsumptionIfTrackable(HomeDevice device) {
        if (device instanceof EnergyTrackable trackable) {
            return trackable.getConsumptionWatts();
        }
        return 0;
    }

    public static void main(String[] args) {
        WashingMachine washingMachine = new WashingMachine(500.0);
        Refrigerator refrigerator = new Refrigerator(150.0);
        MobileApp app = new MobileApp("HomeConnect App");
        System.out.println(washingMachine.activate());
        System.out.println(washingMachine.connect("HomeConnect"));
        System.out.println(getConsumptionIfTrackable(refrigerator));
        System.out.println(app.connect("HomeConnect"));
        HomeDevice reference = washingMachine;
        System.out.println(getConsumptionIfTrackable(reference));
        connectAll(new RemoteControllable[]{washingMachine, app}, "HomeConnect");
    }
}