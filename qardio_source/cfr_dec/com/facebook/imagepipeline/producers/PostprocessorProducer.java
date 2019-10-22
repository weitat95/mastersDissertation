/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.GuardedBy
 */
package com.facebook.imagepipeline.producers;

import android.graphics.Bitmap;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerContextCallbacks;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;
import com.facebook.imagepipeline.request.RepeatedPostprocessor;
import com.facebook.imagepipeline.request.RepeatedPostprocessorRunner;
import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class PostprocessorProducer
implements Producer<CloseableReference<CloseableImage>> {
    private final PlatformBitmapFactory mBitmapFactory;
    private final Executor mExecutor;
    private final Producer<CloseableReference<CloseableImage>> mInputProducer;

    public PostprocessorProducer(Producer<CloseableReference<CloseableImage>> producer, PlatformBitmapFactory platformBitmapFactory, Executor executor) {
        this.mInputProducer = Preconditions.checkNotNull(producer);
        this.mBitmapFactory = platformBitmapFactory;
        this.mExecutor = Preconditions.checkNotNull(executor);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public void produceResults(Consumer<CloseableReference<CloseableImage>> delegatingConsumer, ProducerContext producerContext) {
        void var2_6;
        void var1_4;
        ProducerListener producerListener = var2_6.getListener();
        Postprocessor postprocessor = var2_6.getImageRequest().getPostprocessor();
        PostprocessorConsumer postprocessorConsumer = new PostprocessorConsumer(delegatingConsumer, producerListener, var2_6.getId(), postprocessor, (ProducerContext)var2_6);
        if (postprocessor instanceof RepeatedPostprocessor) {
            RepeatedPostprocessorConsumer repeatedPostprocessorConsumer = new RepeatedPostprocessorConsumer(postprocessorConsumer, (RepeatedPostprocessor)postprocessor, (ProducerContext)var2_6);
        } else {
            SingleUsePostprocessorConsumer singleUsePostprocessorConsumer = new SingleUsePostprocessorConsumer(postprocessorConsumer);
        }
        this.mInputProducer.produceResults((Consumer<CloseableReference<CloseableImage>>)var1_4, (ProducerContext)var2_6);
    }

    private class PostprocessorConsumer
    extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> {
        @GuardedBy(value="PostprocessorConsumer.this")
        private boolean mIsClosed;
        @GuardedBy(value="PostprocessorConsumer.this")
        private boolean mIsDirty;
        @GuardedBy(value="PostprocessorConsumer.this")
        private boolean mIsLast;
        @GuardedBy(value="PostprocessorConsumer.this")
        private boolean mIsPostProcessingRunning;
        private final ProducerListener mListener;
        private final Postprocessor mPostprocessor;
        private final String mRequestId;
        @Nullable
        @GuardedBy(value="PostprocessorConsumer.this")
        private CloseableReference<CloseableImage> mSourceImageRef;

        public PostprocessorConsumer(Consumer<CloseableReference<CloseableImage>> consumer, ProducerListener producerListener, String string2, Postprocessor postprocessor, ProducerContext producerContext) {
            super(consumer);
            this.mSourceImageRef = null;
            this.mIsLast = false;
            this.mIsDirty = false;
            this.mIsPostProcessingRunning = false;
            this.mListener = producerListener;
            this.mRequestId = string2;
            this.mPostprocessor = postprocessor;
            producerContext.addCallbacks(new BaseProducerContextCallbacks(){

                @Override
                public void onCancellationRequested() {
                    PostprocessorConsumer.this.maybeNotifyOnCancellation();
                }
            });
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        private void clearRunningAndStartIfDirty() {
            // MONITORENTER : this
            this.mIsPostProcessingRunning = false;
            boolean bl = this.setRunningIfDirtyAndNotRunning();
            // MONITOREXIT : this
            if (!bl) return;
            this.submitPostprocessing();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private boolean close() {
            CloseableReference<CloseableImage> closeableReference;
            synchronized (this) {
                if (this.mIsClosed) {
                    return false;
                }
                closeableReference = this.mSourceImageRef;
                this.mSourceImageRef = null;
                this.mIsClosed = true;
            }
            CloseableReference.closeSafely(closeableReference);
            return true;
        }

        /*
         * Loose catch block
         * Enabled aggressive exception aggregation
         */
        private void doPostprocessing(CloseableReference<CloseableImage> closeableReference, boolean bl) {
            CloseableReference<CloseableImage> closeableReference2;
            Preconditions.checkArgument(CloseableReference.isValid(closeableReference));
            if (!this.shouldPostprocess(closeableReference.get())) {
                this.maybeNotifyOnNewResult(closeableReference, bl);
                return;
            }
            this.mListener.onProducerStart(this.mRequestId, "PostprocessorProducer");
            CloseableReference<CloseableImage> closeableReference3 = closeableReference2 = null;
            closeableReference3 = closeableReference = this.postprocessInternal(closeableReference.get());
            this.mListener.onProducerFinishWithSuccess(this.mRequestId, "PostprocessorProducer", this.getExtraMap(this.mListener, this.mRequestId, this.mPostprocessor));
            closeableReference3 = closeableReference;
            this.maybeNotifyOnNewResult(closeableReference, bl);
            CloseableReference.closeSafely(closeableReference);
            return;
            {
                catch (Exception exception) {
                    closeableReference3 = closeableReference2;
                    try {
                        this.mListener.onProducerFinishWithFailure(this.mRequestId, "PostprocessorProducer", exception, this.getExtraMap(this.mListener, this.mRequestId, this.mPostprocessor));
                        closeableReference3 = closeableReference2;
                        this.maybeNotifyOnFailure(exception);
                    }
                    catch (Throwable throwable) {
                        CloseableReference.closeSafely(closeableReference3);
                        throw throwable;
                    }
                    CloseableReference.closeSafely(null);
                    return;
                }
            }
        }

        private Map<String, String> getExtraMap(ProducerListener producerListener, String string2, Postprocessor postprocessor) {
            if (!producerListener.requiresExtraMap(string2)) {
                return null;
            }
            return ImmutableMap.of("Postprocessor", postprocessor.getName());
        }

        private boolean isClosed() {
            synchronized (this) {
                boolean bl = this.mIsClosed;
                return bl;
            }
        }

        private void maybeNotifyOnCancellation() {
            if (this.close()) {
                this.getConsumer().onCancellation();
            }
        }

        private void maybeNotifyOnFailure(Throwable throwable) {
            if (this.close()) {
                this.getConsumer().onFailure(throwable);
            }
        }

        private void maybeNotifyOnNewResult(CloseableReference<CloseableImage> closeableReference, boolean bl) {
            if (!bl && !this.isClosed() || bl && this.close()) {
                this.getConsumer().onNewResult(closeableReference, bl);
            }
        }

        private CloseableReference<CloseableImage> postprocessInternal(CloseableImage closeable) {
            CloseableStaticBitmap closeableStaticBitmap = (CloseableStaticBitmap)closeable;
            Object object = closeableStaticBitmap.getUnderlyingBitmap();
            object = this.mPostprocessor.process((Bitmap)object, PostprocessorProducer.this.mBitmapFactory);
            int n = closeableStaticBitmap.getRotationAngle();
            try {
                closeable = CloseableReference.of(new CloseableStaticBitmap((CloseableReference<Bitmap>)object, closeable.getQualityInfo(), n));
                return closeable;
            }
            finally {
                CloseableReference.closeSafely(object);
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private boolean setRunningIfDirtyAndNotRunning() {
            boolean bl = true;
            synchronized (this) {
                if (this.mIsClosed) return false;
                if (!this.mIsDirty) return false;
                if (this.mIsPostProcessingRunning) return false;
                if (!CloseableReference.isValid(this.mSourceImageRef)) return false;
                this.mIsPostProcessingRunning = true;
                return bl;
            }
        }

        private boolean shouldPostprocess(CloseableImage closeableImage) {
            return closeableImage instanceof CloseableStaticBitmap;
        }

        private void submitPostprocessing() {
            PostprocessorProducer.this.mExecutor.execute(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 * Converted monitor instructions to comments
                 * Lifted jumps to return sites
                 */
                @Override
                public void run() {
                    PostprocessorConsumer postprocessorConsumer = PostprocessorConsumer.this;
                    // MONITORENTER : postprocessorConsumer
                    CloseableReference closeableReference = PostprocessorConsumer.this.mSourceImageRef;
                    boolean bl = PostprocessorConsumer.this.mIsLast;
                    PostprocessorConsumer.this.mSourceImageRef = null;
                    PostprocessorConsumer.this.mIsDirty = false;
                    // MONITOREXIT : postprocessorConsumer
                    if (CloseableReference.isValid(closeableReference)) {
                        PostprocessorConsumer.this.doPostprocessing(closeableReference, bl);
                    }
                    PostprocessorConsumer.this.clearRunningAndStartIfDirty();
                    return;
                    finally {
                        CloseableReference.closeSafely(closeableReference);
                    }
                }
            });
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void updateSourceImageRef(@Nullable CloseableReference<CloseableImage> closeableReference, boolean bl) {
            CloseableReference<CloseableImage> closeableReference2;
            synchronized (this) {
                if (this.mIsClosed) {
                    return;
                }
                closeableReference2 = this.mSourceImageRef;
                this.mSourceImageRef = CloseableReference.cloneOrNull(closeableReference);
                this.mIsLast = bl;
                this.mIsDirty = true;
                bl = this.setRunningIfDirtyAndNotRunning();
            }
            CloseableReference.closeSafely(closeableReference2);
            if (bl) {
                this.submitPostprocessing();
                return;
            }
        }

        @Override
        protected void onCancellationImpl() {
            this.maybeNotifyOnCancellation();
        }

        @Override
        protected void onFailureImpl(Throwable throwable) {
            this.maybeNotifyOnFailure(throwable);
        }

        @Override
        protected void onNewResultImpl(CloseableReference<CloseableImage> closeableReference, boolean bl) {
            if (!CloseableReference.isValid(closeableReference)) {
                if (bl) {
                    this.maybeNotifyOnNewResult(null, true);
                }
                return;
            }
            this.updateSourceImageRef(closeableReference, bl);
        }

    }

    class RepeatedPostprocessorConsumer
    extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>>
    implements RepeatedPostprocessorRunner {
        @GuardedBy(value="RepeatedPostprocessorConsumer.this")
        private boolean mIsClosed;
        @Nullable
        @GuardedBy(value="RepeatedPostprocessorConsumer.this")
        private CloseableReference<CloseableImage> mSourceImageRef;

        private RepeatedPostprocessorConsumer(PostprocessorConsumer postprocessorConsumer, RepeatedPostprocessor repeatedPostprocessor, ProducerContext producerContext) {
            super(postprocessorConsumer);
            this.mIsClosed = false;
            this.mSourceImageRef = null;
            repeatedPostprocessor.setCallback(this);
            producerContext.addCallbacks(new BaseProducerContextCallbacks(){

                @Override
                public void onCancellationRequested() {
                    if (RepeatedPostprocessorConsumer.this.close()) {
                        RepeatedPostprocessorConsumer.this.getConsumer().onCancellation();
                    }
                }
            });
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private boolean close() {
            CloseableReference<CloseableImage> closeableReference;
            synchronized (this) {
                if (this.mIsClosed) {
                    return false;
                }
                closeableReference = this.mSourceImageRef;
                this.mSourceImageRef = null;
                this.mIsClosed = true;
            }
            CloseableReference.closeSafely(closeableReference);
            return true;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void setSourceImageRef(CloseableReference<CloseableImage> closeableReference) {
            CloseableReference<CloseableImage> closeableReference2;
            synchronized (this) {
                if (this.mIsClosed) {
                    return;
                }
                closeableReference2 = this.mSourceImageRef;
                this.mSourceImageRef = CloseableReference.cloneOrNull(closeableReference);
            }
            CloseableReference.closeSafely(closeableReference2);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void updateInternal() {
            CloseableReference<CloseableImage> closeableReference;
            synchronized (this) {
                if (this.mIsClosed) {
                    return;
                }
                closeableReference = CloseableReference.cloneOrNull(this.mSourceImageRef);
            }
            try {
                this.getConsumer().onNewResult(closeableReference, false);
                return;
            }
            finally {
                CloseableReference.closeSafely(closeableReference);
            }
        }

        @Override
        protected void onCancellationImpl() {
            if (this.close()) {
                this.getConsumer().onCancellation();
            }
        }

        @Override
        protected void onFailureImpl(Throwable throwable) {
            if (this.close()) {
                this.getConsumer().onFailure(throwable);
            }
        }

        @Override
        protected void onNewResultImpl(CloseableReference<CloseableImage> closeableReference, boolean bl) {
            if (!bl) {
                return;
            }
            this.setSourceImageRef(closeableReference);
            this.updateInternal();
        }

    }

    class SingleUsePostprocessorConsumer
    extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> {
        private SingleUsePostprocessorConsumer(PostprocessorConsumer postprocessorConsumer) {
            super(postprocessorConsumer);
        }

        @Override
        protected void onNewResultImpl(CloseableReference<CloseableImage> closeableReference, boolean bl) {
            if (!bl) {
                return;
            }
            this.getConsumer().onNewResult(closeableReference, bl);
        }
    }

}

