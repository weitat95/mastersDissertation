/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Throwables;
import com.facebook.imagepipeline.memory.NativeMemoryChunkPool;
import com.facebook.imagepipeline.memory.NativePooledByteBuffer;
import com.facebook.imagepipeline.memory.NativePooledByteBufferOutputStream;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.memory.PooledByteBufferOutputStream;
import com.facebook.imagepipeline.memory.PooledByteStreams;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class NativePooledByteBufferFactory
implements PooledByteBufferFactory {
    private final NativeMemoryChunkPool mPool;
    private final PooledByteStreams mPooledByteStreams;

    public NativePooledByteBufferFactory(NativeMemoryChunkPool nativeMemoryChunkPool, PooledByteStreams pooledByteStreams) {
        this.mPool = nativeMemoryChunkPool;
        this.mPooledByteStreams = pooledByteStreams;
    }

    NativePooledByteBuffer newByteBuf(InputStream inputStream, NativePooledByteBufferOutputStream nativePooledByteBufferOutputStream) throws IOException {
        this.mPooledByteStreams.copy(inputStream, nativePooledByteBufferOutputStream);
        return nativePooledByteBufferOutputStream.toByteBuffer();
    }

    @Override
    public NativePooledByteBuffer newByteBuffer(InputStream closeable) throws IOException {
        NativePooledByteBufferOutputStream nativePooledByteBufferOutputStream = new NativePooledByteBufferOutputStream(this.mPool);
        try {
            closeable = this.newByteBuf((InputStream)closeable, nativePooledByteBufferOutputStream);
            return closeable;
        }
        finally {
            nativePooledByteBufferOutputStream.close();
        }
    }

    @Override
    public NativePooledByteBuffer newByteBuffer(InputStream closeable, int n) throws IOException {
        NativePooledByteBufferOutputStream nativePooledByteBufferOutputStream = new NativePooledByteBufferOutputStream(this.mPool, n);
        try {
            closeable = this.newByteBuf((InputStream)closeable, nativePooledByteBufferOutputStream);
            return closeable;
        }
        finally {
            nativePooledByteBufferOutputStream.close();
        }
    }

    @Override
    public NativePooledByteBuffer newByteBuffer(byte[] object) {
        NativePooledByteBufferOutputStream nativePooledByteBufferOutputStream = new NativePooledByteBufferOutputStream(this.mPool, ((byte[])object).length);
        try {
            nativePooledByteBufferOutputStream.write((byte[])object, 0, ((byte[])object).length);
            object = nativePooledByteBufferOutputStream.toByteBuffer();
            return object;
        }
        catch (IOException iOException) {
            throw Throwables.propagate(iOException);
        }
        finally {
            nativePooledByteBufferOutputStream.close();
        }
    }

    @Override
    public NativePooledByteBufferOutputStream newOutputStream() {
        return new NativePooledByteBufferOutputStream(this.mPool);
    }

    @Override
    public NativePooledByteBufferOutputStream newOutputStream(int n) {
        return new NativePooledByteBufferOutputStream(this.mPool, n);
    }
}

