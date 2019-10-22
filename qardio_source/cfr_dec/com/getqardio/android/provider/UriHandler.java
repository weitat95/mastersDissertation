/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.ContentUris
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.ContentObserver
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteException
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.database.sqlite.SQLiteQueryBuilder
 *  android.net.Uri
 */
package com.getqardio.android.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public abstract class UriHandler {
    private UriHandler next;
    protected final SQLiteOpenHelper sqlHelper;
    private int uriId;

    public UriHandler(int n, SQLiteOpenHelper sQLiteOpenHelper) {
        this.uriId = n;
        this.sqlHelper = sQLiteOpenHelper;
    }

    private boolean canProcess(int n) {
        return this.uriId == n;
    }

    public int delete(Context context, int n, Uri uri, String string2, String[] arrstring) {
        if (this.canProcess(n)) {
            return this.delete(context, uri, string2, arrstring);
        }
        if (this.next != null) {
            return this.next.delete(context, n, uri, string2, arrstring);
        }
        throw new IllegalArgumentException("Unknown uri = " + (Object)uri);
    }

    protected abstract int delete(Context var1, Uri var2, String var3, String[] var4);

    public Uri insert(Context context, int n, Uri uri, ContentValues contentValues) {
        if (this.canProcess(n)) {
            return this.insert(context, uri, contentValues);
        }
        if (this.next != null) {
            return this.next.insert(context, n, uri, contentValues);
        }
        throw new IllegalArgumentException("Unknown uri = " + (Object)uri);
    }

    protected abstract Uri insert(Context var1, Uri var2, ContentValues var3);

    public Cursor query(Context context, int n, Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
        if (this.canProcess(n)) {
            return this.query(context, uri, arrstring, string2, arrstring2, string3);
        }
        if (this.next != null) {
            return this.next.query(context, n, uri, arrstring, string2, arrstring2, string3);
        }
        throw new IllegalArgumentException("Unknown uri = " + (Object)uri);
    }

    protected abstract Cursor query(Context var1, Uri var2, String[] var3, String var4, String[] var5, String var6);

    protected Cursor queryFullTable(Context context, String string2, Uri uri, String[] arrstring, String string3, String[] arrstring2, String string4) {
        SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
        sQLiteQueryBuilder.setTables(string2);
        string2 = sQLiteQueryBuilder.query(this.sqlHelper.getReadableDatabase(), arrstring, string3, arrstring2, null, null, string4);
        string2.setNotificationUri(context.getContentResolver(), uri);
        return string2;
    }

    public void setNext(UriHandler uriHandler) {
        this.next = uriHandler;
    }

    protected int simpleDelete(Context context, String string2, Uri uri, String string3, String[] arrstring) {
        int n = this.sqlHelper.getWritableDatabase().delete(string2, string3, arrstring);
        if (n > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return n;
    }

    protected Uri simpleInsert(Context context, String string2, Uri uri, ContentValues contentValues) {
        long l = this.sqlHelper.getWritableDatabase().insert(string2, null, contentValues);
        if (l > 0L) {
            string2 = ContentUris.withAppendedId((Uri)uri, (long)l);
            context.getContentResolver().notifyChange(uri, null);
            return string2;
        }
        throw new SQLiteException("Failed to insert row into " + (Object)uri);
    }

    protected int simpleUpdate(Context context, String string2, Uri uri, ContentValues contentValues, String string3, String[] arrstring) {
        int n = this.sqlHelper.getWritableDatabase().update(string2, contentValues, string3, arrstring);
        if (n > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return n;
    }

    public int update(Context context, int n, Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        if (this.canProcess(n)) {
            return this.update(context, uri, contentValues, string2, arrstring);
        }
        if (this.next != null) {
            return this.next.update(context, n, uri, contentValues, string2, arrstring);
        }
        throw new IllegalArgumentException("Unknown uri = " + (Object)uri);
    }

    protected abstract int update(Context var1, Uri var2, ContentValues var3, String var4, String[] var5);

    public static class Builder {
        private UriHandler head;
        private UriHandler tail;

        public Builder addHandler(UriHandler uriHandler) {
            if (this.head == null) {
                this.head = uriHandler;
                this.tail = uriHandler;
                return this;
            }
            this.tail.setNext(uriHandler);
            this.tail = uriHandler;
            return this;
        }

        public UriHandler create() {
            if (this.head == null) {
                throw new NullPointerException("The chain is empty");
            }
            return this.head;
        }
    }

}

