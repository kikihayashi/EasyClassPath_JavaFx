package com.acer.main.model.config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigManager {

    private static String CONFIG_FILE = Property.CONFIG_FILE.getKey();

    public static String getPropertyValue(Property property) {
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(getConfigFilePath())) {
            properties.load(inputStream);
            return properties.getProperty(property.getKey());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updatePropertyValue(Property property, String newValue) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = Files.newInputStream(getConfigFilePath());
            properties.load(inputStream);
            properties.setProperty(property.getKey(), newValue);

            FileOutputStream outputStream = new FileOutputStream(getConfigFilePath().toString());
            properties.store(outputStream, "Update Value");
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path getConfigFilePath() {
        try {
            Path jarPath = Paths.get(ConfigManager.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            return jarPath.getParent().resolve(CONFIG_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}