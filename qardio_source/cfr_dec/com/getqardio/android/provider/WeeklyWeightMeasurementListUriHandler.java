/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.database.sqlite.SQLiteQueryBuilder
 *  android.net.Uri
 *  android.text.TextUtils
 */
package com.getqardio.android.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import com.getqardio.android.provider.UriHandler;
import java.util.Calendar;
import java.util.List;

public class WeeklyWeightMeasurementListUriHandler
extends UriHandler {
    public WeeklyWeightMeasurementListUriHandler(int n, SQLiteOpenHelper sQLiteOpenHelper) {
        super(n, sQLiteOpenHelper);
    }

    @Override
    protected int delete(Context context, Uri uri, String string2, String[] arrstring) {
        throw new UnsupportedOperationException("WeeklyWeightMeasurementListUriHandler doesn't support delete operation");
    }

    @Override
    protected Uri insert(Context context, Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("WeeklyWeightMeasurementListUriHandler doesn't support insert operation");
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected Cursor query(Context context, Uri uri, String[] cursor, String string2, String[] arrstring, String string3) {
        boolean bl = !TextUtils.isEmpty((CharSequence)string2);
        SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
        sQLiteQueryBuilder.setTables("weight_measurements");
        StringBuilder stringBuilder = new StringBuilder().append("user_id=").append((String)uri.getPathSegments().get(1));
        string2 = bl ? " AND " + string2 : "";
        String string4 = stringBuilder.append(string2).toString();
        if (!bl) {
            arrstring = null;
        }
        string2 = Calendar.getInstance().getFirstDayOfWeek() == 1 ? "strftime(\"%W-%Y\", measure_date / 1000,'unixepoch', 'localtime', 'weekday 6', '-7 day')" : "strftime(\"%W-%Y\", measure_date / 1000,'unixepoch', 'localtime')";
        cursor = sQLiteQueryBuilder.query(this.sqlHelper.getReadableDatabase(), (String[])cursor, string4, arrstring, string2, null, string3);
        cursor.setNotificationUri(context.getContentResolver(), uri);
        return cursor;
    }

    @Override
    protected int update(Context context, Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        throw new UnsupportedOperationException("WeeklyWeightMeasurementListUriHandler doesn't support update operation");
    }
}

