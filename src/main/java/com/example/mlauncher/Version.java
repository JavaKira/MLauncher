package com.example.mlauncher;

import com.example.mlauncher.util.FileDownload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

public class Version implements Serializable {
    private static final Logger log = LogManager.getLogger(Version.class);

    private final String name;
    private final int size;
    private final String downloadUrl;
    private final Date createdAt;
    private final File folder;
    private final String body;
    private final boolean isBE;

    public Version(String name, int size, String downloadUrl, Date createdAt, String body, boolean isBE) {
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
        createVersionDirectory();
        log.info("Starting download version: " + name);
        log.info("Folder " + folder.getAbsolutePath() + " status: " +
                (folder.exists() ? "exists" : folder.mkdir() ? "created" : "failed to create"));
        FileDownload fileDownload = new FileDownload(folder.getAbsolutePath() + "\\Mindustry.jar", downloadUrl);
        new Thread(fileDownload::start).start();
        return fileDownload;
    }

    public void createVersionDirectory() {
        File folder = new File(System.getenv("APPDATA") + "\\MLauncher\\Versions");
        log.info("Folder " + folder.getAbsolutePath() + " status: " +
                (folder.exists() ? "exists" : folder.mkdir() ? "created" : "failed to create"));
    }

    public void launch() {
        log.info("Starting launch version: " + name);
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
