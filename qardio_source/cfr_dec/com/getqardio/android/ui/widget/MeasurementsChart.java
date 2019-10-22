/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.CornerPathEffect
 *  android.graphics.LinearGradient
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.PathEffect
 *  android.graphics.Rect
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.Display
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.MotionEvent
 *  android.view.Surface
 *  android.view.SurfaceHolder
 *  android.view.SurfaceHolder$Callback
 *  android.view.SurfaceView
 *  android.view.View
 *  android.view.WindowManager
 *  android.widget.OverScroller
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.OverScroller;
import com.getqardio.android.ui.widget.MeasurementsChartAdapter;
import com.getqardio.android.utils.Utils;

public class MeasurementsChart
extends SurfaceView
implements GestureDetector.OnGestureListener,
SurfaceHolder.Callback,
MeasurementsChartAdapter.OnDataChangedListener {
    private MeasurementsChartAdapter adapter;
    private Paint chartPaint;
    private DrawingThread drawingThread;
    private GestureDetector gestureDetector;
    private SparseArray<GradientDataContainer> gradientDataCache = new SparseArray();
    private Paint gridPaint;
    private volatile int headerHeight;
    private volatile int measureBarWidth;
    private OnChartScrollListener onChartScrollListener;
    private OnFirstVisibleDateListener onFirstVisibleDateListener;
    private OverScroller scroller;
    private int valuesLabelIndent;
    private Paint valuesPaint;
    private int xOffset;

    public MeasurementsChart(Context context) {
        super(context);
        this.init(context);
    }

    public MeasurementsChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context);
    }

    public MeasurementsChart(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(context);
    }

    static /* synthetic */ void access$000(MeasurementsChart measurementsChart, Canvas canvas) {
        measurementsChart.drawChart(canvas);
    }

    private int calculateGradient(int n, int n2, int n3, int n4, int n5) {
        int n6 = Color.red((int)n2);
        int n7 = Color.green((int)n2);
        n2 = Color.blue((int)n2);
        int n8 = Color.red((int)n4);
        int n9 = Color.green((int)n4);
        n4 = Color.blue((int)n4);
        float f = (float)(n5 - n3) / (float)(n - n3);
        return Color.rgb((int)((int)((float)n8 - (float)(n8 - n6) * f)), (int)((int)((float)n9 - (float)(n9 - n7) * f)), (int)((int)((float)n4 - (float)(n4 - n2) * f)));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void drawChart(Canvas canvas) {
        Object object = this.getBackground();
        if (object != null) {
            object.draw(canvas);
        } else {
            canvas.drawColor(-1);
        }
        if (this.adapter == null || this.measureBarWidth == 0) {
            return;
        }
        object = this.adapter;
        synchronized (object) {
            Object object2;
            Object object3;
            int n;
            int n2;
            Object object4;
            int n3;
            int n4 = this.adapter.getMaxValue();
            int n5 = n4 - this.adapter.getMinValue();
            int n6 = (int)(0.5f * (float)this.measureBarWidth);
            float f = n5 != 0 ? (float)((canvas.getHeight() - this.getPaddingTop() - this.getPaddingBottom() - this.headerHeight) / n5) : 1.0f;
            n5 = Math.max(0, -this.xOffset);
            int n7 = canvas.getWidth() - Math.max(0, this.xOffset + canvas.getWidth() - Math.max(this.adapter.getMeasuresCount(), 8) * this.measureBarWidth);
            int n8 = (int)(0.45f * (float)this.headerHeight);
            int n9 = (int)(0.85f * (float)this.headerHeight);
            this.gridPaint.setColor(-1710106);
            canvas.drawLine((float)n5, (float)this.headerHeight, (float)n7, (float)this.headerHeight, this.gridPaint);
            for (n3 = 0; n3 <= 8; ++n3) {
                n2 = this.xOffset / this.measureBarWidth + n3;
                n = n5;
                if (n2 >= 0) {
                    n = n5;
                    if (n2 < Math.max(this.adapter.getMeasuresCount(), 8)) {
                        object2 = this.gridPaint;
                        n5 = n2 % 2 == 0 ? -132098 : -1118482;
                        object2.setColor(n5);
                        n = this.measureBarWidth * n3 - this.xOffset % this.measureBarWidth;
                        canvas.drawRect((float)n, (float)this.headerHeight, (float)(this.measureBarWidth + n), (float)canvas.getHeight(), this.gridPaint);
                        object4 = this.adapter.getLabels(n2);
                        this.gridPaint.setColor(-6773851);
                        object3 = new TextPaint();
                        object2 = TextUtils.ellipsize((CharSequence)String.valueOf(object4[0]), (TextPaint)object3, (float)50.0f, (TextUtils.TruncateAt)TextUtils.TruncateAt.END);
                        object4 = TextUtils.ellipsize((CharSequence)String.valueOf((Object)object4[1]), (TextPaint)object3, (float)50.0f, (TextUtils.TruncateAt)TextUtils.TruncateAt.END);
                        canvas.drawText(String.valueOf(object2), (float)(n + n6), (float)n8, this.gridPaint);
                        canvas.drawText(String.valueOf(object4), (float)(n + n6), (float)n9, this.gridPaint);
                    }
                }
                n5 = n;
            }
            n8 = 0;
            n3 = n7;
            while (n8 < this.adapter.getChartsCount()) {
                n = n5;
                n7 = n3;
                if (this.adapter.getValuesCount(n8) > 0) {
                    int n10;
                    int n11;
                    int n12;
                    int n13;
                    n = n7 = this.xOffset / this.measureBarWidth - 1;
                    if (n7 < 0) {
                        n = 0;
                    }
                    n9 = Math.min(this.adapter.getMeasuresCount() - 1, (canvas.getWidth() + this.xOffset) / this.measureBarWidth + 1);
                    do {
                        n7 = n9;
                        if (n <= 0) break;
                        if (this.adapter.hasValue(n8, n)) {
                            n7 = n9;
                            break;
                        }
                        --n;
                    } while (true);
                    do {
                        if (n7 >= this.adapter.getMeasuresCount() - 1 || this.adapter.hasValue(n8, n7)) {
                            object2 = (GradientDataContainer)this.gradientDataCache.get(n8);
                            if (object2 != null) {
                                n9 = this.headerHeight;
                                n2 = this.getPaddingTop();
                                n11 = (int)((float)(n4 - object2.values[0]) * f);
                                n13 = this.headerHeight;
                                n12 = this.getPaddingTop();
                                n10 = (int)((float)(n4 - object2.values[object2.values.length - 1]) * f);
                                object4 = new LinearGradient(0.0f, (float)(n9 + n2 + n11), 0.0f, (float)(n13 + n12 + n10), object2.colors, object2.positions, Shader.TileMode.CLAMP);
                                this.chartPaint.setShader((Shader)object4);
                                break;
                            }
                            this.chartPaint.setShader(null);
                            break;
                        }
                        ++n7;
                    } while (true);
                    object4 = new Path();
                    n2 = 1;
                    n11 = 0;
                    n9 = n;
                    n = n5;
                    n5 = n2;
                    do {
                        if (n9 <= n7) {
                            n2 = n5;
                            if (this.adapter.hasValue(n8, n9)) {
                                n11 = 1;
                                n3 = this.adapter.getValue(n8, n9);
                                object3 = this.adapter.getValueLabel(n8, n9);
                                n = this.measureBarWidth * n9 + n6 - this.xOffset;
                                n13 = (int)((float)(this.headerHeight + this.getPaddingTop()) + (float)(n4 - n3) * f);
                                if (n5 != 0) {
                                    object4.moveTo((float)n, (float)n13);
                                    Paint.Style style2 = this.chartPaint.getStyle();
                                    this.chartPaint.setStyle(Paint.Style.FILL);
                                    if (this.adapter.getValuesCount(n8) <= 1) {
                                        canvas.drawCircle((float)n, (float)n13, this.chartPaint.getStrokeWidth() * 2.0f, this.chartPaint);
                                    } else {
                                        float f2 = n;
                                        float f3 = n13;
                                        canvas.drawCircle(f2, f3, this.chartPaint.getStrokeWidth() / 2.0f, this.chartPaint);
                                    }
                                    this.chartPaint.setStyle(style2);
                                    n5 = 0;
                                } else {
                                    object4.lineTo((float)n, (float)n13);
                                }
                                n3 = object2 != null ? this.getValueColor(n3, (GradientDataContainer)object2) : this.chartPaint.getColor();
                                n2 = this.getPreviousValueIndex(n8, n9);
                                n12 = this.getNextValueIndex(n8, n9);
                                n10 = this.measureBarWidth;
                                int n14 = this.xOffset;
                                int n15 = (int)((float)(this.headerHeight + this.getPaddingTop()) + (float)(n4 - this.adapter.getValue(n8, n2)) * f);
                                int n16 = this.measureBarWidth;
                                int n17 = this.xOffset;
                                int n18 = (int)((float)(this.headerHeight + this.getPaddingTop()) + (float)(n4 - this.adapter.getValue(n8, n12)) * f);
                                this.drawMeasureValue(canvas, n, n13, (String)object3, n3, n10 * n2 + n6 - n14, n15, n16 * n12 + n6 - n17, n18);
                                n2 = n5;
                                n3 = n13;
                            }
                        } else {
                            if (n11 != 0) {
                                object4.lineTo((float)n, (float)n3);
                            }
                            canvas.drawPath(object4, this.chartPaint);
                            n7 = n3;
                            break;
                        }
                        ++n9;
                        n5 = n2;
                    } while (true);
                }
                ++n8;
                n5 = n;
                n3 = n7;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawMeasureValue(Canvas canvas, float f, float f2, String string2, int n, int n2, int n3, int n4, int n5) {
        float f3 = 0.0f;
        float f4 = f - (float)n2 != 0.0f ? (f2 - (float)n3) / (f - (float)n2) : 0.0f;
        if ((float)n4 - f != 0.0f) {
            f3 = (f2 - (float)n5) / ((float)n4 - f);
        }
        f4 = Math.max(Math.max(f4, f3) + 0.5f, -0.5f);
        this.valuesPaint.setColor(n);
        canvas.drawText(string2, f, f2 - (float)this.valuesLabelIndent * f4, this.valuesPaint);
    }

    private int getMaxXOffset() {
        int n = 0;
        if (this.adapter != null) {
            n = Math.max(this.measureBarWidth * this.adapter.getMeasuresCount() - this.getWidth(), 0);
        }
        return n;
    }

    private int getNextValueIndex(int n, int n2) {
        for (int i = n2 + 1; i < this.adapter.getMeasuresCount(); ++i) {
            if (!this.adapter.hasValue(n, i)) continue;
            return i;
        }
        return n2;
    }

    private int getPreviousValueIndex(int n, int n2) {
        for (int i = n2 - 1; i >= 0; --i) {
            if (!this.adapter.hasValue(n, i)) continue;
            return i;
        }
        return n2;
    }

    private int getValueColor(int n, GradientDataContainer gradientDataContainer) {
        if (n >= gradientDataContainer.values[0]) {
            return gradientDataContainer.colors[0];
        }
        if (n <= gradientDataContainer.values[gradientDataContainer.values.length - 1]) {
            return gradientDataContainer.colors[gradientDataContainer.colors.length - 1];
        }
        for (int i = 1; i < gradientDataContainer.values.length; ++i) {
            if (n < gradientDataContainer.values[i]) continue;
            return this.calculateGradient(gradientDataContainer.values[i - 1], gradientDataContainer.colors[i - 1], gradientDataContainer.values[i], gradientDataContainer.colors[i], n);
        }
        return -16777216;
    }

    private void init(Context context) {
        this.getHolder().addCallback((SurfaceHolder.Callback)this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager)context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        this.xOffset = 0;
        this.headerHeight = (int)context.getResources().getDimension(2131427445);
        this.gridPaint = new Paint();
        this.gridPaint.setColor(-3355444);
        this.gridPaint.setStrokeWidth(displayMetrics.density * 1.0f);
        this.gridPaint.setTextSize(context.getResources().getDimension(2131427673));
        this.gridPaint.setTypeface(Typeface.DEFAULT);
        this.gridPaint.setTextAlign(Paint.Align.CENTER);
        this.gridPaint.setAntiAlias(true);
        this.chartPaint = new Paint();
        this.chartPaint.setColor(-65536);
        this.chartPaint.setStrokeWidth(displayMetrics.density * 1.0f);
        this.chartPaint.setFakeBoldText(true);
        this.chartPaint.setDither(false);
        this.chartPaint.setStyle(Paint.Style.STROKE);
        this.chartPaint.setStrokeCap(Paint.Cap.ROUND);
        this.chartPaint.setPathEffect((PathEffect)new CornerPathEffect(30.0f * displayMetrics.density));
        this.chartPaint.setAntiAlias(true);
        this.valuesPaint = new Paint();
        this.valuesPaint.setColor(-16777216);
        this.valuesPaint.setTextSize(context.getResources().getDimension(2131427673));
        this.valuesPaint.setTypeface(Typeface.DEFAULT);
        this.valuesPaint.setTextAlign(Paint.Align.CENTER);
        this.valuesPaint.setFakeBoldText(true);
        this.valuesPaint.setAntiAlias(true);
        this.valuesLabelIndent = (int)context.getResources().getDimension(2131427446);
        this.scroller = new OverScroller(context);
        this.gestureDetector = new GestureDetector((GestureDetector.OnGestureListener)this);
    }

    private void setXOffset(int n) {
        if (this.onFirstVisibleDateListener != null && this.measureBarWidth > 0) {
            int n2 = this.xOffset / this.measureBarWidth;
            int n3 = n / this.measureBarWidth;
            if (n3 > 0 && n3 != n2) {
                this.onFirstVisibleDateListener.onFirstVisibleDateChanged(this.getMeasurementDate(n3));
            }
        }
        this.xOffset = n;
    }

    public void clearChartGradients() {
        this.gradientDataCache.clear();
    }

    public void computeScroll() {
        super.computeScroll();
        if (this.scroller.computeScrollOffset()) {
            this.setXOffset(this.scroller.getCurrX());
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    public MeasurementsChartAdapter getAdapter() {
        return this.adapter;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long getFirstVisibleDate() {
        if (this.adapter == null) {
            return -1L;
        }
        MeasurementsChartAdapter measurementsChartAdapter = this.adapter;
        synchronized (measurementsChartAdapter) {
            int n = this.measureBarWidth > 0 ? this.xOffset / this.measureBarWidth : 0;
            return this.adapter.getMeasurementDate(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long getMeasurementDate(int n) {
        if (this.adapter == null) return 0L;
        MeasurementsChartAdapter measurementsChartAdapter = this.adapter;
        synchronized (measurementsChartAdapter) {
            return this.adapter.getMeasurementDate(n);
        }
    }

    @Override
    public void onDataChanged(int n, int n2) {
        if (n > n2) {
            this.scrollToEnd();
        }
    }

    public boolean onDown(MotionEvent motionEvent) {
        this.scroller.forceFinished(true);
        return true;
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        this.scroller.forceFinished(true);
        this.scroller.fling(this.xOffset, 0, (int)(-f), 0, 0, this.getMaxXOffset(), 0, this.getHeight(), this.measureBarWidth, 0);
        ViewCompat.postInvalidateOnAnimation((View)this);
        return true;
    }

    public void onLongPress(MotionEvent motionEvent) {
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (this.adapter != null) {
            int n = this.xOffset + (int)f;
            int n2 = this.getMaxXOffset();
            if (n <= n2) {
                n2 = n;
                if (n < 0) {
                    n2 = 0;
                }
            }
            this.setXOffset(n2);
            if (this.onChartScrollListener != null) {
                this.onChartScrollListener.onChartScroll(this.xOffset);
            }
        }
        return true;
    }

    public void onShowPress(MotionEvent motionEvent) {
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.gestureDetector.onTouchEvent(motionEvent) || super.onTouchEvent(motionEvent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void scrollToDate(long l) {
        int n;
        block6: {
            n = 0;
            int n2 = 0;
            if (this.adapter == null) break block6;
            MeasurementsChartAdapter measurementsChartAdapter = this.adapter;
            synchronized (measurementsChartAdapter) {
                int n3 = this.adapter.getMeasuresCount() - 8;
                do {
                    block7: {
                        n = n2;
                        if (n3 < 0) break block6;
                        if (this.adapter.getMeasurementDate(n3) > l) break block7;
                        n = n3 * this.measureBarWidth;
                        break;
                    }
                    --n3;
                } while (true);
            }
        }
        this.setXOffset(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void scrollToEnd() {
        if (this.adapter != null) {
            MeasurementsChartAdapter measurementsChartAdapter = this.adapter;
            synchronized (measurementsChartAdapter) {
                this.setXOffset(Math.max(0, this.adapter.getMeasuresCount() - 8) * this.measureBarWidth);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setAdapter(MeasurementsChartAdapter measurementsChartAdapter, int n) {
        long l = this.getFirstVisibleDate();
        MeasurementsChartAdapter measurementsChartAdapter2 = this.adapter;
        this.adapter = measurementsChartAdapter;
        this.scroller.forceFinished(true);
        switch (n) {
            case 2: {
                this.setXOffset(0);
                break;
            }
            case 1: {
                this.scrollToEnd();
                break;
            }
            case 3: {
                this.scrollToDate(l);
                break;
            }
        }
        if (measurementsChartAdapter2 != null) {
            measurementsChartAdapter2.setOnDataCountChangedListener(null);
        }
        if (this.adapter != null) {
            this.adapter.setOnDataCountChangedListener(this);
        }
    }

    public void setChartColor(int n) {
        this.chartPaint.setColor(n);
    }

    public void setChartGradient(int n, int[] object, int[] arrn) {
        float[] arrf = new float[arrn.length];
        for (int i = 0; i < arrn.length; ++i) {
            arrf[i] = (float)(arrn[0] - arrn[i]) / (float)(arrn[0] - arrn[arrn.length - 1]);
        }
        GradientDataContainer gradientDataContainer = (GradientDataContainer)this.gradientDataCache.get(n);
        if (gradientDataContainer == null) {
            object = new GradientDataContainer((int[])object, arrn, arrf);
            this.gradientDataCache.put(n, object);
            return;
        }
        gradientDataContainer.colors = object;
        gradientDataContainer.values = arrn;
        gradientDataContainer.positions = arrf;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setMonthVisible(int n) {
        if (this.adapter == null) {
            return;
        }
        MeasurementsChartAdapter measurementsChartAdapter = this.adapter;
        synchronized (measurementsChartAdapter) {
            int n2 = 0;
            do {
                block8: {
                    block7: {
                        if (n2 >= this.adapter.getMeasuresCount()) break block7;
                        if (Utils.getMonthNumber(this.adapter.getMeasurementDate(n2)) != n) break block8;
                        this.scroller.forceFinished(true);
                        this.xOffset = Math.min(this.measureBarWidth * n2, this.getMaxXOffset());
                    }
                    return;
                }
                ++n2;
            } while (true);
        }
    }

    public void setOnChartScrollListener(OnChartScrollListener onChartScrollListener) {
        this.onChartScrollListener = onChartScrollListener;
    }

    public void setOnFirstVisibleDateListener(OnFirstVisibleDateListener onFirstVisibleDateListener) {
        this.onFirstVisibleDateListener = onFirstVisibleDateListener;
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int n, int n2, int n3) {
        this.measureBarWidth = n2 / 8;
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.drawingThread = new DrawingThread(this.getHolder());
        this.drawingThread.setRunning(true);
        this.drawingThread.start();
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.drawingThread.setRunning(false);
        try {
            this.drawingThread.join();
            return;
        }
        catch (InterruptedException interruptedException) {
            return;
        }
    }

    public class DrawingThread
    extends Thread {
        private volatile boolean running;
        private final SurfaceHolder surfaceHolder;

        public DrawingThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            var1_1 = null;
            do {
                if (!this.running) {
                    return;
                }
                var2_4 = var1_1;
                var3_6 = var1_1;
                var5_8 = this.surfaceHolder.getSurface();
                var4_7 = var1_1;
                if (var5_8 == null) ** GOTO lbl49
                var4_7 = var1_1;
                var2_4 = var1_1;
                var3_6 = var1_1;
                if (!var5_8.isValid()) ** GOTO lbl49
                var2_4 = var1_1;
                var3_6 = var1_1;
                var2_4 = var4_7 = this.surfaceHolder.lockCanvas(null);
                var3_6 = var4_7;
                var1_1 = this.surfaceHolder;
                var2_4 = var4_7;
                var3_6 = var4_7;
                synchronized (var1_1) {
                    if (var4_7 != null) {
                    }
                    ** GOTO lbl43
                }
                {
                    catch (Exception var1_2) {
                        var3_6 = var2_4;
                        Log.e((String)"Measurement chart", (String)"[Drawing thread]", (Throwable)var1_2);
                        var1_1 = var2_4;
                        if (var2_4 != null) {
                            var1_1 = var2_4;
                            if (this.surfaceHolder != null) {
                                this.surfaceHolder.unlockCanvasAndPost(var2_4);
                                var1_1 = var2_4;
                            }
                        }
                        break block19;
                    }
                    catch (Throwable var1_3) {
                        if (var3_6 != null && this.surfaceHolder != null) {
                            this.surfaceHolder.unlockCanvasAndPost(var3_6);
                        }
                        throw var1_3;
                    }
                    {
                        block19: {
                            try {
                                MeasurementsChart.access$000(MeasurementsChart.this, var4_7);
lbl43:
                                // 2 sources
                                // MONITOREXIT [1, 6, 8, 11] lbl44 : MonitorExitStatement: MONITOREXIT : var1_1
                            }
                            catch (Throwable var5_9) {}
                            {
                                // MONITOREXIT [3, 6, 8, 11] lbl57 : MonitorExitStatement: MONITOREXIT : var1_1
                                var2_4 = var4_7;
                                var3_6 = var4_7;
                            }
                            {
                                throw var5_9;
                            }
lbl49:
                            // 3 sources
                            var1_1 = var4_7;
                            if (var4_7 != null) {
                                var1_1 = var4_7;
                                if (this.surfaceHolder != null) {
                                    this.surfaceHolder.unlockCanvasAndPost(var4_7);
                                    var1_1 = var4_7;
                                }
                            }
                        }
                        try {
                            Thread.sleep(30L);
                        }
                        catch (InterruptedException var2_5) {}
                        continue;
                    }
                }
                break;
            } while (true);
        }

        public void setRunning(boolean bl) {
            this.running = bl;
        }
    }

    private static class GradientDataContainer {
        int[] colors;
        float[] positions;
        int[] values;

        public GradientDataContainer(int[] arrn, int[] arrn2, float[] arrf) {
            this.colors = arrn;
            this.values = arrn2;
            this.positions = arrf;
        }
    }

    public static interface OnChartScrollListener {
        public void onChartScroll(int var1);
    }

    public static interface OnFirstVisibleDateListener {
        public void onFirstVisibleDateChanged(long var1);
    }

}

