/*
 * Decompiled with CFR 0.147.
 */
package android.arch.lifecycle;

import android.arch.core.internal.SafeIterableMap;
import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Lifecycling;
import java.util.Iterator;
import java.util.Map;

public class LifecycleRegistry
extends Lifecycle {
    private Lifecycle.Event mLastEvent;
    private final LifecycleOwner mLifecycleOwner;
    private SafeIterableMap<LifecycleObserver, ObserverWithState> mObserverSet = new SafeIterableMap();
    private Lifecycle.State mState;

    public LifecycleRegistry(LifecycleOwner lifecycleOwner) {
        this.mLifecycleOwner = lifecycleOwner;
        this.mState = Lifecycle.State.INITIALIZED;
    }

    static Lifecycle.Event downEvent(Lifecycle.State state) {
        switch (state) {
            default: {
                throw new IllegalArgumentException("Unexpected state value " + (Object)((Object)state));
            }
            case INITIALIZED: {
                throw new IllegalArgumentException();
            }
            case CREATED: {
                return Lifecycle.Event.ON_DESTROY;
            }
            case STARTED: {
                return Lifecycle.Event.ON_STOP;
            }
            case RESUMED: {
                return Lifecycle.Event.ON_PAUSE;
            }
            case DESTROYED: 
        }
        throw new IllegalArgumentException();
    }

    static Lifecycle.State getStateAfter(Lifecycle.Event event) {
        switch (event) {
            default: {
                throw new IllegalArgumentException("Unexpected event value " + (Object)((Object)event));
            }
            case ON_CREATE: 
            case ON_STOP: {
                return Lifecycle.State.CREATED;
            }
            case ON_START: 
            case ON_PAUSE: {
                return Lifecycle.State.STARTED;
            }
            case ON_RESUME: {
                return Lifecycle.State.RESUMED;
            }
            case ON_DESTROY: 
        }
        return Lifecycle.State.DESTROYED;
    }

    static Lifecycle.Event upEvent(Lifecycle.State state) {
        switch (state) {
            default: {
                throw new IllegalArgumentException("Unexpected state value " + (Object)((Object)state));
            }
            case INITIALIZED: 
            case DESTROYED: {
                return Lifecycle.Event.ON_CREATE;
            }
            case CREATED: {
                return Lifecycle.Event.ON_START;
            }
            case STARTED: {
                return Lifecycle.Event.ON_RESUME;
            }
            case RESUMED: 
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void addObserver(LifecycleObserver lifecycleObserver) {
        ObserverWithState observerWithState = new ObserverWithState(lifecycleObserver);
        this.mObserverSet.putIfAbsent(lifecycleObserver, observerWithState);
        observerWithState.sync();
    }

    @Override
    public Lifecycle.State getCurrentState() {
        return this.mState;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void handleLifecycleEvent(Lifecycle.Event object) {
        if (this.mLastEvent != object) {
            this.mLastEvent = object;
            this.mState = LifecycleRegistry.getStateAfter((Lifecycle.Event)((Object)object));
            object = this.mObserverSet.iterator();
            while (object.hasNext()) {
                ((ObserverWithState)((Map.Entry)object.next()).getValue()).sync();
            }
        }
    }

    public void markState(Lifecycle.State state) {
        this.mState = state;
    }

    @Override
    public void removeObserver(LifecycleObserver lifecycleObserver) {
        this.mObserverSet.remove(lifecycleObserver);
    }

    class ObserverWithState {
        private GenericLifecycleObserver mCallback;
        private Lifecycle.State mObserverCurrentState = Lifecycle.State.INITIALIZED;

        ObserverWithState(LifecycleObserver lifecycleObserver) {
            this.mCallback = Lifecycling.getCallback(lifecycleObserver);
        }

        /*
         * Enabled aggressive block sorting
         */
        void sync() {
            if (LifecycleRegistry.this.mState == Lifecycle.State.DESTROYED && this.mObserverCurrentState == Lifecycle.State.INITIALIZED) {
                this.mObserverCurrentState = Lifecycle.State.DESTROYED;
            }
            while (this.mObserverCurrentState != LifecycleRegistry.this.mState) {
                Lifecycle.Event event = this.mObserverCurrentState.isAtLeast(LifecycleRegistry.this.mState) ? LifecycleRegistry.downEvent(this.mObserverCurrentState) : LifecycleRegistry.upEvent(this.mObserverCurrentState);
                this.mObserverCurrentState = LifecycleRegistry.getStateAfter(event);
                this.mCallback.onStateChanged(LifecycleRegistry.this.mLifecycleOwner, event);
            }
            return;
        }
    }

}

