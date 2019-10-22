/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.zzcgi;
import com.google.android.gms.internal.zzcgl;
import com.google.android.gms.internal.zzcha;
import com.google.android.gms.internal.zzche;
import com.google.android.gms.internal.zzcln;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;
import java.util.List;

public abstract class zzchf
extends zzev
implements zzche {
    public zzchf() {
        this.attachInterface((IInterface)this, "com.google.android.gms.measurement.internal.IMeasurementService");
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
        void var4_11;
        void var3_10;
        if (this.zza(n, (Parcel)object, (Parcel)var3_10, (int)var4_11)) {
            return true;
        }
        switch (n) {
            default: {
                return false;
            }
            case 1: {
                this.zza(zzew.zza((Parcel)object, zzcha.CREATOR), zzew.zza((Parcel)object, zzcgi.CREATOR));
                var3_10.writeNoException();
                do {
                    return true;
                    break;
                } while (true);
            }
            case 2: {
                this.zza(zzew.zza((Parcel)object, zzcln.CREATOR), zzew.zza((Parcel)object, zzcgi.CREATOR));
                var3_10.writeNoException();
                return true;
            }
            case 4: {
                this.zza(zzew.zza((Parcel)object, zzcgi.CREATOR));
                var3_10.writeNoException();
                return true;
            }
            case 5: {
                this.zza(zzew.zza((Parcel)object, zzcha.CREATOR), object.readString(), object.readString());
                var3_10.writeNoException();
                return true;
            }
            case 6: {
                this.zzb(zzew.zza((Parcel)object, zzcgi.CREATOR));
                var3_10.writeNoException();
                return true;
            }
            case 7: {
                List list = this.zza(zzew.zza((Parcel)object, zzcgi.CREATOR), zzew.zza((Parcel)object));
                var3_10.writeNoException();
                var3_10.writeTypedList(list);
                return true;
            }
            case 9: {
                byte[] arrby = this.zza(zzew.zza((Parcel)object, zzcha.CREATOR), object.readString());
                var3_10.writeNoException();
                var3_10.writeByteArray(arrby);
                return true;
            }
            case 10: {
                this.zza(object.readLong(), object.readString(), object.readString(), object.readString());
                var3_10.writeNoException();
                return true;
            }
            case 11: {
                String string2 = this.zzc(zzew.zza((Parcel)object, zzcgi.CREATOR));
                var3_10.writeNoException();
                var3_10.writeString(string2);
                return true;
            }
            case 12: {
                this.zza(zzew.zza((Parcel)object, zzcgl.CREATOR), zzew.zza((Parcel)object, zzcgi.CREATOR));
                var3_10.writeNoException();
                return true;
            }
            case 13: {
                this.zzb(zzew.zza((Parcel)object, zzcgl.CREATOR));
                var3_10.writeNoException();
                return true;
            }
            case 14: {
                List list = this.zza(object.readString(), object.readString(), zzew.zza((Parcel)object), zzew.zza((Parcel)object, zzcgi.CREATOR));
                var3_10.writeNoException();
                var3_10.writeTypedList(list);
                return true;
            }
            case 15: {
                List list = this.zza(object.readString(), object.readString(), object.readString(), zzew.zza((Parcel)object));
                var3_10.writeNoException();
                var3_10.writeTypedList(list);
                return true;
            }
            case 16: {
                List list = this.zza(object.readString(), object.readString(), zzew.zza((Parcel)object, zzcgi.CREATOR));
                var3_10.writeNoException();
                var3_10.writeTypedList(list);
                return true;
            }
            case 17: {
                List list = this.zzj(object.readString(), object.readString(), object.readString());
                var3_10.writeNoException();
                var3_10.writeTypedList(list);
                return true;
            }
            case 18: 
        }
        this.zzd(zzew.zza((Parcel)object, zzcgi.CREATOR));
        var3_10.writeNoException();
        return true;
    }
}

