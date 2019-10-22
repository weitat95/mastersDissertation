/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.media.ExifInterface
 *  android.net.Uri
 *  android.util.Pair
 */
package com.facebook.imagepipeline.producers;

import android.content.ContentResolver;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Pair;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.memory.PooledByteBufferInputStream;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerContextCallbacks;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.producers.StatefulProducerRunnable;
import com.facebook.imagepipeline.producers.ThumbnailProducer;
import com.facebook.imagepipeline.producers.ThumbnailSizeChecker;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imageutils.BitmapUtil;
import com.facebook.imageutils.JfifUtil;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;

public class LocalExifThumbnailProducer
implements ThumbnailProducer<EncodedImage> {
    private final ContentResolver mContentResolver;
    private final Executor mExecutor;
    private final PooledByteBufferFactory mPooledByteBufferFactory;

    public LocalExifThumbnailProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, ContentResolver contentResolver) {
        this.mExecutor = executor;
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        this.mContentResolver = contentResolver;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private EncodedImage buildEncodedImage(PooledByteBuffer closeable, ExifInterface object) {
        EncodedImage encodedImage;
        int n = -1;
        Pair<Integer, Integer> pair = BitmapUtil.decodeDimensions(new PooledByteBufferInputStream((PooledByteBuffer)closeable));
        int n2 = this.getRotationAngle((ExifInterface)encodedImage);
        int n3 = pair != null ? (Integer)pair.first : -1;
        if (pair != null) {
            n = (Integer)pair.second;
        }
        CloseableReference<PooledByteBuffer> closeableReference = CloseableReference.of(closeable);
        try {
            encodedImage = new EncodedImage(closeableReference);
            encodedImage.setImageFormat(DefaultImageFormats.JPEG);
            encodedImage.setRotationAngle(n2);
            encodedImage.setWidth(n3);
            encodedImage.setHeight(n);
            return encodedImage;
        }
        finally {
            CloseableReference.closeSafely(closeableReference);
        }
    }

    private int getRotationAngle(ExifInterface exifInterface) {
        return JfifUtil.getAutoRotateAngleFromOrientation(Integer.parseInt(exifInterface.getAttribute("Orientation")));
    }

    @Override
    public boolean canProvideImageForSize(ResizeOptions resizeOptions) {
        return ThumbnailSizeChecker.isImageBigEnough(512, 512, resizeOptions);
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean canReadAsFile(String object) throws IOException {
        return object != null && ((File)(object = new File((String)object))).exists() && ((File)object).canRead();
    }

    ExifInterface getExifInterface(Uri object) throws IOException {
        if (this.canReadAsFile((String)(object = UriUtil.getRealPathFromUri(this.mContentResolver, object)))) {
            return new ExifInterface((String)object);
        }
        return null;
    }

    @Override
    public void produceResults(Consumer<EncodedImage> object, ProducerContext producerContext) {
        object = new StatefulProducerRunnable<EncodedImage>(object, producerContext.getListener(), "LocalExifThumbnailProducer", producerContext.getId(), producerContext.getImageRequest()){
            final /* synthetic */ ImageRequest val$imageRequest;
            {
                this.val$imageRequest = imageRequest;
                super(consumer, producerListener, string2, string3);
            }

            @Override
            protected void disposeResult(EncodedImage encodedImage) {
                EncodedImage.closeSafely(encodedImage);
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            protected Map<String, String> getExtraMapOnSuccess(EncodedImage encodedImage) {
                boolean bl;
                if (encodedImage != null) {
                    bl = true;
                    do {
                        return ImmutableMap.of("createdThumbnail", Boolean.toString(bl));
                        break;
                    } while (true);
                }
                bl = false;
                return ImmutableMap.of("createdThumbnail", Boolean.toString(bl));
            }

            @Override
            protected EncodedImage getResult() throws Exception {
                Uri uri = this.val$imageRequest.getSourceUri();
                if ((uri = LocalExifThumbnailProducer.this.getExifInterface(uri)) == null || !uri.hasThumbnail()) {
                    return null;
                }
                Object object = uri.getThumbnail();
                object = LocalExifThumbnailProducer.this.mPooledByteBufferFactory.newByteBuffer((byte[])object);
                return LocalExifThumbnailProducer.this.buildEncodedImage((PooledByteBuffer)object, (ExifInterface)uri);
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

