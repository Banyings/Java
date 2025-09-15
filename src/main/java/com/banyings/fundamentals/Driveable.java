package com.banyings.fundamentals;

/**
 * Interface demonstrating contract definition and multiple inheritance
 */
public interface Driveable {
    void drive();
    void stop();
    
    // Default method (Java 8+)
    default void honk() {
        System.out.println("Beep beep!");
    }
    
    // Static method in interface (Java 8+)
    static void displayDrivingTips() {
        System.out.println("Always wear seatbelt and follow traffic rules!");
    }
}