/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 */
package com.qardio.ble.bpcollector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.qardio.ble.bpcollector.DeviceDbHelper;

public class DeviceManager {
    private static volatile DeviceManager mInstance;
    private DeviceDbHelper mDbHelper;

    private DeviceManager(Context context) {
        this.mDbHelper = new DeviceDbHelper(context);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static DeviceManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DeviceManager.class) {
                if (mInstance == null) {
                    mInstance = new DeviceManager(context);
                }
            }
        }
        return mInstance;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean create(String string2) {
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = this.mDbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("uuid", string2);
            long l = sQLiteDatabase.insert("devices", null, contentValues);
            sQLiteDatabase.close();
            if (l <= -1L) return false;
            return true;
        }
    }

    public int deleteAll() {
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = this.mDbHelper.getWritableDatabase();
            int n = sQLiteDatabase.delete("devices", null, null);
            sQLiteDatabase.close();
            return n;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isExist(String string2) {
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = this.mDbHelper.getReadableDatabase();
            string2 = sQLiteDatabase.query("devices", new String[]{"uuid"}, "uuid = ?", new String[]{string2}, null, null, null);
            int n = string2.getCount();
            string2.close();
            sQLiteDatabase.close();
            if (n <= 0) return false;
            return true;
        }
    }
}

