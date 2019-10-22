/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteException
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzm;
import com.google.android.gms.internal.zzapz;
import com.google.android.gms.internal.zzaqa;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzaqf;
import com.google.android.gms.internal.zzaqm;
import com.google.android.gms.internal.zzard;
import com.google.android.gms.internal.zzarl;
import com.google.android.gms.internal.zzarm;
import com.google.android.gms.internal.zzarq;
import com.google.android.gms.internal.zzarv;
import com.google.android.gms.internal.zzash;
import com.google.android.gms.internal.zzasl;
import java.io.Closeable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class zzaql
extends zzaqa
implements Closeable {
    private static final String zzdug = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", "hits2", "hit_id", "hit_time", "hit_url", "hit_string", "hit_app_id");
    private static final String zzduh = String.format("SELECT MAX(%s) FROM %s WHERE 1;", "hit_time", "hits2");
    private final zzaqm zzdui;
    private final zzash zzduj = new zzash(this.zzws());
    private final zzash zzduk = new zzash(this.zzws());

    zzaql(zzaqc zzaqc2) {
        super(zzaqc2);
        this.zzdui = new zzaqm(this, zzaqc2.getContext(), "google_analytics_v4.db");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final long zza(String var1_1, String[] var2_5, long var3_6) {
        block7: {
            var7_7 = this.getWritableDatabase();
            var5_8 = null;
            var6_10 = null;
            var2_5 = var7_7.rawQuery(var1_1, (String[])var2_5);
            if (!var2_5.moveToFirst()) break block7;
            var3_6 = var2_5.getLong(0);
            if (var2_5 == null) return var3_6;
            var2_5.close();
            return var3_6;
        }
        if (var2_5 == null) return 0L;
        var2_5.close();
        return 0L;
        catch (SQLiteException var5_9) {
            block8: {
                var2_5 = var6_10;
                var6_10 = var5_9;
                break block8;
                catch (Throwable var1_4) {
                    var5_8 = var2_5;
                    ** GOTO lbl-1000
                }
                catch (SQLiteException var6_11) {}
            }
            var5_8 = var2_5;
            try {
                this.zzd("Database error", var1_1, (Object)var6_10);
                var5_8 = var2_5;
                throw var6_10;
            }
            catch (Throwable var1_2) lbl-1000:
            // 2 sources
            {
                if (var5_8 == null) throw var1_3;
                var5_8.close();
                throw var1_3;
            }
        }
    }

    static /* synthetic */ zzash zza(zzaql zzaql2) {
        return zzaql2.zzduk;
    }

    private final long zzb(String string2, String[] sQLiteDatabase) {
        SQLiteDatabase sQLiteDatabase2;
        SQLiteDatabase sQLiteDatabase3;
        block9: {
            long l;
            block10: {
                sQLiteDatabase2 = null;
                sQLiteDatabase = null;
                sQLiteDatabase3 = this.getWritableDatabase();
                sQLiteDatabase = sQLiteDatabase3 = sQLiteDatabase3.rawQuery(string2, null);
                sQLiteDatabase2 = sQLiteDatabase3;
                if (!sQLiteDatabase3.moveToFirst()) break block9;
                sQLiteDatabase = sQLiteDatabase3;
                sQLiteDatabase2 = sQLiteDatabase3;
                l = sQLiteDatabase3.getLong(0);
                if (sQLiteDatabase3 == null) break block10;
                sQLiteDatabase3.close();
            }
            return l;
        }
        sQLiteDatabase = sQLiteDatabase3;
        sQLiteDatabase2 = sQLiteDatabase3;
        try {
            throw new SQLiteException("Database returned empty set");
        }
        catch (SQLiteException sQLiteException) {
            sQLiteDatabase2 = sQLiteDatabase;
            try {
                this.zzd("Database error", string2, (Object)sQLiteException);
                sQLiteDatabase2 = sQLiteDatabase;
            }
            catch (Throwable throwable) {
                if (sQLiteDatabase2 != null) {
                    sQLiteDatabase2.close();
                }
                throw throwable;
            }
            throw sQLiteException;
        }
    }

    static /* synthetic */ String zzb(zzaql zzaql2) {
        return zzaql.zzxw();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final Map<String, String> zzdz(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return new HashMap<String, String>(0);
        }
        try {
            if (string2.startsWith("?")) {
                do {
                    return zzm.zza(new URI(string2), "UTF-8");
                    break;
                } while (true);
            }
            if ((string2 = String.valueOf(string2)).length() != 0) {
                string2 = "?".concat(string2);
                return zzm.zza(new URI(string2), "UTF-8");
            }
            string2 = new String("?");
            return zzm.zza(new URI(string2), "UTF-8");
        }
        catch (URISyntaxException uRISyntaxException) {
            this.zze("Error parsing hit parameters", uRISyntaxException);
            return new HashMap<String, String>(0);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final Map<String, String> zzea(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return new HashMap<String, String>(0);
        }
        try {
            string2 = String.valueOf(string2);
            if (string2.length() != 0) {
                string2 = "?".concat(string2);
                do {
                    return zzm.zza(new URI(string2), "UTF-8");
                    break;
                } while (true);
            }
            string2 = new String("?");
            return zzm.zza(new URI(string2), "UTF-8");
        }
        catch (URISyntaxException uRISyntaxException) {
            this.zze("Error parsing property parameters", uRISyntaxException);
            return new HashMap<String, String>(0);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private final List<Long> zzn(long var1_1) {
        block14: {
            var5_2 = null;
            zzj.zzve();
            this.zzxf();
            if (var1_1 <= 0L) {
                return Collections.emptyList();
            }
            var4_3 = this.getWritableDatabase();
            var7_15 = new ArrayList<Long>();
            var6_16 = String.format("%s ASC", new Object[]{"hit_id"});
            var8_20 = Long.toString(var1_1);
            var4_5 = var5_2 = (var4_4 = var4_3.query("hits2", new String[]{"hit_id"}, null, null, null, null, var6_16, var8_20));
            try {
                if (var5_2.moveToFirst()) {
                    do {
                        var4_7 = var5_2;
                        var7_15.add(var5_2.getLong(0));
                        var4_8 = var5_2;
                    } while (var3_21 = var5_2.moveToNext());
                }
                if (var5_2 == null) break block14;
            }
            catch (SQLiteException var6_19) {
                ** continue;
            }
            var5_2.close();
        }
lbl23:
        // 3 sources
        do {
            return var7_15;
            break;
        } while (true);
        catch (SQLiteException var6_17) {
            var5_2 = null;
lbl27:
            // 2 sources
            do {
                var4_10 = var5_2;
                this.zzd("Error selecting hit ids", var6_16);
                if (var5_2 == null) ** GOTO lbl23
                var5_2.close();
                ** continue;
                break;
            } while (true);
        }
        catch (Throwable var4_11) lbl-1000:
        // 2 sources
        {
            do {
                if (var5_2 != null) {
                    var5_2.close();
                }
                throw var4_12;
                break;
            } while (true);
        }
        {
            catch (Throwable var6_18) {
                var5_2 = var4_13;
                var4_14 = var6_18;
                ** continue;
            }
        }
    }

    private final long zzxp() {
        zzj.zzve();
        this.zzxf();
        return this.zzb("SELECT COUNT(*) FROM hits2", null);
    }

    private static String zzxw() {
        return "google_analytics_v4.db";
    }

    static /* synthetic */ String zzxx() {
        return zzdug;
    }

    public final void beginTransaction() {
        this.zzxf();
        this.getWritableDatabase().beginTransaction();
    }

    @Override
    public final void close() {
        try {
            this.zzdui.close();
            return;
        }
        catch (SQLiteException sQLiteException) {
            this.zze("Sql error closing database", (Object)sQLiteException);
            return;
        }
        catch (IllegalStateException illegalStateException) {
            this.zze("Error closing database", illegalStateException);
            return;
        }
    }

    public final void endTransaction() {
        this.zzxf();
        this.getWritableDatabase().endTransaction();
    }

    final SQLiteDatabase getWritableDatabase() {
        try {
            SQLiteDatabase sQLiteDatabase = this.zzdui.getWritableDatabase();
            return sQLiteDatabase;
        }
        catch (SQLiteException sQLiteException) {
            this.zzd("Error opening database", (Object)sQLiteException);
            throw sQLiteException;
        }
    }

    final boolean isEmpty() {
        return this.zzxp() == 0L;
    }

    public final void setTransactionSuccessful() {
        this.zzxf();
        this.getWritableDatabase().setTransactionSuccessful();
    }

    public final long zza(long l, String string2, String string3) {
        zzbq.zzgm(string2);
        zzbq.zzgm(string3);
        this.zzxf();
        zzj.zzve();
        return this.zza("SELECT hits_count FROM properties WHERE app_uid=? AND cid=? AND tid=?", new String[]{String.valueOf(l), string2, string3}, 0L);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzc(zzarq zzarq2) {
        ContentValues contentValues2;
        Object object;
        zzbq.checkNotNull(zzarq2);
        zzj.zzve();
        this.zzxf();
        zzbq.checkNotNull(zzarq2);
        Object object2 = new Uri.Builder();
        for (ContentValues contentValues2 : zzarq2.zzjh().entrySet()) {
            String string2 = contentValues2.getKey();
            if ("ht".equals(string2) || "qt".equals(string2) || "AppUID".equals(string2)) continue;
            object2.appendQueryParameter(string2, contentValues2.getValue());
        }
        if ((object2 = object2.build().getEncodedQuery()) == null) {
            object2 = "";
        }
        if (((String)object2).length() > 8192) {
            this.zzwt().zza(zzarq2, "Hit length exceeds the maximum allowed size");
            return;
        }
        int n = zzarl.zzdwb.get();
        long l = this.zzxp();
        if (l > (long)(n - 1)) {
            object = this.zzn(l - (long)n + 1L);
            this.zzd("Store full, deleting hits to make room, count", object.size());
            this.zzs((List<Long>)object);
        }
        object = this.getWritableDatabase();
        contentValues2 = new ContentValues();
        contentValues2.put("hit_string", (String)object2);
        contentValues2.put("hit_time", Long.valueOf(zzarq2.zzzi()));
        contentValues2.put("hit_app_id", Integer.valueOf(zzarq2.zzzg()));
        object2 = zzarq2.zzzk() ? zzard.zzyw() : zzard.zzyx();
        contentValues2.put("hit_url", (String)object2);
        try {
            l = object.insert("hits2", null, contentValues2);
            if (l == -1L) {
                this.zzdy("Failed to insert a hit (got -1)");
                return;
            }
        }
        catch (SQLiteException sQLiteException) {
            this.zze("Error storing a hit", (Object)sQLiteException);
            return;
        }
        {
            this.zzb("Hit saved to database. db-id, hit", l, zzarq2);
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public final List<zzarq> zzo(long var1_1) {
        var6_2 = true;
        var8_3 = null;
        if (var1_1 >= 0L) lbl-1000:
        // 2 sources
        {
            do {
                block15: {
                    zzbq.checkArgument(var6_2);
                    zzj.zzve();
                    this.zzxf();
                    var9_11 = this.getWritableDatabase();
                    var7_13 = var8_3;
                    var10_14 = String.format("%s ASC", new Object[]{"hit_id"});
                    var7_13 = var8_3;
                    var11_15 = Long.toString(var1_1);
                    var7_13 = var8_3;
                    var7_13 = var8_4 = var9_11.query("hits2", new String[]{"hit_id", "hit_time", "hit_string", "hit_url", "hit_app_id"}, null, null, null, null, var10_14, var11_15);
                    var9_11 = new ArrayList<E>();
                    var7_13 = var8_4;
                    if (var8_4.moveToFirst()) {
                        do {
                            var7_13 = var8_4;
                            var1_1 = var8_4.getLong(0);
                            var7_13 = var8_4;
                            var4_17 = var8_4.getLong(1);
                            var7_13 = var8_4;
                            var10_14 = var8_4.getString(2);
                            var7_13 = var8_4;
                            var11_15 = var8_4.getString(3);
                            var7_13 = var8_4;
                            var3_16 = var8_4.getInt(4);
                            var7_13 = var8_4;
                            var9_11.add(new zzarq(this, this.zzdz(var10_14), var4_17, zzasl.zzel(var11_15), var1_1, var3_16));
                            var7_13 = var8_4;
                        } while (var6_2 = var8_4.moveToNext());
                    }
                    if (var8_4 == null) break block15;
                    var8_4.close();
                }
                return var9_11;
                break;
            } while (true);
        }
        var6_2 = false;
        ** while (true)
        {
            catch (SQLiteException var8_5) {
                var7_13 = null;
lbl45:
                // 2 sources
                do {
                    try {
                        this.zze("Error loading hits from the database", var8_6);
                        throw var8_6;
                    }
                    catch (Throwable var8_7) lbl-1000:
                    // 2 sources
                    {
                        do {
                            if (var7_13 != null) {
                                var7_13.close();
                            }
                            throw var8_8;
                            break;
                        } while (true);
                    }
                    break;
                } while (true);
            }
        }
        catch (Throwable var8_9) {
            ** continue;
        }
        {
            catch (SQLiteException var9_12) {
                var7_13 = var8_4;
                var8_10 = var9_12;
                ** continue;
            }
        }
    }

    public final void zzp(long l) {
        zzj.zzve();
        this.zzxf();
        ArrayList<Long> arrayList = new ArrayList<Long>(1);
        arrayList.add(l);
        this.zza("Deleting hit, id", l);
        this.zzs(arrayList);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final List<zzaqf> zzq(long var1_1) {
        this.zzxf();
        zzj.zzve();
        var7_2 = this.getWritableDatabase();
        var5_9 = var6_4 /* !! */  = null;
        try {
            block15: {
                var3_10 = zzarl.zzdwd.get();
                var5_9 = var6_4 /* !! */ ;
                var5_9 = var6_4 /* !! */  = var7_2.query("properties", new String[]{"cid", "tid", "adid", "hits_count", "params"}, "app_uid=?", new String[]{"0"}, null, null, null, String.valueOf(var3_10));
                try {
                    block16: {
                        var7_2 = new ArrayList<E>();
                        var5_9 = var6_4 /* !! */ ;
                        if (!var6_4 /* !! */ .moveToFirst()) break block16;
                        do {
                            var5_9 = var6_4 /* !! */ ;
                            var8_12 = var6_4 /* !! */ .getString(0);
                            var5_9 = var6_4 /* !! */ ;
                            var9_13 = var6_4 /* !! */ .getString(1);
                            var5_9 = var6_4 /* !! */ ;
                            var4_11 = var6_4 /* !! */ .getInt(2) != 0;
                            var5_9 = var6_4 /* !! */ ;
                            var1_1 = var6_4 /* !! */ .getInt(3);
                            var5_9 = var6_4 /* !! */ ;
                            var10_14 = this.zzea(var6_4 /* !! */ .getString(4));
                            var5_9 = var6_4 /* !! */ ;
                            if (TextUtils.isEmpty((CharSequence)var8_12)) ** GOTO lbl-1000
                            var5_9 = var6_4 /* !! */ ;
                            if (TextUtils.isEmpty((CharSequence)var9_13)) lbl-1000:
                            // 2 sources
                            {
                                var5_9 = var6_4 /* !! */ ;
                                this.zzc("Read property with empty client id or tracker id", var8_12, var9_13);
                            } else {
                                var5_9 = var6_4 /* !! */ ;
                                var7_2.add(new zzaqf(0L, var8_12, var9_13, var4_11, var1_1, var10_14));
                            }
                            var5_9 = var6_4 /* !! */ ;
                        } while (var6_4 /* !! */ .moveToNext());
                    }
                    var5_9 = var6_4 /* !! */ ;
                    if (var7_2.size() >= var3_10) {
                        var5_9 = var6_4 /* !! */ ;
                        this.zzdx("Sending hits to too many properties. Campaign report might be incorrect");
                    }
                    if (var6_4 /* !! */  == null) break block15;
                }
                catch (SQLiteException var7_3) {
                    block17: {
                        var5_9 = var6_4 /* !! */ ;
                        var6_4 /* !! */  = var7_3;
                        break block17;
                        catch (SQLiteException var6_8) {
                            var5_9 = null;
                        }
                    }
                    try {
                        this.zze("Error loading hits from the database", (Object)var6_4 /* !! */ );
                        throw var6_4 /* !! */ ;
                    }
                    catch (Throwable var6_5) lbl-1000:
                    // 2 sources
                    {
                        do {
                            if (var5_9 != null) {
                                var5_9.close();
                            }
                            throw var6_6;
                            break;
                        } while (true);
                    }
                }
                var6_4 /* !! */ .close();
            }
            return var7_2;
        }
        catch (Throwable var6_7) {
            ** continue;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzs(List<Long> list) {
        zzbq.checkNotNull(list);
        zzj.zzve();
        this.zzxf();
        if (list.isEmpty()) return;
        {
            Long l;
            int n;
            CharSequence charSequence = new StringBuilder("hit_id");
            ((StringBuilder)charSequence).append(" in (");
            for (n = 0; n < list.size(); ++n) {
                l = list.get(n);
                if (l == null || l == 0L) {
                    throw new SQLiteException("Invalid hit id");
                }
                if (n > 0) {
                    ((StringBuilder)charSequence).append(",");
                }
                ((StringBuilder)charSequence).append(l);
            }
            ((StringBuilder)charSequence).append(")");
            charSequence = ((StringBuilder)charSequence).toString();
            try {
                l = this.getWritableDatabase();
                this.zza("Deleting dispatched hits. count", list.size());
                n = l.delete("hits2", (String)charSequence, null);
                if (n == list.size()) return;
                {
                    this.zzb("Deleted fewer hits then expected", list.size(), n, charSequence);
                    return;
                }
            }
            catch (SQLiteException sQLiteException) {
                this.zze("Error deleting hits", (Object)sQLiteException);
                throw sQLiteException;
            }
        }
    }

    @Override
    protected final void zzvf() {
    }

    public final int zzxu() {
        zzj.zzve();
        this.zzxf();
        if (!this.zzduj.zzu(86400000L)) {
            return 0;
        }
        this.zzduj.start();
        this.zzdu("Deleting stale hits (if any)");
        int n = this.getWritableDatabase().delete("hits2", "hit_time < ?", new String[]{Long.toString(this.zzws().currentTimeMillis() - 2592000000L)});
        this.zza("Deleted stale hits, count", n);
        return n;
    }

    public final long zzxv() {
        zzj.zzve();
        this.zzxf();
        return this.zza(zzduh, null, 0L);
    }
}

