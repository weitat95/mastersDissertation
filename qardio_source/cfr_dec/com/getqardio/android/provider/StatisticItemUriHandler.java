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

public class StatisticItemUriHandler
extends UriHandler {
    public StatisticItemUriHandler(int n, SQLiteOpenHelper sQLiteOpenHelper) {
        super(n, sQLiteOpenHelper);
    }

    @Override
    protected int delete(Context context, Uri uri, String string2, String[] arrstring) {
        throw new UnsupportedOperationException("StatisticItemUriHandler does not support delete operation");
    }

    @Override
    protected Uri insert(Context context, Uri uri, ContentValues contentValues) {
        List list = uri.getPathSegments();
        contentValues.put("user_id", Long.valueOf(Long.parseLong((String)list.get(1))));
        contentValues.put("device_id", (String)list.get(3));
        return this.simpleInsert(context, "statistic", uri, contentValues);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected Cursor query(Context context, Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
        boolean bl = !TextUtils.isEmpty((CharSequence)string2);
        Object object = uri.getPathSegments();
        object = new StringBuilder().append("user_id=").append((String)object.get(1)).append(" AND ").append("device_id").append("='").append((String)object.get(3)).append("'");
        string2 = bl ? " AND " + string2 : "";
        string2 = ((StringBuilder)object).append(string2).toString();
        if (bl) {
            return this.queryFullTable(context, "statistic", uri, arrstring, string2, arrstring2, string3);
        }
        arrstring2 = null;
        return this.queryFullTable(context, "statistic", uri, arrstring, string2, arrstring2, string3);
    }

    @Override
    protected int update(Context context, Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        throw new UnsupportedOperationException("StatisticItemUriHandler does not support update operation");
    }
}

