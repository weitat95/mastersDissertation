/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabaseLockedException
 *  android.database.sqlite.SQLiteException
 *  android.database.sqlite.SQLiteFullException
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.SystemClock
 */
package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteFullException;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzcgd;
import com.google.android.gms.internal.zzcgk;
import com.google.android.gms.internal.zzcgl;
import com.google.android.gms.internal.zzcgn;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcgu;
import com.google.android.gms.internal.zzcha;
import com.google.android.gms.internal.zzchh;
import com.google.android.gms.internal.zzchj;
import com.google.android.gms.internal.zzchk;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzcig;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjl;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzclf;
import com.google.android.gms.internal.zzcln;
import com.google.android.gms.internal.zzclq;
import java.util.List;

public final class zzchi
extends zzcjl {
    private final zzchj zzjbn = new zzchj(this, ((zzcjk)this).getContext(), "google_app_measurement_local.db");
    private boolean zzjbo;

    zzchi(zzcim zzcim2) {
        super(zzcim2);
    }

    private final SQLiteDatabase getWritableDatabase() {
        if (this.zzjbo) {
            return null;
        }
        SQLiteDatabase sQLiteDatabase = this.zzjbn.getWritableDatabase();
        if (sQLiteDatabase == null) {
            this.zzjbo = true;
            return null;
        }
        return sQLiteDatabase;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @TargetApi(value=11)
    private final boolean zzb(int var1_1, byte[] var2_2) {
        this.zzve();
        if (this.zzjbo) {
            return false;
        }
        var18_3 = new ContentValues();
        var18_3.put("type", Integer.valueOf(var1_1));
        var18_3.put("entry", (byte[])var2_2);
        var3_4 = 0;
        var1_1 = 5;
        do {
            block23: {
                block22: {
                    if (var3_4 >= 5) {
                        this.zzawy().zzazf().log("Failed to write entry to local database");
                        return false;
                    }
                    var13_15 = null;
                    var9_8 = null;
                    var11_12 = null;
                    var16_18 = null;
                    var17_19 = null;
                    var10_9 = var15_17 = null;
                    var12_13 = var16_18;
                    var2_2 = var17_19;
                    var14_16 = this.getWritableDatabase();
                    if (var14_16 != null) break block22;
                    var10_9 = var15_17;
                    var11_12 = var14_16;
                    var12_13 = var16_18;
                    var13_15 = var14_16;
                    var2_2 = var17_19;
                    var9_8 = var14_16;
                    this.zzjbo = true;
                    if (var14_16 == null) return false;
                    var14_16.close();
                    return false;
                }
                var10_9 = var15_17;
                var11_12 = var14_16;
                var12_13 = var16_18;
                var13_15 = var14_16;
                var2_2 = var17_19;
                var9_8 = var14_16;
                var14_16.beginTransaction();
                var7_7 = 0L;
                var10_9 = var15_17;
                var11_12 = var14_16;
                var12_13 = var16_18;
                var13_15 = var14_16;
                var2_2 = var17_19;
                var9_8 = var14_16;
                var15_17 = var14_16.rawQuery("select count(1) from messages", null);
                var5_6 = var7_7;
                if (var15_17 != null) {
                    var5_6 = var7_7;
                    var10_9 = var15_17;
                    var11_12 = var14_16;
                    var12_13 = var15_17;
                    var13_15 = var14_16;
                    var2_2 = var15_17;
                    var9_8 = var14_16;
                    if (var15_17.moveToFirst()) {
                        var10_9 = var15_17;
                        var11_12 = var14_16;
                        var12_13 = var15_17;
                        var13_15 = var14_16;
                        var2_2 = var15_17;
                        var9_8 = var14_16;
                        var5_6 = var15_17.getLong(0);
                    }
                }
                if (var5_6 < 100000L) ** GOTO lbl93
                var10_9 = var15_17;
                var11_12 = var14_16;
                var12_13 = var15_17;
                var13_15 = var14_16;
                var2_2 = var15_17;
                var9_8 = var14_16;
                this.zzawy().zzazd().log("Data loss, local db full");
                var5_6 = 100000L - var5_6 + 1L;
                var10_9 = var15_17;
                var11_12 = var14_16;
                var12_13 = var15_17;
                var13_15 = var14_16;
                var2_2 = var15_17;
                var9_8 = var14_16;
                try {
                    var7_7 = var14_16.delete("messages", "rowid in (select rowid from messages order by rowid asc limit ?)", new String[]{Long.toString(var5_6)});
                    if (var7_7 != var5_6) {
                        var10_9 = var15_17;
                        var11_12 = var14_16;
                        var12_13 = var15_17;
                        var13_15 = var14_16;
                        var2_2 = var15_17;
                        var9_8 = var14_16;
                        this.zzawy().zzazd().zzd("Different delete count than expected in local db. expected, received, difference", var5_6, var7_7, var5_6 - var7_7);
                    }
lbl93:
                    // 4 sources
                    var10_9 = var15_17;
                    var11_12 = var14_16;
                    var12_13 = var15_17;
                    var13_15 = var14_16;
                    var2_2 = var15_17;
                    var9_8 = var14_16;
                    var14_16.insertOrThrow("messages", null, var18_3);
                    var10_9 = var15_17;
                    var11_12 = var14_16;
                    var12_13 = var15_17;
                    var13_15 = var14_16;
                    var2_2 = var15_17;
                    var9_8 = var14_16;
                    var14_16.setTransactionSuccessful();
                    var10_9 = var15_17;
                    var11_12 = var14_16;
                    var12_13 = var15_17;
                    var13_15 = var14_16;
                    var2_2 = var15_17;
                    var9_8 = var14_16;
                    var14_16.endTransaction();
                    if (var15_17 == null) break block23;
                }
                catch (SQLiteFullException var12_14) {
                    block24: {
                        var2_2 = var10_9;
                        var9_8 = var11_12;
                        this.zzawy().zzazd().zzj("Error writing entry to local database", (Object)var12_14);
                        var2_2 = var10_9;
                        var9_8 = var11_12;
                        this.zzjbo = true;
                        if (var10_9 == null) break block24;
                        {
                            catch (Throwable var10_11) {
                                if (var2_2 != null) {
                                    var2_2.close();
                                }
                                if (var9_8 == null) throw var10_11;
                                var9_8.close();
                                throw var10_11;
                            }
                        }
                        var10_9.close();
                    }
                    var4_5 = var1_1;
                    if (var11_12 != null) {
                        var11_12.close();
                        var4_5 = var1_1;
                    }
                    ** GOTO lbl177
                    catch (SQLiteException var10_10) {
                        block25: {
                            var2_2 = var12_13;
                            var9_8 = var13_15;
                            if (Build.VERSION.SDK_INT >= 11) {
                                var2_2 = var12_13;
                                var9_8 = var13_15;
                                if (var10_10 instanceof SQLiteDatabaseLockedException) {
                                    var2_2 = var12_13;
                                    var9_8 = var13_15;
                                    SystemClock.sleep((long)var1_1);
                                    var1_1 += 20;
                                    break block25;
                                }
                            }
                            if (var13_15 == null) ** GOTO lbl164
                            var2_2 = var12_13;
                            var9_8 = var13_15;
                            if (var13_15.inTransaction()) {
                                var2_2 = var12_13;
                                var9_8 = var13_15;
                                var13_15.endTransaction();
                            }
lbl164:
                            // 4 sources
                            var2_2 = var12_13;
                            var9_8 = var13_15;
                            this.zzawy().zzazd().zzj("Error writing entry to local database", (Object)var10_10);
                            var2_2 = var12_13;
                            var9_8 = var13_15;
                            this.zzjbo = true;
                        }
                        if (var12_13 != null) {
                            var12_13.close();
                        }
                        var4_5 = var1_1;
                        if (var13_15 != null) {
                            var13_15.close();
                            var4_5 = var1_1;
                        }
lbl177:
                        // 4 sources
                        ++var3_4;
                        var1_1 = var4_5;
                        continue;
                    }
                }
                var15_17.close();
            }
            if (var14_16 == null) return true;
            var14_16.close();
            return true;
            break;
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void resetAnalyticsData() {
        int n;
        ((zzcjk)this).zzve();
        try {
            n = this.getWritableDatabase().delete("messages", null, null) + 0;
            if (n <= 0) return;
        }
        catch (SQLiteException sQLiteException) {
            ((zzcjk)this).zzawy().zzazd().zzj("Error resetting local analytics data. error", (Object)sQLiteException);
            return;
        }
        ((zzcjk)this).zzawy().zzazj().zzj("Reset local analytics data. records", n);
    }

    public final boolean zza(zzcha arrby) {
        Parcel parcel = Parcel.obtain();
        arrby.writeToParcel(parcel, 0);
        arrby = parcel.marshall();
        parcel.recycle();
        if (arrby.length > 131072) {
            ((zzcjk)this).zzawy().zzazf().log("Event is too long for local database. Sending event directly to service");
            return false;
        }
        return this.zzb(0, arrby);
    }

    public final boolean zza(zzcln arrby) {
        Parcel parcel = Parcel.obtain();
        arrby.writeToParcel(parcel, 0);
        arrby = parcel.marshall();
        parcel.recycle();
        if (arrby.length > 131072) {
            ((zzcjk)this).zzawy().zzazf().log("User property too long for local database. Sending directly to service");
            return false;
        }
        return this.zzb(1, arrby);
    }

    @Override
    protected final boolean zzaxz() {
        return false;
    }

    public final boolean zzc(zzcgl arrby) {
        ((zzcjk)this).zzawu();
        arrby = zzclq.zza((Parcelable)arrby);
        if (arrby.length > 131072) {
            ((zzcjk)this).zzawy().zzazf().log("Conditional user property too long for local database. Sending directly to service");
            return false;
        }
        return this.zzb(2, arrby);
    }

    /*
     * Exception decompiling
     */
    @TargetApi(value=11)
    public final List<zzbfm> zzeb(int var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [13[TRYBLOCK]], but top level block is 26[CATCHBLOCK]
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
}

