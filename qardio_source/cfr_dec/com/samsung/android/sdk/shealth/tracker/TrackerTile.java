/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 */
package com.samsung.android.sdk.shealth.tracker;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import com.samsung.android.sdk.shealth.tracker.PrivateTrackerTile;

public final class TrackerTile {
    protected PrivateTrackerTile mPrivateTrackerTile;

    private TrackerTile() {
        this.mPrivateTrackerTile = new PrivateTrackerTile();
    }

    public TrackerTile(Context context, String string2, String string3, int n) throws IllegalArgumentException {
        this.mPrivateTrackerTile = new PrivateTrackerTile(context, string2, string3, n);
    }

    PrivateTrackerTile getPrivateTrackerTile() {
        return this.mPrivateTrackerTile;
    }

    public TrackerTile setButtonIntent(CharSequence charSequence, int n, Intent intent) throws IllegalArgumentException {
        this.mPrivateTrackerTile.setButtonIntent(charSequence, n, intent);
        return this;
    }

    public TrackerTile setContentColor(int n) {
        this.mPrivateTrackerTile.setContentColor(n);
        return this;
    }

    public TrackerTile setIcon(int n) throws Resources.NotFoundException, IllegalArgumentException {
        this.mPrivateTrackerTile.setIcon(n);
        return this;
    }

    public TrackerTile setTitle(int n) throws Resources.NotFoundException, IllegalArgumentException {
        this.mPrivateTrackerTile.setTitle(n);
        return this;
    }
}

