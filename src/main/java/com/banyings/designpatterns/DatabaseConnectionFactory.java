package com.banyings.designpatterns;

/**
 * Factory for creating database connections
 */
public class DatabaseConnectionFactory {
    
    public enum DatabaseType {
        MYSQL, POSTGRESQL, MONGODB
    }
    
    public static DatabaseConnection createConnection(DatabaseType type, String host, int port) {
        switch (type) {
            case MYSQL:
                return new MySQLConnection(host, port);
            case POSTGRESQL:
                return new PostgreSQLConnection(host, port);
            case MONGODB:
                return new MongoDBConnection(host, port);
            default:
                throw new IllegalArgumentException("Unsupported database type: " + type);
        }
    }
    
    // Convenience method with default values
    public static DatabaseConnection createConnection(DatabaseType type) {
        return createConnection(type, "localhost", getDefaultPort(type));
    }
    
    private static int getDefaultPort(DatabaseType type) {
        switch (type) {
            case MYSQL: return 3306;
            case POSTGRESQL: return 5432;
            case MONGODB: return 27017;
            default: return 0;
        }
    }
    
    // Demonstration method
    public static void demonstrateFactory() {
        System.out.println("=== Database Factory Pattern Demo ===");
        
        DatabaseConnection mysql = createConnection(DatabaseType.MYSQL);
        DatabaseConnection postgres = createConnection(DatabaseType.POSTGRESQL, "prod-db", 5432);
        DatabaseConnection mongo = createConnection(DatabaseType.MONGODB, "nosql-cluster", 27017);
        
        DatabaseConnection[] connections = {mysql, postgres, mongo};
        
        for (DatabaseConnection conn : connections) {
            conn.connect();
            System.out.println("Connection String: " + conn.getConnectionString());
            conn.disconnect();
            System.out.println();
        }
    }
}