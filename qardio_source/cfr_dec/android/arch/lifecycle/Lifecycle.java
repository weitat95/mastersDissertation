/*
 * Decompiled with CFR 0.147.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.LifecycleObserver;

public abstract class Lifecycle {
    public abstract void addObserver(LifecycleObserver var1);

    public abstract State getCurrentState();

    public abstract void removeObserver(LifecycleObserver var1);

    public static enum Event {
        ON_CREATE,
        ON_START,
        ON_RESUME,
        ON_PAUSE,
        ON_STOP,
        ON_DESTROY,
        ON_ANY;

    }

    public static enum State {
        DESTROYED,
        INITIALIZED,
        CREATED,
        STARTED,
        RESUMED;


        public boolean isAtLeast(State state) {
            return this.compareTo(state) >= 0;
        }
    }

}

