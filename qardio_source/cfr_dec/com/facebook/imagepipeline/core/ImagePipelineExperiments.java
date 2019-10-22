/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.core;

import com.facebook.common.webp.WebpBitmapFactory;
import com.facebook.common.webp.WebpSupportStatus;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

public class ImagePipelineExperiments {
    private boolean mDecodeFileDescriptorEnabled;
    private final int mEnhancedWebpTranscodingType;
    private final boolean mExternalCreatedBitmapLogEnabled;
    private final int mForceSmallCacheThresholdBytes;
    private final int mThrottlingMaxSimultaneousRequests;
    private final WebpBitmapFactory.WebpErrorLogger mWebpErrorLogger;
    private final boolean mWebpSupportEnabled;

    /*
     * Enabled aggressive block sorting
     */
    private ImagePipelineExperiments(Builder builder, ImagePipelineConfig.Builder builder2) {
        boolean bl = true;
        this.mForceSmallCacheThresholdBytes = builder.mForceSmallCacheThresholdBytes;
        boolean bl2 = builder.mWebpSupportEnabled && WebpSupportStatus.sWebpLibraryPresent;
        this.mWebpSupportEnabled = bl2;
        bl2 = builder2.isDownsampleEnabled() && builder.mDecodeFileDescriptorEnabled ? bl : false;
        this.mDecodeFileDescriptorEnabled = bl2;
        this.mThrottlingMaxSimultaneousRequests = builder.mThrottlingMaxSimultaneousRequests;
        this.mExternalCreatedBitmapLogEnabled = builder.mExternalCreatedBitmapLogEnabled;
        this.mWebpErrorLogger = builder.mWebpErrorLogger;
        this.mEnhancedWebpTranscodingType = builder.mEnhancedWebpTranscodingType;
    }

    public int getEnhancedWebpTranscodingType() {
        return this.mEnhancedWebpTranscodingType;
    }

    public int getForceSmallCacheThresholdBytes() {
        return this.mForceSmallCacheThresholdBytes;
    }

    public int getThrottlingMaxSimultaneousRequests() {
        return this.mThrottlingMaxSimultaneousRequests;
    }

    public WebpBitmapFactory.WebpErrorLogger getWebpErrorLogger() {
        return this.mWebpErrorLogger;
    }

    public boolean isDecodeFileDescriptorEnabled() {
        return this.mDecodeFileDescriptorEnabled;
    }

    public boolean isExternalCreatedBitmapLogEnabled() {
        return this.mExternalCreatedBitmapLogEnabled;
    }

    public boolean isWebpSupportEnabled() {
        return this.mWebpSupportEnabled;
    }

    public static class Builder {
        private final ImagePipelineConfig.Builder mConfigBuilder;
        private boolean mDecodeFileDescriptorEnabled = false;
        private int mEnhancedWebpTranscodingType;
        private boolean mExternalCreatedBitmapLogEnabled = false;
        private int mForceSmallCacheThresholdBytes = 0;
        private int mThrottlingMaxSimultaneousRequests = 5;
        private WebpBitmapFactory.WebpErrorLogger mWebpErrorLogger;
        private boolean mWebpSupportEnabled = false;

        public Builder(ImagePipelineConfig.Builder builder) {
            this.mConfigBuilder = builder;
        }

        public ImagePipelineExperiments build() {
            return new ImagePipelineExperiments(this, this.mConfigBuilder);
        }
    }

}

