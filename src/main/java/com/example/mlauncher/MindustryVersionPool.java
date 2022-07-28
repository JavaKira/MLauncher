package com.example.mlauncher;

import com.example.mlauncher.util.URLJsonReader;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MindustryVersionPool {
    private final List<MindustryVersion> objects = new ArrayList<>();

    public void initialize() {
        JsonArray arr = URLJsonReader.readJsonArray("https://api.github.com/repos/anuken/Mindustry/releases");
        for (int i = 0; i < arr.size(); i++) {
            JsonObject obj = arr.getJsonObject(i);
            JsonObject assetObj = obj.getJsonArray("assets").getJsonObject(0);
            try {
                objects.add(new MindustryVersion(
                        obj.getString("name"),
                        assetObj.getInt("size"),
                        assetObj.getString("browser_download_url"),
                        new SimpleDateFormat("yyyy-MM-ddhh:mm:ss").parse(assetObj.getString("created_at").replace("T", "").replace("Z", ""))
                ));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<MindustryVersion> getObjects() {
        return Collections.unmodifiableList(objects);
    }
}
