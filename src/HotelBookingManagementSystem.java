/**
 * Use Case 9: Error Handling & Validation
 * Demonstrates validation, custom exceptions, and fail-fast design.
 *
 * @author Sabariysh
 * @version 9.0
 */

import java.util.*;

// 🔹 Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// 🔹 Inventory Class
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }

    public void reduceAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

// 🔹 Validator Class
class BookingValidator {

    public static void validate(String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        // 🔹 Check valid room type
        if (inventory.getAvailability(roomType) == -1) {
            throw new InvalidBookingException("Invalid room type selected!");
        }

        // 🔹 Check availability
        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }
    }
}

// 🔹 Main Class
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 9.0");

        RoomInventory inventory = new RoomInventory();

        // 🔹 Test inputs (valid + invalid)
        String[] testRequests = {
                "Single Room",
                "Suite Room",     // no availability
                "Luxury Room"     // invalid type
        };

        for (String roomType : testRequests) {

            System.out.println("\nProcessing request for: " + roomType);

            try {
                // 🔹 Validate before booking
                BookingValidator.validate(roomType, inventory);

                // 🔹 If valid → allocate
                inventory.reduceAvailability(roomType);

                System.out.println("Booking Successful for " + roomType);

            } catch (InvalidBookingException e) {
                // 🔹 Graceful failure
                System.out.println("Booking Failed: " + e.getMessage());
            }
        }

        System.out.println("\nApplication continues safely...");
    }
}