/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.drawable.Drawable
 *  android.text.TextPaint
 *  android.util.AttributeSet
 *  android.view.View
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class CenteredRadioButton
extends AppCompatRadioButton {
    public CenteredRadioButton(Context context) {
        super(context);
    }

    public CenteredRadioButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CenteredRadioButton(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    private boolean isRtl() {
        return ViewCompat.getLayoutDirection((View)this) == 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onDraw(Canvas canvas) {
        Drawable drawable2 = this.getCompoundDrawablesRelative();
        Drawable drawable3 = drawable2[0];
        drawable2 = drawable2[2];
        if (drawable3 != null || drawable2 != null) {
            float f = this.getPaint().measureText(this.getText().toString());
            int n = this.getCompoundDrawablePadding();
            int n2 = drawable3 != null ? drawable3.getIntrinsicWidth() : drawable2.getIntrinsicWidth();
            float f2 = n2;
            float f3 = n;
            f = f2 = ((float)this.getWidth() - (f2 + f + f3)) / 2.0f;
            if (this.isRtl()) {
                f = -f2;
            }
            canvas.translate(f, 0.0f);
        }
        super.onDraw(canvas);
    }
}

