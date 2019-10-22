/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.ParcelFileDescriptor
 *  android.os.ParcelFileDescriptor$AutoCloseOutputStream
 *  android.util.Log
 */
package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.google.android.gms.wearable.internal.zzhg;
import java.io.IOException;
import java.util.concurrent.Callable;

final class zzhh
implements Callable<Boolean> {
    private /* synthetic */ byte[] zzkrx;
    private /* synthetic */ ParcelFileDescriptor zzllq;

    zzhh(zzhg zzhg2, ParcelFileDescriptor parcelFileDescriptor, byte[] arrby) {
        this.zzllq = parcelFileDescriptor;
        this.zzkrx = arrby;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final Boolean zzbkf() {
        String string2;
        Object object;
        if (Log.isLoggable((String)"WearableClient", (int)3)) {
            string2 = String.valueOf((Object)this.zzllq);
            Log.d((String)"WearableClient", (String)new StringBuilder(String.valueOf(string2).length() + 36).append("processAssets: writing data to FD : ").append(string2).toString());
        }
        string2 = new ParcelFileDescriptor.AutoCloseOutputStream(this.zzllq);
        string2.write(this.zzkrx);
        string2.flush();
        if (Log.isLoggable((String)"WearableClient", (int)3)) {
            object = String.valueOf((Object)this.zzllq);
            Log.d((String)"WearableClient", (String)new StringBuilder(String.valueOf(object).length() + 27).append("processAssets: wrote data: ").append((String)object).toString());
        }
        object = true;
        try {
            if (Log.isLoggable((String)"WearableClient", (int)3)) {
                String string3 = String.valueOf((Object)this.zzllq);
                Log.d((String)"WearableClient", (String)new StringBuilder(String.valueOf(string3).length() + 24).append("processAssets: closing: ").append(string3).toString());
            }
            string2.close();
            return object;
        }
        catch (IOException iOException) {
            return object;
        }
        catch (IOException iOException) {
            String string4;
            try {
                string4 = String.valueOf((Object)this.zzllq);
                Log.w((String)"WearableClient", (String)new StringBuilder(String.valueOf(string4).length() + 36).append("processAssets: writing data failed: ").append(string4).toString());
            }
            catch (Throwable throwable) {
                try {
                    if (Log.isLoggable((String)"WearableClient", (int)3)) {
                        String string5 = String.valueOf((Object)this.zzllq);
                        Log.d((String)"WearableClient", (String)new StringBuilder(String.valueOf(string5).length() + 24).append("processAssets: closing: ").append(string5).toString());
                    }
                    string2.close();
                }
                catch (IOException iOException2) {
                    throw throwable;
                }
                throw throwable;
            }
            try {
                if (Log.isLoggable((String)"WearableClient", (int)3)) {
                    string4 = String.valueOf((Object)this.zzllq);
                    Log.d((String)"WearableClient", (String)new StringBuilder(String.valueOf(string4).length() + 24).append("processAssets: closing: ").append(string4).toString());
                }
                string2.close();
            }
            catch (IOException iOException3) {
                return false;
            }
            return false;
        }
    }

    @Override
    public final /* synthetic */ Object call() throws Exception {
        return this.zzbkf();
    }
}

