/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.sqlite.SQLiteException
 *  android.text.TextUtils
 *  android.util.Pair
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.analytics.zza;
import com.google.android.gms.analytics.zzg;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzapd;
import com.google.android.gms.internal.zzape;
import com.google.android.gms.internal.zzaph;
import com.google.android.gms.internal.zzapm;
import com.google.android.gms.internal.zzapz;
import com.google.android.gms.internal.zzaqa;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzaqe;
import com.google.android.gms.internal.zzaqf;
import com.google.android.gms.internal.zzaqg;
import com.google.android.gms.internal.zzaql;
import com.google.android.gms.internal.zzaqp;
import com.google.android.gms.internal.zzaqq;
import com.google.android.gms.internal.zzaqr;
import com.google.android.gms.internal.zzaqs;
import com.google.android.gms.internal.zzaqx;
import com.google.android.gms.internal.zzard;
import com.google.android.gms.internal.zzarf;
import com.google.android.gms.internal.zzari;
import com.google.android.gms.internal.zzarj;
import com.google.android.gms.internal.zzarl;
import com.google.android.gms.internal.zzarm;
import com.google.android.gms.internal.zzarq;
import com.google.android.gms.internal.zzarv;
import com.google.android.gms.internal.zzarw;
import com.google.android.gms.internal.zzarx;
import com.google.android.gms.internal.zzarz;
import com.google.android.gms.internal.zzasb;
import com.google.android.gms.internal.zzasc;
import com.google.android.gms.internal.zzasd;
import com.google.android.gms.internal.zzash;
import com.google.android.gms.internal.zzasl;
import com.google.android.gms.internal.zzasm;
import com.google.android.gms.internal.zzbhf;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class zzaqo
extends zzaqa {
    private boolean mStarted;
    private final zzaql zzdum;
    private final zzarx zzdun;
    private final zzarw zzduo;
    private final zzaqg zzdup;
    private long zzduq;
    private final zzarf zzdur;
    private final zzarf zzdus;
    private final zzash zzdut;
    private long zzduu;
    private boolean zzduv;

    protected zzaqo(zzaqc zzaqc2, zzaqe zzaqe2) {
        super(zzaqc2);
        zzbq.checkNotNull(zzaqe2);
        this.zzduq = Long.MIN_VALUE;
        this.zzduo = new zzarw(zzaqc2);
        this.zzdum = new zzaql(zzaqc2);
        this.zzdun = new zzarx(zzaqc2);
        this.zzdup = new zzaqg(zzaqc2);
        this.zzdut = new zzash(this.zzws());
        this.zzdur = new zzaqp(this, zzaqc2);
        this.zzdus = new zzaqq(this, zzaqc2);
    }

    private final void zza(zzaqf zzaqf2, zzape zzape2) {
        zzbq.checkNotNull(zzaqf2);
        zzbq.checkNotNull(zzape2);
        Object object = new zza(this.zzwr());
        ((zza)object).zzde(zzaqf2.zzxn());
        ((zza)object).enableAdvertisingIdCollection(zzaqf2.zzxo());
        object = ((zzi)object).zzun();
        zzapm zzapm2 = ((zzg)object).zzb(zzapm.class);
        zzapm2.zzdp("data");
        zzapm2.zzaj(true);
        ((zzg)object).zza(zzape2);
        zzaph zzaph2 = ((zzg)object).zzb(zzaph.class);
        zzapd zzapd2 = ((zzg)object).zzb(zzapd.class);
        for (Map.Entry<String, String> entry : zzaqf2.zzjh().entrySet()) {
            String string2 = entry.getKey();
            String object2 = entry.getValue();
            if ("an".equals(string2)) {
                zzapd2.setAppName(object2);
                continue;
            }
            if ("av".equals(string2)) {
                zzapd2.setAppVersion(object2);
                continue;
            }
            if ("aid".equals(string2)) {
                zzapd2.setAppId(object2);
                continue;
            }
            if ("aiid".equals(string2)) {
                zzapd2.setAppInstallerId(object2);
                continue;
            }
            if ("uid".equals(string2)) {
                zzapm2.setUserId(object2);
                continue;
            }
            zzaph2.set(string2, object2);
        }
        this.zzb("Sending installation campaign to", zzaqf2.zzxn(), zzape2);
        ((zzg)object).zzl(this.zzxa().zzzw());
        ((zzg)object).zzuv();
    }

    static /* synthetic */ void zza(zzaqo zzaqo2) {
        zzaqo2.zzya();
    }

    static /* synthetic */ void zzb(zzaqo zzaqo2) {
        zzaqo2.zzyb();
    }

    private final boolean zzeb(String string2) {
        return zzbhf.zzdb(this.getContext()).checkCallingOrSelfPermission(string2) == 0;
    }

    private final long zzxv() {
        zzj.zzve();
        this.zzxf();
        try {
            long l = this.zzdum.zzxv();
            return l;
        }
        catch (SQLiteException sQLiteException) {
            this.zze("Failed to get min/max hit times from local store", (Object)sQLiteException);
            return 0L;
        }
    }

    private final void zzya() {
        this.zzb(new zzaqs(this));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzyb() {
        try {
            this.zzdum.zzxu();
            this.zzyf();
        }
        catch (SQLiteException sQLiteException) {
            this.zzd("Failed to delete stale hits", (Object)sQLiteException);
        }
        this.zzdus.zzs(86400000L);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzyc() {
        block3: {
            block2: {
                long l;
                if (this.zzduv || !zzard.zzyq() || this.zzdup.isConnected() || !this.zzdut.zzu(l = zzarl.zzdxk.get().longValue())) break block2;
                this.zzdut.start();
                this.zzdu("Connecting to service");
                if (this.zzdup.connect()) break block3;
            }
            return;
        }
        this.zzdu("Connected to service");
        this.zzdut.clear();
        this.onServiceConnected();
    }

    /*
     * Exception decompiling
     */
    private final boolean zzyd() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[TRYBLOCK], 0[TRYBLOCK]], but top level block is 20[CATCHBLOCK]
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

    /*
     * Enabled aggressive block sorting
     */
    private final void zzyg() {
        long l;
        zzari zzari2 = this.zzwy();
        if (!zzari2.zzze() || zzari2.zzdx() || (l = this.zzxv()) == 0L || Math.abs(this.zzws().currentTimeMillis() - l) > zzarl.zzdwj.get()) {
            return;
        }
        this.zza("Dispatch alarm scheduled (ms)", zzard.zzyt());
        zzari2.schedule();
    }

    private final void zzyh() {
        if (this.zzdur.zzdx()) {
            this.zzdu("All hits dispatched or no network/service. Going to power save mode");
        }
        this.zzdur.cancel();
        zzari zzari2 = this.zzwy();
        if (zzari2.zzdx()) {
            zzari2.cancel();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final long zzyi() {
        if (this.zzduq != Long.MIN_VALUE) {
            return this.zzduq;
        }
        long l = zzarl.zzdwe.get();
        zzasm zzasm2 = this.zzwz();
        zzasm2.zzxf();
        if (!zzasm2.zzdzk) return l;
        zzasm2 = this.zzwz();
        zzasm2.zzxf();
        return (long)zzasm2.zzdxr * 1000L;
    }

    private final void zzyj() {
        this.zzxf();
        zzj.zzve();
        this.zzduv = true;
        this.zzdup.disconnect();
        this.zzyf();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected final void onServiceConnected() {
        zzj.zzve();
        zzj.zzve();
        this.zzxf();
        if (!zzard.zzyq()) {
            this.zzdx("Service client disabled. Can't dispatch local hits to device AnalyticsService");
        }
        if (!this.zzdup.isConnected()) {
            this.zzdu("Service not connected");
            return;
        }
        if (this.zzdum.isEmpty() != false) return;
        this.zzdu("Dispatching local hits to device AnalyticsService");
        do {
            try {
                var1_1 = this.zzdum.zzo(zzard.zzyu());
                if (var1_1.isEmpty()) {
                    this.zzyf();
                    return;
                }
                continue;
            }
            catch (SQLiteException var1_2) {
                this.zze("Failed to read hits from store", (Object)var1_2);
                this.zzyh();
                return;
            }
            break;
        } while (true);
lbl-1000:
        // 1 sources
        {
            var1_1.remove(var2_4);
            this.zzdum.zzp(var2_4.zzzh());
            if (var1_1.isEmpty()) ** continue;
            ** while (this.zzdup.zzb((zzarq)(var2_4 = var1_1.get((int)0))))
        }
lbl28:
        // 1 sources
        this.zzyf();
        return;
        catch (SQLiteException var1_3) {
            this.zze("Failed to remove hit that was send for delivery", (Object)var1_3);
            this.zzyh();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    final void start() {
        this.zzxf();
        boolean bl = !this.mStarted;
        zzbq.zza(bl, "Analytics backend already started");
        this.mStarted = true;
        this.zzwv().zzc(new zzaqr(this));
    }

    /*
     * Exception decompiling
     */
    public final long zza(zzaqf var1_1, boolean var2_7) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zza(zzarq zzarq2) {
        Object object;
        zzbq.checkNotNull(zzarq2);
        zzj.zzve();
        this.zzxf();
        if (this.zzduv) {
            this.zzdv("Hit delivery not possible. Missing network permissions. See http://goo.gl/8Rd3yj for instructions");
        } else {
            this.zza("Delivering hit", zzarq2);
        }
        if (!TextUtils.isEmpty((CharSequence)zzarq2.zzzm())) {
            object = zzarq2;
        } else {
            Object object2 = this.zzxa().zzaab().zzaad();
            object = zzarq2;
            if (object2 != null) {
                object = (Long)((Pair)object2).second;
                object2 = (String)((Pair)object2).first;
                object = String.valueOf(object);
                object = new StringBuilder(String.valueOf(object).length() + 1 + String.valueOf(object2).length()).append((String)object).append(":").append((String)object2).toString();
                object2 = new HashMap<String, String>(zzarq2.zzjh());
                object2.put((String)"_m", (Object)object);
                object = new zzarq(this, (Map<String, String>)object2, zzarq2.zzzi(), zzarq2.zzzk(), zzarq2.zzzh(), zzarq2.zzzg(), zzarq2.zzzj());
            }
        }
        this.zzyc();
        if (this.zzdup.zzb((zzarq)object)) {
            this.zzdv("Hit sent to the device AnalyticsService for delivery");
            return;
        }
        try {
            this.zzdum.zzc((zzarq)object);
            this.zzyf();
            return;
        }
        catch (SQLiteException sQLiteException) {
            this.zze("Delivery failed to save hit to a database", (Object)sQLiteException);
            this.zzwt().zza((zzarq)object, "deliver: failed to insert hit to database");
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void zzb(zzaqf zzaqf2) {
        Object object;
        zzj.zzve();
        this.zzb("Sending first hit to property", zzaqf2.zzxn());
        if (this.zzxa().zzzx().zzu(zzard.zzza()) || TextUtils.isEmpty((CharSequence)(object = this.zzxa().zzaaa()))) {
            return;
        }
        object = zzasl.zza(this.zzwt(), (String)object);
        this.zzb("Found relevant installation campaign", object);
        this.zza(zzaqf2, (zzape)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzb(zzarj zzarj2) {
        long l = this.zzduu;
        zzj.zzve();
        this.zzxf();
        long l2 = -1L;
        long l3 = this.zzxa().zzzy();
        if (l3 != 0L) {
            l2 = Math.abs(this.zzws().currentTimeMillis() - l3);
        }
        this.zzb("Dispatching local hits. Elapsed time since last dispatch (ms)", l2);
        this.zzyc();
        try {
            this.zzyd();
            this.zzxa().zzzz();
            this.zzyf();
            if (zzarj2 != null) {
                zzarj2.zzd(null);
            }
            if (this.zzduu == l) return;
            {
                this.zzduo.zzzr();
                return;
            }
        }
        catch (Throwable throwable) {
            this.zze("Local dispatch failed", throwable);
            this.zzxa().zzzz();
            this.zzyf();
            if (zzarj2 == null) return;
            zzarj2.zzd(throwable);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzec(String iterator) {
        zzbq.zzgm(iterator);
        zzj.zzve();
        zzape zzape2 = zzasl.zza(this.zzwt(), iterator);
        if (zzape2 == null) {
            this.zzd("Parsing failed. Ignoring invalid campaign data", iterator);
            return;
        } else {
            String string2 = this.zzxa().zzaaa();
            if (((String)((Object)iterator)).equals(string2)) {
                this.zzdx("Ignoring duplicate install campaign");
                return;
            }
            if (!TextUtils.isEmpty((CharSequence)string2)) {
                this.zzd("Ignoring multiple install campaigns. original, new", string2, iterator);
                return;
            }
            this.zzxa().zzef((String)((Object)iterator));
            if (this.zzxa().zzzx().zzu(zzard.zzza())) {
                this.zzd("Campaign received too late, ignoring", zzape2);
                return;
            }
            this.zzb("Received installation campaign", zzape2);
            iterator = this.zzdum.zzq(0L).iterator();
            while (iterator.hasNext()) {
                this.zza(iterator.next(), zzape2);
            }
        }
    }

    @Override
    protected final void zzvf() {
        this.zzdum.initialize();
        this.zzdun.initialize();
        this.zzdup.initialize();
    }

    final void zzwq() {
        zzj.zzve();
        this.zzduu = this.zzws().currentTimeMillis();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void zzxz() {
        this.zzxf();
        zzj.zzve();
        Context context = this.zzwr().getContext();
        if (!zzasc.zzbk(context)) {
            this.zzdx("AnalyticsReceiver is not registered or is disabled. Register the receiver for reliable dispatching on non-Google Play devices. See http://goo.gl/8Rd3yj for instructions.");
        } else if (!zzasd.zzbo(context)) {
            this.zzdy("AnalyticsService is not registered or is disabled. Analytics service at risk of not starting. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!CampaignTrackingReceiver.zzbk(context)) {
            this.zzdx("CampaignTrackingReceiver is not registered, not exported or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
        }
        this.zzxa().zzzw();
        if (!this.zzeb("android.permission.ACCESS_NETWORK_STATE")) {
            this.zzdy("Missing required android.permission.ACCESS_NETWORK_STATE. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            this.zzyj();
        }
        if (!this.zzeb("android.permission.INTERNET")) {
            this.zzdy("Missing required android.permission.INTERNET. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            this.zzyj();
        }
        if (zzasd.zzbo(this.getContext())) {
            this.zzdu("AnalyticsService registered in the app manifest and enabled");
        } else {
            this.zzdx("AnalyticsService not registered in the app manifest. Hits might not be delivered reliably. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!this.zzduv && !this.zzdum.isEmpty()) {
            this.zzyc();
        }
        this.zzyf();
    }

    public final void zzye() {
        zzj.zzve();
        this.zzxf();
        this.zzdv("Sync dispatching local hits");
        long l = this.zzduu;
        this.zzyc();
        try {
            this.zzyd();
            this.zzxa().zzzz();
            this.zzyf();
            if (this.zzduu != l) {
                this.zzduo.zzzr();
            }
            return;
        }
        catch (Throwable throwable) {
            this.zze("Sync local dispatch failed", throwable);
            this.zzyf();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzyf() {
        boolean bl;
        zzj.zzve();
        this.zzxf();
        boolean bl2 = !this.zzduv && this.zzyi() > 0L;
        if (!bl2) {
            this.zzduo.unregister();
            this.zzyh();
            return;
        }
        if (this.zzdum.isEmpty()) {
            this.zzduo.unregister();
            this.zzyh();
            return;
        }
        if (!zzarl.zzdxf.get().booleanValue()) {
            this.zzduo.zzzp();
            bl = this.zzduo.isConnected();
        } else {
            bl = true;
        }
        if (!bl) {
            this.zzyh();
            this.zzyg();
            return;
        }
        this.zzyg();
        long l = this.zzyi();
        long l2 = this.zzxa().zzzy();
        if (l2 != 0L) {
            l2 = l - Math.abs(this.zzws().currentTimeMillis() - l2);
            if (l2 <= 0L) {
                l2 = Math.min(zzard.zzys(), l);
            }
        } else {
            l2 = Math.min(zzard.zzys(), l);
        }
        this.zza("Dispatch scheduled (ms)", l2);
        if (this.zzdur.zzdx()) {
            l2 = Math.max(1L, l2 + this.zzdur.zzzb());
            this.zzdur.zzt(l2);
            return;
        }
        this.zzdur.zzs(l2);
    }
}

