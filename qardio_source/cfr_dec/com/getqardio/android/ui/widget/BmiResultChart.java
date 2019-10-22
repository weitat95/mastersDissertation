/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Typeface
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.view.AbsSavedState
 *  android.view.View
 *  android.view.View$BaseSavedState
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.View;
import com.getqardio.android.utils.Utils;

public class BmiResultChart
extends View {
    private static final int[] BMI_STAGES;
    private static final double[] BOTTOM_LABELS;
    private static final int[] COLORS;
    private String bmiLabel;
    private float bmiLabelMarginTop;
    private float bmiLabelTextSize;
    private Paint bmiTextPaint;
    private Rect bmiTextRect;
    private float bmiValueTextSize;
    private float bodyMassIndex;
    private float chartHeight;
    private float chartMarginTop;
    private Paint chartPaint;
    private RectF chartPieceRect;
    private int itemWidth;
    private Resources resources;
    private Typeface robotoLightFont = Typeface.create((String)"sans-serif-light", (int)0);
    private Typeface robotoRegularFont = Typeface.create((String)"sans-serif", (int)0);
    private Rect textBoundsRect;
    private float textLeftMargin;
    private Paint textPaint;

    static {
        COLORS = new int[]{2131689494, 2131689491, 2131689493, 2131689492};
        BMI_STAGES = new int[]{2131362393, 2131362299, 2131362304, 2131362301};
        BOTTOM_LABELS = new double[]{18.5, 25.0, 30.0};
    }

    public BmiResultChart(Context context) {
        this(context, null, 0);
    }

    public BmiResultChart(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BmiResultChart(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init();
    }

    private void clear() {
        this.bodyMassIndex = -1.0f;
        this.invalidate();
    }

    private int getBmiValueTextColor(double d) {
        return COLORS[this.getValueIndex(d)];
    }

    private int getLeftBmiPosition(double d) {
        return this.getValueIndex(d) * this.itemWidth;
    }

    private int getValueIndex(double d) {
        if (d < 18.5) {
            return 0;
        }
        if (d < 25.0) {
            return 1;
        }
        if (d < 30.0) {
            return 2;
        }
        return 3;
    }

    private void init() {
        this.resources = this.getResources();
        float f = this.resources.getDimensionPixelOffset(2131427673);
        this.chartHeight = this.resources.getDimensionPixelOffset(2131427424);
        this.textLeftMargin = this.resources.getDimensionPixelOffset(2131427425);
        this.chartMarginTop = this.resources.getDimensionPixelOffset(2131427342);
        this.bmiValueTextSize = this.resources.getDimensionPixelOffset(2131427674);
        this.bmiLabelMarginTop = this.resources.getDimensionPixelOffset(2131427426);
        this.bmiLabelTextSize = this.resources.getDimensionPixelOffset(2131427673);
        this.bmiLabel = this.resources.getString(2131362181);
        this.textPaint = new Paint();
        this.textPaint.setTextSize(f);
        this.textPaint.setAntiAlias(true);
        this.textPaint.setTextAlign(Paint.Align.LEFT);
        this.textPaint.setTypeface(this.robotoLightFont);
        this.textPaint.setColor(-1);
        this.chartPaint = new Paint();
        this.bmiTextPaint = new Paint();
        this.bmiTextPaint.setAntiAlias(true);
        this.bmiTextPaint.setTextSize(this.bmiValueTextSize);
        this.bmiTextPaint.setTypeface(this.robotoRegularFont);
        this.chartPieceRect = new RectF();
        this.bmiTextRect = new Rect();
        this.textBoundsRect = new Rect();
        this.bodyMassIndex = -1.0f;
    }

    public void clearBodyMassIndex() {
        this.setBodyMassIndex(Float.valueOf(-1.0f));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.bmiTextRect.set(0, 0, 0, 0);
        this.bmiTextPaint.setTextSize(this.bmiValueTextSize);
        this.bmiTextPaint.setColor(this.resources.getColor(this.getBmiValueTextColor(this.bodyMassIndex)));
        Object object = this.getResources().getString(2131362461, new Object[]{Float.valueOf(this.bodyMassIndex)});
        this.bmiTextPaint.getTextBounds((String)object, 0, ((String)object).length(), this.bmiTextRect);
        float f = -this.bmiTextRect.top;
        int n = (int)(this.bmiLabelMarginTop + f);
        this.itemWidth = this.getMeasuredWidth() / COLORS.length;
        this.textBoundsRect.set(0, 0, 0, 0);
        if (this.bodyMassIndex != -1.0f && this.bodyMassIndex != Float.POSITIVE_INFINITY) {
            canvas.drawText((String)object, (float)(this.getLeftBmiPosition(this.bodyMassIndex) + (this.itemWidth / 2 - (this.bmiTextRect.right - this.bmiTextRect.left) / 2)), (float)(-this.bmiTextRect.top), this.bmiTextPaint);
            this.bmiTextPaint.setTextSize(this.bmiLabelTextSize);
            this.bmiTextPaint.getTextBounds(this.bmiLabel, 0, this.bmiLabel.length(), this.bmiTextRect);
            f = this.getLeftBmiPosition(this.bodyMassIndex) + (this.itemWidth / 2 - (this.bmiTextRect.right - this.bmiTextRect.left) / 2);
            canvas.drawText(this.bmiLabel, f, (float)n, this.bmiTextPaint);
        }
        f = (float)n + this.chartMarginTop;
        this.chartPieceRect.set(0.0f, f, 0.0f, this.chartHeight + f);
        this.textPaint.setColor(-1);
        for (n = 0; n < COLORS.length; ++n) {
            this.chartPieceRect.left = f = this.chartPieceRect.right;
            this.chartPieceRect.right = (float)this.itemWidth + f;
            this.chartPaint.setColor(this.resources.getColor(COLORS[n]));
            canvas.drawRect(this.chartPieceRect, this.chartPaint);
            object = this.resources.getString(BMI_STAGES[n]);
            this.textPaint.getTextBounds((String)object, 0, ((String)object).length(), this.textBoundsRect);
            canvas.drawText((String)object, this.chartPieceRect.left + this.textLeftMargin, this.chartPieceRect.bottom + (float)(this.textBoundsRect.top / 2), this.textPaint);
        }
        f = this.itemWidth;
        float f2 = this.chartPieceRect.bottom;
        this.textPaint.setColor(-16777216);
        object = BOTTOM_LABELS;
        int n2 = ((double[])object).length;
        for (n = 0; n < n2; ++n) {
            String string2 = Utils.formatFloat((float)object[n]);
            this.textPaint.getTextBounds(string2, 0, string2.length(), this.textBoundsRect);
            canvas.drawText(string2, f - (float)((this.textBoundsRect.right - this.textBoundsRect.left) / 2), f2 + 10.0f - (float)(this.textBoundsRect.top / 2) + 15.0f, this.textPaint);
            f += (float)this.itemWidth;
        }
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState((Parcelable)View.BaseSavedState.EMPTY_STATE);
        if (parcelable instanceof Bundle) {
            this.bodyMassIndex = ((Bundle)parcelable).getFloat("com.getqardio.android.BMI", -1.0f);
            return;
        }
        this.clear();
    }

    protected Parcelable onSaveInstanceState() {
        super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putFloat("com.getqardio.android.BMI", this.bodyMassIndex);
        return bundle;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setBodyMassIndex(Float f) {
        this.bodyMassIndex = f != null ? f.floatValue() : 0.0f;
        this.invalidate();
    }
}

