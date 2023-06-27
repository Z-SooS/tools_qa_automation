package zsoos.pets.tools_qa_automation.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private PropertyReader() {
    }

    public static final String FILE_ENV_VARIABLE = "config_file";
    private static final String DEFAULT_PROPERTY_FILE = "config.properties";

    private static Properties properties;

    public static String getMustHaveProperty(String propertyName) {
        String value = System.getProperty(propertyName);
        if (value != null) return value;

        if (properties == null) setPropertiesFromFile();

        value = properties.getProperty(propertyName, null);

        if (value == null) throw new RuntimeException("Property " + propertyName + " not set in property file");

        return value;
    }

    public static String getPropertyOrDefault(String propertyName, String defaultValue) {
        String value = System.getProperty(propertyName);
        if (value != null) return value;

        if (properties == null) setPropertiesFromFile();

        return properties.getProperty(propertyName, defaultValue);
    }

    private static void setPropertiesFromFile() {
        final String configFileName = "/" + System.getProperty(FILE_ENV_VARIABLE, DEFAULT_PROPERTY_FILE);
        try (InputStream input = PropertyReader.class.getResourceAsStream(configFileName)) {
            if (input == null) throw new IOException();
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Could not load properties, file not found or could not be accessed: " + configFileName, e);
        }
    }

    static void nullProperties() {
        properties = null;
    }
}
