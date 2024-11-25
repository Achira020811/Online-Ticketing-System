package Updated;

import java.util.logging.*;

public class Customer implements Runnable {
    private TicketPool ticketPool;
    private int customerRetrievalRate; // Rate at which the customer retrieves tickets

    public Customer(TicketPool ticketPool, int customerRetrievalRate) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    @Override
    public void run() {
        // The customer thread will continuously attempt to remove tickets based on customerRetrievalRate
        while (ticketPool.getCurrentTickets() > 0 || ticketPool.isAddingTickets()) {
            try {
                for (int i = 0; i < customerRetrievalRate; i++) {
                    if (ticketPool.getCurrentTickets() > 0) {
                        ticketPool.removeTicket();
                    } else {
                        break;
                    }
                }
                // Simulate some delay between ticket retrieval cycles
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Customer has finished buying tickets.");
    }
}
