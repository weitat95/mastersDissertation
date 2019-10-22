/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.util.Log
 */
package com.qardio.ble.bpcollector;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DeviceDbHelper
extends SQLiteOpenHelper {
    private static final String TAG = DeviceDbHelper.class.getName();

    public DeviceDbHelper(Context context) {
        super(context, "devices.db", null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("create table devices(_id integer primary key autoincrement, uuid TEXT not null, UNIQUE(uuid) ON CONFLICT IGNORE)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
        Log.w((String)TAG, (String)("Upgrading database from version " + n + " to " + n2 + ", which will destroy all old data"));
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS devices");
        this.onCreate(sQLiteDatabase);
    }
}

