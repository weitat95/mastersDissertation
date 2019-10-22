/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.GuardedBy
 */
package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerContextCallbacks;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class BaseProducerContext
implements ProducerContext {
    @GuardedBy(value="this")
    private final List<ProducerContextCallbacks> mCallbacks;
    private final Object mCallerContext;
    private final String mId;
    private final ImageRequest mImageRequest;
    @GuardedBy(value="this")
    private boolean mIsCancelled;
    @GuardedBy(value="this")
    private boolean mIsIntermediateResultExpected;
    @GuardedBy(value="this")
    private boolean mIsPrefetch;
    private final ImageRequest.RequestLevel mLowestPermittedRequestLevel;
    @GuardedBy(value="this")
    private Priority mPriority;
    private final ProducerListener mProducerListener;

    public BaseProducerContext(ImageRequest imageRequest, String string2, ProducerListener producerListener, Object object, ImageRequest.RequestLevel requestLevel, boolean bl, boolean bl2, Priority priority) {
        this.mImageRequest = imageRequest;
        this.mId = string2;
        this.mProducerListener = producerListener;
        this.mCallerContext = object;
        this.mLowestPermittedRequestLevel = requestLevel;
        this.mIsPrefetch = bl;
        this.mPriority = priority;
        this.mIsIntermediateResultExpected = bl2;
        this.mIsCancelled = false;
        this.mCallbacks = new ArrayList<ProducerContextCallbacks>();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void callOnCancellationRequested(@Nullable List<ProducerContextCallbacks> iterator) {
        if (iterator != null) {
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                ((ProducerContextCallbacks)iterator.next()).onCancellationRequested();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void callOnIsIntermediateResultExpectedChanged(@Nullable List<ProducerContextCallbacks> iterator) {
        if (iterator != null) {
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                ((ProducerContextCallbacks)iterator.next()).onIsIntermediateResultExpectedChanged();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void callOnIsPrefetchChanged(@Nullable List<ProducerContextCallbacks> iterator) {
        if (iterator != null) {
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                ((ProducerContextCallbacks)iterator.next()).onIsPrefetchChanged();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void callOnPriorityChanged(@Nullable List<ProducerContextCallbacks> iterator) {
        if (iterator != null) {
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                ((ProducerContextCallbacks)iterator.next()).onPriorityChanged();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void addCallbacks(ProducerContextCallbacks producerContextCallbacks) {
        boolean bl = false;
        // MONITORENTER : this
        this.mCallbacks.add(producerContextCallbacks);
        if (this.mIsCancelled) {
            bl = true;
        }
        // MONITOREXIT : this
        if (!bl) return;
        producerContextCallbacks.onCancellationRequested();
    }

    public void cancel() {
        BaseProducerContext.callOnCancellationRequested(this.cancelNoCallbacks());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    public List<ProducerContextCallbacks> cancelNoCallbacks() {
        synchronized (this) {
            block6: {
                boolean bl = this.mIsCancelled;
                if (!bl) break block6;
                return null;
            }
            this.mIsCancelled = true;
            ArrayList<ProducerContextCallbacks> arrayList = new ArrayList<ProducerContextCallbacks>(this.mCallbacks);
            return arrayList;
        }
    }

    @Override
    public Object getCallerContext() {
        return this.mCallerContext;
    }

    @Override
    public String getId() {
        return this.mId;
    }

    @Override
    public ImageRequest getImageRequest() {
        return this.mImageRequest;
    }

    @Override
    public ProducerListener getListener() {
        return this.mProducerListener;
    }

    @Override
    public ImageRequest.RequestLevel getLowestPermittedRequestLevel() {
        return this.mLowestPermittedRequestLevel;
    }

    @Override
    public Priority getPriority() {
        synchronized (this) {
            Priority priority = this.mPriority;
            return priority;
        }
    }

    @Override
    public boolean isIntermediateResultExpected() {
        synchronized (this) {
            boolean bl = this.mIsIntermediateResultExpected;
            return bl;
        }
    }

    @Override
    public boolean isPrefetch() {
        synchronized (this) {
            boolean bl = this.mIsPrefetch;
            return bl;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    public List<ProducerContextCallbacks> setIsIntermediateResultExpectedNoCallbacks(boolean bl) {
        synchronized (this) {
            block6: {
                boolean bl2 = this.mIsIntermediateResultExpected;
                if (bl != bl2) break block6;
                return null;
            }
            this.mIsIntermediateResultExpected = bl;
            ArrayList<ProducerContextCallbacks> arrayList = new ArrayList<ProducerContextCallbacks>(this.mCallbacks);
            return arrayList;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    public List<ProducerContextCallbacks> setIsPrefetchNoCallbacks(boolean bl) {
        synchronized (this) {
            block6: {
                boolean bl2 = this.mIsPrefetch;
                if (bl != bl2) break block6;
                return null;
            }
            this.mIsPrefetch = bl;
            ArrayList<ProducerContextCallbacks> arrayList = new ArrayList<ProducerContextCallbacks>(this.mCallbacks);
            return arrayList;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    public List<ProducerContextCallbacks> setPriorityNoCallbacks(Priority object) {
        synchronized (this) {
            void var1_3;
            block6: {
                Priority priority = this.mPriority;
                if (object != priority) break block6;
                return var1_3;
            }
            this.mPriority = object;
            ArrayList<ProducerContextCallbacks> arrayList = new ArrayList<ProducerContextCallbacks>(this.mCallbacks);
            return var1_3;
        }
    }
}

