package Updated;



import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
public class Configuration {
    private int maxCapacity;
    private int maxTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    public Configuration() {
        // Default values
        this.maxCapacity = 100; // Default max capacity
        this.maxTickets = 50; // Default total ticket limit
        this.ticketReleaseRate = 10; // Default release rate per cycle
        this.customerRetrievalRate = 5; // Default retrieval rate per cycle
    }

    // Method to load configuration from a Gson file (or use default)
    public void loadConfiguration() {
        try (FileReader reader = new FileReader("config.json")) {
            Gson gson = new Gson();
            Configuration config = gson.fromJson(reader, Configuration.class);
            // Set this instance's fields based on the loaded configuration
            this.maxCapacity = config.maxCapacity;
            this.maxTickets = config.maxTickets;
            this.ticketReleaseRate = config.ticketReleaseRate;
            this.customerRetrievalRate = config.customerRetrievalRate;
            System.out.println("Configuration loaded from file.");
        } catch (IOException ex) {
            System.out.println("Configuration file not found or invalid, using default values.");
        }
    }

    // Method to save configuration to a Gson file
    public void saveConfiguration() {
        try (FileWriter writer = new FileWriter("config.json")) {
            Gson gson = new Gson();
            gson.toJson(this, writer);  // Save the current configuration to the file
            System.out.println("Configuration saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving configuration.");
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
