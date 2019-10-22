/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.NotThreadSafe
 */
package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.memory.NativeMemoryChunk;
import com.facebook.imagepipeline.memory.NativeMemoryChunkPool;
import com.facebook.imagepipeline.memory.NativePooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferOutputStream;
import java.io.IOException;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class NativePooledByteBufferOutputStream
extends PooledByteBufferOutputStream {
    private CloseableReference<NativeMemoryChunk> mBufRef;
    private int mCount;
    private final NativeMemoryChunkPool mPool;

    public NativePooledByteBufferOutputStream(NativeMemoryChunkPool nativeMemoryChunkPool) {
        this(nativeMemoryChunkPool, nativeMemoryChunkPool.getMinBufferSize());
    }

    /*
     * Enabled aggressive block sorting
     */
    public NativePooledByteBufferOutputStream(NativeMemoryChunkPool nativeMemoryChunkPool, int n) {
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        this.mPool = Preconditions.checkNotNull(nativeMemoryChunkPool);
        this.mCount = 0;
        this.mBufRef = CloseableReference.of(this.mPool.get(n), this.mPool);
    }

    private void ensureValid() {
        if (!CloseableReference.isValid(this.mBufRef)) {
            throw new InvalidStreamException();
        }
    }

    @Override
    public void close() {
        CloseableReference.closeSafely(this.mBufRef);
        this.mBufRef = null;
        this.mCount = -1;
        super.close();
    }

    void realloc(int n) {
        this.ensureValid();
        if (n <= this.mBufRef.get().getSize()) {
            return;
        }
        NativeMemoryChunk nativeMemoryChunk = (NativeMemoryChunk)this.mPool.get(n);
        this.mBufRef.get().copy(0, nativeMemoryChunk, 0, this.mCount);
        this.mBufRef.close();
        this.mBufRef = CloseableReference.of(nativeMemoryChunk, this.mPool);
    }

    @Override
    public int size() {
        return this.mCount;
    }

    @Override
    public NativePooledByteBuffer toByteBuffer() {
        this.ensureValid();
        return new NativePooledByteBuffer(this.mBufRef, this.mCount);
    }

    @Override
    public void write(int n) throws IOException {
        this.write(new byte[]{(byte)n});
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        if (n < 0 || n2 < 0 || n + n2 > arrby.length) {
            throw new ArrayIndexOutOfBoundsException("length=" + arrby.length + "; regionStart=" + n + "; regionLength=" + n2);
        }
        this.ensureValid();
        this.realloc(this.mCount + n2);
        this.mBufRef.get().write(this.mCount, arrby, n, n2);
        this.mCount += n2;
    }

    public static class InvalidStreamException
    extends RuntimeException {
        public InvalidStreamException() {
            super("OutputStream no longer valid");
        }
    }

}

