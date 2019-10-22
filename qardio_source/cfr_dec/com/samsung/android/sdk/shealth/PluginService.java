/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.IntentService
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.database.Cursor
 *  android.net.Uri
 *  android.util.Log
 */
package com.samsung.android.sdk.shealth;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.samsung.android.sdk.shealth.PluginContract;
import com.samsung.android.sdk.shealth.tracker.TrackerEventListener;
import java.util.HashMap;

public final class PluginService
extends IntentService {
    private static final String TAG = PluginService.class.getCanonicalName();
    private static HashMap<String, TrackerEventListener> mTrackers = new HashMap();

    public PluginService() {
        super(TAG);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void HandleIntentForTracker(Intent object) {
        String string2;
        Object object2 = null;
        String string3 = object.getStringExtra("com.samsung.android.sdk.shealth.intent.extra.TILE_PROVIDER_ID");
        if (string3 == null) {
            Log.d((String)TAG, (String)"service controller id is null");
            return;
        }
        try {
            string2 = this.getApplicationContext().getPackageName();
        }
        catch (NullPointerException nullPointerException) {
            Log.d((String)TAG, (String)"invalid package name");
            return;
        }
        if (string2 == null || string2.isEmpty()) {
            Log.d((String)TAG, (String)"invalid package name");
            return;
        }
        ContentResolver contentResolver = this.getContentResolver();
        String[] arrstring = new String[]{string3, string2};
        Cursor cursor = contentResolver.query(PluginContract.TileControllerInfo.CONTENT_URI, null, "tile_controller_id = ? AND package_name = ?", arrstring, null);
        string2 = object2;
        if (cursor != null) {
            string2 = object2;
            if (cursor.moveToFirst()) {
                string2 = cursor.getString(cursor.getColumnIndex("plugin_command"));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        if (string2 == null) {
            Log.d((String)TAG, (String)"invalid action");
            return;
        }
        if (!string2.isEmpty()) {
            object2 = new ContentValues();
            object2.put("plugin_command", "");
            if (contentResolver.update(PluginContract.TileControllerInfo.CONTENT_URI, (ContentValues)object2, "tile_controller_id = ? AND package_name = ?", arrstring) == 0) {
                Log.d((String)TAG, (String)"fail to update action");
                return;
            }
        }
        if ((object2 = object.getStringExtra("com.samsung.android.sdk.shealth.intent.extra.TILE_PROVIDER_TYPE")) == null) {
            Log.d((String)TAG, (String)"invalid service controller type");
            return;
        }
        String string4 = object.getStringExtra("com.samsung.android.sdk.shealth.intent.extra.TILE_PROVIDER_EVENT_LISTENER");
        if (string4 == null) {
            Log.d((String)TAG, (String)"invalid service controller interface");
            return;
        }
        arrstring = this.getApplicationContext().getSharedPreferences("sdk_shealth", 4);
        float f = object.getFloatExtra("com.samsung.android.sdk.shealth.intent.extra.SMALL_TILE_WIDTH", 0.0f);
        if (f > 1.0f) {
            arrstring.edit().putInt("small_tracker_tile_width", Math.round(f)).commit();
        }
        if ((f = object.getFloatExtra("com.samsung.android.sdk.shealth.intent.extra.WIDE_TILE_WIDTH", 0.0f)) > 1.0f) {
            arrstring.edit().putInt("wide_tracker_tile_width", Math.round(f)).commit();
        }
        if ((f = object.getFloatExtra("com.samsung.android.sdk.shealth.intent.extra.TILE_HEIGHT", 0.0f)) > 1.0f) {
            arrstring.edit().putInt("tracker_tile_height", Math.round(f)).commit();
        }
        arrstring.edit().putBoolean("is_initialized", true).commit();
        if (!((String)object2).equals("TRACKER")) return;
        {
            Log.d((String)TAG, (String)"Tracker action");
            TrackerEventListener trackerEventListener = mTrackers.get(string3);
            object2 = trackerEventListener;
            if (trackerEventListener == null) {
                object2 = this.loadTrackerEventListener(string4);
                if (object2 != null) {
                    object2.onCreate(this.getApplicationContext(), string3);
                }
                mTrackers.put(string3, (TrackerEventListener)object2);
            }
            if (object2 == null) return;
            {
                Log.d((String)TAG, (String)"found service controller interface");
                if (string2.equals("com.samsung.android.sdk.shealth.intent.action.TILE_REQUESTED")) {
                    Log.d((String)TAG, (String)"tile requested");
                    arrstring.edit().putBoolean("dashboard_enabled", true).commit();
                    String string5 = object.getStringExtra("com.samsung.android.sdk.shealth.intent.extra.TILE_ID");
                    object2.onTileRequested(this.getApplicationContext(), string3, string5);
                    return;
                }
                if (string2.equals("com.samsung.android.sdk.shealth.intent.action.DASHBOARD_PAUSED")) {
                    Log.d((String)TAG, (String)"dashboard paused");
                    arrstring.edit().putBoolean("dashboard_enabled", false).commit();
                    object2.onPaused(this.getApplicationContext(), string3);
                    return;
                }
                if (string2.equals("com.samsung.android.sdk.shealth.intent.action.TILE_REMOVED")) {
                    Log.d((String)TAG, (String)"tile removed");
                    String string6 = object.getStringExtra("com.samsung.android.sdk.shealth.intent.extra.TILE_ID");
                    object2.onTileRemoved(this.getApplicationContext(), string3, string6);
                    return;
                }
                if (string2.equals("com.samsung.android.sdk.shealth.intent.action.SUBSCRIBED")) {
                    Log.d((String)TAG, (String)"subscribed");
                    object2.onSubscribed(this.getApplicationContext(), string3);
                    return;
                }
                if (!string2.equals("com.samsung.android.sdk.shealth.intent.action.UNSUBSCRIBED")) return;
                {
                    Log.d((String)TAG, (String)"unsubscribed");
                    object2.onUnsubscribed(this.getApplicationContext(), string3);
                    return;
                }
            }
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private TrackerEventListener loadTrackerEventListener(String string2) {
        Object object;
        try {
            object = this.getApplicationContext().getClassLoader().loadClass(string2);
            if (object == null) return null;
        }
        catch (ClassNotFoundException classNotFoundException) {
            Log.d((String)TAG, (String)"ClassNotFoundException occurred");
            return null;
        }
        try {
            object = (TrackerEventListener)((Class)object).newInstance();
            if (object == null) return null;
            return object;
        }
        catch (InstantiationException instantiationException) {
            Log.d((String)TAG, (String)"InstantiationException occurred");
        }
        return null;
        catch (IllegalAccessException illegalAccessException) {
            Log.d((String)TAG, (String)"IllegalAccessException occurred");
            return null;
        }
        catch (ClassCastException classCastException) {
            Log.d((String)TAG, (String)("ClassCastException occurred : " + string2));
            return null;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        mTrackers.clear();
    }

    protected void onHandleIntent(Intent intent) {
        Log.d((String)TAG, (String)"onHandleIntent()");
        if (intent == null) {
            return;
        }
        intent.getAction();
        this.HandleIntentForTracker(intent);
    }
}

