import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmartLabControlPanel {
    interface Capability {
        String getName();
        boolean isValid(double value);
        void apply(Device device, double value);
    }

    static class PowerCapability implements Capability {
        @Override
        public String getName() {
            return "Power";
        }

        @Override
        public boolean isValid(double value) {
            return value == 0 || value == 1;
        }

        @Override
        public void apply(Device device, double value) {
            device.setPower(value == 1);
        }
    }

    static class BrightnessCapability implements Capability {
        @Override
        public String getName() {
            return "Brightness";
        }

        @Override
        public boolean isValid(double value) {
            return value >= 0 && value <= 100;
        }

        @Override
        public void apply(Device device, double value) {
            device.setBrightness((int) value);
        }
    }

    static class TemperatureCapability implements Capability {
        @Override
        public String getName() {
            return "Temperature";
        }

        @Override
        public boolean isValid(double value) {
            return value >= 16 && value <= 30;
        }

        @Override
        public void apply(Device device, double value) {
            device.setTemperature((int) value);
        }
    }

    static class Device {
        private final String name;
        private final Map<String, Capability> capabilities = new HashMap<>();
        private boolean powerOn;
        private int brightness;
        private int temperature;

        Device(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void addCapability(Capability capability) {
            capabilities.put(capability.getName(), capability);
        }

        public boolean hasCapability(String capabilityName) {
            return capabilities.containsKey(capabilityName);
        }

        public void setCapabilityValue(String capabilityName, double value) {
            Capability capability = capabilities.get(capabilityName);
            if (capability == null) {
                System.out.println("Rejected: " + name + " does not support " + capabilityName + ".");
                return;
            }
            if (!capability.isValid(value)) {
                if (capabilityName.equals("Temperature")) {
                    System.out.println("Rejected: " + name + " temperature must be between 16°C and 30°C.");
                } else if (capabilityName.equals("Brightness")) {
                    System.out.println("Rejected: " + name + " brightness must be between 0% and 100%.");
                } else {
                    System.out.println("Rejected: " + name + " value is invalid for " + capabilityName + ".");
                }
                return;
            }
            capability.apply(this, value);
            if (capabilityName.equals("Power")) {
                System.out.println(name + ": ON.");
            } else if (capabilityName.equals("Brightness")) {
                System.out.println(name + ": brightness set to " + (int) value + "%.");
            } else if (capabilityName.equals("Temperature")) {
                System.out.println(name + ": temperature set to " + (int) value + "°C.");
            }
        }

        private void setPower(boolean on) {
            this.powerOn = on;
        }

        private void setBrightness(int brightness) {
            this.brightness = brightness;
        }

        private void setTemperature(int temperature) {
            this.temperature = temperature;
        }
    }

    static class SceneStep {
        private final String capabilityName;
        private final double value;

        SceneStep(String capabilityName, double value) {
            this.capabilityName = capabilityName;
            this.value = value;
        }

        public String getCapabilityName() {
            return capabilityName;
        }

        public double getValue() {
            return value;
        }
    }

    static class Scene {
        private final String name;
        private final List<SceneStep> steps = new ArrayList<>();

        Scene(String name) {
            this.name = name;
        }

        public void addStep(SceneStep step) {
            steps.add(step);
        }

        public void execute(List<Device> devices) {
            int actions = 0;
            System.out.println("Scene '" + name + "' started.");
            for (SceneStep step : steps) {
                for (Device device : devices) {
                    if (!device.hasCapability(step.getCapabilityName())) {
                        continue;
                    }
                    device.setCapabilityValue(step.getCapabilityName(), step.getValue());
                    actions++;
                }
            }
            System.out.println("Scene '" + name + "' completed: " + actions + " actions applied.");
        }
    }

    public static void main(String[] args) {
        Device labAc = new Device("Lab AC");
        labAc.addCapability(new PowerCapability());
        labAc.addCapability(new TemperatureCapability());

        Device ceilingLights = new Device("Ceiling Lights");
        ceilingLights.addCapability(new PowerCapability());
        ceilingLights.addCapability(new BrightnessCapability());

        Device projector = new Device("Projector");
        projector.addCapability(new PowerCapability());

        Scene lectureMode = new Scene("Lecture Mode");
        lectureMode.addStep(new SceneStep("Power", 1));
        lectureMode.addStep(new SceneStep("Brightness", 40));
        lectureMode.addStep(new SceneStep("Temperature", 24));

        lectureMode.execute(Arrays.asList(labAc, ceilingLights, projector));

        labAc.setCapabilityValue("Temperature", 12);

        projector.addCapability(new BrightnessCapability());
        System.out.println("Projector: Brightness capability added.");
        projector.setCapabilityValue("Brightness", 70);
    }
}
