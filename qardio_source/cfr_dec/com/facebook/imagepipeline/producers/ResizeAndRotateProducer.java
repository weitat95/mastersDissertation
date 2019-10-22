/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Closeables;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.TriState;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.memory.PooledByteBufferOutputStream;
import com.facebook.imagepipeline.nativecode.JpegTranscoder;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.JobScheduler;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerContextCallbacks;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class ResizeAndRotateProducer
implements Producer<EncodedImage> {
    private final Executor mExecutor;
    private final Producer<EncodedImage> mInputProducer;
    private final PooledByteBufferFactory mPooledByteBufferFactory;
    private final boolean mResizingEnabled;

    public ResizeAndRotateProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, boolean bl, Producer<EncodedImage> producer) {
        this.mExecutor = Preconditions.checkNotNull(executor);
        this.mPooledByteBufferFactory = Preconditions.checkNotNull(pooledByteBufferFactory);
        this.mResizingEnabled = bl;
        this.mInputProducer = Preconditions.checkNotNull(producer);
    }

    static /* synthetic */ PooledByteBufferFactory access$700(ResizeAndRotateProducer resizeAndRotateProducer) {
        return resizeAndRotateProducer.mPooledByteBufferFactory;
    }

    static /* synthetic */ int access$800(ImageRequest imageRequest, EncodedImage encodedImage, boolean bl) {
        return ResizeAndRotateProducer.getScaleNumerator(imageRequest, encodedImage, bl);
    }

    static /* synthetic */ int access$900(RotationOptions rotationOptions, EncodedImage encodedImage) {
        return ResizeAndRotateProducer.getRotationAngle(rotationOptions, encodedImage);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static float determineResizeRatio(ResizeOptions resizeOptions, int n, int n2) {
        float f;
        if (resizeOptions == null) {
            return 1.0f;
        }
        float f2 = f = Math.max((float)resizeOptions.width / (float)n, (float)resizeOptions.height / (float)n2);
        if ((float)n * f > resizeOptions.maxBitmapSize) {
            f2 = resizeOptions.maxBitmapSize / (float)n;
        }
        f = f2;
        if (!((float)n2 * f2 > resizeOptions.maxBitmapSize)) return f;
        return resizeOptions.maxBitmapSize / (float)n2;
    }

    private static int getRotationAngle(RotationOptions rotationOptions, EncodedImage encodedImage) {
        if (rotationOptions.useImageMetadata()) {
            int n;
            int n2 = n = encodedImage.getRotationAngle();
            switch (n) {
                default: {
                    n2 = 0;
                }
                case 90: 
                case 180: 
                case 270: 
            }
            return n2;
        }
        return rotationOptions.getForcedAngle();
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int getScaleNumerator(ImageRequest imageRequest, EncodedImage encodedImage, boolean bl) {
        if (!bl) {
            return 8;
        }
        ResizeOptions resizeOptions = imageRequest.getResizeOptions();
        if (resizeOptions == null) {
            return 8;
        }
        int n = ResizeAndRotateProducer.getRotationAngle(imageRequest.getRotationOptions(), encodedImage);
        int n2 = n == 90 || n == 270 ? 1 : 0;
        n = n2 != 0 ? encodedImage.getHeight() : encodedImage.getWidth();
        if ((n2 = ResizeAndRotateProducer.roundNumerator(ResizeAndRotateProducer.determineResizeRatio(resizeOptions, n, n2 = n2 != 0 ? encodedImage.getWidth() : encodedImage.getHeight()), resizeOptions.roundUpFraction)) > 8) {
            return 8;
        }
        n = n2;
        if (n2 >= 1) return n;
        return 1;
    }

    static int roundNumerator(float f, float f2) {
        return (int)(8.0f * f + f2);
    }

    private static boolean shouldResize(int n) {
        return n < 8;
    }

    private static boolean shouldRotate(RotationOptions rotationOptions, EncodedImage encodedImage) {
        return !rotationOptions.canDeferUntilRendered() && ResizeAndRotateProducer.getRotationAngle(rotationOptions, encodedImage) != 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static TriState shouldTransform(ImageRequest imageRequest, EncodedImage encodedImage, boolean bl) {
        if (encodedImage == null || encodedImage.getImageFormat() == ImageFormat.UNKNOWN) {
            return TriState.UNSET;
        }
        if (encodedImage.getImageFormat() != DefaultImageFormats.JPEG) {
            return TriState.NO;
        }
        if (ResizeAndRotateProducer.shouldRotate(imageRequest.getRotationOptions(), encodedImage) || ResizeAndRotateProducer.shouldResize(ResizeAndRotateProducer.getScaleNumerator(imageRequest, encodedImage, bl))) {
            bl = true;
            do {
                return TriState.valueOf(bl);
                break;
            } while (true);
        }
        bl = false;
        return TriState.valueOf(bl);
    }

    @Override
    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        this.mInputProducer.produceResults(new TransformingConsumer(consumer, producerContext), producerContext);
    }

    private class TransformingConsumer
    extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private boolean mIsCancelled;
        private final JobScheduler mJobScheduler;
        private final ProducerContext mProducerContext;

        public TransformingConsumer(final Consumer<EncodedImage> consumer, ProducerContext object) {
            super(consumer);
            this.mIsCancelled = false;
            this.mProducerContext = object;
            object = new JobScheduler.JobRunnable(){

                @Override
                public void run(EncodedImage encodedImage, boolean bl) {
                    TransformingConsumer.this.doTransform(encodedImage, bl);
                }
            };
            this.mJobScheduler = new JobScheduler(ResizeAndRotateProducer.this.mExecutor, (JobScheduler.JobRunnable)object, 100);
            this.mProducerContext.addCallbacks(new BaseProducerContextCallbacks(){

                @Override
                public void onCancellationRequested() {
                    TransformingConsumer.this.mJobScheduler.clearJob();
                    TransformingConsumer.this.mIsCancelled = true;
                    consumer.onCancellation();
                }

                @Override
                public void onIsIntermediateResultExpectedChanged() {
                    if (TransformingConsumer.this.mProducerContext.isIntermediateResultExpected()) {
                        TransformingConsumer.this.mJobScheduler.scheduleJob();
                    }
                }
            });
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private void doTransform(EncodedImage var1_1, boolean var2_9) {
            this.mProducerContext.getListener().onProducerStart(this.mProducerContext.getId(), "ResizeAndRotateProducer");
            var11_11 = this.mProducerContext.getImageRequest();
            var10_12 = ResizeAndRotateProducer.access$700(ResizeAndRotateProducer.this).newOutputStream();
            var8_13 = null;
            var9_14 = null;
            var4_16 = null;
            var6_17 = var8_13;
            var7_18 = var4_16;
            var5_19 = var9_14;
            var3_21 = ResizeAndRotateProducer.access$800(var11_11, var1_1, ResizeAndRotateProducer.access$500(ResizeAndRotateProducer.this));
            var6_17 = var8_13;
            var7_18 = var4_16;
            var5_19 = var9_14;
            var6_17 = var8_13 = this.getExtraMap(var1_1, var11_11, var3_21);
            var7_18 = var4_16;
            var5_19 = var9_14;
            var4_16 = var1_1.getInputStream();
            var6_17 = var8_13;
            var7_18 = var4_16;
            var5_19 = var4_16;
            JpegTranscoder.transcodeJpeg(var4_16, var10_12, ResizeAndRotateProducer.access$900(var11_11.getRotationOptions(), var1_1), var3_21, 85);
            var6_17 = var8_13;
            var7_18 = var4_16;
            var5_19 = var4_16;
            var9_15 = CloseableReference.of(var10_12.toByteBuffer());
            var1_1 = new EncodedImage(var9_15);
            var1_1.setImageFormat(DefaultImageFormats.JPEG);
            {
                catch (Throwable var1_2) {}
            }
            var1_1.parseMetaData();
            this.mProducerContext.getListener().onProducerFinishWithSuccess(this.mProducerContext.getId(), "ResizeAndRotateProducer", var8_13);
            this.getConsumer().onNewResult(var1_1, (boolean)var2_10);
            EncodedImage.closeSafely(var1_1);
            CloseableReference.closeSafely(var9_15);
            Closeables.closeQuietly(var4_16);
            var10_12.close();
            return;
            catch (Throwable var5_20) {
                EncodedImage.closeSafely(var1_1);
                throw var5_20;
                ** GOTO lbl-1000
                catch (Throwable var1_7) {
                    ** GOTO lbl75
                }
                catch (Exception var1_8) {
                    var6_17 = var8_13;
                    ** GOTO lbl66
                }
                catch (Throwable var1_9) {}
lbl-1000:
                // 2 sources
                {
                    var6_17 = var8_13;
                    var7_18 = var4_16;
                    var5_19 = var4_16;
                    try {
                        try {
                            CloseableReference.closeSafely(var9_15);
                            var6_17 = var8_13;
                            var7_18 = var4_16;
                            var5_19 = var4_16;
                            throw var1_1;
                        }
                        catch (Exception var1_3) {
                            var4_16 = var7_18;
lbl66:
                            // 2 sources
                            var5_19 = var4_16;
                            this.mProducerContext.getListener().onProducerFinishWithFailure(this.mProducerContext.getId(), "ResizeAndRotateProducer", (Throwable)var1_4, var6_17);
                            var5_19 = var4_16;
                            this.getConsumer().onFailure((Throwable)var1_4);
                            Closeables.closeQuietly(var4_16);
                            var10_12.close();
                            return;
                        }
                    }
                    catch (Throwable var1_5) {
                        var4_16 = var5_19;
lbl75:
                        // 2 sources
                        Closeables.closeQuietly(var4_16);
                        var10_12.close();
                        throw var1_6;
                    }
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private Map<String, String> getExtraMap(EncodedImage object, ImageRequest object2, int n) {
            if (!this.mProducerContext.getListener().requiresExtraMap(this.mProducerContext.getId())) {
                return null;
            }
            String string2 = ((EncodedImage)object).getWidth() + "x" + ((EncodedImage)object).getHeight();
            object = ((ImageRequest)object2).getResizeOptions() != null ? object2.getResizeOptions().width + "x" + object2.getResizeOptions().height : "Unspecified";
            if (n > 0) {
                object2 = n + "/8";
                return ImmutableMap.of("Original size", string2, "Requested size", object, "Fraction", object2, "queueTime", String.valueOf(this.mJobScheduler.getQueuedTime()));
            }
            object2 = "";
            return ImmutableMap.of("Original size", string2, "Requested size", object, "Fraction", object2, "queueTime", String.valueOf(this.mJobScheduler.getQueuedTime()));
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        protected void onNewResultImpl(@Nullable EncodedImage encodedImage, boolean bl) {
            if (this.mIsCancelled) return;
            if (encodedImage == null) {
                if (!bl) return;
                {
                    this.getConsumer().onNewResult(null, true);
                    return;
                }
            }
            TriState triState = ResizeAndRotateProducer.shouldTransform(this.mProducerContext.getImageRequest(), encodedImage, ResizeAndRotateProducer.this.mResizingEnabled);
            if (!bl && triState == TriState.UNSET) return;
            if (triState != TriState.YES) {
                this.getConsumer().onNewResult(encodedImage, bl);
                return;
            }
            if (!this.mJobScheduler.updateJob(encodedImage, bl) || !bl && !this.mProducerContext.isIntermediateResultExpected()) {
                return;
            }
            this.mJobScheduler.scheduleJob();
        }

    }

}

