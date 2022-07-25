package com.example.mlauncher.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.function.IntConsumer;

public class FileDownload {

    private final String filename;
    private final String url;

    private EventHandler<ActionEvent> onUpdate;
    private IntConsumer OnRead;

    public FileDownload(String filename, String url) {
        this.filename = filename;
        this.url = url;
    }

    public void start() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filename);
             ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(url).openStream())) {
            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public IntConsumer getOnRead() {
        return OnRead;
    }

    public void setOnRead(IntConsumer onRead) {
        OnRead = onRead;
        onUpdate.handle(new ActionEvent());
    }

    public void setOnUpdate(EventHandler<ActionEvent> onUpdate) {
        this.onUpdate = onUpdate;
    }
}
