/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.getqardio.android.utils;

import android.os.Parcel;

public class ParcelHelper {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Boolean readBool(Parcel parcel, Boolean bl) {
        boolean bl2 = true;
        if (parcel.readByte() != 1) return bl;
        if (parcel.readByte() == 1) {
            do {
                return bl2;
                break;
            } while (true);
        }
        bl2 = false;
        return bl2;
    }

    public static Double readDouble(Parcel parcel, Double d) {
        if (parcel.readByte() == 1) {
            d = parcel.readDouble();
        }
        return d;
    }

    public static Float readFloat(Parcel parcel, Float f) {
        if (parcel.readByte() == 1) {
            f = Float.valueOf(parcel.readFloat());
        }
        return f;
    }

    public static Integer readInt(Parcel parcel, Integer n) {
        if (parcel.readByte() == 1) {
            n = parcel.readInt();
        }
        return n;
    }

    public static Long readLong(Parcel parcel, Long l) {
        if (parcel.readByte() == 1) {
            l = parcel.readLong();
        }
        return l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void writeBool(Parcel parcel, Boolean bl) {
        byte by = 1;
        if (bl == null) {
            parcel.writeByte((byte)0);
            return;
        }
        parcel.writeByte((byte)1);
        if (!bl.booleanValue()) {
            by = 0;
        }
        parcel.writeByte(by);
    }

    public static void writeDouble(Parcel parcel, Double d) {
        if (d != null) {
            parcel.writeByte((byte)1);
            parcel.writeDouble(d.doubleValue());
            return;
        }
        parcel.writeByte((byte)0);
    }

    public static void writeFloat(Parcel parcel, Float f) {
        if (f != null) {
            parcel.writeByte((byte)1);
            parcel.writeFloat(f.floatValue());
            return;
        }
        parcel.writeByte((byte)0);
    }

    public static void writeInt(Parcel parcel, Integer n) {
        if (n != null) {
            parcel.writeByte((byte)1);
            parcel.writeInt(n.intValue());
            return;
        }
        parcel.writeByte((byte)0);
    }

    public static void writeLong(Parcel parcel, Long l) {
        if (l != null) {
            parcel.writeByte((byte)1);
            parcel.writeLong(l.longValue());
            return;
        }
        parcel.writeByte((byte)0);
    }
}

