/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 *  android.util.Log
 */
package io.fabric.sdk.android.services.common;

import android.os.SystemClock;
import android.util.Log;

public class TimingMetric {
    private final boolean disabled;
    private long duration;
    private final String eventName;
    private long start;
    private final String tag;

    /*
     * Enabled aggressive block sorting
     */
    public TimingMetric(String string2, String string3) {
        this.eventName = string2;
        this.tag = string3;
        boolean bl = !Log.isLoggable((String)string3, (int)2);
        this.disabled = bl;
    }

    private void reportToLog() {
        Log.v((String)this.tag, (String)(this.eventName + ": " + this.duration + "ms"));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void startMeasuring() {
        synchronized (this) {
            block6: {
                boolean bl = this.disabled;
                if (!bl) break block6;
                do {
                    return;
                    break;
                } while (true);
            }
            this.start = SystemClock.elapsedRealtime();
            this.duration = 0L;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void stopMeasuring() {
        synchronized (this) {
            boolean bl = this.disabled;
            if (!bl && this.duration == 0L) {
                this.duration = SystemClock.elapsedRealtime() - this.start;
                this.reportToLog();
            }
            return;
        }
    }
}

