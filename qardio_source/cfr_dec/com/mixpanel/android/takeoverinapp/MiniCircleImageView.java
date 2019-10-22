/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  android.widget.ImageView
 */
package com.mixpanel.android.takeoverinapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;

public class MiniCircleImageView
extends ImageView {
    private int mCanvasHeight;
    private int mCanvasWidth;
    private Paint mWhitePaint;

    public MiniCircleImageView(Context context) {
        super(context);
        this.init();
    }

    public MiniCircleImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    public MiniCircleImageView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void init() {
        this.mWhitePaint = new Paint(1);
        if (Build.VERSION.SDK_INT >= 23) {
            this.mWhitePaint.setColor(this.getResources().getColor(17170443, null));
        } else {
            this.mWhitePaint.setColor(this.getResources().getColor(17170443));
        }
        this.mWhitePaint.setStyle(Paint.Style.STROKE);
        float f = TypedValue.applyDimension((int)1, (float)2.0f, (DisplayMetrics)this.getResources().getDisplayMetrics());
        this.mWhitePaint.setStrokeWidth(f);
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.mCanvasWidth = n;
        this.mCanvasHeight = n2;
    }
}

