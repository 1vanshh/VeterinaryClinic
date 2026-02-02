package db;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static final String PROPERTIES_FILE = "db.properties";
    private static final Properties props = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new RuntimeException("Файл " + PROPERTIES_FILE + " не найден в classpath!");
            }

            props.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении " + PROPERTIES_FILE, e);
        }
    }

    public static String getProperty(String key) {
        if (!props.containsKey(key)) {
            throw new IllegalArgumentException("Свойство с ключом '" + key + "' не найдено в " + PROPERTIES_FILE);
        }
        return props.getProperty(key);
    }
}
