package Previous;

public class Producer implements Runnable {

    private final TicketPool ticketPool;

    public Producer(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (true) {
            ticketPool.addTickets();
            try {
                Thread.sleep(1000); // Simulate time delay in adding tickets
            } catch (InterruptedException e) {
                System.out.println("[Previous.Producer] Interrupted.");
                break;
            }
        }
    }
}
