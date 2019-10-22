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
package com.samsung.android.sdk.internal.healthdata;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IHealthResultReceiver
extends IInterface {
    public void send(int var1, Bundle var2) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IHealthResultReceiver {
        public Stub() {
            this.attachInterface((IInterface)this, "com.samsung.android.sdk.internal.healthdata.IHealthResultReceiver");
        }

        public static IHealthResultReceiver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.samsung.android.sdk.internal.healthdata.IHealthResultReceiver");
            if (iInterface != null && iInterface instanceof IHealthResultReceiver) {
                return (IHealthResultReceiver)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            switch (n) {
                default: {
                    return super.onTransact(n, object, parcel, n2);
                }
                case 1598968902: {
                    parcel.writeString("com.samsung.android.sdk.internal.healthdata.IHealthResultReceiver");
                    return true;
                }
                case 1: 
            }
            object.enforceInterface("com.samsung.android.sdk.internal.healthdata.IHealthResultReceiver");
            n = object.readInt();
            object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
            this.send(n, (Bundle)object);
            return true;
        }

        static final class a
        implements IHealthResultReceiver {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            public final IBinder asBinder() {
                return this.a;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public final void send(int n, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.internal.healthdata.IHealthResultReceiver");
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(1, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

