/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Typeface
 *  android.text.TextPaint
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.widget.HorizontalScrollView
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import com.getqardio.android.R;
import com.getqardio.android.ui.widget.WeightHelper;
import com.getqardio.android.utils.QardioBaseUtils;
import com.getqardio.android.utils.Utils;

public class BmiLabelsView
extends View {
    private String bmiLabel;
    private int height;
    private float maxWeight;
    private float minWeight;
    private int textColor;
    private Paint textPaint;
    private int textSize;
    private float valuesPerPixes;
    private WeightHelper weightHelper;

    public BmiLabelsView(Context context) {
        this(context, null);
    }

    public BmiLabelsView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BmiLabelsView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.obtainAttributes(context, attributeSet);
        this.bmiLabel = context.getString(2131362180) + " ";
        context = Typeface.create((String)context.getString(2131362540), (int)0);
        this.textPaint = new TextPaint();
        this.textPaint.setAntiAlias(true);
        this.textPaint.setTypeface((Typeface)context);
        this.textPaint.setColor(this.textColor);
        this.textPaint.setTextSize((float)this.textSize);
    }

    private void obtainAttributes(Context context, AttributeSet attributeSet) {
        context = context.obtainStyledAttributes(attributeSet, R.styleable.BmiLabelsView);
        this.textColor = context.getColor(1, 0);
        this.textSize = context.getDimensionPixelOffset(0, 0);
        context.recycle();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int n = 0;
        int n2 = (int)this.weightHelper.calculateBmi((int)this.minWeight, this.height);
        if ((float)this.height != 0.0f && this.valuesPerPixes != 0.0f) {
            while (n < 7000) {
                float f = (QardioBaseUtils.weightForBmi(++n2, this.height) - this.minWeight) / this.valuesPerPixes;
                String string2 = this.bmiLabel + Utils.formatInteger(n2);
                canvas.drawText(string2, 0, string2.length(), f, (float)this.textSize, this.textPaint);
                n = (int)f;
            }
        }
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        this.setMeasuredDimension(7000, this.textSize);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public void scrollTo(HorizontalScrollView horizontalScrollView, float f) {
        horizontalScrollView.scrollTo((int)((f - this.minWeight) / this.valuesPerPixes), this.textSize);
    }

    public void setCurrentWeight(float f) {
        this.minWeight = this.weightHelper.calculateMinWeight(f);
        this.maxWeight = this.weightHelper.calculateMaxWeight(f);
        this.valuesPerPixes = (this.maxWeight - this.minWeight) / 7000.0f;
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void setHeight(int n) {
        this.height = n;
    }

    public void setWeightHelper(WeightHelper weightHelper) {
        this.weightHelper = weightHelper;
    }
}

