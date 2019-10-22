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
package android.support.v4.os;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IResultReceiver
extends IInterface {
    public void send(int var1, Bundle var2) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IResultReceiver {
        public Stub() {
            this.attachInterface((IInterface)this, "android.support.v4.os.IResultReceiver");
        }

        public static IResultReceiver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("android.support.v4.os.IResultReceiver");
            if (iInterface != null && iInterface instanceof IResultReceiver) {
                return (IResultReceiver)iInterface;
            }
            return new Proxy(iBinder);
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
                    parcel.writeString("android.support.v4.os.IResultReceiver");
                    return true;
                }
                case 1: 
            }
            object.enforceInterface("android.support.v4.os.IResultReceiver");
            n = object.readInt();
            object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
            this.send(n, (Bundle)object);
            return true;
        }

        private static class Proxy
        implements IResultReceiver {
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
            public void send(int n, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.os.IResultReceiver");
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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

