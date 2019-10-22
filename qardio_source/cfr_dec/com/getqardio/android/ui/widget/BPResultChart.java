/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class BPResultChart
extends View {
    private static final int[] DIA_LABELS;
    private static final float[] DIA_PORTIONS;
    private static final int[] DIA_VALUES;
    private static final int[] RECT_COLORS;
    private static final int[] SYS_LABELS;
    private static final float[] SYS_PORTIONS;
    private static final int[] SYS_VALUES;
    private int additionalPadding;
    private int bgColor;
    private float bottomLabelsPanel;
    private String[] bpStages;
    private float bpStagesPadding;
    private int bpStagesTextColor;
    private float bpStagesTextSize;
    private int dia;
    private String diaLabel;
    private float labelsPadding;
    private int labelsTextColor;
    private float labelsTextSize;
    private float leftLabelsPanel;
    private Paint paint;
    private float pinBottomShadowSize;
    private float pinXCache;
    private float pinYCache;
    private Bitmap resultPin;
    private int sys;
    private String sysLabel;

    static {
        RECT_COLORS = new int[]{2131689498, 2131689497, 2131689496, 2131689495, 2131689499, 2131689500};
        DIA_PORTIONS = new float[]{1.0f, 0.93f, 0.81f, 0.69f, 0.57f, 0.45f, 0.0f};
        SYS_PORTIONS = new float[]{1.0f, 0.8f, 0.64f, 0.48f, 0.32f, 0.16f, 0.0f};
        DIA_VALUES = new int[]{120, 110, 100, 90, 85, 80, 40};
        SYS_VALUES = new int[]{190, 180, 160, 140, 130, 120, 70};
        DIA_LABELS = new int[]{0, 110, 100, 90, 85, 80};
        SYS_LABELS = new int[]{0, 180, 160, 140, 130, 120};
    }

    public BPResultChart(Context context) {
        super(context);
        this.init(context);
    }

    public BPResultChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context);
    }

    public BPResultChart(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(context);
    }

    /*
     * Enabled aggressive block sorting
     */
    private float calculatePinX(float f) {
        if (this.pinXCache != -1.0f) return this.pinXCache;
        float f2 = Math.min(this.dia, 120);
        if (f2 >= (float)DIA_VALUES[0]) {
            this.pinXCache = f;
            return this.pinXCache;
        }
        if (f2 <= (float)DIA_VALUES[DIA_VALUES.length - 1]) {
            this.pinXCache = 0.0f;
            return this.pinXCache;
        }
        int n = 0;
        while (n < DIA_VALUES.length - 1) {
            if (f2 > (float)DIA_VALUES[n + 1]) {
                float f3 = (DIA_PORTIONS[n] - DIA_PORTIONS[n + 1]) * f / (float)(DIA_VALUES[n] - DIA_VALUES[n + 1]);
                this.pinXCache = DIA_PORTIONS[n] * f + (f2 - (float)DIA_VALUES[n]) * f3;
                return this.pinXCache;
            }
            ++n;
        }
        return this.pinXCache;
    }

    /*
     * Enabled aggressive block sorting
     */
    private float calculatePinY(float f) {
        if (this.pinYCache != -1.0f) return this.pinYCache;
        float f2 = Math.min(this.sys, 190);
        if (f2 >= (float)SYS_VALUES[0]) {
            this.pinYCache = f;
            return this.pinYCache;
        }
        if (f2 <= (float)SYS_VALUES[SYS_VALUES.length - 1]) {
            this.pinYCache = 0.0f;
            return this.pinYCache;
        }
        int n = 0;
        while (n < SYS_VALUES.length - 1) {
            if (f2 > (float)SYS_VALUES[n + 1]) {
                float f3 = (SYS_PORTIONS[n] - SYS_PORTIONS[n + 1]) * f / (float)(SYS_VALUES[n] - SYS_VALUES[n + 1]);
                this.pinYCache = SYS_PORTIONS[n] * f + (f2 - (float)SYS_VALUES[n]) * f3;
                return this.pinYCache;
            }
            ++n;
        }
        return this.pinYCache;
    }

    private float calculateTextY(float f, float f2) {
        float f3 = 0.0f;
        int n = 0;
        do {
            block4: {
                float f4;
                block3: {
                    f4 = f3;
                    if (n >= SYS_VALUES.length - 1) break block3;
                    if (!(f > (float)SYS_VALUES[n + 1])) break block4;
                    f2 = (SYS_PORTIONS[n] - SYS_PORTIONS[n + 1]) * f2 / (float)(SYS_VALUES[n] - SYS_VALUES[n + 1]);
                    f4 = ((float)SYS_VALUES[n] - f) * f2;
                }
                return f4;
            }
            ++n;
        } while (true);
    }

    private void init(Context context) {
        this.sys = -1;
        this.dia = -1;
        this.pinXCache = -1.0f;
        this.pinYCache = -1.0f;
        this.paint = new Paint();
        this.paint.setTypeface(Typeface.DEFAULT);
        this.paint.setTextSize(this.getResources().getDimension(2131427673));
        this.paint.setAntiAlias(true);
        this.bgColor = this.getResources().getColor(2131689553);
        this.labelsTextColor = this.getResources().getColor(2131689489);
        this.labelsTextSize = this.getResources().getDimension(2131427673);
        this.bpStagesTextColor = this.getResources().getColor(2131689558);
        this.bpStagesTextSize = this.getResources().getDimension(2131427672);
        this.resultPin = BitmapFactory.decodeResource((Resources)this.getResources(), (int)2130837917);
        this.sysLabel = this.getResources().getString(2131362042);
        this.diaLabel = this.getResources().getString(2131361896);
        this.bpStages = this.getResources().getStringArray(2131755009);
        this.pinBottomShadowSize = this.getResources().getDimension(2131427619);
        this.labelsPadding = this.getResources().getDimension(2131427430);
        this.bpStagesPadding = this.getResources().getDimension(2131427429);
        this.leftLabelsPanel = this.getResources().getDimension(2131427362);
        this.bottomLabelsPanel = this.getResources().getDimension(2131427428);
    }

    public void clear() {
        this.sys = -1;
        this.dia = -1;
        this.pinXCache = -1.0f;
        this.pinYCache = -1.0f;
        this.invalidate();
    }

    protected void onDraw(Canvas canvas) {
        float f;
        super.onDraw(canvas);
        if (this.dia >= 115) {
            this.additionalPadding = this.getResources().getDimensionPixelOffset(2131427427);
        }
        RectF rectF = new RectF((float)this.additionalPadding, (float)this.resultPin.getHeight(), (float)(canvas.getWidth() - this.additionalPadding), (float)canvas.getHeight());
        this.paint.setColor(this.bgColor);
        canvas.drawRect(rectF, this.paint);
        rectF.left += this.leftLabelsPanel;
        rectF.bottom -= this.bottomLabelsPanel;
        float f2 = rectF.width();
        float f3 = rectF.height();
        this.paint.setTextAlign(Paint.Align.RIGHT);
        this.paint.setColor(this.labelsTextColor);
        this.paint.setTextSize(this.labelsTextSize);
        Rect rect = new Rect();
        this.paint.getTextBounds("+", 0, 1, rect);
        float f4 = rect.width();
        float f5 = this.labelsPadding;
        this.paint.getTextBounds(this.diaLabel, 0, this.diaLabel.length(), rect);
        float f6 = canvas.getHeight();
        float f7 = (this.bottomLabelsPanel - (float)rect.height()) / 2.0f;
        Resources resources = this.getResources();
        for (int i = 0; i < RECT_COLORS.length; ++i) {
            rectF.top = rectF.bottom - SYS_PORTIONS[i] * f3;
            rectF.right = rectF.left + DIA_PORTIONS[i] * f2;
            this.paint.setColor(resources.getColor(RECT_COLORS[i]));
            canvas.drawRect(rectF, this.paint);
            if (i > 0) {
                this.paint.setTextAlign(Paint.Align.RIGHT);
                this.paint.setTextSize(this.labelsTextSize);
                this.paint.setColor(this.labelsTextColor);
                this.sysLabel = Utils.formatInteger(SYS_LABELS[i]);
                this.paint.getTextBounds(this.sysLabel, 0, this.sysLabel.length(), rect);
                canvas.drawText(this.sysLabel, this.leftLabelsPanel - (f4 + f5) + (float)this.additionalPadding, rectF.top + (float)(rect.height() / 2), this.paint);
                this.diaLabel = Utils.formatInteger(DIA_LABELS[i]);
                this.paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(this.diaLabel, rectF.right, f6 - f7, this.paint);
            }
            this.paint.setTextAlign(Paint.Align.LEFT);
            this.paint.setColor(this.bpStagesTextColor);
            this.paint.setTextSize(this.bpStagesTextSize);
            this.paint.getTextBounds(this.bpStages[i], 0, this.bpStages[i].length(), rect);
            f = this.calculateTextY((SYS_VALUES[i] + SYS_VALUES[i + 1]) / 2, f3);
            canvas.drawText(this.bpStages[i], this.leftLabelsPanel + this.bpStagesPadding + (float)this.additionalPadding, rectF.top + this.bpStagesPadding + f, this.paint);
        }
        f4 = this.leftLabelsPanel;
        f5 = canvas.getHeight();
        f6 = this.bottomLabelsPanel;
        if (this.sys != -1 && this.dia != -1) {
            f7 = this.additionalPadding;
            f2 = this.calculatePinX(f2);
            f = this.resultPin.getWidth() / 2;
            f3 = this.calculatePinY(f3);
            float f8 = this.resultPin.getHeight();
            float f9 = this.pinBottomShadowSize;
            canvas.drawBitmap(this.resultPin, f7 + f4 + (f2 - f), f5 - f6 - (f3 + (f8 - f9)), null);
        }
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState((Parcelable)View.BaseSavedState.EMPTY_STATE);
        if (parcelable instanceof Bundle) {
            parcelable = (Bundle)parcelable;
            this.dia = parcelable.getInt("com.getqardio.android.DIA_KEY", -1);
            this.sys = parcelable.getInt("com.getqardio.android.SYS_KEY", -1);
            this.pinXCache = -1.0f;
            this.pinYCache = -1.0f;
            return;
        }
        this.clear();
    }

    protected Parcelable onSaveInstanceState() {
        super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putInt("com.getqardio.android.DIA_KEY", this.dia);
        bundle.putInt("com.getqardio.android.SYS_KEY", this.sys);
        return bundle;
    }

    public void setBP(int n, int n2) {
        this.dia = n;
        this.sys = n2;
        this.pinXCache = -1.0f;
        this.pinYCache = -1.0f;
        this.invalidate();
    }
}

