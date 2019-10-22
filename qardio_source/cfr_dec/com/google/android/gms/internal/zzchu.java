/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchq;
import com.google.android.gms.internal.zzchr;
import com.google.android.gms.internal.zzchs;
import com.google.android.gms.internal.zzcht;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzclq;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class zzchu
implements Runnable {
    private final String mPackageName;
    private final URL zzbxv;
    private final byte[] zzgfx;
    private final zzchs zzjck;
    private final Map<String, String> zzjcl;
    private /* synthetic */ zzchq zzjcm;

    public zzchu(String string2, URL uRL, byte[] arrby, Map<String, String> map, zzchs zzchs2) {
        this.zzjcm = var1_1;
        zzbq.zzgm(string2);
        zzbq.checkNotNull(uRL);
        zzbq.checkNotNull(zzchs2);
        this.zzbxv = uRL;
        this.zzgfx = arrby;
        this.zzjck = zzchs2;
        this.mPackageName = string2;
        this.zzjcl = map;
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
        var4_1 = 0;
        var5_2 = 0;
        var1_3 = 0;
        this.zzjcm.zzawj();
        try {
            var6_4 = this.zzbxv.openConnection();
            if (!(var6_4 instanceof HttpURLConnection)) {
                throw new IOException("Failed to obtain HTTP connection");
            }
            var6_4 = (HttpURLConnection)var6_4;
            var6_4.setDefaultUseCaches(false);
            var6_4.setConnectTimeout(60000);
            var6_4.setReadTimeout(61000);
            var6_4.setInstanceFollowRedirects(false);
            var6_4.setDoInput(true);
            var2_19 = var4_1;
            var3_20 = var5_2;
            ** GOTO lbl25
        }
        catch (IOException var8_7) {
            block28: {
                block26: {
                    block25: {
                        var9_16 = null;
                        var1_3 = 0;
                        var6_4 = null;
                        var7_18 = null;
                        break block28;
lbl25:
                        // 2 sources
                        if (this.zzjcl != null) {
                            var2_19 = var4_1;
                            var3_20 = var5_2;
                            var7_18 = this.zzjcl.entrySet().iterator();
                            do {
                                var2_19 = var4_1;
                                var3_20 = var5_2;
                                if (!var7_18.hasNext()) break;
                                var2_19 = var4_1;
                                var3_20 = var5_2;
                                var8_8 = (Map.Entry)var7_18.next();
                                var2_19 = var4_1;
                                var3_20 = var5_2;
                                var6_4.addRequestProperty((String)var8_8.getKey(), (String)var8_8.getValue());
                            } while (true);
                        }
                        var2_19 = var4_1;
                        var3_20 = var5_2;
                        if (this.zzgfx == null) break block25;
                        var2_19 = var4_1;
                        var3_20 = var5_2;
                        var8_8 = this.zzjcm.zzawu().zzq(this.zzgfx);
                        var2_19 = var4_1;
                        var3_20 = var5_2;
                        this.zzjcm.zzawy().zzazj().zzj("Uploading data. size", ((byte[])var8_8).length);
                        var2_19 = var4_1;
                        var3_20 = var5_2;
                        var6_4.setDoOutput(true);
                        var2_19 = var4_1;
                        var3_20 = var5_2;
                        var6_4.addRequestProperty("Content-Encoding", "gzip");
                        var2_19 = var4_1;
                        var3_20 = var5_2;
                        var6_4.setFixedLengthStreamingMode(((Object)var8_8).length);
                        var2_19 = var4_1;
                        var3_20 = var5_2;
                        var6_4.connect();
                        var2_19 = var4_1;
                        var3_20 = var5_2;
                        var7_18 = var6_4.getOutputStream();
                        var7_18.write((byte[])var8_8);
                        var7_18.close();
                    }
                    var2_19 = var4_1;
                    var3_20 = var5_2;
                    var2_19 = var1_3 = var6_4.getResponseCode();
                    var3_20 = var1_3;
                    var7_18 = var6_4.getHeaderFields();
                    try {
                        var8_8 = zzchq.zza(this.zzjcm, (HttpURLConnection)var6_4);
                        if (var6_4 == null) break block26;
                        var6_4.disconnect();
                    }
                    catch (IOException var8_14) {
                        var9_16 = var7_18;
                        var7_18 = var6_4;
                        var6_4 = null;
                    }
                }
                this.zzjcm.zzawx().zzg(new zzcht(this.mPackageName, this.zzjck, var1_3, null, (byte[])var8_8, (Map)var7_18, null));
                return;
                catch (Throwable var8_9) {
                    block27: {
                        var7_18 = null;
                        var6_4 = null;
                        var9_17 /* !! */  = null;
                        break block27;
                        catch (Throwable var8_11) {
                            var7_18 = null;
                            var10_21 = null;
                            var9_17 /* !! */  = var6_4;
                            var6_4 = var10_21;
                            var1_3 = var2_19;
                            break block27;
                        }
                        catch (Throwable var8_12) {
                            var10_22 = null;
                            var9_17 /* !! */  = var6_4;
                            var6_4 = var7_18;
                            var7_18 = var10_22;
                            break block27;
                        }
                        catch (Throwable var8_13) {
                            var10_23 = null;
                            var9_17 /* !! */  = var6_4;
                            var6_4 = var10_23;
                        }
                    }
                    if (var6_4 != null) {
                        try {
                            var6_4.close();
                        }
                        catch (IOException var6_6) {
                            this.zzjcm.zzawy().zzazd().zze("Error closing HTTP compressed POST connection output stream. appId", zzchm.zzjk(this.mPackageName), var6_6);
                        }
                    }
                    if (var9_17 /* !! */  != null) {
                        var9_17 /* !! */ .disconnect();
                    }
                    this.zzjcm.zzawx().zzg(new zzcht(this.mPackageName, this.zzjck, var1_3, null, null, (Map)var7_18, null));
                    throw var8_10;
                }
                catch (IOException var10_24) {
                    var9_16 = null;
                    var1_3 = 0;
                    var8_8 = var6_4;
                    var6_4 = var7_18;
                    var7_18 = var8_8;
                    var8_8 = var10_24;
                    break block28;
                }
                break block28;
                catch (IOException var8_15) {
                    var9_16 = null;
                    var1_3 = var3_20;
                    var7_18 = var6_4;
                    var6_4 = null;
                }
            }
            if (var6_4 != null) {
                try {
                    var6_4.close();
                }
                catch (IOException var6_5) {
                    this.zzjcm.zzawy().zzazd().zze("Error closing HTTP compressed POST connection output stream. appId", zzchm.zzjk(this.mPackageName), var6_5);
                }
            }
            if (var7_18 != null) {
                var7_18.disconnect();
            }
            this.zzjcm.zzawx().zzg(new zzcht(this.mPackageName, this.zzjck, var1_3, (Throwable)var8_8, null, var9_16, null));
            return;
        }
    }
}

