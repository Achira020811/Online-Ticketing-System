package Updated;

import java.util.logging.*;

public class Customer implements Runnable {
    private TicketPool ticketPool;
    private int retrievalRate;
    private static final Logger logger = Logger.getLogger(Customer.class.getName());

    public Customer(TicketPool ticketPool, int retrievalRate) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
    }

    @Override
    public void run() {
        while (true) {
            if (ticketPool.isEmpty()) {
                logger.info("Tickets are sold out.");
                break; // Stop the customer thread if tickets are sold out
            }

            // Try to retrieve tickets from the pool at the specified retrieval rate
            for (int i = 0; i < retrievalRate; i++) {
                if (!ticketPool.removeTicket()) {
                    break; // Stop if no tickets are available for customers
                }
            }

            try {
                Thread.sleep(1000); // Simulate time taken for customers to purchase tickets
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
