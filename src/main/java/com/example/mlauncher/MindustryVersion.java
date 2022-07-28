package com.example.mlauncher;

import com.example.mlauncher.util.FileDownload;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MindustryVersion {
    private final String name;
    private final int size;
    private final String downloadUrl;
    private final Date createdAt;

    public MindustryVersion(String name, int size, String downloadUrl, Date createdAt) {
        this.name = name;
        this.size = size;
        this.downloadUrl = downloadUrl;
        this.createdAt = createdAt;
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

    public Date getCreatedAt() {
        return createdAt;
    }
}
