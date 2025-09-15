package com.banyings.concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.*;

/**
 * Demonstrates Java concurrency concepts including threads, synchronization,
 * and modern concurrent utilities
 */
public class ConcurrencyExamples {
    
    /**
     * Thread-safe counter using various synchronization mechanisms
     */
    public static class ThreadSafeCounter {
        private int count = 0;
        private final Object lock = new Object();
        private final ReentrantLock reentrantLock = new ReentrantLock();
        private final AtomicInteger atomicCount = new AtomicInteger(0);
        
        // Synchronized method
        public synchronized void incrementSynchronized() {
            count++;
        }
        
        // Synchronized block
        public void incrementSynchronizedBlock() {
            synchronized (lock) {
                count++;
            }
        }
        
        // ReentrantLock
        public void incrementWithLock() {
            reentrantLock.lock();
            try {
                count++;
            } finally {
                reentrantLock.unlock();
            }
        }
        
        // Atomic operations
        public void incrementAtomic() {
            atomicCount.incrementAndGet();
        }
        
        public int getCount() { return count; }
        public int getAtomicCount() { return atomicCount.get(); }
    }
    
    /**
     * Producer-Consumer pattern using BlockingQueue
     */
    public static class ProducerConsumerExample {
        private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        private volatile boolean running = true;
        
        public class Producer implements Runnable {
            @Override
            public void run() {
                int messageCount = 0;
                while (running && messageCount < 20) {
                    try {
                        String message = "Message " + (++messageCount);
                        queue.put(message);
                        System.out.println("Produced: " + message);
                        Thread.sleep(100); // Simulate work
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
        
        public class Consumer implements Runnable {
            private final String name;
            
            public Consumer(String name) {
                this.name = name;
            }
            
            @Override
            public void run() {
                while (running) {
                    try {
                        String message = queue.poll(1, TimeUnit.SECONDS);
                        if (message != null) {
                            System.out.println(name + " consumed: " + message);
                            Thread.sleep(150); // Simulate processing
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
        
        public void demonstrate() throws InterruptedException {
            System.out.println("=== Producer-Consumer Demo ===");
            
            ExecutorService executor = Executors.newFixedThreadPool(4);
            
            // Start producer and consumers
            executor.submit(new Producer());
            executor.submit(new Consumer("Consumer-1"));
            executor.submit(new Consumer("Consumer-2"));
            
            // Let it run for a while
            Thread.sleep(3000);
            
            running = false;
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
    }
    
    /**
     * Demonstrates CompletableFuture for asynchronous programming
     */
    public static class AsyncOperations {
        
        public static CompletableFuture<String> fetchUserData(int userId) {
            return CompletableFuture.supplyAsync(() -> {
                // Simulate API call delay
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "User{id=" + userId + ", name='User" + userId + "'}";
            });
        }
        
        public static CompletableFuture<String> fetchUserOrders(int userId) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "Orders{userId=" + userId + ", count=" + (userId * 2) + "}";
            });
        }
        
        public static void demonstrateAsync() {
            System.out.println("\n=== Asynchronous Operations Demo ===");
            
            int userId = 123;
            long startTime = System.currentTimeMillis();
            
            // Parallel execution of multiple async operations
            CompletableFuture<String> userFuture = fetchUserData(userId);
            CompletableFuture<String> ordersFuture = fetchUserOrders(userId);
            
            // Combine results
            CompletableFuture<String> combinedFuture = userFuture
                .thenCombine(ordersFuture, (user, orders) -> 
                    "Combined Data: " + user + " with " + orders);
            
            // Handle result
            combinedFuture
                .thenAccept(result -> {
                    long elapsed = System.currentTimeMillis() - startTime;
                    System.out.println(result);
                    System.out.println("Total time: " + elapsed + "ms");
                })
                .exceptionally(throwable -> {
                    System.err.println("Error: " + throwable.getMessage());
                    return null;
                });
            
            // Wait for completion
            try {
                combinedFuture.get(2, TimeUnit.SECONDS);
            } catch (Exception e) {
                System.err.println("Async operation failed: " + e.getMessage());
            }
        }
    }
    
    /**
     * Read-Write Lock example for concurrent data access
     */
    public static class ConcurrentCache<K, V> {
        private final Map<K, V> cache = new HashMap<>();
        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        
        public V get(K key) {
            lock.readLock().lock();
            try {
                return cache.get(key);
            } finally {
                lock.readLock().unlock();
            }
        }
        
        public void put(K key, V value) {
            lock.writeLock().lock();
            try {
                cache.put(key, value);
            } finally {
                lock.writeLock().unlock();
            }
        }
        
        public int size() {
            lock.readLock().lock();
            try {
                return cache.size();
            } finally {
                lock.readLock().unlock();
            }
        }
        
        public void clear() {
            lock.writeLock().lock();
            try {
                cache.clear();
            } finally {
                lock.writeLock().unlock();
            }
        }
    }
    
    /**
     * Demonstrates thread pool usage and parallel processing
     */
    public static void threadPoolDemo() {
        System.out.println("\n=== Thread Pool Demo ===");
        
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<Integer>> futures = new ArrayList<>();
        
        // Submit tasks that calculate squares
        for (int i = 1; i <= 10; i++) {
            final int number = i;
            Future<Integer> future = executor.submit(() -> {
                Thread.sleep(100); // Simulate work
                int result = number * number;
                System.out.println("Thread " + Thread.currentThread().getName() + 
                                 " calculated " + number + "² = " + result);
                return result;
            });
            futures.add(future);
        }
        
        // Collect results
        int sum = 0;
        for (Future<Integer> future : futures) {
            try {
                sum += future.get();
            } catch (Exception e) {
                System.err.println("Task failed: " + e.getMessage());
            }
        }
        
        System.out.println("Sum of squares: " + sum);
        
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Main demonstration method
     */
    public static void demonstrateConcurrency() {
        try {
            // Test thread-safe counter
            System.out.println("=== Thread Safety Demo ===");
            ThreadSafeCounter counter = new ThreadSafeCounter();
            
            ExecutorService executor = Executors.newFixedThreadPool(10);
            CountDownLatch latch = new CountDownLatch(1000);
            
            for (int i = 0; i < 1000; i++) {
                executor.submit(() -> {
                    counter.incrementAtomic();
                    latch.countDown();
                });
            }
            
            latch.await();
            System.out.println("Atomic counter result: " + counter.getAtomicCount());
            executor.shutdown();
            
            // Producer-Consumer demo
            new ProducerConsumerExample().demonstrate();
            
            // Async operations demo
            AsyncOperations.demonstrateAsync();
            
            // Thread pool demo
            threadPoolDemo();
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Demo interrupted");
        }
    }
}