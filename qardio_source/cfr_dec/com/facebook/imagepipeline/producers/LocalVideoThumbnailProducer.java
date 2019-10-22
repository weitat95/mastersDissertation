/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.media.ThumbnailUtils
 */
package com.facebook.imagepipeline.producers;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.imagepipeline.bitmaps.SimpleBitmapReleaser;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerContextCallbacks;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.producers.StatefulProducerRunnable;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.File;
import java.util.Map;
import java.util.concurrent.Executor;

public class LocalVideoThumbnailProducer
implements Producer<CloseableReference<CloseableImage>> {
    private final Executor mExecutor;

    public LocalVideoThumbnailProducer(Executor executor) {
        this.mExecutor = executor;
    }

    private static int calculateKind(ImageRequest imageRequest) {
        if (imageRequest.getPreferredWidth() > 96 || imageRequest.getPreferredHeight() > 96) {
            return 1;
        }
        return 3;
    }

    @Override
    public void produceResults(Consumer<CloseableReference<CloseableImage>> object, ProducerContext producerContext) {
        object = new StatefulProducerRunnable<CloseableReference<CloseableImage>>(object, producerContext.getListener(), "VideoThumbnailProducer", producerContext.getId(), producerContext.getImageRequest()){
            final /* synthetic */ ImageRequest val$imageRequest;
            {
                this.val$imageRequest = imageRequest;
                super(consumer, producerListener, string2, string3);
            }

            @Override
            protected void disposeResult(CloseableReference<CloseableImage> closeableReference) {
                CloseableReference.closeSafely(closeableReference);
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            protected Map<String, String> getExtraMapOnSuccess(CloseableReference<CloseableImage> closeableReference) {
                boolean bl;
                if (closeableReference != null) {
                    bl = true;
                    do {
                        return ImmutableMap.of("createdThumbnail", String.valueOf(bl));
                        break;
                    } while (true);
                }
                bl = false;
                return ImmutableMap.of("createdThumbnail", String.valueOf(bl));
            }

            @Override
            protected CloseableReference<CloseableImage> getResult() throws Exception {
                Bitmap bitmap = ThumbnailUtils.createVideoThumbnail((String)this.val$imageRequest.getSourceFile().getPath(), (int)LocalVideoThumbnailProducer.calculateKind(this.val$imageRequest));
                if (bitmap == null) {
                    return null;
                }
                return CloseableReference.of(new CloseableStaticBitmap(bitmap, SimpleBitmapReleaser.getInstance(), ImmutableQualityInfo.FULL_QUALITY, 0));
            }
        };
        producerContext.addCallbacks(new BaseProducerContextCallbacks((StatefulProducerRunnable)object){
            final /* synthetic */ StatefulProducerRunnable val$cancellableProducerRunnable;
            {
                this.val$cancellableProducerRunnable = statefulProducerRunnable;
            }

            @Override
            public void onCancellationRequested() {
                this.val$cancellableProducerRunnable.cancel();
            }
        });
        this.mExecutor.execute((Runnable)object);
    }

}

