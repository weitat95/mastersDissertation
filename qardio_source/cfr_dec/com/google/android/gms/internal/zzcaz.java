/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzcay;
import com.google.android.gms.internal.zzcba;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;

public abstract class zzcaz
extends zzev
implements zzcay {
    public zzcaz() {
        this.attachInterface((IInterface)this, "com.google.android.gms.flags.IFlagProvider");
    }

    public static zzcay asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.flags.IFlagProvider");
        if (iInterface instanceof zzcay) {
            return (zzcay)iInterface;
        }
        return new zzcba(iBinder);
    }

    public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
        if (this.zza(n, (Parcel)object, parcel, n2)) {
            return true;
        }
        switch (n) {
            default: {
                return false;
            }
            case 1: {
                this.init(IObjectWrapper.zza.zzaq(object.readStrongBinder()));
                parcel.writeNoException();
                return true;
            }
            case 2: {
                boolean bl = this.getBooleanFlagValue(object.readString(), zzew.zza(object), object.readInt());
                parcel.writeNoException();
                zzew.zza(parcel, bl);
                return true;
            }
            case 3: {
                n = this.getIntFlagValue(object.readString(), object.readInt(), object.readInt());
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            case 4: {
                long l = this.getLongFlagValue(object.readString(), object.readLong(), object.readInt());
                parcel.writeNoException();
                parcel.writeLong(l);
                return true;
            }
            case 5: 
        }
        object = this.getStringFlagValue(object.readString(), object.readString(), object.readInt());
        parcel.writeNoException();
        parcel.writeString((String)object);
        return true;
    }
}

