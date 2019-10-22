/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.graphics.Matrix
 *  android.util.Log
 *  android.view.GestureDetector
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.animation.AnimationUtils
 */
package com.github.mikephil.charting.listener;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class BarLineChartTouchListener
extends ChartTouchListener<BarLineChartBase<? extends BarLineScatterCandleBubbleData<? extends IBarLineScatterCandleBubbleDataSet<? extends Entry>>>> {
    private IDataSet mClosestDataSetToTouch;
    private MPPointF mDecelerationCurrentPoint;
    private long mDecelerationLastTime = 0L;
    private MPPointF mDecelerationVelocity;
    private float mDragTriggerDist;
    private Matrix mMatrix = new Matrix();
    private float mMinScalePointerDistance;
    private float mSavedDist = 1.0f;
    private Matrix mSavedMatrix = new Matrix();
    private float mSavedXDist = 1.0f;
    private float mSavedYDist = 1.0f;
    private MPPointF mTouchPointCenter;
    private MPPointF mTouchStartPoint = MPPointF.getInstance(0.0f, 0.0f);
    private VelocityTracker mVelocityTracker;

    public BarLineChartTouchListener(BarLineChartBase<? extends BarLineScatterCandleBubbleData<? extends IBarLineScatterCandleBubbleDataSet<? extends Entry>>> barLineChartBase, Matrix matrix, float f) {
        super(barLineChartBase);
        this.mTouchPointCenter = MPPointF.getInstance(0.0f, 0.0f);
        this.mDecelerationCurrentPoint = MPPointF.getInstance(0.0f, 0.0f);
        this.mDecelerationVelocity = MPPointF.getInstance(0.0f, 0.0f);
        this.mMatrix = matrix;
        this.mDragTriggerDist = Utils.convertDpToPixel(f);
        this.mMinScalePointerDistance = Utils.convertDpToPixel(3.5f);
    }

    private static float getXDist(MotionEvent motionEvent) {
        return Math.abs(motionEvent.getX(0) - motionEvent.getX(1));
    }

    private static float getYDist(MotionEvent motionEvent) {
        return Math.abs(motionEvent.getY(0) - motionEvent.getY(1));
    }

    private boolean inverted() {
        return this.mClosestDataSetToTouch == null && ((BarLineChartBase)this.mChart).isAnyAxisInverted() || this.mClosestDataSetToTouch != null && ((BarLineChartBase)this.mChart).isInverted(this.mClosestDataSetToTouch.getAxisDependency());
    }

    private static void midPoint(MPPointF mPPointF, MotionEvent motionEvent) {
        float f = motionEvent.getX(0);
        float f2 = motionEvent.getX(1);
        float f3 = motionEvent.getY(0);
        float f4 = motionEvent.getY(1);
        mPPointF.x = (f + f2) / 2.0f;
        mPPointF.y = (f3 + f4) / 2.0f;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void performDrag(MotionEvent motionEvent) {
        float f;
        float f2;
        this.mLastGesture = ChartTouchListener.ChartGesture.DRAG;
        this.mMatrix.set(this.mSavedMatrix);
        OnChartGestureListener onChartGestureListener = ((BarLineChartBase)this.mChart).getOnChartGestureListener();
        if (this.inverted()) {
            if (this.mChart instanceof HorizontalBarChart) {
                f = -(motionEvent.getX() - this.mTouchStartPoint.x);
                f2 = motionEvent.getY() - this.mTouchStartPoint.y;
            } else {
                f = motionEvent.getX() - this.mTouchStartPoint.x;
                f2 = -(motionEvent.getY() - this.mTouchStartPoint.y);
            }
        } else {
            f = motionEvent.getX() - this.mTouchStartPoint.x;
            f2 = motionEvent.getY() - this.mTouchStartPoint.y;
        }
        this.mMatrix.postTranslate(f, f2);
        if (onChartGestureListener != null) {
            onChartGestureListener.onChartTranslate(motionEvent, f, f2);
        }
    }

    private void performHighlightDrag(MotionEvent object) {
        if ((object = ((BarLineChartBase)this.mChart).getHighlightByTouchPoint(object.getX(), object.getY())) != null && !((Highlight)object).equalTo(this.mLastHighlighted)) {
            this.mLastHighlighted = object;
            ((BarLineChartBase)this.mChart).highlightValue((Highlight)object, true);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void performZoom(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() >= 2) {
            OnChartGestureListener onChartGestureListener = ((BarLineChartBase)this.mChart).getOnChartGestureListener();
            float f = BarLineChartTouchListener.spacing(motionEvent);
            if (f > this.mMinScalePointerDistance) {
                MPPointF mPPointF = this.getTrans(this.mTouchPointCenter.x, this.mTouchPointCenter.y);
                ViewPortHandler viewPortHandler = ((BarLineChartBase)this.mChart).getViewPortHandler();
                if (this.mTouchMode == 4) {
                    this.mLastGesture = ChartTouchListener.ChartGesture.PINCH_ZOOM;
                    float f2 = f / this.mSavedDist;
                    boolean bl = f2 < 1.0f;
                    boolean bl2 = bl ? viewPortHandler.canZoomOutMoreX() : viewPortHandler.canZoomInMoreX();
                    boolean bl3 = bl ? viewPortHandler.canZoomOutMoreY() : viewPortHandler.canZoomInMoreY();
                    f = ((BarLineChartBase)this.mChart).isScaleXEnabled() ? f2 : 1.0f;
                    if (!((BarLineChartBase)this.mChart).isScaleYEnabled()) {
                        f2 = 1.0f;
                    }
                    if (bl3 || bl2) {
                        this.mMatrix.set(this.mSavedMatrix);
                        this.mMatrix.postScale(f, f2, mPPointF.x, mPPointF.y);
                        if (onChartGestureListener != null) {
                            onChartGestureListener.onChartScale(motionEvent, f, f2);
                        }
                    }
                } else if (this.mTouchMode == 2 && ((BarLineChartBase)this.mChart).isScaleXEnabled()) {
                    this.mLastGesture = ChartTouchListener.ChartGesture.X_ZOOM;
                    f = BarLineChartTouchListener.getXDist(motionEvent) / this.mSavedXDist;
                    boolean bl = f < 1.0f;
                    boolean bl4 = bl ? viewPortHandler.canZoomOutMoreX() : viewPortHandler.canZoomInMoreX();
                    if (bl4) {
                        this.mMatrix.set(this.mSavedMatrix);
                        this.mMatrix.postScale(f, 1.0f, mPPointF.x, mPPointF.y);
                        if (onChartGestureListener != null) {
                            onChartGestureListener.onChartScale(motionEvent, f, 1.0f);
                        }
                    }
                } else if (this.mTouchMode == 3 && ((BarLineChartBase)this.mChart).isScaleYEnabled()) {
                    this.mLastGesture = ChartTouchListener.ChartGesture.Y_ZOOM;
                    f = BarLineChartTouchListener.getYDist(motionEvent) / this.mSavedYDist;
                    boolean bl = f < 1.0f;
                    boolean bl5 = bl ? viewPortHandler.canZoomOutMoreY() : viewPortHandler.canZoomInMoreY();
                    if (bl5) {
                        this.mMatrix.set(this.mSavedMatrix);
                        this.mMatrix.postScale(1.0f, f, mPPointF.x, mPPointF.y);
                        if (onChartGestureListener != null) {
                            onChartGestureListener.onChartScale(motionEvent, 1.0f, f);
                        }
                    }
                }
                MPPointF.recycleInstance(mPPointF);
            }
        }
    }

    private void saveTouchStart(MotionEvent motionEvent) {
        this.mSavedMatrix.set(this.mMatrix);
        this.mTouchStartPoint.x = motionEvent.getX();
        this.mTouchStartPoint.y = motionEvent.getY();
        this.mClosestDataSetToTouch = ((BarLineChartBase)this.mChart).getDataSetByTouchPoint(motionEvent.getX(), motionEvent.getY());
    }

    private static float spacing(MotionEvent motionEvent) {
        float f = motionEvent.getX(0) - motionEvent.getX(1);
        float f2 = motionEvent.getY(0) - motionEvent.getY(1);
        return (float)Math.sqrt(f * f + f2 * f2);
    }

    public void computeScroll() {
        if (this.mDecelerationVelocity.x == 0.0f && this.mDecelerationVelocity.y == 0.0f) {
            return;
        }
        long l = AnimationUtils.currentAnimationTimeMillis();
        MPPointF mPPointF = this.mDecelerationVelocity;
        float f = mPPointF.x;
        mPPointF.x = ((BarLineChartBase)this.mChart).getDragDecelerationFrictionCoef() * f;
        mPPointF = this.mDecelerationVelocity;
        f = mPPointF.y;
        mPPointF.y = ((BarLineChartBase)this.mChart).getDragDecelerationFrictionCoef() * f;
        f = (float)(l - this.mDecelerationLastTime) / 1000.0f;
        float f2 = this.mDecelerationVelocity.x;
        float f3 = this.mDecelerationVelocity.y;
        mPPointF = this.mDecelerationCurrentPoint;
        mPPointF.x += f2 * f;
        mPPointF = this.mDecelerationCurrentPoint;
        mPPointF.y += f3 * f;
        mPPointF = MotionEvent.obtain((long)l, (long)l, (int)2, (float)this.mDecelerationCurrentPoint.x, (float)this.mDecelerationCurrentPoint.y, (int)0);
        this.performDrag((MotionEvent)mPPointF);
        mPPointF.recycle();
        this.mMatrix = ((BarLineChartBase)this.mChart).getViewPortHandler().refresh(this.mMatrix, (View)this.mChart, false);
        this.mDecelerationLastTime = l;
        if ((double)Math.abs(this.mDecelerationVelocity.x) >= 0.01 || (double)Math.abs(this.mDecelerationVelocity.y) >= 0.01) {
            Utils.postInvalidateOnAnimation((View)this.mChart);
            return;
        }
        ((BarLineChartBase)this.mChart).calculateOffsets();
        ((BarLineChartBase)this.mChart).postInvalidate();
        this.stopDeceleration();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public MPPointF getTrans(float f, float f2) {
        ViewPortHandler viewPortHandler = ((BarLineChartBase)this.mChart).getViewPortHandler();
        float f3 = viewPortHandler.offsetLeft();
        if (this.inverted()) {
            f2 = -(f2 - viewPortHandler.offsetTop());
            do {
                return MPPointF.getInstance(f - f3, f2);
                break;
            } while (true);
        }
        f2 = -((float)((BarLineChartBase)this.mChart).getMeasuredHeight() - f2 - viewPortHandler.offsetBottom());
        return MPPointF.getInstance(f - f3, f2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onDoubleTap(MotionEvent motionEvent) {
        float f = 1.4f;
        this.mLastGesture = ChartTouchListener.ChartGesture.DOUBLE_TAP;
        Object object = ((BarLineChartBase)this.mChart).getOnChartGestureListener();
        if (object != null) {
            object.onChartDoubleTapped(motionEvent);
        }
        if (((BarLineChartBase)this.mChart).isDoubleTapToZoomEnabled() && ((BarLineScatterCandleBubbleData)((BarLineChartBase)this.mChart).getData()).getEntryCount() > 0) {
            object = this.getTrans(motionEvent.getX(), motionEvent.getY());
            BarLineChartBase barLineChartBase = (BarLineChartBase)this.mChart;
            float f2 = ((BarLineChartBase)this.mChart).isScaleXEnabled() ? 1.4f : 1.0f;
            if (!((BarLineChartBase)this.mChart).isScaleYEnabled()) {
                f = 1.0f;
            }
            barLineChartBase.zoom(f2, f, ((MPPointF)object).x, ((MPPointF)object).y);
            if (((BarLineChartBase)this.mChart).isLogEnabled()) {
                Log.i((String)"BarlineChartTouch", (String)("Double-Tap, Zooming In, x: " + ((MPPointF)object).x + ", y: " + ((MPPointF)object).y));
            }
            MPPointF.recycleInstance((MPPointF)object);
        }
        return super.onDoubleTap(motionEvent);
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        this.mLastGesture = ChartTouchListener.ChartGesture.FLING;
        OnChartGestureListener onChartGestureListener = ((BarLineChartBase)this.mChart).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.onChartFling(motionEvent, motionEvent2, f, f2);
        }
        return super.onFling(motionEvent, motionEvent2, f, f2);
    }

    public void onLongPress(MotionEvent motionEvent) {
        this.mLastGesture = ChartTouchListener.ChartGesture.LONG_PRESS;
        OnChartGestureListener onChartGestureListener = ((BarLineChartBase)this.mChart).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.onChartLongPressed(motionEvent);
        }
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        this.mLastGesture = ChartTouchListener.ChartGesture.SINGLE_TAP;
        OnChartGestureListener onChartGestureListener = ((BarLineChartBase)this.mChart).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.onChartSingleTapped(motionEvent);
        }
        if (!((BarLineChartBase)this.mChart).isHighlightPerTapEnabled()) {
            return false;
        }
        this.performHighlight(((BarLineChartBase)this.mChart).getHighlightByTouchPoint(motionEvent.getX(), motionEvent.getY()), motionEvent);
        return super.onSingleTapUp(motionEvent);
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"ClickableViewAccessibility"})
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int n = 2;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        if (motionEvent.getActionMasked() == 3 && this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        if (this.mTouchMode == 0) {
            this.mGestureDetector.onTouchEvent(motionEvent);
        }
        if (!(((BarLineChartBase)this.mChart).isDragEnabled() || ((BarLineChartBase)this.mChart).isScaleXEnabled() || ((BarLineChartBase)this.mChart).isScaleYEnabled())) {
            return true;
        }
        switch (motionEvent.getAction() & 0xFF) {
            case 0: {
                this.startAction(motionEvent);
                this.stopDeceleration();
                this.saveTouchStart(motionEvent);
                break;
            }
            case 5: {
                if (motionEvent.getPointerCount() < 2) break;
                ((BarLineChartBase)this.mChart).disableScroll();
                this.saveTouchStart(motionEvent);
                this.mSavedXDist = BarLineChartTouchListener.getXDist(motionEvent);
                this.mSavedYDist = BarLineChartTouchListener.getYDist(motionEvent);
                this.mSavedDist = BarLineChartTouchListener.spacing(motionEvent);
                if (this.mSavedDist > 10.0f) {
                    if (((BarLineChartBase)this.mChart).isPinchZoomEnabled()) {
                        this.mTouchMode = 4;
                    } else if (((BarLineChartBase)this.mChart).isScaleXEnabled() != ((BarLineChartBase)this.mChart).isScaleYEnabled()) {
                        n = ((BarLineChartBase)this.mChart).isScaleXEnabled() ? 2 : 3;
                        this.mTouchMode = n;
                    } else {
                        if (!(this.mSavedXDist > this.mSavedYDist)) {
                            n = 3;
                        }
                        this.mTouchMode = n;
                    }
                }
                BarLineChartTouchListener.midPoint(this.mTouchPointCenter, motionEvent);
                break;
            }
            case 2: {
                if (this.mTouchMode == 1) {
                    ((BarLineChartBase)this.mChart).disableScroll();
                    this.performDrag(motionEvent);
                    break;
                }
                if (this.mTouchMode == 2 || this.mTouchMode == 3 || this.mTouchMode == 4) {
                    ((BarLineChartBase)this.mChart).disableScroll();
                    if (!((BarLineChartBase)this.mChart).isScaleXEnabled() && !((BarLineChartBase)this.mChart).isScaleYEnabled()) break;
                    this.performZoom(motionEvent);
                    break;
                }
                if (this.mTouchMode != 0 || !(Math.abs(BarLineChartTouchListener.distance(motionEvent.getX(), this.mTouchStartPoint.x, motionEvent.getY(), this.mTouchStartPoint.y)) > this.mDragTriggerDist)) break;
                if (((BarLineChartBase)this.mChart).hasNoDragOffset()) {
                    if (!((BarLineChartBase)this.mChart).isFullyZoomedOut() && ((BarLineChartBase)this.mChart).isDragEnabled()) {
                        this.mTouchMode = 1;
                        break;
                    }
                    this.mLastGesture = ChartTouchListener.ChartGesture.DRAG;
                    if (!((BarLineChartBase)this.mChart).isHighlightPerDragEnabled()) break;
                    this.performHighlightDrag(motionEvent);
                    break;
                }
                if (!((BarLineChartBase)this.mChart).isDragEnabled()) break;
                this.mLastGesture = ChartTouchListener.ChartGesture.DRAG;
                this.mTouchMode = 1;
                break;
            }
            case 1: {
                view = this.mVelocityTracker;
                n = motionEvent.getPointerId(0);
                view.computeCurrentVelocity(1000, (float)Utils.getMaximumFlingVelocity());
                float f = view.getYVelocity(n);
                float f2 = view.getXVelocity(n);
                if ((Math.abs(f2) > (float)Utils.getMinimumFlingVelocity() || Math.abs(f) > (float)Utils.getMinimumFlingVelocity()) && this.mTouchMode == 1 && ((BarLineChartBase)this.mChart).isDragDecelerationEnabled()) {
                    this.stopDeceleration();
                    this.mDecelerationLastTime = AnimationUtils.currentAnimationTimeMillis();
                    this.mDecelerationCurrentPoint.x = motionEvent.getX();
                    this.mDecelerationCurrentPoint.y = motionEvent.getY();
                    this.mDecelerationVelocity.x = f2;
                    this.mDecelerationVelocity.y = f;
                    Utils.postInvalidateOnAnimation((View)this.mChart);
                }
                if (this.mTouchMode == 2 || this.mTouchMode == 3 || this.mTouchMode == 4 || this.mTouchMode == 5) {
                    ((BarLineChartBase)this.mChart).calculateOffsets();
                    ((BarLineChartBase)this.mChart).postInvalidate();
                }
                this.mTouchMode = 0;
                ((BarLineChartBase)this.mChart).enableScroll();
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.recycle();
                    this.mVelocityTracker = null;
                }
                this.endAction(motionEvent);
                break;
            }
            case 6: {
                Utils.velocityTrackerPointerUpCleanUpIfNecessary(motionEvent, this.mVelocityTracker);
                this.mTouchMode = 5;
                break;
            }
            case 3: {
                this.mTouchMode = 0;
                this.endAction(motionEvent);
                break;
            }
        }
        this.mMatrix = ((BarLineChartBase)this.mChart).getViewPortHandler().refresh(this.mMatrix, (View)this.mChart, true);
        return true;
    }

    public void stopDeceleration() {
        this.mDecelerationVelocity.x = 0.0f;
        this.mDecelerationVelocity.y = 0.0f;
    }
}

