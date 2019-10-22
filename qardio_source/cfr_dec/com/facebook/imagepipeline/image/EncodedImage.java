/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Pair
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.Immutable
 */
package com.facebook.imagepipeline.image;

import android.util.Pair;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imageformat.ImageFormatChecker;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferInputStream;
import com.facebook.imageutils.BitmapUtil;
import com.facebook.imageutils.JfifUtil;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public class EncodedImage
implements Closeable {
    private int mHeight = -1;
    private ImageFormat mImageFormat = ImageFormat.UNKNOWN;
    @Nullable
    private final Supplier<FileInputStream> mInputStreamSupplier;
    @Nullable
    private final CloseableReference<PooledByteBuffer> mPooledByteBufferRef;
    private int mRotationAngle = -1;
    private int mSampleSize = 1;
    private int mStreamSize = -1;
    private int mWidth = -1;

    public EncodedImage(Supplier<FileInputStream> supplier) {
        Preconditions.checkNotNull(supplier);
        this.mPooledByteBufferRef = null;
        this.mInputStreamSupplier = supplier;
    }

    public EncodedImage(Supplier<FileInputStream> supplier, int n) {
        this(supplier);
        this.mStreamSize = n;
    }

    public EncodedImage(CloseableReference<PooledByteBuffer> closeableReference) {
        Preconditions.checkArgument(CloseableReference.isValid(closeableReference));
        this.mPooledByteBufferRef = closeableReference.clone();
        this.mInputStreamSupplier = null;
    }

    public static EncodedImage cloneOrNull(EncodedImage encodedImage) {
        if (encodedImage != null) {
            return encodedImage.cloneOrNull();
        }
        return null;
    }

    public static void closeSafely(@Nullable EncodedImage encodedImage) {
        if (encodedImage != null) {
            encodedImage.close();
        }
    }

    public static boolean isMetaDataAvailable(EncodedImage encodedImage) {
        return encodedImage.mRotationAngle >= 0 && encodedImage.mWidth >= 0 && encodedImage.mHeight >= 0;
    }

    public static boolean isValid(@Nullable EncodedImage encodedImage) {
        return encodedImage != null && encodedImage.isValid();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public EncodedImage cloneOrNull() {
        EncodedImage encodedImage;
        if (this.mInputStreamSupplier != null) {
            encodedImage = new EncodedImage(this.mInputStreamSupplier, this.mStreamSize);
        } else {
            CloseableReference<PooledByteBuffer> closeableReference = CloseableReference.cloneOrNull(this.mPooledByteBufferRef);
            if (closeableReference == null) {
                return null;
            }
            try {
                encodedImage = new EncodedImage(closeableReference);
            }
            finally {
                CloseableReference.closeSafely(closeableReference);
            }
        }
        if (encodedImage == null) return encodedImage;
        encodedImage.copyMetaDataFrom(this);
        return encodedImage;
    }

    @Override
    public void close() {
        CloseableReference.closeSafely(this.mPooledByteBufferRef);
    }

    public void copyMetaDataFrom(EncodedImage encodedImage) {
        this.mImageFormat = encodedImage.getImageFormat();
        this.mWidth = encodedImage.getWidth();
        this.mHeight = encodedImage.getHeight();
        this.mRotationAngle = encodedImage.getRotationAngle();
        this.mSampleSize = encodedImage.getSampleSize();
        this.mStreamSize = encodedImage.getSize();
    }

    public CloseableReference<PooledByteBuffer> getByteBufferRef() {
        return CloseableReference.cloneOrNull(this.mPooledByteBufferRef);
    }

    public int getHeight() {
        return this.mHeight;
    }

    public ImageFormat getImageFormat() {
        return this.mImageFormat;
    }

    public InputStream getInputStream() {
        if (this.mInputStreamSupplier != null) {
            return this.mInputStreamSupplier.get();
        }
        CloseableReference<PooledByteBuffer> closeableReference = CloseableReference.cloneOrNull(this.mPooledByteBufferRef);
        if (closeableReference != null) {
            try {
                PooledByteBufferInputStream pooledByteBufferInputStream = new PooledByteBufferInputStream(closeableReference.get());
                return pooledByteBufferInputStream;
            }
            finally {
                CloseableReference.closeSafely(closeableReference);
            }
        }
        return null;
    }

    public int getRotationAngle() {
        return this.mRotationAngle;
    }

    public int getSampleSize() {
        return this.mSampleSize;
    }

    public int getSize() {
        if (this.mPooledByteBufferRef != null && this.mPooledByteBufferRef.get() != null) {
            return this.mPooledByteBufferRef.get().size();
        }
        return this.mStreamSize;
    }

    public int getWidth() {
        return this.mWidth;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isCompleteAt(int n) {
        block3: {
            block2: {
                if (this.mImageFormat != DefaultImageFormats.JPEG || this.mInputStreamSupplier != null) break block2;
                Preconditions.checkNotNull(this.mPooledByteBufferRef);
                PooledByteBuffer pooledByteBuffer = this.mPooledByteBufferRef.get();
                if (pooledByteBuffer.read(n - 2) != -1 || pooledByteBuffer.read(n - 1) != -39) break block3;
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isValid() {
        synchronized (this) {
            if (CloseableReference.isValid(this.mPooledByteBufferRef)) return true;
            Supplier<FileInputStream> supplier = this.mInputStreamSupplier;
            if (supplier == null) return false;
            return true;
        }
    }

    public void parseMetaData() {
        ImageFormat imageFormat;
        this.mImageFormat = imageFormat = ImageFormatChecker.getImageFormat_WrapIOException(this.getInputStream());
        Pair<Integer, Integer> pair = null;
        if (!DefaultImageFormats.isWebpFormat(imageFormat)) {
            Pair<Integer, Integer> pair2;
            pair = pair2 = BitmapUtil.decodeDimensions(this.getInputStream());
            if (pair2 != null) {
                this.mWidth = (Integer)pair2.first;
                this.mHeight = (Integer)pair2.second;
                pair = pair2;
            }
        }
        if (imageFormat == DefaultImageFormats.JPEG && this.mRotationAngle == -1) {
            if (pair != null) {
                this.mRotationAngle = JfifUtil.getAutoRotateAngleFromOrientation(JfifUtil.getOrientation(this.getInputStream()));
            }
            return;
        }
        this.mRotationAngle = 0;
    }

    public void setHeight(int n) {
        this.mHeight = n;
    }

    public void setImageFormat(ImageFormat imageFormat) {
        this.mImageFormat = imageFormat;
    }

    public void setRotationAngle(int n) {
        this.mRotationAngle = n;
    }

    public void setSampleSize(int n) {
        this.mSampleSize = n;
    }

    public void setWidth(int n) {
        this.mWidth = n;
    }
}

