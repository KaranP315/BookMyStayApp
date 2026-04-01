import java.io.*;
import java.util.*;

// The Serializable interface tells Java this object can be converted to bytes
class HotelState implements Serializable {
    private static final long serialVersionUID = 1L;
    Map<String, Integer> inventory;
    List<String> confirmedBookings;

    public HotelState(Map<String, Integer> inventory, List<String> confirmedBookings) {
        this.inventory = inventory;
        this.confirmedBookings = confirmedBookings;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "hotel_data.ser";

    public void saveSystemState(Map<String, Integer> inventory, List<String> bookings) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            HotelState state = new HotelState(inventory, bookings);
            oos.writeObject(state);
            System.out.println("SHUTDOWN: System state successfully persisted to " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("ERROR: Failed to save state. " + e.getMessage());
        }
    }

    public HotelState loadSystemState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("STARTUP: No persistence file found. Starting fresh.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("RECOVERY: Loading data from " + FILE_NAME + "...");
            return (HotelState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("RECOVERY ERROR: Data corrupted. Starting fresh.");
            return null;
        }
    }
}

public class UC12 {
    public static void main(String[] args) {
        System.out.println("Hotel Booking System v12.0 - Persistence & Recovery\n");

        PersistenceService persistence = new PersistenceService();

        // 1. Attempt System Recovery
        HotelState recoveredState = persistence.loadSystemState();

        Map<String, Integer> inventory;
        List<String> bookings;

        if (recoveredState != null) {
            inventory = recoveredState.inventory;
            bookings = recoveredState.confirmedBookings;
            System.out.println("System Restored. Current Bookings: " + bookings);
        } else {
            inventory = new HashMap<>();
            inventory.put("Deluxe", 10);
            bookings = new ArrayList<>();
        }

        // 2. Simulate new activity
        System.out.println("Action: New booking for 'Niranjan'");
        bookings.add("Niranjan (Deluxe)");
        inventory.put("Deluxe", inventory.get("Deluxe") - 1);

        // 3. Persist before exit
        persistence.saveSystemState(inventory, bookings);
    }
}