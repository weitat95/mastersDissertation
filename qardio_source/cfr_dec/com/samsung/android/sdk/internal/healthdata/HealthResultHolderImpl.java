/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.util.Log
 *  android.util.Pair
 */
package com.samsung.android.sdk.internal.healthdata;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.sdk.healthdata.HealthResultHolder;
import com.samsung.android.sdk.internal.healthdata.RemoteResultListener;
import java.util.concurrent.CountDownLatch;

public class HealthResultHolderImpl<T extends HealthResultHolder.BaseResult>
implements HealthResultHolder<T>,
RemoteResultListener<T> {
    private final Object a = new Object();
    private final a<T> b;
    private final CountDownLatch c = new CountDownLatch(1);
    private HealthResultHolder.ResultListener<T> d;
    private volatile T e;
    private volatile boolean f;
    private boolean g;
    private boolean h;

    HealthResultHolderImpl() {
        this.b = new a();
    }

    HealthResultHolderImpl(Looper looper) {
        this.b = new a(looper);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private T a() {
        Object object = this.a;
        synchronized (object) {
            this.c();
            this.b();
            T t = this.e;
            this.clearStatus();
            return t;
        }
    }

    private void b() {
        if (this.f) {
            throw new IllegalStateException("Result has already been processed");
        }
    }

    private void c() {
        if (!this.isReady()) {
            throw new IllegalStateException("Result is not ready");
        }
    }

    public static <T extends HealthResultHolder.BaseResult> HealthResultHolderImpl<T> createAndSetResult(T t, Looper object) {
        object = new HealthResultHolderImpl<T>((Looper)object);
        ((HealthResultHolderImpl)object).setResult(t);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final T await() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("await() must not be called on the main thread");
        }
        this.b();
        try {
            this.c.await();
        }
        catch (InterruptedException interruptedException) {
            Object object = this.a;
            synchronized (object) {
                if (!this.isReady()) {
                    this.h = true;
                }
            }
        }
        this.c();
        return this.a();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void cancel() {
        Object object = this.a;
        synchronized (object) {
            if (this.g || this.f) {
                return;
            }
            try {
                this.cancelResult();
            }
            catch (RemoteException remoteException) {
                Log.d((String)"Health.ResultHolder", (String)remoteException.toString());
            }
            this.d = null;
            this.g = true;
            return;
        }
    }

    protected void cancelResult() throws RemoteException {
    }

    protected void clearStatus() {
        this.f = true;
        this.e = null;
        this.d = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean isCanceled() {
        Object object = this.a;
        synchronized (object) {
            return this.g;
        }
    }

    public final boolean isReady() {
        return this.c.getCount() == 0L;
    }

    @Override
    public void onReceiveHealthResult(int n, T t) {
        this.setResult(t);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void setResult(T t) {
        Object object = this.a;
        synchronized (object) {
            if (this.h || this.g) {
                return;
            }
            if (this.isReady()) {
                throw new IllegalStateException("Result have been set already");
            }
            this.b();
            this.e = t;
            this.c.countDown();
            if (this.d != null && !this.g) {
                this.b.a(this.d, this.a());
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void setResultListener(HealthResultHolder.ResultListener<T> resultListener) {
        this.b();
        Object object = this.a;
        synchronized (object) {
            if (this.isCanceled()) {
                return;
            }
            if (this.isReady()) {
                this.b.a(resultListener, this.a());
            } else {
                this.d = resultListener;
            }
            return;
        }
    }

    static final class a<T extends HealthResultHolder.BaseResult>
    extends Handler {
        public a() {
            this(Looper.getMainLooper());
        }

        public a(Looper looper) {
            super(looper);
        }

        public final void a(HealthResultHolder.ResultListener<T> resultListener, T t) {
            this.sendMessage(this.obtainMessage(1, (Object)new Pair(resultListener, t)));
        }

        /*
         * Enabled aggressive block sorting
         */
        public final void handleMessage(Message object) {
            switch (((Message)object).what) {
                default: {
                    Log.d((String)"Health.ResultHolder", (String)"No default handler");
                    return;
                }
                case 1: {
                    Pair pair = (Pair)((Message)object).obj;
                    object = (HealthResultHolder.ResultListener)pair.first;
                    HealthResultHolder.BaseResult baseResult = (HealthResultHolder.BaseResult)pair.second;
                    if (object == null) return;
                    object.onResult(baseResult);
                    return;
                }
            }
        }
    }

}

