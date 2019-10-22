/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3.cache;

import java.io.IOException;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Sink;

abstract class ResponseBodyCacheSink
extends ForwardingSink {
    private boolean failed;

    ResponseBodyCacheSink(BufferedSink bufferedSink) {
        super(bufferedSink);
    }

    @Override
    public void close() throws IOException {
        if (this.failed) {
            return;
        }
        try {
            super.close();
            return;
        }
        catch (Exception exception) {
            this.failed = true;
            this.onException(exception);
            return;
        }
    }

    protected void copyFrom(Buffer buffer, long l, long l2) {
        if (this.failed) {
            return;
        }
        try {
            BufferedSink bufferedSink = (BufferedSink)this.delegate();
            buffer.copyTo(bufferedSink.buffer(), l, l2);
            bufferedSink.emitCompleteSegments();
            return;
        }
        catch (Exception exception) {
            this.failed = true;
            this.onException(exception);
            return;
        }
    }

    @Override
    public void flush() throws IOException {
        if (this.failed) {
            return;
        }
        try {
            super.flush();
            return;
        }
        catch (Exception exception) {
            this.failed = true;
            this.onException(exception);
            return;
        }
    }

    abstract void onException(Exception var1);

    @Override
    public void write(Buffer buffer, long l) throws IOException {
        if (this.failed) {
            return;
        }
        try {
            super.write(buffer, l);
            return;
        }
        catch (Exception exception) {
            this.failed = true;
            this.onException(exception);
            return;
        }
    }
}

