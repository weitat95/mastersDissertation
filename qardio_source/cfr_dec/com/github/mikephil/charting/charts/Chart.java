/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.RectF
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 */
package com.github.mikephil.charting.charts;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.IHighlighter;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.renderer.LegendRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressLint(value={"NewApi"})
public abstract class Chart<T extends ChartData<? extends IDataSet<? extends Entry>>>
extends ViewGroup
implements ChartInterface {
    protected ChartAnimator mAnimator;
    protected ChartTouchListener mChartTouchListener;
    protected T mData = null;
    protected DefaultValueFormatter mDefaultValueFormatter = new DefaultValueFormatter(0);
    protected Paint mDescPaint;
    protected Description mDescription;
    private boolean mDragDecelerationEnabled = true;
    private float mDragDecelerationFrictionCoef = 0.9f;
    protected boolean mDrawMarkers = true;
    private float mExtraBottomOffset = 0.0f;
    private float mExtraLeftOffset = 0.0f;
    private float mExtraRightOffset = 0.0f;
    private float mExtraTopOffset = 0.0f;
    private OnChartGestureListener mGestureListener;
    protected boolean mHighLightPerTapEnabled = true;
    protected IHighlighter mHighlighter;
    protected Highlight[] mIndicesToHighlight;
    protected Paint mInfoPaint;
    protected ArrayList<Runnable> mJobs;
    protected Legend mLegend;
    protected LegendRenderer mLegendRenderer;
    protected boolean mLogEnabled = false;
    protected IMarker mMarker;
    protected float mMaxHighlightDistance = 0.0f;
    private String mNoDataText = "No chart data available.";
    private boolean mOffsetsCalculated = false;
    protected DataRenderer mRenderer;
    protected OnChartValueSelectedListener mSelectionListener;
    protected boolean mTouchEnabled = true;
    private boolean mUnbind = false;
    protected ViewPortHandler mViewPortHandler = new ViewPortHandler();
    protected XAxis mXAxis;

    public Chart(Context context) {
        super(context);
        this.mJobs = new ArrayList();
        this.init();
    }

    public Chart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mJobs = new ArrayList();
        this.init();
    }

    public Chart(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mJobs = new ArrayList();
        this.init();
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup)view).getChildCount(); ++i) {
                this.unbindDrawables(((ViewGroup)view).getChildAt(i));
            }
            ((ViewGroup)view).removeAllViews();
        }
    }

    protected abstract void calcMinMax();

    protected abstract void calculateOffsets();

    public void disableScroll() {
        ViewParent viewParent = this.getParent();
        if (viewParent != null) {
            viewParent.requestDisallowInterceptTouchEvent(true);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void drawDescription(Canvas canvas) {
        if (this.mDescription != null && this.mDescription.isEnabled()) {
            float f;
            float f2;
            MPPointF mPPointF = this.mDescription.getPosition();
            this.mDescPaint.setTypeface(this.mDescription.getTypeface());
            this.mDescPaint.setTextSize(this.mDescription.getTextSize());
            this.mDescPaint.setColor(this.mDescription.getTextColor());
            this.mDescPaint.setTextAlign(this.mDescription.getTextAlign());
            if (mPPointF == null) {
                f = (float)this.getWidth() - this.mViewPortHandler.offsetRight() - this.mDescription.getXOffset();
                f2 = (float)this.getHeight() - this.mViewPortHandler.offsetBottom() - this.mDescription.getYOffset();
            } else {
                f = mPPointF.x;
                f2 = mPPointF.y;
            }
            canvas.drawText(this.mDescription.getText(), f, f2, this.mDescPaint);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void drawMarkers(Canvas canvas) {
        if (this.mMarker != null && this.isDrawMarkersEnabled() && this.valuesToHighlight()) {
            for (int i = 0; i < this.mIndicesToHighlight.length; ++i) {
                float[] arrf;
                Highlight highlight = this.mIndicesToHighlight[i];
                Entry entry = ((ChartData)this.mData).getDataSetByIndex(highlight.getDataSetIndex());
                Entry entry2 = ((ChartData)this.mData).getEntryForHighlight(this.mIndicesToHighlight[i]);
                int n = entry.getEntryIndex((Entry)entry2);
                if (entry2 == null || (float)n > (float)entry.getEntryCount() * this.mAnimator.getPhaseX() || !this.mViewPortHandler.isInBounds((arrf = this.getMarkerPosition(highlight))[0], arrf[1])) continue;
                this.mMarker.refreshContent(entry2, highlight);
                this.mMarker.draw(canvas, arrf[0], arrf[1]);
            }
        }
    }

    public void enableScroll() {
        ViewParent viewParent = this.getParent();
        if (viewParent != null) {
            viewParent.requestDisallowInterceptTouchEvent(false);
        }
    }

    public ChartAnimator getAnimator() {
        return this.mAnimator;
    }

    public MPPointF getCenter() {
        return MPPointF.getInstance((float)this.getWidth() / 2.0f, (float)this.getHeight() / 2.0f);
    }

    public MPPointF getCenterOfView() {
        return this.getCenter();
    }

    public MPPointF getCenterOffsets() {
        return this.mViewPortHandler.getContentCenter();
    }

    /*
     * Enabled aggressive block sorting
     */
    public Bitmap getChartBitmap() {
        Bitmap bitmap = Bitmap.createBitmap((int)this.getWidth(), (int)this.getHeight(), (Bitmap.Config)Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        Drawable drawable2 = this.getBackground();
        if (drawable2 != null) {
            drawable2.draw(canvas);
        } else {
            canvas.drawColor(-1);
        }
        this.draw(canvas);
        return bitmap;
    }

    public RectF getContentRect() {
        return this.mViewPortHandler.getContentRect();
    }

    public T getData() {
        return this.mData;
    }

    public IValueFormatter getDefaultValueFormatter() {
        return this.mDefaultValueFormatter;
    }

    public Description getDescription() {
        return this.mDescription;
    }

    public float getDragDecelerationFrictionCoef() {
        return this.mDragDecelerationFrictionCoef;
    }

    public float getExtraBottomOffset() {
        return this.mExtraBottomOffset;
    }

    public float getExtraLeftOffset() {
        return this.mExtraLeftOffset;
    }

    public float getExtraRightOffset() {
        return this.mExtraRightOffset;
    }

    public float getExtraTopOffset() {
        return this.mExtraTopOffset;
    }

    public Highlight getHighlightByTouchPoint(float f, float f2) {
        if (this.mData == null) {
            Log.e((String)"MPAndroidChart", (String)"Can't select by touch. No data set.");
            return null;
        }
        return this.getHighlighter().getHighlight(f, f2);
    }

    public Highlight[] getHighlighted() {
        return this.mIndicesToHighlight;
    }

    public IHighlighter getHighlighter() {
        return this.mHighlighter;
    }

    public ArrayList<Runnable> getJobs() {
        return this.mJobs;
    }

    public Legend getLegend() {
        return this.mLegend;
    }

    public LegendRenderer getLegendRenderer() {
        return this.mLegendRenderer;
    }

    public IMarker getMarker() {
        return this.mMarker;
    }

    protected float[] getMarkerPosition(Highlight highlight) {
        return new float[]{highlight.getDrawX(), highlight.getDrawY()};
    }

    @Deprecated
    public IMarker getMarkerView() {
        return this.getMarker();
    }

    @Override
    public float getMaxHighlightDistance() {
        return this.mMaxHighlightDistance;
    }

    public OnChartGestureListener getOnChartGestureListener() {
        return this.mGestureListener;
    }

    public ChartTouchListener getOnTouchListener() {
        return this.mChartTouchListener;
    }

    public DataRenderer getRenderer() {
        return this.mRenderer;
    }

    public ViewPortHandler getViewPortHandler() {
        return this.mViewPortHandler;
    }

    public XAxis getXAxis() {
        return this.mXAxis;
    }

    public float getXChartMax() {
        return this.mXAxis.mAxisMaximum;
    }

    public float getXChartMin() {
        return this.mXAxis.mAxisMinimum;
    }

    public float getXRange() {
        return this.mXAxis.mAxisRange;
    }

    public float getYMax() {
        return ((ChartData)this.mData).getYMax();
    }

    public float getYMin() {
        return ((ChartData)this.mData).getYMin();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void highlightValue(Highlight highlight, boolean bl) {
        Entry entry = null;
        if (highlight == null) {
            this.mIndicesToHighlight = null;
        } else {
            if (this.mLogEnabled) {
                Log.i((String)"MPAndroidChart", (String)("Highlighted: " + highlight.toString()));
            }
            if ((entry = ((ChartData)this.mData).getEntryForHighlight(highlight)) == null) {
                this.mIndicesToHighlight = null;
                highlight = null;
            } else {
                this.mIndicesToHighlight = new Highlight[]{highlight};
            }
        }
        this.setLastHighlighted(this.mIndicesToHighlight);
        if (bl && this.mSelectionListener != null) {
            if (!this.valuesToHighlight()) {
                this.mSelectionListener.onNothingSelected();
            } else {
                this.mSelectionListener.onValueSelected(entry, highlight);
            }
        }
        this.invalidate();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void init() {
        this.setWillNotDraw(false);
        this.mAnimator = Build.VERSION.SDK_INT < 11 ? new ChartAnimator() : new ChartAnimator(new ValueAnimator.AnimatorUpdateListener(){

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Chart.this.postInvalidate();
            }
        });
        Utils.init(this.getContext());
        this.mMaxHighlightDistance = Utils.convertDpToPixel(500.0f);
        this.mDescription = new Description();
        this.mLegend = new Legend();
        this.mLegendRenderer = new LegendRenderer(this.mViewPortHandler, this.mLegend);
        this.mXAxis = new XAxis();
        this.mDescPaint = new Paint(1);
        this.mInfoPaint = new Paint(1);
        this.mInfoPaint.setColor(Color.rgb((int)247, (int)189, (int)51));
        this.mInfoPaint.setTextAlign(Paint.Align.CENTER);
        this.mInfoPaint.setTextSize(Utils.convertDpToPixel(12.0f));
        if (this.mLogEnabled) {
            Log.i((String)"", (String)"Chart.init()");
        }
    }

    public boolean isDragDecelerationEnabled() {
        return this.mDragDecelerationEnabled;
    }

    public boolean isDrawMarkersEnabled() {
        return this.mDrawMarkers;
    }

    public boolean isHighlightPerTapEnabled() {
        return this.mHighLightPerTapEnabled;
    }

    public boolean isLogEnabled() {
        return this.mLogEnabled;
    }

    public abstract void notifyDataSetChanged();

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mUnbind) {
            this.unbindDrawables((View)this);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void onDraw(Canvas canvas) {
        boolean bl = true;
        if (this.mData == null) {
            if (TextUtils.isEmpty((CharSequence)this.mNoDataText)) return;
            if (!bl) return;
            MPPointF mPPointF = this.getCenter();
            canvas.drawText(this.mNoDataText, mPPointF.x, mPPointF.y, this.mInfoPaint);
            return;
        }
        if (this.mOffsetsCalculated) return;
        this.calculateOffsets();
        this.mOffsetsCalculated = true;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            this.getChildAt(i).layout(n, n2, n3, n4);
        }
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        int n3 = (int)Utils.convertDpToPixel(50.0f);
        this.setMeasuredDimension(Math.max(this.getSuggestedMinimumWidth(), Chart.resolveSize((int)n3, (int)n)), Math.max(this.getSuggestedMinimumHeight(), Chart.resolveSize((int)n3, (int)n2)));
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        if (this.mLogEnabled) {
            Log.i((String)"MPAndroidChart", (String)"OnSizeChanged()");
        }
        if (n > 0 && n2 > 0 && n < 10000 && n2 < 10000) {
            this.mViewPortHandler.setChartDimens(n, n2);
            if (this.mLogEnabled) {
                Log.i((String)"MPAndroidChart", (String)("Setting chart dimens, width: " + n + ", height: " + n2));
            }
            Iterator<Runnable> iterator = this.mJobs.iterator();
            while (iterator.hasNext()) {
                this.post(iterator.next());
            }
            this.mJobs.clear();
        }
        this.notifyDataSetChanged();
        super.onSizeChanged(n, n2, n3, n4);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setData(T object) {
        block4: {
            block3: {
                this.mData = object;
                this.mOffsetsCalculated = false;
                if (object == null) break block3;
                this.setupDefaultFormatter(((ChartData)object).getYMin(), ((ChartData)object).getYMax());
                for (IDataSet iDataSet : ((ChartData)this.mData).getDataSets()) {
                    if (!iDataSet.needsFormatter() && iDataSet.getValueFormatter() != this.mDefaultValueFormatter) continue;
                    iDataSet.setValueFormatter(this.mDefaultValueFormatter);
                }
                this.notifyDataSetChanged();
                if (this.mLogEnabled) break block4;
            }
            return;
        }
        Log.i((String)"MPAndroidChart", (String)"Data is set.");
    }

    public void setDescription(Description description) {
        this.mDescription = description;
    }

    public void setDragDecelerationEnabled(boolean bl) {
        this.mDragDecelerationEnabled = bl;
    }

    public void setDragDecelerationFrictionCoef(float f) {
        float f2 = f;
        if (f < 0.0f) {
            f2 = 0.0f;
        }
        f = f2;
        if (f2 >= 1.0f) {
            f = 0.999f;
        }
        this.mDragDecelerationFrictionCoef = f;
    }

    @Deprecated
    public void setDrawMarkerViews(boolean bl) {
        this.setDrawMarkers(bl);
    }

    public void setDrawMarkers(boolean bl) {
        this.mDrawMarkers = bl;
    }

    public void setExtraBottomOffset(float f) {
        this.mExtraBottomOffset = Utils.convertDpToPixel(f);
    }

    public void setExtraLeftOffset(float f) {
        this.mExtraLeftOffset = Utils.convertDpToPixel(f);
    }

    public void setExtraOffsets(float f, float f2, float f3, float f4) {
        this.setExtraLeftOffset(f);
        this.setExtraTopOffset(f2);
        this.setExtraRightOffset(f3);
        this.setExtraBottomOffset(f4);
    }

    public void setExtraRightOffset(float f) {
        this.mExtraRightOffset = Utils.convertDpToPixel(f);
    }

    public void setExtraTopOffset(float f) {
        this.mExtraTopOffset = Utils.convertDpToPixel(f);
    }

    public void setHardwareAccelerationEnabled(boolean bl) {
        if (Build.VERSION.SDK_INT >= 11) {
            if (bl) {
                this.setLayerType(2, null);
                return;
            }
            this.setLayerType(1, null);
            return;
        }
        Log.e((String)"MPAndroidChart", (String)"Cannot enable/disable hardware acceleration for devices below API level 11.");
    }

    public void setHighlightPerTapEnabled(boolean bl) {
        this.mHighLightPerTapEnabled = bl;
    }

    public void setHighlighter(ChartHighlighter chartHighlighter) {
        this.mHighlighter = chartHighlighter;
    }

    protected void setLastHighlighted(Highlight[] arrhighlight) {
        if (arrhighlight == null || arrhighlight.length <= 0 || arrhighlight[0] == null) {
            this.mChartTouchListener.setLastHighlighted(null);
            return;
        }
        this.mChartTouchListener.setLastHighlighted(arrhighlight[0]);
    }

    public void setLogEnabled(boolean bl) {
        this.mLogEnabled = bl;
    }

    public void setMarker(IMarker iMarker) {
        this.mMarker = iMarker;
    }

    @Deprecated
    public void setMarkerView(IMarker iMarker) {
        this.setMarker(iMarker);
    }

    public void setMaxHighlightDistance(float f) {
        this.mMaxHighlightDistance = Utils.convertDpToPixel(f);
    }

    public void setNoDataText(String string2) {
        this.mNoDataText = string2;
    }

    public void setNoDataTextColor(int n) {
        this.mInfoPaint.setColor(n);
    }

    public void setNoDataTextTypeface(Typeface typeface) {
        this.mInfoPaint.setTypeface(typeface);
    }

    public void setOnChartGestureListener(OnChartGestureListener onChartGestureListener) {
        this.mGestureListener = onChartGestureListener;
    }

    public void setOnChartValueSelectedListener(OnChartValueSelectedListener onChartValueSelectedListener) {
        this.mSelectionListener = onChartValueSelectedListener;
    }

    public void setOnTouchListener(ChartTouchListener chartTouchListener) {
        this.mChartTouchListener = chartTouchListener;
    }

    public void setPaint(Paint paint, int n) {
        switch (n) {
            default: {
                return;
            }
            case 7: {
                this.mInfoPaint = paint;
                return;
            }
            case 11: 
        }
        this.mDescPaint = paint;
    }

    public void setRenderer(DataRenderer dataRenderer) {
        if (dataRenderer != null) {
            this.mRenderer = dataRenderer;
        }
    }

    public void setTouchEnabled(boolean bl) {
        this.mTouchEnabled = bl;
    }

    public void setUnbindEnabled(boolean bl) {
        this.mUnbind = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void setupDefaultFormatter(float f, float f2) {
        f = this.mData == null || ((ChartData)this.mData).getEntryCount() < 2 ? Math.max(Math.abs(f), Math.abs(f2)) : Math.abs(f2 - f);
        int n = Utils.getDecimals(f);
        this.mDefaultValueFormatter.setup(n);
    }

    public boolean valuesToHighlight() {
        return this.mIndicesToHighlight != null && this.mIndicesToHighlight.length > 0 && this.mIndicesToHighlight[0] != null;
    }

}

