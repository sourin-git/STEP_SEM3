import java.util.ArrayList;
import java.util.List;

public class FoodOrderFlexiblePaymentSystem {
    interface PaymentMethod {
        String getName();
        boolean pay(double amount);
    }

    interface OrderEventListener {
        void onEvent(String message);
    }

    static class Customer implements OrderEventListener {
        private final String name;
        private final List<FoodOrder> orders = new ArrayList<>();
        Customer(String name) { this.name = name; }
        FoodOrder createOrder(Restaurant restaurant) {
            FoodOrder order = new FoodOrder(this, restaurant);
            orders.add(order);
            System.out.println("Order created.");
            return order;
        }
        public void onEvent(String message) { System.out.println("Notification: " + message); }
    }

    static class FoodItem {
        private final String name;
        private final double price;
        FoodItem(String name, double price) { this.name = name; this.price = price; }
    }

    static class Restaurant {
        private final String name;
        Restaurant(String name) { this.name = name; }
    }

    static class LineItem {
        private final FoodItem foodItem;
        private final int quantity;
        LineItem(FoodItem foodItem, int quantity) {
            this.foodItem = foodItem;
            this.quantity = quantity;
        }
        double total() { return foodItem.price * quantity; }
    }

    enum OrderStatus { DRAFT, PENDING_PAYMENT, PAID }

    static class FoodOrder {
        private static int nextId = 122;
        private int orderId;
        private final Customer customer;
        private final Restaurant restaurant;
        private final List<LineItem> lineItems = new ArrayList<>();
        private OrderStatus status = OrderStatus.DRAFT;
        private double total;

        FoodOrder(Customer customer, Restaurant restaurant) {
            this.customer = customer;
            this.restaurant = restaurant;
        }

        void addItem(FoodItem item, int quantity) {
            if (status != OrderStatus.DRAFT || quantity <= 0) throw new IllegalStateException("Cannot add item");
            lineItems.add(new LineItem(item, quantity));
            System.out.println("Added " + item.name + " (Qty " + quantity + ").");
        }

        boolean place() {
            if (status != OrderStatus.DRAFT) {
                throw new IllegalStateException("Order has already been placed");
            }
            if (lineItems.isEmpty()) {
                System.out.println("Cannot place order: Order must contain at least one item.");
                return false;
            }
            for (LineItem item : lineItems) total += item.total();
            orderId = ++nextId;
            status = OrderStatus.PENDING_PAYMENT;
            System.out.println("Order placed successfully.");
            return true;
        }

        void pay(PaymentMethod paymentMethod) {
            if (status != OrderStatus.PENDING_PAYMENT) throw new IllegalStateException("Order is not awaiting payment");
            if (paymentMethod.pay(total)) {
                status = OrderStatus.PAID;
                System.out.println("Payment via " + paymentMethod.getName() + " successful.");
                System.out.println("Order status: " + statusText() + ".");
                customer.onEvent("Order #" + orderId + " placed and paid.");
            } else {
                System.out.println("Payment via " + paymentMethod.getName() + " failed.");
                System.out.println("Order status: Pending Payment.");
                customer.onEvent("Order #" + orderId + " placed, awaiting payment.");
            }
        }

        String statusText() { return status == OrderStatus.PAID ? "Paid" : "Pending Payment"; }
    }

    static class CreditCardPayment implements PaymentMethod {
        public String getName() { return "Credit Card"; }
        public boolean pay(double amount) { return true; }
    }

    static class DigitalWalletPayment implements PaymentMethod {
        public String getName() { return "Digital Wallet"; }
        public boolean pay(double amount) { return false; }
    }

    public static void main(String[] args) {
        Customer customer = new Customer("Morgan");
        Restaurant restaurant = new Restaurant("Campus Kitchen");
        FoodItem pizza = new FoodItem("Pizza", 12.50);
        FoodItem soda = new FoodItem("Soda", 2.50);
        FoodItem burger = new FoodItem("Burger", 10.00);

        FoodOrder emptyOrder = customer.createOrder(restaurant);
        emptyOrder.place();

        FoodOrder firstOrder = customer.createOrder(restaurant);
        firstOrder.addItem(pizza, 2);
        firstOrder.addItem(soda, 1);
        firstOrder.place();
        firstOrder.pay(new CreditCardPayment());

        FoodOrder secondOrder = customer.createOrder(restaurant);
        secondOrder.addItem(burger, 1);
        secondOrder.place();
        secondOrder.pay(new DigitalWalletPayment());
    }
}