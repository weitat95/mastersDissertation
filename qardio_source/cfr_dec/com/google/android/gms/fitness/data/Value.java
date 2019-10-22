/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.fitness.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzl;
import com.google.android.gms.fitness.data.MapValue;
import com.google.android.gms.fitness.data.zzai;
import com.google.android.gms.fitness.zza;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public final class Value
extends zzbfm {
    public static final Parcelable.Creator<Value> CREATOR = new zzai();
    private final int format;
    private float value;
    private final int versionCode;
    private String zzgcc;
    private boolean zzhea;
    private Map<String, MapValue> zzheb;
    private int[] zzhec;
    private float[] zzhed;
    private byte[] zzhee;

    public Value(int n) {
        this(3, n, false, 0.0f, null, null, null, null, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    Value(int n, int n2, boolean bl, float f, String arrayMap, Bundle bundle, int[] arrn, float[] arrf, byte[] arrby) {
        this.versionCode = n;
        this.format = n2;
        this.zzhea = bl;
        this.value = f;
        this.zzgcc = arrayMap;
        if (bundle == null) {
            arrayMap = null;
        } else {
            bundle.setClassLoader(MapValue.class.getClassLoader());
            arrayMap = new ArrayMap(bundle.size());
            for (String string2 : bundle.keySet()) {
                arrayMap.put(string2, (MapValue)bundle.getParcelable(string2));
            }
        }
        this.zzheb = arrayMap;
        this.zzhec = arrn;
        this.zzhed = arrf;
        this.zzhee = arrby;
    }

    public final String asActivity() {
        return zza.getName(this.asInt());
    }

    /*
     * Enabled aggressive block sorting
     */
    public final float asFloat() {
        boolean bl = this.format == 2;
        zzbq.zza(bl, "Value is not in float format");
        return this.value;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int asInt() {
        boolean bl = true;
        if (this.format != 1) {
            bl = false;
        }
        zzbq.zza(bl, "Value is not in int format");
        return Float.floatToRawIntBits(this.value);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        boolean bl;
        boolean bl2;
        boolean bl3;
        block10: {
            block9: {
                bl = false;
                if (this == object) return true;
                bl3 = bl;
                if (!(object instanceof Value)) return bl3;
                object = (Value)object;
                if (this.format != ((Value)object).format || this.zzhea != ((Value)object).zzhea) break block9;
                switch (this.format) {
                    default: {
                        if (this.value != ((Value)object).value) break;
                        bl2 = true;
                        break block10;
                    }
                    case 1: {
                        bl2 = this.asInt() == ((Value)object).asInt();
                        break block10;
                    }
                    case 2: {
                        bl2 = this.value == ((Value)object).value;
                        break block10;
                    }
                    case 3: {
                        bl2 = zzbg.equal(this.zzgcc, ((Value)object).zzgcc);
                        break block10;
                    }
                    case 4: {
                        bl2 = zzbg.equal(this.zzheb, ((Value)object).zzheb);
                        break block10;
                    }
                    case 5: {
                        bl2 = Arrays.equals(this.zzhec, ((Value)object).zzhec);
                        break block10;
                    }
                    case 6: {
                        bl2 = Arrays.equals(this.zzhed, ((Value)object).zzhed);
                        break block10;
                    }
                    case 7: {
                        bl2 = Arrays.equals(this.zzhee, ((Value)object).zzhee);
                        break block10;
                    }
                }
                bl2 = false;
                break block10;
            }
            bl2 = false;
        }
        bl3 = bl;
        if (!bl2) return bl3;
        return true;
    }

    public final int getFormat() {
        return this.format;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.value), this.zzgcc, this.zzheb, this.zzhec, this.zzhed, this.zzhee});
    }

    public final boolean isSet() {
        return this.zzhea;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void setFloat(float f) {
        boolean bl = this.format == 2;
        zzbq.zza(bl, "Attempting to set an float value to a field that is not in FLOAT format.  Please check the data type definition and use the right format.");
        this.zzhea = true;
        this.value = f;
    }

    public final String toString() {
        if (!this.zzhea) {
            return "unset";
        }
        switch (this.format) {
            default: {
                return "unknown";
            }
            case 1: {
                return Integer.toString(this.asInt());
            }
            case 2: {
                return Float.toString(this.value);
            }
            case 3: {
                return this.zzgcc;
            }
            case 4: {
                return new TreeMap<String, MapValue>(this.zzheb).toString();
            }
            case 5: {
                return Arrays.toString(this.zzhec);
            }
            case 6: {
                return Arrays.toString(this.zzhed);
            }
            case 7: 
        }
        return zzl.zza(this.zzhee, 0, this.zzhee.length, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void writeToParcel(Parcel parcel, int n) {
        Bundle bundle;
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.getFormat());
        zzbfp.zza(parcel, 2, this.isSet());
        zzbfp.zza(parcel, 3, this.value);
        zzbfp.zza(parcel, 4, this.zzgcc, false);
        if (this.zzheb == null) {
            bundle = null;
        } else {
            bundle = new Bundle(this.zzheb.size());
            for (Map.Entry<String, MapValue> entry : this.zzheb.entrySet()) {
                bundle.putParcelable(entry.getKey(), (Parcelable)entry.getValue());
            }
        }
        zzbfp.zza(parcel, 5, bundle, false);
        zzbfp.zza(parcel, 6, this.zzhec, false);
        zzbfp.zza(parcel, 7, this.zzhed, false);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zza(parcel, 8, this.zzhee, false);
        zzbfp.zzai(parcel, n);
    }
}

