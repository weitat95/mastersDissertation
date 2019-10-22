/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.samsung.android.sdk.healthdata;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.samsung.android.sdk.healthdata.HealthDevice;
import java.util.ArrayList;
import java.util.List;

public interface IDeviceManager
extends IInterface {
    public boolean changeDeviceName(String var1, String var2) throws RemoteException;

    public List<HealthDevice> getAllRegisteredDevices() throws RemoteException;

    public HealthDevice getDeviceByUuid(String var1) throws RemoteException;

    public List<String> getDeviceUuidsBy(String var1, int var2) throws RemoteException;

    public HealthDevice getLocalDevice() throws RemoteException;

    public HealthDevice getRegisteredDevice(String var1) throws RemoteException;

    public HealthDevice getRegisteredDeviceByUuid(String var1) throws RemoteException;

    public String registerDevice(HealthDevice var1) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IDeviceManager {
        public static IDeviceManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.samsung.android.sdk.healthdata.IDeviceManager");
            if (iInterface != null && iInterface instanceof IDeviceManager) {
                return (IDeviceManager)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            int n3 = 0;
            switch (n) {
                default: {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                case 1598968902: {
                    parcel.writeString("com.samsung.android.sdk.healthdata.IDeviceManager");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDeviceManager");
                    object = this.getLocalDevice();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        ((HealthDevice)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDeviceManager");
                    object = this.getAllRegisteredDevices();
                    parcel.writeNoException();
                    parcel.writeTypedList((List)object);
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDeviceManager");
                    object = this.getRegisteredDevice(object.readString());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        ((HealthDevice)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDeviceManager");
                    object = this.getRegisteredDeviceByUuid(object.readString());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        ((HealthDevice)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 5: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDeviceManager");
                    object = object.readInt() != 0 ? (HealthDevice)HealthDevice.CREATOR.createFromParcel((Parcel)object) : null;
                    object = this.registerDevice((HealthDevice)object);
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 6: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDeviceManager");
                    boolean bl = this.changeDeviceName(object.readString(), object.readString());
                    parcel.writeNoException();
                    n = n3;
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    return true;
                }
                case 7: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDeviceManager");
                    object = this.getDeviceByUuid(object.readString());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        ((HealthDevice)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 8: 
            }
            object.enforceInterface("com.samsung.android.sdk.healthdata.IDeviceManager");
            object = this.getDeviceUuidsBy(object.readString(), object.readInt());
            parcel.writeNoException();
            parcel.writeStringList((List)object);
            return true;
        }

        static final class a
        implements IDeviceManager {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            public final IBinder asBinder() {
                return this.a;
            }

            @Override
            public final boolean changeDeviceName(String string2, String string3) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block2: {
                    bl = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDeviceManager");
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        this.a.transact(6, parcel, parcel2, 0);
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

            @Override
            public final List<HealthDevice> getAllRegisteredDevices() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDeviceManager");
                    this.a.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    ArrayList arrayList = parcel2.createTypedArrayList(HealthDevice.CREATOR);
                    return arrayList;
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
            public final HealthDevice getDeviceByUuid(String object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDeviceManager");
                    parcel.writeString((String)object);
                    this.a.transact(7, parcel, parcel2, 0);
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? (HealthDevice)HealthDevice.CREATOR.createFromParcel(parcel2) : null;
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public final List<String> getDeviceUuidsBy(String object, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDeviceManager");
                    parcel.writeString((String)object);
                    parcel.writeInt(n);
                    this.a.transact(8, parcel, parcel2, 0);
                    parcel2.readException();
                    object = parcel2.createStringArrayList();
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
            public final HealthDevice getLocalDevice() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDeviceManager");
                    this.a.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    HealthDevice healthDevice = parcel2.readInt() != 0 ? (HealthDevice)HealthDevice.CREATOR.createFromParcel(parcel2) : null;
                    return healthDevice;
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
            public final HealthDevice getRegisteredDevice(String object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDeviceManager");
                    parcel.writeString((String)object);
                    this.a.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? (HealthDevice)HealthDevice.CREATOR.createFromParcel(parcel2) : null;
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
            public final HealthDevice getRegisteredDeviceByUuid(String object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDeviceManager");
                    parcel.writeString((String)object);
                    this.a.transact(4, parcel, parcel2, 0);
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? (HealthDevice)HealthDevice.CREATOR.createFromParcel(parcel2) : null;
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
            public final String registerDevice(HealthDevice object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDeviceManager");
                    if (object != null) {
                        parcel.writeInt(1);
                        ((HealthDevice)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(5, parcel, parcel2, 0);
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

