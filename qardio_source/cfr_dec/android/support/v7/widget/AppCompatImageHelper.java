/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.RippleDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.widget.ImageView
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.TintInfo;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AppCompatImageHelper {
    private TintInfo mImageTint;
    private TintInfo mInternalImageTint;
    private TintInfo mTmpInfo;
    private final ImageView mView;

    public AppCompatImageHelper(ImageView imageView) {
        this.mView = imageView;
    }

    private boolean applyFrameworkTintUsingColorFilter(Drawable drawable2) {
        if (this.mTmpInfo == null) {
            this.mTmpInfo = new TintInfo();
        }
        TintInfo tintInfo = this.mTmpInfo;
        tintInfo.clear();
        ColorStateList colorStateList = ImageViewCompat.getImageTintList(this.mView);
        if (colorStateList != null) {
            tintInfo.mHasTintList = true;
            tintInfo.mTintList = colorStateList;
        }
        if ((colorStateList = ImageViewCompat.getImageTintMode(this.mView)) != null) {
            tintInfo.mHasTintMode = true;
            tintInfo.mTintMode = colorStateList;
        }
        if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
            AppCompatDrawableManager.tintDrawable(drawable2, tintInfo, this.mView.getDrawableState());
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean shouldApplyFrameworkTintUsingColorFilter() {
        int n = Build.VERSION.SDK_INT;
        if (n > 21) {
            if (this.mInternalImageTint != null) return true;
            return false;
        }
        if (n != 21) return false;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    void applySupportImageTint() {
        Drawable drawable2;
        block6: {
            block5: {
                drawable2 = this.mView.getDrawable();
                if (drawable2 != null) {
                    DrawableUtils.fixDrawable(drawable2);
                }
                if (drawable2 == null || this.shouldApplyFrameworkTintUsingColorFilter() && this.applyFrameworkTintUsingColorFilter(drawable2)) break block5;
                if (this.mImageTint != null) {
                    AppCompatDrawableManager.tintDrawable(drawable2, this.mImageTint, this.mView.getDrawableState());
                    return;
                }
                if (this.mInternalImageTint != null) break block6;
            }
            return;
        }
        AppCompatDrawableManager.tintDrawable(drawable2, this.mInternalImageTint, this.mView.getDrawableState());
    }

    ColorStateList getSupportImageTintList() {
        if (this.mImageTint != null) {
            return this.mImageTint.mTintList;
        }
        return null;
    }

    PorterDuff.Mode getSupportImageTintMode() {
        if (this.mImageTint != null) {
            return this.mImageTint.mTintMode;
        }
        return null;
    }

    boolean hasOverlappingRendering() {
        Drawable drawable2 = this.mView.getBackground();
        return Build.VERSION.SDK_INT < 21 || !(drawable2 instanceof RippleDrawable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void loadFromAttributes(AttributeSet attributeSet, int n) {
        TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), attributeSet, R.styleable.AppCompatImageView, n, 0);
        try {
            Drawable drawable2 = this.mView.getDrawable();
            attributeSet = drawable2;
            if (drawable2 == null) {
                n = tintTypedArray.getResourceId(R.styleable.AppCompatImageView_srcCompat, -1);
                attributeSet = drawable2;
                if (n != -1) {
                    drawable2 = AppCompatResources.getDrawable(this.mView.getContext(), n);
                    attributeSet = drawable2;
                    if (drawable2 != null) {
                        this.mView.setImageDrawable(drawable2);
                        attributeSet = drawable2;
                    }
                }
            }
            if (attributeSet != null) {
                DrawableUtils.fixDrawable((Drawable)attributeSet);
            }
            if (tintTypedArray.hasValue(R.styleable.AppCompatImageView_tint)) {
                ImageViewCompat.setImageTintList(this.mView, tintTypedArray.getColorStateList(R.styleable.AppCompatImageView_tint));
            }
            if (!tintTypedArray.hasValue(R.styleable.AppCompatImageView_tintMode)) return;
            ImageViewCompat.setImageTintMode(this.mView, DrawableUtils.parseTintMode(tintTypedArray.getInt(R.styleable.AppCompatImageView_tintMode, -1), null));
            return;
        }
        finally {
            tintTypedArray.recycle();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setImageResource(int n) {
        if (n != 0) {
            Drawable drawable2 = AppCompatResources.getDrawable(this.mView.getContext(), n);
            if (drawable2 != null) {
                DrawableUtils.fixDrawable(drawable2);
            }
            this.mView.setImageDrawable(drawable2);
        } else {
            this.mView.setImageDrawable(null);
        }
        this.applySupportImageTint();
    }

    void setSupportImageTintList(ColorStateList colorStateList) {
        if (this.mImageTint == null) {
            this.mImageTint = new TintInfo();
        }
        this.mImageTint.mTintList = colorStateList;
        this.mImageTint.mHasTintList = true;
        this.applySupportImageTint();
    }

    void setSupportImageTintMode(PorterDuff.Mode mode) {
        if (this.mImageTint == null) {
            this.mImageTint = new TintInfo();
        }
        this.mImageTint.mTintMode = mode;
        this.mImageTint.mHasTintMode = true;
        this.applySupportImageTint();
    }
}

