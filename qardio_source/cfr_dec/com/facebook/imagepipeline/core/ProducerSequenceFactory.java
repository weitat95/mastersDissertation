/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.facebook.imagepipeline.core;

import android.net.Uri;
import android.os.Build;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.media.MediaUtils;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.core.ProducerFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.AddImageTransformMetaDataProducer;
import com.facebook.imagepipeline.producers.BitmapMemoryCacheGetProducer;
import com.facebook.imagepipeline.producers.BitmapMemoryCacheKeyMultiplexProducer;
import com.facebook.imagepipeline.producers.BitmapMemoryCacheProducer;
import com.facebook.imagepipeline.producers.DataFetchProducer;
import com.facebook.imagepipeline.producers.DecodeProducer;
import com.facebook.imagepipeline.producers.DiskCacheProducer;
import com.facebook.imagepipeline.producers.EncodedCacheKeyMultiplexProducer;
import com.facebook.imagepipeline.producers.EncodedMemoryCacheProducer;
import com.facebook.imagepipeline.producers.LocalAssetFetchProducer;
import com.facebook.imagepipeline.producers.LocalContentUriFetchProducer;
import com.facebook.imagepipeline.producers.LocalContentUriThumbnailFetchProducer;
import com.facebook.imagepipeline.producers.LocalExifThumbnailProducer;
import com.facebook.imagepipeline.producers.LocalFileFetchProducer;
import com.facebook.imagepipeline.producers.LocalResourceFetchProducer;
import com.facebook.imagepipeline.producers.LocalVideoThumbnailProducer;
import com.facebook.imagepipeline.producers.NetworkFetchProducer;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import com.facebook.imagepipeline.producers.PostprocessedBitmapMemoryCacheProducer;
import com.facebook.imagepipeline.producers.PostprocessorProducer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ResizeAndRotateProducer;
import com.facebook.imagepipeline.producers.ThreadHandoffProducer;
import com.facebook.imagepipeline.producers.ThreadHandoffProducerQueue;
import com.facebook.imagepipeline.producers.ThrottlingProducer;
import com.facebook.imagepipeline.producers.ThumbnailBranchProducer;
import com.facebook.imagepipeline.producers.ThumbnailProducer;
import com.facebook.imagepipeline.producers.WebpTranscodeProducer;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;
import java.util.HashMap;
import java.util.Map;

public class ProducerSequenceFactory {
    Map<Producer<CloseableReference<CloseableImage>>, Producer<Void>> mCloseableImagePrefetchSequences;
    private Producer<EncodedImage> mCommonNetworkFetchToEncodedMemorySequence;
    Producer<CloseableReference<CloseableImage>> mDataFetchSequence;
    Producer<CloseableReference<CloseableImage>> mLocalAssetFetchSequence;
    Producer<CloseableReference<CloseableImage>> mLocalContentUriFetchSequence;
    Producer<CloseableReference<CloseableImage>> mLocalImageFileFetchSequence;
    Producer<CloseableReference<CloseableImage>> mLocalResourceFetchSequence;
    Producer<CloseableReference<CloseableImage>> mLocalVideoFileFetchSequence;
    Producer<CloseableReference<CloseableImage>> mNetworkFetchSequence;
    private final NetworkFetcher mNetworkFetcher;
    Map<Producer<CloseableReference<CloseableImage>>, Producer<CloseableReference<CloseableImage>>> mPostprocessorSequences;
    private final ProducerFactory mProducerFactory;
    private final boolean mResizeAndRotateEnabledForNetwork;
    private final ThreadHandoffProducerQueue mThreadHandoffProducerQueue;
    private final int mThrottlingMaxSimultaneousRequests;
    private final boolean mWebpSupportEnabled;

    public ProducerSequenceFactory(ProducerFactory producerFactory, NetworkFetcher networkFetcher, boolean bl, boolean bl2, ThreadHandoffProducerQueue threadHandoffProducerQueue, int n) {
        this.mProducerFactory = producerFactory;
        this.mNetworkFetcher = networkFetcher;
        this.mResizeAndRotateEnabledForNetwork = bl;
        this.mWebpSupportEnabled = bl2;
        this.mPostprocessorSequences = new HashMap<Producer<CloseableReference<CloseableImage>>, Producer<CloseableReference<CloseableImage>>>();
        this.mCloseableImagePrefetchSequences = new HashMap<Producer<CloseableReference<CloseableImage>>, Producer<Void>>();
        this.mThreadHandoffProducerQueue = threadHandoffProducerQueue;
        this.mThrottlingMaxSimultaneousRequests = n;
    }

    private Producer<CloseableReference<CloseableImage>> getBasicDecodedImageSequence(ImageRequest imageRequest) {
        Preconditions.checkNotNull(imageRequest);
        imageRequest = imageRequest.getSourceUri();
        Preconditions.checkNotNull(imageRequest, "Uri is null.");
        if (UriUtil.isNetworkUri((Uri)imageRequest)) {
            return this.getNetworkFetchSequence();
        }
        if (UriUtil.isLocalFileUri((Uri)imageRequest)) {
            if (MediaUtils.isVideo(MediaUtils.extractMime(imageRequest.getPath()))) {
                return this.getLocalVideoFileFetchSequence();
            }
            return this.getLocalImageFileFetchSequence();
        }
        if (UriUtil.isLocalContentUri((Uri)imageRequest)) {
            return this.getLocalContentUriFetchSequence();
        }
        if (UriUtil.isLocalAssetUri((Uri)imageRequest)) {
            return this.getLocalAssetFetchSequence();
        }
        if (UriUtil.isLocalResourceUri((Uri)imageRequest)) {
            return this.getLocalResourceFetchSequence();
        }
        if (UriUtil.isDataUri((Uri)imageRequest)) {
            return this.getDataFetchSequence();
        }
        throw new IllegalArgumentException("Unsupported uri scheme! Uri is: " + ProducerSequenceFactory.getShortenedUriString((Uri)imageRequest));
    }

    private Producer<EncodedImage> getCommonNetworkFetchToEncodedMemorySequence() {
        synchronized (this) {
            if (this.mCommonNetworkFetchToEncodedMemorySequence == null) {
                this.mCommonNetworkFetchToEncodedMemorySequence = ProducerFactory.newAddImageTransformMetaDataProducer(this.newEncodedCacheMultiplexToTranscodeSequence(this.mProducerFactory.newNetworkFetchProducer(this.mNetworkFetcher)));
                this.mCommonNetworkFetchToEncodedMemorySequence = this.mProducerFactory.newResizeAndRotateProducer(this.mCommonNetworkFetchToEncodedMemorySequence, this.mResizeAndRotateEnabledForNetwork);
            }
            Producer<EncodedImage> producer = this.mCommonNetworkFetchToEncodedMemorySequence;
            return producer;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Producer<CloseableReference<CloseableImage>> getDataFetchSequence() {
        synchronized (this) {
            void var1_5;
            if (this.mDataFetchSequence != null) return this.mDataFetchSequence;
            Object object = this.mProducerFactory.newDataFetchProducer();
            DataFetchProducer dataFetchProducer = object;
            if (Build.VERSION.SDK_INT < 18) {
                DataFetchProducer dataFetchProducer2 = object;
                if (!this.mWebpSupportEnabled) {
                    WebpTranscodeProducer webpTranscodeProducer = this.mProducerFactory.newWebpTranscodeProducer((Producer<EncodedImage>)object);
                }
            }
            object = this.mProducerFactory;
            AddImageTransformMetaDataProducer addImageTransformMetaDataProducer = ProducerFactory.newAddImageTransformMetaDataProducer((Producer<EncodedImage>)var1_5);
            this.mDataFetchSequence = this.newBitmapCacheGetToDecodeSequence(this.mProducerFactory.newResizeAndRotateProducer(addImageTransformMetaDataProducer, true));
            return this.mDataFetchSequence;
        }
    }

    private Producer<CloseableReference<CloseableImage>> getLocalAssetFetchSequence() {
        synchronized (this) {
            if (this.mLocalAssetFetchSequence == null) {
                this.mLocalAssetFetchSequence = this.newBitmapCacheGetToLocalTransformSequence(this.mProducerFactory.newLocalAssetFetchProducer());
            }
            Producer<CloseableReference<CloseableImage>> producer = this.mLocalAssetFetchSequence;
            return producer;
        }
    }

    private Producer<CloseableReference<CloseableImage>> getLocalContentUriFetchSequence() {
        synchronized (this) {
            if (this.mLocalContentUriFetchSequence == null) {
                this.mLocalContentUriFetchSequence = this.newBitmapCacheGetToLocalTransformSequence(this.mProducerFactory.newLocalContentUriFetchProducer(), new ThumbnailProducer[]{this.mProducerFactory.newLocalContentUriThumbnailFetchProducer(), this.mProducerFactory.newLocalExifThumbnailProducer()});
            }
            Producer<CloseableReference<CloseableImage>> producer = this.mLocalContentUriFetchSequence;
            return producer;
        }
    }

    private Producer<CloseableReference<CloseableImage>> getLocalImageFileFetchSequence() {
        synchronized (this) {
            if (this.mLocalImageFileFetchSequence == null) {
                this.mLocalImageFileFetchSequence = this.newBitmapCacheGetToLocalTransformSequence(this.mProducerFactory.newLocalFileFetchProducer());
            }
            Producer<CloseableReference<CloseableImage>> producer = this.mLocalImageFileFetchSequence;
            return producer;
        }
    }

    private Producer<CloseableReference<CloseableImage>> getLocalResourceFetchSequence() {
        synchronized (this) {
            if (this.mLocalResourceFetchSequence == null) {
                this.mLocalResourceFetchSequence = this.newBitmapCacheGetToLocalTransformSequence(this.mProducerFactory.newLocalResourceFetchProducer());
            }
            Producer<CloseableReference<CloseableImage>> producer = this.mLocalResourceFetchSequence;
            return producer;
        }
    }

    private Producer<CloseableReference<CloseableImage>> getLocalVideoFileFetchSequence() {
        synchronized (this) {
            if (this.mLocalVideoFileFetchSequence == null) {
                this.mLocalVideoFileFetchSequence = this.newBitmapCacheGetToBitmapCacheSequence(this.mProducerFactory.newLocalVideoThumbnailProducer());
            }
            Producer<CloseableReference<CloseableImage>> producer = this.mLocalVideoFileFetchSequence;
            return producer;
        }
    }

    private Producer<CloseableReference<CloseableImage>> getNetworkFetchSequence() {
        synchronized (this) {
            if (this.mNetworkFetchSequence == null) {
                this.mNetworkFetchSequence = this.newBitmapCacheGetToDecodeSequence(this.getCommonNetworkFetchToEncodedMemorySequence());
            }
            Producer<CloseableReference<CloseableImage>> producer = this.mNetworkFetchSequence;
            return producer;
        }
    }

    private Producer<CloseableReference<CloseableImage>> getPostprocessorSequence(Producer<CloseableReference<CloseableImage>> producer) {
        synchronized (this) {
            if (!this.mPostprocessorSequences.containsKey(producer)) {
                Producer<CloseableReference<CloseableImage>> producer2 = this.mProducerFactory.newPostprocessorProducer(producer);
                producer2 = this.mProducerFactory.newPostprocessorBitmapMemoryCacheProducer(producer2);
                this.mPostprocessorSequences.put(producer, producer2);
            }
            producer = this.mPostprocessorSequences.get(producer);
            return producer;
        }
    }

    private static String getShortenedUriString(Uri object) {
        String string2 = String.valueOf(object);
        object = string2;
        if (string2.length() > 30) {
            object = string2.substring(0, 30) + "...";
        }
        return object;
    }

    private Producer<CloseableReference<CloseableImage>> newBitmapCacheGetToBitmapCacheSequence(Producer<CloseableReference<CloseableImage>> producer) {
        producer = this.mProducerFactory.newBitmapMemoryCacheProducer(producer);
        producer = this.mProducerFactory.newBitmapMemoryCacheKeyMultiplexProducer(producer);
        producer = this.mProducerFactory.newBackgroundThreadHandoffProducer(producer, this.mThreadHandoffProducerQueue);
        return this.mProducerFactory.newBitmapMemoryCacheGetProducer(producer);
    }

    private Producer<CloseableReference<CloseableImage>> newBitmapCacheGetToDecodeSequence(Producer<EncodedImage> producer) {
        return this.newBitmapCacheGetToBitmapCacheSequence(this.mProducerFactory.newDecodeProducer(producer));
    }

    private Producer<CloseableReference<CloseableImage>> newBitmapCacheGetToLocalTransformSequence(Producer<EncodedImage> producer) {
        return this.newBitmapCacheGetToLocalTransformSequence(producer, new ThumbnailProducer[]{this.mProducerFactory.newLocalExifThumbnailProducer()});
    }

    private Producer<CloseableReference<CloseableImage>> newBitmapCacheGetToLocalTransformSequence(Producer<EncodedImage> producer, ThumbnailProducer<EncodedImage>[] arrthumbnailProducer) {
        return this.newBitmapCacheGetToDecodeSequence(this.newLocalTransformationsSequence(this.newEncodedCacheMultiplexToTranscodeSequence(producer), arrthumbnailProducer));
    }

    private Producer<EncodedImage> newEncodedCacheMultiplexToTranscodeSequence(Producer<EncodedImage> producer) {
        Producer<EncodedImage> producer2 = producer;
        if (Build.VERSION.SDK_INT < 18) {
            producer2 = producer;
            if (!this.mWebpSupportEnabled) {
                producer2 = this.mProducerFactory.newWebpTranscodeProducer(producer);
            }
        }
        producer = this.mProducerFactory.newDiskCacheProducer(producer2);
        producer = this.mProducerFactory.newEncodedMemoryCacheProducer(producer);
        return this.mProducerFactory.newEncodedCacheKeyMultiplexProducer(producer);
    }

    private Producer<EncodedImage> newLocalThumbnailProducer(ThumbnailProducer<EncodedImage>[] object) {
        object = this.mProducerFactory.newThumbnailBranchProducer((ThumbnailProducer<EncodedImage>[])object);
        return this.mProducerFactory.newResizeAndRotateProducer((Producer<EncodedImage>)object, true);
    }

    private Producer<EncodedImage> newLocalTransformationsSequence(Producer<EncodedImage> producer, ThumbnailProducer<EncodedImage>[] arrthumbnailProducer) {
        producer = ProducerFactory.newAddImageTransformMetaDataProducer(producer);
        producer = this.mProducerFactory.newResizeAndRotateProducer(producer, true);
        producer = this.mProducerFactory.newThrottlingProducer(this.mThrottlingMaxSimultaneousRequests, producer);
        ProducerFactory producerFactory = this.mProducerFactory;
        return ProducerFactory.newBranchOnSeparateImagesProducer(this.newLocalThumbnailProducer(arrthumbnailProducer), producer);
    }

    public Producer<CloseableReference<CloseableImage>> getDecodedImageProducerSequence(ImageRequest imageRequest) {
        Producer<CloseableReference<CloseableImage>> producer;
        Producer<CloseableReference<CloseableImage>> producer2 = producer = this.getBasicDecodedImageSequence(imageRequest);
        if (imageRequest.getPostprocessor() != null) {
            producer2 = this.getPostprocessorSequence(producer);
        }
        return producer2;
    }
}

