/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.net.Uri
 */
package com.getqardio.android.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import com.getqardio.android.provider.UriHandler;

public class MeasurementsHistoryListUriHandler
extends UriHandler {
    public MeasurementsHistoryListUriHandler(int n, SQLiteOpenHelper sQLiteOpenHelper) {
        super(n, sQLiteOpenHelper);
    }

    @Override
    protected int delete(Context context, Uri uri, String string2, String[] arrstring) {
        throw new UnsupportedOperationException("MeasurementsHistoryListUriHandler does not support delete operation");
    }

    @Override
    protected Uri insert(Context context, Uri uri, ContentValues contentValues) {
        return this.simpleInsert(context, "measurements_history", uri, contentValues);
    }

    @Override
    protected Cursor query(Context context, Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
        return this.queryFullTable(context, "measurements_history", uri, arrstring, string2, arrstring2, string3);
    }

    @Override
    protected int update(Context context, Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        throw new UnsupportedOperationException("MeasurementsHistoryListUriHandler does not support update operation");
    }
}

