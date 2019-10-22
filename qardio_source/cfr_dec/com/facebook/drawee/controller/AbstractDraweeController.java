/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Animatable
 *  android.graphics.drawable.Drawable
 *  android.view.MotionEvent
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.NotThreadSafe
 */
package com.facebook.drawee.controller;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.components.DeferredReleaser;
import com.facebook.drawee.components.DraweeEventTracker;
import com.facebook.drawee.components.RetryManager;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.controller.ControllerViewportVisibilityListener;
import com.facebook.drawee.controller.ForwardingControllerListener;
import com.facebook.drawee.gestures.GestureDetector;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import com.facebook.drawee.interfaces.SettableDraweeHierarchy;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public abstract class AbstractDraweeController<T, INFO>
implements DeferredReleaser.Releasable,
GestureDetector.ClickListener,
DraweeController {
    private static final Class<?> TAG = AbstractDraweeController.class;
    private Object mCallerContext;
    @Nullable
    private String mContentDescription;
    @Nullable
    private ControllerListener<INFO> mControllerListener;
    @Nullable
    private Drawable mControllerOverlay;
    @Nullable
    private ControllerViewportVisibilityListener mControllerViewportVisibilityListener;
    @Nullable
    private DataSource<T> mDataSource;
    private final DeferredReleaser mDeferredReleaser;
    @Nullable
    private Drawable mDrawable;
    private final DraweeEventTracker mEventTracker = DraweeEventTracker.newInstance();
    @Nullable
    private T mFetchedImage;
    @Nullable
    private GestureDetector mGestureDetector;
    private boolean mHasFetchFailed;
    private String mId;
    private boolean mIsAttached;
    private boolean mIsRequestSubmitted;
    private boolean mIsVisibleInViewportHint;
    private boolean mRetainImageOnFailure;
    @Nullable
    private RetryManager mRetryManager;
    @Nullable
    private SettableDraweeHierarchy mSettableDraweeHierarchy;
    private final Executor mUiThreadImmediateExecutor;

    public AbstractDraweeController(DeferredReleaser deferredReleaser, Executor executor, String string2, Object object) {
        this.mDeferredReleaser = deferredReleaser;
        this.mUiThreadImmediateExecutor = executor;
        this.init(string2, object, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void init(String string2, Object object, boolean bl) {
        this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_INIT_CONTROLLER);
        if (!bl && this.mDeferredReleaser != null) {
            this.mDeferredReleaser.cancelDeferredRelease(this);
        }
        this.mIsAttached = false;
        this.mIsVisibleInViewportHint = false;
        this.releaseFetch();
        this.mRetainImageOnFailure = false;
        if (this.mRetryManager != null) {
            this.mRetryManager.init();
        }
        if (this.mGestureDetector != null) {
            this.mGestureDetector.init();
            this.mGestureDetector.setClickListener(this);
        }
        if (this.mControllerListener instanceof InternalForwardingListener) {
            ((InternalForwardingListener)this.mControllerListener).clearListeners();
        } else {
            this.mControllerListener = null;
        }
        this.mControllerViewportVisibilityListener = null;
        if (this.mSettableDraweeHierarchy != null) {
            this.mSettableDraweeHierarchy.reset();
            this.mSettableDraweeHierarchy.setControllerOverlay(null);
            this.mSettableDraweeHierarchy = null;
        }
        this.mControllerOverlay = null;
        if (FLog.isLoggable(2)) {
            FLog.v(TAG, "controller %x %s -> %s: initialize", (Object)System.identityHashCode(this), (Object)this.mId, (Object)string2);
        }
        this.mId = string2;
        this.mCallerContext = object;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isExpectedDataSource(String string2, DataSource<T> dataSource) {
        return dataSource == null && this.mDataSource == null || string2.equals(this.mId) && dataSource == this.mDataSource && this.mIsRequestSubmitted;
    }

    private void logMessageAndFailure(String string2, Throwable throwable) {
        if (FLog.isLoggable(2)) {
            FLog.v(TAG, "controller %x %s: %s: failure: %s", (Object)System.identityHashCode(this), (Object)this.mId, (Object)string2, (Object)throwable);
        }
    }

    private void logMessageAndImage(String string2, T t) {
        if (FLog.isLoggable(2)) {
            FLog.v(TAG, "controller %x %s: %s: image: %s %x", System.identityHashCode(this), this.mId, string2, this.getImageClass(t), this.getImageHash(t));
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void onFailureInternal(String object, DataSource<T> object2, Throwable throwable, boolean bl) {
        void var3_6;
        void var1_3;
        DraweeEventTracker draweeEventTracker;
        void var4_7;
        if (!this.isExpectedDataSource((String)object, (DataSource<T>)((Object)draweeEventTracker))) {
            this.logMessageAndFailure("ignore_old_datasource @ onFailure", (Throwable)var3_6);
            draweeEventTracker.close();
            return;
        }
        draweeEventTracker = this.mEventTracker;
        if (var4_7 != false) {
            DraweeEventTracker.Event event = DraweeEventTracker.Event.ON_DATASOURCE_FAILURE;
        } else {
            DraweeEventTracker.Event event = DraweeEventTracker.Event.ON_DATASOURCE_FAILURE_INT;
        }
        draweeEventTracker.recordEvent((DraweeEventTracker.Event)var1_3);
        if (var4_7 == false) {
            this.logMessageAndFailure("intermediate_failed @ onFailure", (Throwable)var3_6);
            this.getControllerListener().onIntermediateImageFailed(this.mId, (Throwable)var3_6);
            return;
        }
        this.logMessageAndFailure("final_failed @ onFailure", (Throwable)var3_6);
        this.mDataSource = null;
        this.mHasFetchFailed = true;
        if (this.mRetainImageOnFailure && this.mDrawable != null) {
            this.mSettableDraweeHierarchy.setImage(this.mDrawable, 1.0f, true);
        } else if (this.shouldRetryOnTap()) {
            this.mSettableDraweeHierarchy.setRetry((Throwable)var3_6);
        } else {
            this.mSettableDraweeHierarchy.setFailure((Throwable)var3_6);
        }
        this.getControllerListener().onFailure(this.mId, (Throwable)var3_6);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void onNewResultInternal(String string2, DataSource<T> dataSource, @Nullable T t, float f, boolean bl, boolean bl2) {
        DraweeEventTracker draweeEventTracker;
        T t2;
        void var3_5;
        DraweeEventTracker.Event event;
        block9: {
            void var4_6;
            void var6_8;
            block8: {
                void var5_7;
                if (!this.isExpectedDataSource(string2, dataSource)) {
                    this.logMessageAndImage("ignore_old_datasource @ onNewResult", var3_5);
                    this.releaseImage(var3_5);
                    dataSource.close();
                    return;
                }
                draweeEventTracker = this.mEventTracker;
                event = var5_7 != false ? DraweeEventTracker.Event.ON_DATASOURCE_RESULT : DraweeEventTracker.Event.ON_DATASOURCE_RESULT_INT;
                draweeEventTracker.recordEvent(event);
                event = this.createDrawable(var3_5);
                t2 = this.mFetchedImage;
                draweeEventTracker = this.mDrawable;
                this.mFetchedImage = var3_5;
                this.mDrawable = event;
                if (var5_7 == false) break block8;
                try {
                    this.logMessageAndImage("set_final_result @ onNewResult", var3_5);
                    this.mDataSource = null;
                    this.mSettableDraweeHierarchy.setImage((Drawable)event, 1.0f, (boolean)var6_8);
                    this.getControllerListener().onFinalImageSet(string2, this.getImageInfo(var3_5), this.getAnimatable());
                    break block9;
                }
                catch (Throwable throwable) {
                    if (draweeEventTracker != null && draweeEventTracker != event) {
                        this.releaseDrawable((Drawable)draweeEventTracker);
                    }
                    if (t2 == null) throw throwable;
                    if (t2 == var3_5) throw throwable;
                    this.logMessageAndImage("release_previous_result @ onNewResult", t2);
                    this.releaseImage(t2);
                    throw throwable;
                }
                catch (Exception exception) {
                    this.logMessageAndImage("drawable_failed @ onNewResult", var3_5);
                    this.releaseImage(var3_5);
                    this.onFailureInternal(string2, dataSource, exception, (boolean)var5_7);
                    return;
                }
            }
            this.logMessageAndImage("set_intermediate_result @ onNewResult", var3_5);
            this.mSettableDraweeHierarchy.setImage((Drawable)event, (float)var4_6, (boolean)var6_8);
            this.getControllerListener().onIntermediateImageSet(string2, this.getImageInfo(var3_5));
        }
        if (draweeEventTracker != null && draweeEventTracker != event) {
            this.releaseDrawable((Drawable)draweeEventTracker);
        }
        if (t2 == null) return;
        if (t2 == var3_5) return;
        this.logMessageAndImage("release_previous_result @ onNewResult", t2);
        this.releaseImage(t2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onProgressUpdateInternal(String string2, DataSource<T> dataSource, float f, boolean bl) {
        if (!this.isExpectedDataSource(string2, dataSource)) {
            this.logMessageAndFailure("ignore_old_datasource @ onProgress", null);
            dataSource.close();
            return;
        } else {
            if (bl) return;
            {
                this.mSettableDraweeHierarchy.setProgress(f, false);
                return;
            }
        }
    }

    private void releaseFetch() {
        boolean bl = this.mIsRequestSubmitted;
        this.mIsRequestSubmitted = false;
        this.mHasFetchFailed = false;
        if (this.mDataSource != null) {
            this.mDataSource.close();
            this.mDataSource = null;
        }
        if (this.mDrawable != null) {
            this.releaseDrawable(this.mDrawable);
        }
        if (this.mContentDescription != null) {
            this.mContentDescription = null;
        }
        this.mDrawable = null;
        if (this.mFetchedImage != null) {
            this.logMessageAndImage("release", this.mFetchedImage);
            this.releaseImage(this.mFetchedImage);
            this.mFetchedImage = null;
        }
        if (bl) {
            this.getControllerListener().onRelease(this.mId);
        }
    }

    private boolean shouldRetryOnTap() {
        return this.mHasFetchFailed && this.mRetryManager != null && this.mRetryManager.shouldRetryOnTap();
    }

    public void addControllerListener(ControllerListener<? super INFO> controllerListener) {
        Preconditions.checkNotNull(controllerListener);
        if (this.mControllerListener instanceof InternalForwardingListener) {
            ((InternalForwardingListener)this.mControllerListener).addListener(controllerListener);
            return;
        }
        if (this.mControllerListener != null) {
            this.mControllerListener = InternalForwardingListener.createInternal(this.mControllerListener, controllerListener);
            return;
        }
        this.mControllerListener = controllerListener;
    }

    protected abstract Drawable createDrawable(T var1);

    @Nullable
    public Animatable getAnimatable() {
        if (this.mDrawable instanceof Animatable) {
            return (Animatable)this.mDrawable;
        }
        return null;
    }

    protected T getCachedImage() {
        return null;
    }

    protected ControllerListener<INFO> getControllerListener() {
        if (this.mControllerListener == null) {
            return BaseControllerListener.getNoOpListener();
        }
        return this.mControllerListener;
    }

    protected abstract DataSource<T> getDataSource();

    @Nullable
    protected GestureDetector getGestureDetector() {
        return this.mGestureDetector;
    }

    @Nullable
    @Override
    public DraweeHierarchy getHierarchy() {
        return this.mSettableDraweeHierarchy;
    }

    protected String getImageClass(@Nullable T t) {
        if (t != null) {
            return t.getClass().getSimpleName();
        }
        return "<null>";
    }

    protected int getImageHash(@Nullable T t) {
        return System.identityHashCode(t);
    }

    @Nullable
    protected abstract INFO getImageInfo(T var1);

    @Nullable
    protected RetryManager getRetryManager() {
        return this.mRetryManager;
    }

    protected void initialize(String string2, Object object) {
        this.init(string2, object, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onAttach() {
        if (FLog.isLoggable(2)) {
            Class<?> class_ = TAG;
            int n = System.identityHashCode(this);
            String string2 = this.mId;
            String string3 = this.mIsRequestSubmitted ? "request already submitted" : "request needs submit";
            FLog.v(class_, "controller %x %s: onAttach: %s", (Object)n, (Object)string2, (Object)string3);
        }
        this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_ATTACH_CONTROLLER);
        Preconditions.checkNotNull(this.mSettableDraweeHierarchy);
        this.mDeferredReleaser.cancelDeferredRelease(this);
        this.mIsAttached = true;
        if (!this.mIsRequestSubmitted) {
            this.submitRequest();
        }
    }

    @Override
    public boolean onClick() {
        if (FLog.isLoggable(2)) {
            FLog.v(TAG, "controller %x %s: onClick", (Object)System.identityHashCode(this), (Object)this.mId);
        }
        if (this.shouldRetryOnTap()) {
            this.mRetryManager.notifyTapToRetry();
            this.mSettableDraweeHierarchy.reset();
            this.submitRequest();
            return true;
        }
        return false;
    }

    @Override
    public void onDetach() {
        if (FLog.isLoggable(2)) {
            FLog.v(TAG, "controller %x %s: onDetach", (Object)System.identityHashCode(this), (Object)this.mId);
        }
        this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_DETACH_CONTROLLER);
        this.mIsAttached = false;
        this.mDeferredReleaser.scheduleDeferredRelease(this);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (FLog.isLoggable(2)) {
            FLog.v(TAG, "controller %x %s: onTouchEvent %s", (Object)System.identityHashCode(this), (Object)this.mId, (Object)motionEvent);
        }
        if (this.mGestureDetector == null || !this.mGestureDetector.isCapturingGesture() && !this.shouldHandleGesture()) {
            return false;
        }
        this.mGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    public void release() {
        this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_RELEASE_CONTROLLER);
        if (this.mRetryManager != null) {
            this.mRetryManager.reset();
        }
        if (this.mGestureDetector != null) {
            this.mGestureDetector.reset();
        }
        if (this.mSettableDraweeHierarchy != null) {
            this.mSettableDraweeHierarchy.reset();
        }
        this.releaseFetch();
    }

    protected abstract void releaseDrawable(@Nullable Drawable var1);

    protected abstract void releaseImage(@Nullable T var1);

    public void setContentDescription(@Nullable String string2) {
        this.mContentDescription = string2;
    }

    public void setControllerViewportVisibilityListener(@Nullable ControllerViewportVisibilityListener controllerViewportVisibilityListener) {
        this.mControllerViewportVisibilityListener = controllerViewportVisibilityListener;
    }

    protected void setGestureDetector(@Nullable GestureDetector gestureDetector) {
        this.mGestureDetector = gestureDetector;
        if (this.mGestureDetector != null) {
            this.mGestureDetector.setClickListener(this);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setHierarchy(@Nullable DraweeHierarchy draweeHierarchy) {
        if (FLog.isLoggable(2)) {
            FLog.v(TAG, "controller %x %s: setHierarchy: %s", (Object)System.identityHashCode(this), (Object)this.mId, (Object)draweeHierarchy);
        }
        DraweeEventTracker draweeEventTracker = this.mEventTracker;
        DraweeEventTracker.Event event = draweeHierarchy != null ? DraweeEventTracker.Event.ON_SET_HIERARCHY : DraweeEventTracker.Event.ON_CLEAR_HIERARCHY;
        draweeEventTracker.recordEvent(event);
        if (this.mIsRequestSubmitted) {
            this.mDeferredReleaser.cancelDeferredRelease(this);
            this.release();
        }
        if (this.mSettableDraweeHierarchy != null) {
            this.mSettableDraweeHierarchy.setControllerOverlay(null);
            this.mSettableDraweeHierarchy = null;
        }
        if (draweeHierarchy != null) {
            Preconditions.checkArgument(draweeHierarchy instanceof SettableDraweeHierarchy);
            this.mSettableDraweeHierarchy = (SettableDraweeHierarchy)draweeHierarchy;
            this.mSettableDraweeHierarchy.setControllerOverlay(this.mControllerOverlay);
        }
    }

    protected void setRetainImageOnFailure(boolean bl) {
        this.mRetainImageOnFailure = bl;
    }

    protected void setRetryManager(@Nullable RetryManager retryManager) {
        this.mRetryManager = retryManager;
    }

    protected boolean shouldHandleGesture() {
        return this.shouldRetryOnTap();
    }

    protected void submitRequest() {
        Object object = this.getCachedImage();
        if (object != null) {
            this.mDataSource = null;
            this.mIsRequestSubmitted = true;
            this.mHasFetchFailed = false;
            this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_SUBMIT_CACHE_HIT);
            this.getControllerListener().onSubmit(this.mId, this.mCallerContext);
            this.onNewResultInternal(this.mId, this.mDataSource, object, 1.0f, true, true);
            return;
        }
        this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_DATASOURCE_SUBMIT);
        this.getControllerListener().onSubmit(this.mId, this.mCallerContext);
        this.mSettableDraweeHierarchy.setProgress(0.0f, true);
        this.mIsRequestSubmitted = true;
        this.mHasFetchFailed = false;
        this.mDataSource = this.getDataSource();
        if (FLog.isLoggable(2)) {
            FLog.v(TAG, "controller %x %s: submitRequest: dataSource: %x", (Object)System.identityHashCode(this), (Object)this.mId, (Object)System.identityHashCode(this.mDataSource));
        }
        object = new BaseDataSubscriber<T>(this.mDataSource.hasResult()){
            final /* synthetic */ boolean val$wasImmediate;
            {
                this.val$wasImmediate = bl;
            }

            @Override
            public void onFailureImpl(DataSource<T> dataSource) {
                AbstractDraweeController.this.onFailureInternal(mId, dataSource, dataSource.getFailureCause(), true);
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onNewResultImpl(DataSource<T> dataSource) {
                boolean bl = dataSource.isFinished();
                float f = dataSource.getProgress();
                T t = dataSource.getResult();
                if (t != null) {
                    AbstractDraweeController.this.onNewResultInternal(mId, dataSource, t, f, bl, this.val$wasImmediate);
                    return;
                } else {
                    if (!bl) return;
                    {
                        AbstractDraweeController.this.onFailureInternal(mId, dataSource, new NullPointerException(), true);
                        return;
                    }
                }
            }

            @Override
            public void onProgressUpdate(DataSource<T> dataSource) {
                boolean bl = dataSource.isFinished();
                float f = dataSource.getProgress();
                AbstractDraweeController.this.onProgressUpdateInternal(mId, dataSource, f, bl);
            }
        };
        this.mDataSource.subscribe((DataSubscriber<T>)object, this.mUiThreadImmediateExecutor);
    }

    public String toString() {
        return Objects.toStringHelper(this).add("isAttached", this.mIsAttached).add("isRequestSubmitted", this.mIsRequestSubmitted).add("hasFetchFailed", this.mHasFetchFailed).add("fetchedImage", this.getImageHash(this.mFetchedImage)).add("events", this.mEventTracker.toString()).toString();
    }

    private static class InternalForwardingListener<INFO>
    extends ForwardingControllerListener<INFO> {
        private InternalForwardingListener() {
        }

        public static <INFO> InternalForwardingListener<INFO> createInternal(ControllerListener<? super INFO> controllerListener, ControllerListener<? super INFO> controllerListener2) {
            InternalForwardingListener<Object> internalForwardingListener = new InternalForwardingListener<Object>();
            internalForwardingListener.addListener(controllerListener);
            internalForwardingListener.addListener(controllerListener2);
            return internalForwardingListener;
        }
    }

}

