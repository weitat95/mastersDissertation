/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteException
 *  android.text.TextUtils
 */
package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.tagmanager.zzbe;
import com.google.android.gms.tagmanager.zzbx;
import com.google.android.gms.tagmanager.zzcc;
import com.google.android.gms.tagmanager.zzcd;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzed;
import com.google.android.gms.tagmanager.zzee;
import com.google.android.gms.tagmanager.zzfn;
import com.google.android.gms.tagmanager.zzfo;
import com.google.android.gms.tagmanager.zzfv;
import com.google.android.gms.tagmanager.zzfx;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class zzec
implements zzcc {
    private static final String zzdug = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL,'%s' INTEGER NOT NULL);", "gtm_hits", "hit_id", "hit_time", "hit_url", "hit_first_send_time");
    private final Context mContext;
    private zzd zzata;
    private final zzee zzkhb;
    private volatile zzbe zzkhc;
    private final zzcd zzkhd;
    private final String zzkhe;
    private long zzkhf;
    private final int zzkhg;

    zzec(zzcd zzcd2, Context context) {
        this(zzcd2, context, "gtm_urls.db", 2000);
    }

    private zzec(zzcd zzcd2, Context context, String string2, int n) {
        this.mContext = context.getApplicationContext();
        this.zzkhe = string2;
        this.zzkhd = zzcd2;
        this.zzata = zzh.zzamg();
        this.zzkhb = new zzee(this, this.mContext, this.zzkhe);
        this.zzkhc = new zzfv(this.mContext, new zzed(this));
        this.zzkhf = 0L;
        this.zzkhg = 2000;
    }

    static /* synthetic */ zzd zza(zzec zzec2) {
        return zzec2.zzata;
    }

    static /* synthetic */ void zza(zzec zzec2, long l) {
        zzec2.zzp(l);
    }

    static /* synthetic */ void zza(zzec zzec2, long l, long l2) {
        zzec2.zzh(l, l2);
    }

    static /* synthetic */ String zzb(zzec zzec2) {
        return zzec2.zzkhe;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final int zzbfl() {
        int n;
        SQLiteDatabase sQLiteDatabase;
        int n2;
        block6: {
            long l;
            SQLiteDatabase sQLiteDatabase2 = null;
            SQLiteDatabase sQLiteDatabase3 = null;
            n = 0;
            n2 = 0;
            sQLiteDatabase = this.zzlk("Error opening database for getNumStoredHits.");
            if (sQLiteDatabase == null) return n2;
            try {
                sQLiteDatabase3 = sQLiteDatabase = sQLiteDatabase.rawQuery("SELECT COUNT(*) from gtm_hits", null);
                sQLiteDatabase2 = sQLiteDatabase;
                if (!sQLiteDatabase.moveToFirst()) break block6;
                sQLiteDatabase3 = sQLiteDatabase;
                sQLiteDatabase2 = sQLiteDatabase;
                l = sQLiteDatabase.getLong(0);
            }
            catch (SQLiteException sQLiteException) {
                block7: {
                    sQLiteDatabase2 = sQLiteDatabase3;
                    try {
                        zzdj.zzcu("Error getting numStoredHits");
                        if (sQLiteDatabase3 != null) break block7;
                    }
                    catch (Throwable throwable) {
                        if (sQLiteDatabase2 == null) throw throwable;
                        {
                            sQLiteDatabase2.close();
                        }
                        throw throwable;
                    }
                    return n2;
                }
                sQLiteDatabase3.close();
                return 0;
            }
            n = (int)l;
        }
        n2 = n;
        if (sQLiteDatabase == null) return n2;
        {
            sQLiteDatabase.close();
            return n;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final int zzbfm() {
        var4_1 = null;
        var3_3 = this.zzlk("Error opening database for getNumStoredHits.");
        if (var3_3 == null) {
            return 0;
        }
        var3_4 = var3_3.query("gtm_hits", new String[]{"hit_id", "hit_first_send_time"}, "hit_first_send_time=0", null, null, null, null);
        var1_13 = var2_12 = var3_4.getCount();
        if (var3_4 == null) return var1_13;
        var3_4.close();
        return var2_12;
        catch (SQLiteException var3_5) {
            block9: {
                var3_6 = null;
                break block9;
                catch (Throwable var3_8) {}
                ** GOTO lbl-1000
                catch (Throwable var5_14) {
                    var4_1 = var3_4;
                    var3_10 = var5_14;
                    ** GOTO lbl-1000
                }
                catch (SQLiteException var4_2) {}
            }
            try {
                zzdj.zzcu("Error getting num untried hits");
                if (var3_7 == null) return 0;
            }
            catch (Throwable var5_15) {
                var4_1 = var3_7;
                var3_11 = var5_15;
            }
            var3_7.close();
            return 0;
        }
lbl-1000:
        // 3 sources
        {
            if (var4_1 == null) throw var3_9;
            var4_1.close();
            throw var3_9;
        }
    }

    static /* synthetic */ String zzbfn() {
        return zzdug;
    }

    static /* synthetic */ Context zzc(zzec zzec2) {
        return zzec2.mContext;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zze(String[] object) {
        boolean bl;
        block3: {
            SQLiteDatabase sQLiteDatabase;
            bl = true;
            if (object == null) return;
            if (((Object)object).length == 0 || (sQLiteDatabase = this.zzlk("Error opening database for deleteHits.")) == null) {
                return;
            }
            String string2 = String.format("HIT_ID in (%s)", TextUtils.join((CharSequence)",", Collections.nCopies(((Object)object).length, "?")));
            try {
                sQLiteDatabase.delete("gtm_hits", string2, (String[])object);
                object = this.zzkhd;
                if (this.zzbfl() == 0) break block3;
            }
            catch (SQLiteException sQLiteException) {
                zzdj.zzcu("Error deleting hits");
                return;
            }
            bl = false;
        }
        object.zzbu(bl);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final List<String> zzep(int var1_1) {
        var6_2 = new ArrayList<String>();
        if (var1_1 <= 0) {
            zzdj.zzcu("Invalid maxHits specified. Skipping");
            return var6_2;
        }
        var3_3 = this.zzlk("Error opening database for peekHitIds.");
        if (var3_3 == null) {
            return var6_2;
        }
        var4_18 = String.format("%s ASC", new Object[]{"hit_id"});
        var5_19 = Integer.toString(var1_1);
        var3_4 = var4_18 = var3_3.query("gtm_hits", new String[]{"hit_id"}, null, null, null, null, var4_18, var5_19);
        try {
            if (var4_18.moveToFirst()) {
                do {
                    var3_6 = var4_18;
                    var6_2.add(String.valueOf(var4_18.getLong(0)));
                    var3_7 = var4_18;
                } while (var2_23 = var4_18.moveToNext());
            }
            if (var4_18 == null) return var6_2;
        }
        catch (SQLiteException var5_22) {
            ** continue;
        }
        var4_18.close();
        return var6_2;
        catch (SQLiteException var5_20) {
            block15: {
                var4_18 = null;
                break block15;
                catch (Throwable var3_17) {
                    var4_18 = null;
                    ** GOTO lbl48
                }
            }
lbl30:
            // 2 sources
            do {
                var3_9 = var4_18;
                try {
                    var5_19 = String.valueOf(var5_19.getMessage());
                    var3_10 = var4_18;
                    if (var5_19.length() != 0) {
                        var3_11 = var4_18;
                        var5_19 = "Error in peekHits fetching hitIds: ".concat(var5_19);
                    } else {
                        var3_13 = var4_18;
                        var5_19 = new String("Error in peekHits fetching hitIds: ");
                    }
                    var3_12 = var4_18;
                    zzdj.zzcu(var5_19);
                    if (var4_18 == null) return var6_2;
                }
                catch (Throwable var5_21) {
                    var4_18 = var3_14;
                    var3_15 = var5_21;
lbl48:
                    // 2 sources
                    if (var4_18 == null) throw var3_16;
                    var4_18.close();
                    throw var3_16;
                }
                var4_18.close();
                return var6_2;
                break;
            } while (true);
        }
    }

    /*
     * Exception decompiling
     */
    private final List<zzbx> zzeq(int var1_1) {
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

    private final void zzh(long l, long l2) {
        SQLiteDatabase sQLiteDatabase = this.zzlk("Error opening database for getNumStoredHits.");
        if (sQLiteDatabase == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_first_send_time", Long.valueOf(l2));
        try {
            sQLiteDatabase.update("gtm_hits", contentValues, "hit_id=?", new String[]{String.valueOf(l)});
            return;
        }
        catch (SQLiteException sQLiteException) {
            zzdj.zzcu(new StringBuilder(69).append("Error setting HIT_FIRST_DISPATCH_TIME for hitId: ").append(l).toString());
            this.zzp(l);
            return;
        }
    }

    private final SQLiteDatabase zzlk(String string2) {
        try {
            SQLiteDatabase sQLiteDatabase = this.zzkhb.getWritableDatabase();
            return sQLiteDatabase;
        }
        catch (SQLiteException sQLiteException) {
            zzdj.zzcu(string2);
            return null;
        }
    }

    private final void zzp(long l) {
        this.zze(new String[]{String.valueOf(l)});
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void dispatch() {
        block5: {
            block4: {
                zzdj.v("GTM Dispatch running...");
                if (!this.zzkhc.zzbeq()) break block4;
                List<zzbx> list = this.zzeq(40);
                if (list.isEmpty()) {
                    zzdj.v("...nothing to dispatch");
                    this.zzkhd.zzbu(true);
                    return;
                }
                this.zzkhc.zzal(list);
                if (this.zzbfm() > 0) break block5;
            }
            return;
        }
        ((zzfn)zzfo.zzbgg()).dispatch();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final void zzb(long l, String string2) {
        int n;
        Object object;
        boolean bl = true;
        long l2 = this.zzata.currentTimeMillis();
        if (l2 > this.zzkhf + 86400000L) {
            this.zzkhf = l2;
            object = this.zzlk("Error opening database for deleteStaleHits.");
            if (object != null) {
                object.delete("gtm_hits", "HIT_TIME < ?", new String[]{Long.toString(this.zzata.currentTimeMillis() - 2592000000L)});
                object = this.zzkhd;
                if (this.zzbfl() != 0) {
                    bl = false;
                }
                object.zzbu(bl);
            }
        }
        if ((n = this.zzbfl() - this.zzkhg + 1) > 0) {
            object = this.zzep(n);
            n = object.size();
            zzdj.v(new StringBuilder(51).append("Store full, deleting ").append(n).append(" hits to make room.").toString());
            this.zze(object.toArray(new String[0]));
        }
        if ((object = this.zzlk("Error opening database for putHit")) == null) return;
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_time", Long.valueOf(l));
        contentValues.put("hit_url", string2);
        contentValues.put("hit_first_send_time", Integer.valueOf(0));
        try {
            object.insert("gtm_hits", null, contentValues);
            this.zzkhd.zzbu(false);
            return;
        }
        catch (SQLiteException sQLiteException) {
            zzdj.zzcu("Error storing hit");
            return;
        }
    }
}

