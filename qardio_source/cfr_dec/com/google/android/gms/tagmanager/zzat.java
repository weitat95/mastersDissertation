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
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.zzaq;
import com.google.android.gms.tagmanager.zzau;
import com.google.android.gms.tagmanager.zzav;
import com.google.android.gms.tagmanager.zzax;
import com.google.android.gms.tagmanager.zzay;
import com.google.android.gms.tagmanager.zzdj;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

final class zzat
implements DataLayer.zzc {
    private static final String zzkeu = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' STRING NOT NULL, '%s' BLOB NOT NULL, '%s' INTEGER NOT NULL);", "datalayer", "ID", "key", "value", "expires");
    private final Context mContext;
    private zzd zzata;
    private final Executor zzkev;
    private zzax zzkew;
    private int zzkex;

    public zzat(Context context) {
        this(context, zzh.zzamg(), "google_tagmanager.db", 2000, Executors.newSingleThreadExecutor());
    }

    private zzat(Context context, zzd zzd2, String string2, int n, Executor executor) {
        this.mContext = context;
        this.zzata = zzd2;
        this.zzkex = 2000;
        this.zzkev = executor;
        this.zzkew = new zzax(this, this.mContext, string2);
    }

    static /* synthetic */ List zza(zzat zzat2) {
        return zzat2.zzbel();
    }

    static /* synthetic */ void zza(zzat zzat2, List list, long l) {
        zzat2.zzb(list, l);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static byte[] zzaj(Object var0) {
        var2_7 = new ByteArrayOutputStream();
        var1_8 = new ObjectOutputStream(var2_7);
        var1_8.writeObject(var0);
        var0 = var2_7.toByteArray();
        try {
            var1_8.close();
            var2_7.close();
            return var0;
        }
        catch (IOException var1_10) {
            return var0;
        }
        catch (IOException var0_1) {
            block12: {
                var1_8 = null;
                break block12;
                catch (Throwable var0_3) {
                    block13: {
                        var1_8 = null;
                        break block13;
                        catch (Throwable var0_5) {}
                    }
                    if (var1_8 == null) ** GOTO lbl24
                    try {
                        var1_8.close();
lbl24:
                        // 2 sources
                        var2_7.close();
                    }
                    catch (IOException var1_9) {
                        throw var0_4;
                    }
                    throw var0_4;
                }
                catch (IOException var0_6) {}
            }
            if (var1_8 == null) ** GOTO lbl33
            try {
                var1_8.close();
lbl33:
                // 2 sources
                var2_7.close();
                return null;
            }
            catch (IOException var0_2) {
                return null;
            }
        }
    }

    static /* synthetic */ Context zzb(zzat zzat2) {
        return zzat2.mContext;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzb(List<zzay> list, long l) {
        synchronized (this) {
            try {
                long l2 = this.zzata.currentTimeMillis();
                this.zzbh(l2);
                int n = list.size() + (this.zzben() - this.zzkex);
                if (n > 0) {
                    SQLiteDatabase sQLiteDatabase;
                    Object object = this.zzek(n);
                    n = object.size();
                    zzdj.zzct(new StringBuilder(64).append("DataLayer store full, deleting ").append(n).append(" entries to make room.").toString());
                    object = object.toArray(new String[0]);
                    if (object != null && ((String[])object).length != 0 && (sQLiteDatabase = this.zzlk("Error opening database for deleteEntries.")) != null) {
                        String string2 = String.format("%s in (%s)", "ID", TextUtils.join((CharSequence)",", Collections.nCopies(((String[])object).length, "?")));
                        try {
                            sQLiteDatabase.delete("datalayer", string2, (String[])object);
                        }
                        catch (SQLiteException sQLiteException) {
                            object = String.valueOf(Arrays.toString((Object[])object));
                            object = ((String)object).length() != 0 ? "Error deleting entries ".concat((String)object) : new String("Error deleting entries ");
                            zzdj.zzcu((String)object);
                        }
                    }
                }
                this.zzc(list, l2 + l);
                return;
            }
            finally {
                this.zzbeo();
            }
        }
    }

    private final List<DataLayer.zza> zzbel() {
        ArrayList<DataLayer.zza> arrayList;
        try {
            this.zzbh(this.zzata.currentTimeMillis());
            Object object = this.zzbem();
            arrayList = new ArrayList<DataLayer.zza>();
            object = object.iterator();
            while (object.hasNext()) {
                zzay zzay2 = (zzay)object.next();
                arrayList.add(new DataLayer.zza(zzay2.zzbhb, zzat.zzv(zzay2.zzkfd)));
            }
        }
        finally {
            this.zzbeo();
        }
        return arrayList;
    }

    private final List<zzay> zzbem() {
        SQLiteDatabase sQLiteDatabase = this.zzlk("Error opening database for loadSerialized.");
        ArrayList<zzay> arrayList = new ArrayList<zzay>();
        if (sQLiteDatabase == null) {
            return arrayList;
        }
        sQLiteDatabase = sQLiteDatabase.query("datalayer", new String[]{"key", "value"}, null, null, null, null, "ID", null);
        try {
            while (sQLiteDatabase.moveToNext()) {
                arrayList.add(new zzay(sQLiteDatabase.getString(0), sQLiteDatabase.getBlob(1)));
            }
        }
        finally {
            sQLiteDatabase.close();
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final int zzben() {
        int n;
        SQLiteDatabase sQLiteDatabase;
        int n2;
        block6: {
            long l;
            SQLiteDatabase sQLiteDatabase2 = null;
            SQLiteDatabase sQLiteDatabase3 = null;
            n = 0;
            n2 = 0;
            sQLiteDatabase = this.zzlk("Error opening database for getNumStoredEntries.");
            if (sQLiteDatabase == null) return n2;
            try {
                sQLiteDatabase3 = sQLiteDatabase = sQLiteDatabase.rawQuery("SELECT COUNT(*) from datalayer", null);
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
                        zzdj.zzcu("Error getting numStoredEntries");
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

    private final void zzbeo() {
        try {
            this.zzkew.close();
            return;
        }
        catch (SQLiteException sQLiteException) {
            return;
        }
    }

    static /* synthetic */ String zzbep() {
        return zzkeu;
    }

    private final void zzbh(long l) {
        SQLiteDatabase sQLiteDatabase = this.zzlk("Error opening database for deleteOlderThan.");
        if (sQLiteDatabase == null) {
            return;
        }
        try {
            int n = sQLiteDatabase.delete("datalayer", "expires <= ?", new String[]{Long.toString(l)});
            zzdj.v(new StringBuilder(33).append("Deleted ").append(n).append(" expired items").toString());
            return;
        }
        catch (SQLiteException sQLiteException) {
            zzdj.zzcu("Error deleting old entries.");
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzc(List<zzay> iterator, long l) {
        SQLiteDatabase sQLiteDatabase = this.zzlk("Error opening database for writeEntryToDatabase.");
        if (sQLiteDatabase != null) {
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                zzay zzay2 = (zzay)iterator.next();
                ContentValues contentValues = new ContentValues();
                contentValues.put("expires", Long.valueOf(l));
                contentValues.put("key", zzay2.zzbhb);
                contentValues.put("value", zzay2.zzkfd);
                sQLiteDatabase.insert("datalayer", null, contentValues);
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final List<String> zzek(int var1_1) {
        var6_2 = new ArrayList<String>();
        if (var1_1 <= 0) {
            zzdj.zzcu("Invalid maxEntries specified. Skipping.");
            return var6_2;
        }
        var3_3 = this.zzlk("Error opening database for peekEntryIds.");
        if (var3_3 == null) {
            return var6_2;
        }
        var4_18 = String.format("%s ASC", new Object[]{"ID"});
        var5_19 = Integer.toString(var1_1);
        var3_4 = var4_18 = var3_3.query("datalayer", new String[]{"ID"}, null, null, null, null, var4_18, var5_19);
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
                        var5_19 = "Error in peekEntries fetching entryIds: ".concat(var5_19);
                    } else {
                        var3_13 = var4_18;
                        var5_19 = new String("Error in peekEntries fetching entryIds: ");
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

    private final SQLiteDatabase zzlk(String string2) {
        try {
            SQLiteDatabase sQLiteDatabase = this.zzkew.getWritableDatabase();
            return sQLiteDatabase;
        }
        catch (SQLiteException sQLiteException) {
            zzdj.zzcu(string2);
            return null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Object zzv(byte[] var0) {
        var2_7 = new ByteArrayInputStream((byte[])var0);
        var0 = new ObjectInputStream(var2_7);
        var1_8 = var0.readObject();
        try {
            var0.close();
            var2_7.close();
            return var1_8;
        }
        catch (IOException var0_6) {
            return var1_8;
        }
        catch (IOException var0_1) {
            block16: {
                var0 = null;
                break block16;
                catch (ClassNotFoundException var0_3) {
                    block17: {
                        var0 = null;
                        break block17;
                        catch (Throwable var1_9) {
                            block18: {
                                var0 = null;
                                break block18;
                                catch (Throwable var1_11) {}
                            }
                            if (var0 == null) ** GOTO lbl26
                            try {
                                var0.close();
lbl26:
                                // 2 sources
                                var2_7.close();
                            }
                            catch (IOException var0_5) {
                                throw var1_10;
                            }
                            throw var1_10;
                        }
                        catch (ClassNotFoundException var1_12) {}
                    }
                    if (var0 == null) ** GOTO lbl35
                    try {
                        var0.close();
lbl35:
                        // 2 sources
                        var2_7.close();
                        return null;
                    }
                    catch (IOException var0_4) {
                        return null;
                    }
                }
                catch (IOException var1_13) {}
            }
            if (var0 == null) ** GOTO lbl44
            try {
                var0.close();
lbl44:
                // 2 sources
                var2_7.close();
                return null;
            }
            catch (IOException var0_2) {
                return null;
            }
        }
    }

    @Override
    public final void zza(zzaq zzaq2) {
        this.zzkev.execute(new zzav(this, zzaq2));
    }

    @Override
    public final void zza(List<DataLayer.zza> object, long l) {
        ArrayList<zzay> arrayList = new ArrayList<zzay>();
        object = object.iterator();
        while (object.hasNext()) {
            DataLayer.zza zza2 = (DataLayer.zza)object.next();
            arrayList.add(new zzay(zza2.zzbhb, zzat.zzaj(zza2.mValue)));
        }
        this.zzkev.execute(new zzau(this, arrayList, l));
    }
}

