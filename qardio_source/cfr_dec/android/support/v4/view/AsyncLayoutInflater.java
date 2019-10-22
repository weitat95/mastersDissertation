/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Message
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.v4.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.Pools;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.concurrent.ArrayBlockingQueue;

public final class AsyncLayoutInflater {
    Handler mHandler;
    private Handler.Callback mHandlerCallback = new Handler.Callback(){

        public boolean handleMessage(Message object) {
            object = (InflateRequest)object.obj;
            if (object.view == null) {
                object.view = AsyncLayoutInflater.this.mInflater.inflate(object.resid, object.parent, false);
            }
            object.callback.onInflateFinished(object.view, object.resid, object.parent);
            AsyncLayoutInflater.this.mInflateThread.releaseRequest((InflateRequest)object);
            return true;
        }
    };
    InflateThread mInflateThread;
    LayoutInflater mInflater;

    public AsyncLayoutInflater(Context context) {
        this.mInflater = new BasicInflater(context);
        this.mHandler = new Handler(this.mHandlerCallback);
        this.mInflateThread = InflateThread.getInstance();
    }

    private static class BasicInflater
    extends LayoutInflater {
        private static final String[] sClassPrefixList = new String[]{"android.widget.", "android.webkit.", "android.app."};

        BasicInflater(Context context) {
            super(context);
        }

        public LayoutInflater cloneInContext(Context context) {
            return new BasicInflater(context);
        }

        protected View onCreateView(String string2, AttributeSet attributeSet) throws ClassNotFoundException {
            for (String string3 : sClassPrefixList) {
                try {
                    string3 = this.createView(string2, string3, attributeSet);
                    if (string3 == null) continue;
                    return string3;
                }
                catch (ClassNotFoundException classNotFoundException) {
                    // empty catch block
                }
            }
            return super.onCreateView(string2, attributeSet);
        }
    }

    private static class InflateRequest {
        OnInflateFinishedListener callback;
        AsyncLayoutInflater inflater;
        ViewGroup parent;
        int resid;
        View view;

        InflateRequest() {
        }
    }

    private static class InflateThread
    extends Thread {
        private static final InflateThread sInstance = new InflateThread();
        private ArrayBlockingQueue<InflateRequest> mQueue = new ArrayBlockingQueue(10);
        private Pools.SynchronizedPool<InflateRequest> mRequestPool = new Pools.SynchronizedPool(10);

        static {
            sInstance.start();
        }

        private InflateThread() {
        }

        public static InflateThread getInstance() {
            return sInstance;
        }

        public void releaseRequest(InflateRequest inflateRequest) {
            inflateRequest.callback = null;
            inflateRequest.inflater = null;
            inflateRequest.parent = null;
            inflateRequest.resid = 0;
            inflateRequest.view = null;
            this.mRequestPool.release(inflateRequest);
        }

        @Override
        public void run() {
            do {
                this.runInner();
            } while (true);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void runInner() {
            InflateRequest inflateRequest;
            try {
                inflateRequest = this.mQueue.take();
            }
            catch (InterruptedException interruptedException) {
                Log.w((String)"AsyncLayoutInflater", (Throwable)interruptedException);
                return;
            }
            try {
                inflateRequest.view = inflateRequest.inflater.mInflater.inflate(inflateRequest.resid, inflateRequest.parent, false);
            }
            catch (RuntimeException runtimeException) {
                Log.w((String)"AsyncLayoutInflater", (String)"Failed to inflate resource in the background! Retrying on the UI thread", (Throwable)runtimeException);
            }
            Message.obtain((Handler)inflateRequest.inflater.mHandler, (int)0, (Object)inflateRequest).sendToTarget();
        }
    }

    public static interface OnInflateFinishedListener {
        public void onInflateFinished(View var1, int var2, ViewGroup var3);
    }

}

