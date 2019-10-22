/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.graphics.ColorFilter
 *  android.graphics.Matrix
 *  android.graphics.PointF
 *  android.graphics.drawable.Animatable
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.generic;

import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import com.facebook.drawee.drawable.FadeDrawable;
import com.facebook.drawee.drawable.ForwardingDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RootDrawable;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.generic.WrappingUtils;
import com.facebook.drawee.interfaces.SettableDraweeHierarchy;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class GenericDraweeHierarchy
implements SettableDraweeHierarchy {
    private final ForwardingDrawable mActualImageWrapper;
    private final Drawable mEmptyActualImageDrawable = new ColorDrawable(0);
    private final FadeDrawable mFadeDrawable;
    private final Resources mResources;
    @Nullable
    private RoundingParams mRoundingParams;
    private final RootDrawable mTopLevelDrawable;

    /*
     * Enabled aggressive block sorting
     */
    GenericDraweeHierarchy(GenericDraweeHierarchyBuilder genericDraweeHierarchyBuilder) {
        this.mResources = genericDraweeHierarchyBuilder.getResources();
        this.mRoundingParams = genericDraweeHierarchyBuilder.getRoundingParams();
        this.mActualImageWrapper = new ForwardingDrawable(this.mEmptyActualImageDrawable);
        int n = genericDraweeHierarchyBuilder.getOverlays() != null ? genericDraweeHierarchyBuilder.getOverlays().size() : 1;
        int n2 = genericDraweeHierarchyBuilder.getPressedStateOverlay() != null ? 1 : 0;
        Drawable[] arrdrawable = new Drawable[(n += n2) + 6];
        arrdrawable[0] = this.buildBranch(genericDraweeHierarchyBuilder.getBackground(), null);
        arrdrawable[1] = this.buildBranch(genericDraweeHierarchyBuilder.getPlaceholderImage(), genericDraweeHierarchyBuilder.getPlaceholderImageScaleType());
        arrdrawable[2] = this.buildActualImageBranch(this.mActualImageWrapper, genericDraweeHierarchyBuilder.getActualImageScaleType(), genericDraweeHierarchyBuilder.getActualImageFocusPoint(), genericDraweeHierarchyBuilder.getActualImageMatrix(), genericDraweeHierarchyBuilder.getActualImageColorFilter());
        arrdrawable[3] = this.buildBranch(genericDraweeHierarchyBuilder.getProgressBarImage(), genericDraweeHierarchyBuilder.getProgressBarImageScaleType());
        arrdrawable[4] = this.buildBranch(genericDraweeHierarchyBuilder.getRetryImage(), genericDraweeHierarchyBuilder.getRetryImageScaleType());
        arrdrawable[5] = this.buildBranch(genericDraweeHierarchyBuilder.getFailureImage(), genericDraweeHierarchyBuilder.getFailureImageScaleType());
        if (n > 0) {
            n = 0;
            if (genericDraweeHierarchyBuilder.getOverlays() != null) {
                Iterator<Drawable> iterator = genericDraweeHierarchyBuilder.getOverlays().iterator();
                do {
                    n2 = n++;
                    if (iterator.hasNext()) {
                        arrdrawable[n + 6] = this.buildBranch(iterator.next(), null);
                        continue;
                    }
                    break;
                } while (true);
            } else {
                n2 = 1;
            }
            if (genericDraweeHierarchyBuilder.getPressedStateOverlay() != null) {
                arrdrawable[n2 + 6] = this.buildBranch(genericDraweeHierarchyBuilder.getPressedStateOverlay(), null);
            }
        }
        this.mFadeDrawable = new FadeDrawable(arrdrawable);
        this.mFadeDrawable.setTransitionDuration(genericDraweeHierarchyBuilder.getFadeDuration());
        this.mTopLevelDrawable = new RootDrawable(WrappingUtils.maybeWrapWithRoundedOverlayColor(this.mFadeDrawable, this.mRoundingParams));
        this.mTopLevelDrawable.mutate();
        this.resetFade();
    }

    @Nullable
    private Drawable buildActualImageBranch(Drawable drawable2, @Nullable ScalingUtils.ScaleType scaleType, @Nullable PointF pointF, @Nullable Matrix matrix, @Nullable ColorFilter colorFilter) {
        drawable2.setColorFilter(colorFilter);
        return WrappingUtils.maybeWrapWithMatrix(WrappingUtils.maybeWrapWithScaleType(drawable2, scaleType, pointF), matrix);
    }

    @Nullable
    private Drawable buildBranch(@Nullable Drawable drawable2, @Nullable ScalingUtils.ScaleType scaleType) {
        return WrappingUtils.maybeWrapWithScaleType(WrappingUtils.maybeApplyLeafRounding(drawable2, this.mRoundingParams, this.mResources), scaleType);
    }

    private void fadeInLayer(int n) {
        if (n >= 0) {
            this.mFadeDrawable.fadeInLayer(n);
        }
    }

    private void fadeOutBranches() {
        this.fadeOutLayer(1);
        this.fadeOutLayer(2);
        this.fadeOutLayer(3);
        this.fadeOutLayer(4);
        this.fadeOutLayer(5);
    }

    private void fadeOutLayer(int n) {
        if (n >= 0) {
            this.mFadeDrawable.fadeOutLayer(n);
        }
    }

    private void resetActualImages() {
        this.mActualImageWrapper.setDrawable(this.mEmptyActualImageDrawable);
    }

    private void resetFade() {
        if (this.mFadeDrawable != null) {
            this.mFadeDrawable.beginBatchMode();
            this.mFadeDrawable.fadeInAllLayers();
            this.fadeOutBranches();
            this.fadeInLayer(1);
            this.mFadeDrawable.finishTransitionImmediately();
            this.mFadeDrawable.endBatchMode();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setProgress(float f) {
        Drawable drawable2 = this.mFadeDrawable.getDrawable(3);
        if (drawable2 == null) {
            return;
        }
        if (f >= 0.999f) {
            if (drawable2 instanceof Animatable) {
                ((Animatable)drawable2).stop();
            }
            this.fadeOutLayer(3);
        } else {
            if (drawable2 instanceof Animatable) {
                ((Animatable)drawable2).start();
            }
            this.fadeInLayer(3);
        }
        drawable2.setLevel(Math.round(10000.0f * f));
    }

    @Override
    public Drawable getTopLevelDrawable() {
        return this.mTopLevelDrawable;
    }

    @Override
    public void reset() {
        this.resetActualImages();
        this.resetFade();
    }

    @Override
    public void setControllerOverlay(@Nullable Drawable drawable2) {
        this.mTopLevelDrawable.setControllerOverlay(drawable2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setFailure(Throwable throwable) {
        this.mFadeDrawable.beginBatchMode();
        this.fadeOutBranches();
        if (this.mFadeDrawable.getDrawable(5) != null) {
            this.fadeInLayer(5);
        } else {
            this.fadeInLayer(1);
        }
        this.mFadeDrawable.endBatchMode();
    }

    @Override
    public void setImage(Drawable drawable2, float f, boolean bl) {
        drawable2 = WrappingUtils.maybeApplyLeafRounding(drawable2, this.mRoundingParams, this.mResources);
        drawable2.mutate();
        this.mActualImageWrapper.setDrawable(drawable2);
        this.mFadeDrawable.beginBatchMode();
        this.fadeOutBranches();
        this.fadeInLayer(2);
        this.setProgress(f);
        if (bl) {
            this.mFadeDrawable.finishTransitionImmediately();
        }
        this.mFadeDrawable.endBatchMode();
    }

    @Override
    public void setProgress(float f, boolean bl) {
        if (this.mFadeDrawable.getDrawable(3) == null) {
            return;
        }
        this.mFadeDrawable.beginBatchMode();
        this.setProgress(f);
        if (bl) {
            this.mFadeDrawable.finishTransitionImmediately();
        }
        this.mFadeDrawable.endBatchMode();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setRetry(Throwable throwable) {
        this.mFadeDrawable.beginBatchMode();
        this.fadeOutBranches();
        if (this.mFadeDrawable.getDrawable(4) != null) {
            this.fadeInLayer(4);
        } else {
            this.fadeInLayer(1);
        }
        this.mFadeDrawable.endBatchMode();
    }
}

