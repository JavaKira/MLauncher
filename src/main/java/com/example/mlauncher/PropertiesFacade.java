package com.example.mlauncher;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class PropertiesFacade {
    private static final Logger log = LogManager.getLogger(PropertiesFacade.class);
    public static PropertiesFacade instance;

    private final File file;
    private final Properties properties;
    private EventHandler<ActionEvent> onUpdated = event -> {};

    public static PropertiesFacade getInstance() {
        if (instance == null)
            instance = new PropertiesFacade();

        return instance;
    }

    protected PropertiesFacade() {
        file = new File("settings.properties");
        properties = new Properties();
        try {
            if (!file.exists()) {
                log.info("File " + file.getAbsolutePath() + " created: " + file.createNewFile());
                setDefaults();
                storeProperties();
                return;
            }

            log.info("Starting loading settings.properties");
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void storeProperties() {
        log.info("Starting store settings.properties");
        try {
            properties.store(new FileOutputStream(file), "");
            log.info("onUpdated handling ActionEvent");
            onUpdated.handle(new ActionEvent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDefaults() {
        setBeBuilds(0);
        setVersions(1);
        setPath(System.getenv("APPDATA") + "\\MLauncher\\Versions\\");
    }

    public void setBeBuilds(int beBuilds) {
        properties.setProperty("beBuilds", String.valueOf(beBuilds));
    }

    public int getBeBuilds() {
        return Integer.parseInt(properties.getProperty("beBuilds"));
    }

    public void setVersions(int oldVersions) {
        properties.setProperty("versions", String.valueOf(oldVersions));
    }

    public int getVersions() {
        return Integer.parseInt(properties.getProperty("versions"));
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

    public void setOnUpdated(EventHandler<ActionEvent> onUpdated) {
        this.onUpdated = onUpdated;
    }
}
