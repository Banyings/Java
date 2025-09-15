package com.banyings.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Utility class demonstrating practical Java programming
 * Including file operations, JSON processing, validation, and string manipulation
 */
public class UtilityLibrary {
    
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);
    
    /**
     * File operations utilities
     */
    public static class FileUtils {
        
        public static void writeToFile(String content, String filePath) throws IOException {
            Path path = Paths.get(filePath);
            Files.createDirectories(path.getParent());
            Files.write(path, content.getBytes());
        }
        
        public static String readFromFile(String filePath) throws IOException {
            return Files.readString(Paths.get(filePath));
        }
        
        public static List<String> readLines(String filePath) throws IOException {
            return Files.readAllLines(Paths.get(filePath));
        }
        
        public static void copyFile(String source, String destination) throws IOException {
            Path sourcePath = Paths.get(source);
            Path destPath = Paths.get(destination);
            Files.createDirectories(destPath.getParent());
            Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
        }
        
        public static boolean deleteFile(String filePath) {
            try {
                return Files.deleteIfExists(Paths.get(filePath));
            } catch (IOException e) {
                return false;
            }
        }
        
        public static List<String> findFilesWithExtension(String directory, String extension) {
            List<String> files = new ArrayList<>();
            try {
                Files.walk(Paths.get(directory))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(extension))
                    .forEach(path -> files.add(path.toString()));
            } catch (IOException e) {
                System.err.println("Error walking directory: " + e.getMessage());
            }
            return files;
        }
    }
    
    /**
     * JSON processing utilities
     */
    public static class JsonUtils {
        
        public static <T> String toJson(T object) throws Exception {
            return JSON_MAPPER.writeValueAsString(object);
        }
        
        public static <T> T fromJson(String json, Class<T> clazz) throws Exception {
            return JSON_MAPPER.readValue(json, clazz);
        }
        
        public static <T> void writeJsonToFile(T object, String filePath) throws Exception {
            String json = toJson(object);
            FileUtils.writeToFile(json, filePath);
        }
        
        public static <T> T readJsonFromFile(String filePath, Class<T> clazz) throws Exception {
            String json = FileUtils.readFromFile(filePath);
            return fromJson(json, clazz);
        }
    }
    
    /**
     * String manipulation utilities
     */
    public static class StringUtils {
        
        public static boolean isNullOrEmpty(String str) {
            return str == null || str.isEmpty();
        }
        
        public static boolean isNullOrBlank(String str) {
            return str == null || str.trim().isEmpty();
        }
        
        public static String capitalize(String str) {
            if (isNullOrEmpty(str)) return str;
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
        
        public static String toCamelCase(String str) {
            if (isNullOrEmpty(str)) return str;
            
            StringBuilder result = new StringBuilder();
            boolean capitalizeNext = false;
            
            for (char c : str.toCharArray()) {
                if (Character.isLetterOrDigit(c)) {
                    if (capitalizeNext) {
                        result.append(Character.toUpperCase(c));
                        capitalizeNext = false;
                    } else {
                        result.append(Character.toLowerCase(c));
                    }
                } else {
                    capitalizeNext = true;
                }
            }
            
            return result.toString();
        }
        
        public static String reverse(String str) {
            if (isNullOrEmpty(str)) return str;
            return new StringBuilder(str).reverse().toString();
        }
        
        public static int countOccurrences(String text, String substring) {
            if (isNullOrEmpty(text) || isNullOrEmpty(substring)) return 0;
            
            int count = 0;
            int index = 0;
            while ((index = text.indexOf(substring, index)) != -1) {
                count++;
                index += substring.length();
            }
            return count;
        }
        
        public static String truncate(String str, int maxLength) {
            if (str == null || str.length() <= maxLength) return str;
            return str.substring(0, maxLength - 3) + "...";
        }
    }
    
    /**
     * Validation utilities
     */
    public static class ValidationUtils {
        
        private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        );
        
        private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^\\+?[1-9]\\d{1,14}$"
        );
        
        public static boolean isValidEmail(String email) {
            return email != null && EMAIL_PATTERN.matcher(email).matches();
        }
        
        public static boolean isValidPhoneNumber(String phone) {
            if (phone == null) return false;
            String cleanPhone = phone.replaceAll("[\\s\\-\\(\\)]", "");
            return PHONE_PATTERN.matcher(cleanPhone).matches();
        }
        
        public static boolean isValidUrl(String url) {
            if (StringUtils.isNullOrEmpty(url)) return false;
            try {
                new java.net.URL(url);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        
        public static boolean isNumeric(String str) {
            if (StringUtils.isNullOrEmpty(str)) return false;
            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        
        public static boolean isInRange(int value, int min, int max) {
            return value >= min && value <= max;
        }
    }
    
    /**
     * Date/Time utilities
     */
    public static class DateTimeUtils {
        
        private static final DateTimeFormatter DEFAULT_FORMATTER = 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        public static String getCurrentTimestamp() {
            return LocalDateTime.now().format(DEFAULT_FORMATTER);
        }
        
        public static String formatDateTime(LocalDateTime dateTime, String pattern) {
            return dateTime.format(DateTimeFormatter.ofPattern(pattern));
        }
        
        public static LocalDateTime parseDateTime(String dateTimeStr, String pattern) {
            return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
        }
        
        public static long getTimestamp() {
            return System.currentTimeMillis();
        }
        
        public static String getTimeAgo(LocalDateTime dateTime) {
            LocalDateTime now = LocalDateTime.now();
            long seconds = java.time.Duration.between(dateTime, now).getSeconds();
            
            if (seconds < 60) return seconds + " seconds ago";
            if (seconds < 3600) return (seconds / 60) + " minutes ago";
            if (seconds < 86400) return (seconds / 3600) + " hours ago";
            return (seconds / 86400) + " days ago";
        }
    }
    
    /**
     * Collection utilities
     */
    public static class CollectionUtils {
        
        public static <T> boolean isNullOrEmpty(Collection<T> collection) {
            return collection == null || collection.isEmpty();
        }
        
        public static <T> List<T> safe(List<T> list) {
            return list != null ? list : new ArrayList<>();
        }
        
        public static <T> List<List<T>> partition(List<T> list, int size) {
            List<List<T>> partitions = new ArrayList<>();
            for (int i = 0; i < list.size(); i += size) {
                partitions.add(list.subList(i, Math.min(i + size, list.size())));
            }
            return partitions;
        }
        
        public static <T> T getRandomElement(List<T> list) {
            if (isNullOrEmpty(list)) return null;
            Random random = new Random();
            return list.get(random.nextInt(list.size()));
        }
        
        public static <K, V> Map<K, V> mergeMaps(Map<K, V> map1, Map<K, V> map2) {
            Map<K, V> result = new HashMap<>(map1);
            result.putAll(map2);
            return result;
        }
    }
    
    /**
     * Demo data class for JSON serialization
     */
    public static class Person {
        private String name;
        private int age;
        private String email;
        private List<String> hobbies;
        
        // Default constructor for JSON deserialization
        public Person() {}
        
        public Person(String name, int age, String email, List<String> hobbies) {
            this.name = name;
            this.age = age;
            this.email = email;
            this.hobbies = hobbies;
        }
        
        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public List<String> getHobbies() { return hobbies; }
        public void setHobbies(List<String> hobbies) { this.hobbies = hobbies; }
        
        @Override
        public String toString() {
            return String.format("Person{name='%s', age=%d, email='%s', hobbies=%s}", 
                               name, age, email, hobbies);
        }
    }
}