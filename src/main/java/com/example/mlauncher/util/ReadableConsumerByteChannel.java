package com.example.mlauncher.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.function.IntConsumer;

//TODO написать своё https://stackoverflow.com/questions/30405695/java-nio-filechannels-track-progress
public class ReadableConsumerByteChannel implements ReadableByteChannel {

    private final ReadableByteChannel rbc;
    private IntConsumer onRead;

    private int totalByteRead;

    public ReadableConsumerByteChannel(ReadableByteChannel rbc) {
        this.rbc = rbc;
    }

    @Override
    public int read(ByteBuffer dst) throws IOException {
        int nRead = rbc.read(dst);
        notifyBytesRead(nRead);
        return nRead;
    }

    protected void notifyBytesRead(int nRead){
        if(nRead<=0) {
            return;
        }
        totalByteRead += nRead;
        onRead.accept(totalByteRead);
    }
    @Override
    public boolean isOpen() {
        return rbc.isOpen();
    }

    @Override
    public void close() throws IOException {
        rbc.close();
    }

    public void setOnRead(IntConsumer onRead) {
        this.onRead = onRead;
    }
}