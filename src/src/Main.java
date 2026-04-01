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

public class Main {
    public static void main(String[] args) {

        // Application name and version
        String appName = "Hotel Booking System";
        String version = "v1.0";

        // Static availability
        int singleRoomAvailable = 5;
        int doubleRoomAvailable = 3;
        int suiteRoomAvailable = 2;

        // Polymorphism
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        System.out.println("===== Book My Stay App =====\n");

        // Welcome message
        System.out.println("=================================");
        System.out.println("Welcome to the " + appName);
        System.out.println("Version: " + version);
        System.out.println("=================================\n");

        // Display rooms
        single.displayRoomDetails();
        System.out.println("Available: " + singleRoomAvailable + "\n");

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleRoomAvailable + "\n");

        suite.displayRoomDetails();
        System.out.println("Available: " + suiteRoomAvailable);
    }
}