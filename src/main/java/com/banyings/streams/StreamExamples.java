package com.banyings.streams;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Demonstrates Java 8+ Stream API and functional programming concepts
 */
public class StreamExamples {
    
    public static class Employee {
        private String name;
        private String department;
        private double salary;
        private int age;
        
        public Employee(String name, String department, double salary, int age) {
            this.name = name;
            this.department = department;
            this.salary = salary;
            this.age = age;
        }
        
        // Getters
        public String getName() { return name; }
        public String getDepartment() { return department; }
        public double getSalary() { return salary; }
        public int getAge() { return age; }
        
        @Override
        public String toString() {
            return String.format("Employee{name='%s', dept='%s', salary=%.2f, age=%d}", 
                               name, department, salary, age);
        }
    }
    
    public static List<Employee> getSampleEmployees() {
        return Arrays.asList(
            new Employee("Alice Johnson", "Engineering", 95000, 28),
            new Employee("Bob Smith", "Marketing", 65000, 34),
            new Employee("Carol Davis", "Engineering", 110000, 31),
            new Employee("David Wilson", "Sales", 70000, 29),
            new Employee("Eva Brown", "Engineering", 85000, 26),
            new Employee("Frank Miller", "Marketing", 75000, 35),
            new Employee("Grace Lee", "Sales", 68000, 27),
            new Employee("Henry Clark", "Engineering", 120000, 40),
            new Employee("Ivy Taylor", "Marketing", 72000, 32),
            new Employee("Jack Anderson", "Sales", 90000, 38)
        );
    }
    
    /**
     * Basic stream operations - filter, map, collect
     */
    public static void basicOperations() {
        System.out.println("=== Basic Stream Operations ===");
        List<Employee> employees = getSampleEmployees();
        
        // Filter employees with salary > 80000
        List<String> highEarners = employees.stream()
            .filter(emp -> emp.getSalary() > 80000)
            .map(Employee::getName)
            .collect(Collectors.toList());
        
        System.out.println("High earners: " + highEarners);
        
        // Find average salary by department
        Map<String, Double> avgSalaryByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.averagingDouble(Employee::getSalary)
            ));
        
        System.out.println("Average salary by department:");
        avgSalaryByDept.forEach((dept, avg) -> 
            System.out.printf("  %s: $%.2f%n", dept, avg));
    }
    
    /**
     * Advanced stream operations with complex transformations
     */
    public static void advancedOperations() {
        System.out.println("\n=== Advanced Stream Operations ===");
        List<Employee> employees = getSampleEmployees();
        
        // Complex pipeline: Find top 3 youngest high-earning engineers
        List<Employee> topYoungEngineers = employees.stream()
            .filter(emp -> "Engineering".equals(emp.getDepartment()))
            .filter(emp -> emp.getSalary() > 90000)
            .sorted(Comparator.comparing(Employee::getAge))
            .limit(3)
            .collect(Collectors.toList());
        
        System.out.println("Top 3 youngest high-earning engineers:");
        topYoungEngineers.forEach(System.out::println);
        
        // Partitioning employees by age
        Map<Boolean, List<Employee>> partitionByAge = employees.stream()
            .collect(Collectors.partitioningBy(emp -> emp.getAge() >= 30));
        
        System.out.println("\nEmployees 30 and older: " + partitionByAge.get(true).size());
        System.out.println("Employees under 30: " + partitionByAge.get(false).size());
    }
    
    /**
     * Custom collectors and parallel streams
     */
    public static void customCollectorsAndParallel() {
        System.out.println("\n=== Custom Collectors and Parallel Processing ===");
        
        // Generate large dataset for parallel processing demo
        List<Integer> numbers = IntStream.rangeClosed(1, 1_000_000)
            .boxed()
            .collect(Collectors.toList());
        
        // Sequential processing
        long startTime = System.nanoTime();
        long sumSequential = numbers.stream()
            .mapToLong(Integer::longValue)
            .filter(n -> n % 2 == 0)
            .sum();
        long sequentialTime = System.nanoTime() - startTime;
        
        // Parallel processing
        startTime = System.nanoTime();
        long sumParallel = numbers.parallelStream()
            .mapToLong(Integer::longValue)
            .filter(n -> n % 2 == 0)
            .sum();
        long parallelTime = System.nanoTime() - startTime;
        
        System.out.printf("Sum of even numbers (1-1M): %d%n", sumSequential);
        System.out.printf("Sequential time: %.2f ms%n", sequentialTime / 1_000_000.0);
        System.out.printf("Parallel time: %.2f ms%n", parallelTime / 1_000_000.0);
        System.out.printf("Speedup: %.2fx%n", (double) sequentialTime / parallelTime);
    }
    
    /**
     * Functional interfaces and method references
     */
    public static void functionalProgramming() {
        System.out.println("\n=== Functional Programming Concepts ===");
        List<Employee> employees = getSampleEmployees();
        
        // Using lambda expressions
        Predicate<Employee> isEngineer = emp -> "Engineering".equals(emp.getDepartment());
        Function<Employee, String> getNameUpper = emp -> emp.getName().toUpperCase();
        
        // Method references
        Comparator<Employee> bySalary = Comparator.comparing(Employee::getSalary);
        Consumer<Employee> printEmployee = System.out::println;
        
        // Combining functional interfaces
        employees.stream()
            .filter(isEngineer)
            .sorted(bySalary.reversed())
            .map(getNameUpper)
            .forEach(name -> System.out.println("Engineer: " + name));
        
        // Optional usage
        Optional<Employee> highestPaidEmployee = employees.stream()
            .max(Comparator.comparing(Employee::getSalary));
        
        highestPaidEmployee.ifPresentOrElse(
            emp -> System.out.println("\nHighest paid: " + emp),
            () -> System.out.println("\nNo employees found")
        );
    }
    
    /**
     * Stream reduction operations
     */
    public static void reductionOperations() {
        System.out.println("\n=== Reduction Operations ===");
        List<Employee> employees = getSampleEmployees();
        
        // Total salary expense
        double totalSalary = employees.stream()
            .mapToDouble(Employee::getSalary)
            .sum();
        
        // Custom reduction: concatenate all names
        String allNames = employees.stream()
            .map(Employee::getName)
            .reduce("", (name1, name2) -> name1.isEmpty() ? name2 : name1 + ", " + name2);
        
        // Statistics
        DoubleSummaryStatistics salaryStats = employees.stream()
            .mapToDouble(Employee::getSalary)
            .summaryStatistics();
        
        System.out.printf("Total salary expense: $%.2f%n", totalSalary);
        System.out.println("All employee names: " + allNames);
        System.out.printf("Salary statistics: min=%.2f, max=%.2f, avg=%.2f%n",
                         salaryStats.getMin(), salaryStats.getMax(), salaryStats.getAverage());
    }
}