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

public class FollowingUserInfoItemUriHandler
extends UriHandler {
    public FollowingUserInfoItemUriHandler(int n, SQLiteOpenHelper sQLiteOpenHelper) {
        super(n, sQLiteOpenHelper);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int delete(Context context, Uri uri, String string2, String[] arrstring) {
        boolean bl = !TextUtils.isEmpty((CharSequence)string2);
        StringBuilder stringBuilder = new StringBuilder().append("_id=").append(uri.getLastPathSegment()).append(" AND ").append("user_id").append("=").append((String)uri.getPathSegments().get(1));
        string2 = bl ? " AND " + string2 : "";
        string2 = stringBuilder.append(string2).toString();
        if (bl) {
            return this.simpleDelete(context, "following_user_info", uri, string2, arrstring);
        }
        arrstring = null;
        return this.simpleDelete(context, "following_user_info", uri, string2, arrstring);
    }

    @Override
    public Uri insert(Context context, Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("FollowingUserInfoItemUriHandler doesn't support insert operation");
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Cursor query(Context context, Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
        boolean bl = !TextUtils.isEmpty((CharSequence)string2);
        StringBuilder stringBuilder = new StringBuilder().append("_id=").append(uri.getLastPathSegment()).append(" AND ").append("user_id").append("=").append((String)uri.getPathSegments().get(1));
        string2 = bl ? " AND " + string2 : "";
        string2 = stringBuilder.append(string2).toString();
        if (bl) {
            return this.queryFullTable(context, "following_user_info", uri, arrstring, string2, arrstring2, string3);
        }
        arrstring2 = null;
        return this.queryFullTable(context, "following_user_info", uri, arrstring, string2, arrstring2, string3);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int update(Context context, Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        boolean bl = !TextUtils.isEmpty((CharSequence)string2);
        StringBuilder stringBuilder = new StringBuilder().append("_id=").append(uri.getLastPathSegment()).append(" AND ").append("user_id").append("=").append((String)uri.getPathSegments().get(1));
        string2 = bl ? " AND " + string2 : "";
        string2 = stringBuilder.append(string2).toString();
        if (bl) {
            return this.simpleUpdate(context, "following_user_info", uri, contentValues, string2, arrstring);
        }
        arrstring = null;
        return this.simpleUpdate(context, "following_user_info", uri, contentValues, string2, arrstring);
    }
}

