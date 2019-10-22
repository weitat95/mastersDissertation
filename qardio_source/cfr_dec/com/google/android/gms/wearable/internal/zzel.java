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
package com.google.android.gms.wearable.internal;

import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.wearable.internal.zzbn;
import com.google.android.gms.wearable.internal.zzbp;
import com.google.android.gms.wearable.internal.zzbt;
import com.google.android.gms.wearable.internal.zzdg;
import com.google.android.gms.wearable.internal.zzdi;
import com.google.android.gms.wearable.internal.zzdk;
import com.google.android.gms.wearable.internal.zzdm;
import com.google.android.gms.wearable.internal.zzdo;
import com.google.android.gms.wearable.internal.zzdr;
import com.google.android.gms.wearable.internal.zzdt;
import com.google.android.gms.wearable.internal.zzdv;
import com.google.android.gms.wearable.internal.zzdw;
import com.google.android.gms.wearable.internal.zzdy;
import com.google.android.gms.wearable.internal.zzea;
import com.google.android.gms.wearable.internal.zzec;
import com.google.android.gms.wearable.internal.zzee;
import com.google.android.gms.wearable.internal.zzeg;
import com.google.android.gms.wearable.internal.zzek;
import com.google.android.gms.wearable.internal.zzf;
import com.google.android.gms.wearable.internal.zzfq;
import com.google.android.gms.wearable.internal.zzfu;
import com.google.android.gms.wearable.internal.zzfy;
import com.google.android.gms.wearable.internal.zzga;
import com.google.android.gms.wearable.internal.zzge;

public abstract class zzel
extends zzev
implements zzek {
    public zzel() {
        this.attachInterface((IInterface)this, "com.google.android.gms.wearable.internal.IWearableCallbacks");
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
            case 2: {
                this.zza(zzew.zza(parcel, zzdw.CREATOR));
                break;
            }
            case 13: {
                this.zza(zzew.zza(parcel, zzdy.CREATOR));
                break;
            }
            case 28: {
                this.zza(zzew.zza(parcel, zzdr.CREATOR));
                break;
            }
            case 29: {
                this.zza(zzew.zza(parcel, zzdv.CREATOR));
                break;
            }
            case 30: {
                this.zza(zzew.zza(parcel, zzdt.CREATOR));
                break;
            }
            case 3: {
                this.zza(zzew.zza(parcel, zzfu.CREATOR));
                break;
            }
            case 4: {
                this.zza(zzew.zza(parcel, zzec.CREATOR));
                break;
            }
            case 5: {
                this.zzat(zzew.zza(parcel, DataHolder.CREATOR));
                break;
            }
            case 6: {
                this.zza(zzew.zza(parcel, zzdg.CREATOR));
                break;
            }
            case 7: {
                this.zza(zzew.zza(parcel, zzga.CREATOR));
                break;
            }
            case 8: {
                this.zza(zzew.zza(parcel, zzee.CREATOR));
                break;
            }
            case 9: {
                this.zza(zzew.zza(parcel, zzeg.CREATOR));
                break;
            }
            case 10: {
                this.zza(zzew.zza(parcel, zzea.CREATOR));
                break;
            }
            case 14: {
                this.zza(zzew.zza(parcel, zzfq.CREATOR));
                break;
            }
            case 15: {
                this.zza(zzew.zza(parcel, zzbt.CREATOR));
                break;
            }
            case 16: {
                this.zzb(zzew.zza(parcel, zzbt.CREATOR));
                break;
            }
            case 17: {
                this.zza(zzew.zza(parcel, zzdm.CREATOR));
                break;
            }
            case 18: {
                this.zza(zzew.zza(parcel, zzdo.CREATOR));
                break;
            }
            case 19: {
                this.zza(zzew.zza(parcel, zzbn.CREATOR));
                break;
            }
            case 20: {
                this.zza(zzew.zza(parcel, zzbp.CREATOR));
                break;
            }
            case 11: {
                this.zza(zzew.zza(parcel, Status.CREATOR));
                break;
            }
            case 12: {
                this.zza(zzew.zza(parcel, zzge.CREATOR));
                break;
            }
            case 22: {
                this.zza(zzew.zza(parcel, zzdk.CREATOR));
                break;
            }
            case 23: {
                this.zza(zzew.zza(parcel, zzdi.CREATOR));
                break;
            }
            case 26: {
                this.zza(zzew.zza(parcel, zzf.CREATOR));
                break;
            }
            case 27: {
                this.zza(zzew.zza(parcel, zzfy.CREATOR));
            }
        }
        parcel2.writeNoException();
        return true;
    }
}

