package com.example.mlauncher;

import com.example.mlauncher.util.URLJsonReader;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MindustryVersionPool {
    private final List<MindustryVersion> objects = new ArrayList<>();

    public void initialize(int bEBuilds, int versions) {
        objects.clear();
        readRepositoriesReleases("https://api.github.com/repos/anuken/Mindustry/releases" + "?per_page=" + versions);
        if (bEBuilds > 0) {
            readRepositoriesReleases("https://api.github.com/repos/anuken/MindustryBuilds/releases" + "?per_page=" + bEBuilds);
            objects.sort(Comparator.comparing(MindustryVersion::getCreatedAt));
            Collections.reverse(objects);
        }
    }

    private void readRepositoriesReleases(String url) {
        JsonArray arr = URLJsonReader.readJsonArray(url);
        objects.addAll(new MindustryVersionParser().parseJsonArrayOfReleases(arr));
    }

    public List<MindustryVersion> getObjects() {
        return Collections.unmodifiableList(objects);
    }
}
