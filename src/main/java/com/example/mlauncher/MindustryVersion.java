package com.example.mlauncher;

import com.example.mlauncher.util.FileDownload;
import java.io.File;
import java.io.IOException;

public class MindustryVersion {
    private final String name;
    private final int size;
    private final String downloadUrl;

    public MindustryVersion(String name, int size, String downloadUrl) {
        this.name = name;
        this.size = size;
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isDownloaded() {
        return new File(System.getenv("APPDATA") + "\\MLauncher\\Versions\\" + name).exists();
    }

    public FileDownload download() {
        File dir = new File(System.getenv("APPDATA") + "\\MLauncher\\Versions\\" + name);
        //noinspection ResultOfMethodCallIgnored
        dir.mkdir();
        FileDownload fileDownload = new FileDownload(dir.getAbsolutePath() + "\\Mindustry.jar", downloadUrl);
        new Thread(fileDownload::start).start();
        return fileDownload;
    }

    public void launch() {
        try {
            Runtime.getRuntime().exec(new String[]{"java", "-jar", '"' + System.getenv("APPDATA") + "\\MLauncher\\Versions\\" + name + "\\Mindustry.jar" + '"'});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getSize() {
        return size;
    }
}
