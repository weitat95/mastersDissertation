/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.TintableBackgroundView;
import android.support.v7.widget.AppCompatBackgroundHelper;
import android.support.v7.widget.AppCompatTextHelper;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class AppCompatTextView
extends TextView
implements TintableBackgroundView {
    private final AppCompatBackgroundHelper mBackgroundTintHelper = new AppCompatBackgroundHelper((View)this);
    private final AppCompatTextHelper mTextHelper;

    public AppCompatTextView(Context context) {
        this(context, null);
    }

    public AppCompatTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public AppCompatTextView(Context context, AttributeSet attributeSet, int n) {
        super(TintContextWrapper.wrap(context), attributeSet, n);
        this.mBackgroundTintHelper.loadFromAttributes(attributeSet, n);
        this.mTextHelper = AppCompatTextHelper.create(this);
        this.mTextHelper.loadFromAttributes(attributeSet, n);
        this.mTextHelper.applyCompoundDrawablesTints();
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySupportBackgroundTint();
        }
        if (this.mTextHelper != null) {
            this.mTextHelper.applyCompoundDrawablesTints();
        }
    }

    public int getAutoSizeMaxTextSize() {
        if (Build.VERSION.SDK_INT >= 26) {
            return super.getAutoSizeMaxTextSize();
        }
        if (this.mTextHelper != null) {
            return this.mTextHelper.getAutoSizeMaxTextSize();
        }
        return -1;
    }

    public int getAutoSizeMinTextSize() {
        if (Build.VERSION.SDK_INT >= 26) {
            return super.getAutoSizeMinTextSize();
        }
        if (this.mTextHelper != null) {
            return this.mTextHelper.getAutoSizeMinTextSize();
        }
        return -1;
    }

    public int getAutoSizeStepGranularity() {
        if (Build.VERSION.SDK_INT >= 26) {
            return super.getAutoSizeStepGranularity();
        }
        if (this.mTextHelper != null) {
            return this.mTextHelper.getAutoSizeStepGranularity();
        }
        return -1;
    }

    public int[] getAutoSizeTextAvailableSizes() {
        if (Build.VERSION.SDK_INT >= 26) {
            return super.getAutoSizeTextAvailableSizes();
        }
        if (this.mTextHelper != null) {
            return this.mTextHelper.getAutoSizeTextAvailableSizes();
        }
        return new int[0];
    }

    public int getAutoSizeTextType() {
        if (Build.VERSION.SDK_INT >= 26) {
            if (super.getAutoSizeTextType() == 1) {
                return 1;
            }
            return 0;
        }
        if (this.mTextHelper != null) {
            return this.mTextHelper.getAutoSizeTextType();
        }
        return 0;
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

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        if (this.mTextHelper != null) {
            this.mTextHelper.onLayout(bl, n, n2, n3, n4);
        }
    }

    protected void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        super.onTextChanged(charSequence, n, n2, n3);
        if (this.mTextHelper != null && Build.VERSION.SDK_INT < 26 && this.mTextHelper.isAutoSizeEnabled()) {
            this.mTextHelper.autoSizeText();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setAutoSizeTextTypeUniformWithConfiguration(int n, int n2, int n3, int n4) throws IllegalArgumentException {
        if (Build.VERSION.SDK_INT >= 26) {
            super.setAutoSizeTextTypeUniformWithConfiguration(n, n2, n3, n4);
            return;
        } else {
            if (this.mTextHelper == null) return;
            {
                this.mTextHelper.setAutoSizeTextTypeUniformWithConfiguration(n, n2, n3, n4);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setAutoSizeTextTypeUniformWithPresetSizes(int[] arrn, int n) throws IllegalArgumentException {
        if (Build.VERSION.SDK_INT >= 26) {
            super.setAutoSizeTextTypeUniformWithPresetSizes(arrn, n);
            return;
        } else {
            if (this.mTextHelper == null) return;
            {
                this.mTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(arrn, n);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setAutoSizeTextTypeWithDefaults(int n) {
        if (Build.VERSION.SDK_INT >= 26) {
            super.setAutoSizeTextTypeWithDefaults(n);
            return;
        } else {
            if (this.mTextHelper == null) return;
            {
                this.mTextHelper.setAutoSizeTextTypeWithDefaults(n);
                return;
            }
        }
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

    public void setTextAppearance(Context context, int n) {
        super.setTextAppearance(context, n);
        if (this.mTextHelper != null) {
            this.mTextHelper.onSetTextAppearance(context, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setTextSize(int n, float f) {
        if (Build.VERSION.SDK_INT >= 26) {
            super.setTextSize(n, f);
            return;
        } else {
            if (this.mTextHelper == null) return;
            {
                this.mTextHelper.setTextSize(n, f);
                return;
            }
        }
    }
}

