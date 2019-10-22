/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Binder
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
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.samsung.android.sdk.internal.healthdata.AggregateRequestImpl;
import com.samsung.android.sdk.internal.healthdata.DeleteRequestImpl;
import com.samsung.android.sdk.internal.healthdata.HealthResultReceiver;
import com.samsung.android.sdk.internal.healthdata.InsertRequestImpl;
import com.samsung.android.sdk.internal.healthdata.ReadRequestImpl;
import com.samsung.android.sdk.internal.healthdata.UpdateRequestImpl;

public interface IDataResolver
extends IInterface {
    public HealthResultReceiver aggregateData(AggregateRequestImpl var1) throws RemoteException;

    public void aggregateData2(String var1, HealthResultReceiver var2, AggregateRequestImpl var3) throws RemoteException;

    public HealthResultReceiver deleteData(DeleteRequestImpl var1) throws RemoteException;

    public void deleteData2(String var1, HealthResultReceiver var2, DeleteRequestImpl var3) throws RemoteException;

    public Intent deleteDataWithPermission(String var1, HealthResultReceiver var2, DeleteRequestImpl var3) throws RemoteException;

    public HealthResultReceiver insertData(InsertRequestImpl var1) throws RemoteException;

    public void insertData2(String var1, HealthResultReceiver var2, InsertRequestImpl var3) throws RemoteException;

    public Intent insertDataWithPermission(String var1, HealthResultReceiver var2, InsertRequestImpl var3) throws RemoteException;

    public HealthResultReceiver readData(ReadRequestImpl var1) throws RemoteException;

    public void readData2(String var1, HealthResultReceiver var2, ReadRequestImpl var3) throws RemoteException;

    public Intent readDataWithPermission(String var1, HealthResultReceiver var2, ReadRequestImpl var3) throws RemoteException;

    public HealthResultReceiver updateData(UpdateRequestImpl var1) throws RemoteException;

    public void updateData2(String var1, HealthResultReceiver var2, UpdateRequestImpl var3) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IDataResolver {
        public static IDataResolver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.samsung.android.sdk.healthdata.IDataResolver");
            if (iInterface != null && iInterface instanceof IDataResolver) {
                return (IDataResolver)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            switch (n) {
                default: {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                case 1598968902: {
                    parcel.writeString("com.samsung.android.sdk.healthdata.IDataResolver");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDataResolver");
                    object = object.readInt() != 0 ? (ReadRequestImpl)ReadRequestImpl.CREATOR.createFromParcel((Parcel)object) : null;
                    object = this.readData((ReadRequestImpl)object);
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        ((HealthResultReceiver)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDataResolver");
                    object = object.readInt() != 0 ? (InsertRequestImpl)InsertRequestImpl.CREATOR.createFromParcel((Parcel)object) : null;
                    object = this.insertData((InsertRequestImpl)object);
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        ((HealthResultReceiver)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDataResolver");
                    object = object.readInt() != 0 ? (DeleteRequestImpl)DeleteRequestImpl.CREATOR.createFromParcel((Parcel)object) : null;
                    object = this.deleteData((DeleteRequestImpl)object);
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
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDataResolver");
                    object = object.readInt() != 0 ? (AggregateRequestImpl)AggregateRequestImpl.CREATOR.createFromParcel((Parcel)object) : null;
                    object = this.aggregateData((AggregateRequestImpl)object);
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        ((HealthResultReceiver)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 5: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDataResolver");
                    object = object.readInt() != 0 ? (UpdateRequestImpl)UpdateRequestImpl.CREATOR.createFromParcel((Parcel)object) : null;
                    object = this.updateData((UpdateRequestImpl)object);
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        ((HealthResultReceiver)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 6: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDataResolver");
                    String string2 = object.readString();
                    HealthResultReceiver healthResultReceiver = object.readInt() != 0 ? (HealthResultReceiver)HealthResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                    object = object.readInt() != 0 ? (ReadRequestImpl)ReadRequestImpl.CREATOR.createFromParcel((Parcel)object) : null;
                    this.readData2(string2, healthResultReceiver, (ReadRequestImpl)object);
                    parcel.writeNoException();
                    return true;
                }
                case 7: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDataResolver");
                    String string3 = object.readString();
                    HealthResultReceiver healthResultReceiver = object.readInt() != 0 ? (HealthResultReceiver)HealthResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                    object = object.readInt() != 0 ? (InsertRequestImpl)InsertRequestImpl.CREATOR.createFromParcel((Parcel)object) : null;
                    this.insertData2(string3, healthResultReceiver, (InsertRequestImpl)object);
                    parcel.writeNoException();
                    return true;
                }
                case 8: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDataResolver");
                    String string4 = object.readString();
                    HealthResultReceiver healthResultReceiver = object.readInt() != 0 ? (HealthResultReceiver)HealthResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                    object = object.readInt() != 0 ? (DeleteRequestImpl)DeleteRequestImpl.CREATOR.createFromParcel((Parcel)object) : null;
                    this.deleteData2(string4, healthResultReceiver, (DeleteRequestImpl)object);
                    parcel.writeNoException();
                    return true;
                }
                case 9: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDataResolver");
                    String string5 = object.readString();
                    HealthResultReceiver healthResultReceiver = object.readInt() != 0 ? (HealthResultReceiver)HealthResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                    object = object.readInt() != 0 ? (AggregateRequestImpl)AggregateRequestImpl.CREATOR.createFromParcel((Parcel)object) : null;
                    this.aggregateData2(string5, healthResultReceiver, (AggregateRequestImpl)object);
                    parcel.writeNoException();
                    return true;
                }
                case 10: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDataResolver");
                    String string6 = object.readString();
                    HealthResultReceiver healthResultReceiver = object.readInt() != 0 ? (HealthResultReceiver)HealthResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                    object = object.readInt() != 0 ? (UpdateRequestImpl)UpdateRequestImpl.CREATOR.createFromParcel((Parcel)object) : null;
                    this.updateData2(string6, healthResultReceiver, (UpdateRequestImpl)object);
                    parcel.writeNoException();
                    return true;
                }
                case 11: {
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDataResolver");
                    String string7 = object.readString();
                    HealthResultReceiver healthResultReceiver = object.readInt() != 0 ? (HealthResultReceiver)HealthResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                    object = object.readInt() != 0 ? (ReadRequestImpl)ReadRequestImpl.CREATOR.createFromParcel((Parcel)object) : null;
                    object = this.readDataWithPermission(string7, healthResultReceiver, (ReadRequestImpl)object);
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
                    object.enforceInterface("com.samsung.android.sdk.healthdata.IDataResolver");
                    String string8 = object.readString();
                    HealthResultReceiver healthResultReceiver = object.readInt() != 0 ? (HealthResultReceiver)HealthResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                    object = object.readInt() != 0 ? (InsertRequestImpl)InsertRequestImpl.CREATOR.createFromParcel((Parcel)object) : null;
                    object = this.insertDataWithPermission(string8, healthResultReceiver, (InsertRequestImpl)object);
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 13: 
            }
            object.enforceInterface("com.samsung.android.sdk.healthdata.IDataResolver");
            String string9 = object.readString();
            HealthResultReceiver healthResultReceiver = object.readInt() != 0 ? (HealthResultReceiver)HealthResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
            object = object.readInt() != 0 ? (DeleteRequestImpl)DeleteRequestImpl.CREATOR.createFromParcel((Parcel)object) : null;
            object = this.deleteDataWithPermission(string9, healthResultReceiver, (DeleteRequestImpl)object);
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
        implements IDataResolver {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public final HealthResultReceiver aggregateData(AggregateRequestImpl parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDataResolver");
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        parcelable.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(4, parcel, parcel2, 0);
                    parcel2.readException();
                    parcelable = parcel2.readInt() != 0 ? (HealthResultReceiver)HealthResultReceiver.CREATOR.createFromParcel(parcel2) : null;
                    return parcelable;
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
            public final void aggregateData2(String string2, HealthResultReceiver healthResultReceiver, AggregateRequestImpl aggregateRequestImpl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDataResolver");
                    parcel.writeString(string2);
                    if (healthResultReceiver != null) {
                        parcel.writeInt(1);
                        healthResultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (aggregateRequestImpl != null) {
                        parcel.writeInt(1);
                        aggregateRequestImpl.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(9, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
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
            public final HealthResultReceiver deleteData(DeleteRequestImpl parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDataResolver");
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        parcelable.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    parcelable = parcel2.readInt() != 0 ? (HealthResultReceiver)HealthResultReceiver.CREATOR.createFromParcel(parcel2) : null;
                    return parcelable;
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
            public final void deleteData2(String string2, HealthResultReceiver healthResultReceiver, DeleteRequestImpl deleteRequestImpl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDataResolver");
                    parcel.writeString(string2);
                    if (healthResultReceiver != null) {
                        parcel.writeInt(1);
                        healthResultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (deleteRequestImpl != null) {
                        parcel.writeInt(1);
                        deleteRequestImpl.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(8, parcel, parcel2, 0);
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
            public final Intent deleteDataWithPermission(String string2, HealthResultReceiver healthResultReceiver, DeleteRequestImpl deleteRequestImpl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDataResolver");
                    parcel.writeString(string2);
                    if (healthResultReceiver != null) {
                        parcel.writeInt(1);
                        healthResultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (deleteRequestImpl != null) {
                        parcel.writeInt(1);
                        deleteRequestImpl.writeToParcel(parcel, 0);
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
            public final HealthResultReceiver insertData(InsertRequestImpl parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDataResolver");
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        parcelable.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    parcelable = parcel2.readInt() != 0 ? (HealthResultReceiver)HealthResultReceiver.CREATOR.createFromParcel(parcel2) : null;
                    return parcelable;
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
            public final void insertData2(String string2, HealthResultReceiver healthResultReceiver, InsertRequestImpl insertRequestImpl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDataResolver");
                    parcel.writeString(string2);
                    if (healthResultReceiver != null) {
                        parcel.writeInt(1);
                        healthResultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (insertRequestImpl != null) {
                        parcel.writeInt(1);
                        insertRequestImpl.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(7, parcel, parcel2, 0);
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
            public final Intent insertDataWithPermission(String string2, HealthResultReceiver healthResultReceiver, InsertRequestImpl insertRequestImpl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDataResolver");
                    parcel.writeString(string2);
                    if (healthResultReceiver != null) {
                        parcel.writeInt(1);
                        healthResultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (insertRequestImpl != null) {
                        parcel.writeInt(1);
                        insertRequestImpl.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(12, parcel, parcel2, 0);
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
            public final HealthResultReceiver readData(ReadRequestImpl parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDataResolver");
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        parcelable.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    parcelable = parcel2.readInt() != 0 ? (HealthResultReceiver)HealthResultReceiver.CREATOR.createFromParcel(parcel2) : null;
                    return parcelable;
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
            public final void readData2(String string2, HealthResultReceiver healthResultReceiver, ReadRequestImpl readRequestImpl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDataResolver");
                    parcel.writeString(string2);
                    if (healthResultReceiver != null) {
                        parcel.writeInt(1);
                        healthResultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (readRequestImpl != null) {
                        parcel.writeInt(1);
                        readRequestImpl.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(6, parcel, parcel2, 0);
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
            public final Intent readDataWithPermission(String string2, HealthResultReceiver healthResultReceiver, ReadRequestImpl readRequestImpl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDataResolver");
                    parcel.writeString(string2);
                    if (healthResultReceiver != null) {
                        parcel.writeInt(1);
                        healthResultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (readRequestImpl != null) {
                        parcel.writeInt(1);
                        readRequestImpl.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(11, parcel, parcel2, 0);
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
            public final HealthResultReceiver updateData(UpdateRequestImpl parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDataResolver");
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        parcelable.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(5, parcel, parcel2, 0);
                    parcel2.readException();
                    parcelable = parcel2.readInt() != 0 ? (HealthResultReceiver)HealthResultReceiver.CREATOR.createFromParcel(parcel2) : null;
                    return parcelable;
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
            public final void updateData2(String string2, HealthResultReceiver healthResultReceiver, UpdateRequestImpl updateRequestImpl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.samsung.android.sdk.healthdata.IDataResolver");
                    parcel.writeString(string2);
                    if (healthResultReceiver != null) {
                        parcel.writeInt(1);
                        healthResultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (updateRequestImpl != null) {
                        parcel.writeInt(1);
                        updateRequestImpl.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(10, parcel, parcel2, 0);
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

