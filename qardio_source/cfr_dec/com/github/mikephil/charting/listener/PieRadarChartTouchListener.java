/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.view.GestureDetector
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.animation.AnimationUtils
 */
package com.github.mikephil.charting.listener;

import android.annotation.SuppressLint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieRadarChartBase;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;

public class PieRadarChartTouchListener
extends ChartTouchListener<PieRadarChartBase<?>> {
    private ArrayList<AngularVelocitySample> _velocitySamples;
    private float mDecelerationAngularVelocity = 0.0f;
    private long mDecelerationLastTime = 0L;
    private float mStartAngle = 0.0f;
    private MPPointF mTouchStartPoint = MPPointF.getInstance(0.0f, 0.0f);

    public PieRadarChartTouchListener(PieRadarChartBase<?> pieRadarChartBase) {
        super(pieRadarChartBase);
        this._velocitySamples = new ArrayList();
    }

    /*
     * Enabled aggressive block sorting
     */
    private float calculateVelocity() {
        if (this._velocitySamples.isEmpty()) {
            return 0.0f;
        }
        AngularVelocitySample angularVelocitySample = this._velocitySamples.get(0);
        AngularVelocitySample angularVelocitySample2 = this._velocitySamples.get(this._velocitySamples.size() - 1);
        AngularVelocitySample angularVelocitySample3 = angularVelocitySample;
        int n = this._velocitySamples.size() - 1;
        do {
            block11: {
                float f;
                block10: {
                    if (n < 0) break block10;
                    angularVelocitySample3 = this._velocitySamples.get(n);
                    if (angularVelocitySample3.angle == angularVelocitySample2.angle) break block11;
                }
                float f2 = f = (float)(angularVelocitySample2.time - angularVelocitySample.time) / 1000.0f;
                if (f == 0.0f) {
                    f2 = 0.1f;
                }
                n = angularVelocitySample2.angle >= angularVelocitySample3.angle ? 1 : 0;
                int n2 = n;
                if ((double)Math.abs(angularVelocitySample2.angle - angularVelocitySample3.angle) > 270.0) {
                    n2 = n == 0 ? 1 : 0;
                }
                if ((double)(angularVelocitySample2.angle - angularVelocitySample.angle) > 180.0) {
                    angularVelocitySample.angle = (float)((double)angularVelocitySample.angle + 360.0);
                } else if ((double)(angularVelocitySample.angle - angularVelocitySample2.angle) > 180.0) {
                    angularVelocitySample2.angle = (float)((double)angularVelocitySample2.angle + 360.0);
                }
                f2 = f = Math.abs((angularVelocitySample2.angle - angularVelocitySample.angle) / f2);
                if (n2 != 0) return f2;
                return -f;
            }
            --n;
        } while (true);
    }

    private void resetVelocity() {
        this._velocitySamples.clear();
    }

    private void sampleVelocity(float f, float f2) {
        long l = AnimationUtils.currentAnimationTimeMillis();
        this._velocitySamples.add(new AngularVelocitySample(l, ((PieRadarChartBase)this.mChart).getAngleForPoint(f, f2)));
        int n = 0;
        int n2 = this._velocitySamples.size();
        while (n2 - 2 > 0 && l - this._velocitySamples.get((int)n).time > 1000L) {
            this._velocitySamples.remove(0);
            --n2;
            n = n - 1 + 1;
        }
    }

    public void computeScroll() {
        if (this.mDecelerationAngularVelocity == 0.0f) {
            return;
        }
        long l = AnimationUtils.currentAnimationTimeMillis();
        float f = this.mDecelerationAngularVelocity;
        this.mDecelerationAngularVelocity = ((PieRadarChartBase)this.mChart).getDragDecelerationFrictionCoef() * f;
        f = (float)(l - this.mDecelerationLastTime) / 1000.0f;
        ((PieRadarChartBase)this.mChart).setRotationAngle(((PieRadarChartBase)this.mChart).getRotationAngle() + this.mDecelerationAngularVelocity * f);
        this.mDecelerationLastTime = l;
        if ((double)Math.abs(this.mDecelerationAngularVelocity) >= 0.001) {
            Utils.postInvalidateOnAnimation((View)this.mChart);
            return;
        }
        this.stopDeceleration();
    }

    public void onLongPress(MotionEvent motionEvent) {
        this.mLastGesture = ChartTouchListener.ChartGesture.LONG_PRESS;
        OnChartGestureListener onChartGestureListener = ((PieRadarChartBase)this.mChart).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.onChartLongPressed(motionEvent);
        }
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return true;
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        this.mLastGesture = ChartTouchListener.ChartGesture.SINGLE_TAP;
        OnChartGestureListener onChartGestureListener = ((PieRadarChartBase)this.mChart).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.onChartSingleTapped(motionEvent);
        }
        if (!((PieRadarChartBase)this.mChart).isHighlightPerTapEnabled()) {
            return false;
        }
        this.performHighlight(((PieRadarChartBase)this.mChart).getHighlightByTouchPoint(motionEvent.getX(), motionEvent.getY()), motionEvent);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"ClickableViewAccessibility"})
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.mGestureDetector.onTouchEvent(motionEvent) || !((PieRadarChartBase)this.mChart).isRotationEnabled()) {
            return true;
        }
        float f = motionEvent.getX();
        float f2 = motionEvent.getY();
        switch (motionEvent.getAction()) {
            default: {
                return true;
            }
            case 0: {
                this.startAction(motionEvent);
                this.stopDeceleration();
                this.resetVelocity();
                if (((PieRadarChartBase)this.mChart).isDragDecelerationEnabled()) {
                    this.sampleVelocity(f, f2);
                }
                this.setGestureStartAngle(f, f2);
                this.mTouchStartPoint.x = f;
                this.mTouchStartPoint.y = f2;
                return true;
            }
            case 2: {
                if (((PieRadarChartBase)this.mChart).isDragDecelerationEnabled()) {
                    this.sampleVelocity(f, f2);
                }
                if (this.mTouchMode == 0 && PieRadarChartTouchListener.distance(f, this.mTouchStartPoint.x, f2, this.mTouchStartPoint.y) > Utils.convertDpToPixel(8.0f)) {
                    this.mLastGesture = ChartTouchListener.ChartGesture.ROTATE;
                    this.mTouchMode = 6;
                    ((PieRadarChartBase)this.mChart).disableScroll();
                } else if (this.mTouchMode == 6) {
                    this.updateGestureRotation(f, f2);
                    ((PieRadarChartBase)this.mChart).invalidate();
                }
                this.endAction(motionEvent);
                return true;
            }
            case 1: 
        }
        if (((PieRadarChartBase)this.mChart).isDragDecelerationEnabled()) {
            this.stopDeceleration();
            this.sampleVelocity(f, f2);
            this.mDecelerationAngularVelocity = this.calculateVelocity();
            if (this.mDecelerationAngularVelocity != 0.0f) {
                this.mDecelerationLastTime = AnimationUtils.currentAnimationTimeMillis();
                Utils.postInvalidateOnAnimation((View)this.mChart);
            }
        }
        ((PieRadarChartBase)this.mChart).enableScroll();
        this.mTouchMode = 0;
        this.endAction(motionEvent);
        return true;
    }

    public void setGestureStartAngle(float f, float f2) {
        this.mStartAngle = ((PieRadarChartBase)this.mChart).getAngleForPoint(f, f2) - ((PieRadarChartBase)this.mChart).getRawRotationAngle();
    }

    public void stopDeceleration() {
        this.mDecelerationAngularVelocity = 0.0f;
    }

    public void updateGestureRotation(float f, float f2) {
        ((PieRadarChartBase)this.mChart).setRotationAngle(((PieRadarChartBase)this.mChart).getAngleForPoint(f, f2) - this.mStartAngle);
    }

    private class AngularVelocitySample {
        public float angle;
        public long time;

        public AngularVelocitySample(long l, float f) {
            this.time = l;
            this.angle = f;
        }
    }

}

