package com.banyings.designpatterns;

/**
 * MongoDB database connection implementation
 */
class MongoDBConnection implements DatabaseConnection {
    private String host;
    private int port;
    
    public MongoDBConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    @Override
    public void connect() {
        System.out.println("Connecting to MongoDB at " + host + ":" + port);
    }
    
    @Override
    public void disconnect() {
        System.out.println("Disconnecting from MongoDB");
    }
    
    @Override
    public String getConnectionString() {
        return "mongodb://" + host + ":" + port + "/mydb";
    }
}