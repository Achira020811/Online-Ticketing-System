package Updated;

import java.util.Scanner;

import java.util.logging.Logger;



public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static TicketPool ticketPool;
    private static Thread vendorThread;
    private static Thread customerThread;
    private static Vendor vendor;
    private static Customer customer;

    public static void main(String[] args) {
        // Create the configuration object
        Configuration config = new Configuration();

        // Start menu loop
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Display the main menu
            System.out.println("Ticket Pool System Main Menu");
            System.out.println("1. Load Configuration from File");
            System.out.println("2. Set Configuration Manually");
            System.out.println("3. View Current Configuration");
            System.out.println("4. Start the Ticket System");
            System.out.println("5. Save Current Configuration");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline character

            switch (choice) {
                case 1:
                    // Load configuration from file
                    config.loadConfiguration();
                    if (config.validateConfiguration()) {
                        System.out.println("Configuration loaded successfully.");
                    } else {
                        System.out.println("Invalid configuration file. Using default values.");
                    }
                    break;
                case 2:
                    // Manually set configuration
                    config.promptUserForConfig();
                    break;
                case 3:
                    // View current configuration
                    System.out.println("Current Configuration:");
                    System.out.println("Max Ticket Capacity: " + config.getMaxTicketCapacity());
                    System.out.println("Max Tickets: " + config.getMaxTickets());
                    System.out.println("Ticket Release Rate: " + config.getTicketReleaseRate());
                    System.out.println("Customer Retrieval Rate: " + config.getCustomerRetrievalRate());
                    break;
                case 4:
                    // Start the ticket system (if valid configuration)
                    if (config.validateConfiguration()) {
                        startTicketSystem(config);
                    } else {
                        System.out.println("Invalid configuration. Please load or set the configuration first.");
                    }
                    break;
                case 5:
                    // Save configuration
                    config.saveConfiguration();
                    break;
                case 6:
                    System.out.println("Exiting the system.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 6);

        scanner.close();
    }

    // Method to start the ticket system with the given configuration
    private static void startTicketSystem(Configuration config) {
        // Initialize the ticket pool with max capacity and max tickets
        ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getMaxTickets());

        // Create vendor and customer objects with configuration values
        vendor = new Vendor(ticketPool, config.getTicketReleaseRate());
        customer = new Customer(ticketPool, config.getCustomerRetrievalRate());

        // Start the vendor thread (add tickets)
        vendorThread = new Thread(vendor);
        vendorThread.start();

        try {
            // Wait for the vendor to finish adding tickets
            vendorThread.join();  // Wait for vendor to finish adding tickets
//            System.out.println("Vendor finished adding tickets.");

            // After the vendor finishes, start the customer thread (remove tickets)
            customerThread = new Thread(customer);
            customerThread.start();

            // Wait for the customer to finish removing tickets
            customerThread.join();  // Wait for customer to finish removing tickets
//            System.out.println("Customer finished buying tickets.");

        } catch (InterruptedException e) {
            logger.warning("Main thread interrupted.");
        }

        // After both processes (adding and removing) finish, return to the main menu
        System.out.println("Both ticket adding and buying processes have finished. Returning to the main menu.");
    }
}
