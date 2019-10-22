/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.CancellationSignal
 *  android.os.OperationCanceledException
 */
package android.support.v4.content;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.OperationCanceledException;

public final class ContentResolverCompat {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Cursor query(ContentResolver contentResolver, Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3, android.support.v4.os.CancellationSignal object) {
        if (Build.VERSION.SDK_INT >= 16) {
            if (object != null) {
                try {
                    object = ((android.support.v4.os.CancellationSignal)object).getCancellationSignalObject();
                    do {
                        return contentResolver.query(uri, arrstring, string2, arrstring2, string3, (CancellationSignal)object);
                        break;
                    } while (true);
                }
                catch (Exception exception) {
                    if (!(exception instanceof OperationCanceledException)) throw exception;
                    throw new android.support.v4.os.OperationCanceledException();
                }
            }
            object = null;
            return contentResolver.query(uri, arrstring, string2, arrstring2, string3, (CancellationSignal)object);
        }
        if (object == null) return contentResolver.query(uri, arrstring, string2, arrstring2, string3);
        ((android.support.v4.os.CancellationSignal)object).throwIfCanceled();
        return contentResolver.query(uri, arrstring, string2, arrstring2, string3);
    }
}

