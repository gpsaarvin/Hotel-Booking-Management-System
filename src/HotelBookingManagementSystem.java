/**
 * Use Case 5: Booking Request Queue (FIFO)
 * Demonstrates Queue for fair booking request handling.
 *
 * @author Sabariysh
 * @version 5.0
 */

import java.util.*;

// 🔹 Reservation Class
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return "Guest: " + guestName + " | Room: " + roomType;
    }
}

// 🔹 Booking Queue Manager
class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    // Add request to queue
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Request added: " + reservation);
    }

    // Display all requests
    public void displayQueue() {
        System.out.println("\nCurrent Booking Queue:");
        for (Reservation r : queue) {
            System.out.println(r);
        }
    }
}

// 🔹 Main Class
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 5.0");

        // Initialize queue
        BookingQueue bookingQueue = new BookingQueue();

        // 🔹 Add booking requests (FIFO order)
        bookingQueue.addRequest(new Reservation("Arun", "Single Room"));
        bookingQueue.addRequest(new Reservation("Priya", "Double Room"));
        bookingQueue.addRequest(new Reservation("Rahul", "Suite Room"));

        // 🔹 Display queue
        bookingQueue.displayQueue();

        System.out.println("\nNote: Requests are stored in arrival order (FIFO).");
        System.out.println("No rooms allocated yet.");

        System.out.println("\nApplication terminated.");
    }
}