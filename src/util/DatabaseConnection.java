package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseConnection {

    private static final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        Map<String, String> envValues = loadEnvFile();
        String driver = resolveSetting("DB_DRIVER", envValues, DEFAULT_DRIVER);
        String url = requireSetting("DB_URL", envValues);
        String user = requireSetting("DB_USER", envValues);
        String password = requireSetting("DB_PASSWORD", envValues);

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC driver not found: " + driver, e);
        }

        if (user == null || user.trim().isEmpty()) {
            return DriverManager.getConnection(url);
        }

        return DriverManager.getConnection(url, user, password);
    }

    private static String resolveSetting(String key, Map<String, String> envValues, String fallback) {
        String fromSystemEnv = System.getenv(key);
        if (fromSystemEnv != null && !fromSystemEnv.trim().isEmpty()) {
            return fromSystemEnv.trim();
        }
        String fromEnvFile = envValues.get(key);
        if (fromEnvFile != null && !fromEnvFile.trim().isEmpty()) {
            return fromEnvFile.trim();
        }
        return fallback;
    }

    private static String requireSetting(String key, Map<String, String> envValues) {
        String fromSystemEnv = System.getenv(key);
        if (fromSystemEnv != null && !fromSystemEnv.trim().isEmpty()) {
            return fromSystemEnv.trim();
        }

        String fromEnvFile = envValues.get(key);
        if (fromEnvFile != null && !fromEnvFile.trim().isEmpty()) {
            return fromEnvFile.trim();
        }

        throw new IllegalStateException("Missing required database setting: " + key + " in .env or system environment.");
    }

    private static Map<String, String> loadEnvFile() {
        Map<String, String> values = new HashMap<>();
        Path envPath = Paths.get(".env");

        if (!Files.exists(envPath)) {
            return values;
        }

        try {
            List<String> lines = Files.readAllLines(envPath);
            for (String line : lines) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }
                int idx = trimmed.indexOf('=');
                if (idx <= 0) {
                    continue;
                }
                String key = trimmed.substring(0, idx).trim();
                String value = trimmed.substring(idx + 1).trim();
                values.put(key, value);
            }
        } catch (IOException ignored) {
            // Fallback to defaults/system env when .env cannot be parsed.
        }

        return values;
    }
}
