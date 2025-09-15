package com.banyings.fundamentals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {
    
    private Car testCar;
    private Motorcycle testMotorcycle;
    
    @BeforeEach
    void setUp() {
        testCar = new Car("Toyota", "Prius", 2023, 4, true);
        testMotorcycle = new Motorcycle("Honda", "CBR600", 2023, false);
    }
    
    @Test
    void testVehicleCreation() {
        assertEquals("Toyota", testCar.getBrand());
        assertEquals("Prius", testCar.getModel());
        assertEquals(2023, testCar.getYear());
        assertEquals(0.0, testCar.getFuel());
    }
    
    @Test
    void testFuelAddition() {
        testCar.addFuel(50.0);
        assertEquals(50.0, testCar.getFuel());
        
        // Test negative fuel addition
        testCar.addFuel(-10.0);
        assertEquals(50.0, testCar.getFuel()); // Should remain unchanged
    }
    
    @Test
    void testPolymorphism() {
        Vehicle vehicle = testCar;
        assertTrue(vehicle instanceof Car);
        assertTrue(vehicle instanceof Driveable);
        
        // Test method overriding
        double efficiency = vehicle.calculateFuelEfficiency();
        assertTrue(efficiency > 0);
    }
    
    @Test
    void testElectricCarEfficiency() {
        assertEquals(120.0, testCar.calculateFuelEfficiency());
        
        Car gasCar = new Car("Honda", "Civic", 2023, 4, false);
        assertEquals(28.5, gasCar.calculateFuelEfficiency());
    }
    
    @Test
    void testMotorcycleEfficiency() {
        assertEquals(55.0, testMotorcycle.calculateFuelEfficiency());
        
        Motorcycle withSidecar = new Motorcycle("BMW", "R1250GS", 2023, true);
        assertEquals(45.0, withSidecar.calculateFuelEfficiency());
    }
    
    @Test
    void testDriveInterface() {
        assertDoesNotThrow(() -> {
            testCar.drive();
            testCar.stop();
            testCar.honk();
        });
    }
    
    @Test
    void testEqualsAndHashCode() {
        Car car1 = new Car("Toyota", "Prius", 2023, 4, true);
        Car car2 = new Car("Toyota", "Prius", 2023, 4, true);
        Car car3 = new Car("Honda", "Civic", 2023, 4, false);
        
        assertEquals(car1, car2);
        assertNotEquals(car1, car3);
        assertEquals(car1.hashCode(), car2.hashCode());
    }
    
    @Test
    void testToString() {
        String carString = testCar.toString();
        assertNotNull(carString);
        assertTrue(carString.contains("Toyota"));
        assertTrue(carString.contains("Prius"));
        assertTrue(carString.contains("Electric"));
    }
}