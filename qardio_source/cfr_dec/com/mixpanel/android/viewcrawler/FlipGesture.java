/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.hardware.Sensor
 *  android.hardware.SensorEvent
 *  android.hardware.SensorEventListener
 */
package com.mixpanel.android.viewcrawler;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import com.mixpanel.android.util.MPLog;

class FlipGesture
implements SensorEventListener {
    private int mFlipState = 0;
    private long mLastFlipTime = -1L;
    private final OnFlipGestureListener mListener;
    private final float[] mSmoothed = new float[3];
    private int mTriggerState = -1;

    public FlipGesture(OnFlipGestureListener onFlipGestureListener) {
        this.mListener = onFlipGestureListener;
    }

    private float[] smoothXYZ(float[] arrf) {
        for (int i = 0; i < 3; ++i) {
            float f = this.mSmoothed[i];
            this.mSmoothed[i] = 0.7f * (arrf[i] - f) + f;
        }
        return this.mSmoothed;
    }

    public void onAccuracyChanged(Sensor sensor, int n) {
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] arrf = this.smoothXYZ(sensorEvent.values);
        int n = this.mFlipState;
        float f = arrf[0] * arrf[0] + arrf[1] * arrf[1] + arrf[2] * arrf[2];
        this.mFlipState = 0;
        if (arrf[2] > 7.8f && arrf[2] < 11.8f) {
            this.mFlipState = -1;
        }
        if (arrf[2] < -7.8f && arrf[2] > -11.8f) {
            this.mFlipState = 1;
        }
        if (f < 60.840004f || f > 139.24f) {
            this.mFlipState = 0;
        }
        if (n != this.mFlipState) {
            this.mLastFlipTime = sensorEvent.timestamp;
        }
        long l = sensorEvent.timestamp - this.mLastFlipTime;
        switch (this.mFlipState) {
            case 1: {
                if (l <= 250000000L || this.mTriggerState != 0) return;
                {
                    MPLog.v("MixpanelAPI.FlipGesture", "Flip gesture begun");
                    this.mTriggerState = 1;
                    return;
                }
            }
            case -1: {
                if (l <= 250000000L || this.mTriggerState != 1) return;
                {
                    MPLog.v("MixpanelAPI.FlipGesture", "Flip gesture completed");
                    this.mTriggerState = 0;
                    this.mListener.onFlipGesture();
                    return;
                }
            }
            default: {
                return;
            }
            case 0: 
        }
        if (l <= 1000000000L || this.mTriggerState == 0) return;
        {
            MPLog.v("MixpanelAPI.FlipGesture", "Flip gesture abandoned");
            this.mTriggerState = 0;
            return;
        }
    }

    public static interface OnFlipGestureListener {
        public void onFlipGesture();
    }

}

