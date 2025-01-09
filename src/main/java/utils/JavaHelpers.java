package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class JavaHelpers {

    public static String getProperty(String propertyFile, String propertyName) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(propertyFile));
        return properties.getProperty(propertyName);
    }
}
