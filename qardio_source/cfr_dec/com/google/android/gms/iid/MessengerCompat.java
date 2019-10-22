/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.iid;

import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.iid.zzi;
import com.google.android.gms.iid.zzj;
import com.google.android.gms.iid.zzk;

public class MessengerCompat
implements ReflectedParcelable {
    public static final Parcelable.Creator<MessengerCompat> CREATOR = new zzk();
    private Messenger zzifn;
    private zzi zzifo;

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public MessengerCompat(IBinder object) {
        void var1_3;
        if (Build.VERSION.SDK_INT >= 21) {
            this.zzifn = new Messenger(object);
            return;
        }
        if (object == null) {
            Object var1_2 = null;
        } else {
            IInterface iInterface = object.queryLocalInterface("com.google.android.gms.iid.IMessengerCompat");
            if (iInterface instanceof zzi) {
                zzi zzi2 = (zzi)iInterface;
            } else {
                zzj zzj2 = new zzj((IBinder)object);
            }
        }
        this.zzifo = var1_3;
    }

    private final IBinder getBinder() {
        if (this.zzifn != null) {
            return this.zzifn.getBinder();
        }
        return this.zzifo.asBinder();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        try {
            boolean bl = this.getBinder().equals((Object)((MessengerCompat)object).getBinder());
            return bl;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public int hashCode() {
        return this.getBinder().hashCode();
    }

    public final void send(Message message) throws RemoteException {
        if (this.zzifn != null) {
            this.zzifn.send(message);
            return;
        }
        this.zzifo.send(message);
    }

    public void writeToParcel(Parcel parcel, int n) {
        if (this.zzifn != null) {
            parcel.writeStrongBinder(this.zzifn.getBinder());
            return;
        }
        parcel.writeStrongBinder(this.zzifo.asBinder());
    }
}

