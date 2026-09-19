public class GhostOrderValidator {
    static class FoodOrder {
        private String studentName;
        private String dishName;
        private boolean delivered;

        FoodOrder(String studentName, String dishName) {
            if (studentName == null || studentName.trim().isEmpty()
                    || dishName == null || dishName.trim().isEmpty()) {
                throw new IllegalArgumentException("Student name and dish name are required");
            }
            this.studentName = studentName.trim();
            this.dishName = dishName.trim();
        }

        void markDelivered() {
            if (delivered) {
                System.out.println("Order for " + studentName + " was already delivered.");
            } else {
                delivered = true;
                System.out.println("Order for " + studentName + " marked delivered.");
            }
        }
    }

    static void processBatch(String[][] rawOrders) {
        int valid = 0;
        int rejected = 0;
        for (String[] rawOrder : rawOrders) {
            if (rawOrder == null || rawOrder.length < 2) {
                rejected++;
                continue;
            }
            try {
                new FoodOrder(rawOrder[0], rawOrder[1]);
                valid++;
            } catch (IllegalArgumentException exception) {
                rejected++;
            }
        }
        System.out.println("Valid: " + valid + " | Rejected: " + rejected);
    }

    public static void main(String[] args) {
        String[][] rawOrders = {
                {"Ravi", "Paneer Butter Masala"}, {"", "Chole Bhature"},
                {"Meera", " "}, {"Divya", "Veg Biryani"}
        };
        processBatch(rawOrders);
        FoodOrder order = new FoodOrder("Ravi", "Paneer Butter Masala");
        order.markDelivered();
        order.markDelivered();
    }
}