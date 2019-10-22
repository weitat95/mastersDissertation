/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.operators.observable.AbstractObservableWithUpstream;
import io.reactivex.internal.operators.observable.ObservableScalarXMap;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableFlatMap<T, U>
extends AbstractObservableWithUpstream<T, U> {
    final int bufferSize;
    final boolean delayErrors;
    final Function<? super T, ? extends ObservableSource<? extends U>> mapper;
    final int maxConcurrency;

    public ObservableFlatMap(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<? extends U>> function, boolean bl, int n, int n2) {
        super(observableSource);
        this.mapper = function;
        this.delayErrors = bl;
        this.maxConcurrency = n;
        this.bufferSize = n2;
    }

    @Override
    public void subscribeActual(Observer<? super U> observer) {
        if (ObservableScalarXMap.tryScalarXMapSubscribe(this.source, observer, this.mapper)) {
            return;
        }
        this.source.subscribe(new MergeObserver<T, U>(observer, this.mapper, this.delayErrors, this.maxConcurrency, this.bufferSize));
    }

    static final class InnerObserver<T, U>
    extends AtomicReference<Disposable>
    implements Observer<U> {
        volatile boolean done;
        int fusionMode;
        final long id;
        final MergeObserver<T, U> parent;
        volatile SimpleQueue<U> queue;

        InnerObserver(MergeObserver<T, U> mergeObserver, long l) {
            this.id = l;
            this.parent = mergeObserver;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override
        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        @Override
        public void onError(Throwable throwable) {
            if (this.parent.errors.addThrowable(throwable)) {
                if (!this.parent.delayErrors) {
                    this.parent.disposeAll();
                }
                this.done = true;
                this.parent.drain();
                return;
            }
            RxJavaPlugins.onError(throwable);
        }

        @Override
        public void onNext(U u) {
            if (this.fusionMode == 0) {
                this.parent.tryEmit(u, this);
                return;
            }
            this.parent.drain();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onSubscribe(Disposable disposable) {
            if (!DisposableHelper.setOnce(this, disposable) || !(disposable instanceof QueueDisposable)) return;
            {
                int n = (disposable = (QueueDisposable)disposable).requestFusion(7);
                if (n == 1) {
                    this.fusionMode = n;
                    this.queue = disposable;
                    this.done = true;
                    this.parent.drain();
                    return;
                } else {
                    if (n != 2) return;
                    {
                        this.fusionMode = n;
                        this.queue = disposable;
                        return;
                    }
                }
            }
        }
    }

    static final class MergeObserver<T, U>
    extends AtomicInteger
    implements Observer<T>,
    Disposable {
        static final InnerObserver<?, ?>[] CANCELLED;
        static final InnerObserver<?, ?>[] EMPTY;
        final Observer<? super U> actual;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final AtomicThrowable errors = new AtomicThrowable();
        long lastId;
        int lastIndex;
        final Function<? super T, ? extends ObservableSource<? extends U>> mapper;
        final int maxConcurrency;
        final AtomicReference<InnerObserver<?, ?>[]> observers;
        volatile SimplePlainQueue<U> queue;
        Disposable s;
        Queue<ObservableSource<? extends U>> sources;
        long uniqueId;
        int wip;

        static {
            EMPTY = new InnerObserver[0];
            CANCELLED = new InnerObserver[0];
        }

        MergeObserver(Observer<? super U> observer, Function<? super T, ? extends ObservableSource<? extends U>> function, boolean bl, int n, int n2) {
            this.actual = observer;
            this.mapper = function;
            this.delayErrors = bl;
            this.maxConcurrency = n;
            this.bufferSize = n2;
            if (n != Integer.MAX_VALUE) {
                this.sources = new ArrayDeque<ObservableSource<? extends U>>(n);
            }
            this.observers = new AtomicReference<InnerObserver<?, ?>[]>(EMPTY);
        }

        boolean addInner(InnerObserver<T, U> innerObserver) {
            InnerObserver[] arrinnerObserver;
            InnerObserver<?, ?>[] arrinnerObserver2;
            do {
                if ((arrinnerObserver2 = this.observers.get()) == CANCELLED) {
                    innerObserver.dispose();
                    return false;
                }
                int n = arrinnerObserver2.length;
                arrinnerObserver = new InnerObserver[n + 1];
                System.arraycopy(arrinnerObserver2, 0, arrinnerObserver, 0, n);
                arrinnerObserver[n] = innerObserver;
            } while (!this.observers.compareAndSet(arrinnerObserver2, arrinnerObserver));
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        boolean checkTerminate() {
            block3: {
                Throwable throwable;
                block4: {
                    block2: {
                        if (this.cancelled) break block2;
                        throwable = (Throwable)this.errors.get();
                        if (this.delayErrors || throwable == null) break block3;
                        this.disposeAll();
                        throwable = this.errors.terminate();
                        if (throwable != ExceptionHelper.TERMINATED) break block4;
                    }
                    return true;
                }
                this.actual.onError(throwable);
                return true;
            }
            return false;
        }

        @Override
        public void dispose() {
            if (!this.cancelled) {
                Throwable throwable;
                this.cancelled = true;
                if (this.disposeAll() && (throwable = this.errors.terminate()) != null && throwable != ExceptionHelper.TERMINATED) {
                    RxJavaPlugins.onError(throwable);
                }
            }
        }

        boolean disposeAll() {
            InnerObserver<?, ?>[] arrinnerObserver;
            this.s.dispose();
            if (this.observers.get() != CANCELLED && (arrinnerObserver = this.observers.getAndSet(CANCELLED)) != CANCELLED) {
                int n = arrinnerObserver.length;
                for (int i = 0; i < n; ++i) {
                    arrinnerObserver[i].dispose();
                }
                return true;
            }
            return false;
        }

        void drain() {
            if (this.getAndIncrement() == 0) {
                this.drainLoop();
            }
        }

        /*
         * Exception decompiling
         */
        void drainLoop() {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Nonsensical loop would be emitted - failure
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.LoopIdentifier.considerAsDoLoopStart(LoopIdentifier.java:401)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.LoopIdentifier.identifyLoops1(LoopIdentifier.java:62)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:595)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:794)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:902)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }

        @Override
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.drain();
        }

        @Override
        public void onError(Throwable throwable) {
            if (this.done) {
                RxJavaPlugins.onError(throwable);
                return;
            }
            if (this.errors.addThrowable(throwable)) {
                this.done = true;
                this.drain();
                return;
            }
            RxJavaPlugins.onError(throwable);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onNext(T object) {
            block7: {
                if (this.done) {
                    return;
                }
                try {
                    object = ObjectHelper.requireNonNull(this.mapper.apply(object), "The mapper returned a null ObservableSource");
                    if (this.maxConcurrency == Integer.MAX_VALUE) break block7;
                }
                catch (Throwable throwable) {
                    Exceptions.throwIfFatal(throwable);
                    this.s.dispose();
                    this.onError(throwable);
                    return;
                }
                synchronized (this) {
                    if (this.wip == this.maxConcurrency) {
                        this.sources.offer((ObservableSource<U>)object);
                        return;
                    }
                    ++this.wip;
                }
            }
            this.subscribeInner((ObservableSource<? extends U>)object);
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.s, disposable)) {
                this.s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        void removeInner(InnerObserver<T, U> innerObserver) {
            InnerObserver<?, ?>[] arrinnerObserver;
            int n;
            block0 : while ((n = (arrinnerObserver = this.observers.get()).length) != 0) {
                int n2 = -1;
                int n3 = 0;
                do {
                    block8: {
                        int n4;
                        InnerObserver<?, ?>[] arrinnerObserver2;
                        block7: {
                            n4 = n2;
                            if (n3 >= n) break block7;
                            if (arrinnerObserver[n3] != innerObserver) break block8;
                            n4 = n3;
                        }
                        if (n4 < 0) break block0;
                        if (n == 1) {
                            arrinnerObserver2 = EMPTY;
                        } else {
                            arrinnerObserver2 = new InnerObserver[n - 1];
                            System.arraycopy(arrinnerObserver, 0, arrinnerObserver2, 0, n4);
                            System.arraycopy(arrinnerObserver, n4 + 1, arrinnerObserver2, n4, n - n4 - 1);
                        }
                        if (!this.observers.compareAndSet(arrinnerObserver, arrinnerObserver2)) continue block0;
                        return;
                    }
                    ++n3;
                } while (true);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void subscribeInner(ObservableSource<? extends U> observableSource) {
            while (observableSource instanceof Callable) {
                this.tryEmitScalar((Callable)((Object)observableSource));
                if (this.maxConcurrency == Integer.MAX_VALUE) return;
                {
                    synchronized (this) {
                        observableSource = this.sources.poll();
                        if (observableSource == null) {
                            --this.wip;
                            return;
                        }
                    }
                }
            }
            long l = this.uniqueId;
            this.uniqueId = 1L + l;
            InnerObserver innerObserver = new InnerObserver(this, l);
            if (!this.addInner(innerObserver)) return;
            {
                observableSource.subscribe(innerObserver);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        void tryEmit(U u, InnerObserver<T, U> innerObserver) {
            if (this.get() == 0 && this.compareAndSet(0, 1)) {
                this.actual.onNext(u);
                if (this.decrementAndGet() == 0) {
                    return;
                }
            } else {
                SimpleQueue simpleQueue;
                SimpleQueue simpleQueue2 = simpleQueue = innerObserver.queue;
                if (simpleQueue == null) {
                    simpleQueue2 = new SpscLinkedArrayQueue(this.bufferSize);
                    innerObserver.queue = simpleQueue2;
                }
                simpleQueue2.offer(u);
                if (this.getAndIncrement() != 0) {
                    return;
                }
            }
            this.drainLoop();
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void tryEmitScalar(Callable<? extends U> simplePlainQueue) {
            Object v;
            block12: {
                try {
                    v = simplePlainQueue.call();
                    if (v != null) break block12;
                    return;
                }
                catch (Throwable throwable) {
                    Exceptions.throwIfFatal(throwable);
                    this.errors.addThrowable(throwable);
                    this.drain();
                    return;
                }
            }
            if (this.get() == 0 && this.compareAndSet(0, 1)) {
                this.actual.onNext(v);
                if (this.decrementAndGet() == 0) {
                    return;
                }
            } else {
                void var1_6;
                SimplePlainQueue<U> simplePlainQueue2;
                SimplePlainQueue<U> simplePlainQueue3 = simplePlainQueue2 = this.queue;
                if (simplePlainQueue2 == null) {
                    void var1_5;
                    if (this.maxConcurrency == Integer.MAX_VALUE) {
                        SpscLinkedArrayQueue spscLinkedArrayQueue = new SpscLinkedArrayQueue(this.bufferSize);
                    } else {
                        SpscArrayQueue spscArrayQueue = new SpscArrayQueue(this.maxConcurrency);
                    }
                    this.queue = var1_5;
                }
                if (!var1_6.offer(v)) {
                    this.onError(new IllegalStateException("Scalar queue full?!"));
                    return;
                }
                if (this.getAndIncrement() != 0) return;
            }
            this.drainLoop();
        }
    }

}

