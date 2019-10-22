/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.database.CursorIndexOutOfBoundsException
 *  android.database.CursorWindow
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Log
 */
package com.google.android.gms.common.data;

import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.data.zze;
import com.google.android.gms.common.data.zzf;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@KeepName
public final class DataHolder
extends zzbfm
implements Closeable {
    public static final Parcelable.Creator<DataHolder> CREATOR = new zzf();
    private static final zza zzfwi = new zze(new String[0], null);
    private boolean mClosed = false;
    private final int zzcd;
    private int zzeck;
    private final String[] zzfwb;
    private Bundle zzfwc;
    private final CursorWindow[] zzfwd;
    private final Bundle zzfwe;
    private int[] zzfwf;
    int zzfwg;
    private boolean zzfwh = true;

    DataHolder(int n, String[] arrstring, CursorWindow[] arrcursorWindow, int n2, Bundle bundle) {
        this.zzeck = n;
        this.zzfwb = arrstring;
        this.zzfwd = arrcursorWindow;
        this.zzcd = n2;
        this.zzfwe = bundle;
    }

    private DataHolder(zza zza2, int n, Bundle bundle) {
        this(zza2.zzfwb, DataHolder.zza(zza2, -1), n, null);
    }

    private DataHolder(String[] arrstring, CursorWindow[] arrcursorWindow, int n, Bundle bundle) {
        this.zzeck = 1;
        this.zzfwb = zzbq.checkNotNull(arrstring);
        this.zzfwd = zzbq.checkNotNull(arrcursorWindow);
        this.zzcd = n;
        this.zzfwe = bundle;
        this.zzajz();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static CursorWindow[] zza(zza var0, int var1_2) {
        block26: {
            var4_3 = 0;
            if (zza.zza((zza)var0).length == 0) {
                return new CursorWindow[0];
            }
            var12_4 = zza.zzb((zza)var0);
            var5_5 = var12_4.size();
            var10_6 /* !! */  = new CursorWindow(false);
            var11_7 = new ArrayList<CursorWindow>();
            var11_7.add(var10_6 /* !! */ );
            var10_6 /* !! */ .setNumColumns(zza.zza((zza)var0).length);
            var1_2 = 0;
            var2_8 = 0;
            while (var1_2 < var5_5) {
                block28: {
                    block27: {
                        var9_12 = var10_6 /* !! */ ;
                        try {
                            if (!var10_6 /* !! */ .allocRow()) {
                                Log.d((String)"DataHolder", (String)new StringBuilder(72).append("Allocating additional cursor window for large data set (row ").append(var1_2).append(")").toString());
                                var10_6 /* !! */  = new CursorWindow(false);
                                var10_6 /* !! */ .setStartPosition(var1_2);
                                var10_6 /* !! */ .setNumColumns(zza.zza((zza)var0).length);
                                var11_7.add(var10_6 /* !! */ );
                                var9_12 = var10_6 /* !! */ ;
                                if (!var10_6 /* !! */ .allocRow()) {
                                    Log.e((String)"DataHolder", (String)"Unable to allocate row to hold data.");
                                    var11_7.remove((Object)var10_6 /* !! */ );
                                    return var11_7.toArray((T[])new CursorWindow[var11_7.size()]);
                                }
                            }
                            var13_13 = (Map)var12_4.get(var1_2);
                            var3_9 = 0;
                            var6_10 = true;
lbl36:
                            // 2 sources
                            do {
                                if (var3_9 < zza.zza((zza)var0).length && var6_10) {
                                    var10_6 /* !! */  = zza.zza((zza)var0)[var3_9];
                                    var14_14 = var13_13.get((Object)var10_6 /* !! */ );
                                    if (var14_14 == null) {
                                        var6_10 = var9_12.putNull(var1_2, var3_9);
                                    } else if (var14_14 instanceof String) {
                                        var6_10 = var9_12.putString((String)var14_14, var1_2, var3_9);
                                    } else if (var14_14 instanceof Long) {
                                        var6_10 = var9_12.putLong(((Long)var14_14).longValue(), var1_2, var3_9);
                                    } else if (var14_14 instanceof Integer) {
                                        var6_10 = var9_12.putLong((long)((Integer)var14_14).intValue(), var1_2, var3_9);
                                    } else if (var14_14 instanceof Boolean) {
                                        var7_11 = (Boolean)var14_14 != false ? 1L : 0L;
                                        var6_10 = var9_12.putLong(var7_11, var1_2, var3_9);
                                    } else if (var14_14 instanceof byte[]) {
                                        var6_10 = var9_12.putBlob((byte[])var14_14, var1_2, var3_9);
                                    } else if (var14_14 instanceof Double) {
                                        var6_10 = var9_12.putDouble(((Double)var14_14).doubleValue(), var1_2, var3_9);
                                    } else {
                                        if (!(var14_14 instanceof Float)) {
                                            var0 = String.valueOf(var14_14);
                                            throw new IllegalArgumentException(new StringBuilder(String.valueOf((Object)var10_6 /* !! */ ).length() + 32 + String.valueOf(var0).length()).append("Unsupported object for column ").append((String)var10_6 /* !! */ ).append(": ").append((String)var0).toString());
                                        }
                                        var6_10 = var9_12.putDouble((double)((Float)var14_14).floatValue(), var1_2, var3_9);
                                    }
                                    break block26;
                                }
                                if (var6_10) break block27;
                                if (var2_8 != 0) {
                                    throw new zzb("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
                                }
                                break;
                            } while (true);
                        }
                        catch (RuntimeException var0_1) {
                            var2_8 = var11_7.size();
                            var1_2 = var4_3;
                            while (var1_2 < var2_8) {
                                ((CursorWindow)var11_7.get(var1_2)).close();
                                ++var1_2;
                            }
                            throw var0_1;
                        }
                        Log.d((String)"DataHolder", (String)new StringBuilder(74).append("Couldn't populate window data for row ").append(var1_2).append(" - allocating new window.").toString());
                        var9_12.freeLastRow();
                        var9_12 = new CursorWindow(false);
                        var9_12.setStartPosition(var1_2);
                        var9_12.setNumColumns(zza.zza((zza)var0).length);
                        var11_7.add(var9_12);
                        var2_8 = var1_2 - 1;
                        var1_2 = 1;
                        break block28;
                    }
                    var3_9 = 0;
                    var2_8 = var1_2;
                    var1_2 = var3_9;
                }
                var3_9 = var1_2;
                var1_2 = var2_8 + 1;
                var10_6 /* !! */  = var9_12;
                var2_8 = var3_9;
            }
            return var11_7.toArray((T[])new CursorWindow[var11_7.size()]);
        }
        ++var3_9;
        ** while (true)
    }

    public static DataHolder zzca(int n) {
        return new DataHolder(zzfwi, n, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final void zzh(String string2, int n) {
        if (this.zzfwc == null || !this.zzfwc.containsKey(string2)) {
            if ((string2 = String.valueOf(string2)).length() != 0) {
                string2 = "No such column: ".concat(string2);
                do {
                    throw new IllegalArgumentException(string2);
                    break;
                } while (true);
            }
            string2 = new String("No such column: ");
            throw new IllegalArgumentException(string2);
        }
        if (this.isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        }
        if (n >= 0 && n < this.zzfwg) return;
        throw new CursorIndexOutOfBoundsException(n, this.zzfwg);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void close() {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                for (int i = 0; i < this.zzfwd.length; ++i) {
                    this.zzfwd[i].close();
                }
            }
            return;
        }
    }

    protected final void finalize() throws Throwable {
        try {
            if (this.zzfwh && this.zzfwd.length > 0 && !this.isClosed()) {
                this.close();
                String string2 = this.toString();
                Log.e((String)"DataBuffer", (String)new StringBuilder(String.valueOf(string2).length() + 178).append("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (internal object: ").append(string2).append(")").toString());
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    public final int getCount() {
        return this.zzfwg;
    }

    public final int getStatusCode() {
        return this.zzcd;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean isClosed() {
        synchronized (this) {
            return this.mClosed;
        }
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzfwb, false);
        zzbfp.zza((Parcel)parcel, (int)2, (Parcelable[])this.zzfwd, (int)n, (boolean)false);
        zzbfp.zzc(parcel, 3, this.zzcd);
        zzbfp.zza(parcel, 4, this.zzfwe, false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, n2);
        if ((n & 1) != 0) {
            this.close();
        }
    }

    public final void zzajz() {
        int n;
        int n2 = 0;
        this.zzfwc = new Bundle();
        for (n = 0; n < this.zzfwb.length; ++n) {
            this.zzfwc.putInt(this.zzfwb[n], n);
        }
        this.zzfwf = new int[this.zzfwd.length];
        int n3 = 0;
        n = n2;
        n2 = n3;
        while (n < this.zzfwd.length) {
            this.zzfwf[n] = n2;
            n3 = this.zzfwd[n].getStartPosition();
            n2 += this.zzfwd[n].getNumRows() - (n2 - n3);
            ++n;
        }
        this.zzfwg = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int zzbz(int n) {
        int n2;
        block1: {
            int n3 = 0;
            boolean bl = n >= 0 && n < this.zzfwg;
            zzbq.checkState(bl);
            do {
                n2 = ++n3;
                if (n3 >= this.zzfwf.length) break block1;
            } while (n >= this.zzfwf[n3]);
            n2 = n3 - 1;
        }
        n = n2;
        if (n2 != this.zzfwf.length) return n;
        return n2 - 1;
    }

    public final int zzc(String string2, int n, int n2) {
        this.zzh(string2, n);
        return this.zzfwd[n2].getInt(n, this.zzfwc.getInt(string2));
    }

    public final String zzd(String string2, int n, int n2) {
        this.zzh(string2, n);
        return this.zzfwd[n2].getString(n, this.zzfwc.getInt(string2));
    }

    public final byte[] zzg(String string2, int n, int n2) {
        this.zzh(string2, n);
        return this.zzfwd[n2].getBlob(n, this.zzfwc.getInt(string2));
    }

    public static class zza {
        private final String[] zzfwb;
        private final ArrayList<HashMap<String, Object>> zzfwj;
        private final String zzfwk;
        private final HashMap<Object, Integer> zzfwl;
        private boolean zzfwm;
        private String zzfwn;

        private zza(String[] arrstring, String string2) {
            this.zzfwb = zzbq.checkNotNull(arrstring);
            this.zzfwj = new ArrayList();
            this.zzfwk = string2;
            this.zzfwl = new HashMap();
            this.zzfwm = false;
            this.zzfwn = null;
        }

        /* synthetic */ zza(String[] arrstring, String string2, zze zze2) {
            this(arrstring, null);
        }

        static /* synthetic */ ArrayList zzb(zza zza2) {
            return zza2.zzfwj;
        }
    }

    public static final class zzb
    extends RuntimeException {
        public zzb(String string2) {
            super(string2);
        }
    }

}

