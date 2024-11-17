package Updated;

import java.util.Scanner;

public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    // Setters and Getters
    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    // Configure the system
    public void configure(Scanner scanner) {
        try {
            System.out.print("Set total tickets for movies: ");
            totalTickets = scanner.nextInt();
            validatePositiveInput(totalTickets);

            System.out.print("Set ticket release rate (tickets/second): ");
            ticketReleaseRate = scanner.nextInt();
            validatePositiveInput(ticketReleaseRate);

            System.out.print("Set customer retrieval rate (tickets/second): ");
            customerRetrievalRate = scanner.nextInt();
            validatePositiveInput(customerRetrievalRate);

            System.out.print("Set max ticket capacity: ");
            maxTicketCapacity = scanner.nextInt();
            validatePositiveInput(maxTicketCapacity);
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
            scanner.nextLine(); // Clear buffer
        }
    }

    // Validate positive inputs
    private void validatePositiveInput(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Input must be positive.");
        }
    }
}

