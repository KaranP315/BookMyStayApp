import java.util.ArrayList;
import java.util.List;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double totalCost;

    public Reservation(String reservationId, String guestName, String roomType, double totalCost) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return String.format("ID: %-10s | Guest: %-12s | Type: %-10s | Cost: $%.2f",
                reservationId, guestName, roomType, totalCost);
    }

    public double getTotalCost() { return totalCost; }
}

class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        this.history = new ArrayList<>();
    }

    public void recordReservation(Reservation res) {
        history.add(res);
        System.out.println("History Updated: " + res.getReservationId() + " recorded.");
    }

    public List<Reservation> getAllRecords() {
        return new ArrayList<>(history);
    }
}

class BookingReportService {
    public void generateSummary(List<Reservation> records) {
        System.out.println("\n======= MANAGEMENT SUMMARY REPORT =======");
        System.out.println("Total Bookings: " + records.size());

        double revenue = 0;
        for (Reservation res : records) {
            revenue += res.getTotalCost();
            System.out.println(res);
        }

        System.out.println("-----------------------------------------");
        System.out.printf("Total Revenue Generated: $%.2f\n", revenue);
        System.out.println("=========================================\n");
    }
}

public class UC8 {
    public static void main(String[] args) {
        System.out.println("Hotel Booking System v8.0 - History & Reporting\n");

        BookingHistory historyStore = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        historyStore.recordReservation(new Reservation("RES-001", "Alice", "Deluxe", 250.0));
        historyStore.recordReservation(new Reservation("RES-002", "Bob", "Suite", 500.0));
        historyStore.recordReservation(new Reservation("RES-003", "Charlie", "Standard", 150.0));

        List<Reservation> currentHistory = historyStore.getAllRecords();
        reportService.generateSummary(currentHistory);
    }
}