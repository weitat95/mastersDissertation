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
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import com.google.android.gms.tagmanager.zzbs;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzec;
import java.io.File;
import java.util.HashSet;

final class zzee
extends SQLiteOpenHelper {
    private /* synthetic */ zzec zzkhh;
    private boolean zzkhi;
    private long zzkhj;

    zzee(zzec zzec2, Context context, String string2) {
        this.zzkhh = zzec2;
        super(context, string2, null, 1);
        this.zzkhj = 0L;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static boolean zza(String var0, SQLiteDatabase var1_5) {
        var3_7 = null;
        var1_5 = var1_5.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{var0}, null, null, null);
        var2_9 = var1_5.moveToFirst();
        if (var1_5 == null) return var2_9;
        var1_5.close();
        return var2_9;
        catch (SQLiteException var1_6) {
            block9: {
                var1_5 = null;
                break block9;
                catch (Throwable var0_3) {
                    var1_5 = var3_7;
                    ** GOTO lbl-1000
                }
                catch (Throwable var0_4) {
                    ** GOTO lbl-1000
                }
                catch (SQLiteException var3_8) {}
            }
            try {
                var0 = String.valueOf(var0);
                var0 = var0.length() != 0 ? "Error querying for table ".concat(var0) : new String("Error querying for table ");
                zzdj.zzcu(var0);
                if (var1_5 == null) return false;
            }
            catch (Throwable var0_1) lbl-1000:
            // 3 sources
            {
                if (var1_5 == null) throw var0_2;
                var1_5.close();
                throw var0_2;
            }
            var1_5.close();
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase sQLiteDatabase;
        if (this.zzkhi && this.zzkhj + 3600000L > zzec.zza(this.zzkhh).currentTimeMillis()) {
            throw new SQLiteException("Database creation failed");
        }
        SQLiteDatabase sQLiteDatabase2 = null;
        this.zzkhi = true;
        this.zzkhj = zzec.zza(this.zzkhh).currentTimeMillis();
        try {
            sQLiteDatabase2 = sQLiteDatabase = super.getWritableDatabase();
        }
        catch (SQLiteException sQLiteException) {
            zzec.zzc(this.zzkhh).getDatabasePath(zzec.zzb(this.zzkhh)).delete();
        }
        sQLiteDatabase = sQLiteDatabase2;
        if (sQLiteDatabase2 == null) {
            sQLiteDatabase = super.getWritableDatabase();
        }
        this.zzkhi = false;
        return sQLiteDatabase;
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        zzbs.zzlo(sQLiteDatabase.getPath());
    }

    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void onOpen(SQLiteDatabase sQLiteDatabase) {
        Object object;
        block12: {
            block11: {
                if (Build.VERSION.SDK_INT < 15) {
                    object = sQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", null);
                    object.moveToFirst();
                }
                if (!zzee.zza("gtm_hits", sQLiteDatabase)) {
                    sQLiteDatabase.execSQL(zzec.zzbfn());
                    return;
                }
                break block11;
                finally {
                    object.close();
                }
            }
            sQLiteDatabase = sQLiteDatabase.rawQuery("SELECT * FROM gtm_hits WHERE 0", null);
            object = new HashSet();
            String[] arrstring = sQLiteDatabase.getColumnNames();
            for (int i = 0; i < arrstring.length; ++i) {
                object.add(arrstring[i]);
            }
            if (!(object.remove("hit_id") && object.remove("hit_url") && object.remove("hit_time") && object.remove("hit_first_send_time"))) {
                throw new SQLiteException("Database column missing");
            }
            break block12;
            finally {
                sQLiteDatabase.close();
            }
        }
        if (object.isEmpty()) return;
        {
            throw new SQLiteException("Database has extra columns");
        }
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
    }
}

