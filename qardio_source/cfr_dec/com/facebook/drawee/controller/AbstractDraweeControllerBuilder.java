/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Animatable
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.controller;

import android.content.Context;
import android.graphics.drawable.Animatable;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSources;
import com.facebook.datasource.FirstAvailableDataSourceSupplier;
import com.facebook.datasource.IncreasingQualityDataSourceSupplier;
import com.facebook.drawee.components.RetryManager;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.controller.ControllerViewportVisibilityListener;
import com.facebook.drawee.gestures.GestureDetector;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.Nullable;

public abstract class AbstractDraweeControllerBuilder<BUILDER extends AbstractDraweeControllerBuilder<BUILDER, REQUEST, IMAGE, INFO>, REQUEST, IMAGE, INFO>
implements SimpleDraweeControllerBuilder {
    private static final NullPointerException NO_REQUEST_EXCEPTION;
    private static final ControllerListener<Object> sAutoPlayAnimationsListener;
    private static final AtomicLong sIdCounter;
    private boolean mAutoPlayAnimations;
    private final Set<ControllerListener> mBoundControllerListeners;
    @Nullable
    private Object mCallerContext;
    private String mContentDescription;
    private final Context mContext;
    @Nullable
    private ControllerListener<? super INFO> mControllerListener;
    @Nullable
    private ControllerViewportVisibilityListener mControllerViewportVisibilityListener;
    @Nullable
    private Supplier<DataSource<IMAGE>> mDataSourceSupplier;
    @Nullable
    private REQUEST mImageRequest;
    @Nullable
    private REQUEST mLowResImageRequest;
    @Nullable
    private REQUEST[] mMultiImageRequests;
    @Nullable
    private DraweeController mOldController;
    private boolean mRetainImageOnFailure;
    private boolean mTapToRetryEnabled;
    private boolean mTryCacheOnlyFirst;

    static {
        sAutoPlayAnimationsListener = new BaseControllerListener<Object>(){

            @Override
            public void onFinalImageSet(String string2, @Nullable Object object, @Nullable Animatable animatable) {
                if (animatable != null) {
                    animatable.start();
                }
            }
        };
        NO_REQUEST_EXCEPTION = new NullPointerException("No image request was specified!");
        sIdCounter = new AtomicLong();
    }

    protected AbstractDraweeControllerBuilder(Context context, Set<ControllerListener> set) {
        this.mContext = context;
        this.mBoundControllerListeners = set;
        this.init();
    }

    protected static String generateUniqueControllerId() {
        return String.valueOf(sIdCounter.getAndIncrement());
    }

    private void init() {
        this.mCallerContext = null;
        this.mImageRequest = null;
        this.mLowResImageRequest = null;
        this.mMultiImageRequests = null;
        this.mTryCacheOnlyFirst = true;
        this.mControllerListener = null;
        this.mControllerViewportVisibilityListener = null;
        this.mTapToRetryEnabled = false;
        this.mAutoPlayAnimations = false;
        this.mOldController = null;
        this.mContentDescription = null;
    }

    @Override
    public AbstractDraweeController build() {
        this.validate();
        if (this.mImageRequest == null && this.mMultiImageRequests == null && this.mLowResImageRequest != null) {
            this.mImageRequest = this.mLowResImageRequest;
            this.mLowResImageRequest = null;
        }
        return this.buildController();
    }

    protected AbstractDraweeController buildController() {
        AbstractDraweeController abstractDraweeController = this.obtainController();
        abstractDraweeController.setRetainImageOnFailure(this.getRetainImageOnFailure());
        abstractDraweeController.setContentDescription(this.getContentDescription());
        abstractDraweeController.setControllerViewportVisibilityListener(this.getControllerViewportVisibilityListener());
        this.maybeBuildAndSetRetryManager(abstractDraweeController);
        this.maybeAttachListeners(abstractDraweeController);
        return abstractDraweeController;
    }

    @Nullable
    public Object getCallerContext() {
        return this.mCallerContext;
    }

    @Nullable
    public String getContentDescription() {
        return this.mContentDescription;
    }

    @Nullable
    public ControllerViewportVisibilityListener getControllerViewportVisibilityListener() {
        return this.mControllerViewportVisibilityListener;
    }

    protected abstract DataSource<IMAGE> getDataSourceForRequest(REQUEST var1, Object var2, CacheLevel var3);

    protected Supplier<DataSource<IMAGE>> getDataSourceSupplierForRequest(REQUEST REQUEST) {
        return this.getDataSourceSupplierForRequest(REQUEST, CacheLevel.FULL_FETCH);
    }

    protected Supplier<DataSource<IMAGE>> getDataSourceSupplierForRequest(final REQUEST REQUEST, CacheLevel cacheLevel) {
        return new Supplier<DataSource<IMAGE>>(this.getCallerContext(), cacheLevel){
            final /* synthetic */ CacheLevel val$cacheLevel;
            final /* synthetic */ Object val$callerContext;
            {
                this.val$callerContext = object2;
                this.val$cacheLevel = cacheLevel;
            }

            @Override
            public DataSource<IMAGE> get() {
                return AbstractDraweeControllerBuilder.this.getDataSourceForRequest(REQUEST, this.val$callerContext, this.val$cacheLevel);
            }

            public String toString() {
                return Objects.toStringHelper(this).add("request", REQUEST.toString()).toString();
            }
        };
    }

    protected Supplier<DataSource<IMAGE>> getFirstAvailableDataSourceSupplier(REQUEST[] arrREQUEST, boolean bl) {
        int n;
        ArrayList<Supplier<DataSource<T>>> arrayList = new ArrayList<Supplier<DataSource<T>>>(arrREQUEST.length * 2);
        if (bl) {
            for (n = 0; n < arrREQUEST.length; ++n) {
                arrayList.add(this.getDataSourceSupplierForRequest(arrREQUEST[n], CacheLevel.BITMAP_MEMORY_CACHE));
            }
        }
        for (n = 0; n < arrREQUEST.length; ++n) {
            arrayList.add(this.getDataSourceSupplierForRequest(arrREQUEST[n]));
        }
        return FirstAvailableDataSourceSupplier.create(arrayList);
    }

    @Nullable
    public REQUEST getImageRequest() {
        return this.mImageRequest;
    }

    @Nullable
    public DraweeController getOldController() {
        return this.mOldController;
    }

    public boolean getRetainImageOnFailure() {
        return this.mRetainImageOnFailure;
    }

    protected abstract BUILDER getThis();

    protected void maybeAttachListeners(AbstractDraweeController abstractDraweeController) {
        if (this.mBoundControllerListeners != null) {
            Iterator<ControllerListener> iterator = this.mBoundControllerListeners.iterator();
            while (iterator.hasNext()) {
                abstractDraweeController.addControllerListener(iterator.next());
            }
        }
        if (this.mControllerListener != null) {
            abstractDraweeController.addControllerListener(this.mControllerListener);
        }
        if (this.mAutoPlayAnimations) {
            abstractDraweeController.addControllerListener(sAutoPlayAnimationsListener);
        }
    }

    protected void maybeBuildAndSetGestureDetector(AbstractDraweeController abstractDraweeController) {
        if (abstractDraweeController.getGestureDetector() == null) {
            abstractDraweeController.setGestureDetector(GestureDetector.newInstance(this.mContext));
        }
    }

    protected void maybeBuildAndSetRetryManager(AbstractDraweeController abstractDraweeController) {
        RetryManager retryManager;
        if (!this.mTapToRetryEnabled) {
            return;
        }
        RetryManager retryManager2 = retryManager = abstractDraweeController.getRetryManager();
        if (retryManager == null) {
            retryManager2 = new RetryManager();
            abstractDraweeController.setRetryManager(retryManager2);
        }
        retryManager2.setTapToRetryEnabled(this.mTapToRetryEnabled);
        this.maybeBuildAndSetGestureDetector(abstractDraweeController);
    }

    protected abstract AbstractDraweeController obtainController();

    /*
     * Enabled aggressive block sorting
     */
    protected Supplier<DataSource<IMAGE>> obtainDataSourceSupplier() {
        if (this.mDataSourceSupplier != null) {
            return this.mDataSourceSupplier;
        }
        Supplier<DataSource<IMAGE>> supplier = null;
        if (this.mImageRequest != null) {
            supplier = this.getDataSourceSupplierForRequest(this.mImageRequest);
        } else if (this.mMultiImageRequests != null) {
            supplier = this.getFirstAvailableDataSourceSupplier(this.mMultiImageRequests, this.mTryCacheOnlyFirst);
        }
        IncreasingQualityDataSourceSupplier<T> increasingQualityDataSourceSupplier = supplier;
        if (supplier != null) {
            increasingQualityDataSourceSupplier = supplier;
            if (this.mLowResImageRequest != null) {
                increasingQualityDataSourceSupplier = new ArrayList<E>(2);
                increasingQualityDataSourceSupplier.add(supplier);
                increasingQualityDataSourceSupplier.add(this.getDataSourceSupplierForRequest(this.mLowResImageRequest));
                increasingQualityDataSourceSupplier = IncreasingQualityDataSourceSupplier.create(increasingQualityDataSourceSupplier);
            }
        }
        supplier = increasingQualityDataSourceSupplier;
        if (increasingQualityDataSourceSupplier != null) return supplier;
        return DataSources.getFailedDataSourceSupplier(NO_REQUEST_EXCEPTION);
    }

    public BUILDER setCallerContext(Object object) {
        this.mCallerContext = object;
        return this.getThis();
    }

    public BUILDER setImageRequest(REQUEST REQUEST) {
        this.mImageRequest = REQUEST;
        return this.getThis();
    }

    public BUILDER setOldController(@Nullable DraweeController draweeController) {
        this.mOldController = draweeController;
        return this.getThis();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void validate() {
        boolean bl;
        block3: {
            block2: {
                boolean bl2 = false;
                bl = this.mMultiImageRequests == null || this.mImageRequest == null;
                Preconditions.checkState(bl, "Cannot specify both ImageRequest and FirstAvailableImageRequests!");
                if (this.mDataSourceSupplier == null) break block2;
                bl = bl2;
                if (this.mMultiImageRequests != null) break block3;
                bl = bl2;
                if (this.mImageRequest != null) break block3;
                bl = bl2;
                if (this.mLowResImageRequest != null) break block3;
            }
            bl = true;
        }
        Preconditions.checkState(bl, "Cannot specify DataSourceSupplier with other ImageRequests! Use one or the other.");
    }

    public static enum CacheLevel {
        FULL_FETCH,
        DISK_CACHE,
        BITMAP_MEMORY_CACHE;

    }

}

