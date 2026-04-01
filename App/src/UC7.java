import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AddOnService {
    private String name;
    private double price;

    public AddOnService(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        this.reservationServices = new HashMap<>();
    }

    public void addServiceToReservation(String reservationId, AddOnService service) {
        reservationServices.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
        System.out.println("Added " + service.getName() + " to Reservation: " + reservationId);
    }

    public double calculateTotalExtraCost(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);
        if (services == null) return 0.0;

        double total = 0;
        for (AddOnService service : services) {
            total += service.getPrice();
        }
        return total;
    }

    public void displayServicesForReservation(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);
        System.out.println("\n--- Add-on Services for " + reservationId + " ---");
        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
        } else {
            services.forEach(System.out::println);
            System.out.println("Total Extra Cost: $" + calculateTotalExtraCost(reservationId));
        }
        System.out.println("------------------------------------------");
    }
}

public class UC7 {
    public static void main(String[] args) {
        System.out.println("Hotel Booking System v7.0 - Add-On Services\n");

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        AddOnService breakfast = new AddOnService("Buffet Breakfast", 25.0);
        AddOnService spa = new AddOnService("Spa Treatment", 120.0);
        AddOnService wifi = new AddOnService("Premium WiFi", 15.0);

        String resId = "RES-1001";

        serviceManager.addServiceToReservation(resId, breakfast);
        serviceManager.addServiceToReservation(resId, spa);
        serviceManager.addServiceToReservation(resId, wifi);

        serviceManager.displayServicesForReservation(resId);

        String emptyResId = "RES-1002";
        serviceManager.displayServicesForReservation(emptyResId);
    }
}