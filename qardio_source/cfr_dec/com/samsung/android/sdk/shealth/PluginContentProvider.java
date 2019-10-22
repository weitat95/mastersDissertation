/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentProvider
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.UriMatcher
 *  android.database.Cursor
 *  android.database.MatrixCursor
 *  android.net.Uri
 */
package com.samsung.android.sdk.shealth;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import com.samsung.android.sdk.shealth.Shealth;

public class PluginContentProvider
extends ContentProvider {
    private static final UriMatcher sUriMatcher = new UriMatcher(-1);

    public int delete(Uri uri, String string2, String[] arrstring) {
        return 0;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public boolean onCreate() {
        sUriMatcher.addURI(this.getContext().getPackageName() + ".pluginservice", "version", 1);
        return false;
    }

    public Cursor query(Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
        switch (sUriMatcher.match(uri)) {
            default: {
                return null;
            }
            case 1: 
        }
        uri = new MatrixCursor(arrstring);
        uri.addRow((Object[])new Integer[]{new Shealth().getVersionCode()});
        return uri;
    }

    public int update(Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        return 0;
    }
}

