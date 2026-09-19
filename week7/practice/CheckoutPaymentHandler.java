public class CheckoutPaymentHandler {
    static abstract class PaymentMethod {
        private static int transactionCount;
        private final String transactionId;

        PaymentMethod() {
            transactionCount++;
            transactionId = String.format("TXN-%04d", 1000 + transactionCount);
        }

        public abstract String processPayment(double amount);

        String processPayment(double amount, String note) {
            return processPayment(amount) + " (" + note + ")";
        }

        public String getTransactionId() {
            return transactionId;
        }
    }

    static class CreditCardPayment extends PaymentMethod {
        private String cardNumberLastFour;

        CreditCardPayment(String cardNumberLastFour) {
            this.cardNumberLastFour = cardNumberLastFour;
        }

        @Override
        public String processPayment(double amount) {
            return "Charged $" + amount + " to card ending " + cardNumberLastFour
                    + " - Txn " + getTransactionId();
        }
    }

    static class CashPayment extends PaymentMethod {
        @Override
        public String processPayment(double amount) {
            return "Received $" + amount + " in cash - Txn " + getTransactionId();
        }
    }

    static void printConfirmation(PaymentMethod payment, double amount) {
        System.out.println(payment.processPayment(amount));
    }

    static void testUpcasting() {
        // Upcasting stores a CreditCardPayment in a PaymentMethod reference.
        PaymentMethod reference = new CreditCardPayment("4471");
        printConfirmation(reference, 250.0);
    }

    public static void main(String[] args) {
        CreditCardPayment creditCard = new CreditCardPayment("4471");
        CashPayment cash = new CashPayment();
        System.out.println(creditCard.processPayment(250.0));
        System.out.println(cash.processPayment(40.0));
        System.out.println(creditCard.processPayment(250.0, "Birthday gift"));
        testUpcasting();
    }
}