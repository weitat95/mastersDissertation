/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package com.github.mikephil.charting.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartGestureListener;

public abstract class ChartTouchListener<T extends Chart<?>>
extends GestureDetector.SimpleOnGestureListener
implements View.OnTouchListener {
    protected T mChart;
    protected GestureDetector mGestureDetector;
    protected ChartGesture mLastGesture = ChartGesture.NONE;
    protected Highlight mLastHighlighted;
    protected int mTouchMode = 0;

    public ChartTouchListener(T t) {
        this.mChart = t;
        this.mGestureDetector = new GestureDetector(t.getContext(), (GestureDetector.OnGestureListener)this);
    }

    protected static float distance(float f, float f2, float f3, float f4) {
        f -= f2;
        f2 = f3 - f4;
        return (float)Math.sqrt(f * f + f2 * f2);
    }

    public void endAction(MotionEvent motionEvent) {
        OnChartGestureListener onChartGestureListener = ((Chart)this.mChart).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.onChartGestureEnd(motionEvent, this.mLastGesture);
        }
    }

    protected void performHighlight(Highlight highlight, MotionEvent motionEvent) {
        if (highlight == null || highlight.equalTo(this.mLastHighlighted)) {
            ((Chart)this.mChart).highlightValue(null, true);
            this.mLastHighlighted = null;
            return;
        }
        ((Chart)this.mChart).highlightValue(highlight, true);
        this.mLastHighlighted = highlight;
    }

    public void setLastHighlighted(Highlight highlight) {
        this.mLastHighlighted = highlight;
    }

    public void startAction(MotionEvent motionEvent) {
        OnChartGestureListener onChartGestureListener = ((Chart)this.mChart).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.onChartGestureStart(motionEvent, this.mLastGesture);
        }
    }

    public static enum ChartGesture {
        NONE,
        DRAG,
        X_ZOOM,
        Y_ZOOM,
        PINCH_ZOOM,
        ROTATE,
        SINGLE_TAP,
        DOUBLE_TAP,
        LONG_PRESS,
        FLING;

    }

}

