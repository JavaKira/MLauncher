package com.example.mlauncher.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class FileDownloader {
    public static void load(String filename, String url) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filename);
             ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(url).openStream())) {
            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
