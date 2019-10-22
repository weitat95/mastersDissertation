/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.facebook.imagepipeline.memory;

import android.util.Log;
import com.facebook.common.internal.DoNotStrip;
import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.nativecode.ImagePipelineNativeLoader;
import java.io.Closeable;

@DoNotStrip
public class NativeMemoryChunk
implements Closeable {
    private boolean mClosed;
    private final long mNativePtr;
    private final int mSize;

    static {
        ImagePipelineNativeLoader.load();
    }

    public NativeMemoryChunk() {
        this.mSize = 0;
        this.mNativePtr = 0L;
        this.mClosed = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public NativeMemoryChunk(int n) {
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        this.mSize = n;
        this.mNativePtr = NativeMemoryChunk.nativeAllocate(this.mSize);
        this.mClosed = false;
    }

    private int adjustByteCount(int n, int n2) {
        return Math.min(Math.max(0, this.mSize - n), n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void checkBounds(int n, int n2, int n3, int n4) {
        boolean bl = true;
        boolean bl2 = n4 >= 0;
        Preconditions.checkArgument(bl2);
        bl2 = n >= 0;
        Preconditions.checkArgument(bl2);
        bl2 = n3 >= 0;
        Preconditions.checkArgument(bl2);
        bl2 = n + n4 <= this.mSize;
        Preconditions.checkArgument(bl2);
        bl2 = n3 + n4 <= n2 ? bl : false;
        Preconditions.checkArgument(bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void doCopy(int n, NativeMemoryChunk nativeMemoryChunk, int n2, int n3) {
        boolean bl = true;
        boolean bl2 = !this.isClosed();
        Preconditions.checkState(bl2);
        bl2 = !nativeMemoryChunk.isClosed() ? bl : false;
        Preconditions.checkState(bl2);
        this.checkBounds(n, nativeMemoryChunk.mSize, n2, n3);
        NativeMemoryChunk.nativeMemcpy(nativeMemoryChunk.mNativePtr + (long)n2, this.mNativePtr + (long)n, n3);
    }

    @DoNotStrip
    private static native long nativeAllocate(int var0);

    @DoNotStrip
    private static native void nativeCopyFromByteArray(long var0, byte[] var2, int var3, int var4);

    @DoNotStrip
    private static native void nativeCopyToByteArray(long var0, byte[] var2, int var3, int var4);

    @DoNotStrip
    private static native void nativeFree(long var0);

    @DoNotStrip
    private static native void nativeMemcpy(long var0, long var2, int var4);

    @DoNotStrip
    private static native byte nativeReadByte(long var0);

    @Override
    public void close() {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                NativeMemoryChunk.nativeFree(this.mNativePtr);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void copy(int n, NativeMemoryChunk nativeMemoryChunk, int n2, int n3) {
        Preconditions.checkNotNull(nativeMemoryChunk);
        if (nativeMemoryChunk.mNativePtr == this.mNativePtr) {
            Log.w((String)"NativeMemoryChunk", (String)("Copying from NativeMemoryChunk " + Integer.toHexString(System.identityHashCode(this)) + " to NativeMemoryChunk " + Integer.toHexString(System.identityHashCode(nativeMemoryChunk)) + " which share the same address " + Long.toHexString(this.mNativePtr)));
            Preconditions.checkArgument(false);
        }
        if (nativeMemoryChunk.mNativePtr < this.mNativePtr) {
            synchronized (nativeMemoryChunk) {
                synchronized (this) {
                    this.doCopy(n, nativeMemoryChunk, n2, n3);
                    return;
                }
            }
        }
        synchronized (this) {
            synchronized (nativeMemoryChunk) {
                this.doCopy(n, nativeMemoryChunk, n2, n3);
                return;
            }
        }
    }

    protected void finalize() throws Throwable {
        if (this.isClosed()) {
            return;
        }
        Log.w((String)"NativeMemoryChunk", (String)("finalize: Chunk " + Integer.toHexString(System.identityHashCode(this)) + " still active. Underlying address = " + Long.toHexString(this.mNativePtr)));
        try {
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public int getSize() {
        return this.mSize;
    }

    public boolean isClosed() {
        synchronized (this) {
            boolean bl = this.mClosed;
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public byte read(int n) {
        boolean bl = true;
        synchronized (this) {
            boolean bl2 = !this.isClosed();
            Preconditions.checkState(bl2);
            bl2 = n >= 0;
            Preconditions.checkArgument(bl2);
            bl2 = n < this.mSize ? bl : false;
            Preconditions.checkArgument(bl2);
            return NativeMemoryChunk.nativeReadByte(this.mNativePtr + (long)n);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int read(int n, byte[] arrby, int n2, int n3) {
        synchronized (this) {
            void var3_3;
            int n4;
            Preconditions.checkNotNull(arrby);
            boolean bl = !this.isClosed();
            Preconditions.checkState(bl);
            n4 = this.adjustByteCount(n, n4);
            this.checkBounds(n, arrby.length, (int)var3_3, n4);
            NativeMemoryChunk.nativeCopyToByteArray(this.mNativePtr + (long)n, arrby, (int)var3_3, n4);
            return n4;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int write(int n, byte[] arrby, int n2, int n3) {
        synchronized (this) {
            void var3_3;
            int n4;
            Preconditions.checkNotNull(arrby);
            boolean bl = !this.isClosed();
            Preconditions.checkState(bl);
            n4 = this.adjustByteCount(n, n4);
            this.checkBounds(n, arrby.length, (int)var3_3, n4);
            NativeMemoryChunk.nativeCopyFromByteArray(this.mNativePtr + (long)n, arrby, (int)var3_3, n4);
            return n4;
        }
    }
}

