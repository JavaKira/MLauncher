package com.example.mlauncher;

import java.io.File;

public class MindustryVersion {
    private final String name;
    private final String downloadUrl;

    public MindustryVersion(String name, String downloadUrl) {
        this.name = name;
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isDownloaded() {
        return new File(System.getenv("APPDATA") + "\\MLauncher\\Versions\\" + name).exists();
    }
}
