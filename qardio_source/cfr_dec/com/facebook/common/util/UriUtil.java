/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.database.Cursor
 *  android.net.Uri
 *  android.provider.ContactsContract
 *  android.provider.MediaStore
 *  android.provider.MediaStore$Images
 *  android.provider.MediaStore$Images$Media
 *  javax.annotation.Nullable
 */
package com.facebook.common.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import javax.annotation.Nullable;

public class UriUtil {
    private static final String LOCAL_CONTACT_IMAGE_PREFIX = Uri.withAppendedPath((Uri)ContactsContract.AUTHORITY_URI, (String)"display_photo").getPath();

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    public static String getRealPathFromUri(ContentResolver object, Uri uri) {
        Cursor cursor;
        Cursor cursor2 = null;
        Object var4_10 = null;
        if (UriUtil.isLocalContentUri((Uri)cursor)) {
            void var0_5;
            block6: {
                cursor2 = null;
                try {
                    cursor = object.query((Uri)cursor, null, null, null, null);
                    Object var0_1 = var4_10;
                    if (cursor == null) break block6;
                    Object var0_2 = var4_10;
                    cursor2 = cursor;
                }
                catch (Throwable throwable) {
                    if (cursor2 == null) throw throwable;
                    cursor2.close();
                    throw throwable;
                }
                if (!cursor.moveToFirst()) break block6;
                cursor2 = cursor;
                int n = cursor.getColumnIndex("_data");
                Object var0_3 = var4_10;
                if (n == -1) break block6;
                cursor2 = cursor;
                String string2 = cursor.getString(n);
            }
            cursor2 = var0_5;
            if (cursor == null) return cursor2;
            cursor.close();
            return var0_5;
        }
        if (!UriUtil.isLocalFileUri((Uri)cursor)) return cursor2;
        return cursor.getPath();
    }

    @Nullable
    public static String getSchemeOrNull(@Nullable Uri uri) {
        if (uri == null) {
            return null;
        }
        return uri.getScheme();
    }

    public static boolean isDataUri(@Nullable Uri uri) {
        return "data".equals(UriUtil.getSchemeOrNull(uri));
    }

    public static boolean isLocalAssetUri(@Nullable Uri uri) {
        return "asset".equals(UriUtil.getSchemeOrNull(uri));
    }

    public static boolean isLocalCameraUri(Uri object) {
        return ((String)(object = object.toString())).startsWith(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString()) || ((String)object).startsWith(MediaStore.Images.Media.INTERNAL_CONTENT_URI.toString());
    }

    public static boolean isLocalContactUri(Uri uri) {
        return UriUtil.isLocalContentUri(uri) && "com.android.contacts".equals(uri.getAuthority()) && !uri.getPath().startsWith(LOCAL_CONTACT_IMAGE_PREFIX);
    }

    public static boolean isLocalContentUri(@Nullable Uri uri) {
        return "content".equals(UriUtil.getSchemeOrNull(uri));
    }

    public static boolean isLocalFileUri(@Nullable Uri uri) {
        return "file".equals(UriUtil.getSchemeOrNull(uri));
    }

    public static boolean isLocalResourceUri(@Nullable Uri uri) {
        return "res".equals(UriUtil.getSchemeOrNull(uri));
    }

    public static boolean isNetworkUri(@Nullable Uri object) {
        return "https".equals(object = UriUtil.getSchemeOrNull(object)) || "http".equals(object);
    }
}

