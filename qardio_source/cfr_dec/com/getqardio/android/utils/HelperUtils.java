/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 */
package com.getqardio.android.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.getqardio.android.utils.CipherManager;
import java.util.Date;

public abstract class HelperUtils {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Boolean getBoolean(Cursor cursor, int n, Boolean bl) {
        boolean bl2;
        Boolean bl3 = bl;
        if (n == -1) return bl3;
        bl3 = bl;
        if (cursor.isNull(n)) return bl3;
        if (cursor.getInt(n) == 0) {
            bl2 = false;
            do {
                return bl2;
                break;
            } while (true);
        }
        bl2 = true;
        return bl2;
    }

    public static Boolean getBoolean(Cursor cursor, String string2, Boolean bl) {
        return HelperUtils.getBoolean(cursor, cursor.getColumnIndex(string2), bl);
    }

    public static Date getDate(Cursor cursor, int n, Date date) {
        Date date2 = date;
        if (n != -1) {
            date2 = date;
            if (!cursor.isNull(n)) {
                date2 = new Date(cursor.getLong(n));
            }
        }
        return date2;
    }

    public static Date getDate(Cursor cursor, String string2, Date date) {
        return HelperUtils.getDate(cursor, cursor.getColumnIndex(string2), date);
    }

    public static Double getDouble(Cursor cursor, int n, Double d) {
        Double d2 = d;
        if (n != -1) {
            d2 = d;
            if (!cursor.isNull(n)) {
                d2 = cursor.getDouble(n);
            }
        }
        return d2;
    }

    public static Double getDouble(Cursor cursor, String string2, Double d) {
        return HelperUtils.getDouble(cursor, cursor.getColumnIndex(string2), d);
    }

    public static Float getFloat(Cursor cursor, int n, Float f) {
        Float f2 = f;
        if (n != -1) {
            f2 = f;
            if (!cursor.isNull(n)) {
                f2 = Float.valueOf(cursor.getFloat(n));
            }
        }
        return f2;
    }

    public static Float getFloat(Cursor cursor, String string2, Float f) {
        return HelperUtils.getFloat(cursor, cursor.getColumnIndex(string2), f);
    }

    public static Integer getInt(Cursor cursor, int n, Integer n2) {
        Integer n3 = n2;
        if (n != -1) {
            n3 = n2;
            if (!cursor.isNull(n)) {
                n3 = cursor.getInt(n);
            }
        }
        return n3;
    }

    public static Integer getInt(Cursor cursor, String string2, Integer n) {
        return HelperUtils.getInt(cursor, cursor.getColumnIndex(string2), n);
    }

    public static Long getLong(Cursor cursor, int n, Long l) {
        Long l2 = l;
        if (n != -1) {
            l2 = l;
            if (!cursor.isNull(n)) {
                l2 = cursor.getLong(n);
            }
        }
        return l2;
    }

    public static Long getLong(Cursor cursor, String string2, Long l) {
        return HelperUtils.getLong(cursor, cursor.getColumnIndex(string2), l);
    }

    public static String getString(Context context, Cursor object, int n, String string2, boolean bl) {
        if (n != -1 && !object.isNull(n)) {
            string2 = object.getString(n);
            object = string2;
            if (bl) {
                object = CipherManager.decrypt(context, string2);
            }
            return object;
        }
        return string2;
    }

    public static String getString(Context context, Cursor cursor, String string2, String string3, boolean bl) {
        return HelperUtils.getString(context, cursor, cursor.getColumnIndex(string2), string3, bl);
    }

    public static String getString(Cursor cursor, String string2, String string3) {
        return HelperUtils.getString(null, cursor, string2, string3, false);
    }

    public static boolean isNull(Cursor cursor, int n) {
        return cursor.isNull(n);
    }

    public static boolean isNull(Cursor cursor, String string2) {
        return HelperUtils.isNull(cursor, cursor.getColumnIndex(string2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void put(ContentValues contentValues, String string2, Boolean bl) {
        if (bl != null) {
            int n = bl != false ? 1 : 0;
            contentValues.put(string2, Integer.valueOf(n));
        }
    }

    public static void put(ContentValues contentValues, String string2, Double d) {
        if (d != null) {
            contentValues.put(string2, d);
        }
    }

    public static void put(ContentValues contentValues, String string2, Float f) {
        if (f != null) {
            contentValues.put(string2, f);
        }
    }

    public static void put(ContentValues contentValues, String string2, Integer n) {
        if (n != null) {
            contentValues.put(string2, n);
        }
    }

    public static void put(ContentValues contentValues, String string2, Long l) {
        if (l != null) {
            contentValues.put(string2, l);
        }
    }

    public static void put(ContentValues contentValues, String string2, String string3) {
        HelperUtils.put(null, contentValues, string2, string3, false);
    }

    public static void put(ContentValues contentValues, String string2, Date date) {
        if (date != null) {
            contentValues.put(string2, Long.valueOf(date.getTime()));
        }
    }

    public static void put(Context context, ContentValues contentValues, String string2, String string3, boolean bl) {
        if (string3 != null) {
            String string4 = string3;
            if (bl) {
                string4 = CipherManager.encrypt(context, string3);
            }
            contentValues.put(string2, string4);
        }
    }
}

