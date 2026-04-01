class SearchService {

    private RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms() {

        System.out.println("=== AVAILABLE ROOMS ===");

        // Create room objects (domain)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Check availability (READ ONLY)
        displayIfAvailable(single);
        displayIfAvailable(doubleRoom);
        displayIfAvailable(suite);
    }

    private void displayIfAvailable(Room room) {
        int available = inventory.getAvailability(room.getType());

        // Defensive check
        if (available > 0) {
            room.displayRoomDetails();
            System.out.println("Available: " + available + "\n");
        }
    }
}

public class UC4 {

    public static void main(String[] args) {

        // Initialize inventory (state)
        RoomInventory inventory = new RoomInventory();

        // Inject into search service
        SearchService searchService = new SearchService(inventory);

        // Perform search (READ ONLY)
        searchService.searchAvailableRooms();
    }
}