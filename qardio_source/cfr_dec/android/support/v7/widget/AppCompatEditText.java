/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.EditText
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.view.TintableBackgroundView;
import android.support.v7.appcompat.R;
import android.support.v7.widget.AppCompatBackgroundHelper;
import android.support.v7.widget.AppCompatTextHelper;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AppCompatEditText
extends EditText
implements TintableBackgroundView {
    private final AppCompatBackgroundHelper mBackgroundTintHelper = new AppCompatBackgroundHelper((View)this);
    private final AppCompatTextHelper mTextHelper;

    public AppCompatEditText(Context context) {
        this(context, null);
    }

    public AppCompatEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.editTextStyle);
    }

    public AppCompatEditText(Context context, AttributeSet attributeSet, int n) {
        super(TintContextWrapper.wrap(context), attributeSet, n);
        this.mBackgroundTintHelper.loadFromAttributes(attributeSet, n);
        this.mTextHelper = AppCompatTextHelper.create((TextView)this);
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
}

