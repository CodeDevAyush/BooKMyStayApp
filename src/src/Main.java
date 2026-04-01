import java.util.HashMap;
import java.util.Map;

// Room domain classes
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price per night: $" + price);
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

// Centralized inventory
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public Map<String, Integer> getAllInventory() {
        return new HashMap<>(inventory); // defensive copy
    }
}

// Search service
class SearchService {

    private RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("===== Current Room Inventory =====");
        for (Map.Entry<String, Integer> entry : inventory.getAllInventory().entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
        System.out.println();
    }

    // Display only available rooms
    public void displayAvailableRooms(Room[] rooms) {
        System.out.println("===== Available Rooms =====\n");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());
            if (available > 0) {
                room.displayRoomDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

// Main class
public class Main {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize rooms
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Search service
        SearchService searchService = new SearchService(inventory);

        // Display inventory
        searchService.displayInventory();

        // Update example
        System.out.println("Updating availability for Single Room...\n");
        inventory.updateAvailability("Single Room", 4);

        // Display available rooms
        searchService.displayAvailableRooms(rooms);

        // Display updated inventory
        searchService.displayInventory();
    }
}