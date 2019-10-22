/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  com.getqardio.android.shopify.view.LifeCycleBoundCallback.com.getqardio.android.shopify.view.LifeCycleBoundCallback
 *  com.getqardio.android.shopify.view.LifeCycleBoundCallback.com.getqardio.android.shopify.view.LifeCycleBoundCallback$LifecycleBoundObserver
 */
package com.getqardio.android.shopify.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import com.getqardio.android.shopify.view.LifeCycleBoundCallback.com.getqardio.android.shopify.view.LifeCycleBoundCallback;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LifeCycleBoundCallback<T> {
    private static final LifecycleOwner ALWAYS_ON = new LifecycleOwner(){
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
    private final ReadWriteLock lock;
    private final List<com.getqardio.android.shopify.view.LifeCycleBoundCallback$LifecycleBoundObserver> observers = new LinkedList<com.getqardio.android.shopify.view.LifeCycleBoundCallback$LifecycleBoundObserver>();

    public LifeCycleBoundCallback() {
        this.lock = new ReentrantReadWriteLock();
    }

    public void notify(T t) {
        this.lock.readLock().lock();
        try {
            for (LifecycleBoundObserver lifecycleBoundObserver : this.observers) {
                if (!lifecycleBoundObserver.currentState.isAtLeast(Lifecycle.State.STARTED)) continue;
                lifecycleBoundObserver.observer.onChanged(t);
            }
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    public void observe(Lifecycle lifecycle, Observer<T> observer) {
        if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        this.lock.writeLock().lock();
        try {
            this.observers.add((com.getqardio.android.shopify.view.LifeCycleBoundCallback$LifecycleBoundObserver)new LifecycleBoundObserver(lifecycle, observer));
            return;
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    public void observe(LifecycleOwner lifecycleOwner, Observer<T> observer) {
        this.observe(lifecycleOwner.getLifecycle(), observer);
    }

    public void observeForever(Observer<T> observer) {
        this.observe(ALWAYS_ON, observer);
    }

    class LifecycleBoundObserver
    implements LifecycleObserver {
        Lifecycle.State currentState;
        final Observer<T> observer;

        LifecycleBoundObserver(Lifecycle lifecycle, Observer<T> observer) {
            this.observer = observer;
            lifecycle.addObserver(this);
        }

        @OnLifecycleEvent(value={Lifecycle.Event.ON_ANY})
        void onStateChange(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            this.currentState = lifecycleOwner.getLifecycle().getCurrentState();
            if (this.currentState == Lifecycle.State.DESTROYED) {
                LifeCycleBoundCallback.this.lock.writeLock().lock();
                LifeCycleBoundCallback.this.observers.remove(this);
            }
            return;
            finally {
                LifeCycleBoundCallback.this.lock.writeLock().unlock();
            }
        }
    }

}

