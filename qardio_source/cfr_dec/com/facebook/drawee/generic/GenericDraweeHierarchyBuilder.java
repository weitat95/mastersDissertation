/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.graphics.ColorFilter
 *  android.graphics.Matrix
 *  android.graphics.PointF
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.StateListDrawable
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.generic;

import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class GenericDraweeHierarchyBuilder {
    public static final ScalingUtils.ScaleType DEFAULT_ACTUAL_IMAGE_SCALE_TYPE;
    public static final ScalingUtils.ScaleType DEFAULT_SCALE_TYPE;
    private ColorFilter mActualImageColorFilter;
    private PointF mActualImageFocusPoint;
    private Matrix mActualImageMatrix;
    private ScalingUtils.ScaleType mActualImageScaleType;
    private Drawable mBackground;
    private float mDesiredAspectRatio;
    private int mFadeDuration;
    private Drawable mFailureImage;
    private ScalingUtils.ScaleType mFailureImageScaleType;
    private List<Drawable> mOverlays;
    private Drawable mPlaceholderImage;
    @Nullable
    private ScalingUtils.ScaleType mPlaceholderImageScaleType;
    private Drawable mPressedStateOverlay;
    private Drawable mProgressBarImage;
    private ScalingUtils.ScaleType mProgressBarImageScaleType;
    private Resources mResources;
    private Drawable mRetryImage;
    private ScalingUtils.ScaleType mRetryImageScaleType;
    private RoundingParams mRoundingParams;

    static {
        DEFAULT_SCALE_TYPE = ScalingUtils.ScaleType.CENTER_INSIDE;
        DEFAULT_ACTUAL_IMAGE_SCALE_TYPE = ScalingUtils.ScaleType.CENTER_CROP;
    }

    public GenericDraweeHierarchyBuilder(Resources resources) {
        this.mResources = resources;
        this.init();
    }

    private void init() {
        this.mFadeDuration = 300;
        this.mDesiredAspectRatio = 0.0f;
        this.mPlaceholderImage = null;
        this.mPlaceholderImageScaleType = DEFAULT_SCALE_TYPE;
        this.mRetryImage = null;
        this.mRetryImageScaleType = DEFAULT_SCALE_TYPE;
        this.mFailureImage = null;
        this.mFailureImageScaleType = DEFAULT_SCALE_TYPE;
        this.mProgressBarImage = null;
        this.mProgressBarImageScaleType = DEFAULT_SCALE_TYPE;
        this.mActualImageScaleType = DEFAULT_ACTUAL_IMAGE_SCALE_TYPE;
        this.mActualImageMatrix = null;
        this.mActualImageFocusPoint = null;
        this.mActualImageColorFilter = null;
        this.mBackground = null;
        this.mOverlays = null;
        this.mPressedStateOverlay = null;
        this.mRoundingParams = null;
    }

    private void validate() {
        if (this.mOverlays != null) {
            Iterator<Drawable> iterator = this.mOverlays.iterator();
            while (iterator.hasNext()) {
                Preconditions.checkNotNull(iterator.next());
            }
        }
    }

    public GenericDraweeHierarchy build() {
        this.validate();
        return new GenericDraweeHierarchy(this);
    }

    @Nullable
    public ColorFilter getActualImageColorFilter() {
        return this.mActualImageColorFilter;
    }

    @Nullable
    public PointF getActualImageFocusPoint() {
        return this.mActualImageFocusPoint;
    }

    @Nullable
    public Matrix getActualImageMatrix() {
        return this.mActualImageMatrix;
    }

    @Nullable
    public ScalingUtils.ScaleType getActualImageScaleType() {
        return this.mActualImageScaleType;
    }

    @Nullable
    public Drawable getBackground() {
        return this.mBackground;
    }

    public float getDesiredAspectRatio() {
        return this.mDesiredAspectRatio;
    }

    public int getFadeDuration() {
        return this.mFadeDuration;
    }

    @Nullable
    public Drawable getFailureImage() {
        return this.mFailureImage;
    }

    @Nullable
    public ScalingUtils.ScaleType getFailureImageScaleType() {
        return this.mFailureImageScaleType;
    }

    @Nullable
    public List<Drawable> getOverlays() {
        return this.mOverlays;
    }

    @Nullable
    public Drawable getPlaceholderImage() {
        return this.mPlaceholderImage;
    }

    @Nullable
    public ScalingUtils.ScaleType getPlaceholderImageScaleType() {
        return this.mPlaceholderImageScaleType;
    }

    @Nullable
    public Drawable getPressedStateOverlay() {
        return this.mPressedStateOverlay;
    }

    @Nullable
    public Drawable getProgressBarImage() {
        return this.mProgressBarImage;
    }

    @Nullable
    public ScalingUtils.ScaleType getProgressBarImageScaleType() {
        return this.mProgressBarImageScaleType;
    }

    public Resources getResources() {
        return this.mResources;
    }

    @Nullable
    public Drawable getRetryImage() {
        return this.mRetryImage;
    }

    @Nullable
    public ScalingUtils.ScaleType getRetryImageScaleType() {
        return this.mRetryImageScaleType;
    }

    @Nullable
    public RoundingParams getRoundingParams() {
        return this.mRoundingParams;
    }

    public GenericDraweeHierarchyBuilder setActualImageScaleType(@Nullable ScalingUtils.ScaleType scaleType) {
        this.mActualImageScaleType = scaleType;
        this.mActualImageMatrix = null;
        return this;
    }

    public GenericDraweeHierarchyBuilder setBackground(@Nullable Drawable drawable2) {
        this.mBackground = drawable2;
        return this;
    }

    public GenericDraweeHierarchyBuilder setDesiredAspectRatio(float f) {
        this.mDesiredAspectRatio = f;
        return this;
    }

    public GenericDraweeHierarchyBuilder setFadeDuration(int n) {
        this.mFadeDuration = n;
        return this;
    }

    public GenericDraweeHierarchyBuilder setFailureImage(@Nullable Drawable drawable2) {
        this.mFailureImage = drawable2;
        return this;
    }

    public GenericDraweeHierarchyBuilder setFailureImageScaleType(@Nullable ScalingUtils.ScaleType scaleType) {
        this.mFailureImageScaleType = scaleType;
        return this;
    }

    public GenericDraweeHierarchyBuilder setOverlay(@Nullable Drawable drawable2) {
        if (drawable2 == null) {
            this.mOverlays = null;
            return this;
        }
        this.mOverlays = Arrays.asList(new Drawable[]{drawable2});
        return this;
    }

    public GenericDraweeHierarchyBuilder setPlaceholderImage(@Nullable Drawable drawable2) {
        this.mPlaceholderImage = drawable2;
        return this;
    }

    public GenericDraweeHierarchyBuilder setPlaceholderImageScaleType(@Nullable ScalingUtils.ScaleType scaleType) {
        this.mPlaceholderImageScaleType = scaleType;
        return this;
    }

    public GenericDraweeHierarchyBuilder setPressedStateOverlay(@Nullable Drawable drawable2) {
        if (drawable2 == null) {
            this.mPressedStateOverlay = null;
            return this;
        }
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919}, drawable2);
        this.mPressedStateOverlay = stateListDrawable;
        return this;
    }

    public GenericDraweeHierarchyBuilder setProgressBarImage(@Nullable Drawable drawable2) {
        this.mProgressBarImage = drawable2;
        return this;
    }

    public GenericDraweeHierarchyBuilder setProgressBarImageScaleType(@Nullable ScalingUtils.ScaleType scaleType) {
        this.mProgressBarImageScaleType = scaleType;
        return this;
    }

    public GenericDraweeHierarchyBuilder setRetryImage(@Nullable Drawable drawable2) {
        this.mRetryImage = drawable2;
        return this;
    }

    public GenericDraweeHierarchyBuilder setRetryImageScaleType(@Nullable ScalingUtils.ScaleType scaleType) {
        this.mRetryImageScaleType = scaleType;
        return this;
    }

    public GenericDraweeHierarchyBuilder setRoundingParams(@Nullable RoundingParams roundingParams) {
        this.mRoundingParams = roundingParams;
        return this;
    }
}

