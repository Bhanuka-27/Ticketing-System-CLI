package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class TicketPool {
    private final AtomicInteger ticketCount = new AtomicInteger(0);
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    // Add tickets to the pool
    public synchronized void addTickets(int count) {
        int newCount = ticketCount.get() + count;
        if (newCount <= maxCapacity) {
            ticketCount.addAndGet(count);
            System.out.println("Added " + count + " tickets. Total: " + ticketCount.get());
        } else {
            System.out.println("Cannot add tickets. Max capacity reached.");
        }
    }

    // Remove tickets from the pool
    public synchronized void removeTickets(int count) {
        if (ticketCount.get() >= count) {
            ticketCount.addAndGet(-count);
            System.out.println("Removed " + count + " tickets. Total: " + ticketCount.get());
        } else {
            System.out.println("Not enough tickets to remove.");
        }
    }

    public int getTicketCount() {
        return ticketCount.get();
    }
}
