/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.shared.utils;

public class SingleEvent {
    private long interval = 200L;
    private long lastEventTime;

    public void run(Runnable runnable) {
        long l = System.currentTimeMillis();
        if (this.lastEventTime + this.interval < l) {
            this.lastEventTime = l;
            runnable.run();
        }
    }

    public SingleEvent setInterval(long l) {
        this.interval = l;
        return this;
    }
}

