package Updated;

import java.util.logging.*;

public class Vendor implements Runnable {
    private TicketPool ticketPool;
    private int releaseRate;
    private static final Logger logger = Logger.getLogger(Vendor.class.getName());

    public Vendor(TicketPool ticketPool, int releaseRate) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
    }

    @Override
    public void run() {
        while (true) {
            if (ticketPool.isFull()) {
                logger.info("Ticket pool reached maximum capacity. Vendor will stop adding tickets.");
                break; // Stop the vendor thread once the pool is full
            }

            // Add tickets to the pool at the specified release rate
            if (!ticketPool.addTickets(releaseRate)) {
                break; // Stop if tickets cannot be added due to capacity
            }

            try {
                Thread.sleep(1000); // Simulate time taken to release tickets
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

