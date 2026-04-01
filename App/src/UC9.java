import java.util.*;

class Reservation {
    String id;
    String type;
    String roomId;

    public Reservation(String id, String type, String roomId) {
        this.id = id;
        this.type = type;
        this.roomId = roomId;
    }
}

class CancellationService {
    private Map<String, Integer> inventory;
    private Map<String, Reservation> activeBookings;
    private Stack<String> releasedRooms; // Rollback structure

    public CancellationService(Map<String, Integer> inventory, Map<String, Reservation> activeBookings) {
        this.inventory = inventory;
        this.activeBookings = activeBookings;
        this.releasedRooms = new Stack<>();
    }

    public void cancelBooking(String reservationId) {
        System.out.println("Processing cancellation for: " + reservationId);

        // 1. Validation: Ensure reservation exists
        if (!activeBookings.containsKey(reservationId)) {
            System.err.println("Error: Reservation ID " + reservationId + " not found or already cancelled.");
            return;
        }

        // 2. Identify details for rollback
        Reservation res = activeBookings.get(reservationId);

        // 3. Rollback Logic: Add Room ID to Stack (LIFO)
        releasedRooms.push(res.roomId);

        // 4. Inventory Restoration
        int currentCount = inventory.getOrDefault(res.type, 0);
        inventory.put(res.type, currentCount + 1);

        // 5. Remove from active bookings
        activeBookings.remove(reservationId);

        System.out.println("SUCCESS: " + res.type + " " + res.roomId + " has been rolled back to inventory.");
        System.out.println("Recent Rollbacks (Stack): " + releasedRooms + "\n");
    }
}

public class UC9 {
    public static void main(String[] args) {
        System.out.println("Hotel Booking System v10.0 - Cancellation & Rollback\n");

        // Initial State
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Suite", 5);

        Map<String, Reservation> activeBookings = new HashMap<>();
        activeBookings.put("RES-101", new Reservation("RES-101", "Suite", "S-501"));
        activeBookings.put("RES-102", new Reservation("RES-102", "Suite", "S-502"));

        CancellationService service = new CancellationService(inventory, activeBookings);

        // Perform Cancellations
        service.cancelBooking("RES-102"); // Should rollback S-502 first
        service.cancelBooking("RES-101"); // Should rollback S-501

        // Attempt duplicate cancellation
        service.cancelBooking("RES-102");
    }
}