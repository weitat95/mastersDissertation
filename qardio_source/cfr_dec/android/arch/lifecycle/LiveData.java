/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.arch.lifecycle.LiveData.android.arch.lifecycle.LiveData
 *  android.arch.lifecycle.LiveData.android.arch.lifecycle.LiveData$LifecycleBoundObserver
 *  android.arch.lifecycle.LiveData.android.arch.lifecycle.LiveData$android.arch.lifecycle.LiveData
 *  android.arch.lifecycle.LiveData.android.arch.lifecycle.LiveData$android.arch.lifecycle.LiveData$LifecycleBoundObserver
 */
package android.arch.lifecycle;

import android.arch.core.executor.AppToolkitTaskExecutor;
import android.arch.core.internal.SafeIterableMap;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData.android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import java.util.Map;

public abstract class LiveData<T> {
    private static final LifecycleOwner ALWAYS_ON;
    private static final Object NOT_SET;
    static final int START_VERSION = -1;
    private int mActiveCount = 0;
    private Object mData;
    private final Object mDataLock = new Object();
    private boolean mDispatchInvalidated;
    private boolean mDispatchingValue;
    private SafeIterableMap<Observer<T>, android.arch.lifecycle.LiveData$LifecycleBoundObserver> mObservers = new SafeIterableMap();
    private volatile Object mPendingData;
    private final Runnable mPostValueRunnable;
    private int mVersion = -1;

    static {
        NOT_SET = new Object();
        ALWAYS_ON = new LifecycleOwner(){
            private LifecycleRegistry mRegistry = this.init();

            private LifecycleRegistry init() {
                LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
                return lifecycleRegistry;
            }

            @Override
            public Lifecycle getLifecycle() {
                return this.mRegistry;
            }
        };
    }

    public LiveData() {
        this.mData = NOT_SET;
        this.mPendingData = NOT_SET;
        this.mPostValueRunnable = new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                Object object;
                Object object2 = LiveData.this.mDataLock;
                synchronized (object2) {
                    object = LiveData.this.mPendingData;
                    LiveData.this.mPendingData = NOT_SET;
                }
                LiveData.this.setValue(object);
            }
        };
    }

    private void assertMainThread(String string2) {
        if (!AppToolkitTaskExecutor.getInstance().isMainThread()) {
            throw new IllegalStateException("Cannot invoke " + string2 + " on a background" + " thread");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void considerNotify(android.arch.lifecycle.LiveData$android.arch.lifecycle.LiveData$LifecycleBoundObserver lifecycleBoundObserver) {
        if (!lifecycleBoundObserver.active || !LiveData.isActiveState(lifecycleBoundObserver.owner.getLifecycle().getCurrentState()) || lifecycleBoundObserver.lastVersion >= this.mVersion) {
            return;
        }
        lifecycleBoundObserver.lastVersion = this.mVersion;
        lifecycleBoundObserver.observer.onChanged(this.mData);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void dispatchingValue(android.arch.lifecycle.LiveData$android.arch.lifecycle.LiveData$LifecycleBoundObserver lifecycleBoundObserver) {
        if (this.mDispatchingValue) {
            this.mDispatchInvalidated = true;
            return;
        }
        this.mDispatchingValue = true;
        do {
            android.arch.lifecycle.LiveData$android.arch.lifecycle.LiveData$LifecycleBoundObserver lifecycleBoundObserver2;
            block5: {
                this.mDispatchInvalidated = false;
                if (lifecycleBoundObserver != null) {
                    this.considerNotify((LifecycleBoundObserver)lifecycleBoundObserver);
                    lifecycleBoundObserver2 = null;
                } else {
                    SafeIterableMap.ListIterator<Observer<T>, android.arch.lifecycle.LiveData$LifecycleBoundObserver> listIterator = this.mObservers.iteratorWithAdditions();
                    do {
                        lifecycleBoundObserver2 = lifecycleBoundObserver;
                        if (!listIterator.hasNext()) break block5;
                        this.considerNotify((LifecycleBoundObserver)((Map.Entry)listIterator.next()).getValue());
                    } while (!this.mDispatchInvalidated);
                    lifecycleBoundObserver2 = lifecycleBoundObserver;
                }
            }
            lifecycleBoundObserver = lifecycleBoundObserver2;
        } while (this.mDispatchInvalidated);
        this.mDispatchingValue = false;
    }

    static boolean isActiveState(Lifecycle.State state) {
        return state.isAtLeast(Lifecycle.State.STARTED);
    }

    public T getValue() {
        Object object = this.mData;
        if (this.mData != NOT_SET) {
            return (T)this.mData;
        }
        return null;
    }

    int getVersion() {
        return this.mVersion;
    }

    public boolean hasActiveObservers() {
        return this.mActiveCount > 0;
    }

    public boolean hasObservers() {
        return this.mObservers.size() > 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void observe(LifecycleOwner lifecycleOwner, Observer<T> object) {
        LifecycleBoundObserver lifecycleBoundObserver;
        block5: {
            block4: {
                if (lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) break block4;
                lifecycleBoundObserver = new LifecycleBoundObserver(lifecycleOwner, object);
                if ((object = (LifecycleBoundObserver)this.mObservers.putIfAbsent((Observer<T>)object, (android.arch.lifecycle.LiveData$LifecycleBoundObserver)lifecycleBoundObserver)) != null && ((LifecycleBoundObserver)object).owner != lifecycleBoundObserver.owner) {
                    throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
                }
                if (object == null) break block5;
            }
            return;
        }
        lifecycleOwner.getLifecycle().addObserver(lifecycleBoundObserver);
        lifecycleBoundObserver.activeStateChanged(LiveData.isActiveState(lifecycleOwner.getLifecycle().getCurrentState()));
    }

    public void observeForever(Observer<T> observer) {
        this.observe(ALWAYS_ON, observer);
    }

    protected void onActive() {
    }

    protected void onInactive() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected void postValue(T t) {
        Object object = this.mDataLock;
        // MONITORENTER : object
        boolean bl = this.mPendingData == NOT_SET;
        this.mPendingData = t;
        // MONITOREXIT : object
        if (!bl) {
            return;
        }
        AppToolkitTaskExecutor.getInstance().postToMainThread(this.mPostValueRunnable);
    }

    public void removeObserver(Observer<T> object) {
        this.assertMainThread("removeObserver");
        object = (LifecycleBoundObserver)this.mObservers.remove((Observer<T>)object);
        if (object == null) {
            return;
        }
        ((LifecycleBoundObserver)object).owner.getLifecycle().removeObserver((LifecycleObserver)object);
        ((LifecycleBoundObserver)object).activeStateChanged(false);
    }

    public void removeObservers(LifecycleOwner lifecycleOwner) {
        this.assertMainThread("removeObservers");
        for (Map.Entry entry : this.mObservers) {
            if (((LifecycleBoundObserver)entry.getValue()).owner != lifecycleOwner) continue;
            this.removeObserver((Observer)entry.getKey());
        }
    }

    protected void setValue(T t) {
        this.assertMainThread("setValue");
        ++this.mVersion;
        this.mData = t;
        this.dispatchingValue(null);
    }

    class LifecycleBoundObserver
    implements LifecycleObserver {
        public boolean active;
        public int lastVersion = -1;
        public final Observer<T> observer;
        public final LifecycleOwner owner;

        LifecycleBoundObserver(LifecycleOwner lifecycleOwner, Observer<T> observer) {
            this.owner = lifecycleOwner;
            this.observer = observer;
        }

        /*
         * Enabled aggressive block sorting
         */
        void activeStateChanged(boolean bl) {
            block7: {
                block6: {
                    int n = 1;
                    if (bl == this.active) break block6;
                    this.active = bl;
                    boolean bl2 = LiveData.this.mActiveCount == 0;
                    LiveData liveData = LiveData.this;
                    int n2 = liveData.mActiveCount;
                    if (!this.active) {
                        n = -1;
                    }
                    liveData.mActiveCount = n + n2;
                    if (bl2 && this.active) {
                        LiveData.this.onActive();
                    }
                    if (LiveData.this.mActiveCount == 0 && !this.active) {
                        LiveData.this.onInactive();
                    }
                    if (this.active) break block7;
                }
                return;
            }
            LiveData.this.dispatchingValue(this);
        }

        @OnLifecycleEvent(value={Lifecycle.Event.ON_ANY})
        void onStateChange() {
            if (this.owner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
                LiveData.this.removeObserver(this.observer);
                return;
            }
            this.activeStateChanged(LiveData.isActiveState(this.owner.getLifecycle().getCurrentState()));
        }
    }

}

