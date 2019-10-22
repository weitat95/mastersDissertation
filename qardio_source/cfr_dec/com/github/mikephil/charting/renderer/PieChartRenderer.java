/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.RectF
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Layout
 *  android.text.Layout$Alignment
 *  android.text.StaticLayout
 *  android.text.TextPaint
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.WeakReference;
import java.util.List;

public class PieChartRenderer
extends DataRenderer {
    protected Canvas mBitmapCanvas;
    private RectF mCenterTextLastBounds = new RectF();
    private CharSequence mCenterTextLastValue;
    private StaticLayout mCenterTextLayout;
    private TextPaint mCenterTextPaint;
    protected PieChart mChart;
    protected WeakReference<Bitmap> mDrawBitmap;
    protected Path mDrawCenterTextPathBuffer;
    protected RectF mDrawHighlightedRectF;
    private Paint mEntryLabelsPaint;
    private Path mHoleCirclePath;
    protected Paint mHolePaint;
    private RectF mInnerRectBuffer;
    private Path mPathBuffer;
    private RectF[] mRectBuffer = new RectF[]{new RectF(), new RectF(), new RectF()};
    protected Paint mTransparentCirclePaint;
    protected Paint mValueLinePaint;

    public PieChartRenderer(PieChart pieChart, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mPathBuffer = new Path();
        this.mInnerRectBuffer = new RectF();
        this.mHoleCirclePath = new Path();
        this.mDrawCenterTextPathBuffer = new Path();
        this.mDrawHighlightedRectF = new RectF();
        this.mChart = pieChart;
        this.mHolePaint = new Paint(1);
        this.mHolePaint.setColor(-1);
        this.mHolePaint.setStyle(Paint.Style.FILL);
        this.mTransparentCirclePaint = new Paint(1);
        this.mTransparentCirclePaint.setColor(-1);
        this.mTransparentCirclePaint.setStyle(Paint.Style.FILL);
        this.mTransparentCirclePaint.setAlpha(105);
        this.mCenterTextPaint = new TextPaint(1);
        this.mCenterTextPaint.setColor(-16777216);
        this.mCenterTextPaint.setTextSize(Utils.convertDpToPixel(12.0f));
        this.mValuePaint.setTextSize(Utils.convertDpToPixel(13.0f));
        this.mValuePaint.setColor(-1);
        this.mValuePaint.setTextAlign(Paint.Align.CENTER);
        this.mEntryLabelsPaint = new Paint(1);
        this.mEntryLabelsPaint.setColor(-1);
        this.mEntryLabelsPaint.setTextAlign(Paint.Align.CENTER);
        this.mEntryLabelsPaint.setTextSize(Utils.convertDpToPixel(13.0f));
        this.mValueLinePaint = new Paint(1);
        this.mValueLinePaint.setStyle(Paint.Style.STROKE);
    }

    protected float calculateMinimumRadiusForSpacedSlice(MPPointF mPPointF, float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f5 + f6 / 2.0f;
        float f8 = mPPointF.x + (float)Math.cos((f5 + f6) * 0.017453292f) * f;
        f5 = mPPointF.y + (float)Math.sin((f5 + f6) * 0.017453292f) * f;
        f6 = mPPointF.x;
        float f9 = (float)Math.cos(0.017453292f * f7);
        float f10 = mPPointF.y;
        f7 = (float)Math.sin(0.017453292f * f7);
        return (float)((double)(f - (float)(Math.sqrt(Math.pow(f8 - f3, 2.0) + Math.pow(f5 - f4, 2.0)) / 2.0 * Math.tan((180.0 - (double)f2) / 2.0 * 0.017453292519943295))) - Math.sqrt(Math.pow(f6 + f9 * f - (f8 + f3) / 2.0f, 2.0) + Math.pow(f10 + f7 * f - (f5 + f4) / 2.0f, 2.0)));
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void drawCenterText(Canvas canvas) {
        CharSequence charSequence = this.mChart.getCenterText();
        if (this.mChart.isDrawCenterTextEnabled() && charSequence != null) {
            MPPointF mPPointF = this.mChart.getCenterCircleBox();
            MPPointF mPPointF2 = this.mChart.getCenterTextOffset();
            float f = mPPointF.x + mPPointF2.x;
            float f2 = mPPointF.y + mPPointF2.y;
            float f3 = this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled() ? this.mChart.getRadius() * (this.mChart.getHoleRadius() / 100.0f) : this.mChart.getRadius();
            RectF rectF = this.mRectBuffer[0];
            rectF.left = f - f3;
            rectF.top = f2 - f3;
            rectF.right = f + f3;
            rectF.bottom = f2 + f3;
            RectF rectF2 = this.mRectBuffer[1];
            rectF2.set(rectF);
            f3 = this.mChart.getCenterTextRadiusPercent() / 100.0f;
            if ((double)f3 > 0.0) {
                rectF2.inset((rectF2.width() - rectF2.width() * f3) / 2.0f, (rectF2.height() - rectF2.height() * f3) / 2.0f);
            }
            if (!charSequence.equals(this.mCenterTextLastValue) || !rectF2.equals((Object)this.mCenterTextLastBounds)) {
                this.mCenterTextLastBounds.set(rectF2);
                this.mCenterTextLastValue = charSequence;
                f3 = this.mCenterTextLastBounds.width();
                this.mCenterTextLayout = new StaticLayout(charSequence, 0, charSequence.length(), this.mCenterTextPaint, (int)Math.max(Math.ceil(f3), 1.0), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
            }
            f3 = this.mCenterTextLayout.getHeight();
            canvas.save();
            if (Build.VERSION.SDK_INT >= 18) {
                charSequence = this.mDrawCenterTextPathBuffer;
                charSequence.reset();
                charSequence.addOval(rectF, Path.Direction.CW);
                canvas.clipPath((Path)charSequence);
            }
            canvas.translate(rectF2.left, rectF2.top + (rectF2.height() - f3) / 2.0f);
            this.mCenterTextLayout.draw(canvas);
            canvas.restore();
            MPPointF.recycleInstance(mPPointF);
            MPPointF.recycleInstance(mPPointF2);
        }
    }

    @Override
    public void drawData(Canvas canvas) {
        block4: {
            block3: {
                int n = (int)this.mViewPortHandler.getChartWidth();
                int n2 = (int)this.mViewPortHandler.getChartHeight();
                if (this.mDrawBitmap != null && ((Bitmap)this.mDrawBitmap.get()).getWidth() == n && ((Bitmap)this.mDrawBitmap.get()).getHeight() == n2) break block3;
                if (n <= 0 || n2 <= 0) break block4;
                this.mDrawBitmap = new WeakReference<Bitmap>(Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)Bitmap.Config.ARGB_4444));
                this.mBitmapCanvas = new Canvas((Bitmap)this.mDrawBitmap.get());
            }
            ((Bitmap)this.mDrawBitmap.get()).eraseColor(0);
            for (IPieDataSet iPieDataSet : ((PieData)this.mChart.getData()).getDataSets()) {
                if (!iPieDataSet.isVisible() || iPieDataSet.getEntryCount() <= 0) continue;
                this.drawDataSet(canvas, iPieDataSet);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void drawDataSet(Canvas canvas, IPieDataSet iPieDataSet) {
        int n;
        int n2;
        float f = 0.0f;
        float f2 = this.mChart.getRotationAngle();
        float f3 = this.mAnimator.getPhaseX();
        float f4 = this.mAnimator.getPhaseY();
        canvas = this.mChart.getCircleBox();
        int n3 = iPieDataSet.getEntryCount();
        float[] arrf = this.mChart.getDrawAngles();
        MPPointF mPPointF = this.mChart.getCenterCircleBox();
        float f5 = this.mChart.getRadius();
        boolean bl = this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled();
        float f6 = bl ? f5 * (this.mChart.getHoleRadius() / 100.0f) : 0.0f;
        int n4 = 0;
        for (n2 = 0; n2 < n3; ++n2) {
            n = n4;
            if (Math.abs(((PieEntry)iPieDataSet.getEntryForIndex(n2)).getY()) > Utils.FLOAT_EPSILON) {
                n = n4 + 1;
            }
            n4 = n;
        }
        float f7 = n4 <= 1 ? 0.0f : this.getSliceSpace(iPieDataSet);
        n2 = 0;
        do {
            if (n2 >= n3) {
                MPPointF.recycleInstance(mPPointF);
                return;
            }
            float f8 = arrf[n2];
            float f9 = f6;
            if (Math.abs(((BaseEntry)iPieDataSet.getEntryForIndex(n2)).getY()) > Utils.FLOAT_EPSILON && !this.mChart.needsHighlight(n2)) {
                float f10;
                n = f7 > 0.0f && f8 <= 180.0f ? 1 : 0;
                this.mRenderPaint.setColor(iPieDataSet.getColor(n2));
                float f11 = n4 == 1 ? 0.0f : f7 / (0.017453292f * f5);
                float f12 = f2 + (f11 / 2.0f + f) * f4;
                f11 = f10 = (f8 - f11) * f4;
                if (f10 < 0.0f) {
                    f11 = 0.0f;
                }
                this.mPathBuffer.reset();
                float f13 = mPPointF.x + (float)Math.cos(0.017453292f * f12) * f5;
                float f14 = mPPointF.y + (float)Math.sin(0.017453292f * f12) * f5;
                if (f11 >= 360.0f && f11 % 360.0f <= Utils.FLOAT_EPSILON) {
                    this.mPathBuffer.addCircle(mPPointF.x, mPPointF.y, f5, Path.Direction.CW);
                } else {
                    this.mPathBuffer.moveTo(f13, f14);
                    this.mPathBuffer.arcTo((RectF)canvas, f12, f11);
                }
                this.mInnerRectBuffer.set(mPPointF.x - f9, mPPointF.y - f9, mPPointF.x + f9, mPPointF.y + f9);
                if (bl && (f9 > 0.0f || n != 0)) {
                    f10 = f9;
                    if (n != 0) {
                        f10 = f12 = this.calculateMinimumRadiusForSpacedSlice(mPPointF, f5, f8 * f4, f13, f14, f12, f11);
                        if (f12 < 0.0f) {
                            f10 = -f12;
                        }
                        f10 = Math.max(f9, f10);
                    }
                    f9 = n4 == 1 || f10 == 0.0f ? 0.0f : f7 / (0.017453292f * f10);
                    f13 = f9 / 2.0f;
                    f9 = f12 = (f8 - f9) * f4;
                    if (f12 < 0.0f) {
                        f9 = 0.0f;
                    }
                    f12 = f2 + (f13 + f) * f4 + f9;
                    if (f11 >= 360.0f && f11 % 360.0f <= Utils.FLOAT_EPSILON) {
                        this.mPathBuffer.addCircle(mPPointF.x, mPPointF.y, f10, Path.Direction.CCW);
                    } else {
                        this.mPathBuffer.lineTo(mPPointF.x + (float)Math.cos(0.017453292f * f12) * f10, mPPointF.y + (float)Math.sin(0.017453292f * f12) * f10);
                        this.mPathBuffer.arcTo(this.mInnerRectBuffer, f12, -f9);
                    }
                } else if (f11 % 360.0f > Utils.FLOAT_EPSILON) {
                    if (n != 0) {
                        f9 = f12 + f11 / 2.0f;
                        f11 = this.calculateMinimumRadiusForSpacedSlice(mPPointF, f5, f8 * f4, f13, f14, f12, f11);
                        f10 = mPPointF.x;
                        f12 = (float)Math.cos(0.017453292f * f9);
                        f13 = mPPointF.y;
                        f9 = (float)Math.sin(0.017453292f * f9);
                        this.mPathBuffer.lineTo(f10 + f12 * f11, f13 + f9 * f11);
                    } else {
                        this.mPathBuffer.lineTo(mPPointF.x, mPPointF.y);
                    }
                }
                this.mPathBuffer.close();
                this.mBitmapCanvas.drawPath(this.mPathBuffer, this.mRenderPaint);
            }
            f += f8 * f3;
            ++n2;
        } while (true);
    }

    protected void drawEntryLabel(Canvas canvas, String string2, float f, float f2) {
        canvas.drawText(string2, f, f2, this.mEntryLabelsPaint);
    }

    @Override
    public void drawExtras(Canvas canvas) {
        this.drawHole(canvas);
        canvas.drawBitmap((Bitmap)this.mDrawBitmap.get(), 0.0f, 0.0f, null);
        this.drawCenterText(canvas);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void drawHighlighted(Canvas arrf, Highlight[] arrhighlight) {
        float f = this.mAnimator.getPhaseX();
        float f2 = this.mAnimator.getPhaseY();
        float f3 = this.mChart.getRotationAngle();
        arrf = this.mChart.getDrawAngles();
        float[] arrf2 = this.mChart.getAbsoluteAngles();
        MPPointF mPPointF = this.mChart.getCenterCircleBox();
        float f4 = this.mChart.getRadius();
        boolean bl = this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled();
        float f5 = bl ? f4 * (this.mChart.getHoleRadius() / 100.0f) : 0.0f;
        RectF rectF = this.mDrawHighlightedRectF;
        rectF.set(0.0f, 0.0f, 0.0f, 0.0f);
        int n = 0;
        do {
            IDataSet iDataSet;
            if (n >= arrhighlight.length) {
                MPPointF.recycleInstance(mPPointF);
                return;
            }
            int n2 = (int)arrhighlight[n].getX();
            if (n2 < arrf.length && (iDataSet = ((PieData)this.mChart.getData()).getDataSetByIndex(arrhighlight[n].getDataSetIndex())) != null && iDataSet.isHighlightEnabled()) {
                int n3;
                float f6;
                int n4 = iDataSet.getEntryCount();
                int n5 = 0;
                for (n3 = 0; n3 < n4; ++n3) {
                    int n6 = n5;
                    if (Math.abs(((PieEntry)iDataSet.getEntryForIndex(n3)).getY()) > Utils.FLOAT_EPSILON) {
                        n6 = n5 + 1;
                    }
                    n5 = n6;
                }
                float f7 = n2 == 0 ? 0.0f : arrf2[n2 - 1] * f;
                float f8 = n5 <= 1 ? 0.0f : iDataSet.getSliceSpace();
                float f9 = arrf[n2];
                float f10 = f5;
                float f11 = iDataSet.getSelectionShift();
                float f12 = f4 + f11;
                rectF.set(this.mChart.getCircleBox());
                rectF.inset(-f11, -f11);
                n3 = f8 > 0.0f && f9 <= 180.0f ? 1 : 0;
                this.mRenderPaint.setColor(iDataSet.getColor(n2));
                float f13 = n5 == 1 ? 0.0f : f8 / (0.017453292f * f4);
                f11 = n5 == 1 ? 0.0f : f8 / (0.017453292f * f12);
                float f14 = f3 + (f13 / 2.0f + f7) * f2;
                f13 = f6 = (f9 - f13) * f2;
                if (f6 < 0.0f) {
                    f13 = 0.0f;
                }
                float f15 = f3 + (f11 / 2.0f + f7) * f2;
                f11 = f6 = (f9 - f11) * f2;
                if (f6 < 0.0f) {
                    f11 = 0.0f;
                }
                this.mPathBuffer.reset();
                if (f13 >= 360.0f && f13 % 360.0f <= Utils.FLOAT_EPSILON) {
                    this.mPathBuffer.addCircle(mPPointF.x, mPPointF.y, f12, Path.Direction.CW);
                } else {
                    this.mPathBuffer.moveTo(mPPointF.x + (float)Math.cos(0.017453292f * f15) * f12, mPPointF.y + (float)Math.sin(0.017453292f * f15) * f12);
                    this.mPathBuffer.arcTo(rectF, f15, f11);
                }
                f11 = 0.0f;
                if (n3 != 0) {
                    f11 = mPPointF.x;
                    f6 = (float)Math.cos(0.017453292f * f14);
                    f12 = mPPointF.y;
                    f11 = this.calculateMinimumRadiusForSpacedSlice(mPPointF, f4, f9 * f2, f6 * f4 + f11, (float)Math.sin(0.017453292f * f14) * f4 + f12, f14, f13);
                }
                this.mInnerRectBuffer.set(mPPointF.x - f10, mPPointF.y - f10, mPPointF.x + f10, mPPointF.y + f10);
                if (bl && (f10 > 0.0f || n3 != 0)) {
                    f6 = f10;
                    if (n3 != 0) {
                        f6 = f11;
                        if (f11 < 0.0f) {
                            f6 = -f11;
                        }
                        f6 = Math.max(f10, f6);
                    }
                    f11 = n5 == 1 || f6 == 0.0f ? 0.0f : f8 / (0.017453292f * f6);
                    f10 = f11 / 2.0f;
                    f11 = f8 = (f9 - f11) * f2;
                    if (f8 < 0.0f) {
                        f11 = 0.0f;
                    }
                    f7 = f3 + (f10 + f7) * f2 + f11;
                    if (f13 >= 360.0f && f13 % 360.0f <= Utils.FLOAT_EPSILON) {
                        this.mPathBuffer.addCircle(mPPointF.x, mPPointF.y, f6, Path.Direction.CCW);
                    } else {
                        this.mPathBuffer.lineTo(mPPointF.x + (float)Math.cos(0.017453292f * f7) * f6, mPPointF.y + (float)Math.sin(0.017453292f * f7) * f6);
                        this.mPathBuffer.arcTo(this.mInnerRectBuffer, f7, -f11);
                    }
                } else if (f13 % 360.0f > Utils.FLOAT_EPSILON) {
                    if (n3 != 0) {
                        f10 = f14 + f13 / 2.0f;
                        f7 = mPPointF.x;
                        f13 = (float)Math.cos(0.017453292f * f10);
                        f8 = mPPointF.y;
                        f10 = (float)Math.sin(0.017453292f * f10);
                        this.mPathBuffer.lineTo(f7 + f13 * f11, f8 + f10 * f11);
                    } else {
                        this.mPathBuffer.lineTo(mPPointF.x, mPPointF.y);
                    }
                }
                this.mPathBuffer.close();
                this.mBitmapCanvas.drawPath(this.mPathBuffer, this.mRenderPaint);
            }
            ++n;
        } while (true);
    }

    protected void drawHole(Canvas object) {
        if (this.mChart.isDrawHoleEnabled() && this.mBitmapCanvas != null) {
            float f = this.mChart.getRadius();
            float f2 = f * (this.mChart.getHoleRadius() / 100.0f);
            object = this.mChart.getCenterCircleBox();
            if (Color.alpha((int)this.mHolePaint.getColor()) > 0) {
                this.mBitmapCanvas.drawCircle(object.x, object.y, f2, this.mHolePaint);
            }
            if (Color.alpha((int)this.mTransparentCirclePaint.getColor()) > 0 && this.mChart.getTransparentCircleRadius() > this.mChart.getHoleRadius()) {
                int n = this.mTransparentCirclePaint.getAlpha();
                float f3 = this.mChart.getTransparentCircleRadius() / 100.0f;
                this.mTransparentCirclePaint.setAlpha((int)((float)n * this.mAnimator.getPhaseX() * this.mAnimator.getPhaseY()));
                this.mHoleCirclePath.reset();
                this.mHoleCirclePath.addCircle(object.x, object.y, f * f3, Path.Direction.CW);
                this.mHoleCirclePath.addCircle(object.x, object.y, f2, Path.Direction.CCW);
                this.mBitmapCanvas.drawPath(this.mHoleCirclePath, this.mTransparentCirclePaint);
                this.mTransparentCirclePaint.setAlpha(n);
            }
            MPPointF.recycleInstance((MPPointF)object);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void drawValues(Canvas var1_1) {
        var33_2 = this.mChart.getCenterCircleBox();
        var8_3 = this.mChart.getRadius();
        var9_4 = this.mChart.getRotationAngle();
        var34_5 = this.mChart.getDrawAngles();
        var35_6 = this.mChart.getAbsoluteAngles();
        var10_7 = this.mAnimator.getPhaseX();
        var11_8 = this.mAnimator.getPhaseY();
        var12_9 = this.mChart.getHoleRadius() / 100.0f;
        var2_10 = var8_3 / 10.0f * 3.6f;
        if (this.mChart.isDrawHoleEnabled()) {
            var2_10 = (var8_3 - var8_3 * var12_9) / 2.0f;
        }
        var13_11 = var8_3 - var2_10;
        var36_12 = (PieData)this.mChart.getData();
        var37_13 = var36_12.getDataSets();
        var14_14 = var36_12.getYValueSum();
        var31_15 = this.mChart.isDrawEntryLabelsEnabled();
        var23_16 = 0;
        var1_1.save();
        var15_17 = Utils.convertDpToPixel(5.0f);
        var24_18 = 0;
        block0: do {
            if (var24_18 >= var37_13.size()) {
                MPPointF.recycleInstance(var33_2);
                var1_1.restore();
                return;
            }
            var38_38 = (IPieDataSet)var37_13.get(var24_18);
            var32_37 = var38_38.isDrawValuesEnabled();
            if (!var32_37 && !var31_15) {
                var26_32 = var23_16;
            } else {
                var39_39 = var38_38.getXValuePosition();
                var40_40 = var38_38.getYValuePosition();
                this.applyValueTextStyle(var38_38);
                var16_24 = (float)Utils.calcTextHeight(this.mValuePaint, "Q") + Utils.convertDpToPixel(4.0f);
                var41_41 = var38_38.getValueFormatter();
                var30_36 = var38_38.getEntryCount();
                this.mValueLinePaint.setColor(var38_38.getValueLineColor());
                this.mValueLinePaint.setStrokeWidth(Utils.convertDpToPixel(var38_38.getValueLineWidth()));
                var17_25 = this.getSliceSpace(var38_38);
                var25_31 = 0;
                break;
            }
            do {
                ++var24_18;
                var23_16 = var26_32;
                continue block0;
                break;
            } while (true);
            break;
        } while (true);
        do {
            var26_32 = var23_16;
            if (var25_31 >= var30_36) ** continue;
            var42_42 = (PieEntry)var38_38.getEntryForIndex(var25_31);
            var2_10 = var23_16 == 0 ? 0.0f : var35_6[var23_16 - 1] * var10_7;
            var5_21 = var9_4 + (var2_10 + (var34_5[var23_16] - var17_25 / (0.017453292f * var13_11) / 2.0f) / 2.0f) * var11_8;
            var2_10 = this.mChart.isUsePercentValuesEnabled() != false ? var42_42.getY() / var14_14 * 100.0f : var42_42.getY();
            var19_27 = (float)Math.cos(0.017453292f * var5_21);
            var18_26 = (float)Math.sin(0.017453292f * var5_21);
            var26_32 = var31_15 != false && var39_39 == PieDataSet.ValuePosition.OUTSIDE_SLICE ? 1 : 0;
            var27_33 = var32_37 != false && var40_40 == PieDataSet.ValuePosition.OUTSIDE_SLICE;
            var28_34 = var31_15 != false && var39_39 == PieDataSet.ValuePosition.INSIDE_SLICE;
            var29_35 = var32_37 != false && var40_40 == PieDataSet.ValuePosition.INSIDE_SLICE;
            if (var26_32 != 0 || var27_33) {
                var6_22 = var38_38.getValueLinePart1Length();
                var4_20 = var38_38.getValueLinePart2Length();
                var3_19 = var38_38.getValueLinePart1OffsetPercentage() / 100.0f;
                var3_19 = this.mChart.isDrawHoleEnabled() != false ? (var8_3 - var8_3 * var12_9) * var3_19 + var8_3 * var12_9 : var8_3 * var3_19;
                var4_20 = var38_38.isValueLineVariableLength() != false ? var13_11 * var4_20 * (float)Math.abs(Math.sin(0.017453292f * var5_21)) : var13_11 * var4_20;
                var20_28 = var33_2.x;
                var21_29 = var33_2.y;
                var22_30 = (1.0f + var6_22) * var13_11 * var19_27 + var33_2.x;
                var6_22 = (1.0f + var6_22) * var13_11 * var18_26 + var33_2.y;
                if ((double)var5_21 % 360.0 >= 90.0 && (double)var5_21 % 360.0 <= 270.0) {
                    var4_20 = var22_30 - var4_20;
                    this.mValuePaint.setTextAlign(Paint.Align.RIGHT);
                    if (var26_32 != 0) {
                        this.mEntryLabelsPaint.setTextAlign(Paint.Align.RIGHT);
                    }
                    var5_21 = var4_20 - var15_17;
                } else {
                    var4_20 = var22_30 + var4_20;
                    this.mValuePaint.setTextAlign(Paint.Align.LEFT);
                    if (var26_32 != 0) {
                        this.mEntryLabelsPaint.setTextAlign(Paint.Align.LEFT);
                    }
                    var5_21 = var4_20 + var15_17;
                }
                var7_23 = var6_22;
                if (var38_38.getValueLineColor() != 1122867) {
                    var1_1.drawLine(var3_19 * var19_27 + var20_28, var3_19 * var18_26 + var21_29, var22_30, var6_22, this.mValueLinePaint);
                    var1_1.drawLine(var22_30, var6_22, var4_20, var6_22, this.mValueLinePaint);
                }
                if (var26_32 != 0 && var27_33) {
                    this.drawValue(var1_1, var41_41, var2_10, var42_42, 0, var5_21, var7_23, var38_38.getValueTextColor(var25_31));
                    if (var25_31 < var36_12.getEntryCount() && var42_42.getLabel() != null) {
                        this.drawEntryLabel(var1_1, var42_42.getLabel(), var5_21, var7_23 + var16_24);
                    }
                } else if (var26_32 != 0) {
                    if (var25_31 < var36_12.getEntryCount() && var42_42.getLabel() != null) {
                        this.drawEntryLabel(var1_1, var42_42.getLabel(), var5_21, var16_24 / 2.0f + var7_23);
                    }
                } else if (var27_33) {
                    this.drawValue(var1_1, var41_41, var2_10, var42_42, 0, var5_21, var7_23 + var16_24 / 2.0f, var38_38.getValueTextColor(var25_31));
                }
            }
            if (var28_34 || var29_35) {
                var3_19 = var13_11 * var19_27 + var33_2.x;
                var4_20 = var13_11 * var18_26 + var33_2.y;
                this.mValuePaint.setTextAlign(Paint.Align.CENTER);
                if (var28_34 && var29_35) {
                    this.drawValue(var1_1, var41_41, var2_10, var42_42, 0, var3_19, var4_20, var38_38.getValueTextColor(var25_31));
                    if (var25_31 < var36_12.getEntryCount() && var42_42.getLabel() != null) {
                        this.drawEntryLabel(var1_1, var42_42.getLabel(), var3_19, var4_20 + var16_24);
                    }
                } else if (var28_34) {
                    if (var25_31 < var36_12.getEntryCount() && var42_42.getLabel() != null) {
                        this.drawEntryLabel(var1_1, var42_42.getLabel(), var3_19, var16_24 / 2.0f + var4_20);
                    }
                } else if (var29_35) {
                    this.drawValue(var1_1, var41_41, var2_10, var42_42, 0, var3_19, var4_20 + var16_24 / 2.0f, var38_38.getValueTextColor(var25_31));
                }
            }
            ++var23_16;
            ++var25_31;
        } while (true);
    }

    public TextPaint getPaintCenterText() {
        return this.mCenterTextPaint;
    }

    public Paint getPaintEntryLabels() {
        return this.mEntryLabelsPaint;
    }

    public Paint getPaintHole() {
        return this.mHolePaint;
    }

    public Paint getPaintTransparentCircle() {
        return this.mTransparentCirclePaint;
    }

    protected float getSliceSpace(IPieDataSet iPieDataSet) {
        if (!iPieDataSet.isAutomaticallyDisableSliceSpacingEnabled()) {
            return iPieDataSet.getSliceSpace();
        }
        if (iPieDataSet.getSliceSpace() / this.mViewPortHandler.getSmallestContentExtension() > iPieDataSet.getYMin() / ((PieData)this.mChart.getData()).getYValueSum() * 2.0f) {
            return 0.0f;
        }
        return iPieDataSet.getSliceSpace();
    }

    @Override
    public void initBuffers() {
    }

    public void releaseBitmap() {
        if (this.mBitmapCanvas != null) {
            this.mBitmapCanvas.setBitmap(null);
            this.mBitmapCanvas = null;
        }
        if (this.mDrawBitmap != null) {
            ((Bitmap)this.mDrawBitmap.get()).recycle();
            this.mDrawBitmap.clear();
            this.mDrawBitmap = null;
        }
    }
}

