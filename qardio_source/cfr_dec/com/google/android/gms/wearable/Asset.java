/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable;

import android.net.Uri;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wearable.zze;
import java.util.Arrays;

public class Asset
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<Asset> CREATOR = new zze();
    private Uri uri;
    private byte[] zzhyw;
    private String zzlgk;
    private ParcelFileDescriptor zzlgl;

    Asset(byte[] arrby, String string2, ParcelFileDescriptor parcelFileDescriptor, Uri uri) {
        this.zzhyw = arrby;
        this.zzlgk = string2;
        this.zzlgl = parcelFileDescriptor;
        this.uri = uri;
    }

    public static Asset createFromFd(ParcelFileDescriptor parcelFileDescriptor) {
        zzc.zzv((Object)parcelFileDescriptor);
        return new Asset(null, null, parcelFileDescriptor, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof Asset)) {
                    return false;
                }
                object = (Asset)object;
                if (!Arrays.equals(this.zzhyw, ((Asset)object).zzhyw) || !zzbg.equal(this.zzlgk, ((Asset)object).zzlgk) || !zzbg.equal((Object)this.zzlgl, (Object)((Asset)object).zzlgl) || !zzbg.equal((Object)this.uri, (Object)((Asset)object).uri)) break block5;
            }
            return true;
        }
        return false;
    }

    public final byte[] getData() {
        return this.zzhyw;
    }

    public String getDigest() {
        return this.zzlgk;
    }

    public ParcelFileDescriptor getFd() {
        return this.zzlgl;
    }

    public Uri getUri() {
        return this.uri;
    }

    public int hashCode() {
        return Arrays.deepHashCode(new Object[]{this.zzhyw, this.zzlgk, this.zzlgl, this.uri});
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Asset[@");
        stringBuilder.append(Integer.toHexString(this.hashCode()));
        if (this.zzlgk == null) {
            stringBuilder.append(", nodigest");
        } else {
            stringBuilder.append(", ");
            stringBuilder.append(this.zzlgk);
        }
        if (this.zzhyw != null) {
            stringBuilder.append(", size=");
            stringBuilder.append(this.zzhyw.length);
        }
        if (this.zzlgl != null) {
            stringBuilder.append(", fd=");
            stringBuilder.append((Object)this.zzlgl);
        }
        if (this.uri != null) {
            stringBuilder.append(", uri=");
            stringBuilder.append((Object)this.uri);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzc.zzv((Object)parcel);
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzhyw, false);
        zzbfp.zza(parcel, 3, this.getDigest(), false);
        zzbfp.zza(parcel, 4, (Parcelable)this.zzlgl, n |= 1, false);
        zzbfp.zza(parcel, 5, (Parcelable)this.uri, n, false);
        zzbfp.zzai(parcel, n2);
    }
}

