/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  javax.annotation.Nullable
 */
package com.facebook.imagepipeline.request;

import android.net.Uri;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;
import javax.annotation.Nullable;

public class ImageRequestBuilder {
    private ImageRequest.CacheChoice mCacheChoice;
    private boolean mDiskCacheEnabled = true;
    private ImageDecodeOptions mImageDecodeOptions;
    private boolean mLocalThumbnailPreviewsEnabled = false;
    private ImageRequest.RequestLevel mLowestPermittedRequestLevel = ImageRequest.RequestLevel.FULL_FETCH;
    @Nullable
    private Postprocessor mPostprocessor = null;
    private boolean mProgressiveRenderingEnabled;
    @Nullable
    private RequestListener mRequestListener;
    private Priority mRequestPriority;
    @Nullable
    private ResizeOptions mResizeOptions = null;
    @Nullable
    private RotationOptions mRotationOptions = null;
    private Uri mSourceUri = null;

    private ImageRequestBuilder() {
        this.mImageDecodeOptions = ImageDecodeOptions.defaults();
        this.mCacheChoice = ImageRequest.CacheChoice.DEFAULT;
        this.mProgressiveRenderingEnabled = ImagePipelineConfig.getDefaultImageRequestConfig().isProgressiveRenderingEnabled();
        this.mRequestPriority = Priority.HIGH;
    }

    public static ImageRequestBuilder newBuilderWithSource(Uri uri) {
        return new ImageRequestBuilder().setSource(uri);
    }

    public ImageRequest build() {
        this.validate();
        return new ImageRequest(this);
    }

    public ImageRequest.CacheChoice getCacheChoice() {
        return this.mCacheChoice;
    }

    public ImageDecodeOptions getImageDecodeOptions() {
        return this.mImageDecodeOptions;
    }

    public ImageRequest.RequestLevel getLowestPermittedRequestLevel() {
        return this.mLowestPermittedRequestLevel;
    }

    @Nullable
    public Postprocessor getPostprocessor() {
        return this.mPostprocessor;
    }

    @Nullable
    public RequestListener getRequestListener() {
        return this.mRequestListener;
    }

    public Priority getRequestPriority() {
        return this.mRequestPriority;
    }

    @Nullable
    public ResizeOptions getResizeOptions() {
        return this.mResizeOptions;
    }

    @Nullable
    public RotationOptions getRotationOptions() {
        return this.mRotationOptions;
    }

    public Uri getSourceUri() {
        return this.mSourceUri;
    }

    public boolean isDiskCacheEnabled() {
        return this.mDiskCacheEnabled && UriUtil.isNetworkUri(this.mSourceUri);
    }

    public boolean isLocalThumbnailPreviewsEnabled() {
        return this.mLocalThumbnailPreviewsEnabled;
    }

    public boolean isProgressiveRenderingEnabled() {
        return this.mProgressiveRenderingEnabled;
    }

    public ImageRequestBuilder setProgressiveRenderingEnabled(boolean bl) {
        this.mProgressiveRenderingEnabled = bl;
        return this;
    }

    public ImageRequestBuilder setRotationOptions(@Nullable RotationOptions rotationOptions) {
        this.mRotationOptions = rotationOptions;
        return this;
    }

    public ImageRequestBuilder setSource(Uri uri) {
        Preconditions.checkNotNull(uri);
        this.mSourceUri = uri;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void validate() {
        if (this.mSourceUri == null) {
            throw new BuilderException("Source must be set!");
        }
        if (UriUtil.isLocalResourceUri(this.mSourceUri)) {
            if (!this.mSourceUri.isAbsolute()) {
                throw new BuilderException("Resource URI path must be absolute.");
            }
            if (this.mSourceUri.getPath().isEmpty()) {
                throw new BuilderException("Resource URI must not be empty");
            }
            try {
                Integer.parseInt(this.mSourceUri.getPath().substring(1));
            }
            catch (NumberFormatException numberFormatException) {
                throw new BuilderException("Resource URI path must be a resource id.");
            }
        }
        if (UriUtil.isLocalAssetUri(this.mSourceUri) && !this.mSourceUri.isAbsolute()) {
            throw new BuilderException("Asset URI path must be absolute.");
        }
    }

    public static class BuilderException
    extends RuntimeException {
        public BuilderException(String string2) {
            super("Invalid request builder: " + string2);
        }
    }

}

