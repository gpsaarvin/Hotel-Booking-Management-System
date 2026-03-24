/**
 * Use Case 11: Concurrent Booking Simulation
 * Demonstrates thread safety using synchronized blocks.
 *
 * @author Sabariysh
 * @version 11.0
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

// 🔹 Shared Inventory (Critical Resource)
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
    }

    // 🔹 Synchronized method (critical section)
    public synchronized boolean bookRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }

        return false;
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory: " + inventory);
    }
}

// 🔹 Booking Processor (Thread)
class BookingProcessor implements Runnable {

    private Queue<Reservation> queue;
    private RoomInventory inventory;

    public BookingProcessor(Queue<Reservation> queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation r;

            // 🔹 Synchronize queue access
            synchronized (queue) {
                if (queue.isEmpty()) return;
                r = queue.poll();
            }

            // 🔹 Process booking
            boolean success = inventory.bookRoom(r.roomType);

            if (success) {
                System.out.println(Thread.currentThread().getName() +
                        " → Booking SUCCESS for " + r.guestName);
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " → Booking FAILED for " + r.guestName);
            }
        }
    }
}

// 🔹 Main Class
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 11.0");

        // 🔹 Shared Queue
        Queue<Reservation> queue = new LinkedList<>();

        queue.add(new Reservation("Arun", "Single Room"));
        queue.add(new Reservation("Priya", "Single Room"));
        queue.add(new Reservation("Rahul", "Single Room"));
        queue.add(new Reservation("Sneha", "Single Room"));

        // 🔹 Shared Inventory
        RoomInventory inventory = new RoomInventory();

        // 🔹 Multiple Threads (Simulating users)
        Thread t1 = new Thread(new BookingProcessor(queue, inventory), "Thread-1");
        Thread t2 = new Thread(new BookingProcessor(queue, inventory), "Thread-2");

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final inventory
        inventory.displayInventory();

        System.out.println("\nApplication terminated.");
    }
}