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
import com.samsung.android.sdk.healthdata.IHealthDataObserver;

public interface IDataWatcher
extends IInterface {
    public void registerDataObserver(String var1, IHealthDataObserver var2) throws RemoteException;

    public void registerDataObserver2(String var1, String var2, IHealthDataObserver var3) throws RemoteException;

    public void unregisterDataObserver(IHealthDataObserver var1) throws RemoteException;

    public void unregisterDataObserver2(String var1, IHealthDataObserver var2) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IDataWatcher {
        public static IDataWatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.samsung.android.sdk.healthdata.IDataWatcher");
            if (iInterface != null && iInterface instanceof IDataWatcher) {
                return (IDataWatcher)iInterface;
            }
            return new a(iBinder);
        }

        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            switch (n) {
                default: {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                case 1598968902: {
                    parcel2.writeString("com.samsung.android.sdk.healthdata.IDataWatcher");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.samsung.android.sdk.healthdata.IDataWatcher");
                    this.registerDataObserver(parcel.readString(), IHealthDataObserver.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.samsung.android.sdk.healthdata.IDataWatcher");
                    this.unregisterDataObserver(IHealthDataObserver.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.samsung.android.sdk.healthdata.IDataWatcher");
                    this.registerDataObserver2(parcel.readString(), parcel.readString(), IHealthDataObserver.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 4: 
            }
            parcel.enforceInterface("com.samsung.android.sdk.healthdata.IDataWatcher");
            this.unregisterDataObserver2(parcel.readString(), IHealthDataObserver.Stub.asInterface(parcel.readStrongBinder()));
            parcel2.writeNoException();
            return true;
        }

        static final class a
        implements IDataWatcher {
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
            public final void registerDataObserver(String string2, IHealthDataObserver iHealthDataObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDataWatcher");
                    parcel.writeString(string2);
                    string2 = iHealthDataObserver != null ? iHealthDataObserver.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)string2);
                    this.a.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public final void registerDataObserver2(String string2, String string3, IHealthDataObserver iHealthDataObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDataWatcher");
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    string2 = iHealthDataObserver != null ? iHealthDataObserver.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)string2);
                    this.a.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public final void unregisterDataObserver(IHealthDataObserver iHealthDataObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDataWatcher");
                    iHealthDataObserver = iHealthDataObserver != null ? iHealthDataObserver.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iHealthDataObserver);
                    this.a.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public final void unregisterDataObserver2(String string2, IHealthDataObserver iHealthDataObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDataWatcher");
                    parcel.writeString(string2);
                    string2 = iHealthDataObserver != null ? iHealthDataObserver.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)string2);
                    this.a.transact(4, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

