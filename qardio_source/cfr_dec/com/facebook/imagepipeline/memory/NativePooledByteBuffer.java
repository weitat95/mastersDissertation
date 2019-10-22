/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.GuardedBy
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.memory.NativeMemoryChunk;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class NativePooledByteBuffer
implements PooledByteBuffer {
    @GuardedBy(value="this")
    CloseableReference<NativeMemoryChunk> mBufRef;
    private final int mSize;

    /*
     * Enabled aggressive block sorting
     */
    public NativePooledByteBuffer(CloseableReference<NativeMemoryChunk> closeableReference, int n) {
        Preconditions.checkNotNull(closeableReference);
        boolean bl = n >= 0 && n <= closeableReference.get().getSize();
        Preconditions.checkArgument(bl);
        this.mBufRef = closeableReference.clone();
        this.mSize = n;
    }

    @Override
    public void close() {
        synchronized (this) {
            CloseableReference.closeSafely(this.mBufRef);
            this.mBufRef = null;
            return;
        }
    }

    void ensureValid() {
        synchronized (this) {
            if (this.isClosed()) {
                throw new PooledByteBuffer.ClosedException();
            }
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean isClosed() {
        synchronized (this) {
            boolean bl = CloseableReference.isValid(this.mBufRef);
            if (bl) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public byte read(int n) {
        boolean bl = true;
        synchronized (this) {
            this.ensureValid();
            boolean bl2 = n >= 0;
            Preconditions.checkArgument(bl2);
            bl2 = n < this.mSize ? bl : false;
            Preconditions.checkArgument(bl2);
            return this.mBufRef.get().read(n);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void read(int n, byte[] arrby, int n2, int n3) {
        synchronized (this) {
            void var3_3;
            void var4_4;
            this.ensureValid();
            boolean bl = n + var4_4 <= this.mSize;
            Preconditions.checkArgument(bl);
            this.mBufRef.get().read(n, arrby, (int)var3_3, (int)var4_4);
            return;
        }
    }

    @Override
    public int size() {
        synchronized (this) {
            this.ensureValid();
            int n = this.mSize;
            return n;
        }
    }
}

