/**
 * Use Case 6: Reservation Confirmation & Room Allocation
 * Demonstrates FIFO processing, uniqueness using Set, and inventory update.
 *
 * @author Sabariysh
 * @version 6.0
 */

import java.util.*;

// 🔹 Reservation Class
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// 🔹 Inventory Service
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void reduceAvailability(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void displayInventory() {
        System.out.println("\nUpdated Inventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " → " + e.getValue());
        }
    }
}

// 🔹 Booking Service
class BookingService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    public void processBookings(Queue<Reservation> queue, RoomInventory inventory) {

        int roomCounter = 101;

        while (!queue.isEmpty()) {

            Reservation r = queue.poll(); // FIFO
            String type = r.roomType;

            System.out.println("\nProcessing request for " + r.guestName);

            if (inventory.getAvailability(type) > 0) {

                // 🔹 Generate unique room ID
                String roomId = type.substring(0, 2).toUpperCase() + roomCounter++;

                // Ensure uniqueness
                if (!allocatedRoomIds.contains(roomId)) {
                    allocatedRoomIds.add(roomId);

                    // Store allocation
                    roomAllocations
                            .computeIfAbsent(type, k -> new HashSet<>())
                            .add(roomId);

                    // Update inventory
                    inventory.reduceAvailability(type);

                    System.out.println("Booking Confirmed!");
                    System.out.println("Room Allocated: " + roomId);
                }

            } else {
                System.out.println("Booking Failed! No rooms available.");
            }
        }
    }
}

// 🔹 Main Class
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 6.0");

        // 🔹 Booking Queue
        Queue<Reservation> queue = new LinkedList<>();
        queue.add(new Reservation("Arun", "Single Room"));
        queue.add(new Reservation("Priya", "Single Room"));
        queue.add(new Reservation("Rahul", "Single Room")); // exceeds capacity
        queue.add(new Reservation("Sneha", "Suite Room"));

        // 🔹 Inventory
        RoomInventory inventory = new RoomInventory();

        // 🔹 Booking Service
        BookingService service = new BookingService();

        // Process bookings
        service.processBookings(queue, inventory);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication terminated.");
    }
}