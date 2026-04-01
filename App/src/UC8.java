import java.util.HashMap;
import java.util.Map;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class BookingValidator {
    private Map<String, Integer> inventory;

    public BookingValidator(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public void validateBooking(String guestName, String roomType) throws InvalidBookingException {
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Validation Failed: Guest name cannot be empty.");
        }

        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Validation Failed: Room type '" + roomType + "' does not exist.");
        }

        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("Validation Failed: No availability for '" + roomType + "'.");
        }
    }
}

class ReliableBookingSystem {
    private Map<String, Integer> inventory = new HashMap<>();
    private BookingValidator validator;

    public ReliableBookingSystem() {
        inventory.put("Deluxe", 1);
        this.validator = new BookingValidator(inventory);
    }

    public void processBooking(String guestName, String roomType) {
        try {
            System.out.println("Processing request for " + guestName + "...");

            validator.validateBooking(guestName, roomType);

            inventory.put(roomType, inventory.get(roomType) - 1);
            System.out.println("SUCCESS: Booking confirmed for " + guestName);

        } catch (InvalidBookingException e) {
            System.err.println("ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("FATAL: An unexpected error occurred.");
        } finally {
            System.out.println("System Ready for next request.\n");
        }
    }
}

public class UC9 {
    public static void main(String[] args) {
        System.out.println("Hotel Booking System v9.0 - Error Handling & Validation\n");

        ReliableBookingSystem system = new ReliableBookingSystem();

        system.processBooking("Alice", "Deluxe");

        system.processBooking("Bob", "Deluxe");

        system.processBooking("Charlie", "Penthouse");

        system.processBooking("", "Deluxe");
    }
}