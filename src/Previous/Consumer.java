package Previous;

public class Consumer implements Runnable {

    private final TicketPool ticketPool;
    private final String type;

    public Consumer(TicketPool ticketPool, String type) {
        this.ticketPool = ticketPool;
        this.type = type;
    }

    @Override
    public void run() {
        while (true) {
            ticketPool.retrieveTickets(type);
            try {
                Thread.sleep(1000); // Simulate time delay in purchasing tickets
            } catch (InterruptedException e) {
                System.out.println("[" + type + " Previous.Consumer] Interrupted.");
                break;
            }
        }
    }
}
