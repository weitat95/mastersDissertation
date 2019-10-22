/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.memory;

import java.io.Closeable;

public interface PooledByteBuffer
extends Closeable {
    public boolean isClosed();

    public byte read(int var1);

    public void read(int var1, byte[] var2, int var3, int var4);

    public int size();

    public static class ClosedException
    extends RuntimeException {
        public ClosedException() {
            super("Invalid bytebuf. Already closed");
        }
    }

}

