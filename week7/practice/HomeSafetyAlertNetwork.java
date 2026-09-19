public class HomeSafetyAlertNetwork {
    interface Alertable {
        String sendAlert(String message);
    }

    static class SecuritySensor {
        private String zoneName;

        SecuritySensor(String zoneName) {
            this.zoneName = zoneName;
        }

        String getZoneName() {
            return zoneName;
        }
    }

    static class MotionSensor extends SecuritySensor implements Alertable {
        MotionSensor(String zoneName) {
            super(zoneName);
        }

        @Override
        public String sendAlert(String message) {
            return "[" + getZoneName() + "] " + message;
        }
    }

    static class DualZoneMotionSensor extends MotionSensor {
        private String secondZoneName;

        DualZoneMotionSensor(String zoneName, String secondZoneName) {
            super(zoneName);
            this.secondZoneName = secondZoneName;
        }

        @Override
        public String sendAlert(String message) {
            return super.sendAlert(message) + " [also covering " + secondZoneName + "]";
        }
    }

    static class SmokeDetector implements Alertable {
        private String deviceId;

        SmokeDetector(String deviceId) {
            this.deviceId = deviceId;
        }

        @Override
        public String sendAlert(String message) {
            return "[" + deviceId + "] " + message;
        }
    }

    static void broadcastAll(Alertable[] devices, String message) {
        for (Alertable device : devices) {
            System.out.println(device.sendAlert(message));
        }
    }

    static String getZoneIfMotionSensor(Alertable alertable) {
        if (alertable instanceof MotionSensor motionSensor) {
            return motionSensor.getZoneName();
        }
        return "Not a motion sensor";
    }

    public static void main(String[] args) {
        MotionSensor motion = new MotionSensor("Living Room");
        DualZoneMotionSensor dual = new DualZoneMotionSensor("Hallway", "Stairwell");
        SmokeDetector smoke = new SmokeDetector("SD-01");
        System.out.println(motion.sendAlert("Motion detected"));
        System.out.println(dual.sendAlert("Motion detected"));
        System.out.println(smoke.sendAlert("Smoke detected"));
        System.out.println(getZoneIfMotionSensor(motion));
        System.out.println(getZoneIfMotionSensor(smoke));
        broadcastAll(new Alertable[]{motion, smoke}, "Test alert");
    }
}