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
package com.google.android.gms.internal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzapr;
import com.google.android.gms.internal.zzaqa;
import com.google.android.gms.internal.zzaqb;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzaqt;
import com.google.android.gms.internal.zzaqz;
import com.google.android.gms.internal.zzard;
import com.google.android.gms.internal.zzarl;
import com.google.android.gms.internal.zzarm;
import com.google.android.gms.internal.zzarq;
import com.google.android.gms.internal.zzarv;
import com.google.android.gms.internal.zzary;
import com.google.android.gms.internal.zzash;
import com.google.android.gms.internal.zzasl;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

final class zzarx
extends zzaqa {
    private static final byte[] zzdyj = "\n".getBytes();
    private final String zzczb = String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", "GoogleAnalytics", zzaqb.VERSION, Build.VERSION.RELEASE, zzasl.zza(Locale.getDefault()), Build.MODEL, Build.ID);
    private final zzash zzdyi;

    zzarx(zzaqc zzaqc2) {
        super(zzaqc2);
        this.zzdyi = new zzash(zzaqc2.zzws());
    }

    private final int zza(URL object) {
        Object object2;
        Object object3;
        int n;
        block12: {
            zzbq.checkNotNull(object);
            this.zzb("GET request", object);
            object2 = null;
            object3 = null;
            object3 = object = this.zzb((URL)object);
            object2 = object;
            ((URLConnection)object).connect();
            object3 = object;
            object2 = object;
            this.zzb((HttpURLConnection)object);
            object3 = object;
            object2 = object;
            n = ((HttpURLConnection)object).getResponseCode();
            if (n != 200) break block12;
            object3 = object;
            object2 = object;
            this.zzwx().zzwq();
        }
        object3 = object;
        object2 = object;
        try {
            this.zzb("GET status", n);
            if (object != null) {
                ((HttpURLConnection)object).disconnect();
            }
            return n;
        }
        catch (IOException iOException) {
            object2 = object3;
            try {
                this.zzd("Network GET connection error", iOException);
                if (object3 != null) {
                    ((HttpURLConnection)object3).disconnect();
                }
                return 0;
            }
            catch (Throwable throwable) {
                if (object2 != null) {
                    ((HttpURLConnection)object2).disconnect();
                }
                throw throwable;
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private final int zza(URL var1_1, byte[] var2_5) {
        block24: {
            var5_11 = null;
            var7_12 = null;
            var8_13 = null;
            var6_14 = null;
            zzbq.checkNotNull(var1_1);
            zzbq.checkNotNull(var2_5);
            this.zzb("POST bytes, url", var2_5.length, var1_1);
            if (zzarx.zzpz()) {
                this.zza("Post payload\n", new String(var2_5));
            }
            this.getContext().getPackageName();
            var5_11 = var1_1 = this.zzb((URL)var1_1);
            var1_1 = var7_12;
            var4_15 = var5_11;
            var6_14 = var8_13;
            var5_11.setDoOutput(true);
            var1_1 = var7_12;
            var4_15 = var5_11;
            var6_14 = var8_13;
            var5_11.setFixedLengthStreamingMode(var2_5.length);
            var1_1 = var7_12;
            var4_15 = var5_11;
            var6_14 = var8_13;
            var5_11.connect();
            var1_1 = var7_12;
            var4_15 = var5_11;
            var6_14 = var8_13;
            var7_12 = var5_11.getOutputStream();
            var1_1 = var7_12;
            var4_15 = var5_11;
            var6_14 = var7_12;
            var7_12.write(var2_5);
            var1_1 = var7_12;
            var4_15 = var5_11;
            var6_14 = var7_12;
            this.zzb((HttpURLConnection)var5_11);
            var1_1 = var7_12;
            var4_15 = var5_11;
            var6_14 = var7_12;
            var3_16 = var5_11.getResponseCode();
            if (var3_16 == 200) {
                var1_1 = var7_12;
                var4_15 = var5_11;
                var6_14 = var7_12;
                this.zzwx().zzwq();
            }
            var1_1 = var7_12;
            var4_15 = var5_11;
            var6_14 = var7_12;
            this.zzb("POST status", var3_16);
            if (var7_12 == null) break block24;
            try {
                var7_12.close();
            }
            catch (IOException var1_2) {
                this.zze("Error closing http post connection output stream", var1_2);
                ** continue;
            }
        }
lbl61:
        // 2 sources
        do {
            if (var5_11 != null) {
                var5_11.disconnect();
            }
            return var3_16;
            break;
        } while (true);
        catch (IOException var2_6) {
            var5_11 = null;
lbl67:
            // 2 sources
            do {
                block25: {
                    var1_1 = var6_14;
                    var4_15 = var5_11;
                    this.zzd("Network POST connection error", var2_5);
                    if (var6_14 == null) break block25;
                    try {
                        var6_14.close();
                    }
                    catch (IOException var1_3) {
                        this.zze("Error closing http post connection output stream", var1_3);
                        ** continue;
                    }
                }
lbl79:
                // 2 sources
                do {
                    if (var5_11 != null) {
                        var5_11.disconnect();
                    }
                    return 0;
                    break;
                } while (true);
                break;
            } while (true);
        }
        catch (Throwable var2_7) {
            var4_15 = null;
            var1_1 = var5_11;
lbl86:
            // 2 sources
            do {
                if (var1_1 != null) {
                    var1_1.close();
                }
lbl90:
                // 4 sources
                do {
                    if (var4_15 != null) {
                        var4_15.disconnect();
                    }
                    throw var2_8;
                    break;
                } while (true);
                catch (IOException var1_4) {
                    this.zze("Error closing http post connection output stream", var1_4);
                    ** continue;
                }
                break;
            } while (true);
        }
        {
            catch (Throwable var2_9) {
                ** continue;
            }
        }
        catch (IOException var2_10) {
            ** continue;
        }
    }

    private static void zza(StringBuilder stringBuilder, String string2, String string3) throws UnsupportedEncodingException {
        if (stringBuilder.length() != 0) {
            stringBuilder.append('&');
        }
        stringBuilder.append(URLEncoder.encode(string2, "UTF-8"));
        stringBuilder.append('=');
        stringBuilder.append(URLEncoder.encode(string3, "UTF-8"));
    }

    /*
     * Exception decompiling
     */
    private final int zzb(URL var1_1, byte[] var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [8[CATCHBLOCK]], but top level block is 10[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private final HttpURLConnection zzb(URL object) throws IOException {
        if (!((object = ((URL)object).openConnection()) instanceof HttpURLConnection)) {
            throw new IOException("Failed to obtain http connection");
        }
        object = (HttpURLConnection)object;
        ((URLConnection)object).setDefaultUseCaches(false);
        ((URLConnection)object).setConnectTimeout(zzarl.zzdxa.get());
        ((URLConnection)object).setReadTimeout(zzarl.zzdxb.get());
        ((HttpURLConnection)object).setInstanceFollowRedirects(false);
        ((URLConnection)object).setRequestProperty("User-Agent", this.zzczb);
        ((URLConnection)object).setDoInput(true);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final URL zzb(zzarq object, String string2) {
        if (((zzarq)object).zzzk()) {
            object = zzard.zzyw();
            String string3 = zzard.zzyy();
            object = new StringBuilder(String.valueOf(object).length() + 1 + String.valueOf(string3).length() + String.valueOf(string2).length()).append((String)object).append(string3).append("?").append(string2).toString();
        } else {
            object = zzard.zzyx();
            String string4 = zzard.zzyy();
            object = new StringBuilder(String.valueOf(object).length() + 1 + String.valueOf(string4).length() + String.valueOf(string2).length()).append((String)object).append(string4).append("?").append(string2).toString();
        }
        try {
            return new URL((String)object);
        }
        catch (MalformedURLException malformedURLException) {
            this.zze("Error trying to parse the hardcoded host url", malformedURLException);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzb(HttpURLConnection object) throws IOException {
        Object object2 = null;
        try {
            int n;
            object2 = object = ((URLConnection)object).getInputStream();
            byte[] arrby = new byte[1024];
            do {
                object2 = object;
            } while ((n = ((InputStream)object).read(arrby)) > 0);
            if (object == null) return;
        }
        catch (Throwable throwable) {
            if (object2 == null) throw throwable;
            try {
                ((InputStream)object2).close();
            }
            catch (IOException iOException) {
                this.zze("Error closing http connection input stream", iOException);
                throw throwable;
            }
            throw throwable;
        }
        try {
            ((InputStream)object).close();
            return;
        }
        catch (IOException iOException) {
            this.zze("Error closing http connection input stream", iOException);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final URL zzd(zzarq object) {
        if (((zzarq)object).zzzk()) {
            object = String.valueOf(zzard.zzyw());
            String string2 = String.valueOf(zzard.zzyy());
            object = string2.length() != 0 ? ((String)object).concat(string2) : new String((String)object);
        } else {
            object = String.valueOf(zzard.zzyx());
            String string3 = String.valueOf(zzard.zzyy());
            object = string3.length() != 0 ? ((String)object).concat(string3) : new String((String)object);
        }
        try {
            return new URL((String)object);
        }
        catch (MalformedURLException malformedURLException) {
            this.zze("Error trying to parse the hardcoded host url", malformedURLException);
            return null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private final List<Long> zzv(List<zzarq> var1_1) {
        var4_2 = new ArrayList<Long>(var1_1.size());
        var1_1 = var1_1.iterator();
        do {
            block4: {
                block9: {
                    block8: {
                        block7: {
                            block5: {
                                block6: {
                                    block3: {
                                        if (var1_1.hasNext() == false) return var4_2;
                                        var5_5 = (zzarq)var1_1.next();
                                        zzbq.checkNotNull(var5_5);
                                        var3_4 = var5_5.zzzk() == false;
                                        var6_7 = this.zza(var5_5, var3_4);
                                        if (var6_7 != null) break block3;
                                        this.zzwt().zza(var5_5, "Error formatting hit for upload");
                                        var2_3 = true;
                                        break block4;
                                    }
                                    if (var6_7.length() > zzarl.zzdwq.get()) break block5;
                                    var6_8 = this.zzb(var5_5, var6_7);
                                    if (var6_8 != null) break block6;
                                    this.zzdy("Failed to build collect GET endpoint url");
                                    ** GOTO lbl-1000
                                }
                                var2_3 = this.zza(var6_8) == 200;
                                break block4;
                            }
                            var6_10 = this.zza(var5_5, false);
                            if (var6_10 != null) break block7;
                            this.zzwt().zza(var5_5, "Error formatting hit for POST upload");
                            var2_3 = true;
                            break block4;
                        }
                        var6_11 = var6_10.getBytes();
                        if (var6_11.length <= zzarl.zzdwv.get()) break block8;
                        this.zzwt().zza(var5_5, "Hit payload exceeds size limit");
                        var2_3 = true;
                        break block4;
                    }
                    var7_12 = this.zzd(var5_5);
                    if (var7_12 != null) break block9;
                    this.zzdy("Failed to build collect POST endpoint url");
                    ** GOTO lbl-1000
                }
                if (this.zza(var7_12, var6_11) == 200) {
                    var2_3 = true;
                } else lbl-1000:
                // 3 sources
                {
                    var2_3 = false;
                }
            }
            if (var2_3 == false) return var4_2;
            var4_2.add(var5_5.zzzh());
        } while (var4_2.size() < zzard.zzyu());
        return var4_2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final URL zzzt() {
        Object object = String.valueOf(zzard.zzyw());
        String string2 = String.valueOf(zzarl.zzdwp.get());
        object = string2.length() != 0 ? ((String)object).concat(string2) : new String((String)object);
        try {
            return new URL((String)object);
        }
        catch (MalformedURLException malformedURLException) {
            this.zze("Error trying to parse the hardcoded host url", malformedURLException);
            return null;
        }
    }

    static /* synthetic */ byte[] zzzu() {
        return zzdyj;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final String zza(zzarq object, boolean bl) {
        StringBuilder stringBuilder;
        block4: {
            block6: {
                long l;
                block5: {
                    zzbq.checkNotNull(object);
                    stringBuilder = new StringBuilder();
                    try {
                        for (Map.Entry<String, String> entry : ((zzarq)object).zzjh().entrySet()) {
                            String string2 = entry.getKey();
                            if ("ht".equals(string2) || "qt".equals(string2) || "AppUID".equals(string2) || "z".equals(string2) || "_gmsv".equals(string2)) continue;
                            zzarx.zza(stringBuilder, string2, entry.getValue());
                        }
                        zzarx.zza(stringBuilder, "ht", String.valueOf(((zzarq)object).zzzi()));
                        zzarx.zza(stringBuilder, "qt", String.valueOf(this.zzws().currentTimeMillis() - ((zzarq)object).zzzi()));
                        if (!bl) break block4;
                        l = ((zzarq)object).zzzl();
                        if (l == 0L) break block5;
                        object = String.valueOf(l);
                        break block6;
                    }
                    catch (UnsupportedEncodingException unsupportedEncodingException) {
                        this.zze("Failed to encode name or value", unsupportedEncodingException);
                        return null;
                    }
                }
                l = ((zzarq)object).zzzh();
                object = String.valueOf(l);
            }
            zzarx.zza(stringBuilder, "z", (String)object);
        }
        return stringBuilder.toString();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public final List<Long> zzu(List<zzarq> var1_1) {
        block7: {
            var5_2 = true;
            zzj.zzve();
            this.zzxf();
            zzbq.checkNotNull(var1_1);
            if (!this.zzwu().zzyz().isEmpty() && this.zzdyi.zzu((long)zzarl.zzdwy.get().intValue() * 1000L)) break block7;
            var3_3 = 0;
            ** GOTO lbl-1000
        }
        var2_4 = zzaqt.zzed(zzarl.zzdwr.get()) != zzaqt.zzdux ? 1 : 0;
        var3_3 = var2_4;
        if (zzaqz.zzee(zzarl.zzdws.get()) == zzaqz.zzdvi) {
            var4_5 = true;
        } else lbl-1000:
        // 2 sources
        {
            var4_5 = false;
            var2_4 = var3_3;
        }
        if (var2_4 == 0) return this.zzv((List<zzarq>)var1_1);
        if (var1_1.isEmpty()) {
            var5_2 = false;
        }
        zzbq.checkArgument(var5_2);
        this.zza("Uploading batched hits. compression, count", var4_5, var1_1.size());
        var6_6 = new zzary(this);
        var7_7 = new ArrayList<Long>();
        var1_1 = var1_1.iterator();
        while (var1_1.hasNext() && var6_6.zze(var8_8 = (zzarq)var1_1.next())) {
            var7_7.add(var8_8.zzzh());
        }
        if (var6_6.zzzv() == 0) {
            return var7_7;
        }
        var1_1 = this.zzzt();
        if (var1_1 == null) {
            this.zzdy("Failed to build batching endpoint url");
            return Collections.emptyList();
        }
        var2_4 = var4_5 != false ? this.zzb((URL)var1_1, var6_6.getPayload()) : this.zza((URL)var1_1, var6_6.getPayload());
        if (200 == var2_4) {
            this.zza("Batched upload completed. Hits batched", var6_6.zzzv());
            return var7_7;
        }
        this.zza("Network error uploading hits. status code", var2_4);
        if (this.zzwu().zzyz().contains(var2_4) == false) return Collections.emptyList();
        this.zzdx("Server instructed the client to stop batching");
        this.zzdyi.start();
        return Collections.emptyList();
    }

    @Override
    protected final void zzvf() {
        this.zza("Network initialized. User agent", this.zzczb);
    }

    public final boolean zzzs() {
        zzj.zzve();
        this.zzxf();
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getContext().getSystemService("connectivity");
        try {
            connectivityManager = connectivityManager.getActiveNetworkInfo();
        }
        catch (SecurityException securityException) {
            connectivityManager = null;
        }
        while (connectivityManager == null || !connectivityManager.isConnected()) {
            this.zzdu("No network connectivity");
            return false;
        }
        return true;
    }
}

