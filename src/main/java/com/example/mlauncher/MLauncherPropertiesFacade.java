package com.example.mlauncher;

import java.io.*;
import java.util.Properties;

public class MLauncherPropertiesFacade {
    public static MLauncherPropertiesFacade instance;

    private final File file;
    private final Properties properties;

    public static MLauncherPropertiesFacade getInstance() {
        if (instance == null)
            instance = new MLauncherPropertiesFacade();

        return instance;
    }

    protected MLauncherPropertiesFacade() {
        file = new File("settings.properties");
        properties = new Properties();
        try {
            if (!file.exists()) file.createNewFile();
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void storeProperties() {
        try {
            properties.store(new FileOutputStream(file), "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
