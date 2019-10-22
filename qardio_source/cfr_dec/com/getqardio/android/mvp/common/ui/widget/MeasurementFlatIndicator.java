/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.util.AttributeSet
 *  android.view.View
 */
package com.getqardio.android.mvp.common.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.getqardio.android.R;

public class MeasurementFlatIndicator
extends View {
    private static final int[] DIA_VALUES = new int[]{120, 110, 100, 90, 85, 80, 40};
    private static final int[] SYS_VALUES = new int[]{190, 180, 160, 140, 130, 120, 70};
    private int[] colors;
    private Paint highlightedSectorPaint;
    private Paint sectorPaint;
    private int sectorToHighlight = -1;

    public MeasurementFlatIndicator(Context context) {
        super(context);
        this.init();
    }

    public MeasurementFlatIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.readXmlAttrs(attributeSet);
        this.init();
    }

    public MeasurementFlatIndicator(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.readXmlAttrs(attributeSet);
        this.init();
    }

    private void drawRoundedRect(Canvas canvas, float f, float f2, float f3, float f4, Paint paint) {
        float f5 = (f4 - f2) / 2.0f;
        if (this.sectorToHighlight == 0) {
            canvas.drawRect(f5, f2, f3, f4, paint);
            canvas.drawCircle(f5, f5, f5, paint);
            canvas.drawCircle(f3, f5, f5, paint);
            return;
        }
        if (this.sectorToHighlight == this.colors.length - 1) {
            canvas.drawRect(f, f2, (float)this.getWidth() - f5, f4, paint);
            canvas.drawCircle(f, f5, f5, paint);
            canvas.drawCircle((float)this.getWidth() - f5, f5, f5, paint);
            return;
        }
        canvas.drawRect(f, f2, f3, f4, paint);
        canvas.drawCircle(f, f5, f5, paint);
        canvas.drawCircle(f3, f5, f5, paint);
    }

    private void init() {
        this.sectorPaint = new Paint();
        this.sectorPaint.setStyle(Paint.Style.FILL);
        this.sectorPaint.setAntiAlias(true);
        this.highlightedSectorPaint = new Paint();
        this.highlightedSectorPaint.setStyle(Paint.Style.FILL);
        this.highlightedSectorPaint.setAntiAlias(true);
    }

    private void readXmlAttrs(AttributeSet attributeSet) {
        int n = this.getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.MeasurementFlatIndicator, 0, 0).getResourceId(0, 0);
        if (n == 0) {
            throw new RuntimeException("you should provide sectorColors array");
        }
        this.colors = this.getResources().getIntArray(n);
    }

    public int calculateSectorForBmi(float f) {
        if ((double)f < 18.5) {
            return 0;
        }
        if (f < 25.0f) {
            return 1;
        }
        if (f < 30.0f) {
            return 2;
        }
        return 3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int calculateSectorForBp(int n, int n2) {
        int n3 = 0;
        if (n >= DIA_VALUES[0]) return this.colors.length - 1;
        if (n2 >= SYS_VALUES[0]) {
            return this.colors.length - 1;
        }
        if (n <= DIA_VALUES[DIA_VALUES.length - 1]) {
            if (n2 <= SYS_VALUES[SYS_VALUES.length - 1]) return n3;
        }
        int n4 = 0;
        int n5 = 0;
        int n6 = 1;
        do {
            n3 = n4;
            if (n6 >= DIA_VALUES.length) break;
            if (n >= DIA_VALUES[n6]) {
                n3 = DIA_VALUES.length - 1 - n6;
                break;
            }
            ++n6;
        } while (true);
        n = 1;
        do {
            n6 = n5;
            if (n >= SYS_VALUES.length) return Math.max(n3, n6);
            if (n2 >= SYS_VALUES[n]) {
                n6 = SYS_VALUES.length - 1 - n;
                return Math.max(n3, n6);
            }
            ++n;
        } while (true);
    }

    public void highlightSector(int n) {
        if (n < -1 || n > this.colors.length - 1) {
            throw new IllegalArgumentException(String.format("Wrong sector. Must be between %d and %d", -1, this.colors.length - 1));
        }
        this.sectorToHighlight = n;
        this.invalidate();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int n = this.colors.length;
        float f = this.getWidth() / n;
        float f2 = (float)((double)this.getHeight() / 4.0);
        float f3 = (float)this.getHeight() - f2;
        float f4 = (f3 - f2) / 2.0f;
        for (int i = 0; i < n; ++i) {
            int n2 = this.colors[i];
            this.sectorPaint.setColor(n2);
            if (i == 0) {
                canvas.drawRect((float)i * f + f4, f2, f * (float)(i + 1), f3, this.sectorPaint);
                canvas.drawCircle(f4, (float)(this.getHeight() / 2), f4, this.sectorPaint);
                continue;
            }
            if (i == n - 1) {
                canvas.drawRect(f * (float)i, f2, (float)(i + 1) * f - f4, f3, this.sectorPaint);
                canvas.drawCircle((float)this.getWidth() - f4, (float)(this.getHeight() / 2), f4, this.sectorPaint);
                continue;
            }
            canvas.drawRect(f * (float)i, f2, f * (float)(i + 1), f3, this.sectorPaint);
        }
        if (this.sectorToHighlight >= 0) {
            this.highlightedSectorPaint.setColor(this.colors[this.sectorToHighlight]);
            this.drawRoundedRect(canvas, f * (float)this.sectorToHighlight, 0.0f, f * (float)(this.sectorToHighlight + 1), this.getHeight(), this.highlightedSectorPaint);
        }
    }
}

