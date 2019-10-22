/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.widget.TextView
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;
import com.getqardio.android.ui.widget.TextViewErrorHelper;

public class CustomTextView
extends AppCompatTextView {
    private TextViewErrorHelper errorHelper = new TextViewErrorHelper(this);
    private boolean hasFallen = false;

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CustomTextView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.hasFallen) {
            this.errorHelper.onAttachedToWindow();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!this.hasFallen) {
            this.errorHelper.onDetachedFromWindow();
        }
    }

    protected void onFocusChanged(boolean bl, int n, Rect rect) {
        super.onFocusChanged(bl, n, rect);
        this.errorHelper.onFocusChanged(bl);
    }

    public void setCompoundDrawables(Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
        super.setCompoundDrawables(drawable2, drawable3, drawable4, drawable5);
        if (this.hasFallen || this.errorHelper == null) {
            return;
        }
        this.errorHelper.setCompoundDrawables(drawable2, drawable3, drawable4, drawable5);
    }

    public void setError(CharSequence charSequence) {
        this.errorHelper.setError(charSequence);
    }

    public void setError(CharSequence charSequence, Drawable drawable2) {
        try {
            this.errorHelper.setTheError(charSequence);
            this.errorHelper.setError(charSequence, drawable2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            this.hasFallen = true;
            super.setError(charSequence, drawable2);
            return;
        }
    }

    protected boolean setFrame(int n, int n2, int n3, int n4) {
        boolean bl = super.setFrame(n, n2, n3, n4);
        if (!this.hasFallen) {
            this.errorHelper.setFrame();
        }
        return bl;
    }
}

