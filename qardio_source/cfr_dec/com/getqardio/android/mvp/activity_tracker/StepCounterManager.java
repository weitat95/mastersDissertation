/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.hardware.Sensor
 *  android.hardware.SensorEvent
 *  android.hardware.SensorEventListener
 *  android.hardware.SensorManager
 */
package com.getqardio.android.mvp.activity_tracker;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class StepCounterManager
implements SensorEventListener {
    private int counterSteps = 0;
    private boolean isStarted = false;
    private int previousCounterSteps = 0;
    private SensorManager sensorManager;

    private StepCounterManager(Context context) {
        this.sensorManager = (SensorManager)context.getSystemService("sensor");
    }

    public static StepCounterManager newInstance(Context context) {
        return new StepCounterManager(context);
    }

    public void onAccuracyChanged(Sensor sensor, int n) {
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
    }
}

