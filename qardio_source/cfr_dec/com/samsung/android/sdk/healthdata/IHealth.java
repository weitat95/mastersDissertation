/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.samsung.android.sdk.healthdata;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.samsung.android.sdk.healthdata.IDataResolver;
import com.samsung.android.sdk.healthdata.IDataWatcher;
import com.samsung.android.sdk.healthdata.IDeviceManager;
import com.samsung.android.sdk.internal.healthdata.HealthResultReceiver;

public interface IHealth
extends IInterface {
    public Bundle getConnectionResult(String var1, int var2) throws RemoteException;

    public Bundle getConnectionResult2(Bundle var1) throws RemoteException;

    public IDataResolver getIDataResolver() throws RemoteException;

    public IDataWatcher getIDataWatcher() throws RemoteException;

    public IDeviceManager getIDeviceManager() throws RemoteException;

    public Bundle getUserProfile() throws RemoteException;

    public Bundle getUserProfile2(String var1) throws RemoteException;

    public Bundle isHealthDataPermissionAcquired(Bundle var1) throws RemoteException;

    public Bundle isHealthDataPermissionAcquired2(String var1, Bundle var2) throws RemoteException;

    public boolean isKeyAccessible() throws RemoteException;

    public HealthResultReceiver requestHealthDataPermissions(Bundle var1) throws RemoteException;

    public Intent requestHealthDataPermissions2(String var1, HealthResultReceiver var2, Bundle var3) throws RemoteException;

    public HealthResultReceiver waitForInit(long var1) throws RemoteException;

    public void waitForInit2(String var1, HealthResultReceiver var2, long var3) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IHealth {
        public static IHealth asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.samsung.android.sdk.healthdata.IHealth");
            if (iInterface != null && iInterface instanceof IHealth) {
                return (IHealth)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            Object object2 = null;
            Object object3 = null;
            switch (n) {
                default: {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                case 1598968902: {
                    parcel.writeString("com.samsung.android.sdk.healthdata.IHealth");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IHealth");
                    object = this.getUserProfile();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IHealth");
                    object = this.getConnectionResult(object.readString(), object.readInt());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IHealth");
                    object = this.waitForInit(object.readLong());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        ((HealthResultReceiver)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IHealth");
                    object = this.getIDeviceManager();
                    parcel.writeNoException();
                    object = object != null ? object.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 5: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IHealth");
                    object2 = this.getIDataResolver();
                    parcel.writeNoException();
                    object = object3;
                    if (object2 != null) {
                        object = object2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 6: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IHealth");
                    object3 = this.getIDataWatcher();
                    parcel.writeNoException();
                    object = object2;
                    if (object3 != null) {
                        object = object3.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 7: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IHealth");
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    object = this.requestHealthDataPermissions((Bundle)object);
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        ((HealthResultReceiver)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 8: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IHealth");
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    object = this.isHealthDataPermissionAcquired((Bundle)object);
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 9: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IHealth");
                    boolean bl = this.isKeyAccessible();
                    parcel.writeNoException();
                    n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    return true;
                }
                case 10: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IHealth");
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    object = this.getConnectionResult2((Bundle)object);
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 11: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IHealth");
                    object = this.getUserProfile2(object.readString());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 12: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IHealth");
                    object2 = object.readString();
                    object3 = object.readInt() != 0 ? (HealthResultReceiver)HealthResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                    this.waitForInit2((String)object2, (HealthResultReceiver)object3, object.readLong());
                    parcel.writeNoException();
                    return true;
                }
                case 13: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IHealth");
                    object2 = object.readString();
                    object3 = object.readInt() != 0 ? (HealthResultReceiver)HealthResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    object = this.requestHealthDataPermissions2((String)object2, (HealthResultReceiver)object3, (Bundle)object);
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 14: 
            }
            object.enforceInterface("com.samsung.android.sdk.healthdata.IHealth");
            object3 = object.readString();
            object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
            object = this.isHealthDataPermissionAcquired2((String)object3, (Bundle)object);
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                object.writeToParcel(parcel, 1);
                return true;
            }
            parcel.writeInt(0);
            return true;
        }

        static final class a
        implements IHealth {
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
            public final Bundle getConnectionResult(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IHealth");
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    this.a.transact(2, parcel, parcel2, 0);
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
            public final Bundle getConnectionResult2(Bundle object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IHealth");
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(10, parcel, parcel2, 0);
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel2) : null;
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public final IDataResolver getIDataResolver() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IHealth");
                    this.a.transact(5, parcel, parcel2, 0);
                    parcel2.readException();
                    IDataResolver iDataResolver = IDataResolver.Stub.asInterface(parcel2.readStrongBinder());
                    return iDataResolver;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public final IDataWatcher getIDataWatcher() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IHealth");
                    this.a.transact(6, parcel, parcel2, 0);
                    parcel2.readException();
                    IDataWatcher iDataWatcher = IDataWatcher.Stub.asInterface(parcel2.readStrongBinder());
                    return iDataWatcher;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public final IDeviceManager getIDeviceManager() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IHealth");
                    this.a.transact(4, parcel, parcel2, 0);
                    parcel2.readException();
                    IDeviceManager iDeviceManager = IDeviceManager.Stub.asInterface(parcel2.readStrongBinder());
                    return iDeviceManager;
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
            public final Bundle getUserProfile() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IHealth");
                    this.a.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    Bundle bundle = parcel2.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel2) : null;
                    return bundle;
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
            public final Bundle getUserProfile2(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IHealth");
                    parcel.writeString(string2);
                    this.a.transact(11, parcel, parcel2, 0);
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
            public final Bundle isHealthDataPermissionAcquired(Bundle object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IHealth");
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(8, parcel, parcel2, 0);
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel2) : null;
                    return object;
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
            public final Bundle isHealthDataPermissionAcquired2(String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IHealth");
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(14, parcel, parcel2, 0);
                    parcel2.readException();
                    string2 = parcel2.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel2) : null;
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public final boolean isKeyAccessible() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block2: {
                    bl = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IHealth");
                        this.a.transact(9, parcel, parcel2, 0);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public final HealthResultReceiver requestHealthDataPermissions(Bundle object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IHealth");
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(7, parcel, parcel2, 0);
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? (HealthResultReceiver)HealthResultReceiver.CREATOR.createFromParcel(parcel2) : null;
                    return object;
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
            public final Intent requestHealthDataPermissions2(String string2, HealthResultReceiver healthResultReceiver, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IHealth");
                    parcel.writeString(string2);
                    if (healthResultReceiver != null) {
                        parcel.writeInt(1);
                        healthResultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(13, parcel, parcel2, 0);
                    parcel2.readException();
                    string2 = parcel2.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel(parcel2) : null;
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
            public final HealthResultReceiver waitForInit(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IHealth");
                    parcel.writeLong(l);
                    this.a.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    HealthResultReceiver healthResultReceiver = parcel2.readInt() != 0 ? (HealthResultReceiver)HealthResultReceiver.CREATOR.createFromParcel(parcel2) : null;
                    return healthResultReceiver;
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
            public final void waitForInit2(String string2, HealthResultReceiver healthResultReceiver, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IHealth");
                    parcel.writeString(string2);
                    if (healthResultReceiver != null) {
                        parcel.writeInt(1);
                        healthResultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeLong(l);
                    this.a.transact(12, parcel, parcel2, 0);
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

