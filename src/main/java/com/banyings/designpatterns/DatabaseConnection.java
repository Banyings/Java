package com.banyings.designpatterns;

/**
 * Product interface for database connections
 */
public interface DatabaseConnection {
    void connect();
    void disconnect();
    String getConnectionString();
}