/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.mixpanel.android.viewcrawler;

import android.os.Looper;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class UIThreadSet<T> {
    private Set<T> mSet = new HashSet<T>();

    public void add(T t) {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new RuntimeException("Can't add an activity when not on the UI thread");
        }
        this.mSet.add(t);
    }

    public Set<T> getAll() {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new RuntimeException("Can't remove an activity when not on the UI thread");
        }
        return Collections.unmodifiableSet(this.mSet);
    }

    public void remove(T t) {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new RuntimeException("Can't remove an activity when not on the UI thread");
        }
        this.mSet.remove(t);
    }
}

