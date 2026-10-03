import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class SwiftShipParcelTracker {
    enum ParcelStatus {
        BOOKED, PICKED_UP, IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED
    }

    interface ShippingType {
        double calculateCharge(double weightInKg);
        String getName();
    }

    static class StandardShipping implements ShippingType {
        @Override
        public double calculateCharge(double weightInKg) {
            return 40 + (10 * weightInKg);
        }

        @Override
        public String getName() {
            return "Standard";
        }
    }

    static class ExpressShipping implements ShippingType {
        @Override
        public double calculateCharge(double weightInKg) {
            return 80 + (15 * weightInKg);
        }

        @Override
        public String getName() {
            return "Express";
        }
    }

    static class FragileShipping extends StandardShipping {
        @Override
        public double calculateCharge(double weightInKg) {
            return super.calculateCharge(weightInKg) + 50;
        }

        @Override
        public String getName() {
            return "Fragile";
        }
    }

    interface NotificationChannel {
        void notify(Parcel parcel, ParcelStatus status);
    }

    static class SmsChannel implements NotificationChannel {
        @Override
        public void notify(Parcel parcel, ParcelStatus status) {
            System.out.println("[SMS] " + parcel.getParcelId() + " is now " + status + ".");
        }
    }

    static class EmailChannel implements NotificationChannel {
        @Override
        public void notify(Parcel parcel, ParcelStatus status) {
            System.out.println("[Email] " + parcel.getParcelId() + " is now " + status + ".");
        }
    }

    static class Customer {
        private final String name;

        Customer(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    static class Parcel {
        private final String parcelId;
        private final Customer customer;
        private final double weightInKg;
        private final ShippingType shippingType;
        private final Set<NotificationChannel> channels = new LinkedHashSet<>();
        private ParcelStatus status = ParcelStatus.BOOKED;
        private boolean cancelled;

        Parcel(String parcelId, Customer customer, double weightInKg, ShippingType shippingType) {
            this.parcelId = parcelId;
            this.customer = customer;
            this.weightInKg = weightInKg;
            this.shippingType = shippingType;
        }

        public String getParcelId() {
            return parcelId;
        }

        public double getWeightInKg() {
            return weightInKg;
        }

        public ShippingType getShippingType() {
            return shippingType;
        }

        public ParcelStatus getStatus() {
            return status;
        }

        public void addChannel(NotificationChannel channel) {
            channels.add(channel);
        }

        public void notifyChannels() {
            for (NotificationChannel channel : channels) {
                channel.notify(this, status);
            }
        }

        public double calculateCharge() {
            return shippingType.calculateCharge(weightInKg);
        }

        public void transitionTo(ParcelStatus nextStatus) {
            Map<ParcelStatus, Set<ParcelStatus>> validTransitions = Map.of(
                    ParcelStatus.BOOKED, Set.of(ParcelStatus.PICKED_UP),
                    ParcelStatus.PICKED_UP, Set.of(ParcelStatus.IN_TRANSIT),
                    ParcelStatus.IN_TRANSIT, Set.of(ParcelStatus.OUT_FOR_DELIVERY),
                    ParcelStatus.OUT_FOR_DELIVERY, Set.of(ParcelStatus.DELIVERED)
            );

            if (validTransitions.getOrDefault(status, Set.of()).contains(nextStatus)) {
                status = nextStatus;
                notifyChannels();
            } else {
                System.out.println("Invalid transition: " + status + " → " + nextStatus + " is not allowed.");
            }
        }

        public void cancel() {
            if (status != ParcelStatus.BOOKED || cancelled) {
                System.out.println("Cancellation failed: " + parcelId + " can be cancelled only while BOOKED.");
                return;
            }
            cancelled = true;
            System.out.println("Parcel " + parcelId + " cancelled.");
        }
    }

    static class ParcelService {
        public Parcel bookParcel(Customer customer, String parcelId, double weightInKg, ShippingType shippingType, NotificationChannel... channels) {
            Parcel parcel = new Parcel(parcelId, customer, weightInKg, shippingType);
            Arrays.stream(channels).forEach(parcel::addChannel);
            String formattedWeight = (weightInKg == (int) weightInKg) ? String.valueOf((int) weightInKg) : String.valueOf(weightInKg);
            System.out.println("Parcel " + parcelId + " booked (" + shippingType.getName() + ", " + formattedWeight + " kg).");
            System.out.printf("Charge: \u20B9%.2f.%n", parcel.calculateCharge());
            parcel.notifyChannels();
            return parcel;
        }
    }

    public static void main(String[] args) {
        Customer customer = new Customer("Alice");
        ParcelService service = new ParcelService();
        Parcel parcel = service.bookParcel(customer, "P101", 2.0, new ExpressShipping(), new SmsChannel(), new EmailChannel());

        parcel.transitionTo(ParcelStatus.PICKED_UP);
        parcel.cancel();
        parcel.transitionTo(ParcelStatus.IN_TRANSIT);
        parcel.transitionTo(ParcelStatus.DELIVERED);
    }
}
