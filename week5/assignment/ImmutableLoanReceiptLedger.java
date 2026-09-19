import java.util.Arrays;

public class ImmutableLoanReceiptLedger {
    static class LoanReceipt {
        private final String memberId;
        private final String[] bookIds;

        LoanReceipt(String memberId, String[] bookIds) {
            if (memberId == null || bookIds == null) {
                throw new IllegalArgumentException("Member ID and book IDs are required");
            }
            for (String bookId : bookIds) {
                if (bookId == null || !bookId.matches("BK-\\d{3}")) {
                    throw new IllegalArgumentException("Invalid book ID");
                }
            }
            this.memberId = memberId;
            this.bookIds = bookIds.clone();
        }

        String[] getBookIds() {
            return bookIds.clone();
        }

        LoanReceipt withCorrectedBookId(int index, String newId) {
            String[] corrected = bookIds.clone();
            corrected[index] = newId;
            return new LoanReceipt(memberId, corrected);
        }
    }

    static class ReferenceOnlyLoanReceipt extends LoanReceipt {
        private final String roomNumber;

        ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
            super(memberId, bookIds);
            this.roomNumber = roomNumber;
        }
    }

    static String processNightlyCirculation(LoanReceipt[] receipts) {
        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        for (LoanReceipt receipt : receipts) {
            if (receipt == null) {
                nullSkipped++;
            } else {
                processed++;
                if (receipt instanceof ReferenceOnlyLoanReceipt) {
                    referenceOnly++;
                }
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | " + (processed - referenceOnly) + " regular";
    }

    public static void main(String[] args) {
        try {
            new LoanReceipt("LIB-8841", new String[]{"BK-100", "bad"});
        } catch (IllegalArgumentException exception) {
            System.out.println("construction rejected");
        }
        LoanReceipt receipt = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});
        String[] ids = receipt.getBookIds();
        ids[0] = "HACKED";
        System.out.println(Arrays.toString(receipt.getBookIds()));
        System.out.println(processNightlyCirculation(new LoanReceipt[]{
                new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"}, "Reading Room 3"),
                null, new LoanReceipt("LIB-002", new String[]{"BK-201"})
        }));
    }
}