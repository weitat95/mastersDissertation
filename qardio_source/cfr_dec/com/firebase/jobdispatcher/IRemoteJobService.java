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
import com.firebase.jobdispatcher.IJobCallback;

public interface IRemoteJobService
extends IInterface {
    public void start(Bundle var1, IJobCallback var2) throws RemoteException;

    public void stop(Bundle var1, boolean var2) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IRemoteJobService {
        public Stub() {
            this.attachInterface((IInterface)this, "com.firebase.jobdispatcher.IRemoteJobService");
        }

        public static IRemoteJobService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.firebase.jobdispatcher.IRemoteJobService");
            if (iInterface != null && iInterface instanceof IRemoteJobService) {
                return (IRemoteJobService)iInterface;
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
                    object.writeString("com.firebase.jobdispatcher.IRemoteJobService");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.firebase.jobdispatcher.IRemoteJobService");
                    object = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
                    this.start((Bundle)object, IJobCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                }
                case 2: 
            }
            parcel.enforceInterface("com.firebase.jobdispatcher.IRemoteJobService");
            object = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
            boolean bl = parcel.readInt() != 0;
            this.stop((Bundle)object, bl);
            return true;
        }

        private static class Proxy
        implements IRemoteJobService {
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
            public void start(Bundle bundle, IJobCallback iJobCallback) throws RemoteException {
                Object var3_4 = null;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.firebase.jobdispatcher.IRemoteJobService");
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    bundle = var3_4;
                    if (iJobCallback != null) {
                        bundle = iJobCallback.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)bundle);
                    this.mRemote.transact(1, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void stop(Bundle bundle, boolean bl) throws RemoteException {
                int n = 1;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.firebase.jobdispatcher.IRemoteJobService");
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                }
                catch (Throwable throwable) {
                    parcel.recycle();
                    throw throwable;
                }
                if (!bl) {
                    n = 0;
                }
                parcel.writeInt(n);
                this.mRemote.transact(2, parcel, null, 1);
                parcel.recycle();
            }
        }

    }

}

