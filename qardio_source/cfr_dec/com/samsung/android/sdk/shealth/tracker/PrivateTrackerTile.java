/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 */
package com.samsung.android.sdk.shealth.tracker;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.samsung.android.sdk.shealth.tracker.TrackerTileData;
import com.samsung.android.sdk.shealth.tracker.Validator;
import java.util.ArrayList;
import java.util.Date;

class PrivateTrackerTile {
    private Context mContext;
    protected TrackerTileData mData;
    private int mMaxIconImageWidth;
    private DisplayMetrics mMetrics;
    private String mPackageName;

    public PrivateTrackerTile() {
        this.mData = new TrackerTileData();
    }

    public PrivateTrackerTile(Context context, String string2, String string3, int n) {
        Resources resources;
        Context context2;
        if (context == null) {
            throw new IllegalArgumentException("context is null.");
        }
        if (string2 == null || !Validator.isValidTrackerId(string2)) {
            throw new IllegalArgumentException("invalid tracker id.");
        }
        if (string3 == null || !Validator.isValidTileId(string3)) {
            throw new IllegalArgumentException("invalid tile id.");
        }
        if (n != 0 && n != 1 && n != 2 && n != 98) {
            throw new IllegalArgumentException("invalid tile type.");
        }
        try {
            context2 = context.getApplicationContext();
            if (context2 == null) {
                throw new IllegalArgumentException("context is invalid.");
            }
        }
        catch (NullPointerException nullPointerException) {
            throw new IllegalArgumentException("context is invalid.");
        }
        try {
            resources = context2.getResources();
            if (resources == null) {
                throw new IllegalArgumentException("context is invalid.");
            }
        }
        catch (NullPointerException nullPointerException) {
            throw new IllegalArgumentException("context is invalid.");
        }
        this.mMetrics = resources.getDisplayMetrics();
        if (this.mMetrics == null) {
            throw new IllegalArgumentException("context is invalid.");
        }
        resources = context2.getSharedPreferences("sdk_shealth", 4);
        if (resources == null) {
            throw new IllegalArgumentException("context is invalid.");
        }
        this.mMaxIconImageWidth = resources.getInt("wide_tracker_tile_width", (int)TypedValue.applyDimension((int)1, (float)168.0f, (DisplayMetrics)this.mMetrics));
        try {
            this.mPackageName = context2.getPackageName();
        }
        catch (NullPointerException nullPointerException) {
            throw new IllegalArgumentException("context is invalid.");
        }
        if (this.mPackageName == null || this.mPackageName.isEmpty()) {
            throw new IllegalArgumentException("context is invalid.");
        }
        this.mData = new TrackerTileData();
        this.mContext = context;
        this.mData.mTemplate = n;
        this.mData.mPackageName = this.mPackageName;
        this.mData.mTrackerId = string2;
        this.mData.mTileId = string2 + "." + string3;
        this.mData.mTitle = "";
        this.mData.mContentValue = "";
        this.mData.mContentUnit = "";
        this.mData.mIconResourceName = "";
        this.mData.mDate = new Date();
        this.mData.mContentColor = -16777216;
    }

    public PrivateTrackerTile setButtonIntent(CharSequence charSequence, int n, Intent object) {
        if (charSequence == null) {
            throw new IllegalArgumentException("invalid title.");
        }
        if (charSequence.length() > 14) {
            throw new IllegalArgumentException("button title too long.");
        }
        if (n != 0 && n != 1) {
            throw new IllegalArgumentException("invalid intent type.");
        }
        if (object == null) {
            throw new IllegalArgumentException("invalid intent.");
        }
        Object object2 = object.getComponent();
        if (object2 == null) {
            throw new IllegalArgumentException("invalid intent.");
        }
        if ((object2 = object2.getPackageName()) == null) {
            throw new IllegalArgumentException("invalid intent.");
        }
        if (!((String)object2).equals(this.mPackageName)) {
            throw new IllegalArgumentException("invalid intent.");
        }
        int n2 = this.mData.mActionArray.size();
        object2 = this.mData;
        if (n2 >= 1) {
            object2 = this.mData.mActionArray;
            TrackerTileData trackerTileData = this.mData;
            ((ArrayList)object2).remove(0);
        }
        object = new TrackerTileData.InternalIntent(n, (Intent)object);
        this.mData.mActionArray.add(new TrackerTileData.InternalAction(charSequence, (TrackerTileData.InternalIntent)object));
        return this;
    }

    public PrivateTrackerTile setContentColor(int n) {
        this.mData.mContentColor = n;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public PrivateTrackerTile setIcon(int n) {
        if (!this.mContext.getResources().getResourceTypeName(n).equals("drawable")) {
            throw new IllegalArgumentException("invalid resource id");
        }
        Bitmap bitmap = BitmapFactory.decodeResource((Resources)this.mContext.getResources(), (int)n);
        if (bitmap == null) {
            throw new IllegalArgumentException("invalid resource id");
        }
        int n2 = this.mData.mTemplate == 98 ? 168 : (this.mData.mTemplate == 0 ? 82 : 40);
        n2 = (int)TypedValue.applyDimension((int)1, (float)n2, (DisplayMetrics)this.mMetrics);
        if (bitmap.getWidth() <= this.mMaxIconImageWidth && bitmap.getHeight() <= n2) {
            this.mData.mIconResourceName = this.mContext.getResources().getResourceEntryName(n);
            return this;
        }
        throw new IllegalArgumentException("icon image size is too big.");
    }

    public PrivateTrackerTile setTitle(int n) {
        if (!this.mContext.getResources().getResourceTypeName(n).equals("string")) {
            throw new IllegalArgumentException("invalid resource id");
        }
        CharSequence charSequence = this.mContext.getResources().getText(n);
        if (charSequence.length() > 15) {
            throw new IllegalArgumentException("title too long.");
        }
        this.mData.mTitle = TrackerTileData.safeCharSequence(charSequence);
        return this;
    }
}

