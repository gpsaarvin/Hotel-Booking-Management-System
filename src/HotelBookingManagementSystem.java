/**
 * Use Case 12: Data Persistence & System Recovery
 * Demonstrates serialization, deserialization, and recovery.
 *
 * @author Sabariysh
 * @version 12.0
 */

import java.io.*;
import java.util.*;

// 🔹 Reservation Class (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// 🔹 Inventory Class (Serializable)
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void display() {
        System.out.println("Inventory: " + inventory);
    }
}

// 🔹 Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "hotel_data.ser";

    // 🔹 Save data
    public static void save(RoomInventory inventory, List<Reservation> history) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(inventory);
            oos.writeObject(history);

            System.out.println("Data saved successfully!");

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // 🔹 Load data
    public static Object[] load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            RoomInventory inventory = (RoomInventory) ois.readObject();
            List<Reservation> history = (List<Reservation>) ois.readObject();

            System.out.println("Data loaded successfully!");
            return new Object[]{inventory, history};

        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
            return null;
        }
    }
}

// 🔹 Main Class
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 12.0");

        RoomInventory inventory;
        List<Reservation> history;

        // 🔹 Try loading previous state
        Object[] data = PersistenceService.load();

        if (data != null) {
            inventory = (RoomInventory) data[0];
            history = (List<Reservation>) data[1];
        } else {
            inventory = new RoomInventory();
            history = new ArrayList<>();
        }

        // 🔹 Simulate new booking
        history.add(new Reservation("RES201", "Arun", "Single Room"));

        System.out.println("\nCurrent Booking History:");
        for (Reservation r : history) {
            System.out.println(r);
        }

        System.out.println();
        inventory.display();

        // 🔹 Save state before exit
        PersistenceService.save(inventory, history);

        System.out.println("\nApplication terminated. Restart to see recovery.");
    }
}