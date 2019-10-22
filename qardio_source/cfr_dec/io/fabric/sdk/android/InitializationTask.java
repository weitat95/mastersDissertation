/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.InitializationCallback;
import io.fabric.sdk.android.InitializationException;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.TimingMetric;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityAsyncTask;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;

class InitializationTask<Result>
extends PriorityAsyncTask<Void, Void, Result> {
    final Kit<Result> kit;

    public InitializationTask(Kit<Result> kit) {
        this.kit = kit;
    }

    private TimingMetric createAndStartTimingMetric(String object) {
        object = new TimingMetric(this.kit.getIdentifier() + "." + (String)object, "KitInitialization");
        ((TimingMetric)object).startMeasuring();
        return object;
    }

    protected Result doInBackground(Void ... object) {
        TimingMetric timingMetric = this.createAndStartTimingMetric("doInBackground");
        object = null;
        if (!this.isCancelled()) {
            object = this.kit.doInBackground();
        }
        timingMetric.stopMeasuring();
        return (Result)object;
    }

    @Override
    public Priority getPriority() {
        return Priority.HIGH;
    }

    @Override
    protected void onCancelled(Result object) {
        this.kit.onCancelled(object);
        object = new InitializationException(this.kit.getIdentifier() + " Initialization was cancelled");
        this.kit.initializationCallback.failure((Exception)object);
    }

    @Override
    protected void onPostExecute(Result Result2) {
        this.kit.onPostExecute(Result2);
        this.kit.initializationCallback.success(Result2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        TimingMetric timingMetric = this.createAndStartTimingMetric("onPreExecute");
        try {
            boolean bl = this.kit.onPreExecute();
            return;
        }
        catch (UnmetDependencyException unmetDependencyException) {
            throw unmetDependencyException;
        }
        catch (Exception exception) {
            Fabric.getLogger().e("Fabric", "Failure onPreExecute()", exception);
            return;
        }
        finally {
            timingMetric.stopMeasuring();
            if (!false) {
                this.cancel(true);
            }
        }
    }
}

