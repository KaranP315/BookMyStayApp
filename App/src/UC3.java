import java.util.HashMap;
import java.util.Map;

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        this.inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
        System.out.println("Registered: " + type + " with " + count + " rooms.");
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void updateAvailability(String type, int change) {
        if (inventory.containsKey(type)) {
            int currentCount = inventory.get(type);
            int newCount = currentCount + change;

            if (newCount >= 0) {
                inventory.put(type, newCount);
                System.out.println("Updated " + type + ": New Count = " + newCount);
            } else {
                System.out.println("Error: Insufficient inventory for " + type);
            }
        } else {
            System.out.println("Error: Room type " + type + " not found.");
        }
    }

    public void displayInventory() {
        System.out.println("\n--- Current Inventory State ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " available");
        }
        System.out.println("-------------------------------\n");
    }
}

public class UC3 {
    public static void main(String[] args) {
        System.out.println("Hotel Booking System v3.0 - Inventory Management\n");

        RoomInventory hotelInventory = new RoomInventory();

        hotelInventory.addRoomType("Deluxe", 5);
        hotelInventory.addRoomType("Suite", 2);
        hotelInventory.addRoomType("Standard", 10);

        hotelInventory.displayInventory();

        System.out.println("Action: Guest books 1 Deluxe room.");
        hotelInventory.updateAvailability("Deluxe", -1);

        System.out.println("Action: Guest checks out of 1 Suite.");
        hotelInventory.updateAvailability("Suite", 1);

        hotelInventory.displayInventory();
    }
}