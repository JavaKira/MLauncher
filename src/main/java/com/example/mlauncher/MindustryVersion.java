package com.example.mlauncher;

import com.example.mlauncher.util.FileDownloader;

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

    public void download() {
        File dir = new File(System.getenv("APPDATA") + "\\MLauncher\\Versions\\" + name);
        //noinspection ResultOfMethodCallIgnored
        dir.mkdir();
        FileDownloader.load(dir.getAbsolutePath() + "\\Mindustry.jar", downloadUrl);
    }

    public void launch() {
        System.out.println("launch " + name);
    }
}
