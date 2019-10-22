/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 */
package com.getqardio.android.mvp.common.injection;

import android.content.Context;
import android.content.SharedPreferences;
import com.getqardio.android.fcm.FCMManager;
import com.getqardio.android.fcm.FCMManager_Factory;
import com.getqardio.android.fcm.api.PushNotificationApi;
import com.getqardio.android.io.network.invite.InviteApi;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.mvp.MvpApplication_MembersInjector;
import com.getqardio.android.mvp.activity.ActivityTrackingLocalDataSource;
import com.getqardio.android.mvp.activity.ActivityTrackingRepository;
import com.getqardio.android.mvp.activity_tracker.goals.model.GoalForActivityTypeDataSource;
import com.getqardio.android.mvp.activity_tracker.goals.model.GoalForActivityTypeRepository;
import com.getqardio.android.mvp.activity_tracker.history.model.HistoryActivityDataSource;
import com.getqardio.android.mvp.activity_tracker.history.model.HistoryActivityRepository;
import com.getqardio.android.mvp.activity_tracker.today.model.TodayActivityDataSource;
import com.getqardio.android.mvp.activity_tracker.today.model.TodayActivityRepository;
import com.getqardio.android.mvp.common.injection.ApplicationModule;
import com.getqardio.android.mvp.common.injection.ApplicationModule_ProvideContextFactory;
import com.getqardio.android.mvp.common.injection.ApplicationModule_ProvideRxEventBusFactory;
import com.getqardio.android.mvp.common.injection.LocalDataSourceModule;
import com.getqardio.android.mvp.common.injection.LocalDataSourceModule_ProvideAccountContextHelperFactory;
import com.getqardio.android.mvp.common.injection.LocalDataSourceModule_ProvideActivityTrackerHistoryLocalDataSourceFactory;
import com.getqardio.android.mvp.common.injection.LocalDataSourceModule_ProvideActivityTrackerLocalDataSourceFactory;
import com.getqardio.android.mvp.common.injection.LocalDataSourceModule_ProvideActivityTrackingLocalDataSourceFactory;
import com.getqardio.android.mvp.common.injection.LocalDataSourceModule_ProvideFollowingMeUserLocalDataSourceFactory;
import com.getqardio.android.mvp.common.injection.LocalDataSourceModule_ProvideGoalForActivityTypeLocalDataSourceFactory;
import com.getqardio.android.mvp.common.injection.LocalDataSourceModule_ProvideIFollowUserLocalDataSourceFactory;
import com.getqardio.android.mvp.common.injection.LocalDataSourceModule_ProvideQBMeasurementDetailsLocalDataSourceFactory;
import com.getqardio.android.mvp.common.injection.LocalDataSourceModule_ProvideRealmConfigFactory;
import com.getqardio.android.mvp.common.injection.LocalDataSourceModule_ProvideRealmFactory;
import com.getqardio.android.mvp.common.injection.LocalDataSourceModule_ProvideSharedPreferencesFactory;
import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule;
import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule_GetSimplifiedRetrofitFactory;
import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule_ProvideActivityTrackerHistoryRemoteDataSourceFactory;
import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule_ProvideActivityTrackerRemoteDataSourceFactory;
import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule_ProvideCommonHeadersInterceptorFactory;
import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule_ProvideFCMTokenRegisterApiFactory;
import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule_ProvideFollowingMeUserRemoteDataSourceFactory;
import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule_ProvideGoalForActivityTypeRemoteDataSourceFactory;
import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule_ProvideGsonFactory;
import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule_ProvideIFollowUserRemoteDataSourceFactory;
import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule_ProvideInviteApiFactory;
import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule_ProvideOkHttpClientFactory;
import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule_ProvideServerInterfaceFactory;
import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule_ProvideUnathorizedInterceptorFactory;
import com.getqardio.android.mvp.common.injection.RepositoryComponent;
import com.getqardio.android.mvp.common.injection.RepositoryModule;
import com.getqardio.android.mvp.common.injection.RepositoryModule_ProvideActivityTrackerHistoryRepoFactory;
import com.getqardio.android.mvp.common.injection.RepositoryModule_ProvideActivityTrackerRepoFactory;
import com.getqardio.android.mvp.common.injection.RepositoryModule_ProvideActivityTrackingRepositoryFactory;
import com.getqardio.android.mvp.common.injection.RepositoryModule_ProvideFollowingMeUserRepoFactory;
import com.getqardio.android.mvp.common.injection.RepositoryModule_ProvideGoalForActivityTypeRepoFactory;
import com.getqardio.android.mvp.common.injection.RepositoryModule_ProvideIFollowRepoFactory;
import com.getqardio.android.mvp.common.injection.RepositoryModule_ProvideSyncHelperFactory;
import com.getqardio.android.mvp.common.local.AccountContextHelper;
import com.getqardio.android.mvp.common.remote.CommonHeadersInterceptor;
import com.getqardio.android.mvp.common.remote.ServerInterface;
import com.getqardio.android.mvp.common.remote.UnauthorisedInterceptor;
import com.getqardio.android.mvp.common.util.RxEventBus;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserDataSource;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserDataSource;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository;
import com.getqardio.android.mvp.qardio_base.measurement_details.model.QBMeasurementDetailsLocalDataSource;
import com.getqardio.android.mvp.qardio_base.measurement_details.model.QBMeasurementDetailsRepository;
import com.getqardio.android.mvp.qardio_base.measurement_details.model.QBMeasurementDetailsRepository_Factory;
import com.getqardio.android.provider.SyncHelper;
import com.google.gson.Gson;
import dagger.MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public final class DaggerRepositoryComponent
implements RepositoryComponent {
    static final /* synthetic */ boolean $assertionsDisabled;
    private Provider<FCMManager> fCMManagerProvider;
    private Provider<Retrofit> getSimplifiedRetrofitProvider;
    private MembersInjector<MvpApplication> mvpApplicationMembersInjector;
    private Provider<AccountContextHelper> provideAccountContextHelperProvider;
    private Provider<HistoryActivityDataSource> provideActivityTrackerHistoryLocalDataSourceProvider;
    private Provider<HistoryActivityDataSource> provideActivityTrackerHistoryRemoteDataSourceProvider;
    private Provider<HistoryActivityRepository> provideActivityTrackerHistoryRepoProvider;
    private Provider<TodayActivityDataSource> provideActivityTrackerLocalDataSourceProvider;
    private Provider<TodayActivityDataSource> provideActivityTrackerRemoteDataSourceProvider;
    private Provider<TodayActivityRepository> provideActivityTrackerRepoProvider;
    private Provider<ActivityTrackingLocalDataSource> provideActivityTrackingLocalDataSourceProvider;
    private Provider<ActivityTrackingRepository> provideActivityTrackingRepositoryProvider;
    private Provider<CommonHeadersInterceptor> provideCommonHeadersInterceptorProvider;
    private Provider<Context> provideContextProvider;
    private Provider<PushNotificationApi> provideFCMTokenRegisterApiProvider;
    private Provider<FollowMeUserDataSource> provideFollowingMeUserLocalDataSourceProvider;
    private Provider<FollowMeUserDataSource> provideFollowingMeUserRemoteDataSourceProvider;
    private Provider<FollowMeUserRepository> provideFollowingMeUserRepoProvider;
    private Provider<GoalForActivityTypeDataSource> provideGoalForActivityTypeLocalDataSourceProvider;
    private Provider<GoalForActivityTypeDataSource> provideGoalForActivityTypeRemoteDataSourceProvider;
    private Provider<GoalForActivityTypeRepository> provideGoalForActivityTypeRepoProvider;
    private Provider<Gson> provideGsonProvider;
    private Provider<IFollowUserRepository> provideIFollowRepoProvider;
    private Provider<IFollowUserDataSource> provideIFollowUserLocalDataSourceProvider;
    private Provider<IFollowUserDataSource> provideIFollowUserRemoteDataSourceProvider;
    private Provider<InviteApi> provideInviteApiProvider;
    private Provider<OkHttpClient> provideOkHttpClientProvider;
    private Provider<QBMeasurementDetailsLocalDataSource> provideQBMeasurementDetailsLocalDataSourceProvider;
    private Provider<RealmConfiguration> provideRealmConfigProvider;
    private Provider<Realm> provideRealmProvider;
    private Provider<RxEventBus> provideRxEventBusProvider;
    private Provider<ServerInterface> provideServerInterfaceProvider;
    private Provider<SharedPreferences> provideSharedPreferencesProvider;
    private Provider<SyncHelper> provideSyncHelperProvider;
    private Provider<UnauthorisedInterceptor> provideUnathorizedInterceptorProvider;
    private Provider<QBMeasurementDetailsRepository> qBMeasurementDetailsRepositoryProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !DaggerRepositoryComponent.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    private DaggerRepositoryComponent(Builder builder) {
        if (!$assertionsDisabled && builder == null) {
            throw new AssertionError();
        }
        this.initialize(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(Builder builder) {
        this.provideContextProvider = ApplicationModule_ProvideContextFactory.create(builder.applicationModule);
        this.provideRealmConfigProvider = DoubleCheck.provider(LocalDataSourceModule_ProvideRealmConfigFactory.create(builder.localDataSourceModule, this.provideContextProvider));
        this.provideRealmProvider = DoubleCheck.provider(LocalDataSourceModule_ProvideRealmFactory.create(builder.localDataSourceModule, this.provideRealmConfigProvider));
        this.provideAccountContextHelperProvider = DoubleCheck.provider(LocalDataSourceModule_ProvideAccountContextHelperFactory.create(builder.localDataSourceModule, this.provideRealmProvider));
        this.provideFollowingMeUserLocalDataSourceProvider = DoubleCheck.provider(LocalDataSourceModule_ProvideFollowingMeUserLocalDataSourceFactory.create(builder.localDataSourceModule, this.provideRealmProvider, this.provideAccountContextHelperProvider));
        this.provideGsonProvider = DoubleCheck.provider(RemoteDataSourceModule_ProvideGsonFactory.create(builder.remoteDataSourceModule));
        this.provideRxEventBusProvider = DoubleCheck.provider(ApplicationModule_ProvideRxEventBusFactory.create(builder.applicationModule));
        this.provideUnathorizedInterceptorProvider = DoubleCheck.provider(RemoteDataSourceModule_ProvideUnathorizedInterceptorFactory.create(builder.remoteDataSourceModule, this.provideRxEventBusProvider));
        this.provideCommonHeadersInterceptorProvider = DoubleCheck.provider(RemoteDataSourceModule_ProvideCommonHeadersInterceptorFactory.create(builder.remoteDataSourceModule));
        this.provideOkHttpClientProvider = DoubleCheck.provider(RemoteDataSourceModule_ProvideOkHttpClientFactory.create(builder.remoteDataSourceModule, this.provideUnathorizedInterceptorProvider, this.provideCommonHeadersInterceptorProvider));
        this.provideServerInterfaceProvider = DoubleCheck.provider(RemoteDataSourceModule_ProvideServerInterfaceFactory.create(builder.remoteDataSourceModule, this.provideGsonProvider, this.provideOkHttpClientProvider));
        this.provideFollowingMeUserRemoteDataSourceProvider = DoubleCheck.provider(RemoteDataSourceModule_ProvideFollowingMeUserRemoteDataSourceFactory.create(builder.remoteDataSourceModule, this.provideServerInterfaceProvider, this.provideAccountContextHelperProvider));
        this.provideFollowingMeUserRepoProvider = DoubleCheck.provider(RepositoryModule_ProvideFollowingMeUserRepoFactory.create(builder.repositoryModule, this.provideFollowingMeUserLocalDataSourceProvider, this.provideFollowingMeUserRemoteDataSourceProvider));
        this.provideIFollowUserLocalDataSourceProvider = DoubleCheck.provider(LocalDataSourceModule_ProvideIFollowUserLocalDataSourceFactory.create(builder.localDataSourceModule, this.provideRealmProvider, this.provideAccountContextHelperProvider));
        this.provideIFollowUserRemoteDataSourceProvider = DoubleCheck.provider(RemoteDataSourceModule_ProvideIFollowUserRemoteDataSourceFactory.create(builder.remoteDataSourceModule, this.provideServerInterfaceProvider, this.provideAccountContextHelperProvider));
        this.provideIFollowRepoProvider = DoubleCheck.provider(RepositoryModule_ProvideIFollowRepoFactory.create(builder.repositoryModule, this.provideIFollowUserLocalDataSourceProvider, this.provideIFollowUserRemoteDataSourceProvider));
        this.provideGoalForActivityTypeLocalDataSourceProvider = DoubleCheck.provider(LocalDataSourceModule_ProvideGoalForActivityTypeLocalDataSourceFactory.create(builder.localDataSourceModule, this.provideRealmProvider));
        this.provideGoalForActivityTypeRemoteDataSourceProvider = DoubleCheck.provider(RemoteDataSourceModule_ProvideGoalForActivityTypeRemoteDataSourceFactory.create(builder.remoteDataSourceModule, this.provideServerInterfaceProvider, this.provideAccountContextHelperProvider));
        this.provideGoalForActivityTypeRepoProvider = DoubleCheck.provider(RepositoryModule_ProvideGoalForActivityTypeRepoFactory.create(builder.repositoryModule, this.provideGoalForActivityTypeLocalDataSourceProvider, this.provideGoalForActivityTypeRemoteDataSourceProvider));
        this.provideActivityTrackerLocalDataSourceProvider = DoubleCheck.provider(LocalDataSourceModule_ProvideActivityTrackerLocalDataSourceFactory.create(builder.localDataSourceModule, this.provideRealmProvider));
        this.provideActivityTrackerRemoteDataSourceProvider = DoubleCheck.provider(RemoteDataSourceModule_ProvideActivityTrackerRemoteDataSourceFactory.create(builder.remoteDataSourceModule, this.provideServerInterfaceProvider, this.provideAccountContextHelperProvider));
        this.provideActivityTrackerRepoProvider = DoubleCheck.provider(RepositoryModule_ProvideActivityTrackerRepoFactory.create(builder.repositoryModule, this.provideActivityTrackerLocalDataSourceProvider, this.provideActivityTrackerRemoteDataSourceProvider));
        this.provideActivityTrackerHistoryLocalDataSourceProvider = DoubleCheck.provider(LocalDataSourceModule_ProvideActivityTrackerHistoryLocalDataSourceFactory.create(builder.localDataSourceModule, this.provideRealmProvider));
        this.provideActivityTrackerHistoryRemoteDataSourceProvider = DoubleCheck.provider(RemoteDataSourceModule_ProvideActivityTrackerHistoryRemoteDataSourceFactory.create(builder.remoteDataSourceModule, this.provideServerInterfaceProvider, this.provideAccountContextHelperProvider));
        this.provideActivityTrackerHistoryRepoProvider = DoubleCheck.provider(RepositoryModule_ProvideActivityTrackerHistoryRepoFactory.create(builder.repositoryModule, this.provideActivityTrackerHistoryLocalDataSourceProvider, this.provideActivityTrackerHistoryRemoteDataSourceProvider));
        this.provideQBMeasurementDetailsLocalDataSourceProvider = DoubleCheck.provider(LocalDataSourceModule_ProvideQBMeasurementDetailsLocalDataSourceFactory.create(builder.localDataSourceModule, this.provideContextProvider, this.provideAccountContextHelperProvider));
        this.qBMeasurementDetailsRepositoryProvider = QBMeasurementDetailsRepository_Factory.create(this.provideQBMeasurementDetailsLocalDataSourceProvider);
        this.provideActivityTrackingLocalDataSourceProvider = DoubleCheck.provider(LocalDataSourceModule_ProvideActivityTrackingLocalDataSourceFactory.create(builder.localDataSourceModule, this.provideRealmProvider, this.provideContextProvider));
        this.provideActivityTrackingRepositoryProvider = DoubleCheck.provider(RepositoryModule_ProvideActivityTrackingRepositoryFactory.create(builder.repositoryModule, this.provideActivityTrackingLocalDataSourceProvider));
        this.getSimplifiedRetrofitProvider = DoubleCheck.provider(RemoteDataSourceModule_GetSimplifiedRetrofitFactory.create());
        this.provideInviteApiProvider = DoubleCheck.provider(RemoteDataSourceModule_ProvideInviteApiFactory.create(builder.remoteDataSourceModule, this.getSimplifiedRetrofitProvider));
        this.provideFCMTokenRegisterApiProvider = DoubleCheck.provider(RemoteDataSourceModule_ProvideFCMTokenRegisterApiFactory.create(builder.remoteDataSourceModule, this.getSimplifiedRetrofitProvider));
        this.provideSyncHelperProvider = DoubleCheck.provider(RepositoryModule_ProvideSyncHelperFactory.create(builder.repositoryModule, this.provideIFollowRepoProvider, this.provideFollowingMeUserRepoProvider));
        this.provideSharedPreferencesProvider = LocalDataSourceModule_ProvideSharedPreferencesFactory.create(builder.localDataSourceModule, this.provideContextProvider);
        this.fCMManagerProvider = FCMManager_Factory.create(this.provideFCMTokenRegisterApiProvider, this.provideSharedPreferencesProvider);
        this.mvpApplicationMembersInjector = MvpApplication_MembersInjector.create(this.provideSyncHelperProvider, this.provideRxEventBusProvider, this.fCMManagerProvider);
    }

    @Override
    public FollowMeUserRepository getFollowingMeUserRepository() {
        return this.provideFollowingMeUserRepoProvider.get();
    }

    @Override
    public GoalForActivityTypeRepository getGoalForActivityTypeRepository() {
        return this.provideGoalForActivityTypeRepoProvider.get();
    }

    @Override
    public IFollowUserRepository getIFollowUserRepository() {
        return this.provideIFollowRepoProvider.get();
    }

    @Override
    public QBMeasurementDetailsRepository getQBMeasurementDetailsRepository() {
        return new QBMeasurementDetailsRepository(this.provideQBMeasurementDetailsLocalDataSourceProvider.get());
    }

    @Override
    public void inject(MvpApplication mvpApplication) {
        this.mvpApplicationMembersInjector.injectMembers(mvpApplication);
    }

    @Override
    public InviteApi provideInviteApi() {
        return this.provideInviteApiProvider.get();
    }

    public static final class Builder {
        private ApplicationModule applicationModule;
        private LocalDataSourceModule localDataSourceModule;
        private RemoteDataSourceModule remoteDataSourceModule;
        private RepositoryModule repositoryModule;

        private Builder() {
        }

        public Builder applicationModule(ApplicationModule applicationModule) {
            this.applicationModule = Preconditions.checkNotNull(applicationModule);
            return this;
        }

        public RepositoryComponent build() {
            if (this.applicationModule == null) {
                throw new IllegalStateException(ApplicationModule.class.getCanonicalName() + " must be set");
            }
            if (this.localDataSourceModule == null) {
                this.localDataSourceModule = new LocalDataSourceModule();
            }
            if (this.remoteDataSourceModule == null) {
                this.remoteDataSourceModule = new RemoteDataSourceModule();
            }
            if (this.repositoryModule == null) {
                this.repositoryModule = new RepositoryModule();
            }
            return new DaggerRepositoryComponent(this);
        }

        public Builder localDataSourceModule(LocalDataSourceModule localDataSourceModule) {
            this.localDataSourceModule = Preconditions.checkNotNull(localDataSourceModule);
            return this;
        }

        public Builder remoteDataSourceModule(RemoteDataSourceModule remoteDataSourceModule) {
            this.remoteDataSourceModule = Preconditions.checkNotNull(remoteDataSourceModule);
            return this;
        }
    }

}

