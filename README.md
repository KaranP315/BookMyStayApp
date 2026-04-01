import java.util.HashMap;
import java.util.Map;

/**
* The RoomInventory class centralizes the management of hotel room availability.
* It uses a HashMap to ensure fast lookups and a single source of truth.
* * @author Niranjan Manivannan
* @version 3.0
  */
  class RoomInventory {
  // Centralized data structure: Maps Room Type (Key) to Available Count (Value)
  private Map<String, Integer> inventory;

  /**
    * Constructor initializes the inventory system.
      */
      public RoomInventory() {
      this.inventory = new HashMap<>();
      }

  /**
    * Registers or updates a room type in the inventory.
    * @param type The category of the room (e.g., "Suite")
    * @param count Initial number of rooms available
      */
      public void addRoomType(String type, int count) {
      inventory.put(type, count);
      System.out.println("Registered: " + type + " with " + count + " rooms.");
      }

  /**
    * Retrieves current availability for a specific type.
    * Demonstrates O(1) constant time lookup.
      */
      public int getAvailability(String type) {
      return inventory.getOrDefault(type, 0);
      }

  /**
    * Updates availability after a booking or checkout.
    * @param type Room type to update
    * @param change Positive for checkout, negative for booking
      */
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

  /**
    * Displays the full state of the inventory.
      */
      public void displayInventory() {
      System.out.println("\n--- Current Inventory State ---");
      for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue() + " available");
      }
      System.out.println("-------------------------------\n");
      }
      }

public class UseCase3HotelBookingApp {
public static void main(String[] args) {
System.out.println("Hotel Booking System v3.0 - Inventory Management\n");

        // 1. Initialize Inventory Component
        RoomInventory hotelInventory = new RoomInventory();

        // 2. Register Room Types (Populating the Map)
        hotelInventory.addRoomType("Deluxe", 5);
        hotelInventory.addRoomType("Suite", 2);
        hotelInventory.addRoomType("Standard", 10);

        // 3. Display Initial State
        hotelInventory.displayInventory();

        // 4. Simulate a Booking (Update State)
        System.out.println("Action: Guest books 1 Deluxe room.");
        hotelInventory.updateAvailability("Deluxe", -1);

        // 5. Simulate a Checkout (Update State)
        System.out.println("Action: Guest checks out of 1 Suite.");
        hotelInventory.updateAvailability("Suite", 1);

        // 6. Final State Display
        hotelInventory.displayInventory();
    }
}