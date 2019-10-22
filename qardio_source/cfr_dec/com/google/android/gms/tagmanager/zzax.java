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
import com.google.android.gms.tagmanager.zzat;
import com.google.android.gms.tagmanager.zzbs;
import com.google.android.gms.tagmanager.zzdj;
import java.io.File;
import java.util.HashSet;

final class zzax
extends SQLiteOpenHelper {
    private /* synthetic */ zzat zzkfa;

    zzax(zzat zzat2, Context context, String string2) {
        this.zzkfa = zzat2;
        super(context, string2, null, 1);
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
        SQLiteDatabase sQLiteDatabase2 = null;
        try {
            sQLiteDatabase2 = sQLiteDatabase = super.getWritableDatabase();
        }
        catch (SQLiteException sQLiteException) {
            zzat.zzb(this.zzkfa).getDatabasePath("google_tagmanager.db").delete();
        }
        sQLiteDatabase = sQLiteDatabase2;
        if (sQLiteDatabase2 != null) return sQLiteDatabase;
        return super.getWritableDatabase();
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
                if (!zzax.zza("datalayer", sQLiteDatabase)) {
                    sQLiteDatabase.execSQL(zzat.zzbep());
                    return;
                }
                break block11;
                finally {
                    object.close();
                }
            }
            sQLiteDatabase = sQLiteDatabase.rawQuery("SELECT * FROM datalayer WHERE 0", null);
            object = new HashSet();
            String[] arrstring = sQLiteDatabase.getColumnNames();
            for (int i = 0; i < arrstring.length; ++i) {
                object.add(arrstring[i]);
            }
            if (!(object.remove("key") && object.remove("value") && object.remove("ID") && object.remove("expires"))) {
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

