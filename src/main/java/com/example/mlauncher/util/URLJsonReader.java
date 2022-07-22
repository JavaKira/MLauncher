package com.example.mlauncher.util;

import java.io.*;
import java.net.URL;
import javax.json.*;
import java.nio.charset.StandardCharsets;

public class URLJsonReader {
    public static JsonArray readJsonArray(String url) {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return Json.createReader(rd).readArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
