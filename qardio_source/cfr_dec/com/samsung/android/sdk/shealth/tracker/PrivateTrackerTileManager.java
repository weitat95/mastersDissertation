/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Parcel
 */
package com.samsung.android.sdk.shealth.tracker;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import com.samsung.android.sdk.shealth.Shealth;
import com.samsung.android.sdk.shealth.tracker.PrivateTrackerTile;
import com.samsung.android.sdk.shealth.tracker.TrackerContract;
import com.samsung.android.sdk.shealth.tracker.TrackerTileData;
import com.samsung.android.sdk.shealth.tracker.Validator;
import java.util.ArrayList;

class PrivateTrackerTileManager {
    private Context mContext;
    private boolean mIsInitialized = false;
    private String mPackageName = null;
    private SharedPreferences mSp;

    public PrivateTrackerTileManager(Context context) {
        Context context2;
        if (context == null) {
            throw new IllegalArgumentException("context is null.");
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
            Resources resources = context2.getResources();
            if (resources == null) {
                throw new IllegalArgumentException("context is invalid.");
            }
        }
        catch (NullPointerException nullPointerException) {
            throw new IllegalArgumentException("context is invalid.");
        }
        try {
            this.mPackageName = context2.getPackageName();
        }
        catch (NullPointerException nullPointerException) {
            throw new IllegalArgumentException("context is invalid.");
        }
        if (this.mPackageName == null || this.mPackageName.isEmpty()) {
            throw new IllegalArgumentException("context is invalid.");
        }
        this.mSp = context.getApplicationContext().getSharedPreferences("sdk_shealth", 4);
        if (this.mSp == null) {
            throw new IllegalArgumentException("context is invalid.");
        }
        this.mIsInitialized = this.mSp.getBoolean("is_initialized", false);
        if (!this.mIsInitialized) {
            throw new IllegalArgumentException("Shealth is not initialized.");
        }
        this.mContext = context.getApplicationContext();
    }

    public ArrayList<String> getPostedTrackerTileIds(String string2) {
        if (string2 == null || !Validator.isValidTrackerId(string2)) {
            throw new IllegalArgumentException("invalid tracker id.");
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        ContentResolver contentResolver = this.mContext.getContentResolver();
        String string3 = this.mPackageName;
        if ((contentResolver = contentResolver.query(TrackerContract.TileInfo.CONTENT_URI, null, "tile_controller_id = ? AND package_name = ?", new String[]{string2, string3}, null)) != null) {
            while (contentResolver.moveToNext()) {
                arrayList.add(contentResolver.getString(contentResolver.getColumnIndex("tile_id")).substring(string2.length() + 1));
            }
        }
        if (contentResolver != null) {
            contentResolver.close();
        }
        return arrayList;
    }

    public boolean post(PrivateTrackerTile privateTrackerTile) {
        if (privateTrackerTile == null) {
            throw new IllegalArgumentException("tracker tile is null.");
        }
        Object object = this.mContext.getSharedPreferences("sdk_shealth", 4);
        boolean bl = false;
        if (object != null) {
            bl = object.getBoolean("dashboard_enabled", false);
        }
        if (bl) {
            int n;
            TrackerTileData trackerTileData = privateTrackerTile.mData;
            if (trackerTileData.mTemplate == 98) {
                try {
                    bl = new Shealth().isFeatureEnabled(1002);
                    if (bl) {
                        return false;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    return false;
                }
            }
            if (trackerTileData.mTemplate == 0 || trackerTileData.mTemplate == 2) {
                if (trackerTileData.mActionArray.size() > 0) {
                    trackerTileData.mActions = new TrackerTileData.InternalAction[trackerTileData.mActionArray.size()];
                    for (n = 0; n < trackerTileData.mActionArray.size(); ++n) {
                        trackerTileData.mActions[n] = trackerTileData.mActionArray.get(n);
                    }
                } else if (trackerTileData.mContentIntent == null) {
                    throw new IllegalArgumentException("At least one intent should be set between Action intent or Content intent");
                }
            }
            Parcel parcel = Parcel.obtain();
            trackerTileData.writeToParcel(parcel, 0);
            object = parcel.marshall();
            parcel.recycle();
            trackerTileData = Uri.withAppendedPath((Uri)Uri.withAppendedPath((Uri)Uri.withAppendedPath((Uri)TrackerContract.TileInfo.CONTENT_URI_POST, (String)trackerTileData.mTrackerId), (String)trackerTileData.mTileId), (String)this.mPackageName);
            parcel = new ContentValues();
            parcel.put("tile_data", (byte[])object);
            parcel.put("versionCode", Integer.valueOf(new Shealth().getVersionCode()));
            this.mContext.getContentResolver().insert((Uri)trackerTileData, (ContentValues)parcel);
            object = this.getPostedTrackerTileIds(privateTrackerTile.mData.mTrackerId);
            int n2 = ((ArrayList)object).size();
            int n3 = 0;
            for (n = 0; n < ((ArrayList)object).size(); ++n) {
                int n4 = n3;
                if (n3 < n2 - 1) {
                    n4 = n3;
                    if (!((String)((ArrayList)object).get(n)).equals(privateTrackerTile.mData.mTileId)) {
                        this.remove(privateTrackerTile.mData.mTrackerId, (String)((ArrayList)object).get(n));
                        n4 = n3 + 1;
                    }
                }
                n3 = n4;
            }
            return true;
        }
        return false;
    }

    public boolean remove(String string2, String string3) {
        if (string2 == null || !Validator.isValidTrackerId(string2)) {
            throw new IllegalArgumentException("invalid tracker id.");
        }
        if (string3 == null || !Validator.isValidTileId(string3)) {
            throw new IllegalArgumentException("invalid tile id.");
        }
        string3 = string2 + "." + string3;
        return this.mContext.getContentResolver().delete(Uri.withAppendedPath((Uri)Uri.withAppendedPath((Uri)TrackerContract.TileInfo.CONTENT_URI_REMOVE, (String)string2), (String)string3), "tile_id = ? AND tracker_id = ?", new String[]{string3, string2, this.mPackageName}) > 0;
    }
}

