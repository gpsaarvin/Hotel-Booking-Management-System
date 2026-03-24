/**
 * Use Case 4: Room Search & Availability Check
 * Demonstrates read-only access, filtering, and separation of concerns.
 *
 * @author Sabariysh
 * @version 4.0
 */

import java.util.*;

// 🔹 Abstract Room Class
abstract class Room {
    protected String type;
    protected double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type + " | Price: ₹" + price);
    }

    public String getType() {
        return type;
    }
}

// 🔹 Concrete Rooms
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1500);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 5000);
    }
}

// 🔹 Inventory Class (Read-only usage here)
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // unavailable
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// 🔹 Search Service
class RoomSearchService {

    public static void searchAvailableRooms(List<Room> rooms, RoomInventory inventory) {

        System.out.println("\nAvailable Rooms:\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getType());

            // 🔹 Show only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

// 🔹 Main Class
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 4.0");

        // Room objects
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Search (Read-only)
        RoomSearchService.searchAvailableRooms(rooms, inventory);

        System.out.println("Search completed. System state unchanged.");
    }
}