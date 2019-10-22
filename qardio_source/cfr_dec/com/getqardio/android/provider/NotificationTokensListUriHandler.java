/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.net.Uri
 *  android.text.TextUtils
 */
package com.getqardio.android.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import com.getqardio.android.provider.UriHandler;
import java.util.List;

public class NotificationTokensListUriHandler
extends UriHandler {
    public NotificationTokensListUriHandler(int n, SQLiteOpenHelper sQLiteOpenHelper) {
        super(n, sQLiteOpenHelper);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected int delete(Context context, Uri uri, String string2, String[] arrstring) {
        boolean bl = !TextUtils.isEmpty((CharSequence)string2);
        StringBuilder stringBuilder = new StringBuilder().append("user_id=").append((String)uri.getPathSegments().get(1));
        string2 = bl ? " AND " + string2 : "";
        string2 = stringBuilder.append(string2).toString();
        if (bl) {
            return this.simpleDelete(context, "notifications_device_tokens", uri, string2, arrstring);
        }
        arrstring = null;
        return this.simpleDelete(context, "notifications_device_tokens", uri, string2, arrstring);
    }

    @Override
    protected Uri insert(Context context, Uri uri, ContentValues contentValues) {
        contentValues.put("user_id", Long.valueOf(Long.parseLong((String)uri.getPathSegments().get(1))));
        return this.simpleInsert(context, "notifications_device_tokens", uri, contentValues);
    }

    @Override
    protected Cursor query(Context context, Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
        return this.queryFullTable(context, "notifications_device_tokens", uri, arrstring, string2, arrstring2, string3);
    }

    @Override
    protected int update(Context context, Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        throw new UnsupportedOperationException("NotificationTokensListUriHandler does not support update operation");
    }
}

