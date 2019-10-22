/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.firebase.jobdispatcher;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IJobCallback
extends IInterface {
    public void jobFinished(Bundle var1, int var2) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IJobCallback {
        public Stub() {
            this.attachInterface((IInterface)this, "com.firebase.jobdispatcher.IJobCallback");
        }

        public static IJobCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.firebase.jobdispatcher.IJobCallback");
            if (iInterface != null && iInterface instanceof IJobCallback) {
                return (IJobCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            switch (n) {
                default: {
                    return super.onTransact(n, parcel, object, n2);
                }
                case 1598968902: {
                    object.writeString("com.firebase.jobdispatcher.IJobCallback");
                    return true;
                }
                case 1: 
            }
            parcel.enforceInterface("com.firebase.jobdispatcher.IJobCallback");
            object = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
            this.jobFinished((Bundle)object, parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IJobCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void jobFinished(Bundle bundle, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.firebase.jobdispatcher.IJobCallback");
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    this.mRemote.transact(1, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

