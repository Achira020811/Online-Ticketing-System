package Updated;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;

public class TicketPool {
    private int maxCapacity;
    private int maxTickets;
    private int currentTickets;
    private boolean addingTickets; // Flag to track if vendor is still adding tickets

    public TicketPool(int maxCapacity, int maxTickets) {
        this.maxCapacity = maxCapacity;
        this.maxTickets = maxTickets;
        this.currentTickets = 0;
        this.addingTickets = true; // Start by allowing ticket adding
    }

    public synchronized void addTicket() {
        // Check if the total tickets added is less than maxTickets
        if (currentTickets < maxTickets) {
            if (currentTickets < maxCapacity) {
                Configuration o1 = new Configuration();
                currentTickets += o1.getTicketReleaseRate();

                // Print and wait for a moment before continuing
                System.out.println(o1.getTicketReleaseRate() + " Tickets added. Current tickets in the Pool: " + currentTickets);

                // Add delay to avoid flooding the console with outputs
                try {
                    Thread.sleep(1000); // Adjust the time (in milliseconds) as needed
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public synchronized void removeTicket() {
        if (currentTickets > 0) {
            Configuration o2 = new Configuration();
            currentTickets -= o2.getCustomerRetrievalRate();

            // Print and wait for a moment before continuing
            System.out.println(o2.getCustomerRetrievalRate() + " Tickets bought. Current tickets in the Pool: " + currentTickets);

            // Add delay to avoid flooding the console with outputs
            try {
                Thread.sleep(1000); // Adjust the time (in milliseconds) as needed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized int getCurrentTickets() {
        return currentTickets;
    }

    // Method for the vendor to stop adding tickets once the maxTickets is reached
    public synchronized void stopAddingTickets() {
        addingTickets = false;
    }

    public synchronized boolean isAddingTickets() {
        return addingTickets;
    }

    public synchronized boolean hasReachedMaxTickets() {
        return currentTickets >= maxTickets;
    }
}
