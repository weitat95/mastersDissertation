/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteException
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.Pair
 */
package com.google.android.gms.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzcgh;
import com.google.android.gms.internal.zzcgl;
import com.google.android.gms.internal.zzcgn;
import com.google.android.gms.internal.zzcgp;
import com.google.android.gms.internal.zzcgr;
import com.google.android.gms.internal.zzcgv;
import com.google.android.gms.internal.zzcgw;
import com.google.android.gms.internal.zzcgx;
import com.google.android.gms.internal.zzcha;
import com.google.android.gms.internal.zzchc;
import com.google.android.gms.internal.zzchd;
import com.google.android.gms.internal.zzchk;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzcia;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjl;
import com.google.android.gms.internal.zzclk;
import com.google.android.gms.internal.zzcln;
import com.google.android.gms.internal.zzclp;
import com.google.android.gms.internal.zzclq;
import com.google.android.gms.internal.zzclr;
import com.google.android.gms.internal.zzcls;
import com.google.android.gms.internal.zzclv;
import com.google.android.gms.internal.zzcmb;
import com.google.android.gms.internal.zzcmc;
import com.google.android.gms.internal.zzcme;
import com.google.android.gms.internal.zzcmf;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjs;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class zzcgo
extends zzcjl {
    private static final String[] zziyp = new String[]{"last_bundled_timestamp", "ALTER TABLE events ADD COLUMN last_bundled_timestamp INTEGER;", "last_sampled_complex_event_id", "ALTER TABLE events ADD COLUMN last_sampled_complex_event_id INTEGER;", "last_sampling_rate", "ALTER TABLE events ADD COLUMN last_sampling_rate INTEGER;", "last_exempt_from_sampling", "ALTER TABLE events ADD COLUMN last_exempt_from_sampling INTEGER;"};
    private static final String[] zziyq = new String[]{"origin", "ALTER TABLE user_attributes ADD COLUMN origin TEXT;"};
    private static final String[] zziyr = new String[]{"app_version", "ALTER TABLE apps ADD COLUMN app_version TEXT;", "app_store", "ALTER TABLE apps ADD COLUMN app_store TEXT;", "gmp_version", "ALTER TABLE apps ADD COLUMN gmp_version INTEGER;", "dev_cert_hash", "ALTER TABLE apps ADD COLUMN dev_cert_hash INTEGER;", "measurement_enabled", "ALTER TABLE apps ADD COLUMN measurement_enabled INTEGER;", "last_bundle_start_timestamp", "ALTER TABLE apps ADD COLUMN last_bundle_start_timestamp INTEGER;", "day", "ALTER TABLE apps ADD COLUMN day INTEGER;", "daily_public_events_count", "ALTER TABLE apps ADD COLUMN daily_public_events_count INTEGER;", "daily_events_count", "ALTER TABLE apps ADD COLUMN daily_events_count INTEGER;", "daily_conversions_count", "ALTER TABLE apps ADD COLUMN daily_conversions_count INTEGER;", "remote_config", "ALTER TABLE apps ADD COLUMN remote_config BLOB;", "config_fetched_time", "ALTER TABLE apps ADD COLUMN config_fetched_time INTEGER;", "failed_config_fetch_time", "ALTER TABLE apps ADD COLUMN failed_config_fetch_time INTEGER;", "app_version_int", "ALTER TABLE apps ADD COLUMN app_version_int INTEGER;", "firebase_instance_id", "ALTER TABLE apps ADD COLUMN firebase_instance_id TEXT;", "daily_error_events_count", "ALTER TABLE apps ADD COLUMN daily_error_events_count INTEGER;", "daily_realtime_events_count", "ALTER TABLE apps ADD COLUMN daily_realtime_events_count INTEGER;", "health_monitor_sample", "ALTER TABLE apps ADD COLUMN health_monitor_sample TEXT;", "android_id", "ALTER TABLE apps ADD COLUMN android_id INTEGER;", "adid_reporting_enabled", "ALTER TABLE apps ADD COLUMN adid_reporting_enabled INTEGER;"};
    private static final String[] zziys = new String[]{"realtime", "ALTER TABLE raw_events ADD COLUMN realtime INTEGER;"};
    private static final String[] zziyt = new String[]{"has_realtime", "ALTER TABLE queue ADD COLUMN has_realtime INTEGER;"};
    private static final String[] zziyu = new String[]{"previous_install_count", "ALTER TABLE app2 ADD COLUMN previous_install_count INTEGER;"};
    private final zzcgr zziyv;
    private final zzclk zziyw = new zzclk(this.zzws());

    zzcgo(zzcim zzcim2) {
        super(zzcim2);
        this.zziyv = new zzcgr(this, this.getContext(), "google_app_measurement.db");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final long zza(String string2, String[] cursor, long l) {
        long l2;
        block7: {
            SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
            Cursor cursor2 = null;
            Cursor cursor3 = null;
            cursor3 = cursor = sQLiteDatabase.rawQuery(string2, (String[])cursor);
            cursor2 = cursor;
            if (!cursor.moveToFirst()) break block7;
            cursor3 = cursor;
            cursor2 = cursor;
            try {
                l2 = l = cursor.getLong(0);
                if (cursor == null) return l2;
            }
            catch (SQLiteException sQLiteException) {
                cursor2 = cursor3;
                try {
                    this.zzawy().zzazd().zze("Database error", string2, (Object)sQLiteException);
                    cursor2 = cursor3;
                }
                catch (Throwable throwable) {
                    if (cursor2 == null) throw throwable;
                    cursor2.close();
                    throw throwable;
                }
                throw sQLiteException;
            }
            cursor.close();
            return l;
        }
        l2 = l;
        if (cursor == null) return l2;
        cursor.close();
        return l;
    }

    static /* synthetic */ zzclk zza(zzcgo zzcgo2) {
        return zzcgo2.zziyw;
    }

    private final Object zza(Cursor cursor, int n) {
        int n2 = cursor.getType(n);
        switch (n2) {
            default: {
                this.zzawy().zzazd().zzj("Loaded invalid unknown value type, ignoring it", n2);
                return null;
            }
            case 0: {
                this.zzawy().zzazd().log("Loaded invalid null value from database");
                return null;
            }
            case 1: {
                return cursor.getLong(n);
            }
            case 2: {
                return cursor.getDouble(n);
            }
            case 3: {
                return cursor.getString(n);
            }
            case 4: 
        }
        this.zzawy().zzazd().log("Loaded invalid blob type value, ignoring it");
        return null;
    }

    private static void zza(ContentValues contentValues, String string2, Object object) {
        zzbq.zzgm(string2);
        zzbq.checkNotNull(object);
        if (object instanceof String) {
            contentValues.put(string2, (String)object);
            return;
        }
        if (object instanceof Long) {
            contentValues.put(string2, (Long)object);
            return;
        }
        if (object instanceof Double) {
            contentValues.put(string2, (Double)object);
            return;
        }
        throw new IllegalArgumentException("Invalid value type");
    }

    static void zza(zzchm zzchm2, SQLiteDatabase object) {
        if (zzchm2 == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        if (!((File)(object = new File(object.getPath()))).setReadable(false, false)) {
            zzchm2.zzazf().log("Failed to turn off database read permission");
        }
        if (!((File)object).setWritable(false, false)) {
            zzchm2.zzazf().log("Failed to turn off database write permission");
        }
        if (!((File)object).setReadable(true, true)) {
            zzchm2.zzazf().log("Failed to turn on database read permission for owner");
        }
        if (!((File)object).setWritable(true, true)) {
            zzchm2.zzazf().log("Failed to turn on database write permission for owner");
        }
    }

    static void zza(zzchm zzchm2, SQLiteDatabase sQLiteDatabase, String string2, String string3, String string4, String[] arrstring) throws SQLiteException {
        if (zzchm2 == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        if (!zzcgo.zza(zzchm2, sQLiteDatabase, string2)) {
            sQLiteDatabase.execSQL(string3);
        }
        try {
            zzcgo.zza(zzchm2, sQLiteDatabase, string2, string4, arrstring);
            return;
        }
        catch (SQLiteException sQLiteException) {
            zzchm2.zzazd().zzj("Failed to verify columns on table that was just created", string2);
            throw sQLiteException;
        }
    }

    private static void zza(zzchm zzchm2, SQLiteDatabase sQLiteDatabase, String string2, String arrstring, String[] arrstring2) throws SQLiteException {
        int n;
        int n2 = 0;
        if (zzchm2 == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        Set<String> set = zzcgo.zzb(sQLiteDatabase, string2);
        arrstring = arrstring.split(",");
        int n3 = arrstring.length;
        for (n = 0; n < n3; ++n) {
            String string3 = arrstring[n];
            if (set.remove(string3)) continue;
            throw new SQLiteException(new StringBuilder(String.valueOf(string2).length() + 35 + String.valueOf(string3).length()).append("Table ").append(string2).append(" is missing required column: ").append(string3).toString());
        }
        if (arrstring2 != null) {
            for (n = n2; n < arrstring2.length; n += 2) {
                if (set.remove(arrstring2[n])) continue;
                sQLiteDatabase.execSQL(arrstring2[n + 1]);
            }
        }
        if (!set.isEmpty()) {
            zzchm2.zzazf().zze("Table has extra columns. table, columns", string2, TextUtils.join((CharSequence)", ", set));
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private static boolean zza(zzchm var0, SQLiteDatabase var1_4, String var2_5) {
        block11: {
            var4_6 = null;
            if (var0 == null) {
                throw new IllegalArgumentException("Monitor must not be null");
            }
            var1_4 = var4_6 = (var1_4 = var1_4.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{var2_5}, null, null, null));
            try {
                var3_7 = var4_6.moveToFirst();
                if (var4_6 == null) break block11;
            }
            catch (SQLiteException var5_10) {
                ** continue;
            }
            var4_6.close();
        }
        return var3_7;
        catch (SQLiteException var5_8) {
            var4_6 = null;
lbl14:
            // 2 sources
            do {
                block12: {
                    var1_4 = var4_6;
                    var0.zzazf().zze("Error querying for table", var2_5, var5_9);
                    if (var4_6 == null) break block12;
                    var4_6.close();
                }
                return false;
                break;
            } while (true);
        }
        catch (Throwable var0_1) {
            var1_4 = var4_6;
lbl24:
            // 2 sources
            do {
                if (var1_4 != null) {
                    var1_4.close();
                }
                throw var0_2;
                break;
            } while (true);
        }
        {
            catch (Throwable var0_3) {
                ** continue;
            }
        }
    }

    private final boolean zza(String string2, int n, zzcls zzcls2) {
        zzfjk zzfjk2;
        byte[] arrby;
        this.zzxf();
        this.zzve();
        zzbq.zzgm(string2);
        zzbq.checkNotNull(zzcls2);
        if (TextUtils.isEmpty((CharSequence)zzcls2.zzjjx)) {
            this.zzawy().zzazf().zzd("Event filter had no event name. Audience definition ignored. appId, audienceId, filterId", zzchm.zzjk(string2), n, String.valueOf(zzcls2.zzjjw));
            return false;
        }
        try {
            arrby = new byte[zzcls2.zzho()];
            zzfjk2 = zzfjk.zzo(arrby, 0, arrby.length);
            ((zzfjs)zzcls2).zza(zzfjk2);
            zzfjk2.zzcwt();
        }
        catch (IOException iOException) {
            this.zzawy().zzazd().zze("Configuration loss. Failed to serialize event filter. appId", zzchm.zzjk(string2), iOException);
            return false;
        }
        zzfjk2 = new ContentValues();
        zzfjk2.put("app_id", string2);
        zzfjk2.put("audience_id", Integer.valueOf(n));
        zzfjk2.put("filter_id", zzcls2.zzjjw);
        zzfjk2.put("event_name", zzcls2.zzjjx);
        zzfjk2.put("data", arrby);
        try {
            if (this.getWritableDatabase().insertWithOnConflict("event_filters", null, (ContentValues)zzfjk2, 5) == -1L) {
                this.zzawy().zzazd().zzj("Failed to insert event filter (got -1). appId", zzchm.zzjk(string2));
            }
            return true;
        }
        catch (SQLiteException sQLiteException) {
            this.zzawy().zzazd().zze("Error storing event filter. appId", zzchm.zzjk(string2), (Object)sQLiteException);
            return false;
        }
    }

    private final boolean zza(String string2, int n, zzclv zzclv2) {
        zzfjk zzfjk2;
        byte[] arrby;
        this.zzxf();
        this.zzve();
        zzbq.zzgm(string2);
        zzbq.checkNotNull(zzclv2);
        if (TextUtils.isEmpty((CharSequence)zzclv2.zzjkm)) {
            this.zzawy().zzazf().zzd("Property filter had no property name. Audience definition ignored. appId, audienceId, filterId", zzchm.zzjk(string2), n, String.valueOf(zzclv2.zzjjw));
            return false;
        }
        try {
            arrby = new byte[zzclv2.zzho()];
            zzfjk2 = zzfjk.zzo(arrby, 0, arrby.length);
            ((zzfjs)zzclv2).zza(zzfjk2);
            zzfjk2.zzcwt();
        }
        catch (IOException iOException) {
            this.zzawy().zzazd().zze("Configuration loss. Failed to serialize property filter. appId", zzchm.zzjk(string2), iOException);
            return false;
        }
        zzfjk2 = new ContentValues();
        zzfjk2.put("app_id", string2);
        zzfjk2.put("audience_id", Integer.valueOf(n));
        zzfjk2.put("filter_id", zzclv2.zzjjw);
        zzfjk2.put("property_name", zzclv2.zzjkm);
        zzfjk2.put("data", arrby);
        try {
            if (this.getWritableDatabase().insertWithOnConflict("property_filters", null, (ContentValues)zzfjk2, 5) == -1L) {
                this.zzawy().zzazd().zzj("Failed to insert property filter (got -1). appId", zzchm.zzjk(string2));
                return false;
            }
        }
        catch (SQLiteException sQLiteException) {
            this.zzawy().zzazd().zze("Error storing property filter. appId", zzchm.zzjk(string2), (Object)sQLiteException);
            return false;
        }
        return true;
    }

    private final boolean zzayn() {
        return this.getContext().getDatabasePath("google_app_measurement.db").exists();
    }

    static /* synthetic */ String[] zzayo() {
        return zziyp;
    }

    static /* synthetic */ String[] zzayp() {
        return zziyq;
    }

    static /* synthetic */ String[] zzayq() {
        return zziyr;
    }

    static /* synthetic */ String[] zzayr() {
        return zziyt;
    }

    static /* synthetic */ String[] zzays() {
        return zziys;
    }

    static /* synthetic */ String[] zzayt() {
        return zziyu;
    }

    private final long zzb(String string2, String[] cursor) {
        Cursor cursor2;
        Cursor cursor3;
        block9: {
            long l;
            block10: {
                SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
                cursor3 = null;
                cursor2 = null;
                cursor2 = cursor = sQLiteDatabase.rawQuery(string2, (String[])cursor);
                cursor3 = cursor;
                if (!cursor.moveToFirst()) break block9;
                cursor2 = cursor;
                cursor3 = cursor;
                l = cursor.getLong(0);
                if (cursor == null) break block10;
                cursor.close();
            }
            return l;
        }
        cursor2 = cursor;
        cursor3 = cursor;
        try {
            throw new SQLiteException("Database returned empty set");
        }
        catch (SQLiteException sQLiteException) {
            cursor3 = cursor2;
            try {
                this.zzawy().zzazd().zze("Database error", string2, (Object)sQLiteException);
                cursor3 = cursor2;
            }
            catch (Throwable throwable) {
                if (cursor3 != null) {
                    cursor3.close();
                }
                throw throwable;
            }
            throw sQLiteException;
        }
    }

    private static Set<String> zzb(SQLiteDatabase sQLiteDatabase, String string2) {
        HashSet<String> hashSet = new HashSet<String>();
        sQLiteDatabase = sQLiteDatabase.rawQuery(new StringBuilder(String.valueOf(string2).length() + 22).append("SELECT * FROM ").append(string2).append(" LIMIT 0").toString(), null);
        try {
            Collections.addAll(hashSet, sQLiteDatabase.getColumnNames());
            return hashSet;
        }
        finally {
            sQLiteDatabase.close();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final boolean zze(String string2, List<Integer> object) {
        long l;
        zzbq.zzgm(string2);
        this.zzxf();
        this.zzve();
        SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
        try {
            l = this.zzb("select count(1) from audience_filter_values where app_id=?", new String[]{string2});
        }
        catch (SQLiteException sQLiteException) {
            this.zzawy().zzazd().zze("Database error querying filters. appId", zzchm.zzjk(string2), (Object)sQLiteException);
            return false;
        }
        int n = Math.max(0, Math.min(2000, this.zzaxa().zzb(string2, zzchc.zzjbi)));
        if (l <= (long)n) return false;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < object.size(); ++i) {
            Integer n2 = (Integer)object.get(i);
            if (n2 == null || !(n2 instanceof Integer)) return false;
            {
                arrayList.add(Integer.toString(n2));
                continue;
            }
        }
        object = TextUtils.join((CharSequence)",", arrayList);
        object = new StringBuilder(String.valueOf(object).length() + 2).append("(").append((String)object).append(")").toString();
        if (sQLiteDatabase.delete("audience_filter_values", new StringBuilder(String.valueOf(object).length() + 140).append("audience_id in (select audience_id from audience_filter_values where app_id=? and audience_id not in ").append((String)object).append(" order by rowid desc limit -1 offset ?)").toString(), new String[]{string2, Integer.toString(n)}) > 0) return true;
        return false;
    }

    public final void beginTransaction() {
        this.zzxf();
        this.getWritableDatabase().beginTransaction();
    }

    public final void endTransaction() {
        this.zzxf();
        this.getWritableDatabase().endTransaction();
    }

    final SQLiteDatabase getWritableDatabase() {
        this.zzve();
        try {
            SQLiteDatabase sQLiteDatabase = this.zziyv.getWritableDatabase();
            return sQLiteDatabase;
        }
        catch (SQLiteException sQLiteException) {
            this.zzawy().zzazf().zzj("Error opening database", (Object)sQLiteException);
            throw sQLiteException;
        }
    }

    public final void setTransactionSuccessful() {
        this.zzxf();
        this.getWritableDatabase().setTransactionSuccessful();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final long zza(zzcme zzcme2) throws IOException {
        byte[] arrby;
        Object object;
        long l;
        this.zzve();
        this.zzxf();
        zzbq.checkNotNull(zzcme2);
        zzbq.zzgm(zzcme2.zzcn);
        try {
            arrby = new byte[zzcme2.zzho()];
            object = zzfjk.zzo(arrby, 0, arrby.length);
            ((zzfjs)zzcme2).zza((zzfjk)object);
            ((zzfjk)object).zzcwt();
            object = this.zzawu();
        }
        catch (IOException iOException) {
            this.zzawy().zzazd().zze("Data loss. Failed to serialize event metadata. appId", zzchm.zzjk(zzcme2.zzcn), iOException);
            throw iOException;
        }
        zzbq.checkNotNull(arrby);
        ((zzcjk)object).zzve();
        MessageDigest messageDigest = zzclq.zzek("MD5");
        if (messageDigest == null) {
            ((zzcjk)object).zzawy().zzazd().log("Failed to get MD5");
            l = 0L;
        } else {
            l = zzclq.zzs(messageDigest.digest(arrby));
        }
        object = new ContentValues();
        object.put("app_id", zzcme2.zzcn);
        object.put("metadata_fingerprint", Long.valueOf(l));
        object.put("metadata", arrby);
        try {}
        catch (SQLiteException sQLiteException) {
            this.zzawy().zzazd().zze("Error storing raw event metadata. appId", zzchm.zzjk(zzcme2.zzcn), (Object)sQLiteException);
            throw sQLiteException;
        }
        this.getWritableDatabase().insertWithOnConflict("raw_events_metadata", null, (ContentValues)object, 4);
        return l;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public final zzcgp zza(long var1_1, String var3_2, boolean var4_6, boolean var5_7, boolean var6_8, boolean var7_9, boolean var8_10) {
        block19: {
            block17: {
                block18: {
                    zzbq.zzgm(var3_2);
                    this.zzve();
                    this.zzxf();
                    var12_11 = new zzcgp();
                    var11_12 = this.getWritableDatabase();
                    var9_17 = var10_16 = var11_12.query("apps", new String[]{"day", "daily_events_count", "daily_public_events_count", "daily_conversions_count", "daily_error_events_count", "daily_realtime_events_count"}, "app_id=?", new String[]{var3_2}, null, null, null);
                    if (var10_16.moveToFirst()) break block17;
                    var9_17 = var10_16;
                    this.zzawy().zzazf().zzj("Not updating daily counts, app is not known. appId", zzchm.zzjk(var3_2));
                    if (var10_16 == null) break block18;
                    var10_16.close();
                }
                return var12_11;
            }
            var9_17 = var10_16;
            try {
                if (var10_16.getLong(0) == var1_1) {
                    var9_17 = var10_16;
                    var12_11.zziyy = var10_16.getLong(1);
                    var9_17 = var10_16;
                    var12_11.zziyx = var10_16.getLong(2);
                    var9_17 = var10_16;
                    var12_11.zziyz = var10_16.getLong(3);
                    var9_17 = var10_16;
                    var12_11.zziza = var10_16.getLong(4);
                    var9_17 = var10_16;
                    var12_11.zzizb = var10_16.getLong(5);
                }
                if (var4_6) {
                    var9_17 = var10_16;
                    ++var12_11.zziyy;
                }
                if (var5_7) {
                    var9_17 = var10_16;
                    ++var12_11.zziyx;
                }
                if (var6_8) {
                    var9_17 = var10_16;
                    ++var12_11.zziyz;
                }
                if (var7_9) {
                    var9_17 = var10_16;
                    ++var12_11.zziza;
                }
                if (var8_10) {
                    var9_17 = var10_16;
                    ++var12_11.zzizb;
                }
                var9_17 = var10_16;
                var13_18 = new ContentValues();
                var9_17 = var10_16;
                var13_18.put("day", Long.valueOf(var1_1));
                var9_17 = var10_16;
                var13_18.put("daily_public_events_count", Long.valueOf(var12_11.zziyx));
                var9_17 = var10_16;
                var13_18.put("daily_events_count", Long.valueOf(var12_11.zziyy));
                var9_17 = var10_16;
                var13_18.put("daily_conversions_count", Long.valueOf(var12_11.zziyz));
                var9_17 = var10_16;
                var13_18.put("daily_error_events_count", Long.valueOf(var12_11.zziza));
                var9_17 = var10_16;
                var13_18.put("daily_realtime_events_count", Long.valueOf(var12_11.zzizb));
                var9_17 = var10_16;
                var11_12.update("apps", var13_18, "app_id=?", new String[]{var3_2});
                if (var10_16 == null) break block19;
            }
            catch (SQLiteException var11_15) {
                ** continue;
            }
            var10_16.close();
        }
        return var12_11;
        catch (SQLiteException var11_13) {
            var10_16 = null;
lbl69:
            // 2 sources
            do {
                block20: {
                    var9_17 = var10_16;
                    this.zzawy().zzazd().zze("Error updating daily counts. appId", zzchm.zzjk(var3_2), var11_14);
                    if (var10_16 == null) break block20;
                    var10_16.close();
                }
                return var12_11;
                break;
            } while (true);
        }
        catch (Throwable var3_3) {
            var9_17 = null;
lbl79:
            // 2 sources
            do {
                if (var9_17 != null) {
                    var9_17.close();
                }
                throw var3_4;
                break;
            } while (true);
        }
        {
            catch (Throwable var3_5) {
                ** continue;
            }
        }
    }

    public final void zza(zzcgh zzcgh2) {
        zzbq.checkNotNull(zzcgh2);
        this.zzve();
        this.zzxf();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzcgh2.getAppId());
        contentValues.put("app_instance_id", zzcgh2.getAppInstanceId());
        contentValues.put("gmp_app_id", zzcgh2.getGmpAppId());
        contentValues.put("resettable_device_id_hash", zzcgh2.zzaxc());
        contentValues.put("last_bundle_index", Long.valueOf(zzcgh2.zzaxl()));
        contentValues.put("last_bundle_start_timestamp", Long.valueOf(zzcgh2.zzaxe()));
        contentValues.put("last_bundle_end_timestamp", Long.valueOf(zzcgh2.zzaxf()));
        contentValues.put("app_version", zzcgh2.zzvj());
        contentValues.put("app_store", zzcgh2.zzaxh());
        contentValues.put("gmp_version", Long.valueOf(zzcgh2.zzaxi()));
        contentValues.put("dev_cert_hash", Long.valueOf(zzcgh2.zzaxj()));
        contentValues.put("measurement_enabled", Boolean.valueOf(zzcgh2.zzaxk()));
        contentValues.put("day", Long.valueOf(zzcgh2.zzaxp()));
        contentValues.put("daily_public_events_count", Long.valueOf(zzcgh2.zzaxq()));
        contentValues.put("daily_events_count", Long.valueOf(zzcgh2.zzaxr()));
        contentValues.put("daily_conversions_count", Long.valueOf(zzcgh2.zzaxs()));
        contentValues.put("config_fetched_time", Long.valueOf(zzcgh2.zzaxm()));
        contentValues.put("failed_config_fetch_time", Long.valueOf(zzcgh2.zzaxn()));
        contentValues.put("app_version_int", Long.valueOf(zzcgh2.zzaxg()));
        contentValues.put("firebase_instance_id", zzcgh2.zzaxd());
        contentValues.put("daily_error_events_count", Long.valueOf(zzcgh2.zzaxu()));
        contentValues.put("daily_realtime_events_count", Long.valueOf(zzcgh2.zzaxt()));
        contentValues.put("health_monitor_sample", zzcgh2.zzaxv());
        contentValues.put("android_id", Long.valueOf(zzcgh2.zzaxx()));
        contentValues.put("adid_reporting_enabled", Boolean.valueOf(zzcgh2.zzaxy()));
        try {
            SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
            if ((long)sQLiteDatabase.update("apps", contentValues, "app_id = ?", new String[]{zzcgh2.getAppId()}) == 0L && sQLiteDatabase.insertWithOnConflict("apps", null, contentValues, 5) == -1L) {
                this.zzawy().zzazd().zzj("Failed to insert/update app (got -1). appId", zzchm.zzjk(zzcgh2.getAppId()));
            }
            return;
        }
        catch (SQLiteException sQLiteException) {
            this.zzawy().zzazd().zze("Error storing app. appId", zzchm.zzjk(zzcgh2.getAppId()), (Object)sQLiteException);
            return;
        }
    }

    public final void zza(zzcgw zzcgw2) {
        Long l = null;
        zzbq.checkNotNull(zzcgw2);
        this.zzve();
        this.zzxf();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzcgw2.mAppId);
        contentValues.put("name", zzcgw2.mName);
        contentValues.put("lifetime_count", Long.valueOf(zzcgw2.zzizk));
        contentValues.put("current_bundle_count", Long.valueOf(zzcgw2.zzizl));
        contentValues.put("last_fire_timestamp", Long.valueOf(zzcgw2.zzizm));
        contentValues.put("last_bundled_timestamp", Long.valueOf(zzcgw2.zzizn));
        contentValues.put("last_sampled_complex_event_id", zzcgw2.zzizo);
        contentValues.put("last_sampling_rate", zzcgw2.zzizp);
        Long l2 = l;
        if (zzcgw2.zzizq != null) {
            l2 = l;
            if (zzcgw2.zzizq.booleanValue()) {
                l2 = 1L;
            }
        }
        contentValues.put("last_exempt_from_sampling", l2);
        try {
            if (this.getWritableDatabase().insertWithOnConflict("events", null, contentValues, 5) == -1L) {
                this.zzawy().zzazd().zzj("Failed to insert/update event aggregates (got -1). appId", zzchm.zzjk(zzcgw2.mAppId));
            }
            return;
        }
        catch (SQLiteException sQLiteException) {
            this.zzawy().zzazd().zze("Error storing event aggregates. appId", zzchm.zzjk(zzcgw2.mAppId), (Object)sQLiteException);
            return;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    final void zza(String var1_1, zzclr[] var2_3) {
        var7_4 = 0;
        this.zzxf();
        this.zzve();
        zzbq.zzgm(var1_1);
        zzbq.checkNotNull(var2_3);
        var11_5 = this.getWritableDatabase();
        var11_5.beginTransaction();
        try {
            this.zzxf();
            this.zzve();
            zzbq.zzgm(var1_1);
            var12_6 = this.getWritableDatabase();
            var12_6.delete("property_filters", "app_id=?", new String[]{var1_1});
            var12_6.delete("event_filters", "app_id=?", new String[]{var1_1});
            var8_7 = var2_3.length;
            var4_8 = 0;
lbl22:
            // 2 sources
            do {
                block17: {
                    block15: {
                        block16: {
                            if (var4_8 >= var8_7) break block15;
                            var12_6 = var2_3[var4_8];
                            this.zzxf();
                            this.zzve();
                            zzbq.zzgm(var1_1);
                            zzbq.checkNotNull(var12_6);
                            zzbq.checkNotNull(var12_6.zzjju);
                            zzbq.checkNotNull(var12_6.zzjjt);
                            if (var12_6.zzjjs != null) break block16;
                            this.zzawy().zzazf().zzj("Audience with no ID. appId", zzchm.zzjk(var1_1));
                            ** GOTO lbl112
                        }
                        var9_12 = var12_6.zzjjs;
                        var13_14 = var12_6.zzjju;
                        var5_10 = var13_14.length;
                        break block17;
                    }
                    var12_6 = new ArrayList<E>();
                    var4_8 = var2_3.length;
                    for (var3_9 = var7_4; var3_9 < var4_8; ++var3_9) {
                        var12_6.add(var2_3[var3_9].zzjjs);
                    }
                    this.zze(var1_1, (List<Integer>)var12_6);
                    var11_5.setTransactionSuccessful();
                    var11_5.endTransaction();
                    return;
                }
                for (var3_9 = 0; var3_9 < var5_10; ++var3_9) {
                    if (var13_14[var3_9].zzjjw != null) continue;
                    this.zzawy().zzazf().zze("Event filter with no ID. Audience definition ignored. appId, audienceId", zzchm.zzjk(var1_1), var12_6.zzjjs);
                    ** GOTO lbl112
                }
                var13_14 = var12_6.zzjjt;
                var5_10 = var13_14.length;
                var3_9 = 0;
                break;
            } while (true);
        }
        catch (Throwable var1_2) {
            var11_5.endTransaction();
            throw var1_2;
        }
        do {
            block18: {
                if (var3_9 >= var5_10) break;
                if (var13_14[var3_9].zzjjw != null) break block18;
                this.zzawy().zzazf().zze("Property filter with no ID. Audience definition ignored. appId, audienceId", zzchm.zzjk(var1_1), var12_6.zzjjs);
                ** GOTO lbl112
            }
            ++var3_9;
        } while (true);
        var13_14 = var12_6.zzjju;
        var5_10 = var13_14.length;
        var3_9 = 0;
        do {
            block20: {
                block21: {
                    block19: {
                        if (var3_9 >= var5_10) break block19;
                        if (this.zza(var1_1, var9_12, (zzcls)var13_14[var3_9])) break block20;
                        var3_9 = 0;
                        break block21;
                    }
                    var3_9 = 1;
                }
                var6_11 = var3_9;
                if (var3_9 != 0) {
                    break;
                }
                ** GOTO lbl101
            }
            ++var3_9;
        } while (true);
        var12_6 = var12_6.zzjjt;
        var10_13 = ((Object)var12_6).length;
        var5_10 = 0;
        do {
            block23: {
                block22: {
                    var6_11 = var3_9;
                    if (var5_10 >= var10_13) break block22;
                    if (this.zza(var1_1, var9_12, (zzclv)var12_6[var5_10])) break block23;
                    var6_11 = 0;
                }
                if (var6_11 == 0) {
                    this.zzxf();
                    this.zzve();
                    zzbq.zzgm(var1_1);
                    var12_6 = this.getWritableDatabase();
                    var12_6.delete("property_filters", "app_id=? and audience_id=?", new String[]{var1_1, String.valueOf(var9_12)});
                    var12_6.delete("event_filters", "app_id=? and audience_id=?", new String[]{var1_1, String.valueOf(var9_12)});
                }
lbl112:
                // 6 sources
                ++var4_8;
                ** continue;
            }
            ++var5_10;
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean zza(zzcgl zzcgl2) {
        zzbq.checkNotNull(zzcgl2);
        this.zzve();
        this.zzxf();
        if (this.zzag(zzcgl2.packageName, zzcgl2.zziyg.name) == null) {
            if (this.zzb("SELECT COUNT(1) FROM conditional_properties WHERE app_id=?", new String[]{zzcgl2.packageName}) >= 1000L) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzcgl2.packageName);
        contentValues.put("origin", zzcgl2.zziyf);
        contentValues.put("name", zzcgl2.zziyg.name);
        zzcgo.zza(contentValues, "value", zzcgl2.zziyg.getValue());
        contentValues.put("active", Boolean.valueOf(zzcgl2.zziyi));
        contentValues.put("trigger_event_name", zzcgl2.zziyj);
        contentValues.put("trigger_timeout", Long.valueOf(zzcgl2.zziyl));
        this.zzawu();
        contentValues.put("timed_out_event", zzclq.zza(zzcgl2.zziyk));
        contentValues.put("creation_timestamp", Long.valueOf(zzcgl2.zziyh));
        this.zzawu();
        contentValues.put("triggered_event", zzclq.zza(zzcgl2.zziym));
        contentValues.put("triggered_timestamp", Long.valueOf(zzcgl2.zziyg.zzjji));
        contentValues.put("time_to_live", Long.valueOf(zzcgl2.zziyn));
        this.zzawu();
        contentValues.put("expired_event", zzclq.zza(zzcgl2.zziyo));
        try {
            if (this.getWritableDatabase().insertWithOnConflict("conditional_properties", null, contentValues, 5) == -1L) {
                this.zzawy().zzazd().zzj("Failed to insert/update conditional user property (got -1)", zzchm.zzjk(zzcgl2.packageName));
            }
            do {
                return true;
                break;
            } while (true);
        }
        catch (SQLiteException sQLiteException) {
            this.zzawy().zzazd().zze("Error storing conditional user property", zzchm.zzjk(zzcgl2.packageName), (Object)sQLiteException);
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean zza(zzcgv zzcgv2, long l, boolean bl) {
        Object object;
        this.zzve();
        this.zzxf();
        zzbq.checkNotNull(zzcgv2);
        zzbq.zzgm(zzcgv2.mAppId);
        zzcmb zzcmb2 = new zzcmb();
        zzcmb2.zzjlj = zzcgv2.zzizi;
        zzcmb2.zzjlh = new zzcmc[zzcgv2.zzizj.size()];
        byte[] arrby = zzcgv2.zzizj.iterator();
        int n = 0;
        while (arrby.hasNext()) {
            Object object2 = arrby.next();
            zzcmb2.zzjlh[n] = object = new zzcmc();
            ((zzcmc)object).name = object2;
            object2 = zzcgv2.zzizj.get((String)object2);
            this.zzawu().zza((zzcmc)object, object2);
            ++n;
        }
        try {
            arrby = new byte[zzcmb2.zzho()];
            object = zzfjk.zzo(arrby, 0, arrby.length);
            ((zzfjs)zzcmb2).zza((zzfjk)object);
            ((zzfjk)object).zzcwt();
            this.zzawy().zzazj().zze("Saving event, name, data size", this.zzawt().zzjh(zzcgv2.mName), arrby.length);
        }
        catch (IOException iOException) {
            this.zzawy().zzazd().zze("Data loss. Failed to serialize event params/data. appId", zzchm.zzjk(zzcgv2.mAppId), iOException);
            return false;
        }
        zzcmb2 = new ContentValues();
        zzcmb2.put("app_id", zzcgv2.mAppId);
        zzcmb2.put("name", zzcgv2.mName);
        zzcmb2.put("timestamp", Long.valueOf(zzcgv2.zzfij));
        zzcmb2.put("metadata_fingerprint", Long.valueOf(l));
        zzcmb2.put("data", arrby);
        n = bl ? 1 : 0;
        zzcmb2.put("realtime", Integer.valueOf(n));
        try {}
        catch (SQLiteException sQLiteException) {
            this.zzawy().zzazd().zze("Error storing raw event. appId", zzchm.zzjk(zzcgv2.mAppId), (Object)sQLiteException);
            return false;
        }
        if (this.getWritableDatabase().insert("raw_events", null, (ContentValues)zzcmb2) == -1L) {
            this.zzawy().zzazd().zzj("Failed to insert raw event (got -1). appId", zzchm.zzjk(zzcgv2.mAppId));
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean zza(zzclp zzclp2) {
        zzbq.checkNotNull(zzclp2);
        this.zzve();
        this.zzxf();
        if (this.zzag(zzclp2.mAppId, zzclp2.mName) == null) {
            if (zzclq.zzjz(zzclp2.mName)) {
                if (this.zzb("select count(1) from user_attributes where app_id=? and name not like '!_%' escape '!'", new String[]{zzclp2.mAppId}) >= 25L) {
                    return false;
                }
            } else if (this.zzb("select count(1) from user_attributes where app_id=? and origin=? AND name like '!_%' escape '!'", new String[]{zzclp2.mAppId, zzclp2.mOrigin}) >= 25L) return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzclp2.mAppId);
        contentValues.put("origin", zzclp2.mOrigin);
        contentValues.put("name", zzclp2.mName);
        contentValues.put("set_timestamp", Long.valueOf(zzclp2.zzjjm));
        zzcgo.zza(contentValues, "value", zzclp2.mValue);
        try {
            if (this.getWritableDatabase().insertWithOnConflict("user_attributes", null, contentValues, 5) != -1L) return true;
            this.zzawy().zzazd().zzj("Failed to insert/update user property (got -1). appId", zzchm.zzjk(zzclp2.mAppId));
            return true;
        }
        catch (SQLiteException sQLiteException) {
            this.zzawy().zzazd().zze("Error storing user property. appId", zzchm.zzjk(zzclp2.mAppId), (Object)sQLiteException);
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean zza(zzcme zzcme2, boolean bl) {
        byte[] arrby;
        zzfjk zzfjk2;
        this.zzve();
        this.zzxf();
        zzbq.checkNotNull(zzcme2);
        zzbq.zzgm(zzcme2.zzcn);
        zzbq.checkNotNull(zzcme2.zzjlt);
        this.zzayh();
        long l = this.zzws().currentTimeMillis();
        if (zzcme2.zzjlt < l - zzcgn.zzayb() || zzcme2.zzjlt > zzcgn.zzayb() + l) {
            this.zzawy().zzazf().zzd("Storing bundle outside of the max uploading time span. appId, now, timestamp", zzchm.zzjk(zzcme2.zzcn), l, zzcme2.zzjlt);
        }
        try {
            arrby = new byte[zzcme2.zzho()];
            zzfjk2 = zzfjk.zzo(arrby, 0, arrby.length);
            ((zzfjs)zzcme2).zza(zzfjk2);
            zzfjk2.zzcwt();
            arrby = this.zzawu().zzq(arrby);
            this.zzawy().zzazj().zzj("Saving bundle, size", arrby.length);
        }
        catch (IOException iOException) {
            this.zzawy().zzazd().zze("Data loss. Failed to serialize bundle. appId", zzchm.zzjk(zzcme2.zzcn), iOException);
            return false;
        }
        zzfjk2 = new ContentValues();
        zzfjk2.put("app_id", zzcme2.zzcn);
        zzfjk2.put("bundle_end_timestamp", zzcme2.zzjlt);
        zzfjk2.put("data", arrby);
        int n = bl ? 1 : 0;
        zzfjk2.put("has_realtime", Integer.valueOf(n));
        try {}
        catch (SQLiteException sQLiteException) {
            this.zzawy().zzazd().zze("Error storing bundle. appId", zzchm.zzjk(zzcme2.zzcn), (Object)sQLiteException);
            return false;
        }
        if (this.getWritableDatabase().insert("queue", null, (ContentValues)zzfjk2) == -1L) {
            this.zzawy().zzazd().zzj("Failed to insert bundle (got -1). appId", zzchm.zzjk(zzcme2.zzcn));
            return false;
        }
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public final zzcgw zzae(String var1_1, String var2_6) {
        block23: {
            block22: {
                block20: {
                    block21: {
                        zzbq.zzgm((String)var1_1);
                        zzbq.zzgm(var2_6);
                        this.zzve();
                        this.zzxf();
                        var14_7 = this.getWritableDatabase().query("events", new String[]{"lifetime_count", "current_bundle_count", "last_fire_timestamp", "last_bundled_timestamp", "last_sampled_complex_event_id", "last_sampling_rate", "last_exempt_from_sampling"}, "app_id=? and name=?", new String[]{var1_1, var2_6}, null, null, null);
                        var3_8 = var14_7.moveToFirst();
                        if (var3_8) break block20;
                        if (var14_7 == null) break block21;
                        var14_7.close();
                    }
                    var1_1 = null;
                    do {
                        return var1_1;
                        break;
                    } while (true);
                }
                var6_9 = var14_7.getLong(0);
                var8_10 = var14_7.getLong(1);
                var10_11 = var14_7.getLong(2);
                if (!var14_7.isNull(3)) break block22;
                var4_12 = 0L;
lbl25:
                // 2 sources
                while (var14_7.isNull(4)) {
                    var15_13 = null;
lbl27:
                    // 2 sources
                    while (var14_7.isNull(5)) {
                        var16_17 = null;
lbl29:
                        // 2 sources
                        do {
                            var17_18 = null;
                            if (var14_7.isNull(6)) ** GOTO lbl36
                            if (var14_7.getLong(6) != 1L) break block23;
                            var3_8 = true;
lbl34:
                            // 2 sources
                            do {
                                var17_18 = var3_8;
lbl36:
                                // 2 sources
                                var15_13 = new zzcgw((String)var1_1, var2_6, var6_9, var8_10, var10_11, var4_12, (Long)var15_13, var16_17, var17_18);
                                if (var14_7.moveToNext()) {
                                    this.zzawy().zzazd().zzj("Got multiple records for event aggregates, expected one. appId", zzchm.zzjk((String)var1_1));
                                }
                                var1_1 = var15_13;
                                if (var14_7 == null) ** continue;
                                break;
                            } while (true);
                            break;
                        } while (true);
                    }
                    ** GOTO lbl52
                }
                ** GOTO lbl50
                {
                    var14_7.close();
                    return var15_13;
                    break;
                }
            }
            try {
                var4_12 = var14_7.getLong(3);
                ** GOTO lbl25
lbl50:
                // 1 sources
                var15_13 = var14_7.getLong(4);
                ** GOTO lbl27
lbl52:
                // 1 sources
                var12_19 = var14_7.getLong(5);
            }
            catch (Throwable var1_4) {
                ** GOTO lbl69
            }
            var16_17 = var12_19;
            ** while (true)
        }
        var3_8 = false;
        ** while (true)
        catch (SQLiteException var15_14) {
            var14_7 = null;
lbl60:
            // 2 sources
            do {
                block24: {
                    this.zzawy().zzazd().zzd("Error querying events. appId", zzchm.zzjk((String)var1_1), this.zzawt().zzjh(var2_6), var15_15);
                    if (var14_7 == null) break block24;
                    var14_7.close();
                }
                return null;
                break;
            } while (true);
        }
        catch (Throwable var1_2) {
            var14_7 = null;
lbl69:
            // 3 sources
            do {
                if (var14_7 != null) {
                    var14_7.close();
                }
                throw var1_3;
                break;
            } while (true);
        }
        {
            catch (Throwable var1_5) {
                ** continue;
            }
        }
        catch (SQLiteException var15_16) {
            ** continue;
        }
    }

    public final void zzaf(String string2, String string3) {
        zzbq.zzgm(string2);
        zzbq.zzgm(string3);
        this.zzve();
        this.zzxf();
        try {
            int n = this.getWritableDatabase().delete("user_attributes", "app_id=? and name=?", new String[]{string2, string3});
            this.zzawy().zzazj().zzj("Deleted user attribute rows", n);
            return;
        }
        catch (SQLiteException sQLiteException) {
            this.zzawy().zzazd().zzd("Error deleting user attribute. appId", zzchm.zzjk(string2), this.zzawt().zzjj(string3), (Object)sQLiteException);
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public final zzclp zzag(String var1_1, String var2_6) {
        block14: {
            block15: {
                var7_7 = null;
                zzbq.zzgm((String)var1_1);
                zzbq.zzgm(var2_6);
                this.zzve();
                this.zzxf();
                var6_11 = this.getWritableDatabase().query("user_attributes", new String[]{"set_timestamp", "value", "origin"}, "app_id=? and name=?", new String[]{var1_1, var2_6}, null, null, null);
                var3_12 = var6_11.moveToFirst();
                if (var3_12) break block14;
                if (var6_11 == null) break block15;
                var6_11.close();
            }
            var1_1 = null;
            do {
                return var1_1;
                break;
            } while (true);
        }
        try {
            var4_13 = var6_11.getLong(0);
            var7_7 = this.zza(var6_11, 1);
            var7_7 = new zzclp((String)var1_1, var6_11.getString(2), var2_6, var4_13, var7_7);
            if (var6_11.moveToNext()) {
                this.zzawy().zzazd().zzj("Got multiple records for user property, expected one. appId", zzchm.zzjk((String)var1_1));
            }
            var1_1 = var7_7;
            if (var6_11 == null) ** continue;
        }
        catch (Throwable var1_4) {
            var2_6 = var6_11;
            ** GOTO lbl41
        }
        var6_11.close();
        return var7_7;
        catch (SQLiteException var7_8) {
            var6_11 = null;
lbl32:
            // 2 sources
            do {
                block16: {
                    this.zzawy().zzazd().zzd("Error querying user property. appId", zzchm.zzjk((String)var1_1), this.zzawt().zzjj(var2_6), var7_9);
                    if (var6_11 == null) break block16;
                    var6_11.close();
                }
                return null;
                break;
            } while (true);
        }
        catch (Throwable var1_2) {
            var2_6 = var7_7;
lbl41:
            // 3 sources
            do {
                if (var2_6 != null) {
                    var2_6.close();
                }
                throw var1_3;
                break;
            } while (true);
        }
        {
            catch (Throwable var1_5) {
                var2_6 = var6_11;
                ** continue;
            }
        }
        catch (SQLiteException var7_10) {
            ** continue;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final zzcgl zzah(String var1_1, String var2_6) {
        block15: {
            block14: {
                zzbq.zzgm((String)var1_1);
                zzbq.zzgm(var2_6);
                this.zzve();
                this.zzxf();
                var12_7 = this.getWritableDatabase().query("conditional_properties", new String[]{"origin", "value", "active", "trigger_event_name", "trigger_timeout", "timed_out_event", "creation_timestamp", "triggered_event", "triggered_timestamp", "time_to_live", "expired_event"}, "app_id=? and name=?", new String[]{var1_1, var2_6}, null, null, null);
                var3_8 = var12_7.moveToFirst();
                if (var3_8) break block14;
                if (var12_7 == null) return null;
                var12_7.close();
                return null;
            }
            try {
                var13_9 = var12_7.getString(0);
                var14_13 = this.zza(var12_7, 1);
                if (var12_7.getInt(2) == 0) break block15;
                var3_8 = true;
            }
            catch (Throwable var1_4) {
                ** GOTO lbl51
            }
lbl21:
            // 2 sources
            do {
                var15_14 = var12_7.getString(3);
                var4_15 = var12_7.getLong(4);
                var16_16 = this.zzawu().zzb(var12_7.getBlob(5), zzcha.CREATOR);
                var6_17 = var12_7.getLong(6);
                var17_18 = this.zzawu().zzb(var12_7.getBlob(7), zzcha.CREATOR);
                var8_19 = var12_7.getLong(8);
                var10_20 = var12_7.getLong(9);
                var18_21 = this.zzawu().zzb(var12_7.getBlob(10), zzcha.CREATOR);
                var13_9 = new zzcgl((String)var1_1, (String)var13_9, new zzcln(var2_6, var8_19, var14_13, (String)var13_9), var6_17, var3_8, var15_14, var16_16, var4_15, var17_18, var10_20, var18_21);
                if (var12_7.moveToNext()) {
                    this.zzawy().zzazd().zze("Got multiple records for conditional property, expected one", zzchm.zzjk((String)var1_1), this.zzawt().zzjj(var2_6));
                }
                var1_1 = var13_9;
                if (var12_7 == null) return var1_1;
                var12_7.close();
                return var13_9;
                break;
            } while (true);
        }
        var3_8 = false;
        ** while (true)
        catch (SQLiteException var13_10) {
            var12_7 = null;
lbl43:
            // 2 sources
            do {
                this.zzawy().zzazd().zzd("Error querying conditional property", zzchm.zzjk((String)var1_1), this.zzawt().zzjj(var2_6), var13_11);
                if (var12_7 == null) return null;
                var12_7.close();
                return null;
                break;
            } while (true);
        }
        catch (Throwable var1_2) {
            var12_7 = null;
lbl51:
            // 3 sources
            do {
                if (var12_7 == null) throw var1_3;
                var12_7.close();
                throw var1_3;
                break;
            } while (true);
        }
        {
            catch (Throwable var1_5) {
                ** continue;
            }
        }
        catch (SQLiteException var13_12) {
            ** continue;
        }
    }

    public final void zzah(List<Long> list) {
        int n;
        zzbq.checkNotNull(list);
        this.zzve();
        this.zzxf();
        StringBuilder stringBuilder = new StringBuilder("rowid in (");
        for (n = 0; n < list.size(); ++n) {
            if (n != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(list.get(n));
        }
        stringBuilder.append(")");
        n = this.getWritableDatabase().delete("raw_events", stringBuilder.toString(), null);
        if (n != list.size()) {
            this.zzawy().zzazd().zze("Deleted fewer rows from raw events table than expected", n, list.size());
        }
    }

    public final int zzai(String string2, String string3) {
        zzbq.zzgm(string2);
        zzbq.zzgm(string3);
        this.zzve();
        this.zzxf();
        try {
            int n = this.getWritableDatabase().delete("conditional_properties", "app_id=? and name=?", new String[]{string2, string3});
            return n;
        }
        catch (SQLiteException sQLiteException) {
            this.zzawy().zzazd().zzd("Error deleting conditional property", zzchm.zzjk(string2), this.zzawt().zzjj(string3), (Object)sQLiteException);
            return 0;
        }
    }

    /*
     * Exception decompiling
     */
    final Map<Integer, List<zzcls>> zzaj(String var1_1, String var2_5) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 4[TRYBLOCK]
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
     * Exception decompiling
     */
    final Map<Integer, List<zzclv>> zzak(String var1_1, String var2_5) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 4[TRYBLOCK]
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
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected final long zzal(String var1_1, String var2_3) {
        block10: {
            block8: {
                block9: {
                    zzbq.zzgm(var1_1);
                    zzbq.zzgm(var2_3);
                    this.zzve();
                    this.zzxf();
                    var8_4 = this.getWritableDatabase();
                    var8_4.beginTransaction();
                    var3_6 = var5_5 = this.zza(new StringBuilder(String.valueOf(var2_3).length() + 32).append("select ").append(var2_3).append(" from app2 where app_id=?").toString(), new String[]{var1_1}, -1L);
                    if (var5_5 != -1L) break block8;
                    var7_7 = new ContentValues();
                    var7_7.put("app_id", var1_1);
                    var7_7.put("first_open_count", Integer.valueOf(0));
                    var7_7.put("previous_install_count", Integer.valueOf(0));
                    if (var8_4.insertWithOnConflict("app2", null, var7_7, 5) != -1L) break block9;
                    this.zzawy().zzazd().zze("Failed to insert column (got -1). appId", zzchm.zzjk(var1_1), var2_3);
                    var8_4.endTransaction();
                    return -1L;
                }
                var3_6 = 0L;
            }
            var7_7 = new ContentValues();
            var7_7.put("app_id", var1_1);
            var7_7.put(var2_3, Long.valueOf(1L + var3_6));
            if ((long)var8_4.update("app2", var7_7, "app_id = ?", new String[]{var1_1}) != 0L) break block10;
            this.zzawy().zzazd().zze("Failed to update column (got 0). appId", zzchm.zzjk(var1_1), var2_3);
            var8_4.endTransaction();
            return -1L;
        }
        var8_4.setTransactionSuccessful();
        var8_4.endTransaction();
        return var3_6;
        catch (SQLiteException var7_8) {
            block11: {
                var3_6 = 0L;
                break block11;
                catch (Throwable var1_2) {
                    throw var1_2;
                }
                catch (SQLiteException var7_10) {}
            }
            ** try [egrp 3[TRYBLOCK] [8 : 283->303)] { 
lbl44:
            // 1 sources
            this.zzawy().zzazd().zzd("Error inserting column. appId", zzchm.zzjk(var1_1), var2_3, var7_9);
            return var3_6;
lbl46:
            // 1 sources
            finally {
                var8_4.endTransaction();
            }
        }
    }

    @Override
    protected final boolean zzaxz() {
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public final String zzayf() {
        block11: {
            block12: {
                var4_1 = null;
                var1_2 = this.getWritableDatabase();
                var2_6 = var1_2 = var1_2.rawQuery("select app_id from queue order by has_realtime desc, rowid asc limit 1;", null);
                try {
                    if (!var1_2.moveToFirst()) break block11;
                    var2_7 = var1_2;
                    var2_8 = var3_17 = var1_2.getString(0);
                    if (var1_2 == null) break block12;
                }
                catch (SQLiteException var3_20) {
                    ** continue;
                }
                var1_2.close();
                var2_9 = var3_17;
            }
lbl13:
            // 3 sources
            do {
                return var2_10;
                break;
            } while (true);
        }
        var2_11 = var4_1;
        if (var1_2 == null) ** GOTO lbl13
        var1_2.close();
        return null;
        catch (SQLiteException var3_18) {
            var1_2 = null;
lbl22:
            // 2 sources
            do {
                var2_12 = var1_2;
                this.zzawy().zzazd().zzj("Database error getting next bundle app id", var3_19);
                var2_13 = var4_1;
                if (var1_2 == null) ** continue;
                var1_2.close();
                return null;
                break;
            } while (true);
        }
        catch (Throwable var1_3) {
            var2_14 = null;
lbl32:
            // 2 sources
            do {
                if (var2_15 != null) {
                    var2_15.close();
                }
                throw var1_4;
                break;
            } while (true);
        }
        {
            catch (Throwable var1_5) {
                ** continue;
            }
        }
    }

    public final boolean zzayg() {
        return this.zzb("select count(1) > 0 from queue where has_realtime = 1", null) != 0L;
    }

    /*
     * Enabled aggressive block sorting
     */
    final void zzayh() {
        int n;
        block3: {
            block2: {
                this.zzve();
                this.zzxf();
                if (!this.zzayn()) break block2;
                long l = this.zzawz().zzjcu.get();
                long l2 = this.zzws().elapsedRealtime();
                if (Math.abs(l2 - l) <= zzchc.zzjbb.get()) break block2;
                this.zzawz().zzjcu.set(l2);
                this.zzve();
                this.zzxf();
                if (!this.zzayn()) break block2;
                n = this.getWritableDatabase().delete("queue", "abs(bundle_end_timestamp - ?) > cast(? as integer)", new String[]{String.valueOf(this.zzws().currentTimeMillis()), String.valueOf(zzcgn.zzayb())});
                if (n > 0) break block3;
            }
            return;
        }
        this.zzawy().zzazj().zzj("Deleted stale rows. rowsDeleted", n);
    }

    public final long zzayi() {
        return this.zza("select max(bundle_end_timestamp) from queue", null, 0L);
    }

    public final long zzayj() {
        return this.zza("select max(timestamp) from raw_events", null, 0L);
    }

    public final boolean zzayk() {
        return this.zzb("select count(1) > 0 from raw_events", null) != 0L;
    }

    public final boolean zzayl() {
        return this.zzb("select count(1) > 0 from raw_events where realtime = 1", null) != 0L;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final long zzaym() {
        Cursor cursor;
        long l;
        Cursor cursor2;
        Cursor cursor3;
        long l2;
        block6: {
            l = -1L;
            cursor2 = null;
            cursor = null;
            cursor = cursor3 = this.getWritableDatabase().rawQuery("select rowid from raw_events order by rowid desc limit 1;", null);
            cursor2 = cursor3;
            boolean bl = cursor3.moveToFirst();
            if (bl) break block6;
            l2 = l;
            if (cursor3 == null) return l2;
            cursor3.close();
            return l;
        }
        cursor = cursor3;
        cursor2 = cursor3;
        try {
            l2 = l = (l2 = cursor3.getLong(0));
            if (cursor3 == null) return l2;
        }
        catch (SQLiteException sQLiteException) {
            cursor2 = cursor;
            try {
                this.zzawy().zzazd().zzj("Error querying raw events", (Object)sQLiteException);
                l2 = l;
                if (cursor == null) return l2;
            }
            catch (Throwable throwable) {
                if (cursor2 == null) throw throwable;
                cursor2.close();
                throw throwable;
            }
            cursor.close();
            return -1L;
        }
        cursor3.close();
        return l;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public final String zzba(long var1_1) {
        block12: {
            block13: {
                var6_2 = null;
                this.zzve();
                this.zzxf();
                var4_7 = var3_3 = this.getWritableDatabase().rawQuery("select app_id from apps where app_id in (select distinct app_id from raw_events) and config_fetched_time < ? order by failed_config_fetch_time limit 1;", new String[]{String.valueOf(var1_1)});
                if (var3_3.moveToFirst()) break block12;
                var4_8 = var3_3;
                this.zzawy().zzazj().log("No expired configs for apps with pending events");
                var4_9 = var6_2;
                if (var3_3 == null) break block13;
                var3_3.close();
                var4_10 = var6_2;
            }
lbl15:
            // 3 sources
            do {
                return var4_11;
                break;
            } while (true);
        }
        var4_12 = var3_3;
        try {
            var4_13 = var5_20 = var3_3.getString(0);
            if (var3_3 == null) ** GOTO lbl15
        }
        catch (SQLiteException var5_23) {
            ** continue;
        }
        var3_3.close();
        return var5_20;
        catch (SQLiteException var5_21) {
            var3_3 = null;
lbl26:
            // 2 sources
            do {
                var4_15 = var3_3;
                this.zzawy().zzazd().zzj("Error selecting expired configs", var5_22);
                var4_16 = var6_2;
                if (var3_3 == null) ** continue;
                var3_3.close();
                return null;
                break;
            } while (true);
        }
        catch (Throwable var3_4) {
            var4_17 = null;
lbl36:
            // 2 sources
            do {
                if (var4_18 != null) {
                    var4_18.close();
                }
                throw var3_5;
                break;
            } while (true);
        }
        {
            catch (Throwable var3_6) {
                ** continue;
            }
        }
    }

    /*
     * Exception decompiling
     */
    public final List<zzcgl> zzc(String var1_1, String[] var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[CATCHBLOCK]], but top level block is 6[CATCHBLOCK]
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
     * Exception decompiling
     */
    public final List<zzclp> zzg(String var1_1, String var2_6, String var3_8) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [13[DOLOOP]], but top level block is 3[TRYBLOCK]
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

    public final List<zzcgl> zzh(String charSequence, String arrstring, String string2) {
        zzbq.zzgm((String)charSequence);
        this.zzve();
        this.zzxf();
        ArrayList<String> arrayList = new ArrayList<String>(3);
        arrayList.add((String)charSequence);
        charSequence = new StringBuilder("app_id=?");
        if (!TextUtils.isEmpty((CharSequence)arrstring)) {
            arrayList.add((String)arrstring);
            ((StringBuilder)charSequence).append(" and origin=?");
        }
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            arrayList.add(String.valueOf(string2).concat("*"));
            ((StringBuilder)charSequence).append(" and name glob ?");
        }
        arrstring = arrayList.toArray(new String[arrayList.size()]);
        return this.zzc(((StringBuilder)charSequence).toString(), arrstring);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final List<zzclp> zzja(String var1_1) {
        var6_6 /* !! */  = null;
        zzbq.zzgm(var1_1);
        this.zzve();
        this.zzxf();
        var8_10 = new ArrayList<zzclp>();
        var5_11 = this.getWritableDatabase().query("user_attributes", new String[]{"name", "origin", "set_timestamp", "value"}, "app_id=?", new String[]{var1_1}, null, null, "rowid", "1000");
        var2_12 = var5_11.moveToFirst();
        if (var2_12) ** GOTO lbl21
        if (var5_11 == null) return var8_10;
        var5_11.close();
        return var8_10;
        catch (Throwable var1_2) {
            block13: {
                var5_11 = var6_6 /* !! */ ;
                break block13;
                catch (SQLiteException var6_9) {
                    var5_11 = null;
                    ** GOTO lbl-1000
                }
lbl21:
                // 1 sources
                try {
                    do {
                        var9_15 = var5_11.getString(0);
                        var7_14 = var5_11.getString(1);
                        var6_6 /* !! */  = var7_14;
                        if (var7_14 == null) {
                            var6_6 /* !! */  = "";
                        }
                        var3_13 = var5_11.getLong(2);
                        var7_14 = this.zza(var5_11, 3);
                        if (var7_14 == null) {
                            this.zzawy().zzazd().zzj("Read invalid user property value, ignoring it. appId", zzchm.zzjk(var1_1));
                            continue;
                        }
                        var8_10.add(new zzclp(var1_1, (String)var6_6 /* !! */ , var9_15, var3_13, var7_14));
                    } while (var2_12 = var5_11.moveToNext());
                    if (var5_11 == null) return var8_10;
                }
                catch (SQLiteException var6_7) lbl-1000:
                // 2 sources
                {
                    try {
                        this.zzawy().zzazd().zze("Error querying user properties. appId", zzchm.zzjk(var1_1), var6_8);
                        if (var5_11 == null) return null;
                    }
                    catch (Throwable var1_5) {}
                    var5_11.close();
                    return null;
                    catch (Throwable var1_4) {}
                    {
                    }
                }
                var5_11.close();
                return var8_10;
            }
            if (var5_11 == null) throw var1_3;
            var5_11.close();
            throw var1_3;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final zzcgh zzjb(String var1_1) {
        block54: {
            block50: {
                block55: {
                    block53: {
                        block52: {
                            block51: {
                                block49: {
                                    zzbq.zzgm((String)var1_1);
                                    this.zzve();
                                    this.zzxf();
                                    var5_6 = var6_5 = this.getWritableDatabase().query("apps", new String[]{"app_instance_id", "gmp_app_id", "resettable_device_id_hash", "last_bundle_index", "last_bundle_start_timestamp", "last_bundle_end_timestamp", "app_version", "app_store", "gmp_version", "dev_cert_hash", "measurement_enabled", "day", "daily_public_events_count", "daily_events_count", "daily_conversions_count", "config_fetched_time", "failed_config_fetch_time", "app_version_int", "firebase_instance_id", "daily_error_events_count", "daily_realtime_events_count", "health_monitor_sample", "android_id", "adid_reporting_enabled"}, "app_id=?", new String[]{var1_1}, null, null, null);
                                    var2_7 = var6_5.moveToFirst();
                                    if (var2_7) break block49;
                                    if (var6_5 == null) return null;
                                    var6_5.close();
                                    return null;
                                }
                                var5_6 = var6_5;
                                var7_8 = new zzcgh(this.zziwf, (String)var1_1);
                                var5_6 = var6_5;
                                var7_8.zzir(var6_5.getString(0));
                                var5_6 = var6_5;
                                var7_8.zzis(var6_5.getString(1));
                                var5_6 = var6_5;
                                var7_8.zzit(var6_5.getString(2));
                                var5_6 = var6_5;
                                var7_8.zzaq(var6_5.getLong(3));
                                var5_6 = var6_5;
                                var7_8.zzal(var6_5.getLong(4));
                                var5_6 = var6_5;
                                var7_8.zzam(var6_5.getLong(5));
                                var5_6 = var6_5;
                                var7_8.setAppVersion(var6_5.getString(6));
                                var5_6 = var6_5;
                                var7_8.zziv(var6_5.getString(7));
                                var5_6 = var6_5;
                                var7_8.zzao(var6_5.getLong(8));
                                var5_6 = var6_5;
                                var7_8.zzap(var6_5.getLong(9));
                                var5_6 = var6_5;
                                if (var6_5.isNull(10)) break block50;
                                var5_6 = var6_5;
                                if (var6_5.getInt(10) == 0) break block51;
lbl54:
                                // 2 sources
                                do {
                                    var5_6 = var6_5;
                                    var7_8.setMeasurementEnabled(var2_7);
                                    var5_6 = var6_5;
                                    var7_8.zzat(var6_5.getLong(11));
                                    var5_6 = var6_5;
                                    var7_8.zzau(var6_5.getLong(12));
                                    var5_6 = var6_5;
                                    var7_8.zzav(var6_5.getLong(13));
                                    var5_6 = var6_5;
                                    var7_8.zzaw(var6_5.getLong(14));
                                    var5_6 = var6_5;
                                    var7_8.zzar(var6_5.getLong(15));
                                    var5_6 = var6_5;
                                    var7_8.zzas(var6_5.getLong(16));
                                    var5_6 = var6_5;
                                    if (!var6_5.isNull(17)) break block52;
                                    var3_12 = Integer.MIN_VALUE;
lbl80:
                                    // 2 sources
                                    do {
                                        var5_6 = var6_5;
                                        var7_8.zzan(var3_12);
                                        var5_6 = var6_5;
                                        var7_8.zziu(var6_5.getString(18));
                                        var5_6 = var6_5;
                                        var7_8.zzay(var6_5.getLong(19));
                                        var5_6 = var6_5;
                                        var7_8.zzax(var6_5.getLong(20));
                                        var5_6 = var6_5;
                                        var7_8.zziw(var6_5.getString(21));
                                        var5_6 = var6_5;
                                        if (!var6_5.isNull(22)) break block53;
                                        var3_12 = 0L;
lbl100:
                                        // 2 sources
                                        do {
                                            var5_6 = var6_5;
                                            var7_8.zzaz(var3_12);
                                            var5_6 = var6_5;
                                            if (var6_5.isNull(23)) break block54;
                                            var5_6 = var6_5;
                                            if (var6_5.getInt(23) == 0) break block55;
                                            break block54;
                                            break;
                                        } while (true);
                                        break;
                                    } while (true);
lbl111:
                                    // 2 sources
                                    do {
                                        block56: {
                                            var5_6 = var6_5;
                                            var7_8.zzbl(var2_7);
                                            var5_6 = var6_5;
                                            var7_8.zzaxb();
                                            var5_6 = var6_5;
                                            if (!var6_5.moveToNext()) break block56;
                                            var5_6 = var6_5;
                                            this.zzawy().zzazd().zzj("Got multiple records for app, expected one. appId", zzchm.zzjk((String)var1_1));
                                        }
                                        var1_1 = var7_8;
                                        if (var6_5 == null) return var1_1;
                                        var6_5.close();
                                        return var7_8;
                                        break;
                                    } while (true);
                                    break;
                                } while (true);
                            }
                            var2_7 = false;
                            ** GOTO lbl54
                        }
                        var5_6 = var6_5;
                        var3_12 = var6_5.getInt(17);
                        ** continue;
                    }
                    var5_6 = var6_5;
                    try {
                        var3_12 = var6_5.getLong(22);
                        ** continue;
                    }
                    catch (SQLiteException var7_11) {
                        ** continue;
                    }
                }
                var2_7 = false;
                ** GOTO lbl111
                catch (SQLiteException var7_9) {
                    var6_5 = null;
lbl147:
                    // 2 sources
                    do {
                        var5_6 = var6_5;
                        this.zzawy().zzazd().zze("Error querying app. appId", zzchm.zzjk((String)var1_1), var7_10);
                        if (var6_5 == null) return null;
                        var6_5.close();
                        return null;
                        break;
                    } while (true);
                }
                catch (Throwable var1_2) {
                    var5_6 = null;
lbl156:
                    // 2 sources
                    do {
                        if (var5_6 == null) throw var1_3;
                        var5_6.close();
                        throw var1_3;
                        break;
                    } while (true);
                }
                {
                    catch (Throwable var1_4) {
                        ** continue;
                    }
                }
            }
            var2_7 = true;
            ** while (true)
        }
        var2_7 = true;
        ** while (true)
    }

    public final long zzjc(String string2) {
        int n;
        zzbq.zzgm(string2);
        this.zzve();
        this.zzxf();
        try {
            n = this.getWritableDatabase().delete("raw_events", "rowid in (select rowid from raw_events where app_id=? order by rowid desc limit -1 offset ?)", new String[]{string2, String.valueOf(Math.max(0, Math.min(1000000, this.zzaxa().zzb(string2, zzchc.zzjas))))});
        }
        catch (SQLiteException sQLiteException) {
            this.zzawy().zzazd().zze("Error deleting over the limit events. appId", zzchm.zzjk(string2), (Object)sQLiteException);
            return 0L;
        }
        return n;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public final byte[] zzjd(String var1_1) {
        block13: {
            block14: {
                zzbq.zzgm((String)var1_1);
                this.zzve();
                this.zzxf();
                var3_6 = var4_5 = this.getWritableDatabase().query("apps", new String[]{"remote_config"}, "app_id=?", new String[]{var1_1}, null, null, null);
                var2_7 = var4_5.moveToFirst();
                if (var2_7) break block13;
                if (var4_5 == null) break block14;
                var4_5.close();
            }
            var1_1 = null;
            do {
                return var1_1;
                break;
            } while (true);
        }
        var3_6 = var4_5;
        try {
            var5_8 = var4_5.getBlob(0);
            var3_6 = var4_5;
            if (var4_5.moveToNext()) {
                var3_6 = var4_5;
                this.zzawy().zzazd().zzj("Got multiple records for app config, expected one. appId", zzchm.zzjk((String)var1_1));
            }
            var1_1 = var5_8;
            if (var4_5 == null) ** continue;
        }
        catch (SQLiteException var5_11) {
            ** continue;
        }
        var4_5.close();
        return var5_8;
        catch (SQLiteException var5_9) {
            var4_5 = null;
lbl30:
            // 2 sources
            do {
                block15: {
                    var3_6 = var4_5;
                    this.zzawy().zzazd().zze("Error querying remote config. appId", zzchm.zzjk((String)var1_1), var5_10);
                    if (var4_5 == null) break block15;
                    var4_5.close();
                }
                return null;
                break;
            } while (true);
        }
        catch (Throwable var1_2) {
            var3_6 = null;
lbl40:
            // 2 sources
            do {
                if (var3_6 != null) {
                    var3_6.close();
                }
                throw var1_3;
                break;
            } while (true);
        }
        {
            catch (Throwable var1_4) {
                ** continue;
            }
        }
    }

    /*
     * Exception decompiling
     */
    final Map<Integer, zzcmf> zzje(String var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 4[TRYBLOCK]
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

    public final long zzjf(String string2) {
        zzbq.zzgm(string2);
        return this.zza("select count(1) from events where app_id=? and name not like '!_%' escape '!'", new String[]{string2}, 0L);
    }

    /*
     * Exception decompiling
     */
    public final List<Pair<zzcme, Long>> zzl(String var1_1, int var2_6, int var3_7) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 7[CATCHBLOCK]
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

