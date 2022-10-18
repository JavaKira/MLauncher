package com.example.mlauncher;

import com.example.mlauncher.util.URLJsonReader;

import javax.json.JsonArray;
import java.io.*;
import java.util.*;

public class MindustryVersionPool {
    private final List<Version> objects = new ArrayList<>();

    public MindustryVersionPool() {
        List<Version> githubVersions = null;
        List<Version> fileVersions = null;
        try {
            githubVersions = loadReleases();
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }

        try {
            fileVersions = load();
        } catch (Exception ignored) {

        }

        if (fileVersions == null) {
            if (githubVersions == null) {
                throw new RuntimeException("fucked up, hopeless situation");
            }

            write(githubVersions);
            objects.addAll(githubVersions);
            return;
        }

        if (!fileVersions.equals(githubVersions)) {
            write(githubVersions);
        }

        objects.addAll(fileVersions);
        System.out.println("load from file");
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
        return new MindustryVersionParser().parseJsonArrayOfReleases(arr);
    }

    public List<Version> getObjects() {
        return Collections.unmodifiableList(objects);
    }
}
