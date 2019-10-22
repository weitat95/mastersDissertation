/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.MotionEvent
 *  android.view.ViewConfiguration
 */
package com.facebook.drawee.gestures;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class GestureDetector {
    long mActionDownTime;
    float mActionDownX;
    float mActionDownY;
    ClickListener mClickListener;
    boolean mIsCapturingGesture;
    boolean mIsClickCandidate;
    final float mSingleTapSlopPx;

    public GestureDetector(Context context) {
        this.mSingleTapSlopPx = ViewConfiguration.get((Context)context).getScaledTouchSlop();
        this.init();
    }

    public static GestureDetector newInstance(Context context) {
        return new GestureDetector(context);
    }

    public void init() {
        this.mClickListener = null;
        this.reset();
    }

    public boolean isCapturingGesture() {
        return this.mIsCapturingGesture;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0: {
                this.mIsCapturingGesture = true;
                this.mIsClickCandidate = true;
                this.mActionDownTime = motionEvent.getEventTime();
                this.mActionDownX = motionEvent.getX();
                this.mActionDownY = motionEvent.getY();
                return true;
            }
            case 2: {
                if (Math.abs(motionEvent.getX() - this.mActionDownX) > this.mSingleTapSlopPx || Math.abs(motionEvent.getY() - this.mActionDownY) > this.mSingleTapSlopPx) {
                    this.mIsClickCandidate = false;
                    return true;
                }
            }
            default: {
                return true;
            }
            case 3: {
                this.mIsCapturingGesture = false;
                this.mIsClickCandidate = false;
                return true;
            }
            case 1: 
        }
        this.mIsCapturingGesture = false;
        if (Math.abs(motionEvent.getX() - this.mActionDownX) > this.mSingleTapSlopPx || Math.abs(motionEvent.getY() - this.mActionDownY) > this.mSingleTapSlopPx) {
            this.mIsClickCandidate = false;
        }
        if (this.mIsClickCandidate && motionEvent.getEventTime() - this.mActionDownTime <= (long)ViewConfiguration.getLongPressTimeout() && this.mClickListener != null) {
            this.mClickListener.onClick();
        }
        this.mIsClickCandidate = false;
        return true;
    }

    public void reset() {
        this.mIsCapturingGesture = false;
        this.mIsClickCandidate = false;
    }

    public void setClickListener(ClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    public static interface ClickListener {
        public boolean onClick();
    }

}

