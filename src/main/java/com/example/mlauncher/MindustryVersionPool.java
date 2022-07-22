package com.example.mlauncher;

import com.example.mlauncher.util.URLJsonReader;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MindustryVersionPool {
    private final List<MindustryVersion> objects = new ArrayList<>();

    public void initialize() {
        JsonArray arr = URLJsonReader.readJsonArray("https://api.github.com/repos/anuken/Mindustry/releases");
        for (int i = 0; i < arr.size(); i++) {
            JsonObject obj = arr.getJsonObject(i);
            objects.add(new MindustryVersion(obj.getString("name"), obj.getJsonArray("assets").getJsonObject(0).getString("browser_download_url")));
        }
    }

    public List<MindustryVersion> getObjects() {
        return Collections.unmodifiableList(objects);
    }
}
