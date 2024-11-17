package Previous;

import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private int currentTickets = 0;
    private final Lock lock = new ReentrantLock();

    public void configureSystem(Scanner scanner) {
        totalTickets = getValidInput(scanner, "Enter total tickets available: ");
        ticketReleaseRate = getValidInput(scanner, "Enter ticket release rate (tickets/second): ");
        customerRetrievalRate = getValidInput(scanner, "Enter customer retrieval rate (tickets/second): ");
        maxTicketCapacity = getValidInput(scanner, "Enter max ticket capacity: ");
        currentTickets = totalTickets;
        System.out.println("System configuration completed.");
    }

    public void changeDetails(Scanner scanner) {
        System.out.println("Change Details:");
        System.out.println("1. Change Total Tickets");
        System.out.println("2. Change Ticket Release Rate");
        System.out.println("3. Change Customer Retrieval Rate");
        System.out.println("4. Change Max Ticket Capacity");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                totalTickets = getValidInput(scanner, "Enter new total tickets: ");
                break;
            case 2:
                ticketReleaseRate = getValidInput(scanner, "Enter new ticket release rate: ");
                break;
            case 3:
                customerRetrievalRate = getValidInput(scanner, "Enter new customer retrieval rate: ");
                break;
            case 4:
                maxTicketCapacity = getValidInput(scanner, "Enter new max ticket capacity: ");
                break;
            default:
                System.out.println("Invalid choice.");
        }
        System.out.println("Details updated successfully.");
    }

    public void ticketPoolStatus() {
        System.out.println("Ticket Pool Status:");
        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Current Tickets: " + currentTickets);
        System.out.println("Max Capacity: " + maxTicketCapacity);
        System.out.println("Ticket Release Rate: " + ticketReleaseRate);
        System.out.println("Customer Retrieval Rate: " + customerRetrievalRate);
    }

    // Method to add tickets to the pool (called by producer)
    public void addTickets() {
        lock.lock();
        try {
            if (currentTickets < maxTicketCapacity) {
                currentTickets += ticketReleaseRate;
                logOperation("[Previous.Producer] Tickets added: " + ticketReleaseRate + " | Current tickets: " + currentTickets);
            }
        } finally {
            lock.unlock();
        }
    }

    // Method to retrieve tickets from the pool (called by consumers)
    public void retrieveTickets(String type) {
        lock.lock();
        try {
            if (currentTickets > 0) {
                currentTickets -= customerRetrievalRate;
                logOperation("[" + type + " Previous.Consumer] Tickets purchased: " + customerRetrievalRate + " | Remaining tickets: " + currentTickets);
            } else {
                logOperation("[" + type + " Previous.Consumer] No tickets available.");
            }
        } finally {
            lock.unlock();
        }
    }

    private int getValidInput(Scanner scanner, String prompt) {
        int input = -1;
        while (input <= 0) {
            System.out.print(prompt);
            try {
                input = scanner.nextInt();
                if (input <= 0) {
                    System.out.println("Please enter a positive value.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
        return input;
    }

    private void logOperation(String message) {
        System.out.println(message);
    }
}
