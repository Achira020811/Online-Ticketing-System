package Updated;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int ticketReleaseRate;

    public Vendor(TicketPool ticketPool, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public void run() {
        while (TicketSystem.isRunning) {
            ticketPool.addTickets(ticketReleaseRate);
            System.out.println("[Vendor] Tickets added: " + ticketReleaseRate);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("[Vendor] Interrupted.");
            }
        }
    }
}
