/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.ActivityInfo
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 */
package com.segment.analytics;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.segment.analytics.AnalyticsContext;
import com.segment.analytics.BooleanPreference;
import com.segment.analytics.Cartographer;
import com.segment.analytics.Client;
import com.segment.analytics.ConnectionFactory;
import com.segment.analytics.Crypto;
import com.segment.analytics.IntegrationOperation;
import com.segment.analytics.Middleware;
import com.segment.analytics.Options;
import com.segment.analytics.ProjectSettings;
import com.segment.analytics.Properties;
import com.segment.analytics.RealMiddlewareChain;
import com.segment.analytics.SegmentIntegration;
import com.segment.analytics.Stats;
import com.segment.analytics.Traits;
import com.segment.analytics.ValueMap;
import com.segment.analytics.integrations.AliasPayload;
import com.segment.analytics.integrations.BasePayload;
import com.segment.analytics.integrations.GroupPayload;
import com.segment.analytics.integrations.IdentifyPayload;
import com.segment.analytics.integrations.Integration;
import com.segment.analytics.integrations.Logger;
import com.segment.analytics.integrations.ScreenPayload;
import com.segment.analytics.integrations.TrackPayload;
import com.segment.analytics.internal.Utils;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Analytics {
    static final Properties EMPTY_PROPERTIES;
    static final Handler HANDLER;
    static final List<String> INSTANCES;
    static volatile Analytics singleton;
    final Application.ActivityLifecycleCallbacks activityLifecycleCallback;
    private final CountDownLatch advertisingIdLatch;
    final AnalyticsContext analyticsContext;
    private final ExecutorService analyticsExecutor;
    private final Application application;
    final Map<String, Boolean> bundledIntegrations = new ConcurrentHashMap<String, Boolean>();
    final Cartographer cartographer;
    final Client client;
    final Crypto crypto;
    final Options defaultOptions;
    private List<Integration.Factory> factories;
    final long flushIntervalInMillis;
    final int flushQueueSize;
    private Map<String, Integration<?>> integrations;
    private final Logger logger;
    private final List<Middleware> middlewares;
    final ExecutorService networkExecutor;
    private final BooleanPreference optOut;
    ProjectSettings projectSettings;
    private final ProjectSettings.Cache projectSettingsCache;
    volatile boolean shutdown;
    final Stats stats;
    final String tag;
    final Traits.Cache traitsCache;
    final String writeKey;

    static {
        HANDLER = new Handler(Looper.getMainLooper()){

            public void handleMessage(Message message) {
                throw new AssertionError((Object)("Unknown handler message received: " + message.what));
            }
        };
        INSTANCES = new ArrayList<String>(1);
        singleton = null;
        EMPTY_PROPERTIES = new Properties();
    }

    Analytics(Application application, ExecutorService executorService, Stats stats, Traits.Cache cache, AnalyticsContext analyticsContext, Options options, Logger logger, String string2, List<Integration.Factory> list, Client client, Cartographer cartographer, ProjectSettings.Cache cache2, String string3, int n, long l, final ExecutorService executorService2, final boolean bl, CountDownLatch countDownLatch, final boolean bl2, final boolean bl3, BooleanPreference booleanPreference, Crypto crypto, List<Middleware> list2) {
        this.application = application;
        this.networkExecutor = executorService;
        this.stats = stats;
        this.traitsCache = cache;
        this.analyticsContext = analyticsContext;
        this.defaultOptions = options;
        this.logger = logger;
        this.tag = string2;
        this.client = client;
        this.cartographer = cartographer;
        this.projectSettingsCache = cache2;
        this.writeKey = string3;
        this.flushQueueSize = n;
        this.flushIntervalInMillis = l;
        this.advertisingIdLatch = countDownLatch;
        this.optOut = booleanPreference;
        this.factories = list;
        this.analyticsExecutor = executorService2;
        this.crypto = crypto;
        this.middlewares = list2;
        this.namespaceSharedPreferences();
        executorService2.submit(new Runnable(){

            @Override
            public void run() {
                Analytics.this.projectSettings = Analytics.this.getSettings();
                if (Utils.isNullOrEmpty(Analytics.this.projectSettings)) {
                    Analytics.this.projectSettings = ProjectSettings.create(new ValueMap().putValue("integrations", new ValueMap().putValue("Segment.io", new ValueMap().putValue("apiKey", Analytics.this.writeKey))));
                }
                HANDLER.post(new Runnable(){

                    @Override
                    public void run() {
                        Analytics.this.performInitializeIntegrations(Analytics.this.projectSettings);
                    }
                });
            }

        });
        logger.debug("Created analytics client for project with tag:%s.", string2);
        this.activityLifecycleCallback = new Application.ActivityLifecycleCallbacks(){
            final AtomicBoolean trackedApplicationLifecycleEvents = new AtomicBoolean(false);

            public void onActivityCreated(Activity activity, Bundle bundle) {
                if (!this.trackedApplicationLifecycleEvents.getAndSet(true) && bl) {
                    Analytics.this.trackApplicationLifecycleEvents();
                    if (bl3) {
                        executorService2.submit(new Runnable(){

                            @Override
                            public void run() {
                                Analytics.this.trackAttributionInformation();
                            }
                        });
                    }
                }
                Analytics.this.runOnMainThread(IntegrationOperation.onActivityCreated(activity, bundle));
            }

            public void onActivityDestroyed(Activity activity) {
                Analytics.this.runOnMainThread(IntegrationOperation.onActivityDestroyed(activity));
            }

            public void onActivityPaused(Activity activity) {
                Analytics.this.runOnMainThread(IntegrationOperation.onActivityPaused(activity));
            }

            public void onActivityResumed(Activity activity) {
                Analytics.this.runOnMainThread(IntegrationOperation.onActivityResumed(activity));
            }

            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                Analytics.this.runOnMainThread(IntegrationOperation.onActivitySaveInstanceState(activity, bundle));
            }

            public void onActivityStarted(Activity activity) {
                if (bl2) {
                    Analytics.this.recordScreenViews(activity);
                }
                Analytics.this.runOnMainThread(IntegrationOperation.onActivityStarted(activity));
            }

            public void onActivityStopped(Activity activity) {
                Analytics.this.runOnMainThread(IntegrationOperation.onActivityStopped(activity));
            }

        };
        application.registerActivityLifecycleCallbacks(this.activityLifecycleCallback);
    }

    private void assertNotShutdown() {
        if (this.shutdown) {
            throw new IllegalStateException("Cannot enqueue messages after client is shutdown.");
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private ProjectSettings downloadSettings() {
        try {
            ProjectSettings projectSettings = this.networkExecutor.submit(new Callable<ProjectSettings>(){

                @Override
                public ProjectSettings call() throws Exception {
                    Client.Connection connection;
                    Client.Connection connection2 = null;
                    try {
                        connection2 = connection = Analytics.this.client.fetchSettings();
                    }
                    catch (Throwable throwable) {
                        Utils.closeQuietly(connection2);
                        throw throwable;
                    }
                    ProjectSettings projectSettings = ProjectSettings.create(Analytics.this.cartographer.fromJson(Utils.buffer(connection.is)));
                    Utils.closeQuietly(connection);
                    return projectSettings;
                }
            }).get();
            this.projectSettingsCache.set(projectSettings);
            return projectSettings;
        }
        catch (InterruptedException interruptedException) {
            this.logger.error(interruptedException, "Thread interrupted while fetching settings.", new Object[0]);
            do {
                return null;
                break;
            } while (true);
        }
        catch (ExecutionException executionException) {
            this.logger.error(executionException, "Unable to fetch settings. Retrying in %s ms.", 60000L);
            return null;
        }
    }

    static PackageInfo getPackageInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageManager;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            throw new AssertionError((Object)("Package not found: " + context.getPackageName()));
        }
    }

    private void namespaceSharedPreferences() {
        SharedPreferences sharedPreferences = Utils.getSegmentSharedPreferences((Context)this.application, this.tag);
        BooleanPreference booleanPreference = new BooleanPreference(sharedPreferences, "namespaceSharedPreferences", true);
        if (booleanPreference.get()) {
            Utils.copySharedPreferences(this.application.getSharedPreferences("analytics-android", 0), sharedPreferences);
            booleanPreference.set(false);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setSingletonInstance(Analytics analytics) {
        synchronized (Analytics.class) {
            if (singleton != null) {
                throw new IllegalStateException("Singleton instance already exists.");
            }
            singleton = analytics;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void waitForAdvertisingId() {
        try {
            this.advertisingIdLatch.await(15L, TimeUnit.SECONDS);
        }
        catch (InterruptedException interruptedException) {
            this.logger.error(interruptedException, "Thread interrupted while waiting for advertising ID.", new Object[0]);
        }
        if (this.advertisingIdLatch.getCount() == 1L) {
            this.logger.debug("Advertising ID may not be collected because the API did not respond within 15 seconds.", new Object[0]);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Analytics with(Context context) {
        boolean bl = false;
        if (singleton != null) return singleton;
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        synchronized (Analytics.class) {
            if (singleton == null) {
                Builder builder = new Builder(context, Utils.getResourceString(context, "analytics_write_key"));
                try {
                    String string2 = context.getPackageName();
                    if ((context.getPackageManager().getApplicationInfo((String)string2, (int)0).flags & 2) != 0) {
                        bl = true;
                    }
                    if (bl) {
                        builder.logLevel(LogLevel.INFO);
                    }
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {}
                singleton = builder.build();
            }
            return singleton;
        }
    }

    void enqueue(BasePayload basePayload) {
        if (this.optOut.get()) {
            return;
        }
        this.logger.verbose("Created payload %s.", basePayload);
        new RealMiddlewareChain(0, basePayload, this.middlewares, this).proceed(basePayload);
    }

    void fillAndEnqueue(BasePayload.Builder<?, ?> builder, Options object) {
        this.waitForAdvertisingId();
        AnalyticsContext analyticsContext = this.analyticsContext.unmodifiableCopy();
        builder.context(analyticsContext);
        builder.anonymousId(analyticsContext.traits().anonymousId());
        builder.integrations(((Options)object).integrations());
        object = analyticsContext.traits().userId();
        if (!Utils.isNullOrEmpty((CharSequence)object)) {
            builder.userId((String)object);
        }
        this.enqueue((BasePayload)builder.build());
    }

    public AnalyticsContext getAnalyticsContext() {
        return this.analyticsContext;
    }

    public Application getApplication() {
        return this.application;
    }

    public Logger getLogger() {
        return this.logger;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    ProjectSettings getSettings() {
        ProjectSettings projectSettings = (ProjectSettings)this.projectSettingsCache.get();
        if (Utils.isNullOrEmpty(projectSettings)) {
            return this.downloadSettings();
        }
        ProjectSettings projectSettings2 = projectSettings;
        if (projectSettings.timestamp() + 86400000L > System.currentTimeMillis()) return projectSettings2;
        ProjectSettings projectSettings3 = this.downloadSettings();
        projectSettings2 = projectSettings;
        if (Utils.isNullOrEmpty(projectSettings3)) return projectSettings2;
        return projectSettings3;
    }

    public void identify(String string2, Traits traits, final Options options) {
        this.assertNotShutdown();
        if (Utils.isNullOrEmpty(string2) && Utils.isNullOrEmpty(traits)) {
            throw new IllegalArgumentException("Either userId or some traits must be provided.");
        }
        Traits traits2 = (Traits)this.traitsCache.get();
        if (!Utils.isNullOrEmpty(string2)) {
            traits2.putUserId(string2);
        }
        if (!Utils.isNullOrEmpty(traits)) {
            traits2.putAll(traits);
        }
        this.traitsCache.set(traits2);
        this.analyticsContext.setTraits(traits2);
        this.analyticsExecutor.submit(new Runnable(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void run() {
                Options options2 = options == null ? Analytics.this.defaultOptions : options;
                IdentifyPayload.Builder builder = new IdentifyPayload.Builder().traits((Map<String, ?>)Analytics.this.traitsCache.get());
                Analytics.this.fillAndEnqueue(builder, options2);
            }
        });
    }

    public Logger logger(String string2) {
        return this.logger.subLog(string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    void performInitializeIntegrations(ProjectSettings valueMap) {
        valueMap = ((ProjectSettings)valueMap).integrations();
        this.integrations = new LinkedHashMap(this.factories.size());
        int n = 0;
        do {
            if (n >= this.factories.size()) {
                this.factories = null;
                return;
            }
            Integration.Factory factory = this.factories.get(n);
            String string2 = factory.key();
            Object object = valueMap.getValueMap(string2);
            if (Utils.isNullOrEmpty((Map)object)) {
                this.logger.debug("Integration %s is not enabled.", string2);
            } else if ((object = factory.create((ValueMap)object, this)) == null) {
                this.logger.info("Factory %s couldn't create integration.", factory);
            } else {
                this.integrations.put(string2, (Integration<?>)object);
                this.bundledIntegrations.put(string2, false);
            }
            ++n;
        } while (true);
    }

    void performRun(IntegrationOperation integrationOperation) {
        for (Map.Entry<String, Integration<?>> entry : this.integrations.entrySet()) {
            String string2 = entry.getKey();
            long l = System.nanoTime();
            integrationOperation.run(string2, entry.getValue(), this.projectSettings);
            long l2 = System.nanoTime();
            long l3 = TimeUnit.NANOSECONDS.toMillis(l2 - l);
            this.stats.dispatchIntegrationOperation(string2, l3);
            this.logger.debug("Ran %s on integration %s in %d ns.", integrationOperation, string2, l2 - l);
        }
    }

    void recordScreenViews(Activity activity) {
        PackageManager packageManager = activity.getPackageManager();
        try {
            this.screen(null, packageManager.getActivityInfo(activity.getComponentName(), 128).loadLabel(packageManager).toString());
            return;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            throw new AssertionError((Object)("Activity Not Found: " + nameNotFoundException.toString()));
        }
    }

    public void reset() {
        Utils.getSegmentSharedPreferences((Context)this.application, this.tag).edit().clear().apply();
        this.traitsCache.delete();
        this.traitsCache.set(Traits.create());
        this.analyticsContext.setTraits((Traits)this.traitsCache.get());
        this.runOnMainThread(IntegrationOperation.RESET);
    }

    /*
     * Enabled aggressive block sorting
     */
    void run(BasePayload object) {
        this.logger.verbose("Running payload %s.", object);
        switch (13.$SwitchMap$com$segment$analytics$integrations$BasePayload$Type[((BasePayload)object).type().ordinal()]) {
            default: {
                throw new AssertionError((Object)("unknown type " + (Object)((Object)((BasePayload)object).type())));
            }
            case 1: {
                object = IntegrationOperation.identify((IdentifyPayload)object);
                break;
            }
            case 2: {
                object = IntegrationOperation.alias((AliasPayload)object);
                break;
            }
            case 3: {
                object = IntegrationOperation.group((GroupPayload)object);
                break;
            }
            case 4: {
                object = IntegrationOperation.track((TrackPayload)object);
                break;
            }
            case 5: {
                object = IntegrationOperation.screen((ScreenPayload)object);
            }
        }
        HANDLER.post(new Runnable((IntegrationOperation)object){
            final /* synthetic */ IntegrationOperation val$operation;
            {
                this.val$operation = integrationOperation;
            }

            @Override
            public void run() {
                Analytics.this.performRun(this.val$operation);
            }
        });
    }

    void runOnMainThread(final IntegrationOperation integrationOperation) {
        if (this.shutdown) {
            return;
        }
        this.analyticsExecutor.submit(new Runnable(){

            @Override
            public void run() {
                HANDLER.post(new Runnable(){

                    @Override
                    public void run() {
                        Analytics.this.performRun(integrationOperation);
                    }
                });
            }

        });
    }

    public void screen(String string2, String string3) {
        this.screen(string2, string3, null, null);
    }

    public void screen(String string2, String string3, Properties properties) {
        this.screen(string2, string3, properties, null);
    }

    public void screen(final String string2, final String string3, final Properties properties, final Options options) {
        this.assertNotShutdown();
        if (Utils.isNullOrEmpty(string2) && Utils.isNullOrEmpty(string3)) {
            throw new IllegalArgumentException("either category or name must be provided.");
        }
        this.analyticsExecutor.submit(new Runnable(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void run() {
                Options options2 = options == null ? Analytics.this.defaultOptions : options;
                Object object = properties == null ? EMPTY_PROPERTIES : properties;
                object = new ScreenPayload.Builder().name(string3).category(string2).properties((Map<String, ?>)object);
                Analytics.this.fillAndEnqueue((BasePayload.Builder<?, ?>)object, options2);
            }
        });
    }

    public void track(String string2, Properties properties) {
        this.track(string2, properties, null);
    }

    public void track(final String string2, final Properties properties, final Options options) {
        this.assertNotShutdown();
        if (Utils.isNullOrEmpty(string2)) {
            throw new IllegalArgumentException("event must not be null or empty.");
        }
        this.analyticsExecutor.submit(new Runnable(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void run() {
                Options options2 = options == null ? Analytics.this.defaultOptions : options;
                Object object = properties == null ? EMPTY_PROPERTIES : properties;
                object = new TrackPayload.Builder().event(string2).properties((Map<String, ?>)object);
                Analytics.this.fillAndEnqueue((BasePayload.Builder<?, ?>)object, options2);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    void trackApplicationLifecycleEvents() {
        PackageInfo packageInfo = Analytics.getPackageInfo((Context)this.application);
        String string2 = packageInfo.versionName;
        int n = packageInfo.versionCode;
        packageInfo = Utils.getSegmentSharedPreferences((Context)this.application, this.tag);
        String string3 = packageInfo.getString("version", null);
        int n2 = packageInfo.getInt("build", -1);
        if (n2 == -1) {
            this.track("Application Installed", new Properties().putValue("version", string2).putValue("build", n));
        } else if (n != n2) {
            this.track("Application Updated", new Properties().putValue("version", string2).putValue("build", n).putValue("previous_version", string3).putValue("previous_build", n2));
        }
        this.track("Application Opened", new Properties().putValue("version", string2).putValue("build", n));
        packageInfo = packageInfo.edit();
        packageInfo.putString("version", string2);
        packageInfo.putInt("build", n);
        packageInfo.apply();
    }

    void trackAttributionInformation() {
        Client.Connection connection;
        BooleanPreference booleanPreference = new BooleanPreference(Utils.getSegmentSharedPreferences((Context)this.application, this.tag), "tracked_attribution", false);
        if (booleanPreference.get()) {
            return;
        }
        this.waitForAdvertisingId();
        Client.Connection connection2 = null;
        Client.Connection connection3 = null;
        connection3 = connection = this.client.attribution();
        connection2 = connection;
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.os));
        connection3 = connection;
        connection2 = connection;
        this.cartographer.toJson(this.analyticsContext, bufferedWriter);
        connection3 = connection;
        connection2 = connection;
        this.track("Install Attributed", new Properties(this.cartographer.fromJson(Utils.buffer(Utils.getInputStream(connection.connection)))));
        connection3 = connection;
        connection2 = connection;
        try {
            booleanPreference.set(true);
        }
        catch (IOException iOException) {
            connection2 = connection3;
            try {
                this.logger.error(iOException, "Unable to track attribution information. Retrying on next launch.", new Object[0]);
            }
            catch (Throwable throwable) {
                Utils.closeQuietly(connection2);
                throw throwable;
            }
            Utils.closeQuietly(connection3);
            return;
        }
        Utils.closeQuietly(connection);
        return;
    }

    public static class Builder {
        private final Application application;
        private boolean collectDeviceID = true;
        private ConnectionFactory connectionFactory;
        private Crypto crypto;
        private Options defaultOptions;
        private ExecutorService executor;
        private final List<Integration.Factory> factories = new ArrayList<Integration.Factory>();
        private long flushIntervalInMillis = 30000L;
        private int flushQueueSize = 20;
        private LogLevel logLevel;
        private List<Middleware> middlewares;
        private ExecutorService networkExecutor;
        private boolean recordScreenViews = false;
        private String tag;
        private boolean trackApplicationLifecycleEvents = false;
        private boolean trackAttributionInformation = false;
        private String writeKey;

        public Builder(Context context, String string2) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            if (!Utils.hasPermission(context, "android.permission.INTERNET")) {
                throw new IllegalArgumentException("INTERNET permission is required.");
            }
            this.application = (Application)context.getApplicationContext();
            if (this.application == null) {
                throw new IllegalArgumentException("Application context must not be null.");
            }
            if (Utils.isNullOrEmpty(string2)) {
                throw new IllegalArgumentException("writeKey must not be null or empty.");
            }
            this.writeKey = string2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public Analytics build() {
            if (Utils.isNullOrEmpty(this.tag)) {
                this.tag = this.writeKey;
            }
            Object object = INSTANCES;
            // MONITORENTER : object
            if (INSTANCES.contains(this.tag)) {
                throw new IllegalStateException("Duplicate analytics client created with tag: " + this.tag + ". If you want to use multiple Analytics clients, use a different writeKey or set a tag via the builder during construction.");
            }
            INSTANCES.add(this.tag);
            // MONITOREXIT : object
            if (this.defaultOptions == null) {
                this.defaultOptions = new Options();
            }
            if (this.logLevel == null) {
                this.logLevel = LogLevel.NONE;
            }
            if (this.networkExecutor == null) {
                this.networkExecutor = new Utils.AnalyticsNetworkExecutorService();
            }
            if (this.connectionFactory == null) {
                this.connectionFactory = new ConnectionFactory();
            }
            if (this.crypto == null) {
                this.crypto = Crypto.none();
            }
            Stats stats = new Stats();
            Cartographer cartographer = Cartographer.INSTANCE;
            Client client = new Client(this.writeKey, this.connectionFactory);
            ProjectSettings.Cache cache = new ProjectSettings.Cache((Context)this.application, cartographer, this.tag);
            BooleanPreference booleanPreference = new BooleanPreference(Utils.getSegmentSharedPreferences((Context)this.application, this.tag), "opt-out", false);
            Traits.Cache cache2 = new Traits.Cache((Context)this.application, cartographer, this.tag);
            if (!cache2.isSet() || cache2.get() == null) {
                cache2.set(Traits.create());
            }
            Logger logger = Logger.with(this.logLevel);
            AnalyticsContext analyticsContext = AnalyticsContext.create((Context)this.application, (Traits)cache2.get(), this.collectDeviceID);
            CountDownLatch countDownLatch = new CountDownLatch(1);
            analyticsContext.attachAdvertisingId((Context)this.application, countDownLatch, logger);
            ArrayList<Integration.Factory> arrayList = new ArrayList<Integration.Factory>(this.factories.size() + 1);
            arrayList.add(SegmentIntegration.FACTORY);
            arrayList.addAll(this.factories);
            List<Middleware> list = Utils.immutableCopyOf(this.middlewares);
            ExecutorService executorService = this.executor;
            object = executorService;
            if (executorService != null) return new Analytics(this.application, this.networkExecutor, stats, cache2, analyticsContext, this.defaultOptions, logger, this.tag, Collections.unmodifiableList(arrayList), client, cartographer, cache, this.writeKey, this.flushQueueSize, this.flushIntervalInMillis, (ExecutorService)object, this.trackApplicationLifecycleEvents, countDownLatch, this.recordScreenViews, this.trackAttributionInformation, booleanPreference, this.crypto, list);
            object = Executors.newSingleThreadExecutor();
            return new Analytics(this.application, this.networkExecutor, stats, cache2, analyticsContext, this.defaultOptions, logger, this.tag, Collections.unmodifiableList(arrayList), client, cartographer, cache, this.writeKey, this.flushQueueSize, this.flushIntervalInMillis, (ExecutorService)object, this.trackApplicationLifecycleEvents, countDownLatch, this.recordScreenViews, this.trackAttributionInformation, booleanPreference, this.crypto, list);
        }

        public Builder logLevel(LogLevel logLevel) {
            if (logLevel == null) {
                throw new IllegalArgumentException("LogLevel must not be null.");
            }
            this.logLevel = logLevel;
            return this;
        }

        public Builder use(Integration.Factory factory) {
            if (factory == null) {
                throw new IllegalArgumentException("Factory must not be null.");
            }
            this.factories.add(factory);
            return this;
        }
    }

    public static enum LogLevel {
        NONE,
        INFO,
        DEBUG,
        BASIC,
        VERBOSE;

    }

}

