/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.getqardio.android.mvp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.fcm.FCMManager;
import com.getqardio.android.mvp.MvpApplication$$Lambda$1;
import com.getqardio.android.mvp.MvpApplication$$Lambda$2;
import com.getqardio.android.mvp.activity_tracker.ActivityRecognitionManager;
import com.getqardio.android.mvp.activity_tracker.ActivityTrackerAggregator;
import com.getqardio.android.mvp.activity_tracker.ActivityTrackerSetting;
import com.getqardio.android.mvp.activity_tracker.StepCounterManager;
import com.getqardio.android.mvp.common.injection.ApplicationModule;
import com.getqardio.android.mvp.common.injection.DaggerRepositoryComponent;
import com.getqardio.android.mvp.common.injection.LocalDataSourceModule;
import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule;
import com.getqardio.android.mvp.common.injection.RepositoryComponent;
import com.getqardio.android.mvp.common.util.RxEventBus;
import com.getqardio.android.mvp.common.util.RxUtil;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository;
import com.getqardio.android.provider.SyncHelper;
import com.getqardio.android.shopify.ShopifyAnalytics;
import com.getqardio.android.shopify.ShopifyKeyManager;
import com.getqardio.android.shopify.ShopifySDK;
import com.getqardio.android.ui.activity.SplashActivity;
import com.getqardio.android.utils.notifications.AppNotificationAssistant;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.Locale;
import timber.log.Timber;

public class MvpApplication
extends CustomApplication {
    private ActivityTrackerAggregator activityTrackerAggregator;
    RxEventBus eventBus;
    private AppNotificationAssistant mAppNotificationAssistant;
    FCMManager mFCMManager;
    private RepositoryComponent repositoryComponent;
    SyncHelper syncHelper;

    public static MvpApplication get(Context context) {
        return (MvpApplication)context.getApplicationContext();
    }

    public ActivityTrackerAggregator getActivityTracker() {
        return this.activityTrackerAggregator;
    }

    public RepositoryComponent getApplicationComponent() {
        return this.repositoryComponent;
    }

    public FCMManager getFCMManager() {
        return this.mFCMManager;
    }

    public AppNotificationAssistant getNotificationAssistant() {
        if (Build.VERSION.SDK_INT >= 26) {
            return this.mAppNotificationAssistant;
        }
        throw new RuntimeException("version not greater than O cannot use this API");
    }

    public SyncHelper getSyncHelper() {
        return this.syncHelper;
    }

    /* synthetic */ void lambda$onCreate$0(RxEventBus.UnauthorizedResponse unauthorizedResponse) throws Exception {
        Timber.d("Token expired. Opening splash activity", new Object[0]);
        unauthorizedResponse = SplashActivity.createStartIntent((Context)this);
        unauthorizedResponse.addFlags(67141632);
        this.startActivity((Intent)unauthorizedResponse);
    }

    @Override
    public void onCreate() {
        this.repositoryComponent = DaggerRepositoryComponent.builder().remoteDataSourceModule(new RemoteDataSourceModule()).localDataSourceModule(new LocalDataSourceModule()).applicationModule(new ApplicationModule((Context)this)).build();
        this.repositoryComponent.inject(this);
        this.repositoryComponent.getFollowingMeUserRepository();
        super.onCreate();
        this.eventBus.filteredObservable(RxEventBus.UnauthorizedResponse.class).compose(RxUtil.applySchedulers()).subscribe(MvpApplication$$Lambda$1.lambdaFactory$(this), MvpApplication$$Lambda$2.lambdaFactory$());
        ShopifyAnalytics.getInstance().init((Context)this);
        ShopifyKeyManager.getInstance().setCountry(this.getResources().getConfiguration().locale.getCountry());
        ShopifySDK.getInstance((Context)this).initFresco();
        ShopifySDK.getInstance((Context)this).initializeGraphClient();
        ShopifySDK.getInstance((Context)this).fetchShopSettings();
        this.activityTrackerAggregator = new ActivityTrackerAggregator(ActivityTrackerSetting.newInstance((Context)this), ActivityRecognitionManager.newInstance((Context)this), StepCounterManager.newInstance((Context)this));
        if (Build.VERSION.SDK_INT >= 26) {
            this.mAppNotificationAssistant = AppNotificationAssistant.getInstance((Context)this);
            this.registerReceiver((BroadcastReceiver)new DeviceLocaleChangeListener(), new IntentFilter("android.intent.action.LOCALE_CHANGED"));
        }
    }

    class DeviceLocaleChangeListener
    extends BroadcastReceiver {
        DeviceLocaleChangeListener() {
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                MvpApplication.this.mAppNotificationAssistant.refreshChannelLocales();
            }
        }
    }

}

