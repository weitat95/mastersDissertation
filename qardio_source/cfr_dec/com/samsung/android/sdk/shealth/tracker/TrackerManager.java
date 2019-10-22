/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.samsung.android.sdk.shealth.tracker;

import android.content.Context;
import com.samsung.android.sdk.shealth.tracker.PrivateTrackerManager;

public final class TrackerManager {
    PrivateTrackerManager mPrivateTrackerManager;

    public TrackerManager(Context context) throws IllegalArgumentException {
        this.mPrivateTrackerManager = new PrivateTrackerManager(context);
    }
}

