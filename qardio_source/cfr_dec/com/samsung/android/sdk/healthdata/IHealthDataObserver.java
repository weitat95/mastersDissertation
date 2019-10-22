/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.samsung.android.sdk.healthdata;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IHealthDataObserver
extends IInterface {
    public void onChange(String var1) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IHealthDataObserver {
        public Stub() {
            this.attachInterface((IInterface)this, "com.samsung.android.sdk.healthdata.IHealthDataObserver");
        }

        public static IHealthDataObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.samsung.android.sdk.healthdata.IHealthDataObserver");
            if (iInterface != null && iInterface instanceof IHealthDataObserver) {
                return (IHealthDataObserver)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            switch (n) {
                default: {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                case 1598968902: {
                    parcel2.writeString("com.samsung.android.sdk.healthdata.IHealthDataObserver");
                    return true;
                }
                case 1: 
            }
            parcel.enforceInterface("com.samsung.android.sdk.healthdata.IHealthDataObserver");
            this.onChange(parcel.readString());
            return true;
        }

        static final class a
        implements IHealthDataObserver {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            public final IBinder asBinder() {
                return this.a;
            }

            @Override
            public final void onChange(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IHealthDataObserver");
                    parcel.writeString(string2);
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

