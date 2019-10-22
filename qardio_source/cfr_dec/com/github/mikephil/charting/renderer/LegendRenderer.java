/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.DashPathEffect
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$FontMetrics
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.PathEffect
 *  android.graphics.Typeface
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Typeface;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.renderer.Renderer;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LegendRenderer
extends Renderer {
    protected List<LegendEntry> computedEntries = new ArrayList<LegendEntry>(16);
    protected Paint.FontMetrics legendFontMetrics = new Paint.FontMetrics();
    protected Legend mLegend;
    protected Paint mLegendFormPaint;
    protected Paint mLegendLabelPaint;
    private Path mLineFormPath = new Path();

    public LegendRenderer(ViewPortHandler viewPortHandler, Legend legend) {
        super(viewPortHandler);
        this.mLegend = legend;
        this.mLegendLabelPaint = new Paint(1);
        this.mLegendLabelPaint.setTextSize(Utils.convertDpToPixel(9.0f));
        this.mLegendLabelPaint.setTextAlign(Paint.Align.LEFT);
        this.mLegendFormPaint = new Paint(1);
        this.mLegendFormPaint.setStyle(Paint.Style.FILL);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void computeLegend(ChartData<?> var1_1) {
        if (this.mLegend.isLegendCustom()) ** GOTO lbl44
        this.computedEntries.clear();
        var2_2 = 0;
        block0: do {
            if (var2_2 >= var1_1.getDataSetCount()) ** GOTO lbl40
            var6_6 = var1_1.getDataSetByIndex(var2_2);
            var7_7 = var6_6.getColors();
            var4_4 = var6_6.getEntryCount();
            if (var6_6 instanceof IBarDataSet && ((IBarDataSet)var6_6).isStacked()) {
                var5_5 = (IBarDataSet)var6_6;
                var8_8 = var5_5.getStackLabels();
                for (var3_3 = 0; var3_3 < var7_7.size() && var3_3 < var5_5.getStackSize(); ++var3_3) {
                    this.computedEntries.add(new LegendEntry(var8_8[var3_3 % var8_8.length], var6_6.getForm(), var6_6.getFormSize(), var6_6.getFormLineWidth(), var6_6.getFormLineDashEffect(), var7_7.get(var3_3)));
                }
                if (var5_5.getLabel() != null) {
                    this.computedEntries.add(new LegendEntry(var6_6.getLabel(), Legend.LegendForm.NONE, Float.NaN, Float.NaN, null, 1122867));
                }
            } else if (var6_6 instanceof IPieDataSet) {
                var5_5 = (IPieDataSet)var6_6;
                for (var3_3 = 0; var3_3 < var7_7.size() && var3_3 < var4_4; ++var3_3) {
                    this.computedEntries.add(new LegendEntry(var5_5.getEntryForIndex(var3_3).getLabel(), var6_6.getForm(), var6_6.getFormSize(), var6_6.getFormLineWidth(), var6_6.getFormLineDashEffect(), var7_7.get(var3_3)));
                }
                if (var5_5.getLabel() != null) {
                    this.computedEntries.add(new LegendEntry(var6_6.getLabel(), Legend.LegendForm.NONE, Float.NaN, Float.NaN, null, 1122867));
                }
            } else if (var6_6 instanceof ICandleDataSet && ((ICandleDataSet)var6_6).getDecreasingColor() != 1122867) {
                var3_3 = ((ICandleDataSet)var6_6).getDecreasingColor();
                var4_4 = ((ICandleDataSet)var6_6).getIncreasingColor();
                this.computedEntries.add(new LegendEntry(null, var6_6.getForm(), var6_6.getFormSize(), var6_6.getFormLineWidth(), var6_6.getFormLineDashEffect(), var3_3));
                this.computedEntries.add(new LegendEntry(var6_6.getLabel(), var6_6.getForm(), var6_6.getFormSize(), var6_6.getFormLineWidth(), var6_6.getFormLineDashEffect(), var4_4));
            } else {
                var3_3 = 0;
                break;
lbl40:
                // 1 sources
                if (this.mLegend.getExtraEntries() != null) {
                    Collections.addAll(this.computedEntries, this.mLegend.getExtraEntries());
                }
                this.mLegend.setEntries(this.computedEntries);
lbl44:
                // 2 sources
                if ((var1_1 = this.mLegend.getTypeface()) != null) {
                    this.mLegendLabelPaint.setTypeface(var1_1);
                }
                this.mLegendLabelPaint.setTextSize(this.mLegend.getTextSize());
                this.mLegendLabelPaint.setColor(this.mLegend.getTextColor());
                this.mLegend.calculateDimensions(this.mLegendLabelPaint, this.mViewPortHandler);
                return;
            }
            do {
                ++var2_2;
                continue block0;
                break;
            } while (true);
            break;
        } while (true);
        do {
            if (var3_3 >= var7_7.size() || var3_3 >= var4_4) ** continue;
            var5_5 = var3_3 < var7_7.size() - 1 && var3_3 < var4_4 - 1 ? null : var1_1.getDataSetByIndex(var2_2).getLabel();
            this.computedEntries.add(new LegendEntry((String)var5_5, var6_6.getForm(), var6_6.getFormSize(), var6_6.getFormLineWidth(), var6_6.getFormLineDashEffect(), var7_7.get(var3_3)));
            ++var3_3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void drawForm(Canvas canvas, float f, float f2, LegendEntry legendEntry, Legend legend) {
        Legend.LegendForm legendForm;
        if (legendEntry.formColor == 1122868 || legendEntry.formColor == 1122867 || legendEntry.formColor == 0) {
            return;
        }
        int n = canvas.save();
        Legend.LegendForm legendForm2 = legendForm = legendEntry.form;
        if (legendForm == Legend.LegendForm.DEFAULT) {
            legendForm2 = legend.getForm();
        }
        this.mLegendFormPaint.setColor(legendEntry.formColor);
        float f3 = Float.isNaN(legendEntry.formSize) ? legend.getFormSize() : legendEntry.formSize;
        float f4 = Utils.convertDpToPixel(f3);
        f3 = f4 / 2.0f;
        switch (legendForm2) {
            case DEFAULT: 
            case CIRCLE: {
                this.mLegendFormPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(f + f3, f2, f3, this.mLegendFormPaint);
                break;
            }
            case SQUARE: {
                this.mLegendFormPaint.setStyle(Paint.Style.FILL);
                canvas.drawRect(f, f2 - f3, f + f4, f2 + f3, this.mLegendFormPaint);
                break;
            }
            case LINE: {
                f3 = Float.isNaN(legendEntry.formLineWidth) ? legend.getFormLineWidth() : legendEntry.formLineWidth;
                f3 = Utils.convertDpToPixel(f3);
                legendEntry = legendEntry.formLineDashEffect == null ? legend.getFormLineDashEffect() : legendEntry.formLineDashEffect;
                this.mLegendFormPaint.setStyle(Paint.Style.STROKE);
                this.mLegendFormPaint.setStrokeWidth(f3);
                this.mLegendFormPaint.setPathEffect((PathEffect)legendEntry);
                this.mLineFormPath.reset();
                this.mLineFormPath.moveTo(f, f2);
                this.mLineFormPath.lineTo(f + f4, f2);
                canvas.drawPath(this.mLineFormPath, this.mLegendFormPaint);
                break;
            }
        }
        canvas.restoreToCount(n);
    }

    protected void drawLabel(Canvas canvas, float f, float f2, String string2) {
        canvas.drawText(string2, f, f2, this.mLegendLabelPaint);
    }

    public Paint getLabelPaint() {
        return this.mLegendLabelPaint;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void renderLegend(Canvas canvas) {
        if (!this.mLegend.isEnabled()) return;
        {
            float f;
            LegendEntry[] arrlegendEntry = this.mLegend.getTypeface();
            if (arrlegendEntry != null) {
                this.mLegendLabelPaint.setTypeface((Typeface)arrlegendEntry);
            }
            this.mLegendLabelPaint.setTextSize(this.mLegend.getTextSize());
            this.mLegendLabelPaint.setColor(this.mLegend.getTextColor());
            float f2 = Utils.getLineHeight(this.mLegendLabelPaint, this.legendFontMetrics);
            float f3 = Utils.getLineSpacing(this.mLegendLabelPaint, this.legendFontMetrics) + Utils.convertDpToPixel(this.mLegend.getYEntrySpace());
            float f4 = f2 - (float)Utils.calcTextHeight(this.mLegendLabelPaint, "ABC") / 2.0f;
            arrlegendEntry = this.mLegend.getEntries();
            float f5 = Utils.convertDpToPixel(this.mLegend.getFormToTextSpace());
            float f6 = Utils.convertDpToPixel(this.mLegend.getXEntrySpace());
            Object object = this.mLegend.getOrientation();
            Object object2 = this.mLegend.getHorizontalAlignment();
            Object object3 = this.mLegend.getVerticalAlignment();
            Legend.LegendDirection legendDirection = this.mLegend.getDirection();
            float f7 = Utils.convertDpToPixel(this.mLegend.getFormSize());
            float f8 = Utils.convertDpToPixel(this.mLegend.getStackSpace());
            float f9 = this.mLegend.getYOffset();
            float f10 = this.mLegend.getXOffset();
            float f11 = 0.0f;
            switch (1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[object2.ordinal()]) {
                case 1: {
                    if (object != Legend.LegendOrientation.VERTICAL) {
                        f10 = this.mViewPortHandler.contentLeft() + f10;
                    }
                    f11 = f10;
                    if (legendDirection != Legend.LegendDirection.RIGHT_TO_LEFT) break;
                    f11 = f10 + this.mLegend.mNeededWidth;
                    break;
                }
                case 2: {
                    f10 = object == Legend.LegendOrientation.VERTICAL ? this.mViewPortHandler.getChartWidth() - f10 : this.mViewPortHandler.contentRight() - f10;
                    f11 = f10;
                    if (legendDirection != Legend.LegendDirection.LEFT_TO_RIGHT) break;
                    f11 = f10 - this.mLegend.mNeededWidth;
                    break;
                }
                case 3: {
                    f11 = object == Legend.LegendOrientation.VERTICAL ? this.mViewPortHandler.getChartWidth() / 2.0f : this.mViewPortHandler.contentLeft() + this.mViewPortHandler.contentWidth() / 2.0f;
                    f = legendDirection == Legend.LegendDirection.LEFT_TO_RIGHT ? f10 : -f10;
                    f11 = f = f11 + f;
                    if (object != Legend.LegendOrientation.VERTICAL) break;
                    double d = f;
                    double d2 = legendDirection == Legend.LegendDirection.LEFT_TO_RIGHT ? (double)(-this.mLegend.mNeededWidth) / 2.0 + (double)f10 : (double)this.mLegend.mNeededWidth / 2.0 - (double)f10;
                    f11 = (float)(d2 + d);
                    break;
                }
            }
            switch (1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[((Enum)object).ordinal()]) {
                default: {
                    return;
                }
                case 1: {
                    object = this.mLegend.getCalculatedLineSizes();
                    List<FSize> list = this.mLegend.getCalculatedLabelSizes();
                    List<Boolean> list2 = this.mLegend.getCalculatedLabelBreakPoints();
                    f = f11;
                    f10 = 0.0f;
                    switch (object3) {
                        case TOP: {
                            f10 = f9;
                            break;
                        }
                        case BOTTOM: {
                            f10 = this.mViewPortHandler.getChartHeight() - f9 - this.mLegend.mNeededHeight;
                            break;
                        }
                        case CENTER: {
                            f10 = (this.mViewPortHandler.getChartHeight() - this.mLegend.mNeededHeight) / 2.0f + f9;
                            break;
                        }
                    }
                    int n = 0;
                    int n2 = arrlegendEntry.length;
                    f9 = f10;
                    for (int i = 0; i < n2; ++i) {
                        object3 = arrlegendEntry[i];
                        boolean bl = ((LegendEntry)object3).form != Legend.LegendForm.NONE;
                        float f12 = Float.isNaN(((LegendEntry)object3).formSize) ? f7 : Utils.convertDpToPixel(((LegendEntry)object3).formSize);
                        f10 = f;
                        float f13 = f9;
                        if (i < list2.size()) {
                            f10 = f;
                            f13 = f9;
                            if (list2.get(i).booleanValue()) {
                                f10 = f11;
                                f13 = f9 + (f2 + f3);
                            }
                        }
                        f = f10;
                        int n3 = n;
                        if (f10 == f11) {
                            f = f10;
                            n3 = n;
                            if (object2 == Legend.LegendHorizontalAlignment.CENTER) {
                                f = f10;
                                n3 = n;
                                if (n < object.size()) {
                                    f = legendDirection == Legend.LegendDirection.RIGHT_TO_LEFT ? ((FSize)object.get((int)n)).width : -((FSize)object.get((int)n)).width;
                                    f = f10 + f / 2.0f;
                                    n3 = n + 1;
                                }
                            }
                        }
                        n = ((LegendEntry)object3).label == null ? 1 : 0;
                        f10 = f;
                        if (bl) {
                            f9 = f;
                            if (legendDirection == Legend.LegendDirection.RIGHT_TO_LEFT) {
                                f9 = f - f12;
                            }
                            this.drawForm(canvas, f9, f13 + f4, (LegendEntry)object3, this.mLegend);
                            f10 = f9;
                            if (legendDirection == Legend.LegendDirection.LEFT_TO_RIGHT) {
                                f10 = f9 + f12;
                            }
                        }
                        if (n == 0) {
                            f = f10;
                            if (bl) {
                                f = legendDirection == Legend.LegendDirection.RIGHT_TO_LEFT ? -f5 : f5;
                                f = f10 + f;
                            }
                            f10 = f;
                            if (legendDirection == Legend.LegendDirection.RIGHT_TO_LEFT) {
                                f10 = f - list.get((int)i).width;
                            }
                            this.drawLabel(canvas, f10, f13 + f2, ((LegendEntry)object3).label);
                            f = f10;
                            if (legendDirection == Legend.LegendDirection.LEFT_TO_RIGHT) {
                                f = f10 + list.get((int)i).width;
                            }
                            f10 = legendDirection == Legend.LegendDirection.RIGHT_TO_LEFT ? -f6 : f6;
                            f += f10;
                        } else {
                            f = legendDirection == Legend.LegendDirection.RIGHT_TO_LEFT ? -f8 : f8;
                            f = f10 + f;
                        }
                        n = n3;
                        f9 = f13;
                    }
                    return;
                }
                case 2: {
                    float f14 = 0.0f;
                    boolean bl = false;
                    f10 = 0.0f;
                    switch (object3) {
                        case TOP: {
                            f10 = object2 == Legend.LegendHorizontalAlignment.CENTER ? 0.0f : this.mViewPortHandler.contentTop();
                            f10 += f9;
                            break;
                        }
                        case BOTTOM: {
                            f10 = object2 == Legend.LegendHorizontalAlignment.CENTER ? this.mViewPortHandler.getChartHeight() : this.mViewPortHandler.contentBottom();
                            f10 -= this.mLegend.mNeededHeight + f9;
                            break;
                        }
                        case CENTER: {
                            f10 = this.mViewPortHandler.getChartHeight() / 2.0f - this.mLegend.mNeededHeight / 2.0f + this.mLegend.getYOffset();
                            break;
                        }
                    }
                    f = f10;
                    for (int i = 0; i < arrlegendEntry.length; ++i) {
                        object2 = arrlegendEntry[i];
                        boolean bl2 = ((LegendEntry)object2).form != Legend.LegendForm.NONE;
                        float f15 = Float.isNaN(((LegendEntry)object2).formSize) ? f7 : Utils.convertDpToPixel(((LegendEntry)object2).formSize);
                        f10 = f9 = f11;
                        if (bl2) {
                            f9 = legendDirection == Legend.LegendDirection.LEFT_TO_RIGHT ? (f9 += f14) : (f9 -= f15 - f14);
                            this.drawForm(canvas, f9, f + f4, (LegendEntry)object2, this.mLegend);
                            f10 = f9;
                            if (legendDirection == Legend.LegendDirection.LEFT_TO_RIGHT) {
                                f10 = f9 + f15;
                            }
                        }
                        if (((LegendEntry)object2).label != null) {
                            if (bl2 && !bl) {
                                f9 = legendDirection == Legend.LegendDirection.LEFT_TO_RIGHT ? f5 : -f5;
                                f10 += f9;
                            } else if (bl) {
                                f10 = f11;
                            }
                            f9 = f10;
                            if (legendDirection == Legend.LegendDirection.RIGHT_TO_LEFT) {
                                f9 = f10 - (float)Utils.calcTextWidth(this.mLegendLabelPaint, ((LegendEntry)object2).label);
                            }
                            if (!bl) {
                                this.drawLabel(canvas, f9, f + f2, ((LegendEntry)object2).label);
                            } else {
                                this.drawLabel(canvas, f9, (f += f2 + f3) + f2, ((LegendEntry)object2).label);
                            }
                            f += f2 + f3;
                            f10 = 0.0f;
                        } else {
                            f10 = f14 + (f15 + f8);
                            bl = true;
                        }
                        f14 = f10;
                    }
                }
            }
        }
    }

}

