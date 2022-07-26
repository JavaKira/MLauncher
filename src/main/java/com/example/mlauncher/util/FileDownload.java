package com.example.mlauncher.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.function.IntConsumer;

public class FileDownload {

    private final String filename;

    private EventHandler<ActionEvent> onUpdate;
    private IntConsumer onRead;

    private EventHandler<ActionEvent> onEnd;

    private final ReadableConsumerByteChannel readableConsumerByteChannel;

    public FileDownload(String filename, String url) {
        this.filename = filename;
        try {
            readableConsumerByteChannel = new ReadableConsumerByteChannel(Channels.newChannel(new URL(url).openStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        onUpdate = event -> readableConsumerByteChannel.setOnRead(getOnRead());
        onEnd = event -> {};
    }

    public void start() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            fileOutputStream.getChannel()
                    .transferFrom(readableConsumerByteChannel, 0, Long.MAX_VALUE);
            onEnd.handle(new ActionEvent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public IntConsumer getOnRead() {
        return onRead;
    }

    public void setOnRead(IntConsumer onRead) {
        this.onRead = onRead;
        onUpdate.handle(new ActionEvent());
    }

    public void setOnUpdate(EventHandler<ActionEvent> onUpdate) {
        this.onUpdate = onUpdate;
    }

    public void setOnEnd(EventHandler<ActionEvent> onEnd) {
        this.onEnd = onEnd;
    }
}
