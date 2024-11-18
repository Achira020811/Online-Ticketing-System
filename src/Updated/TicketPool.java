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
                currentTickets++;
                System.out.println("Ticket added. Current tickets: " + currentTickets);
            }
        }
    }

    public synchronized void removeTicket() {
        if (currentTickets > 0) {
            currentTickets--;
            System.out.println("Ticket removed. Current tickets: " + currentTickets);
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
