package com.banyings.fundamentals;

/**
 * Demonstrates core Object-Oriented Programming concepts in Java
 * Including inheritance, polymorphism, encapsulation, and abstraction
 */
public abstract class Vehicle {
    private String brand;
    private String model;
    private int year;
    protected double fuel;
    
    public Vehicle(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.fuel = 0.0;
    }
    
    // Encapsulation - getters and setters
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getFuel() { return fuel; }
    
    public void addFuel(double amount) {
        if (amount > 0) {
            this.fuel += amount;
        }
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract double calculateFuelEfficiency();
    
    // Template method pattern
    public final String getVehicleInfo() {
        return String.format("%d %s %s (Fuel: %.2f L, Efficiency: %.2f mpg)", 
                           year, brand, model, fuel, calculateFuelEfficiency());
    }
    
    // Method that can be overridden
    public void start() {
        System.out.println(brand + " " + model + " is starting...");
    }
    
    @Override
    public String toString() {
        return getVehicleInfo();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Vehicle vehicle = (Vehicle) obj;
        return year == vehicle.year &&
               brand.equals(vehicle.brand) &&
               model.equals(vehicle.model);
    }
    
    @Override
    public int hashCode() {
        return brand.hashCode() * 31 + model.hashCode() * 31 + year;
    }
}