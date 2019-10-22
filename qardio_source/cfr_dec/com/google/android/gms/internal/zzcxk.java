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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzcxg;
import com.google.android.gms.internal.zzcxj;
import com.google.android.gms.internal.zzcxq;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;

public abstract class zzcxk
extends zzev
implements zzcxj {
    public zzcxk() {
        this.attachInterface((IInterface)this, "com.google.android.gms.signin.internal.ISignInCallbacks");
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (this.zza(n, parcel, parcel2, n2)) {
            return true;
        }
        switch (n) {
            default: {
                return false;
            }
            case 3: {
                zzew.zza(parcel, ConnectionResult.CREATOR);
                zzew.zza(parcel, zzcxg.CREATOR);
                break;
            }
            case 4: {
                zzew.zza(parcel, Status.CREATOR);
                break;
            }
            case 6: {
                zzew.zza(parcel, Status.CREATOR);
                break;
            }
            case 7: {
                zzew.zza(parcel, Status.CREATOR);
                zzew.zza(parcel, GoogleSignInAccount.CREATOR);
                break;
            }
            case 8: {
                this.zzb(zzew.zza(parcel, zzcxq.CREATOR));
            }
        }
        parcel2.writeNoException();
        return true;
    }
}

