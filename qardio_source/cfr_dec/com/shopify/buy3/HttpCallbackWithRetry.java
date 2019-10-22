/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package com.shopify.buy3;

import android.os.Handler;
import com.shopify.buy3.GraphCall;
import com.shopify.buy3.GraphError;
import com.shopify.buy3.GraphNetworkError;
import com.shopify.buy3.GraphResponse;
import com.shopify.buy3.HttpCallbackWithRetry$$Lambda$1;
import com.shopify.buy3.HttpCallbackWithRetry$$Lambda$2;
import com.shopify.buy3.HttpResponseParser;
import com.shopify.buy3.RetryHandler;
import com.shopify.graphql.support.AbstractResponse;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

final class HttpCallbackWithRetry<T extends AbstractResponse<T>>
implements Callback {
    private final ScheduledExecutorService dispatcher;
    private final GraphCall.Callback<T> graphCallback;
    private final Handler handler;
    private volatile Call httpCall;
    private final HttpResponseParser<T> httpResponseParser;
    private final RetryHandler retryHandler;
    private volatile ScheduledFuture<Void> scheduledFuture;

    private void handleError(GraphError graphError) {
        if (this.retryHandler.retry(graphError)) {
            this.schedule();
            return;
        }
        this.notifyError(graphError);
    }

    static /* synthetic */ void lambda$notifyError$1(HttpCallbackWithRetry httpCallbackWithRetry, GraphError graphError) {
        httpCallbackWithRetry.graphCallback.onFailure(graphError);
    }

    static /* synthetic */ void lambda$notifyResponse$0(HttpCallbackWithRetry httpCallbackWithRetry, GraphResponse graphResponse) {
        httpCallbackWithRetry.graphCallback.onResponse(graphResponse);
    }

    private void notifyError(GraphError object) {
        object = HttpCallbackWithRetry$$Lambda$2.lambdaFactory$(this, (GraphError)object);
        if (this.handler != null) {
            this.handler.post((Runnable)object);
            return;
        }
        object.run();
    }

    private void notifyResponse(GraphResponse<T> object) {
        object = HttpCallbackWithRetry$$Lambda$1.lambdaFactory$(this, object);
        if (this.handler != null) {
            this.handler.post((Runnable)object);
            return;
        }
        object.run();
    }

    private void schedule() {
        this.scheduledFuture = this.dispatcher.schedule(new Callable<Void>(){

            @Override
            public Void call() throws Exception {
                HttpCallbackWithRetry.this.httpCall = HttpCallbackWithRetry.this.httpCall.clone();
                HttpCallbackWithRetry.this.httpCall.enqueue(HttpCallbackWithRetry.this);
                return null;
            }
        }, this.retryHandler.nextRetryDelayMs(), TimeUnit.MILLISECONDS);
    }

    void cancel() {
        ScheduledFuture<Void> scheduledFuture = this.scheduledFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
        this.httpCall.cancel();
    }

    @Override
    public void onFailure(Call call, IOException iOException) {
        this.handleError(new GraphNetworkError("Failed to execute GraphQL http request", iOException));
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void onResponse(Call object, Response response) throws IOException {
        block8: {
            try {
                object = this.httpResponseParser.parse(response);
                if (this.retryHandler.retry(object)) {
                    this.schedule();
                    return;
                }
                break block8;
            }
            catch (GraphError graphError) {
                this.handleError(graphError);
                return;
            }
            finally {
                response.close();
            }
            catch (Exception exception) {
                this.notifyError(new GraphError("Failed to reschedule GraphQL query execution", exception));
            }
        }
        this.notifyResponse(object);
    }

}

