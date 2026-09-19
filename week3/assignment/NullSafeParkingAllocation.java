public class NullSafeParkingAllocation {
    static class ParkingSlot {
        private String slotNo;
        private int capacity;
        private int occupiedCount;

        ParkingSlot(String slotNo, int capacity, int occupiedCount) {
            this.slotNo = slotNo;
            this.capacity = capacity;
            this.occupiedCount = occupiedCount;
        }

        void allot(String vehicleNo) {
            if (occupiedCount < capacity) {
                occupiedCount++;
                System.out.println(vehicleNo + " allotted to slot " + slotNo);
            }
        }
    }

    // The array contains references to the existing ParkingSlot objects, not copies of those objects.
    static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        for (ParkingSlot slot : slots) {
            if (slot != null && slot.occupiedCount < slot.capacity) {
                return slot;
            }
        }
        return null;
    }

    static void safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot availableSlot = findAvailableSlot(slots);
        if (availableSlot == null) {
            System.out.println("No slots available for " + vehicleNo);
        } else {
            availableSlot.allot(vehicleNo);
        }
    }

    public static void main(String[] args) {
        ParkingSlot[] slotsWithSpace = {
                new ParkingSlot("A1", 4, 3),
                new ParkingSlot("A2", 5, 5)
        };
        safeAllot(slotsWithSpace, "TN09AB1234");

        ParkingSlot[] fullSlots = {
                new ParkingSlot("A1", 4, 4),
                new ParkingSlot("A2", 5, 5)
        };
        safeAllot(fullSlots, "TN09AB1234");
    }
}