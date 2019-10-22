/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify;

import com.getqardio.android.shopify.RxUtil$$Lambda$1;
import com.getqardio.android.shopify.RxUtil$$Lambda$2;
import com.getqardio.android.shopify.RxUtil$$Lambda$3;
import com.getqardio.android.shopify.RxUtil$$Lambda$4;
import com.getqardio.android.shopify.RxUtil$$Lambda$5;
import com.getqardio.android.shopify.RxUtil$$Lambda$6;
import com.getqardio.android.shopify.RxUtil$$Lambda$7;
import com.getqardio.android.shopify.util.Util;
import com.shopify.buy3.GraphCall;
import com.shopify.buy3.GraphResponse;
import com.shopify.buy3.Storefront;
import com.shopify.graphql.support.AbstractResponse;
import com.shopify.graphql.support.Error;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Function;
import java.util.List;

public final class RxUtil {
    private RxUtil() {
    }

    static /* synthetic */ StringBuilder lambda$null$2(StringBuilder stringBuilder, Error error) {
        return stringBuilder.append(error.message()).append("\n");
    }

    static /* synthetic */ SingleSource lambda$null$3(GraphResponse graphResponse) throws Exception {
        if (graphResponse.errors().isEmpty()) {
            return Single.just(graphResponse.data());
        }
        return Single.error(new RuntimeException(Util.fold(new StringBuilder(), graphResponse.errors(), RxUtil$$Lambda$5.lambdaFactory$()).toString()));
    }

    static /* synthetic */ SingleSource lambda$queryResponseDataTransformer$4(Single single) {
        return single.flatMap(RxUtil$$Lambda$4.lambdaFactory$());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static /* synthetic */ void lambda$rxGraphMutationCall$1(GraphCall graphCall, SingleEmitter singleEmitter) throws Exception {
        graphCall.getClass();
        singleEmitter.setCancellable(RxUtil$$Lambda$6.lambdaFactory$(graphCall));
        try {
            singleEmitter.onSuccess(graphCall.execute());
            return;
        }
        catch (Exception exception) {
            Exceptions.throwIfFatal(exception);
            if (singleEmitter.isDisposed()) return;
            singleEmitter.onError(exception);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static /* synthetic */ void lambda$rxGraphQueryCall$0(GraphCall graphCall, SingleEmitter singleEmitter) throws Exception {
        graphCall.getClass();
        singleEmitter.setCancellable(RxUtil$$Lambda$7.lambdaFactory$(graphCall));
        try {
            singleEmitter.onSuccess(graphCall.execute());
            return;
        }
        catch (Exception exception) {
            Exceptions.throwIfFatal(exception);
            if (singleEmitter.isDisposed()) return;
            singleEmitter.onError(exception);
            return;
        }
    }

    private static <T extends AbstractResponse<T>> SingleTransformer<GraphResponse<T>, T> queryResponseDataTransformer() {
        return RxUtil$$Lambda$3.lambdaFactory$();
    }

    public static Single<Storefront.Mutation> rxGraphMutationCall(GraphCall<Storefront.Mutation> graphCall) {
        return Single.create(RxUtil$$Lambda$2.lambdaFactory$(graphCall)).compose(RxUtil.<T>queryResponseDataTransformer());
    }

    public static Single<Storefront.QueryRoot> rxGraphQueryCall(GraphCall<Storefront.QueryRoot> graphCall) {
        return Single.create(RxUtil$$Lambda$1.lambdaFactory$(graphCall)).compose(RxUtil.<T>queryResponseDataTransformer());
    }
}

