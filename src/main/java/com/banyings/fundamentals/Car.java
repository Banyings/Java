package com.banyings.fundamentals;

/**
 * Concrete implementation demonstrating inheritance and polymorphism
 */
public class Car extends Vehicle implements Driveable {
    private int numberOfDoors;
    private boolean isElectric;
    
    public Car(String brand, String model, int year, int numberOfDoors, boolean isElectric) {
        super(brand, model, year);
        this.numberOfDoors = numberOfDoors;
        this.isElectric = isElectric;
    }
    
    @Override
    public double calculateFuelEfficiency() {
        // Electric cars have higher efficiency equivalent
        return isElectric ? 120.0 : 28.5;
    }
    
    @Override
    public void start() {
        if (isElectric) {
            System.out.println("Electric " + getBrand() + " " + getModel() + " starting silently...");
        } else {
            super.start();
            System.out.println("Engine roaring to life!");
        }
    }
    
    @Override
    public void drive() {
        if (fuel > 0 || isElectric) {
            System.out.println("Driving the " + getBrand() + " " + getModel());
            if (!isElectric) {
                fuel -= 0.1; // Consume fuel
            }
        } else {
            System.out.println("Cannot drive - no fuel!");
        }
    }
    
    @Override
    public void stop() {
        System.out.println(getBrand() + " " + getModel() + " has stopped.");
    }
    
    public int getNumberOfDoors() { return numberOfDoors; }
    public boolean isElectric() { return isElectric; }
    
    @Override
    public String toString() {
        return super.toString() + String.format(" [%d doors, %s]", 
                                               numberOfDoors, 
                                               isElectric ? "Electric" : "Gas");
    }
}