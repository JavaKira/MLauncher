package com.example.mlauncher;

import com.example.mlauncher.util.FileDownload;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

public class MindustryVersion implements Serializable {
    private final String name;
    private final int size;
    private final String downloadUrl;
    private final Date createdAt;
    private final File folder;
    private final String body;
    private final boolean isBE;

    public MindustryVersion(String name, int size, String downloadUrl, Date createdAt, String body, boolean isBE) {
        this.name = name;
        this.size = size;
        this.downloadUrl = downloadUrl;
        this.createdAt = createdAt;
        this.body = body;
        this.isBE = isBE;
        folder = new File(System.getenv("APPDATA") + "\\MLauncher\\Versions\\" + name);
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isDownloaded() {
        return folder.exists();
    }

    public FileDownload download() {
        //noinspection ResultOfMethodCallIgnored
        folder.mkdir();
        FileDownload fileDownload = new FileDownload(folder.getAbsolutePath() + "\\Mindustry.jar", downloadUrl);
        new Thread(fileDownload::start).start();
        return fileDownload;
    }

    public void launch() {
        try {
            Runtime.getRuntime().exec(new String[]{"java", "-jar", '"' + folder.getAbsolutePath() + "\\Mindustry.jar" + '"'});
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

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public boolean isBE() {
        return isBE;
    }
}
