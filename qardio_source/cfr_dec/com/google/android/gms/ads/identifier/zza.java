/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.util.Log
 */
package com.google.android.gms.ads.identifier;

import android.net.Uri;
import android.util.Log;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.zzc;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

final class zza
extends Thread {
    private /* synthetic */ Map zzanb;

    zza(AdvertisingIdClient advertisingIdClient, Map map) {
        this.zzanb = map;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final void run() {
        block8: {
            new zzc();
            var2_1 = this.zzanb;
            var3_6 = Uri.parse((String)"https://pagead2.googlesyndication.com/pagead/gen_204?id=gmob-apps").buildUpon();
            for (String var5_10 : var2_1.keySet()) {
                var3_6.appendQueryParameter(var5_10, (String)var2_1.get(var5_10));
            }
            var3_7 = var3_6.build().toString();
            var2_1 = (HttpURLConnection)new URL(var3_7).openConnection();
            var1_11 = var2_1.getResponseCode();
            if (var1_11 >= 200 && var1_11 < 300) break block8;
            Log.w((String)"HttpUrlPinger", (String)new StringBuilder(String.valueOf(var3_7).length() + 65).append("Received non-success response code ").append(var1_11).append(" from pinging URL: ").append(var3_7).toString());
            {
                catch (Throwable var4_9) {
                    var2_1.disconnect();
                    throw var4_9;
                }
            }
        }
        try {
            var2_1.disconnect();
            return;
        }
        catch (IndexOutOfBoundsException var2_2) {
            var4_8 = var2_2.getMessage();
            Log.w((String)"HttpUrlPinger", (String)new StringBuilder(String.valueOf(var3_7).length() + 32 + String.valueOf(var4_8).length()).append("Error while parsing ping URL: ").append(var3_7).append(". ").append((String)var4_8).toString(), (Throwable)var2_2);
            return;
        }
        catch (RuntimeException var2_3) {}
        ** GOTO lbl-1000
        catch (IOException var2_5) {}
lbl-1000:
        // 2 sources
        {
            var4_8 = var2_4.getMessage();
            Log.w((String)"HttpUrlPinger", (String)new StringBuilder(String.valueOf(var3_7).length() + 27 + String.valueOf(var4_8).length()).append("Error while pinging URL: ").append(var3_7).append(". ").append((String)var4_8).toString(), (Throwable)var2_4);
            return;
        }
    }
}

