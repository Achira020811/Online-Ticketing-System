package Updated;

import java.io.*;
import java.util.Scanner;

public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    public Configuration() {
        // Default values
        this.totalTickets = 100;
        this.ticketReleaseRate = 5;
        this.customerRetrievalRate = 3;
        this.maxTicketCapacity = 200;
    }

    // Getter and Setter methods
    public int getTotalTickets() { return totalTickets; }
    public void setTotalTickets(int totalTickets) { this.totalTickets = totalTickets; }

    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public void setTicketReleaseRate(int ticketReleaseRate) { this.ticketReleaseRate = ticketReleaseRate; }

    public int getCustomerRetrievalRate() { return customerRetrievalRate; }
    public void setCustomerRetrievalRate(int customerRetrievalRate) { this.customerRetrievalRate = customerRetrievalRate; }

    public int getMaxTicketCapacity() { return maxTicketCapacity; }
    public void setMaxTicketCapacity(int maxTicketCapacity) { this.maxTicketCapacity = maxTicketCapacity; }

    // Method to load configuration from file
    public void loadConfiguration() {
        try (BufferedReader br = new BufferedReader(new FileReader("config.txt"))) {
            totalTickets = Integer.parseInt(br.readLine());
            ticketReleaseRate = Integer.parseInt(br.readLine());
            customerRetrievalRate = Integer.parseInt(br.readLine());
            maxTicketCapacity = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            System.out.println("Error reading configuration file. Using default values.");
        }
    }

    // Method to save configuration to file
    public void saveConfiguration() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"))) {
            bw.write(totalTickets + "\n");
            bw.write(ticketReleaseRate + "\n");
            bw.write(customerRetrievalRate + "\n");
            bw.write(maxTicketCapacity + "\n");
        } catch (IOException e) {
            System.out.println("Error saving configuration.");
        }
    }

    // Method to prompt the user for input
    public void promptUserForConfig() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter total tickets: ");
        totalTickets = sc.nextInt();

        System.out.print("Enter ticket release rate: ");
        ticketReleaseRate = sc.nextInt();

        System.out.print("Enter customer retrieval rate: ");
        customerRetrievalRate = sc.nextInt();

        System.out.print("Enter max ticket capacity: ");
        maxTicketCapacity = sc.nextInt();
    }

    // Validate the input values
    public boolean validateConfiguration() {
        if (totalTickets <= 0 || ticketReleaseRate <= 0 || customerRetrievalRate <= 0 || maxTicketCapacity <= 0) {
            System.out.println("Invalid input. All values must be positive.");
            return false;
        }
        return true;
    }
}
