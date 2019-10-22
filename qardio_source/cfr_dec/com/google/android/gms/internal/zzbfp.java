/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public final class zzbfp {
    public static void zza(Parcel parcel, int n, byte by) {
        zzbfp.zzb(parcel, n, 4);
        parcel.writeInt((int)by);
    }

    public static void zza(Parcel parcel, int n, double d) {
        zzbfp.zzb(parcel, n, 8);
        parcel.writeDouble(d);
    }

    public static void zza(Parcel parcel, int n, float f) {
        zzbfp.zzb(parcel, n, 4);
        parcel.writeFloat(f);
    }

    public static void zza(Parcel parcel, int n, long l) {
        zzbfp.zzb(parcel, n, 8);
        parcel.writeLong(l);
    }

    public static void zza(Parcel parcel, int n, Bundle bundle, boolean bl) {
        if (bundle == null) {
            return;
        }
        n = zzbfp.zzag(parcel, n);
        parcel.writeBundle(bundle);
        zzbfp.zzah(parcel, n);
    }

    public static void zza(Parcel parcel, int n, IBinder iBinder, boolean bl) {
        if (iBinder == null) {
            return;
        }
        n = zzbfp.zzag(parcel, n);
        parcel.writeStrongBinder(iBinder);
        zzbfp.zzah(parcel, n);
    }

    public static void zza(Parcel parcel, int n, Parcelable parcelable, int n2, boolean bl) {
        if (parcelable == null) {
            if (bl) {
                zzbfp.zzb(parcel, n, 0);
            }
            return;
        }
        n = zzbfp.zzag(parcel, n);
        parcelable.writeToParcel(parcel, n2);
        zzbfp.zzah(parcel, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void zza(Parcel parcel, int n, Boolean bl, boolean bl2) {
        if (bl == null) {
            return;
        }
        zzbfp.zzb(parcel, 3, 4);
        n = bl != false ? 1 : 0;
        parcel.writeInt(n);
    }

    public static void zza(Parcel parcel, int n, Double d, boolean bl) {
        if (d == null) {
            return;
        }
        zzbfp.zzb(parcel, n, 8);
        parcel.writeDouble(d.doubleValue());
    }

    public static void zza(Parcel parcel, int n, Float f, boolean bl) {
        if (f == null) {
            return;
        }
        zzbfp.zzb(parcel, n, 4);
        parcel.writeFloat(f.floatValue());
    }

    public static void zza(Parcel parcel, int n, Integer n2, boolean bl) {
        if (n2 == null) {
            return;
        }
        zzbfp.zzb(parcel, n, 4);
        parcel.writeInt(n2.intValue());
    }

    public static void zza(Parcel parcel, int n, Long l, boolean bl) {
        if (l == null) {
            return;
        }
        zzbfp.zzb(parcel, n, 8);
        parcel.writeLong(l.longValue());
    }

    public static void zza(Parcel parcel, int n, String string2, boolean bl) {
        if (string2 == null) {
            if (bl) {
                zzbfp.zzb(parcel, n, 0);
            }
            return;
        }
        n = zzbfp.zzag(parcel, n);
        parcel.writeString(string2);
        zzbfp.zzah(parcel, n);
    }

    public static void zza(Parcel parcel, int n, List<Integer> list, boolean bl) {
        if (list == null) {
            return;
        }
        int n2 = zzbfp.zzag(parcel, n);
        int n3 = list.size();
        parcel.writeInt(n3);
        for (n = 0; n < n3; ++n) {
            parcel.writeInt(list.get(n).intValue());
        }
        zzbfp.zzah(parcel, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void zza(Parcel parcel, int n, boolean bl) {
        zzbfp.zzb(parcel, n, 4);
        n = bl ? 1 : 0;
        parcel.writeInt(n);
    }

    public static void zza(Parcel parcel, int n, byte[] arrby, boolean bl) {
        if (arrby == null) {
            if (bl) {
                zzbfp.zzb(parcel, n, 0);
            }
            return;
        }
        n = zzbfp.zzag(parcel, n);
        parcel.writeByteArray(arrby);
        zzbfp.zzah(parcel, n);
    }

    public static void zza(Parcel parcel, int n, float[] arrf, boolean bl) {
        if (arrf == null) {
            return;
        }
        n = zzbfp.zzag(parcel, 7);
        parcel.writeFloatArray(arrf);
        zzbfp.zzah(parcel, n);
    }

    public static void zza(Parcel parcel, int n, int[] arrn, boolean bl) {
        if (arrn == null) {
            return;
        }
        n = zzbfp.zzag(parcel, n);
        parcel.writeIntArray(arrn);
        zzbfp.zzah(parcel, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static <T extends Parcelable> void zza(Parcel parcel, int n, T[] arrT, int n2, boolean bl) {
        if (arrT == null) {
            return;
        }
        int n3 = zzbfp.zzag(parcel, n);
        int n4 = arrT.length;
        parcel.writeInt(n4);
        n = 0;
        do {
            if (n >= n4) {
                zzbfp.zzah(parcel, n3);
                return;
            }
            T t = arrT[n];
            if (t == null) {
                parcel.writeInt(0);
            } else {
                zzbfp.zza(parcel, t, n2);
            }
            ++n;
        } while (true);
    }

    public static void zza(Parcel parcel, int n, String[] arrstring, boolean bl) {
        if (arrstring == null) {
            return;
        }
        n = zzbfp.zzag(parcel, n);
        parcel.writeStringArray(arrstring);
        zzbfp.zzah(parcel, n);
    }

    private static <T extends Parcelable> void zza(Parcel parcel, T t, int n) {
        int n2 = parcel.dataPosition();
        parcel.writeInt(1);
        int n3 = parcel.dataPosition();
        t.writeToParcel(parcel, n);
        n = parcel.dataPosition();
        parcel.setDataPosition(n2);
        parcel.writeInt(n - n3);
        parcel.setDataPosition(n);
    }

    private static int zzag(Parcel parcel, int n) {
        parcel.writeInt(0xFFFF0000 | n);
        parcel.writeInt(0);
        return parcel.dataPosition();
    }

    private static void zzah(Parcel parcel, int n) {
        int n2 = parcel.dataPosition();
        parcel.setDataPosition(n - 4);
        parcel.writeInt(n2 - n);
        parcel.setDataPosition(n2);
    }

    public static void zzai(Parcel parcel, int n) {
        zzbfp.zzah(parcel, n);
    }

    private static void zzb(Parcel parcel, int n, int n2) {
        if (n2 >= 65535) {
            parcel.writeInt(0xFFFF0000 | n);
            parcel.writeInt(n2);
            return;
        }
        parcel.writeInt(n2 << 16 | n);
    }

    public static void zzb(Parcel parcel, int n, List<String> list, boolean bl) {
        if (list == null) {
            return;
        }
        n = zzbfp.zzag(parcel, n);
        parcel.writeStringList(list);
        zzbfp.zzah(parcel, n);
    }

    public static void zzc(Parcel parcel, int n, int n2) {
        zzbfp.zzb(parcel, n, 4);
        parcel.writeInt(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static <T extends Parcelable> void zzc(Parcel parcel, int n, List<T> list, boolean bl) {
        if (list == null) {
            if (bl) {
                zzbfp.zzb(parcel, n, 0);
            }
            return;
        }
        int n2 = zzbfp.zzag(parcel, n);
        int n3 = list.size();
        parcel.writeInt(n3);
        n = 0;
        do {
            if (n >= n3) {
                zzbfp.zzah(parcel, n2);
                return;
            }
            Parcelable parcelable = (Parcelable)list.get(n);
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                zzbfp.zza(parcel, parcelable, 0);
            }
            ++n;
        } while (true);
    }

    public static void zzd(Parcel parcel, int n, List list, boolean bl) {
        if (list == null) {
            return;
        }
        n = zzbfp.zzag(parcel, n);
        parcel.writeList(list);
        zzbfp.zzah(parcel, n);
    }

    public static int zze(Parcel parcel) {
        return zzbfp.zzag(parcel, 20293);
    }
}

