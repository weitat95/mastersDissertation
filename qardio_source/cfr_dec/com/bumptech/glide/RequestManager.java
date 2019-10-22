/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.ParcelFileDescriptor
 *  com.bumptech.glide.RequestManager$GenericModelRequest.com.bumptech.glide.RequestManager
 *  com.bumptech.glide.RequestManager$GenericModelRequest.com.bumptech.glide.RequestManager$GenericModelRequest
 *  com.bumptech.glide.RequestManager$GenericModelRequest.com.bumptech.glide.RequestManager$GenericModelRequest$GenericTypeRequest
 */
package com.bumptech.glide;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.GenericTranscodeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.manager.ConnectivityMonitor;
import com.bumptech.glide.manager.ConnectivityMonitorFactory;
import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.LifecycleListener;
import com.bumptech.glide.manager.RequestManagerTreeNode;
import com.bumptech.glide.manager.RequestTracker;
import com.bumptech.glide.signature.ApplicationVersionSignature;
import com.bumptech.glide.util.Util;
import java.io.InputStream;

public class RequestManager
implements LifecycleListener {
    private final Context context;
    private final Glide glide;
    private final Lifecycle lifecycle;
    private DefaultOptions options;
    private final OptionsApplier optionsApplier;
    private final RequestTracker requestTracker;
    private final RequestManagerTreeNode treeNode;

    public RequestManager(Context context, Lifecycle lifecycle, RequestManagerTreeNode requestManagerTreeNode) {
        this(context, lifecycle, requestManagerTreeNode, new RequestTracker(), new ConnectivityMonitorFactory());
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    RequestManager(Context object, Lifecycle lifecycle, RequestManagerTreeNode requestManagerTreeNode, RequestTracker requestTracker, ConnectivityMonitorFactory connectivityMonitorFactory) {
        void var3_4;
        void var4_5;
        void var5_6;
        void var2_3;
        this.context = object.getApplicationContext();
        this.lifecycle = var2_3;
        this.treeNode = var3_4;
        this.requestTracker = var4_5;
        this.glide = Glide.get(object);
        this.optionsApplier = new OptionsApplier();
        ConnectivityMonitor connectivityMonitor = var5_6.build((Context)object, new RequestManagerConnectivityListener((RequestTracker)var4_5));
        if (Util.isOnBackgroundThread()) {
            new Handler(Looper.getMainLooper()).post(new Runnable((Lifecycle)var2_3){
                final /* synthetic */ Lifecycle val$lifecycle;
                {
                    this.val$lifecycle = lifecycle;
                }

                @Override
                public void run() {
                    this.val$lifecycle.addListener(RequestManager.this);
                }
            });
        } else {
            var2_3.addListener(this);
        }
        var2_3.addListener(connectivityMonitor);
    }

    private static <T> Class<T> getSafeClass(T t) {
        if (t != null) {
            return t.getClass();
        }
        return null;
    }

    private <T> DrawableTypeRequest<T> loadGeneric(Class<T> class_) {
        ModelLoader<T, InputStream> modelLoader = Glide.buildStreamModelLoader(class_, this.context);
        ModelLoader<T, ParcelFileDescriptor> modelLoader2 = Glide.buildFileDescriptorModelLoader(class_, this.context);
        if (class_ != null && modelLoader == null && modelLoader2 == null) {
            throw new IllegalArgumentException("Unknown type " + class_ + ". You must provide a Model of a type for" + " which there is a registered ModelLoader, if you are using a custom model, you must first call" + " Glide#register with a ModelLoaderFactory for your custom model class");
        }
        return this.optionsApplier.apply(new DrawableTypeRequest<T>(class_, modelLoader, modelLoader2, this.context, this.glide, this.requestTracker, this.lifecycle, this.optionsApplier));
    }

    public DrawableTypeRequest<Integer> fromResource() {
        return (DrawableTypeRequest)this.loadGeneric(Integer.class).signature(ApplicationVersionSignature.obtain(this.context));
    }

    public DrawableTypeRequest<String> fromString() {
        return this.loadGeneric(String.class);
    }

    public DrawableTypeRequest<Integer> load(Integer n) {
        return (DrawableTypeRequest)this.fromResource().load((Object)n);
    }

    public DrawableTypeRequest<String> load(String string2) {
        return (DrawableTypeRequest)this.fromString().load((Object)string2);
    }

    @Override
    public void onDestroy() {
        this.requestTracker.clearRequests();
    }

    public void onLowMemory() {
        this.glide.clearMemory();
    }

    @Override
    public void onStart() {
        this.resumeRequests();
    }

    @Override
    public void onStop() {
        this.pauseRequests();
    }

    public void onTrimMemory(int n) {
        this.glide.trimMemory(n);
    }

    public void pauseRequests() {
        Util.assertMainThread();
        this.requestTracker.pauseRequests();
    }

    public void resumeRequests() {
        Util.assertMainThread();
        this.requestTracker.resumeRequests();
    }

    public <A, T> GenericModelRequest<A, T> using(ModelLoader<A, T> modelLoader, Class<T> class_) {
        return new GenericModelRequest<A, T>(modelLoader, class_);
    }

    public static interface DefaultOptions {
        public <T> void apply(GenericRequestBuilder<T, ?, ?, ?> var1);
    }

    public final class GenericModelRequest<A, T> {
        private final Class<T> dataClass;
        private final ModelLoader<A, T> modelLoader;

        GenericModelRequest(ModelLoader<A, T> modelLoader, Class<T> class_) {
            this.modelLoader = modelLoader;
            this.dataClass = class_;
        }

        public RequestManager.GenericModelRequest.GenericTypeRequest load(A a2) {
            return new GenericTypeRequest(a2);
        }

        public final class GenericTypeRequest {
            private final A model;
            private final Class<A> modelClass;
            private final boolean providedModel;

            GenericTypeRequest(A a2) {
                this.providedModel = true;
                this.model = a2;
                this.modelClass = RequestManager.getSafeClass(a2);
            }

            public <Z> GenericTranscodeRequest<A, T, Z> as(Class<Z> object) {
                object = RequestManager.this.optionsApplier.apply(new GenericTranscodeRequest(RequestManager.this.context, RequestManager.this.glide, this.modelClass, GenericModelRequest.this.modelLoader, GenericModelRequest.this.dataClass, (Class<Z>)object, RequestManager.this.requestTracker, RequestManager.this.lifecycle, RequestManager.this.optionsApplier));
                if (this.providedModel) {
                    ((GenericRequestBuilder)object).load(this.model);
                }
                return object;
            }
        }

    }

    class OptionsApplier {
        OptionsApplier() {
        }

        public <A, X extends GenericRequestBuilder<A, ?, ?, ?>> X apply(X x) {
            if (RequestManager.this.options != null) {
                RequestManager.this.options.apply(x);
            }
            return x;
        }
    }

    private static class RequestManagerConnectivityListener
    implements ConnectivityMonitor.ConnectivityListener {
        private final RequestTracker requestTracker;

        public RequestManagerConnectivityListener(RequestTracker requestTracker) {
            this.requestTracker = requestTracker;
        }

        @Override
        public void onConnectivityChanged(boolean bl) {
            if (bl) {
                this.requestTracker.restartRequests();
            }
        }
    }

}

