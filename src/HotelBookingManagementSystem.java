/**
 * Use Case 8: Booking History & Reporting
 * Demonstrates List for historical tracking and reporting service.
 *
 * @author Sabariysh
 * @version 8.0
 */

import java.util.*;

// 🔹 Reservation Class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getRoomType() {
        return roomType;
    }

    public String toString() {
        return "Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType;
    }
}

// 🔹 Booking History
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    // Add booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Get all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// 🔹 Reporting Service
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {
        System.out.println("\nBooking History:\n");

        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Summary report
    public void generateSummary(List<Reservation> reservations) {

        Map<String, Integer> summary = new HashMap<>();

        for (Reservation r : reservations) {
            summary.put(r.getRoomType(),
                    summary.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("\nBooking Summary Report:");

        for (Map.Entry<String, Integer> entry : summary.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue() + " bookings");
        }
    }
}

// 🔹 Main Class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 8.0");

        // 🔹 Booking History
        BookingHistory history = new BookingHistory();

        // 🔹 Add confirmed bookings
        history.addReservation(new Reservation("RES101", "Arun", "Single Room"));
        history.addReservation(new Reservation("RES102", "Priya", "Double Room"));
        history.addReservation(new Reservation("RES103", "Rahul", "Single Room"));
        history.addReservation(new Reservation("RES104", "Sneha", "Suite Room"));

        // 🔹 Reporting
        BookingReportService reportService = new BookingReportService();

        // Display all bookings
        reportService.displayAllBookings(history.getAllReservations());

        // Generate summary
        reportService.generateSummary(history.getAllReservations());

        System.out.println("\nApplication terminated.");
    }
}