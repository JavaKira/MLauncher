package com.example.mlauncher;

import com.example.mlauncher.util.URLJsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.json.JsonArray;
import java.io.*;
import java.util.*;

public class VersionPool {
    private static final Logger log = LogManager.getLogger(VersionPool.class);

    private final List<Version> objects = new ArrayList<>();

    public VersionPool() {
        List<Version> githubVersions = null;
        List<Version> fileVersions = null;
        try {
            githubVersions = loadReleases();
        } catch (Exception ignore) {
            log.warn("Cannot releases from github");
        }

        try {
            fileVersions = load();
        } catch (Exception ignored) {
            log.warn("Cannot load versionsList file");
        }

        if (fileVersions == null) {
            if (githubVersions == null) {
                log.error("fucked up, hopeless situation: launcher doesnt have any version");
                throw new RuntimeException("fucked up, hopeless situation: launcher doesnt have any version");
            }

            log.info("Writing to versionList from github");
            write(githubVersions);
            objects.addAll(githubVersions);
            return;
        }

        if (!fileVersions.equals(githubVersions)) {
            log.info("Updating versionList from github");
            write(githubVersions);
        }

        log.info("Loading versions from versionList file");
        objects.addAll(fileVersions);
    }

    @SuppressWarnings("unchecked")
    private List<Version> load() {
        List<Version> versions;
        try (FileInputStream fileInputStream = new FileInputStream("versionsList");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            versions = (List<Version>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return versions;
    }

    private void write(List<Version> versions) {
        try (FileOutputStream fileOutputStream = new FileOutputStream("versionsList");
             ObjectOutputStream objectInputStream = new ObjectOutputStream(fileOutputStream)) {
            objectInputStream.writeObject(versions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Version> loadReleases() {
        List<Version> versionsList = new ArrayList<>(
                readRepositoriesReleases("https://api.github.com/repos/anuken/Mindustry/releases" + "?per_page=" + 25)
        );
        versionsList.addAll(
                readRepositoriesReleases("https://api.github.com/repos/anuken/MindustryBuilds/releases" + "?per_page=" + 25)
        );
        versionsList.sort(Comparator.comparing(Version::getCreatedAt));
        Collections.reverse(versionsList);
        return versionsList;
    }

    private List<Version> readRepositoriesReleases(String url) {
        JsonArray arr = URLJsonReader.readJsonArray(url);
        return new VersionParser().parseJsonArrayOfReleases(arr);
    }

    public List<Version> getObjects() {
        return Collections.unmodifiableList(objects);
    }
}
