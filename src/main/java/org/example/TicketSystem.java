package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TicketSystem {
    public static void main(String[] args) {
        Configuration config;

        try {
            // Attempt to load configuration from file
            config = Configuration.loadFromFile("config.dat");
            System.out.println("Loaded configuration: " + config);
        } catch (FileNotFoundException e) {
            // If configuration file not found, create a new one interactively
            System.out.println("Configuration file not found. Creating a new configuration.");
            config = Configuration.createFromUserInput();
            try {
                config.saveToFile("config.dat");
                System.out.println("Configuration saved to 'config.dat'.");
            } catch (IOException ioException) {
                System.err.println("Failed to save configuration: " + ioException.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Failed to load configuration: " + e.getMessage());
            return;
        }

        // Initialize TicketPool
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());

        // Start vendor threads
        List<Thread> vendors = new ArrayList<>();
        for (int i = 0; i < 2; i++) { // Example: 2 vendors
            Thread vendor = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate()), "Vendor-" + (i + 1));
            vendor.start();
            vendors.add(vendor);
        }

        // Start customer threads
        List<Thread> customers = new ArrayList<>();
        for (int i = 0; i < 3; i++) { // Example: 3 customers
            Thread customer = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate()), "Customer-" + (i + 1));
            customer.start();
            customers.add(customer);
        }

        // Run the simulation for 10 seconds
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.err.println("Simulation interrupted: " + e.getMessage());
        }

        // Interrupt all threads to stop the simulation
        vendors.forEach(Thread::interrupt);
        customers.forEach(Thread::interrupt);

        System.out.println("Simulation complete.");
    }
}
