/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package android.support.customtabs;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.customtabs.ICustomTabsCallback;
import java.util.ArrayList;
import java.util.List;

public interface ICustomTabsService
extends IInterface {
    public Bundle extraCommand(String var1, Bundle var2) throws RemoteException;

    public boolean mayLaunchUrl(ICustomTabsCallback var1, Uri var2, Bundle var3, List<Bundle> var4) throws RemoteException;

    public boolean newSession(ICustomTabsCallback var1) throws RemoteException;

    public int postMessage(ICustomTabsCallback var1, String var2, Bundle var3) throws RemoteException;

    public boolean requestPostMessageChannel(ICustomTabsCallback var1, Uri var2) throws RemoteException;

    public boolean updateVisuals(ICustomTabsCallback var1, Bundle var2) throws RemoteException;

    public boolean warmup(long var1) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements ICustomTabsService {
        public Stub() {
            this.attachInterface((IInterface)this, "android.support.customtabs.ICustomTabsService");
        }

        public static ICustomTabsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("android.support.customtabs.ICustomTabsService");
            if (iInterface != null && iInterface instanceof ICustomTabsService) {
                return (ICustomTabsService)iInterface;
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
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            switch (n) {
                default: {
                    return super.onTransact(n, object, parcel, n2);
                }
                case 1598968902: {
                    parcel.writeString("android.support.customtabs.ICustomTabsService");
                    return true;
                }
                case 2: {
                    object.enforceInterface("android.support.customtabs.ICustomTabsService");
                    boolean bl = this.warmup(object.readLong());
                    parcel.writeNoException();
                    n = n7;
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    return true;
                }
                case 3: {
                    object.enforceInterface("android.support.customtabs.ICustomTabsService");
                    boolean bl = this.newSession(ICustomTabsCallback.Stub.asInterface(object.readStrongBinder()));
                    parcel.writeNoException();
                    n = n3;
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    return true;
                }
                case 4: {
                    object.enforceInterface("android.support.customtabs.ICustomTabsService");
                    ICustomTabsCallback iCustomTabsCallback = ICustomTabsCallback.Stub.asInterface(object.readStrongBinder());
                    Uri uri = object.readInt() != 0 ? (Uri)Uri.CREATOR.createFromParcel(object) : null;
                    Bundle bundle = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
                    boolean bl = this.mayLaunchUrl(iCustomTabsCallback, uri, bundle, (List)object.createTypedArrayList(Bundle.CREATOR));
                    parcel.writeNoException();
                    n = n4;
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    return true;
                }
                case 5: {
                    object.enforceInterface("android.support.customtabs.ICustomTabsService");
                    String string2 = object.readString();
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
                    object = this.extraCommand(string2, (Bundle)object);
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 6: {
                    object.enforceInterface("android.support.customtabs.ICustomTabsService");
                    ICustomTabsCallback iCustomTabsCallback = ICustomTabsCallback.Stub.asInterface(object.readStrongBinder());
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
                    boolean bl = this.updateVisuals(iCustomTabsCallback, (Bundle)object);
                    parcel.writeNoException();
                    n = n5;
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    return true;
                }
                case 7: {
                    object.enforceInterface("android.support.customtabs.ICustomTabsService");
                    ICustomTabsCallback iCustomTabsCallback = ICustomTabsCallback.Stub.asInterface(object.readStrongBinder());
                    object = object.readInt() != 0 ? (Uri)Uri.CREATOR.createFromParcel(object) : null;
                    boolean bl = this.requestPostMessageChannel(iCustomTabsCallback, (Uri)object);
                    parcel.writeNoException();
                    n = n6;
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    return true;
                }
                case 8: 
            }
            object.enforceInterface("android.support.customtabs.ICustomTabsService");
            ICustomTabsCallback iCustomTabsCallback = ICustomTabsCallback.Stub.asInterface(object.readStrongBinder());
            String string3 = object.readString();
            object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
            n = this.postMessage(iCustomTabsCallback, string3, (Bundle)object);
            parcel.writeNoException();
            parcel.writeInt(n);
            return true;
        }

        private static class Proxy
        implements ICustomTabsService {
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
            public Bundle extraCommand(String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.customtabs.ICustomTabsService");
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(5, parcel, parcel2, 0);
                    parcel2.readException();
                    string2 = parcel2.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel2) : null;
                    return string2;
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
            public boolean mayLaunchUrl(ICustomTabsCallback iCustomTabsCallback, Uri uri, Bundle bundle, List<Bundle> list) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block9: {
                    block8: {
                        bl = true;
                        parcel = Parcel.obtain();
                        parcel2 = Parcel.obtain();
                        try {
                            parcel.writeInterfaceToken("android.support.customtabs.ICustomTabsService");
                            iCustomTabsCallback = iCustomTabsCallback != null ? iCustomTabsCallback.asBinder() : null;
                            parcel.writeStrongBinder((IBinder)iCustomTabsCallback);
                            if (uri != null) {
                                parcel.writeInt(1);
                                uri.writeToParcel(parcel, 0);
                            } else {
                                parcel.writeInt(0);
                            }
                            if (bundle != null) {
                                parcel.writeInt(1);
                                bundle.writeToParcel(parcel, 0);
                            } else {
                                parcel.writeInt(0);
                            }
                            parcel.writeTypedList(list);
                            this.mRemote.transact(4, parcel, parcel2, 0);
                            parcel2.readException();
                            int n = parcel2.readInt();
                            if (n == 0) break block8;
                            break block9;
                        }
                        catch (Throwable throwable) {
                            parcel2.recycle();
                            parcel.recycle();
                            throw throwable;
                        }
                    }
                    bl = false;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean newSession(ICustomTabsCallback iCustomTabsCallback) throws RemoteException {
                boolean bl = false;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.customtabs.ICustomTabsService");
                    iCustomTabsCallback = iCustomTabsCallback != null ? iCustomTabsCallback.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iCustomTabsCallback);
                    this.mRemote.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) return bl;
                    {
                        bl = true;
                        return bl;
                    }
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
            public int postMessage(ICustomTabsCallback iCustomTabsCallback, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.customtabs.ICustomTabsService");
                    iCustomTabsCallback = iCustomTabsCallback != null ? iCustomTabsCallback.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iCustomTabsCallback);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(8, parcel, parcel2, 0);
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
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
            public boolean requestPostMessageChannel(ICustomTabsCallback iCustomTabsCallback, Uri uri) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block6: {
                    block5: {
                        bl = true;
                        parcel = Parcel.obtain();
                        parcel2 = Parcel.obtain();
                        try {
                            parcel.writeInterfaceToken("android.support.customtabs.ICustomTabsService");
                            iCustomTabsCallback = iCustomTabsCallback != null ? iCustomTabsCallback.asBinder() : null;
                            parcel.writeStrongBinder((IBinder)iCustomTabsCallback);
                            if (uri != null) {
                                parcel.writeInt(1);
                                uri.writeToParcel(parcel, 0);
                            } else {
                                parcel.writeInt(0);
                            }
                            this.mRemote.transact(7, parcel, parcel2, 0);
                            parcel2.readException();
                            int n = parcel2.readInt();
                            if (n == 0) break block5;
                            break block6;
                        }
                        catch (Throwable throwable) {
                            parcel2.recycle();
                            parcel.recycle();
                            throw throwable;
                        }
                    }
                    bl = false;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean updateVisuals(ICustomTabsCallback iCustomTabsCallback, Bundle bundle) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block6: {
                    block5: {
                        bl = true;
                        parcel = Parcel.obtain();
                        parcel2 = Parcel.obtain();
                        try {
                            parcel.writeInterfaceToken("android.support.customtabs.ICustomTabsService");
                            iCustomTabsCallback = iCustomTabsCallback != null ? iCustomTabsCallback.asBinder() : null;
                            parcel.writeStrongBinder((IBinder)iCustomTabsCallback);
                            if (bundle != null) {
                                parcel.writeInt(1);
                                bundle.writeToParcel(parcel, 0);
                            } else {
                                parcel.writeInt(0);
                            }
                            this.mRemote.transact(6, parcel, parcel2, 0);
                            parcel2.readException();
                            int n = parcel2.readInt();
                            if (n == 0) break block5;
                            break block6;
                        }
                        catch (Throwable throwable) {
                            parcel2.recycle();
                            parcel.recycle();
                            throw throwable;
                        }
                    }
                    bl = false;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean warmup(long l) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block2: {
                    bl = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("android.support.customtabs.ICustomTabsService");
                        parcel.writeLong(l);
                        this.mRemote.transact(2, parcel, parcel2, 0);
                        parcel2.readException();
                        int n = parcel2.readInt();
                        if (n == 0) break block2;
                        bl = true;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }
        }

    }

}

