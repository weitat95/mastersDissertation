/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.utils;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.wearable.DataMap;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import timber.log.Timber;

public class CrashlyticsUtil {
    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void logExceptionFromWear(DataMap var0) {
        var1_5 = new ByteArrayInputStream(var0.getByteArray("com.getqardio.shared.wearable.key.EXCEPTION"));
        try {
            var1_5 = (Throwable)new ObjectInputStream((InputStream)var1_5).readObject();
            Crashlytics.setBool("wear_exception", true);
            Crashlytics.setString("board", var0.getString("com.getqardio.shared.wearable.key.BOARD"));
            Crashlytics.setString("fingerprint", var0.getString("com.getqardio.shared.wearable.key.FINGERPRINT"));
            Crashlytics.setString("model", var0.getString("com.getqardio.shared.wearable.key.MODEL"));
            Crashlytics.setString("manufacturer", var0.getString("com.getqardio.shared.wearable.key.MANUFACTURER"));
            Crashlytics.setString("product", var0.getString("com.getqardio.shared.wearable.key.PRODUCT"));
            Crashlytics.logException((Throwable)var1_5);
            return;
        }
        catch (IOException var0_1) {}
        ** GOTO lbl-1000
        catch (ClassNotFoundException var0_4) {}
lbl-1000:
        // 2 sources
        {
            Timber.e((Throwable)var0_2);
            return;
        }
    }
}

