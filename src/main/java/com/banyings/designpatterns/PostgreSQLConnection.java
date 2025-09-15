package com.banyings.designpatterns;

/**
 * PostgreSQL database connection implementation
 */
class PostgreSQLConnection implements DatabaseConnection {
    private String host;
    private int port;
    
    public PostgreSQLConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    @Override
    public void connect() {
        System.out.println("Connecting to PostgreSQL database at " + host + ":" + port);
    }
    
    @Override
    public void disconnect() {
        System.out.println("Disconnecting from PostgreSQL database");
    }
    
    @Override
    public String getConnectionString() {
        return "jdbc:postgresql://" + host + ":" + port + "/mydb";
    }
}