public class BookCopyCirculationGuard {
    static class BookInventory {
        private int copiesTotal;
        private int copiesAvailable;

        BookInventory(int copiesTotal) {
            if (copiesTotal <= 0) {
                throw new IllegalArgumentException("Total copies must be positive");
            }
            this.copiesTotal = copiesTotal;
            copiesAvailable = copiesTotal;
        }

        void checkOut() {
            if (copiesAvailable > 0) {
                copiesAvailable--;
            }
        }

        void checkIn() {
            if (copiesAvailable < copiesTotal) {
                copiesAvailable++;
            }
        }

        int getCopiesAvailable() {
            return copiesAvailable;
        }
    }

    public static void main(String[] args) {
        try {
            new BookInventory(0);
        } catch (IllegalArgumentException exception) {
            System.out.println("construction rejected");
        }
        BookInventory inventory = new BookInventory(3);
        inventory.checkOut();
        inventory.checkOut();
        inventory.checkOut();
        inventory.checkOut();
        System.out.println("After checkouts: " + inventory.getCopiesAvailable());
        inventory.checkIn();
        inventory.checkIn();
        inventory.checkIn();
        inventory.checkIn();
        System.out.println("After check-ins: " + inventory.getCopiesAvailable());
    }
}