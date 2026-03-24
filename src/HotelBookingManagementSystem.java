/**
 * Use Case 7: Add-On Service Selection
 * Demonstrates Map + List for one-to-many relationship and cost aggregation.
 *
 * @author Sabariysh
 * @version 7.0
 */

import java.util.*;

// 🔹 Service Class
class AddOnService {
    private String name;
    private double cost;

    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String toString() {
        return name + " (₹" + cost + ")";
    }
}

// 🔹 Add-On Service Manager
class AddOnServiceManager {

    // Map: Reservation ID → List of Services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Service added to " + reservationId + ": " + service);
    }

    // Display services
    public void displayServices(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services added.");
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId + ":");
        for (AddOnService s : services) {
            System.out.println(s);
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);
        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }
        return total;
    }
}

// 🔹 Main Class
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 7.0");

        // Sample reservation ID
        String reservationId = "RES101";

        // Add-On Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // 🔹 Add services
        manager.addService(reservationId, new AddOnService("Breakfast", 500));
        manager.addService(reservationId, new AddOnService("WiFi", 200));
        manager.addService(reservationId, new AddOnService("Spa", 1500));

        // 🔹 Display services
        manager.displayServices(reservationId);

        // 🔹 Total cost
        double total = manager.calculateTotalCost(reservationId);
        System.out.println("\nTotal Add-On Cost: ₹" + total);

        System.out.println("\nNote: Booking and inventory remain unchanged.");
        System.out.println("Application terminated.");
    }
}