/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.samsung.android.sdk.shealth.tracker;

import android.content.Context;
import com.samsung.android.sdk.shealth.tracker.PrivateTrackerTileManager;

public final class InternalTrackerTileManager {
    private PrivateTrackerTileManager mPrivateTrackerTileManager;

    public InternalTrackerTileManager(Context context) throws IllegalArgumentException {
        this.mPrivateTrackerTileManager = new PrivateTrackerTileManager(context);
    }
}

