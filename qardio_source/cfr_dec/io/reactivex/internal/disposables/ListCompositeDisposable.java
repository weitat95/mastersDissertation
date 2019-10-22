/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class ListCompositeDisposable
implements Disposable,
DisposableContainer {
    volatile boolean disposed;
    List<Disposable> resources;

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
                    List<Disposable> list;
                    List<Disposable> list2 = list = this.resources;
                    if (list == null) {
                        this.resources = list2 = new LinkedList<Disposable>();
                    }
                    list2.add(disposable);
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
            List<Disposable> list = this.resources;
            return list != null && list.remove(disposable);
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
        List<Disposable> list;
        if (this.disposed) {
            return;
        }
        synchronized (this) {
            if (this.disposed) {
                return;
            }
            this.disposed = true;
            list = this.resources;
            this.resources = null;
        }
        this.dispose(list);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void dispose(List<Disposable> list) {
        block9: {
            block8: {
                if (list == null) break block8;
                ArrayList<Disposable> arrayList = null;
                Iterator<Disposable> iterator = list.iterator();
                list = arrayList;
                while (iterator.hasNext()) {
                    arrayList = iterator.next();
                    try {
                        arrayList.dispose();
                    }
                    catch (Throwable throwable) {
                        Exceptions.throwIfFatal(throwable);
                        arrayList = list;
                        if (list == null) {
                            arrayList = new ArrayList<Disposable>();
                        }
                        arrayList.add((Disposable)((Object)throwable));
                        list = arrayList;
                    }
                }
                if (list != null) break block9;
            }
            return;
        }
        if (list.size() == 1) {
            throw ExceptionHelper.wrapOrThrow((Throwable)((Object)list.get(0)));
        }
        throw new CompositeException(list);
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

