/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$FontMetrics
 *  android.graphics.Paint$FontMetricsInt
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextPaint
 *  android.util.AttributeSet
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.TextPaint;
import android.util.AttributeSet;
import com.getqardio.android.R;

public class ThumbTextSeekBar
extends AppCompatSeekBar {
    private Paint.FontMetrics fontMetrics;
    private String thumbBottomText;
    private int thumbTextPadding;
    private TextPaint thumbTextPaint;
    private String thumbTopText;

    public ThumbTextSeekBar(Context context) {
        this(context, null);
    }

    public ThumbTextSeekBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ThumbTextSeekBar(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.ThumbTextSeekBar, n, 2131493267);
        this.thumbTextPaint = new TextPaint();
        this.thumbTextPaint.setTextAlign(Paint.Align.CENTER);
        this.thumbTextPaint.setAntiAlias(true);
        n = context.getDimensionPixelSize(2, 10);
        int n2 = context.getColor(1, -16777216);
        this.thumbTextPadding = context.getDimensionPixelSize(0, 0);
        context.recycle();
        this.thumbTextPaint.setTextSize((float)n);
        this.thumbTextPaint.setColor(n2);
        this.fontMetrics = new Paint.FontMetrics();
        this.thumbTextPaint.getFontMetrics(this.fontMetrics);
    }

    /*
     * Enabled aggressive block sorting
     */
    private float adjustTextPositionToBounds(float f, String string2) {
        float f2;
        block3: {
            float f3;
            block2: {
                f2 = this.thumbTextPaint.measureText(string2);
                f3 = (float)this.getPaddingLeft() + f2 / 2.0f;
                f2 = (float)(this.getWidth() - this.getPaddingRight()) - f2 / 2.0f;
                if (f < f3) break block2;
                f3 = f;
                if (f > f2) break block3;
            }
            return f3;
        }
        return f2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void onDraw(Canvas canvas) {
        synchronized (this) {
            float f = this.fontMetrics.descent;
            float f2 = this.fontMetrics.ascent;
            int n = (int)((float)(this.thumbTextPadding * 2) + (f - f2));
            Rect rect = this.getThumb().getBounds();
            if (Build.VERSION.SDK_INT >= 23) {
                super.onDraw(canvas);
            } else {
                rect.offset(0, n);
                super.onDraw(canvas);
                rect.offset(0, -n);
            }
            f2 = rect.centerX() + this.getPaddingLeft() - this.getThumbOffset();
            f = this.getHeight() / 2;
            n = this.getThumb().getIntrinsicHeight();
            if (this.thumbTopText != null) {
                float f3 = this.adjustTextPositionToBounds(f2, this.thumbTopText);
                canvas.drawText(this.thumbTopText, f3, f - (float)n * 1.0f / 2.0f - (float)this.thumbTextPadding - this.fontMetrics.descent, (Paint)this.thumbTextPaint);
            }
            if (this.thumbBottomText != null) {
                f2 = this.adjustTextPositionToBounds(f2, this.thumbBottomText);
                canvas.drawText(this.thumbBottomText, f2, (float)n * 1.0f / 2.0f + f + (float)this.thumbTextPadding - this.fontMetrics.ascent, (Paint)this.thumbTextPaint);
            }
            return;
        }
    }

    protected void onMeasure(int n, int n2) {
        synchronized (this) {
            super.onMeasure(n, n2);
            int n3 = this.thumbTextPaint.getFontMetricsInt(null);
            int n4 = this.getThumb().getIntrinsicHeight();
            n3 = Math.max(this.getMeasuredHeight(), n4 + n3 + this.thumbTextPadding * 2 + n3 + this.thumbTextPadding * 2 + this.getPaddingTop() + this.getPaddingBottom());
            this.setMeasuredDimension(ThumbTextSeekBar.resolveSizeAndState((int)this.getMeasuredWidth(), (int)n, (int)0), ThumbTextSeekBar.resolveSizeAndState((int)n3, (int)n2, (int)0));
            return;
        }
    }

    public void setThumbBottomText(String string2) {
        this.thumbBottomText = string2;
        this.invalidate();
    }

    public void setThumbTopText(String string2) {
        this.thumbTopText = string2;
        this.invalidate();
    }
}

