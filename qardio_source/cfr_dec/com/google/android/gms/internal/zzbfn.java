/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfo;
import java.util.ArrayList;
import java.util.List;

public final class zzbfn {
    public static int zza(Parcel parcel, int n) {
        if ((n & 0xFFFF0000) != -65536) {
            return n >> 16 & 0xFFFF;
        }
        return parcel.readInt();
    }

    public static <T extends Parcelable> T zza(Parcel parcel, int n, Parcelable.Creator<T> parcelable) {
        n = zzbfn.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        parcelable = (Parcelable)parcelable.createFromParcel(parcel);
        parcel.setDataPosition(n + n2);
        return (T)parcelable;
    }

    private static void zza(Parcel parcel, int n, int n2) {
        if ((n = zzbfn.zza(parcel, n)) != n2) {
            String string2 = Integer.toHexString(n);
            throw new zzbfo(new StringBuilder(String.valueOf(string2).length() + 46).append("Expected size ").append(n2).append(" got ").append(n).append(" (0x").append(string2).append(")").toString(), parcel);
        }
    }

    private static void zza(Parcel parcel, int n, int n2, int n3) {
        if (n2 != n3) {
            String string2 = Integer.toHexString(n2);
            throw new zzbfo(new StringBuilder(String.valueOf(string2).length() + 46).append("Expected size ").append(n3).append(" got ").append(n2).append(" (0x").append(string2).append(")").toString(), parcel);
        }
    }

    public static void zza(Parcel parcel, int n, List list, ClassLoader classLoader) {
        n = zzbfn.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return;
        }
        parcel.readList(list, classLoader);
        parcel.setDataPosition(n + n2);
    }

    public static String[] zzaa(Parcel parcel, int n) {
        n = zzbfn.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        String[] arrstring = parcel.createStringArray();
        parcel.setDataPosition(n + n2);
        return arrstring;
    }

    public static ArrayList<Integer> zzab(Parcel parcel, int n) {
        int n2 = zzbfn.zza(parcel, n);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int n4 = parcel.readInt();
        for (n = 0; n < n4; ++n) {
            arrayList.add(parcel.readInt());
        }
        parcel.setDataPosition(n3 + n2);
        return arrayList;
    }

    public static ArrayList<String> zzac(Parcel parcel, int n) {
        n = zzbfn.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        ArrayList arrayList = parcel.createStringArrayList();
        parcel.setDataPosition(n + n2);
        return arrayList;
    }

    public static void zzaf(Parcel parcel, int n) {
        if (parcel.dataPosition() != n) {
            throw new zzbfo(new StringBuilder(37).append("Overread allowed size end=").append(n).toString(), parcel);
        }
    }

    public static void zzb(Parcel parcel, int n) {
        parcel.setDataPosition(zzbfn.zza(parcel, n) + parcel.dataPosition());
    }

    public static <T> T[] zzb(Parcel parcel, int n, Parcelable.Creator<T> arrobject) {
        n = zzbfn.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        arrobject = parcel.createTypedArray(arrobject);
        parcel.setDataPosition(n + n2);
        return arrobject;
    }

    public static <T> ArrayList<T> zzc(Parcel parcel, int n, Parcelable.Creator<T> object) {
        n = zzbfn.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        object = parcel.createTypedArrayList(object);
        parcel.setDataPosition(n + n2);
        return object;
    }

    public static boolean zzc(Parcel parcel, int n) {
        zzbfn.zza(parcel, n, 4);
        return parcel.readInt() != 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int zzd(Parcel parcel) {
        int n = parcel.readInt();
        int n2 = zzbfn.zza(parcel, n);
        int n3 = parcel.dataPosition();
        if ((0xFFFF & n) != 20293) {
            String string2 = String.valueOf(Integer.toHexString(n));
            if (string2.length() != 0) {
                string2 = "Expected object header. Got 0x".concat(string2);
                do {
                    throw new zzbfo(string2, parcel);
                    break;
                } while (true);
            }
            string2 = new String("Expected object header. Got 0x");
            throw new zzbfo(string2, parcel);
        }
        n = n3 + n2;
        if (n >= n3 && n <= parcel.dataSize()) return n;
        throw new zzbfo(new StringBuilder(54).append("Size read is invalid start=").append(n3).append(" end=").append(n).toString(), parcel);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Boolean zzd(Parcel parcel, int n) {
        boolean bl;
        int n2 = zzbfn.zza(parcel, n);
        if (n2 == 0) {
            return null;
        }
        zzbfn.zza(parcel, n, n2, 4);
        if (parcel.readInt() != 0) {
            bl = true;
            do {
                return bl;
                break;
            } while (true);
        }
        bl = false;
        return bl;
    }

    public static byte zze(Parcel parcel, int n) {
        zzbfn.zza(parcel, n, 4);
        return (byte)parcel.readInt();
    }

    public static int zzg(Parcel parcel, int n) {
        zzbfn.zza(parcel, n, 4);
        return parcel.readInt();
    }

    public static Integer zzh(Parcel parcel, int n) {
        int n2 = zzbfn.zza(parcel, n);
        if (n2 == 0) {
            return null;
        }
        zzbfn.zza(parcel, n, n2, 4);
        return parcel.readInt();
    }

    public static long zzi(Parcel parcel, int n) {
        zzbfn.zza(parcel, n, 8);
        return parcel.readLong();
    }

    public static Long zzj(Parcel parcel, int n) {
        int n2 = zzbfn.zza(parcel, n);
        if (n2 == 0) {
            return null;
        }
        zzbfn.zza(parcel, n, n2, 8);
        return parcel.readLong();
    }

    public static float zzl(Parcel parcel, int n) {
        zzbfn.zza(parcel, n, 4);
        return parcel.readFloat();
    }

    public static Float zzm(Parcel parcel, int n) {
        int n2 = zzbfn.zza(parcel, n);
        if (n2 == 0) {
            return null;
        }
        zzbfn.zza(parcel, n, n2, 4);
        return Float.valueOf(parcel.readFloat());
    }

    public static double zzn(Parcel parcel, int n) {
        zzbfn.zza(parcel, n, 8);
        return parcel.readDouble();
    }

    public static Double zzo(Parcel parcel, int n) {
        int n2 = zzbfn.zza(parcel, n);
        if (n2 == 0) {
            return null;
        }
        zzbfn.zza(parcel, n, n2, 8);
        return parcel.readDouble();
    }

    public static String zzq(Parcel parcel, int n) {
        n = zzbfn.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        String string2 = parcel.readString();
        parcel.setDataPosition(n + n2);
        return string2;
    }

    public static IBinder zzr(Parcel parcel, int n) {
        n = zzbfn.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        IBinder iBinder = parcel.readStrongBinder();
        parcel.setDataPosition(n + n2);
        return iBinder;
    }

    public static Bundle zzs(Parcel parcel, int n) {
        n = zzbfn.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        Bundle bundle = parcel.readBundle();
        parcel.setDataPosition(n + n2);
        return bundle;
    }

    public static byte[] zzt(Parcel parcel, int n) {
        n = zzbfn.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        byte[] arrby = parcel.createByteArray();
        parcel.setDataPosition(n + n2);
        return arrby;
    }

    public static int[] zzw(Parcel parcel, int n) {
        n = zzbfn.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        int[] arrn = parcel.createIntArray();
        parcel.setDataPosition(n + n2);
        return arrn;
    }

    public static float[] zzy(Parcel parcel, int n) {
        n = zzbfn.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        float[] arrf = parcel.createFloatArray();
        parcel.setDataPosition(n + n2);
        return arrf;
    }
}

