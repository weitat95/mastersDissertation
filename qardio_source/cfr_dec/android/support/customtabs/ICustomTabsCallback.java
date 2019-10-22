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
package android.support.customtabs;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ICustomTabsCallback
extends IInterface {
    public void extraCallback(String var1, Bundle var2) throws RemoteException;

    public void onMessageChannelReady(Bundle var1) throws RemoteException;

    public void onNavigationEvent(int var1, Bundle var2) throws RemoteException;

    public void onPostMessage(String var1, Bundle var2) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements ICustomTabsCallback {
        public Stub() {
            this.attachInterface((IInterface)this, "android.support.customtabs.ICustomTabsCallback");
        }

        public static ICustomTabsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("android.support.customtabs.ICustomTabsCallback");
            if (iInterface != null && iInterface instanceof ICustomTabsCallback) {
                return (ICustomTabsCallback)iInterface;
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
                    parcel.writeString("android.support.customtabs.ICustomTabsCallback");
                    return true;
                }
                case 2: {
                    object.enforceInterface("android.support.customtabs.ICustomTabsCallback");
                    n = object.readInt();
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
                    this.onNavigationEvent(n, (Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 3: {
                    object.enforceInterface("android.support.customtabs.ICustomTabsCallback");
                    String string2 = object.readString();
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
                    this.extraCallback(string2, (Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 4: {
                    object.enforceInterface("android.support.customtabs.ICustomTabsCallback");
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
                    this.onMessageChannelReady((Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 5: 
            }
            object.enforceInterface("android.support.customtabs.ICustomTabsCallback");
            String string3 = object.readString();
            object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
            this.onPostMessage(string3, (Bundle)object);
            parcel.writeNoException();
            return true;
        }

        private static class Proxy
        implements ICustomTabsCallback {
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
            public void extraCallback(String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.customtabs.ICustomTabsCallback");
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(3, parcel, parcel2, 0);
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
            public void onMessageChannelReady(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.customtabs.ICustomTabsCallback");
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(4, parcel, parcel2, 0);
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
            public void onNavigationEvent(int n, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.customtabs.ICustomTabsCallback");
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(2, parcel, parcel2, 0);
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
            public void onPostMessage(String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.customtabs.ICustomTabsCallback");
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(5, parcel, parcel2, 0);
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

