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
 *  android.widget.CompoundButton
 *  android.widget.RadioButton
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.TintableCompoundButton;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatCompoundButtonHelper;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class AppCompatRadioButton
extends RadioButton
implements TintableCompoundButton {
    private final AppCompatCompoundButtonHelper mCompoundButtonHelper = new AppCompatCompoundButtonHelper((CompoundButton)this);

    public AppCompatRadioButton(Context context) {
        this(context, null);
    }

    public AppCompatRadioButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.radioButtonStyle);
    }

    public AppCompatRadioButton(Context context, AttributeSet attributeSet, int n) {
        super(TintContextWrapper.wrap(context), attributeSet, n);
        this.mCompoundButtonHelper.loadFromAttributes(attributeSet, n);
    }

    public int getCompoundPaddingLeft() {
        int n;
        int n2 = n = super.getCompoundPaddingLeft();
        if (this.mCompoundButtonHelper != null) {
            n2 = this.mCompoundButtonHelper.getCompoundPaddingLeft(n);
        }
        return n2;
    }

    public ColorStateList getSupportButtonTintList() {
        if (this.mCompoundButtonHelper != null) {
            return this.mCompoundButtonHelper.getSupportButtonTintList();
        }
        return null;
    }

    public PorterDuff.Mode getSupportButtonTintMode() {
        if (this.mCompoundButtonHelper != null) {
            return this.mCompoundButtonHelper.getSupportButtonTintMode();
        }
        return null;
    }

    public void setButtonDrawable(int n) {
        this.setButtonDrawable(AppCompatResources.getDrawable(this.getContext(), n));
    }

    public void setButtonDrawable(Drawable drawable2) {
        super.setButtonDrawable(drawable2);
        if (this.mCompoundButtonHelper != null) {
            this.mCompoundButtonHelper.onSetButtonDrawable();
        }
    }

    @Override
    public void setSupportButtonTintList(ColorStateList colorStateList) {
        if (this.mCompoundButtonHelper != null) {
            this.mCompoundButtonHelper.setSupportButtonTintList(colorStateList);
        }
    }

    @Override
    public void setSupportButtonTintMode(PorterDuff.Mode mode) {
        if (this.mCompoundButtonHelper != null) {
            this.mCompoundButtonHelper.setSupportButtonTintMode(mode);
        }
    }
}

