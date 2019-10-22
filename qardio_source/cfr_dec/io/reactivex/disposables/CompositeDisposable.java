/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.disposables;

import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.OpenHashSet;
import java.util.ArrayList;

public final class CompositeDisposable
implements Disposable,
DisposableContainer {
    volatile boolean disposed;
    OpenHashSet<Disposable> resources;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean add(Disposable disposable) {
        ObjectHelper.requireNonNull(disposable, "d is null");
        if (!this.disposed) {
            synchronized (this) {
                if (!this.disposed) {
                    OpenHashSet<Disposable> openHashSet;
                    OpenHashSet<Disposable> openHashSet2 = openHashSet = this.resources;
                    if (openHashSet == null) {
                        this.resources = openHashSet2 = new OpenHashSet();
                    }
                    openHashSet2.add(disposable);
                    return true;
                }
            }
        }
        disposable.dispose();
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void clear() {
        OpenHashSet<Disposable> openHashSet;
        if (this.disposed) {
            return;
        }
        synchronized (this) {
            if (this.disposed) {
                return;
            }
            openHashSet = this.resources;
            this.resources = null;
        }
        this.dispose(openHashSet);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean delete(Disposable disposable) {
        ObjectHelper.requireNonNull(disposable, "Disposable item is null");
        if (this.disposed) {
            return false;
        }
        synchronized (this) {
            if (this.disposed) {
                return false;
            }
            OpenHashSet<Disposable> openHashSet = this.resources;
            return openHashSet != null && openHashSet.remove(disposable);
            {
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void dispose() {
        OpenHashSet<Disposable> openHashSet;
        if (this.disposed) {
            return;
        }
        synchronized (this) {
            if (this.disposed) {
                return;
            }
            this.disposed = true;
            openHashSet = this.resources;
            this.resources = null;
        }
        this.dispose(openHashSet);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void dispose(OpenHashSet<Disposable> openHashSet) {
        block10: {
            block9: {
                if (openHashSet == null) break block9;
                Object object = null;
                Object[] arrobject = openHashSet.keys();
                int n = arrobject.length;
                openHashSet = object;
                for (int i = 0; i < n; ++i) {
                    Object object2 = arrobject[i];
                    object = openHashSet;
                    if (object2 instanceof Disposable) {
                        try {
                            ((Disposable)object2).dispose();
                            object = openHashSet;
                        }
                        catch (Throwable throwable) {
                            Exceptions.throwIfFatal(throwable);
                            object = openHashSet;
                            if (openHashSet == null) {
                                object = new ArrayList();
                            }
                            object.add((Throwable)throwable);
                        }
                    }
                    openHashSet = object;
                }
                if (openHashSet != null) break block10;
            }
            return;
        }
        if (openHashSet.size() == 1) {
            throw ExceptionHelper.wrapOrThrow((Throwable)openHashSet.get(0));
        }
        throw new CompositeException((Iterable<? extends Throwable>)((Object)openHashSet));
    }

    @Override
    public boolean isDisposed() {
        return this.disposed;
    }

    @Override
    public boolean remove(Disposable disposable) {
        if (this.delete(disposable)) {
            disposable.dispose();
            return true;
        }
        return false;
    }
}

