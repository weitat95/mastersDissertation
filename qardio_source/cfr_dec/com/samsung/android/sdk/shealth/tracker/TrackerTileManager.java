/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.samsung.android.sdk.shealth.tracker;

import android.content.Context;
import com.samsung.android.sdk.shealth.tracker.PrivateTrackerTile;
import com.samsung.android.sdk.shealth.tracker.PrivateTrackerTileManager;
import com.samsung.android.sdk.shealth.tracker.TrackerTile;

public final class TrackerTileManager {
    private PrivateTrackerTileManager mPrivateTrackerTileManager;

    public TrackerTileManager(Context context) throws IllegalArgumentException {
        this.mPrivateTrackerTileManager = new PrivateTrackerTileManager(context);
    }

    public boolean post(TrackerTile trackerTile) throws IllegalArgumentException {
        if (trackerTile == null) {
            throw new IllegalArgumentException("tracker tile is null.");
        }
        return this.mPrivateTrackerTileManager.post(trackerTile.getPrivateTrackerTile());
    }
}

