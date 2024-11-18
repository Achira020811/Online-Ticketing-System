package Updated;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;

public class TicketPool {
    private List<Integer> tickets;
    private int maxCapacity;
    private static final Logger logger = Logger.getLogger(TicketPool.class.getName());

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.tickets = new LinkedList<>();
    }

    // Vendor adds tickets to the pool (producer method)
    public synchronized boolean addTickets(int count) {
        if (tickets.size() >= maxCapacity) {
            // Do not add tickets if it exceeds the max capacity
            logger.warning("Ticket pool reached maximum capacity. No more tickets can be added.");
            return false; // Stop adding tickets
        }
        for (int i = 0; i < count; i++) {
            tickets.add(1); // Representing a ticket as 1
        }
        logger.info("Tickets added. Current pool size: " + tickets.size());
        return true;
    }

    // Customer retrieves tickets from the pool (consumer method)
    public synchronized boolean removeTicket() {
        if (tickets.isEmpty()) {
            logger.warning("No tickets available. Tickets are sold out.");
            return false; // Stop if no tickets are available
        } else {
            tickets.remove(0);
            logger.info("Ticket removed. Current pool size: " + tickets.size());
            return true;
        }
    }

    public synchronized int getAvailableTickets() {
        return tickets.size();
    }

    public synchronized boolean isFull() {
        return tickets.size() >= maxCapacity;
    }

    public synchronized boolean isEmpty() {
        return tickets.size() == 0;
    }
}
