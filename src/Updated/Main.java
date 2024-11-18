package Updated;

import java.util.Scanner;
import java.util.logging.*;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static TicketPool ticketPool;
    private static Thread vendorThread;
    private static Thread customerThread;
    private static Vendor vendor;
    private static Customer customer;

    public static void main(String[] args) {
        Configuration config = new Configuration();

        // Load the configuration from file or prompt the user for input
        config.loadConfiguration();

        if (!config.validateConfiguration()) {
            config.promptUserForConfig();
            while (!config.validateConfiguration()) {
                config.promptUserForConfig();
            }
        }

        // Initialize the ticket pool with max capacity
        ticketPool = new TicketPool(config.getMaxTicketCapacity());

        // Create vendor and customer objects
        vendor = new Vendor(ticketPool, config.getTicketReleaseRate());
        customer = new Customer(ticketPool, config.getCustomerRetrievalRate());

        // Display a welcome message and available commands
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Welcome to the Ticket System");
        System.out.println("Enter 'start' to begin, 'stop' to end, 'status' for pool status, 'exit' to exit.");

        // Main loop to handle user commands
        while (true) {
            System.out.print("> ");
            command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "start":
                    if (vendorThread == null || !vendorThread.isAlive()) {
                        vendorThread = new Thread(vendor);
                        customerThread = new Thread(customer);
                        vendorThread.start();
                       try{ vendorThread.join();}
                       catch(InterruptedException e){}
                        customerThread.start();
                        logger.info("Ticket system started.");
                    } else {
                        System.out.println("System is already running.");
                    }
                    break;
                case "stop":
                    if (vendorThread != null && vendorThread.isAlive()) {
                        vendorThread.interrupt();
                        customerThread.interrupt();
                        logger.info("Ticket system stopped.");
                    } else {
                        System.out.println("System is not running.");
                    }
                    break;
                case "status":
                    System.out.println("Current available tickets: " + ticketPool.getAvailableTickets());
                    break;
                case "exit":
                    System.out.println("Exiting the system.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command.");
            }

            // If the ticket pool is full, stop adding tickets but allow customers to remove tickets
            if (ticketPool.isFull()) {
                System.out.println("Ticket pool is full. Vendors will stop adding tickets.");
            }

            // If tickets are sold out, stop all operations
            if (ticketPool.isEmpty()) {
                System.out.println("Tickets are sold out.");
                if (vendorThread != null && vendorThread.isAlive()) {
                    vendorThread.interrupt();
                }
                if (customerThread != null && customerThread.isAlive()) {
                    customerThread.interrupt();
                }
                break; // Exit the loop and terminate the system
            }
        }
    }
}
