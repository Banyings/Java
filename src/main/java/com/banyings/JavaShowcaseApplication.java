package com.banyings;

import com.banyings.fundamentals.*;
import com.banyings.datastructures.BinarySearchTree;
import com.banyings.algorithms.SortingAlgorithms;
import com.banyings.designpatterns.*;
import com.banyings.streams.StreamExamples;
import com.banyings.concurrency.ConcurrencyExamples;
import com.banyings.utilities.UtilityLibrary;

import java.util.*;

/**
 * Main demonstration class showcasing Java fluency across multiple domains
 * This class serves as the entry point to demonstrate various Java concepts
 * and programming techniques developed since 2023.
 */
public class JavaShowcaseApplication {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("      JAVA FLUENCY SHOWCASE - 2023-2024");
        System.out.println("    Demonstrating Advanced Java Programming Skills");
        System.out.println("=".repeat(60));
        
        try {
            // 1. Object-Oriented Programming Fundamentals
            demonstrateOOPConcepts();
            
            // 2. Data Structures and Algorithms
            demonstrateDataStructuresAndAlgorithms();
            
            // 3. Design Patterns
            demonstrateDesignPatterns();
            
            // 4. Modern Java Features (Streams, Lambda)
            demonstrateModernJavaFeatures();
            
            // 5. Concurrency and Multithreading
            demonstrateConcurrency();
            
            // 6. Utility Libraries and Practical Programming
            demonstrateUtilities();
            
            System.out.println("\n" + "=".repeat(60));
            System.out.println("     SHOWCASE COMPLETED SUCCESSFULLY!");
            System.out.println("All demonstrations executed without errors.");
            System.out.println("=".repeat(60));
            
        } catch (Exception e) {
            System.err.println("An error occurred during demonstration: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void demonstrateOOPConcepts() {
        System.out.println("\n🚗 1. OBJECT-ORIENTED PROGRAMMING CONCEPTS");
        System.out.println("-".repeat(50));
        
        // Create vehicle instances
        Car electricCar = new Car("Tesla", "Model 3", 2023, 4, true);
        Car gasCar = new Car("Toyota", "Camry", 2024, 4, false);
        Motorcycle motorcycle = new Motorcycle("Harley-Davidson", "Street 750", 2023, false);
        
        // Demonstrate polymorphism
        List<Vehicle> vehicles = Arrays.asList(electricCar, gasCar, motorcycle);
        
        System.out.println("Vehicle Fleet Information:");
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
            vehicle.addFuel(50.0);
            vehicle.start();
            
            if (vehicle instanceof Driveable) {
                Driveable driveable = (Driveable) vehicle;
                driveable.drive();
                driveable.honk();
                driveable.stop();
            }
            System.out.println();
        }
        
        // Demonstrate interface static method
        Driveable.displayDrivingTips();
    }
    
    private static void demonstrateDataStructuresAndAlgorithms() {
        System.out.println("\n🌳 2. DATA STRUCTURES AND ALGORITHMS");
        System.out.println("-".repeat(50));
        
        // Binary Search Tree demonstration
        System.out.println("Binary Search Tree Operations:");
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        
        int[] values = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 65};
        for (int value : values) {
            bst.insert(value);
        }
        
        System.out.println("Tree contents (inorder): " + bst);
        System.out.println("Tree size: " + bst.size());
        System.out.println("Tree height: " + bst.height());
        System.out.println("Contains 40: " + bst.contains(40));
        System.out.println("Contains 100: " + bst.contains(100));
        
        bst.delete(30);
        System.out.println("After deleting 30: " + bst);
        
        // Sorting algorithms demonstration
        System.out.println("\nSorting Algorithms Performance:");
        SortingAlgorithms.comparePerformance();
    }
    
    private static void demonstrateDesignPatterns() {
        System.out.println("\n🏗️ 3. DESIGN PATTERNS");
        System.out.println("-".repeat(50));
        
        // Singleton pattern
        System.out.println("Singleton Pattern - Configuration Manager:");
        ConfigurationManager config1 = ConfigurationManager.getInstance();
        ConfigurationManager config2 = ConfigurationManager.getInstance();
        
        System.out.println("Same instance: " + (config1 == config2));
        config1.loadConfiguration("production");
        System.out.println("Config: " + config2); // Shows same instance
        
        // Factory pattern
        System.out.println("\nFactory Pattern - Database Connections:");
        FactoryPattern.demonstrateFactory();
        
        // Observer pattern
        System.out.println("Observer Pattern - News Agency:");
        NewsAgency agency = ObserverPattern.createNewsAgency();
        NewsChannel cnn = new NewsChannel("CNN");
        NewsChannel bbc = new NewsChannel("BBC");
        MobileNewsApp app = new MobileNewsApp("NewsApp");
        
        agency.addObserver(cnn);
        agency.addObserver(bbc);
        agency.addObserver(app);
        
        agency.publishNews("Breaking: Java 21 Released!", 
                          "Oracle announces the latest LTS version of Java with exciting new features.");
        
        System.out.printf("News agency has %d observers%n", agency.getObserverCount());
    }
    
    private static void demonstrateModernJavaFeatures() {
        System.out.println("\n🚀 4. MODERN JAVA FEATURES (JAVA 8+)");
        System.out.println("-".repeat(50));
        
        // Stream API demonstrations
        StreamExamples.basicOperations();
        StreamExamples.advancedOperations();
        StreamExamples.customCollectorsAndParallel();
        StreamExamples.functionalProgramming();
        StreamExamples.reductionOperations();
    }
    
    private static void demonstrateConcurrency() {
        System.out.println("\n⚡ 5. CONCURRENCY AND MULTITHREADING");
        System.out.println("-".repeat(50));
        
        ConcurrencyExamples.demonstrateConcurrency();
    }
    
    private static void demonstrateUtilities() {
        System.out.println("\n🛠️ 6. UTILITY LIBRARIES AND PRACTICAL PROGRAMMING");
        System.out.println("-".repeat(50));
        
        // String utilities
        System.out.println("String Utilities:");
        System.out.println("Capitalize: " + UtilityLibrary.StringUtils.capitalize("hello world"));
        System.out.println("CamelCase: " + UtilityLibrary.StringUtils.toCamelCase("hello-world_example"));
        System.out.println("Reverse: " + UtilityLibrary.StringUtils.reverse("Java"));
        System.out.println("Truncate: " + UtilityLibrary.StringUtils.truncate("This is a very long string", 10));
        
        // Validation utilities
        System.out.println("\nValidation Utilities:");
        System.out.println("Valid email (test@example.com): " + 
                         UtilityLibrary.ValidationUtils.isValidEmail("test@example.com"));
        System.out.println("Valid email (invalid): " + 
                         UtilityLibrary.ValidationUtils.isValidEmail("invalid-email"));
        System.out.println("Valid phone (+1234567890): " + 
                         UtilityLibrary.ValidationUtils.isValidPhoneNumber("+1234567890"));
        System.out.println("Is numeric (123.45): " + 
                         UtilityLibrary.ValidationUtils.isNumeric("123.45"));
        
        // Date/Time utilities
        System.out.println("\nDate/Time Utilities:");
        System.out.println("Current timestamp: " + UtilityLibrary.DateTimeUtils.getCurrentTimestamp());
        System.out.println("Current time millis: " + UtilityLibrary.DateTimeUtils.getTimestamp());
        
        // Collection utilities
        System.out.println("\nCollection Utilities:");
        List<String> testList = Arrays.asList("apple", "banana", "cherry", "date", "elderberry");
        System.out.println("Original list: " + testList);
        System.out.println("Random element: " + UtilityLibrary.CollectionUtils.getRandomElement(testList));
        System.out.println("Partitioned (size 2): " + 
                         UtilityLibrary.CollectionUtils.partition(testList, 2));
        
        // JSON serialization example
        try {
            System.out.println("\nJSON Serialization:");
            UtilityLibrary.Person person = new UtilityLibrary.Person(
                "John Doe", 30, "john@example.com", 
                Arrays.asList("programming", "reading", "hiking")
            );
            
            String json = UtilityLibrary.JsonUtils.toJson(person);
            System.out.println("Serialized to JSON:\n" + json);
            
            UtilityLibrary.Person deserializedPerson = UtilityLibrary.JsonUtils.fromJson(json, UtilityLibrary.Person.class);
            System.out.println("Deserialized back: " + deserializedPerson);
            
        } catch (Exception e) {
            System.err.println("JSON processing error: " + e.getMessage());
        }
        
        // File operations would require actual file system access
        System.out.println("\nFile utilities are available for:");
        System.out.println("- Reading/writing files");
        System.out.println("- Copying files");
        System.out.println("- Finding files by extension");
        System.out.println("- JSON file I/O operations");
    }
}