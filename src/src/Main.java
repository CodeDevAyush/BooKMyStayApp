import java.util.*;

/* Reservation class representing a confirmed booking */
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private int nights;

    public Reservation(String reservationId, String guestName, String roomType, int nights) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Guest Name: " + guestName);
        System.out.println("Room Type: " + roomType);
        System.out.println("Nights: " + nights);
        System.out.println("----------------------------");
    }
}

/* BookingHistory stores reservations */
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Reservation added to booking history.");
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

/* Report service */
class BookingReportService {

    public void generateReport(List<Reservation> reservations) {

        if (reservations.isEmpty()) {
            System.out.println("No booking history available.");
            return;
        }

        System.out.println("\n===== BOOKING HISTORY REPORT =====");

        for (Reservation r : reservations) {
            r.displayReservation();
        }

        System.out.println("Total Bookings: " + reservations.size());
    }
}

/* Main class */
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        int choice;

        do {
            System.out.println("\n1. Confirm Booking");
            System.out.println("2. View Booking History");
            System.out.println("3. Generate Report");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {

                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String id = sc.nextLine();

                    System.out.print("Enter Guest Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Room Type: ");
                    String room = sc.nextLine();

                    System.out.print("Enter Nights: ");
                    int nights = sc.nextInt();
                    sc.nextLine(); // fix input bug

                    Reservation r = new Reservation(id, name, room, nights);
                    history.addReservation(r);
                    break;

                case 2:
                    List<Reservation> bookings = history.getAllReservations();

                    if (bookings.isEmpty()) {
                        System.out.println("No bookings found.");
                    } else {
                        for (Reservation res : bookings) {
                            res.displayReservation();
                        }
                    }
                    break;

                case 3:
                    reportService.generateReport(history.getAllReservations());
                    break;

                case 4:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 4);

        sc.close();
    }
}