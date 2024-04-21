package stepDefinitions.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for reading configuration properties.
 */
public class ConfigReader {

    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private static final String CONFIG_FILE = "config.properties";

    /**
     * Retrieves the base URL from the configuration properties.
     *
     * @return The base URL.
     * @throws RuntimeException If an error occurs while reading the configuration file.
     */
    public static String getBaseUrl() {
        Properties properties = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                String errorMessage = "Unable to find " + CONFIG_FILE;
                logger.error(errorMessage);
                throw new RuntimeException(errorMessage);
            }
            properties.load(input);
            return properties.getProperty("baseUrl");
        } catch (IOException ex) {
            String errorMessage = "Error reading " + CONFIG_FILE;
            logger.error(errorMessage, ex);
            throw new RuntimeException(errorMessage, ex);
        }
    }
}
