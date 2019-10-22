/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 *  android.net.Uri$Builder
 */
package com.getqardio.android.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.getqardio.android.datamodel.SupportTicket;
import com.getqardio.android.provider.AppProvideContract;
import com.getqardio.android.utils.HelperUtils;

public abstract class SupportTicketsHelper {
    private static Uri buildSupportTicketUri() {
        return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("support_tickets").build();
    }

    private static Uri buildSupportTicketsUri(long l) {
        return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("support_tickets").appendPath(Long.toString(l)).build();
    }

    public static int deleteTicket(Context context, long l) {
        Uri uri = SupportTicketsHelper.buildSupportTicketsUri(l);
        return context.getContentResolver().delete(uri, null, null);
    }

    public static SupportTicket getSupportTicket(Context object, int n) {
        Cursor cursor = object.getContentResolver().query(SupportTicketsHelper.buildSupportTicketsUri(n), null, null, null, null);
        Object var2_3 = null;
        object = var2_3;
        if (cursor != null) {
            object = var2_3;
            if (cursor.moveToFirst()) {
                object = SupportTicketsHelper.parseSupportTicket(cursor);
            }
        }
        return object;
    }

    public static int insertTicket(Context context, SupportTicket supportTicket) {
        Uri uri = SupportTicketsHelper.buildSupportTicketUri();
        int n = -1;
        ContentValues contentValues = new ContentValues(2);
        contentValues.put("subject", supportTicket.subject);
        contentValues.put("message_body", supportTicket.messageBody);
        context = context.getContentResolver().insert(uri, contentValues);
        if (context != null) {
            n = Integer.valueOf(context.getLastPathSegment());
        }
        return n;
    }

    public static SupportTicket parseSupportTicket(Cursor cursor) {
        SupportTicket supportTicket = new SupportTicket();
        supportTicket._id = HelperUtils.getLong(cursor, "_id", (Long)0L);
        supportTicket.subject = HelperUtils.getString(cursor, "subject", "");
        supportTicket.messageBody = HelperUtils.getString(cursor, "message_body", "");
        return supportTicket;
    }
}

