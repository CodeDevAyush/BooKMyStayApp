import java.util.*;

// -------------------- Room --------------------
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 100);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 180);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 300);
    }
}

// -------------------- Reservation --------------------
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room: " + roomType);
    }
}

// -------------------- Inventory --------------------
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public boolean allocateRoom(String roomType) {
        int available = getAvailability(roomType);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\n===== Remaining Inventory =====");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// -------------------- Queue --------------------
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        queue.add(r);
        System.out.println("Added: " + r.getGuestName());
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// -------------------- Booking Service --------------------
class BookingService {
    private RoomInventory inventory;
    private Map<String, Set<String>> allocatedRoomIDs;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRoomIDs = new HashMap<>();
    }

    // Generate unique room ID
    private String generateRoomID(String roomType) {
        Set<String> ids = allocatedRoomIDs.getOrDefault(roomType, new HashSet<>());
        String id = roomType.substring(0, 1) + String.format("%03d", ids.size() + 1);
        ids.add(id);
        allocatedRoomIDs.put(roomType, ids);
        return id;
    }

    public void confirmReservation(Reservation r) {
        String type = r.getRoomType();

        if (inventory.allocateRoom(type)) {
            String roomID = generateRoomID(type);

            System.out.println("✅ Confirmed: " + r.getGuestName() +
                    " | " + type + " | Room ID: " + roomID);
        } else {
            System.out.println("❌ Failed: " + r.getGuestName() +
                    " | " + type + " (No rooms available)");
        }
    }
}

// -------------------- MAIN --------------------
public class Main {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        BookingService service = new BookingService(inventory);

        // Add requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Suite Room"));
        queue.addRequest(new Reservation("Diana", "Single Room"));
        queue.addRequest(new Reservation("Eve", "Single Room"));

        // Process queue
        System.out.println("\n===== Processing Bookings =====");
        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            service.confirmReservation(r);
        }

        // Final inventory
        inventory.displayInventory();
    }
}