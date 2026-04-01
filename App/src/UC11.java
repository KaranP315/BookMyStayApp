import java.util.LinkedList;
import java.util.Queue;

class ConcurrentBookingProcessor {
    private int availableRooms = 1; // Only 1 room left!
    private final Object lock = new Object();

    public void processBooking(String guestName) {
        System.out.println(guestName + " is attempting to book...");

        // Critical Section: Only one thread can enter this block at a time
        synchronized (lock) {
            if (availableRooms > 0) {
                // Simulate processing delay to expose potential race conditions
                try { Thread.sleep(100); } catch (InterruptedException e) {}

                availableRooms--;
                System.out.println("SUCCESS: Room allocated to " + guestName);
            } else {
                System.out.println("FAILURE: No rooms left for " + guestName);
            }
        }
    }

    public int getRemainingRooms() {
        return availableRooms;
    }
}

public class UC11 {
    public static void main(String[] args) {
        System.out.println("Hotel Booking System v11.0 - Concurrent Simulation\n");

        ConcurrentBookingProcessor processor = new ConcurrentBookingProcessor();

        // Simulate two guests clicking 'Book' at the exact same time
        Thread guest1 = new Thread(() -> processor.processBooking("Alice"));
        Thread guest2 = new Thread(() -> processor.processBooking("Bob"));

        guest1.start();
        guest2.start();

        try {
            guest1.join();
            guest2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nFinal Room Count: " + processor.getRemainingRooms());
    }
}