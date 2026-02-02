package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    private static volatile DatabaseConnection instance;
    private volatile Connection connection;

    private DatabaseConnection() {
        connectOrThrow();
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) instance = new DatabaseConnection();
            }
        }
        return instance;
    }

    private void connectOrThrow() {
        try {
            Class.forName("org.postgresql.Driver");

            final String url  = DatabaseConfig.getProperty("db.url");
            final String user = DatabaseConfig.getProperty("db.user");
            final String pass = DatabaseConfig.getProperty("db.password");

            Connection conn = DriverManager.getConnection(url, user, pass);

            try {
                if (!conn.isValid(2))
                    throw new SQLException("Connection is not valid");
            } catch (AbstractMethodError ignored) { }

            this.connection = conn;
            System.out.println("Соединение с БД установлено");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка подключения к БД: " + e.getMessage(), e);
        }
    }

    private boolean alive() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public Connection getConnection() {
        if (!alive()) {
            System.out.println("Переподключение к БД...");
            connectOrThrow();
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close(); System.out.println("Соединение закрыто");
            } catch (SQLException ignored) { }
            finally {
                connection = null;
            }
        }
    }
}
