package Updated;

public class TicketPool {
    private int currentTickets;
    private final int maxTicketCapacity;

    public TicketPool(int initialTickets, int maxTicketCapacity) {
        this.currentTickets = initialTickets;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public synchronized void addTickets(int tickets) {
        if (currentTickets + tickets <= maxTicketCapacity) {
            currentTickets += tickets;
        }
    }

    public synchronized boolean removeTickets(int tickets) {
        if (currentTickets >= tickets) {
            currentTickets -= tickets;
            return true;
        }
        return false;
    }

    public int getCurrentTickets() {
        return currentTickets;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }
}

