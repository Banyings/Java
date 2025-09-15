package com.banyings.datastructures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class BinarySearchTreeTest {
    
    private BinarySearchTree<Integer> bst;
    
    @BeforeEach
    void setUp() {
        bst = new BinarySearchTree<>();
    }
    
    @Test
    void testEmptyTree() {
        assertTrue(bst.isEmpty());
        assertEquals(0, bst.size());
        assertEquals(-1, bst.height());
        assertFalse(bst.contains(5));
    }
    
    @Test
    void testInsertAndContains() {
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        
        assertEquals(5, bst.size());
        assertFalse(bst.isEmpty());
        
        assertTrue(bst.contains(50));
        assertTrue(bst.contains(30));
        assertTrue(bst.contains(70));
        assertTrue(bst.contains(20));
        assertTrue(bst.contains(40));
        assertFalse(bst.contains(100));
    }
    
    @Test
    void testInorderTraversal() {
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int value : values) {
            bst.insert(value);
        }
        
        List<Integer> inorder = bst.inorderTraversal();
        assertEquals(List.of(20, 30, 40, 50, 60, 70, 80), inorder);
    }
    
    @Test
    void testDelete() {
        // Build tree
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int value : values) {
            bst.insert(value);
        }
        
        // Delete leaf node
        bst.delete(20);
        assertEquals(6, bst.size());
        assertFalse(bst.contains(20));
        
        // Delete node with one child
        bst.delete(60);
        assertEquals(5, bst.size());
        assertFalse(bst.contains(60));
        
        // Delete node with two children
        bst.delete(30);
        assertEquals(4, bst.size());
        assertFalse(bst.contains(30));
        
        // Verify tree structure is still valid
        List<Integer> inorder = bst.inorderTraversal();
        for (int i = 1; i < inorder.size(); i++) {
            assertTrue(inorder.get(i-1) < inorder.get(i));
        }
    }
    
    @Test
    void testHeight() {
        assertEquals(-1, bst.height()); // Empty tree
        
        bst.insert(50);
        assertEquals(0, bst.height()); // Single node
        
        bst.insert(30);
        bst.insert(70);
        assertEquals(1, bst.height()); // Balanced tree
        
        bst.insert(20);
        bst.insert(10);
        assertEquals(3, bst.height()); // Left-heavy tree (50->30->20->10)
    }
    
    @Test
    void testDuplicateInsertion() {
        bst.insert(50);
        bst.insert(50); // Duplicate
        
        assertEquals(1, bst.size()); // Size should not increase
        assertTrue(bst.contains(50));
    }
    
    @Test
    void testNullInsertion() {
        assertThrows(IllegalArgumentException.class, () -> {
            bst.insert(null);
        });
    }
    
    @Test
    void testIterator() {
        int[] values = {50, 30, 70, 20, 40};
        for (int value : values) {
            bst.insert(value);
        }
        
        List<Integer> expected = List.of(20, 30, 40, 50, 70);
        List<Integer> actual = bst.inorderTraversal();
        assertEquals(expected, actual);
        
        // Test enhanced for loop
        int index = 0;
        for (Integer value : bst) {
            assertEquals(expected.get(index++), value);
        }
    }
    
    @Test
    void testToString() {
        assertTrue(bst.toString().equals("[]")); // Empty tree
        
        bst.insert(30);
        bst.insert(20);
        bst.insert(40);
        
        String treeString = bst.toString();
        assertNotNull(treeString);
        assertTrue(treeString.contains("20"));
        assertTrue(treeString.contains("30"));
        assertTrue(treeString.contains("40"));
    }
}