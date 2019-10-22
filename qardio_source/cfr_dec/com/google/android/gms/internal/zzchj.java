/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteDatabaseLockedException
 *  android.database.sqlite.SQLiteException
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzchi;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcjk;
import java.io.File;

@TargetApi(value=11)
final class zzchj
extends SQLiteOpenHelper {
    private /* synthetic */ zzchi zzjbp;

    zzchj(zzchi zzchi2, Context context, String string2) {
        this.zzjbp = zzchi2;
        super(context, string2, null, 1);
    }

    public final SQLiteDatabase getWritableDatabase() {
        try {
            SQLiteDatabase sQLiteDatabase = super.getWritableDatabase();
            return sQLiteDatabase;
        }
        catch (SQLiteException sQLiteException) {
            if (Build.VERSION.SDK_INT >= 11 && sQLiteException instanceof SQLiteDatabaseLockedException) {
                throw sQLiteException;
            }
            ((zzcjk)this.zzjbp).zzawy().zzazd().log("Opening the local database failed, dropping and recreating it");
            if (!((zzcjk)this.zzjbp).getContext().getDatabasePath("google_app_measurement_local.db").delete()) {
                ((zzcjk)this.zzjbp).zzawy().zzazd().zzj("Failed to delete corrupted local db file", "google_app_measurement_local.db");
            }
            try {
                SQLiteDatabase sQLiteDatabase = super.getWritableDatabase();
                return sQLiteDatabase;
            }
            catch (SQLiteException sQLiteException2) {
                ((zzcjk)this.zzjbp).zzawy().zzazd().zzj("Failed to open local database. Events will bypass local storage", (Object)sQLiteException2);
                return null;
            }
        }
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        zzcgo.zza(((zzcjk)this.zzjbp).zzawy(), sQLiteDatabase);
    }

    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final void onOpen(SQLiteDatabase var1_1) {
        block5: {
            var2_5 = null;
            if (Build.VERSION.SDK_INT < 15) {
                var3_6 = var1_1.rawQuery("PRAGMA journal_mode=memory", null);
                var3_6.moveToFirst();
                if (var3_6 == null) break block5;
                var3_6.close();
            }
        }
        zzcgo.zza(this.zzjbp.zzawy(), var1_1, "messages", "create table if not exists messages ( type INTEGER NOT NULL, entry BLOB NOT NULL)", "type,entry", null);
        return;
        catch (Throwable var1_2) {}
        ** GOTO lbl-1000
        catch (Throwable var1_4) {
            var2_5 = var3_6;
        }
lbl-1000:
        // 2 sources
        {
            if (var2_5 == null) throw var1_3;
            var2_5.close();
            throw var1_3;
        }
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
    }
}

