import java.util.*;

/* Reservation class */
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    boolean cancelled;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.cancelled = false;
    }

    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }
    public boolean isCancelled() { return cancelled; }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Guest Name: " + guestName);
        System.out.println("Room Type: " + roomType);
        System.out.println("Room ID: " + roomId);
        System.out.println("Cancelled: " + cancelled);
        System.out.println("-----------------------------");
    }
}

/* Inventory management */
class InventoryManager {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryManager() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public boolean isValidRoom(String roomType) {
        return inventory.containsKey(roomType);
    }

    public boolean hasAvailability(String roomType) {
        return inventory.get(roomType) > 0;
    }

    public void decrement(String roomType) {
        if (hasAvailability(roomType)) {
            inventory.put(roomType, inventory.get(roomType) - 1);
        }
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms Available: " + inventory.get(type));
        }
    }
}

/* Cancellation Service */
class CancellationService {

    private Map<String, Reservation> reservations;
    private InventoryManager inventory;
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(Map<String, Reservation> reservations, InventoryManager inventory) {
        this.reservations = reservations;
        this.inventory = inventory;
    }

    public void cancelBooking(String reservationId) {

        if (!reservations.containsKey(reservationId)) {
            System.out.println("Cancellation failed: Reservation does not exist.");
            return;
        }

        Reservation r = reservations.get(reservationId);

        if (r.isCancelled()) {
            System.out.println("Cancellation failed: Already cancelled.");
            return;
        }

        rollbackStack.push(r.getRoomId());
        inventory.increment(r.getRoomType());
        r.setCancelled(true);

        System.out.println("Booking cancelled successfully.");
        System.out.println("Room released: " + r.getRoomId());
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack: " + rollbackStack);
    }
}

/* Main */
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        InventoryManager inventory = new InventoryManager();
        Map<String, Reservation> reservations = new HashMap<>();

        CancellationService cancellationService =
                new CancellationService(reservations, inventory);

        int choice;

        do {
            System.out.println("\n1. Confirm Booking");
            System.out.println("2. Cancel Booking");
            System.out.println("3. Show Reservations");
            System.out.println("4. Show Rollback Stack");
            System.out.println("5. Show Inventory");
            System.out.println("6. Exit");

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

                    if (!inventory.isValidRoom(type)) {
                        System.out.println("Invalid room type!");
                        break;
                    }

                    if (!inventory.hasAvailability(type)) {
                        System.out.println("No rooms available!");
                        break;
                    }

                    String roomId = "ROOM-" + (reservations.size() + 1);

                    Reservation r = new Reservation(id, name, type, roomId);
                    reservations.put(id, r);

                    inventory.decrement(type);

                    System.out.println("Booking confirmed. Room allocated: " + roomId);
                    break;

                case 2:
                    System.out.print("Enter Reservation ID to cancel: ");
                    String cancelId = sc.nextLine();
                    cancellationService.cancelBooking(cancelId);
                    break;

                case 3:
                    if (reservations.isEmpty()) {
                        System.out.println("No reservations found.");
                    } else {
                        for (Reservation res : reservations.values()) {
                            res.display();
                        }
                    }
                    break;

                case 4:
                    cancellationService.showRollbackStack();
                    break;

                case 5:
                    inventory.displayInventory();
                    break;

                case 6:
                    System.out.println("Exiting system.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);

        sc.close();
    }
}