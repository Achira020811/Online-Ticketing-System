package Updated;



import java.util.Scanner;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Configuration {
    private int maxCapacity; // Maximum capacity of tickets in the pool
    private int maxTickets; // The total number of tickets to add before stopping
    private int ticketReleaseRate; // Rate at which the vendor adds tickets
    private int customerRetrievalRate; // Rate at which customers remove tickets

    public Configuration() {
        // Default values
        this.maxCapacity = 100; // Default max capacity
        this.maxTickets = 50; // Default total ticket limit
        this.ticketReleaseRate = 10; // Default release rate per cycle
        this.customerRetrievalRate = 5; // Default retrieval rate per cycle
    }

    // Method to load configuration from a file (or use default)
    public void loadConfiguration() {
        try (InputStream input = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            // Load values from the config file
            this.maxCapacity = Integer.parseInt(prop.getProperty("maxCapacity", "100"));
            this.maxTickets = Integer.parseInt(prop.getProperty("maxTickets", "50"));
            this.ticketReleaseRate = Integer.parseInt(prop.getProperty("ticketReleaseRate", "10"));
            this.customerRetrievalRate = Integer.parseInt(prop.getProperty("customerRetrievalRate", "5"));
        } catch (IOException ex) {
            System.out.println("Configuration file not found or invalid, using default values.");
        }
    }

    // Method to validate configuration values
    public boolean validateConfiguration() {
        return maxCapacity > 0 && maxTickets > 0 && ticketReleaseRate > 0 && customerRetrievalRate > 0;
    }

    // Method to prompt the user for configuration if invalid
    public void promptUserForConfig() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the maximum ticket capacity:");
        this.maxCapacity = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the total number of tickets to add before stopping:");
        this.maxTickets = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the ticket release rate (how many tickets the vendor adds per cycle):");
        this.ticketReleaseRate = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the customer retrieval rate (how many tickets a customer removes per cycle):");
        this.customerRetrievalRate = Integer.parseInt(scanner.nextLine());
    }

    // Getter methods for the configuration values
    public int getMaxTicketCapacity() {
        return maxCapacity;
    }

    public int getMaxTickets() {
        return maxTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }
}
