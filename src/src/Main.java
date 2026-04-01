import java.util.*;

/* Custom Exception */
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

/* Room Inventory */
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public void validateAvailability(String roomType) throws InvalidBookingException {
        int available = inventory.get(roomType);
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }
    }

    public void bookRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms Available: " + inventory.get(type));
        }
    }
}

/* Validator */
class InvalidBookingValidator {

    public void validateBooking(String guestName, String roomType, int nights)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (nights <= 0) {
            throw new InvalidBookingException("Number of nights must be greater than zero.");
        }

        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty.");
        }
    }
}

/* Main */
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        InvalidBookingValidator validator = new InvalidBookingValidator();

        while (true) {
            try {
                System.out.println("\n--- New Booking Request ---");

                System.out.print("Enter Guest Name: ");
                String guestName = sc.nextLine();

                System.out.print("Enter Room Type (Single/Double/Suite): ");
                String roomType = sc.nextLine();

                System.out.print("Enter Nights: ");
                int nights = sc.nextInt();
                sc.nextLine(); // fix input issue

                validator.validateBooking(guestName, roomType, nights);
                inventory.validateRoomType(roomType);
                inventory.validateAvailability(roomType);

                inventory.bookRoom(roomType);

                System.out.println("Booking successful for " + guestName);

            } catch (InvalidBookingException e) {
                System.out.println("Booking failed: " + e.getMessage());

            } catch (Exception e) {
                System.out.println("Unexpected error occurred.");
            }

            inventory.displayInventory();

            System.out.print("\nContinue booking? (yes/no): ");
            String choice = sc.nextLine();

            if (!choice.equalsIgnoreCase("yes")) {
                break;
            }
        }

        sc.close();
    }
}