/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.firebase.jobdispatcher;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.firebase.jobdispatcher.JobCallback;

final class GooglePlayJobCallback
implements JobCallback {
    private final IBinder remote;

    public GooglePlayJobCallback(IBinder iBinder) {
        this.remote = iBinder;
    }

    @Override
    public void jobFinished(int n) {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.google.android.gms.gcm.INetworkTaskCallback");
            parcel.writeInt(n);
            this.remote.transact(2, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }
}

