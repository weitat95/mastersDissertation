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

public class UserListUriHandler
extends UriHandler {
    public UserListUriHandler(int n, SQLiteOpenHelper sQLiteOpenHelper) {
        super(n, sQLiteOpenHelper);
    }

    @Override
    public int delete(Context context, Uri uri, String string2, String[] arrstring) {
        return this.simpleDelete(context, "users", uri, string2, arrstring);
    }

    @Override
    public Uri insert(Context context, Uri uri, ContentValues contentValues) {
        return this.simpleInsert(context, "users", uri, contentValues);
    }

    @Override
    public Cursor query(Context context, Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
        return this.queryFullTable(context, "users", uri, arrstring, string2, arrstring2, string3);
    }

    @Override
    public int update(Context context, Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        return this.simpleUpdate(context, "users", uri, contentValues, string2, arrstring);
    }
}

