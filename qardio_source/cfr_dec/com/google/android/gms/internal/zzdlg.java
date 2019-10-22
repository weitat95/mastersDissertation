/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzdkq;
import com.google.android.gms.internal.zzdks;
import com.google.android.gms.internal.zzdku;
import com.google.android.gms.internal.zzdlf;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.zzar;

public abstract class zzdlg
extends zzev
implements zzdlf {
    public zzdlg() {
        this.attachInterface((IInterface)this, "com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (this.zza(n, parcel, parcel2, n2)) {
            return true;
        }
        switch (n) {
            default: {
                return false;
            }
            case 1: {
                this.zza(parcel.readInt(), zzew.zza(parcel, MaskedWallet.CREATOR), (Bundle)zzew.zza(parcel, Bundle.CREATOR));
                do {
                    return true;
                    break;
                } while (true);
            }
            case 2: {
                this.zza(parcel.readInt(), zzew.zza(parcel, FullWallet.CREATOR), (Bundle)zzew.zza(parcel, Bundle.CREATOR));
                return true;
            }
            case 3: {
                this.zza(parcel.readInt(), zzew.zza(parcel), (Bundle)zzew.zza(parcel, Bundle.CREATOR));
                return true;
            }
            case 4: {
                this.zzg(parcel.readInt(), (Bundle)zzew.zza(parcel, Bundle.CREATOR));
                return true;
            }
            case 6: {
                parcel.readInt();
                zzew.zza(parcel);
                zzew.zza(parcel, Bundle.CREATOR);
                return true;
            }
            case 7: {
                zzew.zza(parcel, Status.CREATOR);
                zzew.zza(parcel, zzdkq.CREATOR);
                zzew.zza(parcel, Bundle.CREATOR);
                return true;
            }
            case 8: {
                zzew.zza(parcel, Status.CREATOR);
                zzew.zza(parcel, Bundle.CREATOR);
                return true;
            }
            case 9: {
                this.zza(zzew.zza(parcel, Status.CREATOR), zzew.zza(parcel), (Bundle)zzew.zza(parcel, Bundle.CREATOR));
                return true;
            }
            case 10: {
                zzew.zza(parcel, Status.CREATOR);
                zzew.zza(parcel, zzdks.CREATOR);
                zzew.zza(parcel, Bundle.CREATOR);
                return true;
            }
            case 11: {
                zzew.zza(parcel, Status.CREATOR);
                zzew.zza(parcel, Bundle.CREATOR);
                return true;
            }
            case 12: {
                zzew.zza(parcel, Status.CREATOR);
                zzew.zza(parcel, zzar.CREATOR);
                zzew.zza(parcel, Bundle.CREATOR);
                return true;
            }
            case 13: {
                zzew.zza(parcel, Status.CREATOR);
                zzew.zza(parcel, Bundle.CREATOR);
                return true;
            }
            case 14: {
                this.zza(zzew.zza(parcel, Status.CREATOR), zzew.zza(parcel, PaymentData.CREATOR), (Bundle)zzew.zza(parcel, Bundle.CREATOR));
                return true;
            }
            case 15: 
        }
        zzew.zza(parcel, Status.CREATOR);
        zzew.zza(parcel, zzdku.CREATOR);
        zzew.zza(parcel, Bundle.CREATOR);
        return true;
    }
}

