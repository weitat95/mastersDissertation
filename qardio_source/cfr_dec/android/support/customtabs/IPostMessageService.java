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
import android.support.customtabs.ICustomTabsCallback;

public interface IPostMessageService
extends IInterface {
    public void onMessageChannelReady(ICustomTabsCallback var1, Bundle var2) throws RemoteException;

    public void onPostMessage(ICustomTabsCallback var1, String var2, Bundle var3) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IPostMessageService {
        public Stub() {
            this.attachInterface((IInterface)this, "android.support.customtabs.IPostMessageService");
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
                    parcel.writeString("android.support.customtabs.IPostMessageService");
                    return true;
                }
                case 2: {
                    object.enforceInterface("android.support.customtabs.IPostMessageService");
                    ICustomTabsCallback iCustomTabsCallback = ICustomTabsCallback.Stub.asInterface(object.readStrongBinder());
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
                    this.onMessageChannelReady(iCustomTabsCallback, (Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 3: 
            }
            object.enforceInterface("android.support.customtabs.IPostMessageService");
            ICustomTabsCallback iCustomTabsCallback = ICustomTabsCallback.Stub.asInterface(object.readStrongBinder());
            String string2 = object.readString();
            object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
            this.onPostMessage(iCustomTabsCallback, string2, (Bundle)object);
            parcel.writeNoException();
            return true;
        }
    }

}

