/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.memory;

import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferOutputStream;
import java.io.IOException;
import java.io.InputStream;

public interface PooledByteBufferFactory {
    public PooledByteBuffer newByteBuffer(InputStream var1) throws IOException;

    public PooledByteBuffer newByteBuffer(InputStream var1, int var2) throws IOException;

    public PooledByteBuffer newByteBuffer(byte[] var1);

    public PooledByteBufferOutputStream newOutputStream();

    public PooledByteBufferOutputStream newOutputStream(int var1);
}

