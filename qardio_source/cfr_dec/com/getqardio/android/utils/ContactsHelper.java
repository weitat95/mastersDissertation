/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ContentResolver
 *  android.content.Intent
 *  android.database.Cursor
 *  android.net.Uri
 *  android.provider.ContactsContract
 *  android.provider.ContactsContract$CommonDataKinds
 *  android.provider.ContactsContract$CommonDataKinds$Email
 *  android.provider.ContactsContract$Contacts
 */
package com.getqardio.android.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import com.getqardio.android.utils.HelperUtils;

public class ContactsHelper {
    private static final Uri CONTACTS_EMAIL_CONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
    private static final String[] PICK_CONTACT_PROJECTION = new String[]{"display_name"};
    private static final String[] PICK_EMAIL_PROJECTION = new String[]{"data1"};

    private static Contact getContactData(Activity object, Intent object2) {
        Uri uri = object2.getData();
        String string2 = null;
        Object var2_6 = null;
        Cursor cursor = object.getContentResolver().query(uri, PICK_CONTACT_PROJECTION, null, null, null);
        object2 = string2;
        if (cursor != null) {
            object2 = string2;
            if (cursor.moveToFirst()) {
                object2 = HelperUtils.getString(cursor, "display_name", "");
            }
        }
        string2 = uri.getLastPathSegment();
        string2 = object.getContentResolver().query(CONTACTS_EMAIL_CONTENT_URI, PICK_EMAIL_PROJECTION, "contact_id=?", new String[]{string2}, null);
        object = var2_6;
        if (string2 != null) {
            object = var2_6;
            if (string2.moveToFirst()) {
                object = HelperUtils.getString((Cursor)string2, "data1", "");
            }
        }
        return new Contact((String)object, (String)object2);
        finally {
            cursor.close();
        }
        finally {
            string2.close();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Contact onActivityResult(Activity activity, int n, int n2, Intent intent) {
        if (n2 != -1) return null;
        switch (n) {
            default: {
                return null;
            }
            case 102: 
        }
        return ContactsHelper.getContactData(activity, intent);
    }

    public static void requestPickContact(Activity activity) {
        activity.startActivityForResult(new Intent("android.intent.action.PICK", ContactsContract.Contacts.CONTENT_URI), 102);
    }

    public static class Contact {
        private String email;
        private String name;

        public Contact(String string2, String string3) {
            this.email = string2;
            this.name = string3;
        }

        public String getEmail() {
            return this.email;
        }

        public String getName() {
            return this.name;
        }
    }

}

