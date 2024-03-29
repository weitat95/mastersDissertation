/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package com.bumptech.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.resource.UnitTransformation;
import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.LifecycleListener;
import com.bumptech.glide.manager.RequestTracker;
import com.bumptech.glide.provider.ChildLoadProvider;
import com.bumptech.glide.provider.LoadProvider;
import com.bumptech.glide.request.GenericRequest;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestCoordinator;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.ThumbnailRequestCoordinator;
import com.bumptech.glide.request.animation.GlideAnimationFactory;
import com.bumptech.glide.request.animation.NoAnimation;
import com.bumptech.glide.request.target.PreloadTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.EmptySignature;
import com.bumptech.glide.util.Util;

public class GenericRequestBuilder<ModelType, DataType, ResourceType, TranscodeType>
implements Cloneable {
    private GlideAnimationFactory<TranscodeType> animationFactory;
    protected final Context context;
    private DiskCacheStrategy diskCacheStrategy;
    private int errorId;
    private Drawable errorPlaceholder;
    private Drawable fallbackDrawable;
    private int fallbackResource;
    protected final Glide glide;
    private boolean isCacheable;
    private boolean isModelSet;
    private boolean isThumbnailBuilt;
    private boolean isTransformationSet;
    protected final Lifecycle lifecycle;
    private ChildLoadProvider<ModelType, DataType, ResourceType, TranscodeType> loadProvider;
    private ModelType model;
    protected final Class<ModelType> modelClass;
    private int overrideHeight;
    private int overrideWidth;
    private Drawable placeholderDrawable;
    private int placeholderId;
    private Priority priority;
    private RequestListener<? super ModelType, TranscodeType> requestListener;
    protected final RequestTracker requestTracker;
    private Key signature;
    private Float sizeMultiplier;
    private Float thumbSizeMultiplier;
    private GenericRequestBuilder<?, ?, ?, TranscodeType> thumbnailRequestBuilder;
    protected final Class<TranscodeType> transcodeClass;
    private Transformation<ResourceType> transformation;

    GenericRequestBuilder(Context context, Class<ModelType> class_, LoadProvider<ModelType, DataType, ResourceType, TranscodeType> loadProvider, Class<TranscodeType> object, Glide glide, RequestTracker requestTracker, Lifecycle lifecycle) {
        Object var8_8 = null;
        this.signature = EmptySignature.obtain();
        this.sizeMultiplier = Float.valueOf(1.0f);
        this.priority = null;
        this.isCacheable = true;
        this.animationFactory = NoAnimation.getFactory();
        this.overrideHeight = -1;
        this.overrideWidth = -1;
        this.diskCacheStrategy = DiskCacheStrategy.RESULT;
        this.transformation = UnitTransformation.get();
        this.context = context;
        this.modelClass = class_;
        this.transcodeClass = object;
        this.glide = glide;
        this.requestTracker = requestTracker;
        this.lifecycle = lifecycle;
        object = var8_8;
        if (loadProvider != null) {
            object = new ChildLoadProvider<ModelType, DataType, ResourceType, TranscodeType>(loadProvider);
        }
        this.loadProvider = object;
        if (context == null) {
            throw new NullPointerException("Context can't be null");
        }
        if (class_ != null && loadProvider == null) {
            throw new NullPointerException("LoadProvider must not be null");
        }
    }

    GenericRequestBuilder(LoadProvider<ModelType, DataType, ResourceType, TranscodeType> loadProvider, Class<TranscodeType> class_, GenericRequestBuilder<ModelType, ?, ?, ?> genericRequestBuilder) {
        this(genericRequestBuilder.context, genericRequestBuilder.modelClass, loadProvider, class_, genericRequestBuilder.glide, genericRequestBuilder.requestTracker, genericRequestBuilder.lifecycle);
        this.model = genericRequestBuilder.model;
        this.isModelSet = genericRequestBuilder.isModelSet;
        this.signature = genericRequestBuilder.signature;
        this.diskCacheStrategy = genericRequestBuilder.diskCacheStrategy;
        this.isCacheable = genericRequestBuilder.isCacheable;
    }

    private Request buildRequest(Target<TranscodeType> target) {
        if (this.priority == null) {
            this.priority = Priority.NORMAL;
        }
        return this.buildRequestRecursive(target, null);
    }

    private Request buildRequestRecursive(Target<TranscodeType> object, ThumbnailRequestCoordinator thumbnailRequestCoordinator) {
        if (this.thumbnailRequestBuilder != null) {
            if (this.isThumbnailBuilt) {
                throw new IllegalStateException("You cannot use a request as both the main request and a thumbnail, consider using clone() on the request(s) passed to thumbnail()");
            }
            if (this.thumbnailRequestBuilder.animationFactory.equals(NoAnimation.getFactory())) {
                this.thumbnailRequestBuilder.animationFactory = this.animationFactory;
            }
            if (this.thumbnailRequestBuilder.priority == null) {
                this.thumbnailRequestBuilder.priority = this.getThumbnailPriority();
            }
            if (Util.isValidDimensions(this.overrideWidth, this.overrideHeight) && !Util.isValidDimensions(this.thumbnailRequestBuilder.overrideWidth, this.thumbnailRequestBuilder.overrideHeight)) {
                this.thumbnailRequestBuilder.override(this.overrideWidth, this.overrideHeight);
            }
            thumbnailRequestCoordinator = new ThumbnailRequestCoordinator(thumbnailRequestCoordinator);
            Request request = this.obtainRequest((Target<TranscodeType>)object, this.sizeMultiplier.floatValue(), this.priority, thumbnailRequestCoordinator);
            this.isThumbnailBuilt = true;
            object = super.buildRequestRecursive((Target<TranscodeType>)object, thumbnailRequestCoordinator);
            this.isThumbnailBuilt = false;
            thumbnailRequestCoordinator.setRequests(request, (Request)object);
            return thumbnailRequestCoordinator;
        }
        if (this.thumbSizeMultiplier != null) {
            thumbnailRequestCoordinator = new ThumbnailRequestCoordinator(thumbnailRequestCoordinator);
            thumbnailRequestCoordinator.setRequests(this.obtainRequest((Target<TranscodeType>)object, this.sizeMultiplier.floatValue(), this.priority, thumbnailRequestCoordinator), this.obtainRequest((Target<TranscodeType>)object, this.thumbSizeMultiplier.floatValue(), this.getThumbnailPriority(), thumbnailRequestCoordinator));
            return thumbnailRequestCoordinator;
        }
        return this.obtainRequest((Target<TranscodeType>)object, this.sizeMultiplier.floatValue(), this.priority, thumbnailRequestCoordinator);
    }

    private Priority getThumbnailPriority() {
        if (this.priority == Priority.LOW) {
            return Priority.NORMAL;
        }
        if (this.priority == Priority.NORMAL) {
            return Priority.HIGH;
        }
        return Priority.IMMEDIATE;
    }

    private Request obtainRequest(Target<TranscodeType> target, float f, Priority priority, RequestCoordinator requestCoordinator) {
        return GenericRequest.obtain(this.loadProvider, this.model, this.signature, this.context, priority, target, f, this.placeholderDrawable, this.placeholderId, this.errorPlaceholder, this.errorId, this.fallbackDrawable, this.fallbackResource, this.requestListener, requestCoordinator, this.glide.getEngine(), this.transformation, this.transcodeClass, this.isCacheable, this.animationFactory, this.overrideWidth, this.overrideHeight, this.diskCacheStrategy);
    }

    GenericRequestBuilder<ModelType, DataType, ResourceType, TranscodeType> animate(GlideAnimationFactory<TranscodeType> glideAnimationFactory) {
        if (glideAnimationFactory == null) {
            throw new NullPointerException("Animation factory must not be null!");
        }
        this.animationFactory = glideAnimationFactory;
        return this;
    }

    void applyCenterCrop() {
    }

    void applyFitCenter() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public GenericRequestBuilder<ModelType, DataType, ResourceType, TranscodeType> clone() {
        try {
            GenericRequestBuilder genericRequestBuilder = (GenericRequestBuilder)super.clone();
            Object object = this.loadProvider != null ? this.loadProvider.clone() : null;
            genericRequestBuilder.loadProvider = object;
            return genericRequestBuilder;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException);
        }
    }

    public GenericRequestBuilder<ModelType, DataType, ResourceType, TranscodeType> decoder(ResourceDecoder<DataType, ResourceType> resourceDecoder) {
        if (this.loadProvider != null) {
            this.loadProvider.setSourceDecoder(resourceDecoder);
        }
        return this;
    }

    public GenericRequestBuilder<ModelType, DataType, ResourceType, TranscodeType> diskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
        this.diskCacheStrategy = diskCacheStrategy;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Target<TranscodeType> into(ImageView imageView) {
        Util.assertMainThread();
        if (imageView == null) {
            throw new IllegalArgumentException("You must pass in a non null View");
        }
        if (this.isTransformationSet) return this.into(this.glide.buildImageViewTarget(imageView, this.transcodeClass));
        if (imageView.getScaleType() == null) return this.into(this.glide.buildImageViewTarget(imageView, this.transcodeClass));
        switch (2.$SwitchMap$android$widget$ImageView$ScaleType[imageView.getScaleType().ordinal()]) {
            case 1: {
                this.applyCenterCrop();
            }
            default: {
                return this.into(this.glide.buildImageViewTarget(imageView, this.transcodeClass));
            }
            case 2: 
            case 3: 
            case 4: 
        }
        this.applyFitCenter();
        return this.into(this.glide.buildImageViewTarget(imageView, this.transcodeClass));
    }

    public <Y extends Target<TranscodeType>> Y into(Y y) {
        Util.assertMainThread();
        if (y == null) {
            throw new IllegalArgumentException("You must pass in a non null Target");
        }
        if (!this.isModelSet) {
            throw new IllegalArgumentException("You must first set a model (try #load())");
        }
        Request request = y.getRequest();
        if (request != null) {
            request.clear();
            this.requestTracker.removeRequest(request);
            request.recycle();
        }
        request = this.buildRequest(y);
        y.setRequest(request);
        this.lifecycle.addListener(y);
        this.requestTracker.runRequest(request);
        return y;
    }

    public GenericRequestBuilder<ModelType, DataType, ResourceType, TranscodeType> listener(RequestListener<? super ModelType, TranscodeType> requestListener) {
        this.requestListener = requestListener;
        return this;
    }

    public GenericRequestBuilder<ModelType, DataType, ResourceType, TranscodeType> load(ModelType ModelType) {
        this.model = ModelType;
        this.isModelSet = true;
        return this;
    }

    public GenericRequestBuilder<ModelType, DataType, ResourceType, TranscodeType> override(int n, int n2) {
        if (!Util.isValidDimensions(n, n2)) {
            throw new IllegalArgumentException("Width and height must be Target#SIZE_ORIGINAL or > 0");
        }
        this.overrideWidth = n;
        this.overrideHeight = n2;
        return this;
    }

    public Target<TranscodeType> preload() {
        return this.preload(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public Target<TranscodeType> preload(int n, int n2) {
        return this.into(PreloadTarget.obtain(n, n2));
    }

    public GenericRequestBuilder<ModelType, DataType, ResourceType, TranscodeType> signature(Key key) {
        if (key == null) {
            throw new NullPointerException("Signature must not be null");
        }
        this.signature = key;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public GenericRequestBuilder<ModelType, DataType, ResourceType, TranscodeType> skipMemoryCache(boolean bl) {
        bl = !bl;
        this.isCacheable = bl;
        return this;
    }

    public GenericRequestBuilder<ModelType, DataType, ResourceType, TranscodeType> sourceEncoder(Encoder<DataType> encoder) {
        if (this.loadProvider != null) {
            this.loadProvider.setSourceEncoder(encoder);
        }
        return this;
    }

    public GenericRequestBuilder<ModelType, DataType, ResourceType, TranscodeType> transform(Transformation<ResourceType> ... arrtransformation) {
        this.isTransformationSet = true;
        if (arrtransformation.length == 1) {
            this.transformation = arrtransformation[0];
            return this;
        }
        this.transformation = new MultiTransformation<ResourceType>(arrtransformation);
        return this;
    }

}

