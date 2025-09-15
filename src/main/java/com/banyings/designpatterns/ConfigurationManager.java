package com.banyings.designpatterns;

/**
 * Singleton Pattern implementation with thread safety
 * Demonstrates the Singleton design pattern with lazy initialization
 */
public class ConfigurationManager {
    // Volatile ensures visibility across threads
    private static volatile ConfigurationManager instance;
    private String environment;
    private String databaseUrl;
    private int maxConnections;
    
    // Private constructor prevents external instantiation
    private ConfigurationManager() {
        // Initialize with default values
        this.environment = "development";
        this.databaseUrl = "jdbc:h2:mem:testdb";
        this.maxConnections = 10;
    }
    
    /**
     * Double-checked locking pattern for thread-safe singleton
     */
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }
    
    // Configuration methods
    public String getEnvironment() { return environment; }
    public void setEnvironment(String environment) { this.environment = environment; }
    
    public String getDatabaseUrl() { return databaseUrl; }
    public void setDatabaseUrl(String databaseUrl) { this.databaseUrl = databaseUrl; }
    
    public int getMaxConnections() { return maxConnections; }
    public void setMaxConnections(int maxConnections) { this.maxConnections = maxConnections; }
    
    public void loadConfiguration(String env) {
        switch (env.toLowerCase()) {
            case "production":
                this.environment = "production";
                this.databaseUrl = "jdbc:postgresql://prod-db:5432/myapp";
                this.maxConnections = 100;
                break;
            case "staging":
                this.environment = "staging";
                this.databaseUrl = "jdbc:postgresql://staging-db:5432/myapp";
                this.maxConnections = 50;
                break;
            default:
                this.environment = "development";
                this.databaseUrl = "jdbc:h2:mem:testdb";
                this.maxConnections = 10;
        }
    }
    
    @Override
    public String toString() {
        return String.format("Configuration{env='%s', db='%s', maxConn=%d}", 
                           environment, databaseUrl, maxConnections);
    }
}