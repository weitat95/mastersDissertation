/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteException
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import com.google.android.gms.internal.zzaql;
import com.google.android.gms.internal.zzark;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

final class zzaqm
extends SQLiteOpenHelper {
    private /* synthetic */ zzaql zzdul;

    zzaqm(zzaql zzaql2, Context context, String string2) {
        this.zzdul = zzaql2;
        super(context, string2, null, 1);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void zza(SQLiteDatabase object) {
        object = zzaqm.zzb((SQLiteDatabase)object, "properties");
        for (int i = 0; i < 6; ++i) {
            String string2 = new String[]{"app_uid", "cid", "tid", "params", "adid", "hits_count"}[i];
            if (object.remove(string2)) continue;
            object = String.valueOf(string2);
            if (((String)object).length() != 0) {
                object = "Database properties is missing required column: ".concat((String)object);
                do {
                    throw new SQLiteException((String)object);
                    break;
                } while (true);
            }
            object = new String("Database properties is missing required column: ");
            throw new SQLiteException((String)object);
        }
        if (object.isEmpty()) return;
        throw new SQLiteException("Database properties table has extra columns");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private final boolean zza(SQLiteDatabase var1_1, String var2_3) {
        block10: {
            var4_11 = null;
            var1_3 = var4_12 = (var1_2 = var1_1 /* !! */ .query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{var2_10}, null, null, null));
            try {
                var3_16 = var4_12.moveToFirst();
                if (var4_12 == null) break block10;
            }
            catch (SQLiteException var5_19) {
                ** continue;
            }
            var4_12.close();
        }
        return var3_16;
        catch (SQLiteException var5_17) {
            var4_13 = null;
lbl12:
            // 2 sources
            do {
                block11: {
                    var1_5 = var4_14;
                    this.zzdul.zzc("Error querying for table", var2_10, var5_18);
                    if (var4_14 == null) break block11;
                    var4_14.close();
                }
                return false;
                break;
            } while (true);
        }
        catch (Throwable var1_6) {
            var2_10 = var4_11;
lbl22:
            // 2 sources
            do {
                if (var2_10 != null) {
                    var2_10.close();
                }
                throw var1_7;
                break;
            } while (true);
        }
        {
            catch (Throwable var4_15) {
                var2_10 = var1_8;
                var1_9 = var4_15;
                ** continue;
            }
        }
    }

    private static Set<String> zzb(SQLiteDatabase sQLiteDatabase, String arrstring) {
        int n;
        HashSet<String> hashSet = new HashSet<String>();
        sQLiteDatabase = sQLiteDatabase.rawQuery(new StringBuilder(String.valueOf(arrstring).length() + 22).append("SELECT * FROM ").append((String)arrstring).append(" LIMIT 0").toString(), null);
        try {
            arrstring = sQLiteDatabase.getColumnNames();
            n = 0;
        }
        catch (Throwable throwable) {
            sQLiteDatabase.close();
            throw throwable;
        }
        do {
            if (n >= arrstring.length) break;
            hashSet.add(arrstring[n]);
            ++n;
            continue;
            break;
        } while (true);
        sQLiteDatabase.close();
        return hashSet;
    }

    public final SQLiteDatabase getWritableDatabase() {
        if (!zzaql.zza(this.zzdul).zzu(3600000L)) {
            throw new SQLiteException("Database open failed");
        }
        try {
            SQLiteDatabase sQLiteDatabase = super.getWritableDatabase();
            return sQLiteDatabase;
        }
        catch (SQLiteException sQLiteException) {
            zzaql.zza(this.zzdul).start();
            this.zzdul.zzdy("Opening the database failed, dropping the table and recreating it");
            String string2 = zzaql.zzb(this.zzdul);
            this.zzdul.getContext().getDatabasePath(string2).delete();
            try {
                string2 = super.getWritableDatabase();
                zzaql.zza(this.zzdul).clear();
                return string2;
            }
            catch (SQLiteException sQLiteException2) {
                this.zzdul.zze("Failed to open freshly created database", (Object)sQLiteException2);
                throw sQLiteException2;
            }
        }
    }

    public final void onCreate(SQLiteDatabase object) {
        object = object.getPath();
        if (zzark.version() >= 9) {
            object = new File((String)object);
            ((File)object).setReadable(false, false);
            ((File)object).setWritable(false, false);
            ((File)object).setReadable(true, true);
            ((File)object).setWritable(true, true);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final void onOpen(SQLiteDatabase var1_1) {
        block11: {
            block10: {
                block9: {
                    var3_3 = 1;
                    if (Build.VERSION.SDK_INT < 15) {
                        var4_4 = var1_1.rawQuery("PRAGMA journal_mode=memory", null);
                        var4_4.moveToFirst();
                    }
                    if (this.zza((SQLiteDatabase)var1_1, "hits2")) break block9;
                    var1_1.execSQL(zzaql.zzxx());
                    break block10;
                    finally {
                        var4_4.close();
                    }
                }
                var4_4 = zzaqm.zzb((SQLiteDatabase)var1_1, "hits2");
                break block11;
            }
lbl17:
            // 3 sources
            do {
                if (!this.zza((SQLiteDatabase)var1_1, "properties")) {
                    var1_1.execSQL("CREATE TABLE IF NOT EXISTS properties ( app_uid INTEGER NOT NULL, cid TEXT NOT NULL, tid TEXT NOT NULL, params TEXT NOT NULL, adid INTEGER NOT NULL, hits_count INTEGER NOT NULL, PRIMARY KEY (app_uid, cid, tid)) ;");
                    return;
                }
                zzaqm.zza((SQLiteDatabase)var1_1);
                return;
                break;
            } while (true);
        }
        for (var2_5 = 0; var2_5 < 4; ++var2_5) {
            var5_6 = new String[]{"hit_id", "hit_string", "hit_time", "hit_url"}[var2_5];
            if (var4_4.remove(var5_6)) continue;
            var1_1 = String.valueOf(var5_6);
            if (var1_1.length() != 0) {
                var1_1 = "Database hits2 is missing required column: ".concat((String)var1_1);
                throw new SQLiteException((String)var1_1);
            }
            var1_1 = new String("Database hits2 is missing required column: ");
            throw new SQLiteException((String)var1_1);
        }
        var2_5 = var4_4.remove("hit_app_id") == false ? var3_3 : 0;
        if (!var4_4.isEmpty()) {
            throw new SQLiteException("Database hits2 has extra columns");
        }
        if (var2_5 == 0) ** GOTO lbl17
        var1_1.execSQL("ALTER TABLE hits2 ADD COLUMN hit_app_id INTEGER");
        ** while (true)
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
    }
}

