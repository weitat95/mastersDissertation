/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.DashPathEffect
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.PathEffect
 *  android.graphics.drawable.Drawable
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;
import com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer;
import com.github.mikephil.charting.renderer.LineRadarRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

public class LineChartRenderer
extends LineRadarRenderer {
    protected Path cubicFillPath;
    protected Path cubicPath;
    protected Canvas mBitmapCanvas;
    protected Bitmap.Config mBitmapConfig = Bitmap.Config.ARGB_8888;
    protected LineDataProvider mChart;
    protected Paint mCirclePaintInner;
    private float[] mCirclesBuffer;
    protected WeakReference<Bitmap> mDrawBitmap;
    protected Path mGenerateFilledPathBuffer;
    private HashMap<IDataSet, DataSetImageCache> mImageCaches;
    private float[] mLineBuffer;

    public LineChartRenderer(LineDataProvider lineDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.cubicPath = new Path();
        this.cubicFillPath = new Path();
        this.mLineBuffer = new float[4];
        this.mGenerateFilledPathBuffer = new Path();
        this.mImageCaches = new HashMap();
        this.mCirclesBuffer = new float[2];
        this.mChart = lineDataProvider;
        this.mCirclePaintInner = new Paint(1);
        this.mCirclePaintInner.setStyle(Paint.Style.FILL);
        this.mCirclePaintInner.setColor(-1);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void generateFilledPath(ILineDataSet iLineDataSet, int n, int n2, Path path) {
        float f = iLineDataSet.getFillFormatter().getFillLinePosition(iLineDataSet, this.mChart);
        float f2 = this.mAnimator.getPhaseY();
        boolean bl = iLineDataSet.getMode() == LineDataSet.Mode.STEPPED;
        path.reset();
        Object t = iLineDataSet.getEntryForIndex(n);
        path.moveTo(((Entry)t).getX(), f);
        path.lineTo(((Entry)t).getX(), ((BaseEntry)t).getY() * f2);
        Entry entry = null;
        Object var8_9 = null;
        ++n;
        while (n <= n2) {
            void var8_10;
            entry = (Entry)iLineDataSet.getEntryForIndex(n);
            if (bl && var8_10 != null) {
                path.lineTo(entry.getX(), var8_10.getY() * f2);
            }
            path.lineTo(entry.getX(), entry.getY() * f2);
            Entry entry2 = entry;
            ++n;
        }
        if (entry != null) {
            path.lineTo(entry.getX(), f);
        }
        path.close();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void drawCircles(Canvas var1_1) {
        this.mRenderPaint.setStyle(Paint.Style.FILL);
        var2_2 = this.mAnimator.getPhaseY();
        this.mCirclesBuffer[0] = 0.0f;
        this.mCirclesBuffer[1] = 0.0f;
        var12_3 = this.mChart.getLineData().getDataSets();
        var5_4 = 0;
        block0: do {
            if (var5_4 >= var12_3.size()) return;
            var13_13 = (ILineDataSet)var12_3.get(var5_4);
            if (!var13_13.isVisible() || !var13_13.isDrawCirclesEnabled() || var13_13.getEntryCount() == 0) ** GOTO lbl31
            this.mCirclePaintInner.setColor(var13_13.getCircleHoleColor());
            var14_14 = this.mChart.getTransformer(var13_13.getAxisDependency());
            this.mXBounds.set(this.mChart, var13_13);
            var3_5 = var13_13.getCircleRadius();
            var4_6 = var13_13.getCircleHoleRadius();
            var9_10 = var13_13.isDrawCircleHoleEnabled() != false && var4_6 < var3_5 && var4_6 > 0.0f;
            var10_11 = var9_10 != false && var13_13.getCircleHoleColor() == 1122867;
            if (this.mImageCaches.containsKey(var13_13)) {
                var11_12 = this.mImageCaches.get(var13_13);
            } else {
                var11_12 = new DataSetImageCache();
                this.mImageCaches.put(var13_13, var11_12);
            }
            if (var11_12.init(var13_13)) {
                var11_12.fill(var13_13, var9_10, var10_11);
            }
            var7_8 = this.mXBounds.range;
            var8_9 = this.mXBounds.min;
            var6_7 = this.mXBounds.min;
            do {
                block10: {
                    if (var6_7 <= var7_8 + var8_9 && (var15_16 = var13_13.getEntryForIndex(var6_7)) != null) break block10;
lbl31:
                    // 3 sources
                    do {
                        ++var5_4;
                        continue block0;
                        break;
                    } while (true);
                }
                this.mCirclesBuffer[0] = var15_16.getX();
                this.mCirclesBuffer[1] = var15_16.getY() * var2_2;
                var14_14.pointValuesToPixel(this.mCirclesBuffer);
                if (!this.mViewPortHandler.isInBoundsRight(this.mCirclesBuffer[0])) ** continue;
                if (this.mViewPortHandler.isInBoundsLeft(this.mCirclesBuffer[0]) && this.mViewPortHandler.isInBoundsY(this.mCirclesBuffer[1]) && (var15_17 = var11_12.getBitmap(var6_7)) != null) {
                    var1_1.drawBitmap(var15_17, this.mCirclesBuffer[0] - var3_5, this.mCirclesBuffer[1] - var3_5, this.mRenderPaint);
                }
                ++var6_7;
            } while (true);
            break;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void drawCubicBezier(ILineDataSet iLineDataSet) {
        Math.max(0.0f, Math.min(1.0f, this.mAnimator.getPhaseX()));
        float f = this.mAnimator.getPhaseY();
        Transformer transformer = this.mChart.getTransformer(iLineDataSet.getAxisDependency());
        this.mXBounds.set(this.mChart, iLineDataSet);
        float f2 = iLineDataSet.getCubicIntensity();
        this.cubicPath.reset();
        if (this.mXBounds.range >= 1) {
            Object t;
            int n = this.mXBounds.min + 1;
            int n2 = this.mXBounds.min;
            n2 = this.mXBounds.range;
            Object t2 = iLineDataSet.getEntryForIndex(Math.max(n - 2, 0));
            Object t3 = t = iLineDataSet.getEntryForIndex(Math.max(n - 1, 0));
            n2 = -1;
            if (t == null) {
                return;
            }
            this.cubicPath.moveTo(((Entry)t).getX(), ((BaseEntry)t).getY() * f);
            n = this.mXBounds.min + 1;
            do {
                Object t4 = t2;
                if (n > this.mXBounds.range + this.mXBounds.min) break;
                t2 = t;
                t = n2 == n ? t3 : iLineDataSet.getEntryForIndex(n);
                n2 = n + 1 < iLineDataSet.getEntryCount() ? n + 1 : n;
                t3 = iLineDataSet.getEntryForIndex(n2);
                float f3 = ((Entry)t).getX();
                float f4 = ((Entry)t4).getX();
                float f5 = ((BaseEntry)t).getY();
                float f6 = ((BaseEntry)t4).getY();
                float f7 = ((Entry)t3).getX();
                float f8 = ((Entry)t2).getX();
                float f9 = ((BaseEntry)t3).getY();
                float f10 = ((BaseEntry)t2).getY();
                this.cubicPath.cubicTo(((Entry)t2).getX() + (f3 - f4) * f2, (((BaseEntry)t2).getY() + (f5 - f6) * f2) * f, ((Entry)t).getX() - (f7 - f8) * f2, (((BaseEntry)t).getY() - (f9 - f10) * f2) * f, ((Entry)t).getX(), ((BaseEntry)t).getY() * f);
                ++n;
            } while (true);
        }
        if (iLineDataSet.isDrawFilledEnabled()) {
            this.cubicFillPath.reset();
            this.cubicFillPath.addPath(this.cubicPath);
            this.drawCubicFill(this.mBitmapCanvas, iLineDataSet, this.cubicFillPath, transformer, this.mXBounds);
        }
        this.mRenderPaint.setColor(iLineDataSet.getColor());
        this.mRenderPaint.setStyle(Paint.Style.STROKE);
        transformer.pathValueToPixel(this.cubicPath);
        this.mBitmapCanvas.drawPath(this.cubicPath, this.mRenderPaint);
        this.mRenderPaint.setPathEffect(null);
    }

    protected void drawCubicFill(Canvas canvas, ILineDataSet iLineDataSet, Path path, Transformer transformer, BarLineScatterCandleBubbleRenderer.XBounds xBounds) {
        float f = iLineDataSet.getFillFormatter().getFillLinePosition(iLineDataSet, this.mChart);
        path.lineTo(((Entry)iLineDataSet.getEntryForIndex(xBounds.min + xBounds.range)).getX(), f);
        path.lineTo(((Entry)iLineDataSet.getEntryForIndex(xBounds.min)).getX(), f);
        path.close();
        transformer.pathValueToPixel(path);
        transformer = iLineDataSet.getFillDrawable();
        if (transformer != null) {
            this.drawFilledPath(canvas, path, (Drawable)transformer);
            return;
        }
        this.drawFilledPath(canvas, path, iLineDataSet.getFillColor(), iLineDataSet.getFillAlpha());
    }

    @Override
    public void drawData(Canvas canvas) {
        block4: {
            block3: {
                int n = (int)this.mViewPortHandler.getChartWidth();
                int n2 = (int)this.mViewPortHandler.getChartHeight();
                if (this.mDrawBitmap != null && ((Bitmap)this.mDrawBitmap.get()).getWidth() == n && ((Bitmap)this.mDrawBitmap.get()).getHeight() == n2) break block3;
                if (n <= 0 || n2 <= 0) break block4;
                this.mDrawBitmap = new WeakReference<Bitmap>(Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)this.mBitmapConfig));
                this.mBitmapCanvas = new Canvas((Bitmap)this.mDrawBitmap.get());
            }
            ((Bitmap)this.mDrawBitmap.get()).eraseColor(0);
            for (ILineDataSet iLineDataSet : this.mChart.getLineData().getDataSets()) {
                if (!iLineDataSet.isVisible()) continue;
                this.drawDataSet(canvas, iLineDataSet);
            }
            canvas.drawBitmap((Bitmap)this.mDrawBitmap.get(), 0.0f, 0.0f, this.mRenderPaint);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void drawDataSet(Canvas canvas, ILineDataSet iLineDataSet) {
        if (iLineDataSet.getEntryCount() < 1) {
            return;
        }
        this.mRenderPaint.setStrokeWidth(iLineDataSet.getLineWidth());
        this.mRenderPaint.setPathEffect((PathEffect)iLineDataSet.getDashPathEffect());
        switch (1.$SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode[iLineDataSet.getMode().ordinal()]) {
            default: {
                this.drawLinear(canvas, iLineDataSet);
                break;
            }
            case 3: {
                this.drawCubicBezier(iLineDataSet);
                break;
            }
            case 4: {
                this.drawHorizontalBezier(iLineDataSet);
            }
        }
        this.mRenderPaint.setPathEffect(null);
    }

    @Override
    public void drawExtras(Canvas canvas) {
        this.drawCircles(canvas);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void drawHighlighted(Canvas canvas, Highlight[] arrhighlight) {
        LineData lineData = this.mChart.getLineData();
        int n = arrhighlight.length;
        int n2 = 0;
        while (n2 < n) {
            Object t;
            Highlight highlight = arrhighlight[n2];
            ILineDataSet iLineDataSet = (ILineDataSet)lineData.getDataSetByIndex(highlight.getDataSetIndex());
            if (iLineDataSet != null && iLineDataSet.isHighlightEnabled() && this.isInBoundsX((Entry)(t = iLineDataSet.getEntryForXValue(highlight.getX(), highlight.getY())), iLineDataSet)) {
                MPPointD mPPointD = this.mChart.getTransformer(iLineDataSet.getAxisDependency()).getPixelForValues(((Entry)t).getX(), ((BaseEntry)t).getY() * this.mAnimator.getPhaseY());
                highlight.setDraw((float)mPPointD.x, (float)mPPointD.y);
                this.drawHighlightLines(canvas, (float)mPPointD.x, (float)mPPointD.y, iLineDataSet);
            }
            ++n2;
        }
        return;
    }

    protected void drawHorizontalBezier(ILineDataSet iLineDataSet) {
        float f = this.mAnimator.getPhaseY();
        Transformer transformer = this.mChart.getTransformer(iLineDataSet.getAxisDependency());
        this.mXBounds.set(this.mChart, iLineDataSet);
        this.cubicPath.reset();
        if (this.mXBounds.range >= 1) {
            Object t = iLineDataSet.getEntryForIndex(this.mXBounds.min);
            this.cubicPath.moveTo(((Entry)t).getX(), ((BaseEntry)t).getY() * f);
            for (int i = this.mXBounds.min + 1; i <= this.mXBounds.range + this.mXBounds.min; ++i) {
                Object t2 = iLineDataSet.getEntryForIndex(i);
                float f2 = ((Entry)t).getX() + (((Entry)t2).getX() - ((Entry)t).getX()) / 2.0f;
                this.cubicPath.cubicTo(f2, ((BaseEntry)t).getY() * f, f2, ((BaseEntry)t2).getY() * f, ((Entry)t2).getX(), ((BaseEntry)t2).getY() * f);
                t = t2;
            }
        }
        if (iLineDataSet.isDrawFilledEnabled()) {
            this.cubicFillPath.reset();
            this.cubicFillPath.addPath(this.cubicPath);
            this.drawCubicFill(this.mBitmapCanvas, iLineDataSet, this.cubicFillPath, transformer, this.mXBounds);
        }
        this.mRenderPaint.setColor(iLineDataSet.getColor());
        this.mRenderPaint.setStyle(Paint.Style.STROKE);
        transformer.pathValueToPixel(this.cubicPath);
        this.mBitmapCanvas.drawPath(this.cubicPath, this.mRenderPaint);
        this.mRenderPaint.setPathEffect(null);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    protected void drawLinear(Canvas object, ILineDataSet iLineDataSet) {
        block15: {
            int n;
            boolean bl;
            Transformer transformer;
            Object object2;
            void var2_8;
            float f;
            int n2;
            int n3;
            block18: {
                block17: {
                    block16: {
                        n3 = var2_8.getEntryCount();
                        bl = var2_8.isDrawSteppedEnabled();
                        n2 = bl ? 4 : 2;
                        transformer = this.mChart.getTransformer(var2_8.getAxisDependency());
                        f = this.mAnimator.getPhaseY();
                        this.mRenderPaint.setStyle(Paint.Style.STROKE);
                        object2 = var2_8.isDashedLineEnabled() ? this.mBitmapCanvas : object;
                        this.mXBounds.set(this.mChart, (IBarLineScatterCandleBubbleDataSet)var2_8);
                        if (var2_8.isDrawFilledEnabled() && n3 > 0) {
                            this.drawLinearFill((Canvas)object, (ILineDataSet)var2_8, transformer, this.mXBounds);
                        }
                        if (var2_8.getColors().size() <= 1) break block16;
                        if (this.mLineBuffer.length <= n2 * 2) {
                            this.mLineBuffer = new float[n2 * 4];
                        }
                        break block17;
                    }
                    if (this.mLineBuffer.length < Math.max(n3 * n2, n2) * 2) {
                        this.mLineBuffer = new float[Math.max(n3 * n2, n2) * 4];
                    }
                    if (var2_8.getEntryForIndex(this.mXBounds.min) == null) break block15;
                    n = 0;
                    break block18;
                }
                for (n3 = this.mXBounds.min; n3 <= this.mXBounds.range + this.mXBounds.min; ++n3) {
                    Object t = var2_8.getEntryForIndex(n3);
                    if (t == null) continue;
                    this.mLineBuffer[0] = ((Entry)t).getX();
                    this.mLineBuffer[1] = ((BaseEntry)t).getY() * f;
                    if (n3 < this.mXBounds.max) {
                        Object t2 = var2_8.getEntryForIndex(n3 + 1);
                        if (t2 == null) break block15;
                        if (bl) {
                            this.mLineBuffer[2] = ((Entry)t2).getX();
                            this.mLineBuffer[3] = this.mLineBuffer[1];
                            this.mLineBuffer[4] = this.mLineBuffer[2];
                            this.mLineBuffer[5] = this.mLineBuffer[3];
                            this.mLineBuffer[6] = ((Entry)t2).getX();
                            this.mLineBuffer[7] = ((BaseEntry)t2).getY() * f;
                        } else {
                            this.mLineBuffer[2] = ((Entry)t2).getX();
                            this.mLineBuffer[3] = ((BaseEntry)t2).getY() * f;
                        }
                    } else {
                        this.mLineBuffer[2] = this.mLineBuffer[0];
                        this.mLineBuffer[3] = this.mLineBuffer[1];
                    }
                    transformer.pointValuesToPixel(this.mLineBuffer);
                    if (this.mViewPortHandler.isInBoundsRight(this.mLineBuffer[0])) {
                        if (!this.mViewPortHandler.isInBoundsLeft(this.mLineBuffer[2]) || !this.mViewPortHandler.isInBoundsTop(this.mLineBuffer[1]) && !this.mViewPortHandler.isInBoundsBottom(this.mLineBuffer[3])) continue;
                        this.mRenderPaint.setColor(var2_8.getColor(n3));
                        object2.drawLines(this.mLineBuffer, 0, n2 * 2, this.mRenderPaint);
                        continue;
                    }
                    break block15;
                }
                break block15;
            }
            for (n3 = this.mXBounds.min; n3 <= this.mXBounds.range + this.mXBounds.min; ++n3) {
                int n4 = n3 == 0 ? 0 : n3 - 1;
                Object object3 = var2_8.getEntryForIndex(n4);
                Object t = var2_8.getEntryForIndex(n3);
                n4 = n;
                if (object3 != null) {
                    if (t == null) {
                        n4 = n;
                    } else {
                        float[] arrf = this.mLineBuffer;
                        int n5 = n + 1;
                        arrf[n] = ((Entry)object3).getX();
                        arrf = this.mLineBuffer;
                        n4 = n5 + 1;
                        arrf[n5] = ((BaseEntry)object3).getY() * f;
                        n = n4;
                        if (bl) {
                            arrf = this.mLineBuffer;
                            n = n4 + 1;
                            arrf[n4] = ((Entry)t).getX();
                            arrf = this.mLineBuffer;
                            n4 = n + 1;
                            arrf[n] = ((BaseEntry)object3).getY() * f;
                            arrf = this.mLineBuffer;
                            n5 = n4 + 1;
                            arrf[n4] = ((Entry)t).getX();
                            arrf = this.mLineBuffer;
                            n = n5 + 1;
                            arrf[n5] = ((BaseEntry)object3).getY() * f;
                        }
                        object3 = this.mLineBuffer;
                        n5 = n + 1;
                        object3[n] = ((Entry)t).getX();
                        object3 = this.mLineBuffer;
                        n4 = n5 + 1;
                        object3[n5] = ((BaseEntry)t).getY() * f;
                    }
                }
                n = n4;
            }
            if (n > 0) {
                transformer.pointValuesToPixel(this.mLineBuffer);
                n2 = Math.max((this.mXBounds.range + 1) * n2, n2);
                this.mRenderPaint.setColor(var2_8.getColor());
                object2.drawLines(this.mLineBuffer, 0, n2 * 2, this.mRenderPaint);
            }
        }
        this.mRenderPaint.setPathEffect(null);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void drawLinearFill(Canvas canvas, ILineDataSet iLineDataSet, Transformer transformer, BarLineScatterCandleBubbleRenderer.XBounds xBounds) {
        int n;
        int n2;
        Path path = this.mGenerateFilledPathBuffer;
        int n3 = xBounds.min;
        int n4 = xBounds.range + xBounds.min;
        int n5 = 0;
        do {
            int n6;
            n = n3 + n5 * 128;
            n2 = n6 = n + 128;
            if (n6 > n4) {
                n2 = n4;
            }
            if (n <= n2) {
                this.generateFilledPath(iLineDataSet, n, n2, path);
                transformer.pathValueToPixel(path);
                xBounds = iLineDataSet.getFillDrawable();
                if (xBounds != null) {
                    this.drawFilledPath(canvas, path, (Drawable)xBounds);
                } else {
                    this.drawFilledPath(canvas, path, iLineDataSet.getFillColor(), iLineDataSet.getFillAlpha());
                }
            }
            ++n5;
        } while (n <= n2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void drawValues(Canvas var1_1) {
        if (this.isDrawingValuesAllowed(this.mChart) == false) return;
        var7_2 = this.mChart.getLineData().getDataSets();
        var4_3 = 0;
        block0: do {
            if (var4_3 >= var7_2.size()) return;
            var8_8 = (ILineDataSet)var7_2.get(var4_3);
            if (!this.shouldDrawValues(var8_8)) ** GOTO lbl18
            this.applyValueTextStyle(var8_8);
            var9_9 = this.mChart.getTransformer(var8_8.getAxisDependency());
            var5_6 = var6_7 = (int)(var8_8.getCircleRadius() * 1.75f);
            if (!var8_8.isDrawCirclesEnabled()) {
                var5_6 = var6_7 / 2;
            }
            this.mXBounds.set(this.mChart, var8_8);
            var9_9 = var9_9.generateTransformedValuesLine(var8_8, this.mAnimator.getPhaseX(), this.mAnimator.getPhaseY(), this.mXBounds.min, this.mXBounds.max);
            var6_7 = 0;
            do {
                block8: {
                    if (var6_7 < var9_9.length) break block8;
lbl18:
                    // 3 sources
                    do {
                        ++var4_3;
                        continue block0;
                        break;
                    } while (true);
                }
                var2_4 = var9_9[var6_7];
                var3_5 = var9_9[var6_7 + 1];
                if (!this.mViewPortHandler.isInBoundsRight(var2_4)) ** continue;
                if (this.mViewPortHandler.isInBoundsLeft(var2_4) && this.mViewPortHandler.isInBoundsY(var3_5)) {
                    var10_10 = var8_8.getEntryForIndex(var6_7 / 2 + this.mXBounds.min);
                    this.drawValue(var1_1, var8_8.getValueFormatter(), var10_10.getY(), (Entry)var10_10, var4_3, var2_4, var3_5 - (float)var5_6, var8_8.getValueTextColor(var6_7 / 2));
                }
                var6_7 += 2;
            } while (true);
            break;
        } while (true);
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

    private class DataSetImageCache {
        private Bitmap[] circleBitmaps;
        private Path mCirclePathBuffer = new Path();

        private DataSetImageCache() {
        }

        /*
         * Enabled aggressive block sorting
         */
        protected void fill(ILineDataSet iLineDataSet, boolean bl, boolean bl2) {
            int n = iLineDataSet.getCircleColorCount();
            float f = iLineDataSet.getCircleRadius();
            float f2 = iLineDataSet.getCircleHoleRadius();
            int n2 = 0;
            while (n2 < n) {
                Bitmap.Config config = Bitmap.Config.ARGB_4444;
                config = Bitmap.createBitmap((int)((int)((double)f * 2.1)), (int)((int)((double)f * 2.1)), (Bitmap.Config)config);
                Canvas canvas = new Canvas((Bitmap)config);
                this.circleBitmaps[n2] = config;
                LineChartRenderer.this.mRenderPaint.setColor(iLineDataSet.getCircleColor(n2));
                if (bl2) {
                    this.mCirclePathBuffer.reset();
                    this.mCirclePathBuffer.addCircle(f, f, f, Path.Direction.CW);
                    this.mCirclePathBuffer.addCircle(f, f, f2, Path.Direction.CCW);
                    canvas.drawPath(this.mCirclePathBuffer, LineChartRenderer.this.mRenderPaint);
                } else {
                    canvas.drawCircle(f, f, f, LineChartRenderer.this.mRenderPaint);
                    if (bl) {
                        canvas.drawCircle(f, f, f2, LineChartRenderer.this.mCirclePaintInner);
                    }
                }
                ++n2;
            }
            return;
        }

        protected Bitmap getBitmap(int n) {
            return this.circleBitmaps[n % this.circleBitmaps.length];
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        protected boolean init(ILineDataSet iLineDataSet) {
            int n = iLineDataSet.getCircleColorCount();
            boolean bl = false;
            if (this.circleBitmaps == null) {
                this.circleBitmaps = new Bitmap[n];
                return true;
            }
            if (this.circleBitmaps.length == n) return bl;
            this.circleBitmaps = new Bitmap[n];
            return true;
        }
    }

}

