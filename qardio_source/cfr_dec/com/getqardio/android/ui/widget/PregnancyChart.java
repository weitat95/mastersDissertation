/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.Canvas
 *  android.graphics.CornerPathEffect
 *  android.graphics.LinearGradient
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.PathEffect
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.Typeface
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.View
 *  android.view.WindowManager
 */
package com.getqardio.android.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import com.getqardio.android.R;
import com.getqardio.android.ui.widget.PregnancyChartAdapter;

public class PregnancyChart
extends View {
    private static final float[] GRADIENT_POSITIONS = new float[]{0.17f, 0.5f, 0.83f};
    private PregnancyChartAdapter adapter;
    private float chartPaddingBottom;
    private float chartPaddingTop;
    private Paint chartPaint;
    private DataSetObserver dataSetObserver;
    private int gridBarWidth;
    private Paint gridPaint;
    private float trimesterHeaderHeight;
    private Paint trimesterStripPaint;
    private Paint trimesterTextPaint;
    private Paint valueLabelsPaint;
    private float valuesLabelIndent;

    public PregnancyChart(Context context) {
        super(context);
        this.init(context, null);
    }

    public PregnancyChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context, attributeSet);
    }

    public PregnancyChart(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(context, attributeSet);
    }

    @SuppressLint(value={"NewApi"})
    public PregnancyChart(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.init(context, attributeSet);
    }

    private void drawMeasureValueLabel(Canvas canvas, float f, float f2, String string2, int n) {
        this.valueLabelsPaint.setColor(n);
        canvas.drawText(string2, f, f2 - this.valuesLabelIndent, this.valueLabelsPaint);
    }

    private void init(Context context, AttributeSet attributeSet) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager)context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        this.gridPaint = new Paint();
        this.gridPaint.setStrokeWidth(displayMetrics.density * 1.0f);
        this.trimesterStripPaint = new Paint();
        this.trimesterTextPaint = new Paint();
        this.trimesterTextPaint.setTextAlign(Paint.Align.CENTER);
        this.trimesterTextPaint.setAntiAlias(true);
        this.chartPaint = new Paint();
        this.chartPaint.setColor(-16777216);
        this.chartPaint.setDither(false);
        this.chartPaint.setStyle(Paint.Style.STROKE);
        this.chartPaint.setStrokeCap(Paint.Cap.ROUND);
        this.chartPaint.setPathEffect((PathEffect)new CornerPathEffect(displayMetrics.density * 30.0f));
        this.chartPaint.setAntiAlias(true);
        this.valueLabelsPaint = new Paint();
        this.valueLabelsPaint.setColor(-16777216);
        this.valueLabelsPaint.setTypeface(Typeface.DEFAULT);
        this.valueLabelsPaint.setTextAlign(Paint.Align.CENTER);
        this.valueLabelsPaint.setAntiAlias(true);
        this.valuesLabelIndent = 5.0f * displayMetrics.density;
        if (attributeSet != null) {
            context = context.obtainStyledAttributes(attributeSet, R.styleable.PregnancyChart, 0, 0);
            this.gridPaint.setColor(context.getColor(0, -3355444));
            this.trimesterHeaderHeight = context.getDimension(4, displayMetrics.density * 30.0f);
            float f = context.getDimension(3, 3.0f * displayMetrics.density);
            this.trimesterStripPaint.setStrokeWidth(f);
            this.trimesterTextPaint.setTextSize(context.getDimension(1, 14.0f * displayMetrics.density));
            this.trimesterTextPaint.setColor(context.getColor(2, -3355444));
            this.chartPaint.setStrokeWidth(context.getDimension(5, displayMetrics.density * 1.0f));
            this.valueLabelsPaint.setTextSize(context.getDimension(6, 12.0f * displayMetrics.density));
            this.chartPaddingTop = context.getDimension(7, 0.0f);
            this.chartPaddingBottom = context.getDimension(8, 0.0f);
        }
        return;
        finally {
            context.recycle();
        }
    }

    public PregnancyChartAdapter getAdapter() {
        return this.adapter;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onDraw(Canvas canvas) {
        float f;
        LinearGradient linearGradient;
        float f2;
        block6: {
            block5: {
                int n;
                super.onDraw(canvas);
                if (this.adapter == null) break block5;
                int n2 = canvas.getWidth();
                int n3 = canvas.getHeight();
                for (n = 1; n < 9; ++n) {
                    canvas.drawLine((float)(this.gridBarWidth * n), this.trimesterHeaderHeight, (float)(this.gridBarWidth * n), (float)n3, this.gridPaint);
                }
                linearGradient = new int[3];
                f2 = (float)n2 / 3.0f;
                f = (this.trimesterHeaderHeight - this.trimesterStripPaint.getStrokeWidth() + this.trimesterTextPaint.getTextSize()) / 2.0f;
                for (n = 0; n < 3; ++n) {
                    linearGradient[n] = this.adapter.getTrimesterColor(n);
                    this.trimesterStripPaint.setColor(this.adapter.getTrimesterColor(n));
                    canvas.drawLine((float)n * f2, this.trimesterHeaderHeight, (float)(n + 1) * f2, this.trimesterHeaderHeight, this.trimesterStripPaint);
                    canvas.drawText(this.adapter.getTrimesterLabel(n), ((float)n + 0.5f) * f2, f, this.trimesterTextPaint);
                }
                linearGradient = new LinearGradient(0.0f, 0.0f, (float)n2, 0.0f, (int[])linearGradient, GRADIENT_POSITIONS, Shader.TileMode.CLAMP);
                this.chartPaint.setShader((Shader)linearGradient);
                f = this.chartPaint.getStrokeWidth();
                f2 = (float)n3 - this.chartPaddingBottom;
                float f3 = this.adapter.getMaxValue();
                float f4 = ((float)n3 - this.trimesterHeaderHeight - this.chartPaddingTop - this.chartPaddingBottom) / (f3 - this.adapter.getMinValue());
                linearGradient = new Path();
                linearGradient.moveTo(f, f2);
                n2 = 0;
                for (n = 0; n < 9; ++n) {
                    if (!this.adapter.hasValue(n)) continue;
                    n2 = 1;
                    f2 = this.adapter.getValue(n);
                    f = ((float)n + 0.5f) * (float)this.gridBarWidth;
                    f2 = this.trimesterHeaderHeight + this.chartPaddingTop + (f3 - f2) * f4;
                    linearGradient.lineTo(f, f2);
                    this.drawMeasureValueLabel(canvas, f, f2, this.adapter.getValueLabel(n), this.adapter.getTrimesterColor(n / 3));
                }
                if (n2 != 0) break block6;
            }
            return;
        }
        linearGradient.lineTo(f, f2);
        canvas.drawPath((Path)linearGradient, this.chartPaint);
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        this.gridBarWidth = n / 9;
    }

    public void setAdapter(PregnancyChartAdapter pregnancyChartAdapter) {
        if (this.adapter != null && this.dataSetObserver != null) {
            this.adapter.unregisterDataSetObserver(this.dataSetObserver);
        }
        if (pregnancyChartAdapter != null) {
            this.dataSetObserver = new DataSetObserver(){

                public void onChanged() {
                    PregnancyChart.this.invalidate();
                }

                public void onInvalidated() {
                    PregnancyChart.this.invalidate();
                }
            };
            pregnancyChartAdapter.registerDataSetObserver(this.dataSetObserver);
        }
        this.adapter = pregnancyChartAdapter;
        this.invalidate();
    }

}

