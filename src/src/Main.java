import java.util.*;

/* Service class representing an optional add-on */
class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

/* Manager class handling add-on services */
class AddOnServiceManager {

    // Map reservationId -> List of services
    private Map<String, List<Service>> reservationServices = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationId, Service service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);

        System.out.println(service.getName() + " added to reservation " + reservationId);
    }

    // Display services for a reservation
    public void showServices(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        System.out.println("Selected Services:");

        for (Service s : services) {
            System.out.println("- " + s.getName() + " : ₹" + s.getCost());
        }
    }

    // Calculate additional cost
    public double calculateCost(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null)
            return 0;

        double total = 0;

        for (Service s : services) {
            total += s.getCost();
        }

        return total;
    }
}

/* Main class */
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        System.out.print("Enter Reservation ID: ");
        String reservationId = sc.nextLine();

        int choice;

        do {
            System.out.println("\nAdd-On Services");
            System.out.println("1. Breakfast - ₹500");
            System.out.println("2. Airport Pickup - ₹1000");
            System.out.println("3. Extra Bed - ₹800");
            System.out.println("4. Show Selected Services");
            System.out.println("5. Show Total Add-On Cost");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    manager.addService(reservationId, new Service("Breakfast", 500));
                    break;

                case 2:
                    manager.addService(reservationId, new Service("Airport Pickup", 1000));
                    break;

                case 3:
                    manager.addService(reservationId, new Service("Extra Bed", 800));
                    break;

                case 4:
                    manager.showServices(reservationId);
                    break;

                case 5:
                    double cost = manager.calculateCost(reservationId);
                    System.out.println("Total Add-On Cost: ₹" + cost);
                    break;

                case 6:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 6);

        sc.close();
    }
}