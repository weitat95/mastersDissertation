/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.util;

import com.getqardio.android.mvp.common.util.RxEventBus$$Lambda$1;
import com.getqardio.android.mvp.common.util.RxEventBus$$Lambda$2;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;

public class RxEventBus {
    private final PublishSubject<Object> busSubject = PublishSubject.create();

    static /* synthetic */ Object lambda$filteredObservable$0(Object object) throws Exception {
        return object;
    }

    public <T> Observable<T> filteredObservable(Class<T> class_) {
        PublishSubject<Object> publishSubject = this.busSubject;
        class_.getClass();
        return publishSubject.filter(RxEventBus$$Lambda$1.lambdaFactory$(class_)).map(RxEventBus$$Lambda$2.lambdaFactory$());
    }

    public void post(Object object) {
        this.busSubject.onNext(object);
    }

    public static abstract class RxEvent {
    }

    public static class UnauthorizedResponse
    extends RxEvent {
    }

}

