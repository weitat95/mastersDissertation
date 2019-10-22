/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.samsung.android.sdk.shealth.tracker;

import android.content.Context;

public interface TrackerEventListener {
    public void onCreate(Context var1, String var2);

    public void onPaused(Context var1, String var2);

    public void onSubscribed(Context var1, String var2);

    public void onTileRemoved(Context var1, String var2, String var3);

    public void onTileRequested(Context var1, String var2, String var3);

    public void onUnsubscribed(Context var1, String var2);
}

