package org.example;

import java.io.*;
import java.util.Scanner;

public class Configuration implements Serializable {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    // Getters and setters
    public int getTotalTickets() { return totalTickets; }
    public void setTotalTickets(int totalTickets) { this.totalTickets = totalTickets; }

    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public void setTicketReleaseRate(int ticketReleaseRate) { this.ticketReleaseRate = ticketReleaseRate; }

    public int getCustomerRetrievalRate() { return customerRetrievalRate; }
    public void setCustomerRetrievalRate(int customerRetrievalRate) { this.customerRetrievalRate = customerRetrievalRate; }

    public int getMaxTicketCapacity() { return maxTicketCapacity; }
    public void setMaxTicketCapacity(int maxTicketCapacity) { this.maxTicketCapacity = maxTicketCapacity; }

    // Save configuration to file
    public void saveToFile(String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
        }
    }

    // Load configuration from file
    public static Configuration loadFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Configuration) in.readObject();
        }
    }

    // Interactive method to create a new configuration
    public static Configuration createFromUserInput() {
        Configuration config = new Configuration();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Ticket Management System Configuration!");

        config.setTotalTickets(getPositiveInt(scanner, "Enter the total number of tickets: "));
        config.setTicketReleaseRate(getPositiveInt(scanner, "Enter the ticket release rate: "));
        config.setCustomerRetrievalRate(getPositiveInt(scanner, "Enter the customer retrieval rate: "));
        config.setMaxTicketCapacity(getPositiveInt(scanner, "Enter the maximum ticket capacity: "));

        System.out.println("\nConfiguration Complete:");
        System.out.println(config);

        return config;
    }

    // Helper method to get positive integer input
    private static int getPositiveInt(Scanner scanner, String prompt) {
        int value;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a positive integer.");
                scanner.next();
            }
            value = scanner.nextInt();
        } while (value <= 0);
        return value;
    }

    @Override
    public String toString() {
        return "Configuration: [Total Tickets = " + totalTickets +
                ", Ticket Release Rate = " + ticketReleaseRate +
                ", Customer Retrieval Rate = " + customerRetrievalRate +
                ", Max Ticket Capacity = " + maxTicketCapacity + "]";
    }
}
