/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package android.support.v4.os;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.os.IResultReceiver;

public class ResultReceiver
implements Parcelable {
    public static final Parcelable.Creator<ResultReceiver> CREATOR = new Parcelable.Creator<ResultReceiver>(){

        public ResultReceiver createFromParcel(Parcel parcel) {
            return new ResultReceiver(parcel);
        }

        public ResultReceiver[] newArray(int n) {
            return new ResultReceiver[n];
        }
    };
    final Handler mHandler = null;
    final boolean mLocal;
    IResultReceiver mReceiver;

    ResultReceiver(Parcel parcel) {
        this.mLocal = false;
        this.mReceiver = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
    }

    public int describeContents() {
        return 0;
    }

    protected void onReceiveResult(int n, Bundle bundle) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void send(int n, Bundle bundle) {
        if (this.mLocal) {
            if (this.mHandler == null) {
                this.onReceiveResult(n, bundle);
                return;
            }
            this.mHandler.post((Runnable)new MyRunnable(n, bundle));
            return;
        } else {
            if (this.mReceiver == null) return;
            {
                try {
                    this.mReceiver.send(n, bundle);
                    return;
                }
                catch (RemoteException remoteException) {
                    return;
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void writeToParcel(Parcel parcel, int n) {
        synchronized (this) {
            if (this.mReceiver == null) {
                this.mReceiver = new MyResultReceiver();
            }
            parcel.writeStrongBinder(this.mReceiver.asBinder());
            return;
        }
    }

    class MyResultReceiver
    extends IResultReceiver.Stub {
        MyResultReceiver() {
        }

        @Override
        public void send(int n, Bundle bundle) {
            if (ResultReceiver.this.mHandler != null) {
                ResultReceiver.this.mHandler.post((Runnable)new MyRunnable(n, bundle));
                return;
            }
            ResultReceiver.this.onReceiveResult(n, bundle);
        }
    }

    class MyRunnable
    implements Runnable {
        final int mResultCode;
        final Bundle mResultData;

        MyRunnable(int n, Bundle bundle) {
            this.mResultCode = n;
            this.mResultData = bundle;
        }

        @Override
        public void run() {
            ResultReceiver.this.onReceiveResult(this.mResultCode, this.mResultData);
        }
    }

}

