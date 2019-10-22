/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import com.getqardio.android.R;

public class StartButton
extends AppCompatButton {
    private int disabledBackgroundResId;
    private boolean enabled;
    private int enabledBackgroundResId;

    public StartButton(Context context) {
        super(context);
        this.init(context, null);
    }

    public StartButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context, attributeSet);
    }

    public StartButton(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.enabled = false;
        this.obtainAttributes(context, attributeSet);
        this.setTypeface(Typeface.create((String)"sans-serif-light", (int)0));
        this.updateButtonBackground(false);
    }

    private void obtainAttributes(Context context, AttributeSet attributeSet) {
        context = context.obtainStyledAttributes(attributeSet, R.styleable.StartButton);
        this.enabledBackgroundResId = context.getResourceId(0, 2130837960);
        this.disabledBackgroundResId = context.getResourceId(1, 2130837983);
        context.recycle();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateButtonBackground(boolean bl) {
        int n = bl ? this.enabledBackgroundResId : this.disabledBackgroundResId;
        this.setBackground(this.getContext().getResources().getDrawable(n));
    }

    public boolean isButtonEnabled() {
        return this.enabled;
    }

    public void setButtonEnabled(boolean bl) {
        this.enabled = bl;
        this.updateButtonBackground(bl);
    }
}

