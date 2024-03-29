/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.Message
 */
package android.support.v4.provider;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SelfDestructiveThread {
    private Handler.Callback mCallback;
    private final int mDestructAfterMillisec;
    private int mGeneration;
    private Handler mHandler;
    private final Object mLock = new Object();
    private final int mPriority;
    private HandlerThread mThread;
    private final String mThreadName;

    public SelfDestructiveThread(String string2, int n, int n2) {
        this.mCallback = new Handler.Callback(){

            public boolean handleMessage(Message message) {
                switch (message.what) {
                    default: {
                        return true;
                    }
                    case 1: {
                        SelfDestructiveThread.this.onInvokeRunnable((Runnable)message.obj);
                        return true;
                    }
                    case 0: 
                }
                SelfDestructiveThread.this.onDestruction();
                return true;
            }
        };
        this.mThreadName = string2;
        this.mPriority = n;
        this.mDestructAfterMillisec = n2;
        this.mGeneration = 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onDestruction() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mHandler.hasMessages(1)) {
                return;
            }
            this.mThread.quit();
            this.mThread = null;
            this.mHandler = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onInvokeRunnable(Runnable object) {
        object.run();
        object = this.mLock;
        synchronized (object) {
            this.mHandler.removeMessages(0);
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0), (long)this.mDestructAfterMillisec);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void post(Runnable runnable) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mThread == null) {
                this.mThread = new HandlerThread(this.mThreadName, this.mPriority);
                this.mThread.start();
                this.mHandler = new Handler(this.mThread.getLooper(), this.mCallback);
                ++this.mGeneration;
            }
            this.mHandler.removeMessages(0);
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, (Object)runnable));
            return;
        }
    }

    public <T> void postAndReply(final Callable<T> callable, ReplyCallback<T> replyCallback) {
        this.post(new Runnable(new Handler(), replyCallback){
            final /* synthetic */ Handler val$callingHandler;
            final /* synthetic */ ReplyCallback val$reply;
            {
                this.val$callingHandler = handler;
                this.val$reply = replyCallback;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                Object v;
                try {
                    v = callable.call();
                }
                catch (Exception exception) {
                    v = null;
                }
                this.val$callingHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        val$reply.onReply(v);
                    }
                });
            }

        });
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public <T> T postAndWait(final Callable<T> var1_1, int var2_4) throws InterruptedException {
        block7: {
            var7_5 = new ReentrantLock();
            var8_6 = var7_5.newCondition();
            var9_7 = new AtomicReference<V>();
            var10_8 = new AtomicBoolean(true);
            this.post(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    try {
                        var9_7.set(var1_1.call());
                    }
                    catch (Exception exception) {}
                    var7_5.lock();
                    try {
                        var10_8.set(false);
                        var8_6.signal();
                        return;
                    }
                    finally {
                        var7_5.unlock();
                    }
                }
            });
            var7_5.lock();
            if (var10_8.get()) break block7;
            var1_1 /* !! */  = var9_7.get();
            var7_5.unlock();
            return (T)var1_1 /* !! */ ;
        }
        try {
            var3_9 = TimeUnit.MILLISECONDS.toNanos(var2_4);
            do lbl-1000:
            // 2 sources
            {
                try {
                    var5_10 = var8_6.awaitNanos(var3_9);
                }
                catch (InterruptedException var1_3) {
                    var5_10 = var3_9;
                }
                if (var10_8.get()) break block8;
                var1_1 /* !! */  = var9_7.get();
                var7_5.unlock();
                break;
            } while (true);
        }
        catch (Throwable var1_2) {
            var7_5.unlock();
            throw var1_2;
        }
        {
            block8: {
                return (T)var1_1 /* !! */ ;
            }
            var3_9 = var5_10;
            ** while (var5_10 > 0L)
        }
lbl32:
        // 2 sources
        throw new InterruptedException("timeout");
    }

    public static interface ReplyCallback<T> {
        public void onReply(T var1);
    }

}

