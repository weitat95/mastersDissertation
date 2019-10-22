/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Intent
 *  android.hardware.Sensor
 *  android.hardware.SensorEvent
 *  android.hardware.SensorEventListener
 *  android.hardware.SensorManager
 *  android.os.IBinder
 */
package com.getqardio.android.mvp.activity_tracker.common.model.remote;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import java.util.concurrent.TimeUnit;

public class StepsCounterService
extends Service
implements SensorEventListener {
    private static int counterSteps = 0;
    private static int previousCounterSteps = 0;
    private static int steps;
    private SensorManager sensorManager;

    private void registerSensor() {
        if (this.sensorManager == null) {
            this.sensorManager = (SensorManager)this.getSystemService("sensor");
            int n = (int)TimeUnit.MINUTES.toMicros(2L);
            this.sensorManager.registerListener((SensorEventListener)this, this.sensorManager.getDefaultSensor(19), 3, n);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int n) {
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == 19) {
            if (counterSteps < 1) {
                counterSteps = (int)sensorEvent.values[0];
            }
            steps = (int)sensorEvent.values[0] - counterSteps;
            steps += previousCounterSteps;
        }
    }

    public int onStartCommand(Intent intent, int n, int n2) {
        this.registerSensor();
        return 1;
    }
}

