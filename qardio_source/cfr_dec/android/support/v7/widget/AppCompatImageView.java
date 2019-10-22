/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Bitmap
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Icon
 *  android.net.Uri
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.ImageView
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.widget.TintableImageSourceView;
import android.support.v7.widget.AppCompatBackgroundHelper;
import android.support.v7.widget.AppCompatImageHelper;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class AppCompatImageView
extends ImageView
implements TintableBackgroundView,
TintableImageSourceView {
    private final AppCompatBackgroundHelper mBackgroundTintHelper = new AppCompatBackgroundHelper((View)this);
    private final AppCompatImageHelper mImageHelper;

    public AppCompatImageView(Context context) {
        this(context, null);
    }

    public AppCompatImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AppCompatImageView(Context context, AttributeSet attributeSet, int n) {
        super(TintContextWrapper.wrap(context), attributeSet, n);
        this.mBackgroundTintHelper.loadFromAttributes(attributeSet, n);
        this.mImageHelper = new AppCompatImageHelper(this);
        this.mImageHelper.loadFromAttributes(attributeSet, n);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySupportBackgroundTint();
        }
        if (this.mImageHelper != null) {
            this.mImageHelper.applySupportImageTint();
        }
    }

    @Override
    public ColorStateList getSupportBackgroundTintList() {
        if (this.mBackgroundTintHelper != null) {
            return this.mBackgroundTintHelper.getSupportBackgroundTintList();
        }
        return null;
    }

    @Override
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        if (this.mBackgroundTintHelper != null) {
            return this.mBackgroundTintHelper.getSupportBackgroundTintMode();
        }
        return null;
    }

    @Override
    public ColorStateList getSupportImageTintList() {
        if (this.mImageHelper != null) {
            return this.mImageHelper.getSupportImageTintList();
        }
        return null;
    }

    @Override
    public PorterDuff.Mode getSupportImageTintMode() {
        if (this.mImageHelper != null) {
            return this.mImageHelper.getSupportImageTintMode();
        }
        return null;
    }

    public boolean hasOverlappingRendering() {
        return this.mImageHelper.hasOverlappingRendering() && super.hasOverlappingRendering();
    }

    public void setBackgroundDrawable(Drawable drawable2) {
        super.setBackgroundDrawable(drawable2);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundDrawable(drawable2);
        }
    }

    public void setBackgroundResource(int n) {
        super.setBackgroundResource(n);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundResource(n);
        }
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        if (this.mImageHelper != null) {
            this.mImageHelper.applySupportImageTint();
        }
    }

    public void setImageDrawable(Drawable drawable2) {
        super.setImageDrawable(drawable2);
        if (this.mImageHelper != null) {
            this.mImageHelper.applySupportImageTint();
        }
    }

    public void setImageIcon(Icon icon) {
        super.setImageIcon(icon);
        if (this.mImageHelper != null) {
            this.mImageHelper.applySupportImageTint();
        }
    }

    public void setImageResource(int n) {
        if (this.mImageHelper != null) {
            this.mImageHelper.setImageResource(n);
        }
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        if (this.mImageHelper != null) {
            this.mImageHelper.applySupportImageTint();
        }
    }

    @Override
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintList(colorStateList);
        }
    }

    @Override
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintMode(mode);
        }
    }

    @Override
    public void setSupportImageTintList(ColorStateList colorStateList) {
        if (this.mImageHelper != null) {
            this.mImageHelper.setSupportImageTintList(colorStateList);
        }
    }

    @Override
    public void setSupportImageTintMode(PorterDuff.Mode mode) {
        if (this.mImageHelper != null) {
            this.mImageHelper.setSupportImageTintMode(mode);
        }
    }
}

