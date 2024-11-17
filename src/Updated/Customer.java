package Updated;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final String type;
    private final int customerRetrievalRate;

    public Customer(TicketPool ticketPool, String type, int customerRetrievalRate) {
        this.ticketPool = ticketPool;
        this.type = type;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    @Override
    public void run() {
        while (TicketSystem.isRunning) {
            if (ticketPool.removeTickets(customerRetrievalRate)) {
                System.out.println("[" + type + " Consumer] Tickets purchased: " + customerRetrievalRate);
            } else {
                System.out.println("[" + type + " Consumer] No tickets available.");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("[" + type + " Consumer] Interrupted.");
            }
        }
    }
}
