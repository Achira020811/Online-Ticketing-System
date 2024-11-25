package Updated;

import java.util.logging.*;

public class Vendor implements Runnable {
    private TicketPool ticketPool;
    private int ticketReleaseRate;

    public Vendor(TicketPool ticketPool, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public void run() {
        // Vendor adds tickets in batches based on ticketReleaseRate until maxTickets is reached
        while (!ticketPool.hasReachedMaxTickets()) {
            // Add tickets up to ticketReleaseRate, as long as maxTickets is not reached
            for (int i = 0; i < ticketReleaseRate; i++) {
                if (!ticketPool.hasReachedMaxTickets()) {
                    ticketPool.addTicket();
                }
            }
            try {
                // Simulate the delay between ticket additions (adjust the sleep as necessary)
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        // Stop adding tickets once maxTickets is reached
        ticketPool.stopAddingTickets();
        System.out.println("Vendor has finished adding tickets.");
    }
}
