import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;

public class TicketSystemCLI {
    private static int totalTickets;
    private static int ticketReleaseRate;
    private static int customerRetrievalRate;
    private static int maxTicketCapacity;

    private static TicketPool ticketPool = new TicketPool();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Ticket System CLI ===");

        // Configuration Setup
        configureSystem(scanner);

        // Command Execution
        boolean running = true;
        while (running) {
            System.out.println("\nCommands: START, STOP, STATUS, EXIT");
            System.out.print("Enter command: ");
            String command = scanner.nextLine().trim().toUpperCase();

            switch (command) {
                case "START":
                    startTicketHandling();
                    break;
                case "STOP":
                    stopTicketHandling();
                    break;
                case "STATUS":
                    ticketPool.showStatus();
                    break;
                case "EXIT":
                    running = false;
                    System.out.println("Exiting system...");
                    break;
                default:
                    System.out.println("Invalid command. Try again.");
            }
        }
        scanner.close();
    }

    private static void configureSystem(Scanner scanner) {
        totalTickets = getValidInt(scanner, "Enter total tickets available: ", 1, Integer.MAX_VALUE);
        ticketReleaseRate = getValidInt(scanner, "Enter ticket release rate: ", 1, 100);
        customerRetrievalRate = getValidInt(scanner, "Enter customer retrieval rate: ", 1, 100);
        maxTicketCapacity = getValidInt(scanner, "Enter max ticket capacity: ", 1, Integer.MAX_VALUE);

        ticketPool.setMaxCapacity(maxTicketCapacity);
    }

    private static int getValidInt(Scanner scanner, String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Value must be between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static void startTicketHandling() {
        System.out.println("Starting ticket handling...");
        ticketPool.startProducers(ticketReleaseRate, totalTickets);
        ticketPool.startConsumers(customerRetrievalRate);
    }

    private static void stopTicketHandling() {
        System.out.println("Stopping ticket handling...");
        ticketPool.stopAll();
    }
}

class TicketPool {
    private final ArrayList<Integer> tickets = new ArrayList<>();
    private int maxCapacity;
    private boolean running = false;

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private Thread producerThread;
    private Thread consumerThread;

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void startProducers(int ticketReleaseRate, int totalTickets) {
        running = true;
        producerThread = new Thread(() -> {
            int ticketsProduced = 0;
            while (running && ticketsProduced < totalTickets) {
                lock.lock();
                try {
                    while (tickets.size() >= maxCapacity) {
                        notFull.await();
                    }
                    tickets.add(1); // Add a ticket
                    ticketsProduced++;
                    System.out.println("[Producer] Ticket added. Total tickets: " + tickets.size());
                    notEmpty.signalAll();
                    Thread.sleep(1000 / ticketReleaseRate);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.unlock();
                }
            }
        });
        producerThread.start();
    }

    public void startConsumers(int customerRetrievalRate) {
        consumerThread = new Thread(() -> {
            while (running) {
                lock.lock();
                try {
                    while (tickets.isEmpty()) {
                        notEmpty.await();
                    }
                    tickets.remove(0); // Remove a ticket
                    System.out.println("[Consumer] Ticket purchased. Remaining tickets: " + tickets.size());
                    notFull.signalAll();
                    Thread.sleep(1000 / customerRetrievalRate);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.unlock();
                }
            }
        });
        consumerThread.start();
    }

    public void stopAll() {
        running = false;
        if (producerThread != null) producerThread.interrupt();
        if (consumerThread != null) consumerThread.interrupt();
    }

    public void showStatus() {
        lock.lock();
        try {
            System.out.println("Current tickets in pool: " + tickets.size());
        } finally {
            lock.unlock();
        }
    }
}
