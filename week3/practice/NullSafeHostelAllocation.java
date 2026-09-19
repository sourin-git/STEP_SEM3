import java.util.Scanner;

public class NullSafeHostelAllocation {
    static class HostelRoom {
        private String roomNo;
        private int beds;
        private int occupied;

        HostelRoom(String roomNo, int beds, int occupied) {
            this.roomNo = roomNo;
            this.beds = beds;
            this.occupied = occupied;
        }

        void allot(String name) {
            if (occupied < beds) {
                occupied++;
                System.out.println(name + " allotted to room " + roomNo);
            }
        }
    }

    // The array stores references to the same HostelRoom objects, so no room objects are copied.
    static HostelRoom findAvailableRoom(HostelRoom[] rooms) {
        for (HostelRoom room : rooms) {
            if (room != null && room.occupied < room.beds) {
                return room;
            }
        }
        return null;
    }

    static void safeAllot(HostelRoom[] rooms, String studentName) {
        HostelRoom availableRoom = findAvailableRoom(rooms);
        if (availableRoom == null) {
            System.out.println("No rooms available for " + studentName);
        } else {
            availableRoom.allot(studentName);
        }
    }

    public static void main(String[] args) {
        HostelRoom[] roomsWithSpace = {
                new HostelRoom("C-214", 3, 2),
                new HostelRoom("C-507", 2, 2)
        };
        safeAllot(roomsWithSpace, "Divya");

        HostelRoom[] fullRooms = {
                new HostelRoom("C-214", 3, 3),
                new HostelRoom("C-507", 2, 2)
        };
        safeAllot(fullRooms, "Divya");
    }
}