/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzk;
import com.google.android.gms.dynamic.zzm;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;

public abstract class zzl
extends zzev
implements zzk {
    public zzl() {
        this.attachInterface((IInterface)this, "com.google.android.gms.dynamic.IFragmentWrapper");
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
        void var2_17;
        void var4_21;
        void var3_20;
        Object var7_22 = null;
        IInterface iInterface = null;
        if (this.zza(n, (Parcel)object, (Parcel)var3_20, (int)var4_21)) {
            return true;
        }
        switch (n) {
            default: {
                return false;
            }
            case 2: {
                IObjectWrapper iObjectWrapper = this.zzapx();
                var3_20.writeNoException();
                zzew.zza((Parcel)var3_20, iObjectWrapper);
                return true;
            }
            case 3: {
                Bundle bundle = this.getArguments();
                var3_20.writeNoException();
                zzew.zzb((Parcel)var3_20, (Parcelable)bundle);
                return true;
            }
            case 4: {
                n = this.getId();
                var3_20.writeNoException();
                var3_20.writeInt(n);
                return true;
            }
            case 5: {
                zzk zzk2 = this.zzapy();
                var3_20.writeNoException();
                zzew.zza((Parcel)var3_20, zzk2);
                return true;
            }
            case 6: {
                IObjectWrapper iObjectWrapper = this.zzapz();
                var3_20.writeNoException();
                zzew.zza((Parcel)var3_20, iObjectWrapper);
                return true;
            }
            case 7: {
                boolean bl = this.getRetainInstance();
                var3_20.writeNoException();
                zzew.zza((Parcel)var3_20, bl);
                return true;
            }
            case 8: {
                String string2 = this.getTag();
                var3_20.writeNoException();
                var3_20.writeString(string2);
                return true;
            }
            case 9: {
                zzk zzk3 = this.zzaqa();
                var3_20.writeNoException();
                zzew.zza((Parcel)var3_20, zzk3);
                return true;
            }
            case 10: {
                n = this.getTargetRequestCode();
                var3_20.writeNoException();
                var3_20.writeInt(n);
                return true;
            }
            case 11: {
                boolean bl = this.getUserVisibleHint();
                var3_20.writeNoException();
                zzew.zza((Parcel)var3_20, bl);
                return true;
            }
            case 12: {
                IObjectWrapper iObjectWrapper = this.getView();
                var3_20.writeNoException();
                zzew.zza((Parcel)var3_20, iObjectWrapper);
                return true;
            }
            case 13: {
                boolean bl = this.isAdded();
                var3_20.writeNoException();
                zzew.zza((Parcel)var3_20, bl);
                return true;
            }
            case 14: {
                boolean bl = this.isDetached();
                var3_20.writeNoException();
                zzew.zza((Parcel)var3_20, bl);
                return true;
            }
            case 15: {
                boolean bl = this.isHidden();
                var3_20.writeNoException();
                zzew.zza((Parcel)var3_20, bl);
                return true;
            }
            case 16: {
                boolean bl = this.isInLayout();
                var3_20.writeNoException();
                zzew.zza((Parcel)var3_20, bl);
                return true;
            }
            case 17: {
                boolean bl = this.isRemoving();
                var3_20.writeNoException();
                zzew.zza((Parcel)var3_20, bl);
                return true;
            }
            case 18: {
                boolean bl = this.isResumed();
                var3_20.writeNoException();
                zzew.zza((Parcel)var3_20, bl);
                return true;
            }
            case 19: {
                boolean bl = this.isVisible();
                var3_20.writeNoException();
                zzew.zza((Parcel)var3_20, bl);
                return true;
            }
            case 20: {
                void var2_12;
                IBinder iBinder = object.readStrongBinder();
                if (iBinder == null) {
                    IInterface iInterface2 = iInterface;
                } else {
                    iInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
                    if (iInterface instanceof IObjectWrapper) {
                        IObjectWrapper iObjectWrapper = (IObjectWrapper)iInterface;
                    } else {
                        zzm zzm2 = new zzm(iBinder);
                    }
                }
                this.zzv((IObjectWrapper)var2_12);
                var3_20.writeNoException();
                return true;
            }
            case 21: {
                this.setHasOptionsMenu(zzew.zza(object));
                var3_20.writeNoException();
                return true;
            }
            case 22: {
                this.setMenuVisibility(zzew.zza(object));
                var3_20.writeNoException();
                return true;
            }
            case 23: {
                this.setRetainInstance(zzew.zza(object));
                var3_20.writeNoException();
                return true;
            }
            case 24: {
                this.setUserVisibleHint(zzew.zza(object));
                var3_20.writeNoException();
                return true;
            }
            case 25: {
                this.startActivity((Intent)zzew.zza(object, Intent.CREATOR));
                var3_20.writeNoException();
                return true;
            }
            case 26: {
                this.startActivityForResult((Intent)zzew.zza(object, Intent.CREATOR), object.readInt());
                var3_20.writeNoException();
                return true;
            }
            case 27: 
        }
        IBinder iBinder = object.readStrongBinder();
        if (iBinder == null) {
            Object var2_16 = var7_22;
        } else {
            iInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
            if (iInterface instanceof IObjectWrapper) {
                IObjectWrapper iObjectWrapper = (IObjectWrapper)iInterface;
            } else {
                zzm zzm3 = new zzm(iBinder);
            }
        }
        this.zzw((IObjectWrapper)var2_17);
        var3_20.writeNoException();
        return true;
    }
}

