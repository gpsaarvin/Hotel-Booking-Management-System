/**
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * Demonstrates Stack (LIFO), rollback logic, and safe state restoration.
 *
 * @author Sabariysh
 * @version 10.0
 */

import java.util.*;

// 🔹 Reservation Class
class Reservation {
    String reservationId;
    String roomType;
    String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

// 🔹 Inventory Service
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 0);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void increaseAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " → " + e.getValue());
        }
    }
}

// 🔹 Cancellation Service
class CancellationService {

    private Map<String, Reservation> activeBookings = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>();

    // Add booking (simulate confirmed booking)
    public void addBooking(Reservation r) {
        activeBookings.put(r.reservationId, r);
    }

    // Cancel booking
    public void cancelBooking(String reservationId, RoomInventory inventory) {

        System.out.println("\nProcessing cancellation for: " + reservationId);

        // 🔹 Validate existence
        if (!activeBookings.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found!");
            return;
        }

        Reservation r = activeBookings.get(reservationId);

        // 🔹 Push room ID to rollback stack
        rollbackStack.push(r.roomId);

        // 🔹 Restore inventory
        inventory.increaseAvailability(r.roomType);

        // 🔹 Remove booking
        activeBookings.remove(reservationId);

        System.out.println("Cancellation Successful!");
        System.out.println("Room Released: " + r.roomId);
    }

    // Display rollback stack
    public void displayRollbackStack() {
        System.out.println("\nRollback Stack (Recently Released Rooms): " + rollbackStack);
    }
}

// 🔹 Main Class
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 10.0");

        // 🔹 Inventory
        RoomInventory inventory = new RoomInventory();

        // 🔹 Cancellation Service
        CancellationService service = new CancellationService();

        // 🔹 Simulate confirmed bookings
        service.addBooking(new Reservation("RES101", "Single Room", "SI101"));
        service.addBooking(new Reservation("RES102", "Double Room", "DO102"));

        // 🔹 Cancel bookings
        service.cancelBooking("RES101", inventory);
        service.cancelBooking("RES999", inventory); // invalid
        service.cancelBooking("RES102", inventory);

        // 🔹 Show rollback stack
        service.displayRollbackStack();

        // 🔹 Show updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication terminated.");
    }
}