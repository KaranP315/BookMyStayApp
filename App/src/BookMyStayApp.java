abstract class Room {
    private String type;
    private int numberOfBeds;
    private double size;
    private double price;

    public Room(String type, int numberOfBeds, double size, double price) {
        this.type = type;
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price: ₹" + price);
    }
}
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 200, 1500);
    }
}
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 350, 2500);
    }
}
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 600, 5000);
    }
}


public class UseCase2 {

    public static void main(String[] args) {

        // Create room objects (Polymorphism)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability (intentionally primitive)
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display details
        System.out.println("=== ROOM DETAILS & AVAILABILITY ===\n");

        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable + "\n");

        System.out.println("=== END ===");
    }
}