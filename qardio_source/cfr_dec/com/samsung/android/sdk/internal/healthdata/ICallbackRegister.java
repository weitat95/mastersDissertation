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
package com.samsung.android.sdk.internal.healthdata;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.sdk.internal.healthdata.IHealthResultReceiver;

public interface ICallbackRegister
extends IInterface {
    public void cancel(int var1) throws RemoteException;

    public void setCallback(int var1, IHealthResultReceiver var2) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements ICallbackRegister {
        public Stub() {
            this.attachInterface((IInterface)this, "com.samsung.android.sdk.internal.healthdata.ICallbackRegister");
        }

        public static ICallbackRegister asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.samsung.android.sdk.internal.healthdata.ICallbackRegister");
            if (iInterface != null && iInterface instanceof ICallbackRegister) {
                return (ICallbackRegister)iInterface;
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
                    parcel2.writeString("com.samsung.android.sdk.internal.healthdata.ICallbackRegister");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.samsung.android.sdk.internal.healthdata.ICallbackRegister");
                    this.setCallback(parcel.readInt(), IHealthResultReceiver.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                }
                case 2: 
            }
            parcel.enforceInterface("com.samsung.android.sdk.internal.healthdata.ICallbackRegister");
            this.cancel(parcel.readInt());
            return true;
        }

        static final class a
        implements ICallbackRegister {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            public final IBinder asBinder() {
                return this.a;
            }

            @Override
            public final void cancel(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.internal.healthdata.ICallbackRegister");
                    parcel.writeInt(n);
                    this.a.transact(2, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public final void setCallback(int n, IHealthResultReceiver iHealthResultReceiver) throws RemoteException {
                IBinder iBinder = null;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.internal.healthdata.ICallbackRegister");
                    parcel.writeInt(n);
                    if (iHealthResultReceiver != null) {
                        iBinder = iHealthResultReceiver.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
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

