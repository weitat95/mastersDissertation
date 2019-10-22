/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 *  android.os.TransactionTooLargeException
 */
package com.samsung.android.sdk.healthdata;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.TransactionTooLargeException;
import com.samsung.android.sdk.healthdata.HealthData;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthResultHolder;
import com.samsung.android.sdk.healthdata.IDataResolver;
import com.samsung.android.sdk.internal.database.BulkCursorDescriptor;
import com.samsung.android.sdk.internal.database.BulkCursorToCursorAdaptor;
import com.samsung.android.sdk.internal.healthdata.DeleteRequestImpl;
import com.samsung.android.sdk.internal.healthdata.ErrorUtil;
import com.samsung.android.sdk.internal.healthdata.HealthResultHolderImpl;
import com.samsung.android.sdk.internal.healthdata.HealthResultReceiver;
import com.samsung.android.sdk.internal.healthdata.InsertRequestImpl;
import com.samsung.android.sdk.internal.healthdata.IpcUtil;
import com.samsung.android.sdk.internal.healthdata.ReadRequestImpl;
import com.samsung.android.sdk.internal.healthdata.UpdateRequestImpl;
import com.samsung.android.sdk.internal.healthdata.query.AndFilter;
import com.samsung.android.sdk.internal.healthdata.query.ComparisonFilter;
import com.samsung.android.sdk.internal.healthdata.query.NotFilter;
import com.samsung.android.sdk.internal.healthdata.query.NumberArrayFilter;
import com.samsung.android.sdk.internal.healthdata.query.OrFilter;
import com.samsung.android.sdk.internal.healthdata.query.StringArrayFilter;
import com.samsung.android.sdk.internal.healthdata.query.StringFilter;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class HealthDataResolver {
    private final HealthDataStore a;
    private final Handler b;

    public HealthDataResolver(HealthDataStore healthDataStore, Handler handler) {
        this.a = healthDataStore;
        this.b = handler;
    }

    private IDataResolver a() {
        IDataResolver iDataResolver;
        block3: {
            try {
                iDataResolver = HealthDataStore.getInterface(this.a).getIDataResolver();
                if (iDataResolver != null) break block3;
            }
            catch (RemoteException remoteException) {
                throw new IllegalStateException(ErrorUtil.getRemoteExceptionMessage((Exception)((Object)remoteException)));
            }
            throw new IllegalStateException("IDataResolver is null");
        }
        return iDataResolver;
    }

    private static void a(String string2, List<HealthData> object) throws RemoteException {
        object = object.iterator();
        while (object.hasNext()) {
            HealthData healthData = (HealthData)object.next();
            Iterator<String> iterator = healthData.getBlobKeySet().iterator();
            while (iterator.hasNext()) {
                IpcUtil.sendBlob(string2, healthData, iterator.next());
            }
            iterator = healthData.getInputStreamKeySet().iterator();
            while (iterator.hasNext()) {
                IpcUtil.sendStream(string2, healthData, iterator.next());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private Looper b() {
        Looper looper = this.b != null ? this.b.getLooper() : Looper.myLooper();
        if (looper == null) {
            throw new IllegalStateException("This thread has no looper");
        }
        return looper;
    }

    public HealthResultHolder<HealthResultHolder.BaseResult> delete(DeleteRequest deleteRequest) {
        if (!(deleteRequest instanceof DeleteRequestImpl)) {
            throw new IllegalArgumentException("Invalid request instance");
        }
        IDataResolver iDataResolver = this.a();
        Object object = this.b();
        deleteRequest = (DeleteRequestImpl)deleteRequest;
        try {
            HealthResultReceiver.ForwardAsync forwardAsync = new HealthResultReceiver.ForwardAsync();
            object = IpcUtil.makeResultHolder(forwardAsync, object);
            iDataResolver.deleteData2(this.a.a().getPackageName(), forwardAsync, (DeleteRequestImpl)deleteRequest);
            return object;
        }
        catch (RemoteException remoteException) {
            throw new IllegalStateException(ErrorUtil.getRemoteExceptionMessage((Exception)((Object)remoteException)));
        }
    }

    public HealthResultHolder<HealthResultHolder.BaseResult> insert(InsertRequest insertRequest) {
        if (!(insertRequest instanceof InsertRequestImpl)) {
            throw new IllegalArgumentException("Invalid request instance");
        }
        IDataResolver iDataResolver = this.a();
        Object object = this.b();
        if (((InsertRequestImpl)(insertRequest = (InsertRequestImpl)insertRequest)).isEmpty()) {
            return HealthResultHolderImpl.createAndSetResult(new HealthResultHolder.BaseResult(1, 0), object);
        }
        try {
            HealthDataResolver.a(((InsertRequestImpl)insertRequest).getDataType(), ((InsertRequestImpl)insertRequest).getItems());
            HealthResultReceiver.ForwardAsync forwardAsync = new HealthResultReceiver.ForwardAsync();
            object = IpcUtil.makeResultHolder(forwardAsync, object);
            iDataResolver.insertData2(this.a.a().getPackageName(), forwardAsync, (InsertRequestImpl)insertRequest);
            return object;
        }
        catch (TransactionTooLargeException transactionTooLargeException) {
            throw new IllegalArgumentException(transactionTooLargeException.toString());
        }
        catch (RemoteException remoteException) {
            throw new IllegalStateException(ErrorUtil.getRemoteExceptionMessage((Exception)((Object)remoteException)));
        }
    }

    public HealthResultHolder<ReadResult> read(ReadRequest readRequest) {
        if (!(readRequest instanceof ReadRequestImpl)) {
            throw new IllegalArgumentException("Invalid request instance");
        }
        IDataResolver iDataResolver = this.a();
        Object object = this.b();
        readRequest = (ReadRequestImpl)readRequest;
        try {
            HealthResultReceiver.ForwardAsync forwardAsync = new HealthResultReceiver.ForwardAsync();
            object = IpcUtil.makeResultHolder(forwardAsync, object);
            iDataResolver.readData2(this.a.a().getPackageName(), forwardAsync, (ReadRequestImpl)readRequest);
            return object;
        }
        catch (RemoteException remoteException) {
            throw new IllegalStateException(ErrorUtil.getRemoteExceptionMessage((Exception)((Object)remoteException)));
        }
    }

    public HealthResultHolder<HealthResultHolder.BaseResult> update(UpdateRequest updateRequest) {
        if (!(updateRequest instanceof UpdateRequestImpl)) {
            throw new IllegalArgumentException("Invalid request instance");
        }
        IDataResolver iDataResolver = this.a();
        Object object = this.b();
        updateRequest = (UpdateRequestImpl)updateRequest;
        try {
            HealthDataResolver.a(((UpdateRequestImpl)updateRequest).getDataType(), Collections.singletonList(((UpdateRequestImpl)updateRequest).getDataObject()));
            HealthResultReceiver.ForwardAsync forwardAsync = new HealthResultReceiver.ForwardAsync();
            object = IpcUtil.makeResultHolder(forwardAsync, object);
            iDataResolver.updateData2(this.a.a().getPackageName(), forwardAsync, (UpdateRequestImpl)updateRequest);
            return object;
        }
        catch (TransactionTooLargeException transactionTooLargeException) {
            throw new IllegalArgumentException(transactionTooLargeException.toString());
        }
        catch (RemoteException remoteException) {
            throw new IllegalStateException(ErrorUtil.getRemoteExceptionMessage((Exception)((Object)remoteException)));
        }
    }

    public static interface AggregateRequest {

        public static enum AggregateFunction {
            SUMjava.lang.IllegalArgumentException: fromIndex(2) > toIndex(1)

