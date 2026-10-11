package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Central MySQL connection settings. Configure credentials through environment variables. */
public final class DBConnection {
    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/campus_connect?serverTimezone=Asia/Kolkata&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String URL = env("CAMPUS_CONNECT_DB_URL", DEFAULT_URL);
    private static final String USER = env("CAMPUS_CONNECT_DB_USER", "root");
    private static final String PASSWORD = env("CAMPUS_CONNECT_DB_PASSWORD", "Shree@007");

    private DBConnection() { }

    private static String env(String key, String fallback) {
        String value = System.getenv(key);
        return value == null ? fallback : value;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void testConnection() throws SQLException {
        try (Connection connection = getConnection()) {
            System.out.println("MySQL connected successfully: " + connection.getCatalog());
        }
    }
}
