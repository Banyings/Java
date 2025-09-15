package com.banyings.fundamentals;

/**
 * Another concrete implementation showing polymorphism
 */
public class Motorcycle extends Vehicle implements Driveable {
    private boolean hasSidecar;
    
    public Motorcycle(String brand, String model, int year, boolean hasSidecar) {
        super(brand, model, year);
        this.hasSidecar = hasSidecar;
    }
    
    @Override
    public double calculateFuelEfficiency() {
        return hasSidecar ? 45.0 : 55.0;
    }
    
    @Override
    public void start() {
        super.start();
        System.out.println("Vroom vroom! Motorcycle engine revving!");
    }
    
    @Override
    public void drive() {
        if (fuel > 0) {
            System.out.println("Riding the " + getBrand() + " " + getModel() + 
                             (hasSidecar ? " with sidecar" : ""));
            fuel -= 0.05; // Motorcycles are more fuel efficient
        } else {
            System.out.println("Cannot ride - no fuel!");
        }
    }
    
    @Override
    public void stop() {
        System.out.println(getBrand() + " " + getModel() + " motorcycle has stopped.");
    }
    
    @Override
    public void honk() {
        System.out.println("Beep beep! *motorcycle horn*");
    }
    
    public boolean hasSidecar() { return hasSidecar; }
    
    @Override
    public String toString() {
        return super.toString() + (hasSidecar ? " [With Sidecar]" : " [Solo]");
    }
}