import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampusCanteenSmartCard {
    interface PricingPlan {
        BigDecimal calculatePrice(BigDecimal itemPrice);
    }

    static class DayScholarPlan implements PricingPlan {
        @Override
        public BigDecimal calculatePrice(BigDecimal itemPrice) {
            return itemPrice.setScale(2, RoundingMode.HALF_UP);
        }
    }

    static class HostellerPlan implements PricingPlan {
        @Override
        public BigDecimal calculatePrice(BigDecimal itemPrice) {
            return itemPrice.multiply(new BigDecimal("0.90")).setScale(2, RoundingMode.HALF_UP);
        }
    }

    static class StaffPlan implements PricingPlan {
        @Override
        public BigDecimal calculatePrice(BigDecimal itemPrice) {
            return itemPrice.multiply(new BigDecimal("0.80")).setScale(2, RoundingMode.HALF_UP);
        }
    }

    static class Transaction {
        private final String description;
        private final BigDecimal amount;

        Transaction(String description, BigDecimal amount) {
            this.description = description;
            this.amount = amount;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }
    }

    static class SmartCard {
        private final String cardId;
        private final PricingPlan pricingPlan;
        private final List<Transaction> transactions = new ArrayList<>();
        private final Map<String, BigDecimal> purchaseAmounts = new HashMap<>();
        private final Map<String, Boolean> refundedItems = new HashMap<>();
        private boolean blocked;

        SmartCard(String cardId, PricingPlan pricingPlan) {
            this.cardId = cardId;
            this.pricingPlan = pricingPlan;
        }

        public BigDecimal getBalance() {
            BigDecimal total = BigDecimal.ZERO;
            for (Transaction transaction : transactions) {
                total = total.add(transaction.getAmount());
            }
            return total.setScale(2, RoundingMode.HALF_UP);
        }

        public void topUp(BigDecimal amount) {
            if (blocked) {
                System.out.println("Top-up rejected: card " + cardId + " is blocked.");
                return;
            }
            if (amount.compareTo(new BigDecimal("100")) < 0) {
                System.out.println("Top-up rejected: minimum top-up is \u20B9100.00.");
                return;
            }
            if (getBalance().add(amount).compareTo(new BigDecimal("5000")) > 0) {
                System.out.println("Top-up rejected: maximum balance is \u20B95000.00.");
                return;
            }
            transactions.add(new Transaction("Top-up", amount));
            System.out.println("C-2045 topped up with \u20B9500.00. Balance: \u20B9500.00.");
        }

        public boolean purchase(String itemName, BigDecimal price) {
            if (blocked) {
                System.out.println("Purchase rejected: card " + cardId + " is blocked.");
                return false;
            }
            BigDecimal charge = pricingPlan.calculatePrice(price);
            if (getBalance().compareTo(charge) < 0) {
                System.out.println("Purchase failed: Insufficient balance (required \u20B9" + charge + ", available \u20B9" + getBalance() + ").");
                return false;
            }
            transactions.add(new Transaction(itemName, charge.negate()));
            purchaseAmounts.put(itemName, charge);
            refundedItems.put(itemName, false);
            System.out.println(itemName + " purchased for \u20B9" + charge + ". Balance: \u20B9" + getBalance() + ".");
            return true;
        }

        public void refund(String itemName) {
            if (!purchaseAmounts.containsKey(itemName)) {
                System.out.println("Refund rejected: " + itemName + " was never purchased.");
                return;
            }
            if (Boolean.TRUE.equals(refundedItems.get(itemName))) {
                System.out.println("Refund rejected: " + itemName + " has already been refunded.");
                return;
            }
            BigDecimal refundAmount = purchaseAmounts.get(itemName);
            transactions.add(new Transaction("Refund: " + itemName, refundAmount));
            refundedItems.put(itemName, true);
            System.out.println("Refund of \u20B9" + refundAmount + " for " + itemName + " processed. Balance: \u20B9" + getBalance() + ".");
        }

        public void miniStatement() {
            StringBuilder builder = new StringBuilder();
            builder.append("Mini-statement for ").append(cardId).append(": ");
            for (int i = 0; i < transactions.size(); i++) {
                Transaction tx = transactions.get(i);
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(formatSignedAmount(tx.getAmount()));
            }
            builder.append(" = \u20B9").append(getBalance().setScale(2, RoundingMode.HALF_UP));
            System.out.println(builder.toString() + ".");
        }

        private String formatSignedAmount(BigDecimal amount) {
            if (amount.compareTo(BigDecimal.ZERO) >= 0) {
                return "+" + amount.setScale(2, RoundingMode.HALF_UP);
            }
            return amount.setScale(2, RoundingMode.HALF_UP).toPlainString();
        }

        public void block() {
            blocked = true;
        }

        public void unblock() {
            blocked = false;
        }
    }

    public static void main(String[] args) {
        SmartCard card = new SmartCard("C-2045", new HostellerPlan());
        card.topUp(new BigDecimal("500"));

        card.purchase("Veg Thali", new BigDecimal("120"));
        card.purchase("Cold Coffee", new BigDecimal("60"));
        card.purchase("Snacks Pack", new BigDecimal("400"));

        card.refund("Veg Thali");
        card.refund("Veg Thali");
        card.miniStatement();
    }
}
