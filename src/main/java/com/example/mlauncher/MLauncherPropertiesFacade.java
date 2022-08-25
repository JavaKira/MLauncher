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
            if (!file.exists()) {
                file.createNewFile();
                setDefaults();
                storeProperties();
                return;
            }

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

    public void setDefaults() {
        setBeBuilds(false);
        setOldBeBuilds(false);
        setOldVersions(true);
        setPath(System.getenv("APPDATA") + "\\MLauncher\\Versions\\");
    }

    public void setBeBuilds(boolean beBuilds) {
        properties.setProperty("beBuilds", String.valueOf(beBuilds));
    }

    public boolean getBeBuilds() {
        return Boolean.parseBoolean(properties.getProperty("beBuilds"));
    }

    public void setOldBeBuilds(boolean oldBeBuilds) {
        properties.setProperty("oldBeBuilds", String.valueOf(oldBeBuilds));
    }

    public boolean getOldBeBuilds() {
        return Boolean.parseBoolean(properties.getProperty("oldBeBuilds"));
    }

    public void setOldVersions(boolean oldVersions) {
        properties.setProperty("oldVersions", String.valueOf(oldVersions));
    }

    public boolean getOldVersions() {
        return Boolean.parseBoolean(properties.getProperty("oldVersions"));
    }

    public void setPath(String path) {
        properties.setProperty("path", path);
    }

    public String getPath() {
        return properties.getProperty("path");
    }

    public Properties getProperties() {
        return properties;
    }
}
