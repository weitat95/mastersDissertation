/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.image;

import com.facebook.common.logging.FLog;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import java.io.Closeable;

public abstract class CloseableImage
implements ImageInfo,
Closeable {
    @Override
    public abstract void close();

    protected void finalize() throws Throwable {
        if (this.isClosed()) {
            return;
        }
        FLog.w("CloseableImage", "finalize: %s %x still open.", this.getClass().getSimpleName(), System.identityHashCode(this));
        try {
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public QualityInfo getQualityInfo() {
        return ImmutableQualityInfo.FULL_QUALITY;
    }

    public abstract int getSizeInBytes();

    public abstract boolean isClosed();

    public boolean isStateful() {
        return false;
    }
}

