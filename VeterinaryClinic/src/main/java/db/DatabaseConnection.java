package db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public final class DatabaseConnection {

    private static volatile DatabaseConnection instance;
    private final HikariDataSource dataSource;

    private DatabaseConnection() {
        dataSource = createDataSource();
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) instance = new DatabaseConnection();
            }
        }
        return instance;
    }

    private HikariDataSource createDataSource() {
        HikariConfig cfg = new HikariConfig();

        cfg.setJdbcUrl(DatabaseConfig.getProperty("db.url"));
        cfg.setUsername(DatabaseConfig.getProperty("db.user"));
        cfg.setPassword(DatabaseConfig.getProperty("db.password"));

        cfg.setPoolName(DatabaseConfig.getProperty("db.pool.poolName", "AppPool"));
        cfg.setMaximumPoolSize(Integer.parseInt(DatabaseConfig.getProperty("db.pool.maximumPoolSize", "10")));
        cfg.setMinimumIdle(Integer.parseInt(DatabaseConfig.getProperty("db.pool.minimumIdle", "2")));

        cfg.setConnectionTimeout(Long.parseLong(DatabaseConfig.getProperty("db.pool.connectionTimeoutMs", "30000")));
        cfg.setIdleTimeout(Long.parseLong(DatabaseConfig.getProperty("db.pool.idleTimeoutMs", "600000")));
        cfg.setMaxLifetime(Long.parseLong(DatabaseConfig.getProperty("db.pool.maxLifetimeMs", "1800000")));
        cfg.setValidationTimeout(Long.parseLong(DatabaseConfig.getProperty("db.pool.validationTimeoutMs", "5000")));

        return new HikariDataSource(cfg);
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения соединения из пула: " + e.getMessage(), e);
        }
    }

    public void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
