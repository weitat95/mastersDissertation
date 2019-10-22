/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.google.android.gms.tagmanager.zzbe;
import com.google.android.gms.tagmanager.zzbx;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzdo;
import com.google.android.gms.tagmanager.zzfw;
import com.google.android.gms.tagmanager.zzfx;
import com.google.android.gms.tagmanager.zzfy;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

final class zzfv
implements zzbe {
    private final Context mContext;
    private final String zzczb;
    private final zzfy zzkjy;
    private final zzfx zzkjz;

    zzfv(Context context, zzfx zzfx2) {
        this(new zzfw(), context, zzfx2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private zzfv(zzfy object, Context object2, zzfx object3) {
        Locale locale;
        Object var4_5 = null;
        this.zzkjy = object;
        this.mContext = object2.getApplicationContext();
        this.zzkjz = locale;
        String string2 = Build.VERSION.RELEASE;
        locale = Locale.getDefault();
        if (locale == null) {
            object = var4_5;
        } else {
            object = var4_5;
            if (locale.getLanguage() != null) {
                object = var4_5;
                if (locale.getLanguage().length() != 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(locale.getLanguage().toLowerCase());
                    if (locale.getCountry() != null && locale.getCountry().length() != 0) {
                        ((StringBuilder)object).append("-").append(locale.getCountry().toLowerCase());
                    }
                    object = ((StringBuilder)object).toString();
                }
            }
        }
        this.zzczb = String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", "GoogleTagManager", "4.00", string2, object, Build.MODEL, Build.ID);
    }

    private static URL zzd(zzbx object) {
        object = ((zzbx)object).zzbfa();
        try {
            object = new URL((String)object);
            return object;
        }
        catch (MalformedURLException malformedURLException) {
            zzdj.e("Error trying to parse the GTM url.");
            return null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final void zzal(List<zzbx> var1_1) {
        var6_2 = Math.min(var1_1.size(), 40);
        var3_3 = 1;
        var5_4 = 0;
        while (var5_4 < var6_2) {
            block11: {
                block13: {
                    block12: {
                        var9_13 = var1_1.get(var5_4);
                        var7_7 = zzfv.zzd(var9_13);
                        if (var7_7 != null) break block12;
                        zzdj.zzcu("No destination: discarding hit.");
                        this.zzkjz.zzb(var9_13);
                        var2_5 = var3_3;
                        break block11;
                    }
                    var4_6 = var3_3;
                    try {
                        var10_14 = this.zzkjy.zzc((URL)var7_7);
                        var2_5 = var3_3;
                        if (var3_3 == 0) ** GOTO lbl24
                    }
                    catch (IOException var7_9) {
                        var2_5 = var4_6;
                        ** GOTO lbl-1000
                    }
                    zzdo.zzej(this.mContext);
                    var2_5 = 0;
lbl24:
                    // 2 sources
                    var3_3 = var2_5;
                    var10_14.setRequestProperty("User-Agent", this.zzczb);
                    var3_3 = var2_5;
                    var4_6 = var10_14.getResponseCode();
                    var3_3 = var2_5;
                    var7_7 = var10_14.getInputStream();
                    if (var4_6 == 200) ** GOTO lbl35
                    zzdj.zzcu(new StringBuilder(25).append("Bad response: ").append(var4_6).toString());
                    this.zzkjz.zzc(var9_13);
                    break block13;
lbl35:
                    // 1 sources
                    this.zzkjz.zza(var9_13);
                }
                if (var7_7 == null) ** GOTO lbl41
                var4_6 = var2_5;
                var7_7.close();
lbl41:
                // 2 sources
                var4_6 = var2_5;
                var10_14.disconnect();
                break block11;
                catch (Throwable var8_11) {}
                ** GOTO lbl-1000
                catch (Throwable var8_12) {
                    var7_7 = null;
                    var2_5 = var3_3;
                }
lbl-1000:
                // 2 sources
                {
                    if (var7_7 == null) ** GOTO lbl52
                    try {
                        var7_7.close();
lbl52:
                        // 2 sources
                        var10_14.disconnect();
                        throw var8_10;
                    }
                    catch (IOException var7_8) lbl-1000:
                    // 2 sources
                    {
                        var8_10 = (var8_10 = String.valueOf(var7_7.getClass().getSimpleName())).length() != 0 ? "Exception sending hit: ".concat(var8_10) : new String("Exception sending hit: ");
                        zzdj.zzcu(var8_10);
                        zzdj.zzcu(var7_7.getMessage());
                        this.zzkjz.zzc(var9_13);
                    }
                }
            }
            ++var5_4;
            var3_3 = var2_5;
        }
    }

    @Override
    public final boolean zzbeq() {
        NetworkInfo networkInfo = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            zzdj.v("...no network connectivity");
            return false;
        }
        return true;
    }
}

