import java.util.*;

class BookingService {

    private RoomInventory inventory;
    private BookingQueue bookingQueue;

    // Track allocated rooms (prevents duplicates)
    private Set<String> allocatedRoomIds;

    // Track room type → assigned room IDs
    private Map<String, Set<String>> roomAllocations;

    public BookingService(RoomInventory inventory, BookingQueue bookingQueue) {
        this.inventory = inventory;
        this.bookingQueue = bookingQueue;
        this.allocatedRoomIds = new HashSet<>();
        this.roomAllocations = new HashMap<>();
    }

    public void processBookings() {

        System.out.println("\n=== PROCESSING BOOKINGS ===");

        while (true) {
            Reservation request = bookingQueue.pollRequest();

            if (request == null) {
                System.out.println("No more requests.");
                break;
            }

            String roomType = request.getRoomType();

            int available = inventory.getAvailability(roomType);

            if (available > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(roomType);

                // Ensure uniqueness
                while (allocatedRoomIds.contains(roomId)) {
                    roomId = generateRoomId(roomType);
                }

                allocatedRoomIds.add(roomId);

                // Map allocation
                roomAllocations.putIfAbsent(roomType, new HashSet<>());
                roomAllocations.get(roomType).add(roomId);

                // Update inventory (CRITICAL)
                inventory.updateAvailability(roomType, available - 1);

                System.out.println("Booking CONFIRMED for " +
                        request.getGuestName() +
                        " | Room ID: " + roomId);

            } else {
                System.out.println("Booking FAILED for " +
                        request.getGuestName() +
                        " | No availability for " + roomType);
            }
        }
    }

    private String generateRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 4);
    }
}

public class UseCase6 {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        // Add requests (FIFO)
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Suite Room"));
        queue.addRequest(new Reservation("David", "Suite Room"));
        queue.addRequest(new Reservation("Eve", "Suite Room")); // likely to fail

        BookingService service = new BookingService(inventory, queue);

        service.processBookings();

        System.out.println("\n=== FINAL INVENTORY ===");
        inventory.displayInventory();
    }
}