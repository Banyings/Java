package com.banyings.utilities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class UtilityLibraryTest {
    
    @Test
    void testStringUtilities() {
        // Test null/empty checks
        assertTrue(UtilityLibrary.StringUtils.isNullOrEmpty(null));
        assertTrue(UtilityLibrary.StringUtils.isNullOrEmpty(""));
        assertFalse(UtilityLibrary.StringUtils.isNullOrEmpty("test"));
        
        assertTrue(UtilityLibrary.StringUtils.isNullOrBlank(null));
        assertTrue(UtilityLibrary.StringUtils.isNullOrBlank("   "));
        assertFalse(UtilityLibrary.StringUtils.isNullOrBlank("test"));
        
        // Test capitalize
        assertEquals("Hello", UtilityLibrary.StringUtils.capitalize("hello"));
        assertEquals("Hello", UtilityLibrary.StringUtils.capitalize("HELLO"));
        assertNull(UtilityLibrary.StringUtils.capitalize(null));
        
        // Test camelCase
        assertEquals("helloWorld", UtilityLibrary.StringUtils.toCamelCase("hello-world"));
        assertEquals("helloWorldExample", UtilityLibrary.StringUtils.toCamelCase("hello_world_example"));
        
        // Test reverse
        assertEquals("avaJ", UtilityLibrary.StringUtils.reverse("Java"));
        assertEquals("", UtilityLibrary.StringUtils.reverse(""));
        
        // Test count occurrences
        assertEquals(2, UtilityLibrary.StringUtils.countOccurrences("hello world hello", "hello"));
        assertEquals(0, UtilityLibrary.StringUtils.countOccurrences("test", "xyz"));
        
        // Test truncate
        assertEquals("Hello...", UtilityLibrary.StringUtils.truncate("Hello World", 8));
        assertEquals("Hello", UtilityLibrary.StringUtils.truncate("Hello", 10));
    }
    
    @Test
    void testValidationUtilities() {
        // Test email validation
        assertTrue(UtilityLibrary.ValidationUtils.isValidEmail("test@example.com"));
        assertTrue(UtilityLibrary.ValidationUtils.isValidEmail("user.name+tag@domain.co.uk"));
        assertFalse(UtilityLibrary.ValidationUtils.isValidEmail("invalid-email"));
        assertFalse(UtilityLibrary.ValidationUtils.isValidEmail("@domain.com"));
        assertFalse(UtilityLibrary.ValidationUtils.isValidEmail(null));
        
        // Test phone validation
        assertTrue(UtilityLibrary.ValidationUtils.isValidPhoneNumber("+1234567890"));
        assertTrue(UtilityLibrary.ValidationUtils.isValidPhoneNumber("1234567890"));
        assertTrue(UtilityLibrary.ValidationUtils.isValidPhoneNumber("+44 20 7946 0958"));
        assertFalse(UtilityLibrary.ValidationUtils.isValidPhoneNumber("abc"));
        assertFalse(UtilityLibrary.ValidationUtils.isValidPhoneNumber(null));
        
        // Test URL validation
        assertTrue(UtilityLibrary.ValidationUtils.isValidUrl("https://www.example.com"));
        assertTrue(UtilityLibrary.ValidationUtils.isValidUrl("http://localhost:8080"));
        assertFalse(UtilityLibrary.ValidationUtils.isValidUrl("invalid-url"));
        assertFalse(UtilityLibrary.ValidationUtils.isValidUrl(null));
        
        // Test numeric validation
        assertTrue(UtilityLibrary.ValidationUtils.isNumeric("123"));
        assertTrue(UtilityLibrary.ValidationUtils.isNumeric("123.45"));
        assertTrue(UtilityLibrary.ValidationUtils.isNumeric("-123.45"));
        assertFalse(UtilityLibrary.ValidationUtils.isNumeric("abc"));
        assertFalse(UtilityLibrary.ValidationUtils.isNumeric(null));
        
        // Test range validation
        assertTrue(UtilityLibrary.ValidationUtils.isInRange(5, 1, 10));
        assertTrue(UtilityLibrary.ValidationUtils.isInRange(1, 1, 10));
        assertTrue(UtilityLibrary.ValidationUtils.isInRange(10, 1, 10));
        assertFalse(UtilityLibrary.ValidationUtils.isInRange(0, 1, 10));
        assertFalse(UtilityLibrary.ValidationUtils.isInRange(11, 1, 10));
    }
    
    @Test
    void testDateTimeUtilities() {
        // Test current timestamp (just ensure it returns something)
        String timestamp = UtilityLibrary.DateTimeUtils.getCurrentTimestamp();
        assertNotNull(timestamp);
        assertFalse(timestamp.isEmpty());
        
        // Test timestamp generation
        long millis = UtilityLibrary.DateTimeUtils.getTimestamp();
        assertTrue(millis > 0);
        
        // Test time formatting
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        String formatted = UtilityLibrary.DateTimeUtils.formatDateTime(now, "yyyy-MM-dd");
        assertNotNull(formatted);
        assertTrue(formatted.matches("\\d{4}-\\d{2}-\\d{2}"));
    }
    
    @Test
    void testCollectionUtilities() {
        // Test null/empty checks
        assertTrue(UtilityLibrary.CollectionUtils.isNullOrEmpty(null));
        assertTrue(UtilityLibrary.CollectionUtils.isNullOrEmpty(new ArrayList<>()));
        assertFalse(UtilityLibrary.CollectionUtils.isNullOrEmpty(Arrays.asList("item")));
        
        // Test safe list
        List<String> nullList = null;
        List<String> safeList = UtilityLibrary.CollectionUtils.safe(nullList);
        assertNotNull(safeList);
        assertTrue(safeList.isEmpty());
        
        List<String> existingList = Arrays.asList("a", "b");
        assertEquals(existingList, UtilityLibrary.CollectionUtils.safe(existingList));
        
        // Test partition
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        List<List<Integer>> partitions = UtilityLibrary.CollectionUtils.partition(numbers, 3);
        assertEquals(3, partitions.size());
        assertEquals(Arrays.asList(1, 2, 3), partitions.get(0));
        assertEquals(Arrays.asList(4, 5, 6), partitions.get(1));
        assertEquals(Arrays.asList(7), partitions.get(2));
        
        // Test random element
        List<String> items = Arrays.asList("apple", "banana", "cherry");
        String randomItem = UtilityLibrary.CollectionUtils.getRandomElement(items);
        assertTrue(items.contains(randomItem));
        
        assertNull(UtilityLibrary.CollectionUtils.getRandomElement(null));
        assertNull(UtilityLibrary.CollectionUtils.getRandomElement(new ArrayList<>()));
        
        // Test merge maps
        Map<String, Integer> map1 = Map.of("a", 1, "b", 2);
        Map<String, Integer> map2 = Map.of("c", 3, "d", 4);
        Map<String, Integer> merged = UtilityLibrary.CollectionUtils.mergeMaps(map1, map2);
        assertEquals(4, merged.size());
        assertEquals(Integer.valueOf(1), merged.get("a"));
        assertEquals(Integer.valueOf(4), merged.get("d"));
    }
    
    @Test
    void testJsonUtilities() {
        try {
            // Test JSON serialization/deserialization
            UtilityLibrary.Person person = new UtilityLibrary.Person(
                "John Doe", 30, "john@example.com", 
                Arrays.asList("reading", "coding")
            );
            
            // Serialize to JSON
            String json = UtilityLibrary.JsonUtils.toJson(person);
            assertNotNull(json);
            assertTrue(json.contains("John Doe"));
            assertTrue(json.contains("john@example.com"));
            
            // Deserialize from JSON
            UtilityLibrary.Person deserializedPerson = UtilityLibrary.JsonUtils.fromJson(json, UtilityLibrary.Person.class);
            assertEquals(person.getName(), deserializedPerson.getName());
            assertEquals(person.getAge(), deserializedPerson.getAge());
            assertEquals(person.getEmail(), deserializedPerson.getEmail());
            assertEquals(person.getHobbies(), deserializedPerson.getHobbies());
            
        } catch (Exception e) {
            fail("JSON processing should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    void testPersonClass() {
        UtilityLibrary.Person person = new UtilityLibrary.Person(
            "Jane Smith", 25, "jane@example.com", 
            Arrays.asList("swimming", "photography")
        );
        
        assertEquals("Jane Smith", person.getName());
        assertEquals(25, person.getAge());
        assertEquals("jane@example.com", person.getEmail());
        assertEquals(2, person.getHobbies().size());
        
        String toString = person.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Jane Smith"));
        assertTrue(toString.contains("25"));
    }
}