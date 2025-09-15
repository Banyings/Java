package com.banyings.algorithms;

import java.util.*;

/**
 * Implementation of various sorting algorithms
 * Demonstrates algorithmic thinking and complexity analysis
 */
public class SortingAlgorithms {
    
    /**
     * Quick Sort - Average O(n log n), Worst O(n²)
     */
    public static <T extends Comparable<T>> void quickSort(List<T> list) {
        if (list == null || list.size() <= 1) return;
        quickSortHelper(list, 0, list.size() - 1);
    }
    
    private static <T extends Comparable<T>> void quickSortHelper(List<T> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(list, low, high);
            quickSortHelper(list, low, pivotIndex - 1);
            quickSortHelper(list, pivotIndex + 1, high);
        }
    }
    
    private static <T extends Comparable<T>> int partition(List<T> list, int low, int high) {
        T pivot = list.get(high);
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (list.get(j).compareTo(pivot) <= 0) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, high);
        return i + 1;
    }
    
    /**
     * Merge Sort - O(n log n) time complexity
     */
    public static <T extends Comparable<T>> void mergeSort(List<T> list) {
        if (list == null || list.size() <= 1) return;
        
        List<T> temp = new ArrayList<>(list);
        mergeSortHelper(list, temp, 0, list.size() - 1);
    }
    
    private static <T extends Comparable<T>> void mergeSortHelper(
            List<T> list, List<T> temp, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortHelper(list, temp, left, mid);
            mergeSortHelper(list, temp, mid + 1, right);
            merge(list, temp, left, mid, right);
        }
    }
    
    private static <T extends Comparable<T>> void merge(
            List<T> list, List<T> temp, int left, int mid, int right) {
        
        // Copy to temp array
        for (int i = left; i <= right; i++) {
            temp.set(i, list.get(i));
        }
        
        int i = left, j = mid + 1, k = left;
        
        while (i <= mid && j <= right) {
            if (temp.get(i).compareTo(temp.get(j)) <= 0) {
                list.set(k++, temp.get(i++));
            } else {
                list.set(k++, temp.get(j++));
            }
        }
        
        while (i <= mid) {
            list.set(k++, temp.get(i++));
        }
        
        while (j <= right) {
            list.set(k++, temp.get(j++));
        }
    }
    
    /**
     * Heap Sort - O(n log n) time complexity
     */
    public static <T extends Comparable<T>> void heapSort(List<T> list) {
        if (list == null || list.size() <= 1) return;
        
        int n = list.size();
        
        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(list, n, i);
        }
        
        // Extract elements from heap one by one
        for (int i = n - 1; i > 0; i--) {
            Collections.swap(list, 0, i);
            heapify(list, i, 0);
        }
    }
    
    private static <T extends Comparable<T>> void heapify(List<T> list, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        
        if (left < n && list.get(left).compareTo(list.get(largest)) > 0) {
            largest = left;
        }
        
        if (right < n && list.get(right).compareTo(list.get(largest)) > 0) {
            largest = right;
        }
        
        if (largest != i) {
            Collections.swap(list, i, largest);
            heapify(list, n, largest);
        }
    }
    
    /**
     * Binary Search - O(log n) time complexity
     */
    public static <T extends Comparable<T>> int binarySearch(List<T> sortedList, T target) {
        if (sortedList == null || target == null) return -1;
        
        int left = 0, right = sortedList.size() - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = target.compareTo(sortedList.get(mid));
            
            if (comparison == 0) return mid;
            if (comparison < 0) right = mid - 1;
            else left = mid + 1;
        }
        
        return -1;
    }
    
    /**
     * Utility method to demonstrate performance comparison
     */
    public static void comparePerformance() {
        Random random = new Random();
        int size = 10000;
        
        // Generate test data
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add(random.nextInt(size));
        }
        
        // Test QuickSort
        List<Integer> quickSortData = new ArrayList<>(data);
        long startTime = System.nanoTime();
        quickSort(quickSortData);
        long quickSortTime = System.nanoTime() - startTime;
        
        // Test MergeSort
        List<Integer> mergeSortData = new ArrayList<>(data);
        startTime = System.nanoTime();
        mergeSort(mergeSortData);
        long mergeSortTime = System.nanoTime() - startTime;
        
        // Test HeapSort
        List<Integer> heapSortData = new ArrayList<>(data);
        startTime = System.nanoTime();
        heapSort(heapSortData);
        long heapSortTime = System.nanoTime() - startTime;
        
        System.out.printf("Performance comparison for %d elements:%n", size);
        System.out.printf("QuickSort: %.2f ms%n", quickSortTime / 1_000_000.0);
        System.out.printf("MergeSort: %.2f ms%n", mergeSortTime / 1_000_000.0);
        System.out.printf("HeapSort:  %.2f ms%n", heapSortTime / 1_000_000.0);
    }
}