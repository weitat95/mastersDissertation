/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.SharedPreferences
 *  android.database.DatabaseErrorHandler
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 */
package io.fabric.sdk.android;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;

class FabricContext
extends ContextWrapper {
    private final String componentName;
    private final String componentPath;

    public FabricContext(Context context, String string2, String string3) {
        super(context);
        this.componentName = string2;
        this.componentPath = string3;
    }

    public File getCacheDir() {
        return new File(super.getCacheDir(), this.componentPath);
    }

    public File getDatabasePath(String string2) {
        File file = new File(super.getDatabasePath(string2).getParentFile(), this.componentPath);
        file.mkdirs();
        return new File(file, string2);
    }

    @TargetApi(value=8)
    public File getExternalCacheDir() {
        return new File(super.getExternalCacheDir(), this.componentPath);
    }

    @TargetApi(value=8)
    public File getExternalFilesDir(String string2) {
        return new File(super.getExternalFilesDir(string2), this.componentPath);
    }

    public File getFilesDir() {
        return new File(super.getFilesDir(), this.componentPath);
    }

    public SharedPreferences getSharedPreferences(String string2, int n) {
        return super.getSharedPreferences(this.componentName + ":" + string2, n);
    }

    public SQLiteDatabase openOrCreateDatabase(String string2, int n, SQLiteDatabase.CursorFactory cursorFactory) {
        return SQLiteDatabase.openOrCreateDatabase((File)this.getDatabasePath(string2), (SQLiteDatabase.CursorFactory)cursorFactory);
    }

    @TargetApi(value=11)
    public SQLiteDatabase openOrCreateDatabase(String string2, int n, SQLiteDatabase.CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler) {
        return SQLiteDatabase.openOrCreateDatabase((String)this.getDatabasePath(string2).getPath(), (SQLiteDatabase.CursorFactory)cursorFactory, (DatabaseErrorHandler)databaseErrorHandler);
    }
}

