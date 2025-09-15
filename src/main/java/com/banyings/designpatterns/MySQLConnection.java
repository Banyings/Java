package com.banyings.designpatterns;

/**
 * MySQL database connection implementation
 */
class MySQLConnection implements DatabaseConnection {
    private String host;
    private int port;
    
    public MySQLConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    @Override
    public void connect() {
        System.out.println("Connecting to MySQL database at " + host + ":" + port);
    }
    
    @Override
    public void disconnect() {
        System.out.println("Disconnecting from MySQL database");
    }
    
    @Override
    public String getConnectionString() {
        return "jdbc:mysql://" + host + ":" + port + "/mydb";
    }
}