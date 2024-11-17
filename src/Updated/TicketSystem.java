package Updated;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TicketSystem {

    public static boolean isRunning = false;
    private static final ExecutorService executor = Executors.newFixedThreadPool(5);
    private static final Logger logger = Logger.getLogger(TicketSystem.class.getName());
    private static Configuration config;
    private static TicketPool ticketPool;

    public static void main(String[] args) {
        // Setup logging
        setupLogging();

        Scanner scanner = new Scanner(System.in);

        // Main Menu Loop
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("01. Configure System");
            System.out.println("02. Start Ticket Operations");
            System.out.println("03. Stop Ticket Operations");
            System.out.println("04. View Ticket Pool Status");
            System.out.println("05. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    configureSystem(scanner);
                    break;
                case 2:
                    if (!isRunning) {
                        isRunning = true;
                        startTicketOperations();
                    } else {
                        System.out.println("Ticket operations are already running.");
                    }
                    break;
                case 3:
                    stopTicketOperation();
                    break;
                case 4:
                    ticketPoolStatus();
                    break;
                case 5:
                    stopTicketOperation();
                    executor.shutdown();
                    System.out.println("Exiting program...");
                    logger.info("System exited.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Set up logging configuration
    private static void setupLogging() {
        try {
            FileHandler fileHandler = new FileHandler("ticket_system.log", true);
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            System.err.println("Error setting up logging.");
        }
    }

    // Configure system settings
    private static void configureSystem(Scanner scanner) {
        config = new Configuration();
        config.configure(scanner);
        ticketPool = new TicketPool(config.getTotalTickets(), config.getMaxTicketCapacity());
    }

    // Start the ticket operations (vendor and consumer threads)
    private static void startTicketOperations() {
        executor.submit(new Vendor(ticketPool, config.getTicketReleaseRate()));
        executor.submit(new Customer(ticketPool, "Normal", config.getCustomerRetrievalRate()));
        executor.submit(new Customer(ticketPool, "VIP", config.getCustomerRetrievalRate()));
    }

    // Stop the ticket operation
    private static void stopTicketOperation() {
        if (isRunning) {
            isRunning = false;
            logger.info("Ticket operations stopped.");
            System.out.println("Ticket operations stopped.");
        } else {
            System.out.println("Ticket operations are not running.");
        }
    }

    // Show ticket pool status
    private static void ticketPoolStatus() {
        System.out.println("Ticket Pool Status:");
        System.out.println("Total Tickets: " + config.getTotalTickets());
        System.out.println("Current Tickets: " + ticketPool.getCurrentTickets());
        System.out.println("Max Capacity: " + config.getMaxTicketCapacity());
        System.out.println("Ticket Release Rate: " + config.getTicketReleaseRate());
        System.out.println("Customer Retrieval Rate: " + config.getCustomerRetrievalRate());
        logger.info("Ticket Pool Status viewed.");
    }
}
