/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Throwables;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import java.io.IOException;
import java.io.OutputStream;

public abstract class PooledByteBufferOutputStream
extends OutputStream {
    @Override
    public void close() {
        try {
            super.close();
            return;
        }
        catch (IOException iOException) {
            Throwables.propagate(iOException);
            return;
        }
    }

    public abstract int size();

    public abstract PooledByteBuffer toByteBuffer();
}

