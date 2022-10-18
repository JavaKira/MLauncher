package com.example.mlauncher;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class VersionParser {
    public List<Version> parseJsonArrayOfReleases(JsonArray arr) {
        List<Version> versions = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            JsonObject obj = arr.getJsonObject(i);
            JsonObject assetObj = obj.getJsonArray("assets").getJsonObject(0);
            String name = obj.getString("name");
            try {
                versions.add(new Version(
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

        return versions;
    }
}
