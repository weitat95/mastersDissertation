/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 */
package com.getqardio.android.shopify;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.getqardio.android.BuildConfig;
import com.getqardio.android.shopify.ShopifyAnalytics;
import com.getqardio.android.shopify.ShopifyKeyManager;
import com.getqardio.android.shopify.ShopifySDK$$Lambda$1;
import com.getqardio.android.shopify.ShopifySDK$$Lambda$2;
import com.getqardio.android.shopify.domain.interactor.RealShopSettingInteractor;
import com.getqardio.android.shopify.domain.model.ShopSettings;
import com.getqardio.android.shopify.util.RxRetryHandler;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.HttpCachePolicy;
import com.shopify.buy3.pay.CardNetworkType;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.reactivestreams.Publisher;
import timber.log.Timber;

public class ShopifySDK {
    private static GraphClient graphClient;
    private static volatile ShopifySDK instance;
    private final Context context;
    private final FrescoMemoryTrimmableRegistry frescoMemoryTrimmableRegistry;
    private MutableLiveData<ShopSettings> shopSettings = new MutableLiveData();

    private ShopifySDK(Context context) {
        this.frescoMemoryTrimmableRegistry = new FrescoMemoryTrimmableRegistry();
        this.context = context.getApplicationContext();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ShopifySDK getInstance(Context context) {
        if (instance == null) {
            synchronized (ShopifySDK.class) {
                if (instance == null) {
                    instance = new ShopifySDK(context);
                }
            }
        }
        return instance;
    }

    public static GraphClient graphClient() {
        return graphClient;
    }

    public void fetchShopSettings() {
        this.shopSettings.setValue(new ShopSettings("test", Collections.<CardNetworkType>emptySet(), this.context.getResources().getConfiguration().locale.getCountry()));
        new RealShopSettingInteractor().execute().observeOn(AndroidSchedulers.mainThread()).retryWhen(RxRetryHandler.delay(3L, TimeUnit.SECONDS).maxRetries(5).build()).onErrorReturn(ShopifySDK$$Lambda$1.lambdaFactory$(this)).subscribe(ShopifySDK$$Lambda$2.lambdaFactory$(this));
    }

    public void initFresco() {
        Object object;
        ImagePipelineConfig.Builder builder = ImagePipelineConfig.newBuilder(this.context);
        Object object2 = object = this.context.getExternalCacheDir();
        if (object == null) {
            object2 = this.context.getCacheDir();
        }
        object = DiskCacheConfig.newBuilder(this.context);
        ((DiskCacheConfig.Builder)object).setBaseDirectoryName("image_cache");
        ((DiskCacheConfig.Builder)object).setBaseDirectoryPath((File)object2);
        ((DiskCacheConfig.Builder)object).setMaxCacheSize(104857600L);
        ((DiskCacheConfig.Builder)object).setVersion(1);
        builder.setMainDiskCacheConfig(((DiskCacheConfig.Builder)object).build());
        object2 = ImagePipelineConfig.newBuilder(this.context);
        builder.setMemoryTrimmableRegistry(this.frescoMemoryTrimmableRegistry);
        Fresco.initialize(this.context, ((ImagePipelineConfig.Builder)object2).build());
    }

    public void initializeGraphClient() {
        String string2 = ShopifyKeyManager.getInstance().resolveShopifyDomain();
        String string3 = ShopifyKeyManager.getInstance().resolveShopifyAPIKey();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.OKHTTP_LOG_LEVEL)).build();
        graphClient = GraphClient.builder(this.context).shopDomain(string2).accessToken(string3).httpClient(okHttpClient).httpCache(this.context.getCacheDir(), 0xA00000L).defaultHttpCachePolicy(HttpCachePolicy.CACHE_FIRST.expireAfter(20L, TimeUnit.MINUTES)).build();
    }

    /* synthetic */ ShopSettings lambda$fetchShopSettings$0(Throwable throwable) throws Exception {
        Timber.e(throwable, "Failed to fetch shop settings", new Object[0]);
        return (ShopSettings)this.shopSettings.getValue();
    }

    /* synthetic */ void lambda$fetchShopSettings$1(ShopSettings shopSettings) throws Exception {
        try {
            this.shopSettings.postValue(shopSettings);
            return;
        }
        catch (Exception exception) {
            ShopifyAnalytics.getInstance().trackFetchShopSettingsCrash(exception);
            return;
        }
    }

    public MutableLiveData<ShopSettings> shopSettings() {
        return this.shopSettings;
    }

    private static class FrescoMemoryTrimmableRegistry
    implements MemoryTrimmableRegistry {
        final List<MemoryTrimmable> trimmables = new LinkedList<MemoryTrimmable>();

        private FrescoMemoryTrimmableRegistry() {
        }

        @Override
        public void registerMemoryTrimmable(MemoryTrimmable memoryTrimmable) {
            this.trimmables.add(memoryTrimmable);
        }

        void trim(MemoryTrimType memoryTrimType) {
            synchronized (this) {
                Iterator<MemoryTrimmable> iterator = this.trimmables.iterator();
                while (iterator.hasNext()) {
                    iterator.next().trim(memoryTrimType);
                }
                return;
            }
        }

        public void unregisterMemoryTrimmable(MemoryTrimmable memoryTrimmable) {
            this.trimmables.remove(memoryTrimmable);
        }
    }

}

