/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$Style
 *  android.graphics.Typeface
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.renderer.Renderer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public abstract class DataRenderer
extends Renderer {
    protected ChartAnimator mAnimator;
    protected Paint mDrawPaint;
    protected Paint mHighlightPaint;
    protected Paint mRenderPaint;
    protected Paint mValuePaint;

    public DataRenderer(ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(viewPortHandler);
        this.mAnimator = chartAnimator;
        this.mRenderPaint = new Paint(1);
        this.mRenderPaint.setStyle(Paint.Style.FILL);
        this.mDrawPaint = new Paint(4);
        this.mValuePaint = new Paint(1);
        this.mValuePaint.setColor(Color.rgb((int)63, (int)63, (int)63));
        this.mValuePaint.setTextAlign(Paint.Align.CENTER);
        this.mValuePaint.setTextSize(Utils.convertDpToPixel(9.0f));
        this.mHighlightPaint = new Paint(1);
        this.mHighlightPaint.setStyle(Paint.Style.STROKE);
        this.mHighlightPaint.setStrokeWidth(2.0f);
        this.mHighlightPaint.setColor(Color.rgb((int)255, (int)187, (int)115));
    }

    protected void applyValueTextStyle(IDataSet iDataSet) {
        this.mValuePaint.setTypeface(iDataSet.getValueTypeface());
        this.mValuePaint.setTextSize(iDataSet.getValueTextSize());
    }

    public abstract void drawData(Canvas var1);

    public abstract void drawExtras(Canvas var1);

    public abstract void drawHighlighted(Canvas var1, Highlight[] var2);

    public void drawValue(Canvas canvas, IValueFormatter iValueFormatter, float f, Entry entry, int n, float f2, float f3, int n2) {
        this.mValuePaint.setColor(n2);
        canvas.drawText(iValueFormatter.getFormattedValue(f, entry, n, this.mViewPortHandler), f2, f3, this.mValuePaint);
    }

    public abstract void drawValues(Canvas var1);

    public abstract void initBuffers();

    protected boolean isDrawingValuesAllowed(ChartInterface chartInterface) {
        return (float)chartInterface.getData().getEntryCount() < (float)chartInterface.getMaxVisibleCount() * this.mViewPortHandler.getScaleX();
    }
}

