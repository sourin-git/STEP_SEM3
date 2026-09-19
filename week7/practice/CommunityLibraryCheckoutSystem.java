public class CommunityLibraryCheckoutSystem {
    interface Renewable {
        String renew();
    }

    interface Reservable {
        String reserve();
    }

    static abstract class LibraryItem {
        private static int itemCount;
        private final String itemId;

        LibraryItem() {
            itemCount++;
            itemId = String.format("ITEM-%04d", 1000 + itemCount);
        }

        abstract int getLoanPeriodDays();

        String getItemId() {
            return itemId;
        }
    }

    static class Textbook extends LibraryItem implements Renewable, Reservable {
        private String title;

        Textbook(String title) {
            this.title = title;
        }

        @Override
        int getLoanPeriodDays() {
            return 14;
        }

        @Override
        public String renew() {
            return title + " renewed";
        }

        @Override
        public String reserve() {
            return title + " reserved";
        }
    }

    static class Magazine extends LibraryItem implements Renewable {
        private String title;

        Magazine(String title) {
            this.title = title;
        }

        @Override
        int getLoanPeriodDays() {
            return 7;
        }

        @Override
        public String renew() {
            return title + " renewed";
        }
    }

    static class DigitalPass implements Renewable {
        private String resourceName;

        DigitalPass(String resourceName) {
            this.resourceName = resourceName;
        }

        @Override
        public String renew() {
            return resourceName + " renewed";
        }
    }

    static void processCheckouts(LibraryItem[] items) {
        for (LibraryItem item : items) {
            System.out.println(item.getItemId() + " | Loan days: " + item.getLoanPeriodDays());
        }
    }

    static String reserveIfSupported(Object object) {
        if (object instanceof Reservable reservable) {
            return reservable.reserve();
        }
        return "Reservation not supported";
    }

    public static void main(String[] args) {
        Textbook textbook = new Textbook("Java Fundamentals");
        Magazine magazine = new Magazine("Tech Monthly");
        DigitalPass digitalPass = new DigitalPass("E-Journal Access");
        System.out.println(textbook.getLoanPeriodDays());
        System.out.println(textbook.renew());
        System.out.println(textbook.reserve());
        System.out.println(reserveIfSupported(magazine));
        System.out.println(reserveIfSupported(digitalPass));
        LibraryItem reference = textbook;
        System.out.println(reserveIfSupported(reference));
        processCheckouts(new LibraryItem[]{textbook, magazine});
    }
}