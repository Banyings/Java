package com.banyings.datastructures;

import java.util.*;

/**
 * Custom implementation of a Generic Binary Search Tree
 * Demonstrates generics, recursion, and tree data structures
 */
public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T> {
    private Node<T> root;
    private int size;
    
    private static class Node<T> {
        T data;
        Node<T> left;
        Node<T> right;
        
        Node(T data) {
            this.data = data;
        }
    }
    
    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }
    
    public void insert(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data");
        }
        root = insertRecursive(root, data);
    }
    
    private Node<T> insertRecursive(Node<T> node, T data) {
        if (node == null) {
            size++;
            return new Node<>(data);
        }
        
        int comparison = data.compareTo(node.data);
        if (comparison < 0) {
            node.left = insertRecursive(node.left, data);
        } else if (comparison > 0) {
            node.right = insertRecursive(node.right, data);
        }
        // If equal, don't insert duplicates
        
        return node;
    }
    
    public boolean contains(T data) {
        return containsRecursive(root, data);
    }
    
    private boolean containsRecursive(Node<T> node, T data) {
        if (node == null) return false;
        
        int comparison = data.compareTo(node.data);
        if (comparison == 0) return true;
        if (comparison < 0) return containsRecursive(node.left, data);
        return containsRecursive(node.right, data);
    }
    
    public void delete(T data) {
        root = deleteRecursive(root, data);
    }
    
    private Node<T> deleteRecursive(Node<T> node, T data) {
        if (node == null) return null;
        
        int comparison = data.compareTo(node.data);
        if (comparison < 0) {
            node.left = deleteRecursive(node.left, data);
        } else if (comparison > 0) {
            node.right = deleteRecursive(node.right, data);
        } else {
            // Node to delete found
            size--;
            
            // Case 1: No children
            if (node.left == null && node.right == null) {
                return null;
            }
            
            // Case 2: One child
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            
            // Case 3: Two children - find inorder successor
            T minValue = findMin(node.right);
            node.data = minValue;
            node.right = deleteRecursive(node.right, minValue);
            size++; // Compensate for the decrement above
        }
        
        return node;
    }
    
    private T findMin(Node<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node.data;
    }
    
    public List<T> inorderTraversal() {
        List<T> result = new ArrayList<>();
        inorderTraversalRecursive(root, result);
        return result;
    }
    
    private void inorderTraversalRecursive(Node<T> node, List<T> result) {
        if (node != null) {
            inorderTraversalRecursive(node.left, result);
            result.add(node.data);
            inorderTraversalRecursive(node.right, result);
        }
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int height() {
        return calculateHeight(root);
    }
    
    private int calculateHeight(Node<T> node) {
        if (node == null) return -1;
        return 1 + Math.max(calculateHeight(node.left), calculateHeight(node.right));
    }
    
    @Override
    public Iterator<T> iterator() {
        return inorderTraversal().iterator();
    }
    
    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        return inorderTraversal().toString();
    }
}