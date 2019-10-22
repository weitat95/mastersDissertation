/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.bumptech.glide.load.engine;

import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DecodeJob;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.executor.Prioritized;
import com.bumptech.glide.request.ResourceCallback;

class EngineRunnable
implements Prioritized,
Runnable {
    private final DecodeJob<?, ?, ?> decodeJob;
    private volatile boolean isCancelled;
    private final EngineRunnableManager manager;
    private final Priority priority;
    private Stage stage;

    public EngineRunnable(EngineRunnableManager engineRunnableManager, DecodeJob<?, ?, ?> decodeJob, Priority priority) {
        this.manager = engineRunnableManager;
        this.decodeJob = decodeJob;
        this.stage = Stage.CACHE;
        this.priority = priority;
    }

    private Resource<?> decode() throws Exception {
        if (this.isDecodingFromCache()) {
            return this.decodeFromCache();
        }
        return this.decodeFromSource();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Resource<?> decodeFromCache() throws Exception {
        Resource<?> resource;
        Resource<?> resource2;
        block2: {
            resource = null;
            try {
                resource2 = this.decodeJob.decodeResultFromCache();
            }
            catch (Exception exception) {
                resource2 = resource;
                if (!Log.isLoggable((String)"EngineRunnable", (int)3)) break block2;
                Log.d((String)"EngineRunnable", (String)("Exception decoding result from cache: " + exception));
                resource2 = resource;
            }
        }
        resource = resource2;
        if (resource2 != null) return resource;
        return this.decodeJob.decodeSourceFromCache();
    }

    private Resource<?> decodeFromSource() throws Exception {
        return this.decodeJob.decodeFromSource();
    }

    private boolean isDecodingFromCache() {
        return this.stage == Stage.CACHE;
    }

    private void onLoadComplete(Resource resource) {
        this.manager.onResourceReady(resource);
    }

    private void onLoadFailed(Exception exception) {
        if (this.isDecodingFromCache()) {
            this.stage = Stage.SOURCE;
            this.manager.submitForSource(this);
            return;
        }
        this.manager.onException(exception);
    }

    public void cancel() {
        this.isCancelled = true;
        this.decodeJob.cancel();
    }

    @Override
    public int getPriority() {
        return this.priority.ordinal();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        Resource<?> resource;
        Exception exception;
        block8: {
            block9: {
                block7: {
                    block6: {
                        if (this.isCancelled) break block7;
                        exception = null;
                        resource = null;
                        try {
                            Resource<?> resource2;
                            resource = resource2 = this.decode();
                        }
                        catch (Exception exception2) {
                            if (!Log.isLoggable((String)"EngineRunnable", (int)2)) break block6;
                            Log.v((String)"EngineRunnable", (String)"Exception decoding", (Throwable)exception2);
                        }
                    }
                    if (!this.isCancelled) break block8;
                    if (resource != null) break block9;
                }
                return;
            }
            resource.recycle();
            return;
        }
        if (resource == null) {
            this.onLoadFailed(exception);
            return;
        }
        this.onLoadComplete(resource);
    }

    static interface EngineRunnableManager
    extends ResourceCallback {
        public void submitForSource(EngineRunnable var1);
    }

    private static enum Stage {
        CACHE,
        SOURCE;

    }

}

