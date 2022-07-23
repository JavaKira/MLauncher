package com.example.mlauncher;

import com.example.mlauncher.util.FileDownloader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

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
        new Thread(() -> {
            File dir = new File(System.getenv("APPDATA") + "\\MLauncher\\Versions\\" + name);
            //noinspection ResultOfMethodCallIgnored
            dir.mkdir();
            FileDownloader.load(dir.getAbsolutePath() + "\\Mindustry.jar", downloadUrl);
        }).start();
    }

    public void launch() {
        try {
            Runtime.getRuntime().exec(new String[]{"java", "-jar", '"' + System.getenv("APPDATA") + "\\MLauncher\\Versions\\" + name + "\\Mindustry.jar" + '"'});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
