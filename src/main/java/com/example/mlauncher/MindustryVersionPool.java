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
        readRepositoriesReleases("https://api.github.com/repos/anuken/Mindustry/releases" + "?per_page=" + versions);
        if (bEBuilds > 0) {
            readRepositoriesReleases("https://api.github.com/repos/anuken/MindustryBuilds/releases" + "?per_page=" + bEBuilds);
            objects.sort(Comparator.comparing(MindustryVersion::getCreatedAt));
            Collections.reverse(objects);
        }
    }

    private void readRepositoriesReleases(String url) {
        JsonArray arr = URLJsonReader.readJsonArray(url);
        for (int i = 0; i < arr.size(); i++) {
            JsonObject obj = arr.getJsonObject(i);
            JsonObject assetObj = obj.getJsonArray("assets").getJsonObject(0);
            String name = obj.getString("name");
            try {
                objects.add(new MindustryVersion(
                        name.isEmpty() ? "Build " + obj.getString("tag_name") : name,
                        assetObj.getInt("size"),
                        assetObj.getString("browser_download_url"),
                        new SimpleDateFormat("yyyy-MM-ddhh:mm:ss").parse(assetObj.getString("created_at").replace("T", "").replace("Z", "")),
                        obj.getString("body"),
                        name.isEmpty()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<MindustryVersion> getObjects() {
        return Collections.unmodifiableList(objects);
    }
}
