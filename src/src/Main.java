import java.io.*;
import java.util.*;

/* Reservation class (Serializable) */
class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Guest Name: " + guestName);
        System.out.println("Room Type: " + roomType);
        System.out.println("---------------------------");
    }
}

/* System State */
class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

/* Persistence Service */
class PersistenceService {

    private static final String FILE_NAME = "booking_system_state.dat";

    public void saveState(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state.");
        }
    }

    public SystemState loadState() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) in.readObject();
            System.out.println("System state restored successfully.");
            return state;

        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }
    }
}

/* Main */
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PersistenceService persistenceService = new PersistenceService();

        Map<String, Integer> inventory = new HashMap<>();
        List<Reservation> bookings = new ArrayList<>();

        // Restore state
        SystemState restoredState = persistenceService.loadState();

        if (restoredState != null) {
            inventory = restoredState.inventory;
            bookings = restoredState.bookings;
        } else {
            inventory.put("Single", 5);
            inventory.put("Double", 3);
            inventory.put("Suite", 2);
        }

        int choice;

        do {
            System.out.println("\n1. Create Booking");
            System.out.println("2. View Bookings");
            System.out.println("3. View Inventory");
            System.out.println("4. Save System State");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String id = sc.nextLine();

                    System.out.print("Enter Guest Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Room Type (Single/Double/Suite): ");
                    String type = sc.nextLine();

                    if (!inventory.containsKey(type)) {
                        System.out.println("Invalid room type.");
                        break;
                    }

                    int available = inventory.get(type);

                    if (available > 0) {
                        Reservation r = new Reservation(id, name, type);
                        bookings.add(r);
                        inventory.put(type, available - 1);

                        System.out.println("Booking successful.");
                    } else {
                        System.out.println("Room not available.");
                    }
                    break;

                case 2:
                    if (bookings.isEmpty()) {
                        System.out.println("No bookings found.");
                    } else {
                        for (Reservation r : bookings) {
                            r.display();
                        }
                    }
                    break;

                case 3:
                    System.out.println("\nCurrent Inventory:");
                    for (String key : inventory.keySet()) {
                        System.out.println(key + " Rooms Available: " + inventory.get(key));
                    }
                    break;

                case 4:
                    SystemState state = new SystemState(inventory, bookings);
                    persistenceService.saveState(state);
                    break;

                case 5:
                    System.out.println("Exiting system.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 5);

        sc.close();
    }
}