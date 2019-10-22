/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Pair
 */
package com.segment.analytics;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import java.util.HashMap;
import java.util.Map;

class Stats {
    long flushCount;
    long flushEventCount;
    final StatsHandler handler;
    long integrationOperationCount;
    long integrationOperationDuration;
    Map<String, Long> integrationOperationDurationByIntegration = new HashMap<String, Long>();
    final HandlerThread statsThread = new HandlerThread("Segment-Stats", 10);

    Stats() {
        this.statsThread.start();
        this.handler = new StatsHandler(this.statsThread.getLooper(), this);
    }

    void dispatchFlush(int n) {
        this.handler.sendMessage(this.handler.obtainMessage(1, n, 0));
    }

    void dispatchIntegrationOperation(String string2, long l) {
        this.handler.sendMessage(this.handler.obtainMessage(2, (Object)new Pair((Object)string2, (Object)l)));
    }

    void performFlush(int n) {
        ++this.flushCount;
        this.flushEventCount += (long)n;
    }

    void performIntegrationOperation(Pair<String, Long> pair) {
        ++this.integrationOperationCount;
        this.integrationOperationDuration += ((Long)pair.second).longValue();
        Long l = this.integrationOperationDurationByIntegration.get(pair.first);
        if (l == null) {
            this.integrationOperationDurationByIntegration.put((String)pair.first, (Long)pair.second);
            return;
        }
        this.integrationOperationDurationByIntegration.put((String)pair.first, l + (Long)pair.second);
    }

    private static class StatsHandler
    extends Handler {
        private final Stats stats;

        StatsHandler(Looper looper, Stats stats) {
            super(looper);
            this.stats = stats;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    throw new AssertionError((Object)("Unknown Stats handler message: " + (Object)message));
                }
                case 1: {
                    this.stats.performFlush(message.arg1);
                    return;
                }
                case 2: 
            }
            this.stats.performIntegrationOperation((Pair<String, Long>)((Pair)message.obj));
        }
    }

}

