package Previous;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicketSystem {

    private static ExecutorService executor = Executors.newFixedThreadPool(2);
    private static TicketPool ticketPool = new TicketPool();
    private static boolean isRunning = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Main Menu for user to interact with the system
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("01. Configure System");
            System.out.println("02. Start Normal Ticket Operation");
            System.out.println("03. Start VIP Ticket Operation");
            System.out.println("04. Change Details");
            System.out.println("05. Ticket Pool Status");
            System.out.println("06. Stop Ticket Operation");
            System.out.println("07. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    configureSystem(scanner);
                    break;
                case 2:
                    // Start normal operation with producer-consumer pattern
                    startNormalOperation();
                    break;
                case 3:
                    // Start VIP operation with consumer only (VIP consumers may have different behavior)
                    startVIPOperation();
                    break;
                case 4:
                    changeDetails(scanner);
                    break;
                case 5:
                    ticketPool.ticketPoolStatus();
                    break;
                case 6:
                    stopTicketOperation();
                    break;
                case 7:
                    exitProgram();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void configureSystem(Scanner scanner) {
        ticketPool.configureSystem(scanner);
    }

    private static void startNormalOperation() {
        System.out.println("Starting Normal Ticket Operation...");
        executor.submit(new Producer(ticketPool));
        executor.submit(new Consumer(ticketPool, "Normal"));
    }

    private static void startVIPOperation() {
        System.out.println("Starting VIP Ticket Operation...");
        executor.submit(new Consumer(ticketPool, "VIP"));
    }

    private static void changeDetails(Scanner scanner) {
        ticketPool.changeDetails(scanner);
    }

    private static void stopTicketOperation() {
        isRunning = false;
        System.out.println("Ticket operations stopped.");
    }

    private static void exitProgram() {
        stopTicketOperation();
        executor.shutdown();
        System.out.println("Exiting program...");
    }
}

