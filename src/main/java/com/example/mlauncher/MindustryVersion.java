package com.example.mlauncher;

import java.io.File;

public class MindustryVersion {
    private final String name;

    public MindustryVersion(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isDownloaded() {
        return new File(System.getenv("APPDATA") + "\\MLauncher\\Versions\\" + name).exists();
    }
}
