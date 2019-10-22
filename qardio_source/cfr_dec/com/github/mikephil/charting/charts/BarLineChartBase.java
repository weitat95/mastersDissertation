/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 */
package com.github.mikephil.charting.charts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.listener.BarLineChartTouchListener;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnDrawListener;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.renderer.LegendRenderer;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

@SuppressLint(value={"RtlHardcoded"})
public abstract class BarLineChartBase<T extends BarLineScatterCandleBubbleData<? extends IBarLineScatterCandleBubbleDataSet<? extends Entry>>>
extends Chart<T>
implements BarLineScatterCandleBubbleDataProvider {
    private long drawCycles = 0L;
    protected boolean mAutoScaleMinMaxEnabled = false;
    protected YAxis mAxisLeft;
    protected YAxisRenderer mAxisRendererLeft;
    protected YAxisRenderer mAxisRendererRight;
    protected YAxis mAxisRight;
    protected Paint mBorderPaint;
    protected boolean mClipValuesToContent = false;
    private boolean mCustomViewPortEnabled = false;
    protected boolean mDoubleTapToZoomEnabled = true;
    private boolean mDragEnabled = true;
    protected boolean mDrawBorders = false;
    protected boolean mDrawGridBackground = false;
    protected OnDrawListener mDrawListener;
    protected Matrix mFitScreenMatrixBuffer;
    protected float[] mGetPositionBuffer;
    protected Paint mGridBackgroundPaint;
    protected boolean mHighlightPerDragEnabled = true;
    protected boolean mKeepPositionOnRotation = false;
    protected Transformer mLeftAxisTransformer;
    protected int mMaxVisibleCount = 100;
    protected float mMinOffset = 15.0f;
    private RectF mOffsetsBuffer = new RectF();
    protected float[] mOnSizeChangedBuffer;
    protected boolean mPinchZoomEnabled = false;
    protected Transformer mRightAxisTransformer;
    private boolean mScaleXEnabled = true;
    private boolean mScaleYEnabled = true;
    protected XAxisRenderer mXAxisRenderer;
    protected Matrix mZoomInMatrixBuffer = new Matrix();
    protected Matrix mZoomMatrixBuffer;
    protected Matrix mZoomOutMatrixBuffer = new Matrix();
    protected MPPointD posForGetHighestVisibleX;
    protected MPPointD posForGetLowestVisibleX;
    private long totalTime = 0L;

    public BarLineChartBase(Context context) {
        super(context);
        this.mZoomMatrixBuffer = new Matrix();
        this.mFitScreenMatrixBuffer = new Matrix();
        this.mGetPositionBuffer = new float[2];
        this.posForGetLowestVisibleX = MPPointD.getInstance(0.0, 0.0);
        this.posForGetHighestVisibleX = MPPointD.getInstance(0.0, 0.0);
        this.mOnSizeChangedBuffer = new float[2];
    }

    public BarLineChartBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mZoomMatrixBuffer = new Matrix();
        this.mFitScreenMatrixBuffer = new Matrix();
        this.mGetPositionBuffer = new float[2];
        this.posForGetLowestVisibleX = MPPointD.getInstance(0.0, 0.0);
        this.posForGetHighestVisibleX = MPPointD.getInstance(0.0, 0.0);
        this.mOnSizeChangedBuffer = new float[2];
    }

    public BarLineChartBase(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mZoomMatrixBuffer = new Matrix();
        this.mFitScreenMatrixBuffer = new Matrix();
        this.mGetPositionBuffer = new float[2];
        this.posForGetLowestVisibleX = MPPointD.getInstance(0.0, 0.0);
        this.posForGetHighestVisibleX = MPPointD.getInstance(0.0, 0.0);
        this.mOnSizeChangedBuffer = new float[2];
    }

    protected void autoScale() {
        float f = this.getLowestVisibleX();
        float f2 = this.getHighestVisibleX();
        ((BarLineScatterCandleBubbleData)this.mData).calcMinMaxY(f, f2);
        this.mXAxis.calculate(((BarLineScatterCandleBubbleData)this.mData).getXMin(), ((BarLineScatterCandleBubbleData)this.mData).getXMax());
        this.mAxisLeft.calculate(((BarLineScatterCandleBubbleData)this.mData).getYMin(YAxis.AxisDependency.LEFT), ((BarLineScatterCandleBubbleData)this.mData).getYMax(YAxis.AxisDependency.LEFT));
        this.mAxisRight.calculate(((BarLineScatterCandleBubbleData)this.mData).getYMin(YAxis.AxisDependency.RIGHT), ((BarLineScatterCandleBubbleData)this.mData).getYMax(YAxis.AxisDependency.RIGHT));
        this.calculateOffsets();
    }

    @Override
    protected void calcMinMax() {
        this.mXAxis.calculate(((BarLineScatterCandleBubbleData)this.mData).getXMin(), ((BarLineScatterCandleBubbleData)this.mData).getXMax());
        this.mAxisLeft.calculate(((BarLineScatterCandleBubbleData)this.mData).getYMin(YAxis.AxisDependency.LEFT), ((BarLineScatterCandleBubbleData)this.mData).getYMax(YAxis.AxisDependency.LEFT));
        this.mAxisRight.calculate(((BarLineScatterCandleBubbleData)this.mData).getYMin(YAxis.AxisDependency.RIGHT), ((BarLineScatterCandleBubbleData)this.mData).getYMax(YAxis.AxisDependency.RIGHT));
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void calculateLegendOffsets(RectF rectF) {
        rectF.left = 0.0f;
        rectF.right = 0.0f;
        rectF.top = 0.0f;
        rectF.bottom = 0.0f;
        if (this.mLegend == null || !this.mLegend.isEnabled() || this.mLegend.isDrawInsideEnabled()) return;
        {
            switch (this.mLegend.getOrientation()) {
                default: {
                    return;
                }
                case VERTICAL: {
                    switch (this.mLegend.getHorizontalAlignment()) {
                        default: {
                            return;
                        }
                        case LEFT: {
                            rectF.left += Math.min(this.mLegend.mNeededWidth, this.mViewPortHandler.getChartWidth() * this.mLegend.getMaxSizePercent()) + this.mLegend.getXOffset();
                            return;
                        }
                        case RIGHT: {
                            rectF.right += Math.min(this.mLegend.mNeededWidth, this.mViewPortHandler.getChartWidth() * this.mLegend.getMaxSizePercent()) + this.mLegend.getXOffset();
                            return;
                        }
                        case CENTER: 
                    }
                    switch (2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[this.mLegend.getVerticalAlignment().ordinal()]) {
                        default: {
                            return;
                        }
                        case 1: {
                            rectF.top += Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent()) + this.mLegend.getYOffset();
                            return;
                        }
                        case 2: 
                    }
                    rectF.bottom += Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent()) + this.mLegend.getYOffset();
                    return;
                }
                case HORIZONTAL: {
                    switch (2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[this.mLegend.getVerticalAlignment().ordinal()]) {
                        default: {
                            return;
                        }
                        case 1: {
                            rectF.top += Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent()) + this.mLegend.getYOffset();
                            if (!this.getXAxis().isEnabled() || !this.getXAxis().isDrawLabelsEnabled()) return;
                            rectF.top += (float)this.getXAxis().mLabelRotatedHeight;
                            return;
                        }
                        case 2: {
                            rectF.bottom += Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent()) + this.mLegend.getYOffset();
                            if (!this.getXAxis().isEnabled() || !this.getXAxis().isDrawLabelsEnabled()) return;
                            rectF.bottom += (float)this.getXAxis().mLabelRotatedHeight;
                            return;
                        }
                    }
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void calculateOffsets() {
        if (!this.mCustomViewPortEnabled) {
            this.calculateLegendOffsets(this.mOffsetsBuffer);
            float f = 0.0f + this.mOffsetsBuffer.left;
            float f2 = 0.0f + this.mOffsetsBuffer.top;
            float f3 = 0.0f + this.mOffsetsBuffer.right;
            float f4 = 0.0f + this.mOffsetsBuffer.bottom;
            float f5 = f;
            if (this.mAxisLeft.needsOffset()) {
                f5 = f + this.mAxisLeft.getRequiredWidthSpace(this.mAxisRendererLeft.getPaintAxisLabels());
            }
            float f6 = f3;
            if (this.mAxisRight.needsOffset()) {
                f6 = f3 + this.mAxisRight.getRequiredWidthSpace(this.mAxisRendererRight.getPaintAxisLabels());
            }
            f3 = f4;
            f = f2;
            if (this.mXAxis.isEnabled()) {
                f3 = f4;
                f = f2;
                if (this.mXAxis.isDrawLabelsEnabled()) {
                    float f7 = (float)this.mXAxis.mLabelRotatedHeight + this.mXAxis.getYOffset();
                    if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM) {
                        f3 = f4 + f7;
                        f = f2;
                    } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP) {
                        f = f2 + f7;
                        f3 = f4;
                    } else {
                        f3 = f4;
                        f = f2;
                        if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTH_SIDED) {
                            f3 = f4 + f7;
                            f = f2 + f7;
                        }
                    }
                }
            }
            f2 = Utils.convertDpToPixel(this.mMinOffset);
            this.mViewPortHandler.restrainViewPort(Math.max(f2, f5 += this.getExtraLeftOffset()), Math.max(f2, f += this.getExtraTopOffset()), Math.max(f2, f6 += this.getExtraRightOffset()), Math.max(f2, f3 += this.getExtraBottomOffset()));
            if (this.mLogEnabled) {
                Log.i((String)"MPAndroidChart", (String)("offsetLeft: " + f5 + ", offsetTop: " + f + ", offsetRight: " + f6 + ", offsetBottom: " + f3));
                Log.i((String)"MPAndroidChart", (String)("Content: " + this.mViewPortHandler.getContentRect().toString()));
            }
        }
        this.prepareOffsetMatrix();
        this.prepareValuePxMatrix();
    }

    public void computeScroll() {
        if (this.mChartTouchListener instanceof BarLineChartTouchListener) {
            ((BarLineChartTouchListener)this.mChartTouchListener).computeScroll();
        }
    }

    protected void drawGridBackground(Canvas canvas) {
        if (this.mDrawGridBackground) {
            canvas.drawRect(this.mViewPortHandler.getContentRect(), this.mGridBackgroundPaint);
        }
        if (this.mDrawBorders) {
            canvas.drawRect(this.mViewPortHandler.getContentRect(), this.mBorderPaint);
        }
    }

    public YAxis getAxis(YAxis.AxisDependency axisDependency) {
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            return this.mAxisLeft;
        }
        return this.mAxisRight;
    }

    public YAxis getAxisLeft() {
        return this.mAxisLeft;
    }

    protected float getAxisRange(YAxis.AxisDependency axisDependency) {
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            return this.mAxisLeft.mAxisRange;
        }
        return this.mAxisRight.mAxisRange;
    }

    public YAxis getAxisRight() {
        return this.mAxisRight;
    }

    public IBarLineScatterCandleBubbleDataSet getDataSetByTouchPoint(float f, float f2) {
        Highlight highlight = this.getHighlightByTouchPoint(f, f2);
        if (highlight != null) {
            return (IBarLineScatterCandleBubbleDataSet)((BarLineScatterCandleBubbleData)this.mData).getDataSetByIndex(highlight.getDataSetIndex());
        }
        return null;
    }

    public OnDrawListener getDrawListener() {
        return this.mDrawListener;
    }

    @Override
    public float getHighestVisibleX() {
        this.getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom(), this.posForGetHighestVisibleX);
        return (float)Math.min((double)this.mXAxis.mAxisMaximum, this.posForGetHighestVisibleX.x);
    }

    @Override
    public float getLowestVisibleX() {
        this.getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.posForGetLowestVisibleX);
        return (float)Math.max((double)this.mXAxis.mAxisMinimum, this.posForGetLowestVisibleX.x);
    }

    @Override
    public int getMaxVisibleCount() {
        return this.mMaxVisibleCount;
    }

    public float getMinOffset() {
        return this.mMinOffset;
    }

    public YAxisRenderer getRendererLeftYAxis() {
        return this.mAxisRendererLeft;
    }

    public YAxisRenderer getRendererRightYAxis() {
        return this.mAxisRendererRight;
    }

    public XAxisRenderer getRendererXAxis() {
        return this.mXAxisRenderer;
    }

    public float getScaleX() {
        if (this.mViewPortHandler == null) {
            return 1.0f;
        }
        return this.mViewPortHandler.getScaleX();
    }

    public float getScaleY() {
        if (this.mViewPortHandler == null) {
            return 1.0f;
        }
        return this.mViewPortHandler.getScaleY();
    }

    @Override
    public Transformer getTransformer(YAxis.AxisDependency axisDependency) {
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            return this.mLeftAxisTransformer;
        }
        return this.mRightAxisTransformer;
    }

    public float getVisibleXRange() {
        return Math.abs(this.getHighestVisibleX() - this.getLowestVisibleX());
    }

    public float getYChartMax() {
        return Math.max(this.mAxisLeft.mAxisMaximum, this.mAxisRight.mAxisMaximum);
    }

    public float getYChartMin() {
        return Math.min(this.mAxisLeft.mAxisMinimum, this.mAxisRight.mAxisMinimum);
    }

    public boolean hasNoDragOffset() {
        return this.mViewPortHandler.hasNoDragOffset();
    }

    @Override
    protected void init() {
        super.init();
        this.mAxisLeft = new YAxis(YAxis.AxisDependency.LEFT);
        this.mAxisRight = new YAxis(YAxis.AxisDependency.RIGHT);
        this.mLeftAxisTransformer = new Transformer(this.mViewPortHandler);
        this.mRightAxisTransformer = new Transformer(this.mViewPortHandler);
        this.mAxisRendererLeft = new YAxisRenderer(this.mViewPortHandler, this.mAxisLeft, this.mLeftAxisTransformer);
        this.mAxisRendererRight = new YAxisRenderer(this.mViewPortHandler, this.mAxisRight, this.mRightAxisTransformer);
        this.mXAxisRenderer = new XAxisRenderer(this.mViewPortHandler, this.mXAxis, this.mLeftAxisTransformer);
        this.setHighlighter(new ChartHighlighter<BarLineChartBase>(this));
        this.mChartTouchListener = new BarLineChartTouchListener(this, this.mViewPortHandler.getMatrixTouch(), 3.0f);
        this.mGridBackgroundPaint = new Paint();
        this.mGridBackgroundPaint.setStyle(Paint.Style.FILL);
        this.mGridBackgroundPaint.setColor(Color.rgb((int)240, (int)240, (int)240));
        this.mBorderPaint = new Paint();
        this.mBorderPaint.setStyle(Paint.Style.STROKE);
        this.mBorderPaint.setColor(-16777216);
        this.mBorderPaint.setStrokeWidth(Utils.convertDpToPixel(1.0f));
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isAnyAxisInverted() {
        return this.mAxisLeft.isInverted() || this.mAxisRight.isInverted();
    }

    public boolean isClipValuesToContentEnabled() {
        return this.mClipValuesToContent;
    }

    public boolean isDoubleTapToZoomEnabled() {
        return this.mDoubleTapToZoomEnabled;
    }

    public boolean isDragEnabled() {
        return this.mDragEnabled;
    }

    public boolean isFullyZoomedOut() {
        return this.mViewPortHandler.isFullyZoomedOut();
    }

    public boolean isHighlightPerDragEnabled() {
        return this.mHighlightPerDragEnabled;
    }

    @Override
    public boolean isInverted(YAxis.AxisDependency axisDependency) {
        return this.getAxis(axisDependency).isInverted();
    }

    public boolean isPinchZoomEnabled() {
        return this.mPinchZoomEnabled;
    }

    public boolean isScaleXEnabled() {
        return this.mScaleXEnabled;
    }

    public boolean isScaleYEnabled() {
        return this.mScaleYEnabled;
    }

    @Override
    public void notifyDataSetChanged() {
        if (this.mData == null) {
            if (this.mLogEnabled) {
                Log.i((String)"MPAndroidChart", (String)"Preparing... DATA NOT SET.");
            }
            return;
        }
        if (this.mLogEnabled) {
            Log.i((String)"MPAndroidChart", (String)"Preparing...");
        }
        if (this.mRenderer != null) {
            this.mRenderer.initBuffers();
        }
        this.calcMinMax();
        this.mAxisRendererLeft.computeAxis(this.mAxisLeft.mAxisMinimum, this.mAxisLeft.mAxisMaximum, this.mAxisLeft.isInverted());
        this.mAxisRendererRight.computeAxis(this.mAxisRight.mAxisMinimum, this.mAxisRight.mAxisMaximum, this.mAxisRight.isInverted());
        this.mXAxisRenderer.computeAxis(this.mXAxis.mAxisMinimum, this.mXAxis.mAxisMaximum, false);
        if (this.mLegend != null) {
            this.mLegendRenderer.computeLegend(this.mData);
        }
        this.calculateOffsets();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onDraw(Canvas canvas) {
        long l;
        block17: {
            block16: {
                super.onDraw(canvas);
                if (this.mData == null) break block16;
                l = System.currentTimeMillis();
                this.drawGridBackground(canvas);
                if (this.mAxisLeft.isEnabled()) {
                    this.mAxisRendererLeft.computeAxis(this.mAxisLeft.mAxisMinimum, this.mAxisLeft.mAxisMaximum, this.mAxisLeft.isInverted());
                }
                if (this.mAxisRight.isEnabled()) {
                    this.mAxisRendererRight.computeAxis(this.mAxisRight.mAxisMinimum, this.mAxisRight.mAxisMaximum, this.mAxisRight.isInverted());
                }
                if (this.mXAxis.isEnabled()) {
                    this.mXAxisRenderer.computeAxis(this.mXAxis.mAxisMinimum, this.mXAxis.mAxisMaximum, false);
                }
                this.mXAxisRenderer.renderAxisLine(canvas);
                this.mAxisRendererLeft.renderAxisLine(canvas);
                this.mAxisRendererRight.renderAxisLine(canvas);
                if (this.mAutoScaleMinMaxEnabled) {
                    this.autoScale();
                }
                this.mXAxisRenderer.renderGridLines(canvas);
                this.mAxisRendererLeft.renderGridLines(canvas);
                this.mAxisRendererRight.renderGridLines(canvas);
                if (this.mXAxis.isDrawLimitLinesBehindDataEnabled()) {
                    this.mXAxisRenderer.renderLimitLines(canvas);
                }
                if (this.mAxisLeft.isDrawLimitLinesBehindDataEnabled()) {
                    this.mAxisRendererLeft.renderLimitLines(canvas);
                }
                if (this.mAxisRight.isDrawLimitLinesBehindDataEnabled()) {
                    this.mAxisRendererRight.renderLimitLines(canvas);
                }
                int n = canvas.save();
                canvas.clipRect(this.mViewPortHandler.getContentRect());
                this.mRenderer.drawData(canvas);
                if (this.valuesToHighlight()) {
                    this.mRenderer.drawHighlighted(canvas, this.mIndicesToHighlight);
                }
                canvas.restoreToCount(n);
                this.mRenderer.drawExtras(canvas);
                if (!this.mXAxis.isDrawLimitLinesBehindDataEnabled()) {
                    this.mXAxisRenderer.renderLimitLines(canvas);
                }
                if (!this.mAxisLeft.isDrawLimitLinesBehindDataEnabled()) {
                    this.mAxisRendererLeft.renderLimitLines(canvas);
                }
                if (!this.mAxisRight.isDrawLimitLinesBehindDataEnabled()) {
                    this.mAxisRendererRight.renderLimitLines(canvas);
                }
                this.mXAxisRenderer.renderAxisLabels(canvas);
                this.mAxisRendererLeft.renderAxisLabels(canvas);
                this.mAxisRendererRight.renderAxisLabels(canvas);
                if (this.isClipValuesToContentEnabled()) {
                    n = canvas.save();
                    canvas.clipRect(this.mViewPortHandler.getContentRect());
                    this.mRenderer.drawValues(canvas);
                    canvas.restoreToCount(n);
                } else {
                    this.mRenderer.drawValues(canvas);
                }
                this.mLegendRenderer.renderLegend(canvas);
                this.drawDescription(canvas);
                this.drawMarkers(canvas);
                if (this.mLogEnabled) break block17;
            }
            return;
        }
        l = System.currentTimeMillis() - l;
        this.totalTime += l;
        ++this.drawCycles;
        long l2 = this.totalTime / this.drawCycles;
        Log.i((String)"MPAndroidChart", (String)("Drawtime: " + l + " ms, average: " + l2 + " ms, cycles: " + this.drawCycles));
    }

    @Override
    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        float[] arrf = this.mOnSizeChangedBuffer;
        this.mOnSizeChangedBuffer[1] = 0.0f;
        arrf[0] = 0.0f;
        if (this.mKeepPositionOnRotation) {
            this.mOnSizeChangedBuffer[0] = this.mViewPortHandler.contentLeft();
            this.mOnSizeChangedBuffer[1] = this.mViewPortHandler.contentTop();
            this.getTransformer(YAxis.AxisDependency.LEFT).pixelsToValue(this.mOnSizeChangedBuffer);
        }
        super.onSizeChanged(n, n2, n3, n4);
        if (this.mKeepPositionOnRotation) {
            this.getTransformer(YAxis.AxisDependency.LEFT).pointValuesToPixel(this.mOnSizeChangedBuffer);
            this.mViewPortHandler.centerViewPort(this.mOnSizeChangedBuffer, (View)this);
            return;
        }
        this.mViewPortHandler.refresh(this.mViewPortHandler.getMatrixTouch(), (View)this, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        if (this.mChartTouchListener == null || this.mData == null || !this.mTouchEnabled) {
            return false;
        }
        return this.mChartTouchListener.onTouch((View)this, motionEvent);
    }

    protected void prepareOffsetMatrix() {
        this.mRightAxisTransformer.prepareMatrixOffset(this.mAxisRight.isInverted());
        this.mLeftAxisTransformer.prepareMatrixOffset(this.mAxisLeft.isInverted());
    }

    protected void prepareValuePxMatrix() {
        if (this.mLogEnabled) {
            Log.i((String)"MPAndroidChart", (String)("Preparing Value-Px Matrix, xmin: " + this.mXAxis.mAxisMinimum + ", xmax: " + this.mXAxis.mAxisMaximum + ", xdelta: " + this.mXAxis.mAxisRange));
        }
        this.mRightAxisTransformer.prepareMatrixValuePx(this.mXAxis.mAxisMinimum, this.mXAxis.mAxisRange, this.mAxisRight.mAxisRange, this.mAxisRight.mAxisMinimum);
        this.mLeftAxisTransformer.prepareMatrixValuePx(this.mXAxis.mAxisMinimum, this.mXAxis.mAxisRange, this.mAxisLeft.mAxisRange, this.mAxisLeft.mAxisMinimum);
    }

    public void setAutoScaleMinMaxEnabled(boolean bl) {
        this.mAutoScaleMinMaxEnabled = bl;
    }

    public void setBorderColor(int n) {
        this.mBorderPaint.setColor(n);
    }

    public void setBorderWidth(float f) {
        this.mBorderPaint.setStrokeWidth(Utils.convertDpToPixel(f));
    }

    public void setClipValuesToContent(boolean bl) {
        this.mClipValuesToContent = bl;
    }

    public void setDoubleTapToZoomEnabled(boolean bl) {
        this.mDoubleTapToZoomEnabled = bl;
    }

    public void setDragEnabled(boolean bl) {
        this.mDragEnabled = bl;
    }

    public void setDragOffsetX(float f) {
        this.mViewPortHandler.setDragOffsetX(f);
    }

    public void setDragOffsetY(float f) {
        this.mViewPortHandler.setDragOffsetY(f);
    }

    public void setDrawBorders(boolean bl) {
        this.mDrawBorders = bl;
    }

    public void setDrawGridBackground(boolean bl) {
        this.mDrawGridBackground = bl;
    }

    public void setGridBackgroundColor(int n) {
        this.mGridBackgroundPaint.setColor(n);
    }

    public void setHighlightPerDragEnabled(boolean bl) {
        this.mHighlightPerDragEnabled = bl;
    }

    public void setKeepPositionOnRotation(boolean bl) {
        this.mKeepPositionOnRotation = bl;
    }

    public void setMaxVisibleValueCount(int n) {
        this.mMaxVisibleCount = n;
    }

    public void setMinOffset(float f) {
        this.mMinOffset = f;
    }

    public void setOnDrawListener(OnDrawListener onDrawListener) {
        this.mDrawListener = onDrawListener;
    }

    @Override
    public void setPaint(Paint paint, int n) {
        super.setPaint(paint, n);
        switch (n) {
            default: {
                return;
            }
            case 4: 
        }
        this.mGridBackgroundPaint = paint;
    }

    public void setPinchZoom(boolean bl) {
        this.mPinchZoomEnabled = bl;
    }

    public void setRendererLeftYAxis(YAxisRenderer yAxisRenderer) {
        this.mAxisRendererLeft = yAxisRenderer;
    }

    public void setRendererRightYAxis(YAxisRenderer yAxisRenderer) {
        this.mAxisRendererRight = yAxisRenderer;
    }

    public void setScaleEnabled(boolean bl) {
        this.mScaleXEnabled = bl;
        this.mScaleYEnabled = bl;
    }

    public void setScaleMinima(float f, float f2) {
        this.mViewPortHandler.setMinimumScaleX(f);
        this.mViewPortHandler.setMinimumScaleY(f2);
    }

    public void setScaleXEnabled(boolean bl) {
        this.mScaleXEnabled = bl;
    }

    public void setScaleYEnabled(boolean bl) {
        this.mScaleYEnabled = bl;
    }

    public void setViewPortOffsets(final float f, final float f2, final float f3, final float f4) {
        this.mCustomViewPortEnabled = true;
        this.post(new Runnable(){

            @Override
            public void run() {
                BarLineChartBase.this.mViewPortHandler.restrainViewPort(f, f2, f3, f4);
                BarLineChartBase.this.prepareOffsetMatrix();
                BarLineChartBase.this.prepareValuePxMatrix();
            }
        });
    }

    public void setVisibleXRange(float f, float f2) {
        f = this.mXAxis.mAxisRange / f;
        f2 = this.mXAxis.mAxisRange / f2;
        this.mViewPortHandler.setMinMaxScaleX(f, f2);
    }

    public void setVisibleXRangeMaximum(float f) {
        f = this.mXAxis.mAxisRange / f;
        this.mViewPortHandler.setMinimumScaleX(f);
    }

    public void setVisibleXRangeMinimum(float f) {
        f = this.mXAxis.mAxisRange / f;
        this.mViewPortHandler.setMaximumScaleX(f);
    }

    public void setVisibleYRange(float f, float f2, YAxis.AxisDependency axisDependency) {
        f = this.getAxisRange(axisDependency) / f;
        f2 = this.getAxisRange(axisDependency) / f2;
        this.mViewPortHandler.setMinMaxScaleY(f, f2);
    }

    public void setVisibleYRangeMaximum(float f, YAxis.AxisDependency axisDependency) {
        f = this.getAxisRange(axisDependency) / f;
        this.mViewPortHandler.setMinimumScaleY(f);
    }

    public void setVisibleYRangeMinimum(float f, YAxis.AxisDependency axisDependency) {
        f = this.getAxisRange(axisDependency) / f;
        this.mViewPortHandler.setMaximumScaleY(f);
    }

    public void setXAxisRenderer(XAxisRenderer xAxisRenderer) {
        this.mXAxisRenderer = xAxisRenderer;
    }

    public void zoom(float f, float f2, float f3, float f4) {
        Matrix matrix = this.mZoomMatrixBuffer;
        this.mViewPortHandler.zoom(f, f2, f3, -f4, matrix);
        this.mViewPortHandler.refresh(matrix, (View)this, false);
        this.calculateOffsets();
        this.postInvalidate();
    }

}

