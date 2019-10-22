/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ObserverPairList<T extends ObserverPair> {
    private boolean cleared = false;
    private List<T> pairs = new CopyOnWriteArrayList<T>();

    public void add(T t) {
        if (!this.pairs.contains(t)) {
            this.pairs.add(t);
            ((ObserverPair)t).removed = false;
        }
        if (this.cleared) {
            this.cleared = false;
        }
    }

    public void clear() {
        this.cleared = true;
        this.pairs.clear();
    }

    public void foreach(Callback<T> callback) {
        Iterator<T> iterator = this.pairs.iterator();
        do {
            ObserverPair observerPair;
            block6: {
                block5: {
                    if (!iterator.hasNext()) break block5;
                    observerPair = (ObserverPair)iterator.next();
                    if (!this.cleared) break block6;
                }
                return;
            }
            Object t = observerPair.observerRef.get();
            if (t == null) {
                this.pairs.remove(observerPair);
                continue;
            }
            if (observerPair.removed) continue;
            callback.onCalled(observerPair, t);
        } while (true);
    }

    public boolean isEmpty() {
        return this.pairs.isEmpty();
    }

    public <S, U> void remove(S s, U u) {
        for (ObserverPair observerPair : this.pairs) {
            if (s != observerPair.observerRef.get() || !u.equals(observerPair.listener)) continue;
            observerPair.removed = true;
            this.pairs.remove(observerPair);
            break;
        }
    }

    void removeByObserver(Object object) {
        for (ObserverPair observerPair : this.pairs) {
            Object t = observerPair.observerRef.get();
            if (t != null && t != object) continue;
            observerPair.removed = true;
            this.pairs.remove(observerPair);
        }
    }

    public int size() {
        return this.pairs.size();
    }

    public static interface Callback<T extends ObserverPair> {
        public void onCalled(T var1, Object var2);
    }

    public static abstract class ObserverPair<T, S> {
        protected final S listener;
        final WeakReference<T> observerRef;
        boolean removed = false;

        ObserverPair(T t, S s) {
            this.listener = s;
            this.observerRef = new WeakReference<T>(t);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block5: {
                block4: {
                    if (this == object) break block4;
                    if (!(object instanceof ObserverPair)) {
                        return false;
                    }
                    object = (ObserverPair)object;
                    if (!this.listener.equals(((ObserverPair)object).listener) || this.observerRef.get() != ((ObserverPair)object).observerRef.get()) break block5;
                }
                return true;
            }
            return false;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int hashCode() {
            int n = 0;
            Object t = this.observerRef.get();
            int n2 = t != null ? t.hashCode() : 0;
            if (this.listener != null) {
                n = this.listener.hashCode();
            }
            return (n2 + 527) * 31 + n;
        }
    }

}

