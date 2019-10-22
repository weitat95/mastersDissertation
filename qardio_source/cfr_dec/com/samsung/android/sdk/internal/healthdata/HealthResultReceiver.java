/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.samsung.android.sdk.internal.healthdata;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import com.samsung.android.sdk.healthdata.HealthPermissionManager;
import com.samsung.android.sdk.healthdata.HealthResultHolder;
import com.samsung.android.sdk.internal.healthdata.ICallbackRegister;
import com.samsung.android.sdk.internal.healthdata.IHealthResultReceiver;
import com.samsung.android.sdk.internal.healthdata.RemoteResultListener;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class HealthResultReceiver
implements Parcelable {
    public static final Parcelable.Creator<HealthResultReceiver> CREATOR = new Parcelable.Creator<HealthResultReceiver>(){

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            int n = parcel.dataPosition();
            parcel.readInt();
            parcel.readStrongBinder();
            int n2 = parcel.readInt();
            parcel.setDataPosition(n);
            switch (n2) {
                default: {
                    throw new IllegalArgumentException("Invalid result parcel type : " + n2);
                }
                case 1: {
                    return new Sync(parcel, 0);
                }
                case 0: {
                    return new Async(parcel, 0);
                }
                case 2: 
            }
            return new ForwardAsync(parcel, 0);
        }
    };
    private final int a;

    private HealthResultReceiver() {
        this.a = 0;
    }

    /* synthetic */ HealthResultReceiver(byte by) {
        this();
    }

    private HealthResultReceiver(Parcel parcel) {
        this.a = parcel.readInt();
    }

    /* synthetic */ HealthResultReceiver(Parcel parcel, byte by) {
        this(parcel);
    }

    static /* synthetic */ ClassLoader a(int n) {
        switch (n) {
            default: {
                return HealthResultHolder.BaseResult.class.getClassLoader();
            }
            case 1: {
                return HealthDataResolver.ReadResult.class.getClassLoader();
            }
            case 2: {
                return HealthDataResolver.AggregateResult.class.getClassLoader();
            }
            case 3: 
        }
        return HealthPermissionManager.PermissionResult.class.getClassLoader();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.a);
    }

    public static class Async
    extends HealthResultReceiver {
        private RemoteResultListener a;
        private ICallbackRegister b;
        private IHealthResultReceiver c;
        private final int d;
        private final int e;
        private Intent f;

        protected Async() {
            super((byte)0);
            this.d = 0;
            this.e = 0;
        }

        private Async(Parcel parcel) {
            super(parcel, (byte)0);
            this.c = new b(this, 0);
            this.b = ICallbackRegister.Stub.asInterface(parcel.readStrongBinder());
            parcel.readInt();
            this.d = parcel.readInt();
            this.e = parcel.readInt();
            parcel.readBundle(HealthResultReceiver.a(this.e));
            this.f = (Intent)parcel.readParcelable(Intent.class.getClassLoader());
            try {
                this.b.setCallback(0, this.c);
                return;
            }
            catch (RemoteException remoteException) {
                return;
            }
        }

        /* synthetic */ Async(Parcel parcel, byte by) {
            this(parcel);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void a(int n, Bundle object) {
            if (this.a == null) {
                return;
            }
            object.setClassLoader(HealthPermissionManager.PermissionResult.class.getClassLoader());
            switch (object.getInt("type", -1)) {
                default: {
                    HealthResultHolder.BaseResult baseResult = (HealthResultHolder.BaseResult)object.getParcelable("parcel");
                    this.a.onReceiveHealthResult(n, baseResult);
                    break;
                }
                case 1: {
                    HealthDataResolver.ReadResult readResult = (HealthDataResolver.ReadResult)object.getParcelable("parcel");
                    this.a.onReceiveHealthResult(n, readResult);
                    break;
                }
                case 2: {
                    HealthDataResolver.AggregateResult aggregateResult = (HealthDataResolver.AggregateResult)object.getParcelable("parcel");
                    this.a.onReceiveHealthResult(n, aggregateResult);
                    break;
                }
                case 3: {
                    int n2 = object.getInt("PERMISSION_RESULT_COUNT");
                    object.remove("PERMISSION_RESULT_COUNT");
                    object.remove("type");
                    HealthPermissionManager.PermissionResult permissionResult = new HealthPermissionManager.PermissionResult((Bundle)object, n2);
                    this.a.onReceiveHealthResult(n, permissionResult);
                }
            }
            this.a = null;
        }

        protected void onCancelResult(int n) {
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void writeToParcel(Parcel parcel, int n) {
            void var2_2;
            super.writeToParcel(parcel, (int)var2_2);
            synchronized (this) {
                if (this.b == null) {
                    this.b = new a(this, 0);
                }
                parcel.writeStrongBinder(this.b.asBinder());
            }
            parcel.writeInt(0);
            parcel.writeInt(this.d);
            parcel.writeInt(this.e);
            parcel.writeBundle(null);
            parcel.writeParcelable((Parcelable)this.f, 0);
        }

        final class a
        extends ICallbackRegister.Stub {
            private /* synthetic */ Async a;

            private a(Async async) {
                this.a = async;
            }

            /* synthetic */ a(Async async, byte by) {
                this(async);
            }

            @Override
            public final void cancel(int n) throws RemoteException {
                this.a.onCancelResult(n);
            }

            @Override
            public final void setCallback(int n, IHealthResultReceiver iHealthResultReceiver) throws RemoteException {
                this.a.c = iHealthResultReceiver;
            }
        }

        final class b
        extends IHealthResultReceiver.Stub {
            private /* synthetic */ Async a;

            private b(Async async) {
                this.a = async;
            }

            /* synthetic */ b(Async async, byte by) {
                this(async);
            }

            @Override
            public final void send(int n, Bundle bundle) {
                this.a.a(n, bundle);
            }
        }

    }

    public static class ForwardAsync
    extends HealthResultReceiver {
        private IHealthResultReceiver a;
        private RemoteResultListener b;
        private final AtomicBoolean c;

        public ForwardAsync() {
            super((byte)0);
            this.a = new b(this, 0);
            this.c = new AtomicBoolean(false);
        }

        private ForwardAsync(Parcel parcel) {
            super(parcel, (byte)0);
            this.a = IHealthResultReceiver.Stub.asInterface(parcel.readStrongBinder());
            parcel.readInt();
            this.c = new AtomicBoolean(false);
        }

        /* synthetic */ ForwardAsync(Parcel parcel, byte by) {
            this(parcel);
        }

        /*
         * Enabled aggressive block sorting
         */
        static /* synthetic */ void a(ForwardAsync forwardAsync, int n, Bundle object) {
            if (forwardAsync.c.get() || forwardAsync.b == null) {
                return;
            }
            object.setClassLoader(HealthPermissionManager.PermissionResult.class.getClassLoader());
            switch (object.getInt("type", -1)) {
                default: {
                    HealthResultHolder.BaseResult baseResult = (HealthResultHolder.BaseResult)object.getParcelable("parcel");
                    forwardAsync.b.onReceiveHealthResult(n, baseResult);
                    break;
                }
                case 1: {
                    HealthDataResolver.ReadResult readResult = (HealthDataResolver.ReadResult)object.getParcelable("parcel");
                    if (readResult != null) {
                        readResult.setResultId(object.getString("result_identifier"));
                    }
                    forwardAsync.b.onReceiveHealthResult(n, readResult);
                    break;
                }
                case 2: {
                    HealthDataResolver.AggregateResult aggregateResult = (HealthDataResolver.AggregateResult)object.getParcelable("parcel");
                    forwardAsync.b.onReceiveHealthResult(n, aggregateResult);
                    break;
                }
                case 3: {
                    int n2 = object.getInt("PERMISSION_RESULT_COUNT");
                    object.remove("PERMISSION_RESULT_COUNT");
                    object.remove("type");
                    HealthPermissionManager.PermissionResult permissionResult = new HealthPermissionManager.PermissionResult((Bundle)object, n2);
                    forwardAsync.b.onReceiveHealthResult(n, permissionResult);
                }
            }
            forwardAsync.b = null;
        }

        public void cancel() throws RemoteException {
            this.c.set(true);
        }

        public <T extends HealthResultHolder.BaseResult> void registerListener(RemoteResultListener<T> remoteResultListener) {
            this.b = remoteResultListener;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            if (!(this.a instanceof a)) {
                this.a = new a(this, 0);
            }
            parcel.writeStrongBinder(this.a.asBinder());
            parcel.writeInt(2);
        }

        final class a
        extends IHealthResultReceiver.Stub {
            private /* synthetic */ ForwardAsync a;

            private a(ForwardAsync forwardAsync) {
                this.a = forwardAsync;
            }

            /* synthetic */ a(ForwardAsync forwardAsync, byte by) {
                this(forwardAsync);
            }

            @Override
            public final void send(int n, Bundle bundle) {
                ForwardAsync.a(this.a, n, bundle);
            }
        }

        final class b
        implements IHealthResultReceiver {
            private /* synthetic */ ForwardAsync a;

            private b(ForwardAsync forwardAsync) {
                this.a = forwardAsync;
            }

            /* synthetic */ b(ForwardAsync forwardAsync, byte by) {
                this(forwardAsync);
            }

            public final IBinder asBinder() {
                throw new UnsupportedOperationException();
            }

            @Override
            public final void send(int n, Bundle bundle) {
                ForwardAsync.a(this.a, n, bundle);
            }
        }

    }

    public static class Sync
    extends HealthResultReceiver {
        private final int a;
        private final int b;
        private final Bundle c;

        private Sync(Parcel parcel) {
            super(parcel, (byte)0);
            parcel.readStrongBinder();
            parcel.readInt();
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            this.c = parcel.readBundle(HealthResultReceiver.a(this.b));
        }

        /* synthetic */ Sync(Parcel parcel, byte by) {
            this(parcel);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeStrongBinder(null);
            parcel.writeInt(1);
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeBundle(this.c);
            parcel.writeParcelable(null, 0);
        }
    }

}

