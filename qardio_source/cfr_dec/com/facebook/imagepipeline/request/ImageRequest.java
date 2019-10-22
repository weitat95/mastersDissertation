/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.Immutable
 */
package com.facebook.imagepipeline.request;

import android.net.Uri;
import com.facebook.common.internal.Objects;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import java.io.File;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public class ImageRequest {
    private final CacheChoice mCacheChoice;
    private final ImageDecodeOptions mImageDecodeOptions;
    private final boolean mIsDiskCacheEnabled;
    private final boolean mLocalThumbnailPreviewsEnabled;
    private final RequestLevel mLowestPermittedRequestLevel;
    private final Postprocessor mPostprocessor;
    private final boolean mProgressiveRenderingEnabled;
    @Nullable
    private final RequestListener mRequestListener;
    private final Priority mRequestPriority;
    @Nullable
    private final ResizeOptions mResizeOptions;
    private final RotationOptions mRotationOptions;
    private File mSourceFile;
    private final Uri mSourceUri;

    /*
     * Enabled aggressive block sorting
     */
    protected ImageRequest(ImageRequestBuilder imageRequestBuilder) {
        this.mCacheChoice = imageRequestBuilder.getCacheChoice();
        this.mSourceUri = imageRequestBuilder.getSourceUri();
        this.mProgressiveRenderingEnabled = imageRequestBuilder.isProgressiveRenderingEnabled();
        this.mLocalThumbnailPreviewsEnabled = imageRequestBuilder.isLocalThumbnailPreviewsEnabled();
        this.mImageDecodeOptions = imageRequestBuilder.getImageDecodeOptions();
        this.mResizeOptions = imageRequestBuilder.getResizeOptions();
        RotationOptions rotationOptions = imageRequestBuilder.getRotationOptions() == null ? RotationOptions.autoRotate() : imageRequestBuilder.getRotationOptions();
        this.mRotationOptions = rotationOptions;
        this.mRequestPriority = imageRequestBuilder.getRequestPriority();
        this.mLowestPermittedRequestLevel = imageRequestBuilder.getLowestPermittedRequestLevel();
        this.mIsDiskCacheEnabled = imageRequestBuilder.isDiskCacheEnabled();
        this.mPostprocessor = imageRequestBuilder.getPostprocessor();
        this.mRequestListener = imageRequestBuilder.getRequestListener();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof ImageRequest)) break block2;
                object = (ImageRequest)object;
                if (Objects.equal((Object)this.mSourceUri, (Object)((ImageRequest)object).mSourceUri) && Objects.equal((Object)this.mCacheChoice, (Object)((ImageRequest)object).mCacheChoice) && Objects.equal(this.mSourceFile, ((ImageRequest)object).mSourceFile)) break block3;
            }
            return false;
        }
        return true;
    }

    public CacheChoice getCacheChoice() {
        return this.mCacheChoice;
    }

    public ImageDecodeOptions getImageDecodeOptions() {
        return this.mImageDecodeOptions;
    }

    public boolean getLocalThumbnailPreviewsEnabled() {
        return this.mLocalThumbnailPreviewsEnabled;
    }

    public RequestLevel getLowestPermittedRequestLevel() {
        return this.mLowestPermittedRequestLevel;
    }

    @Nullable
    public Postprocessor getPostprocessor() {
        return this.mPostprocessor;
    }

    public int getPreferredHeight() {
        if (this.mResizeOptions != null) {
            return this.mResizeOptions.height;
        }
        return 2048;
    }

    public int getPreferredWidth() {
        if (this.mResizeOptions != null) {
            return this.mResizeOptions.width;
        }
        return 2048;
    }

    public Priority getPriority() {
        return this.mRequestPriority;
    }

    public boolean getProgressiveRenderingEnabled() {
        return this.mProgressiveRenderingEnabled;
    }

    @Nullable
    public RequestListener getRequestListener() {
        return this.mRequestListener;
    }

    @Nullable
    public ResizeOptions getResizeOptions() {
        return this.mResizeOptions;
    }

    public RotationOptions getRotationOptions() {
        return this.mRotationOptions;
    }

    public File getSourceFile() {
        synchronized (this) {
            if (this.mSourceFile == null) {
                this.mSourceFile = new File(this.mSourceUri.getPath());
            }
            File file = this.mSourceFile;
            return file;
        }
    }

    public Uri getSourceUri() {
        return this.mSourceUri;
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.mCacheChoice, this.mSourceUri, this.mSourceFile});
    }

    public boolean isDiskCacheEnabled() {
        return this.mIsDiskCacheEnabled;
    }

    public static enum CacheChoice {
        SMALL,
        DEFAULT;

    }

    public static enum RequestLevel {
        FULL_FETCH(1),
        DISK_CACHE(2),
        ENCODED_MEMORY_CACHE(3),
        BITMAP_MEMORY_CACHE(4);

        private int mValue;

        private RequestLevel(int n2) {
            this.mValue = n2;
        }

        public static RequestLevel getMax(RequestLevel requestLevel, RequestLevel requestLevel2) {
            if (requestLevel.getValue() > requestLevel2.getValue()) {
                return requestLevel;
            }
            return requestLevel2;
        }

        public int getValue() {
            return this.mValue;
        }
    }

}

