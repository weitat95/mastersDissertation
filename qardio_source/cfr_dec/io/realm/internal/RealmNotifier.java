/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.RealmChangeListener;
import io.realm.internal.Keep;
import io.realm.internal.ObserverPairList;
import io.realm.internal.SharedRealm;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Keep
public abstract class RealmNotifier
implements Closeable {
    private final ObserverPairList.Callback<RealmObserverPair> onChangeCallBack;
    private ObserverPairList<RealmObserverPair> realmObserverPairs = new ObserverPairList();
    private SharedRealm sharedRealm;
    private List<Runnable> transactionCallbacks;

    protected RealmNotifier(SharedRealm sharedRealm) {
        this.onChangeCallBack = new ObserverPairList.Callback<RealmObserverPair>(){

            @Override
            public void onCalled(RealmObserverPair realmObserverPair, Object object) {
                if (RealmNotifier.this.sharedRealm != null && !RealmNotifier.this.sharedRealm.isClosed()) {
                    realmObserverPair.onChange(object);
                }
            }
        };
        this.transactionCallbacks = new ArrayList<Runnable>();
        this.sharedRealm = sharedRealm;
    }

    private void removeAllChangeListeners() {
        this.realmObserverPairs.clear();
    }

    public <T> void addChangeListener(T object, RealmChangeListener<T> realmChangeListener) {
        object = new RealmObserverPair<T>(object, realmChangeListener);
        this.realmObserverPairs.add((RealmObserverPair)object);
    }

    public void addTransactionCallback(Runnable runnable) {
        this.transactionCallbacks.add(runnable);
    }

    void beforeNotify() {
        this.sharedRealm.invalidateIterators();
    }

    @Override
    public void close() {
        this.removeAllChangeListeners();
    }

    void didChange() {
        this.realmObserverPairs.foreach(this.onChangeCallBack);
        if (!this.transactionCallbacks.isEmpty()) {
            Object object = this.transactionCallbacks;
            this.transactionCallbacks = new ArrayList<Runnable>();
            object = object.iterator();
            while (object.hasNext()) {
                ((Runnable)object.next()).run();
            }
        }
    }

    public int getListenersListSize() {
        return this.realmObserverPairs.size();
    }

    public abstract boolean post(Runnable var1);

    public <E> void removeChangeListener(E e, RealmChangeListener<E> realmChangeListener) {
        this.realmObserverPairs.remove(e, realmChangeListener);
    }

    public <E> void removeChangeListeners(E e) {
        this.realmObserverPairs.removeByObserver(e);
    }

    private static class RealmObserverPair<T>
    extends ObserverPairList.ObserverPair<T, RealmChangeListener<T>> {
        public RealmObserverPair(T t, RealmChangeListener<T> realmChangeListener) {
            super(t, realmChangeListener);
        }

        private void onChange(T t) {
            if (t != null) {
                ((RealmChangeListener)this.listener).onChange(t);
            }
        }
    }

}

