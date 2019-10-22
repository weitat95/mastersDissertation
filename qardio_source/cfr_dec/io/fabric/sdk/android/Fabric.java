/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 */
package io.fabric.sdk.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import io.fabric.sdk.android.ActivityLifecycleManager;
import io.fabric.sdk.android.DefaultLogger;
import io.fabric.sdk.android.FabricKitsFinder;
import io.fabric.sdk.android.InitializationCallback;
import io.fabric.sdk.android.InitializationTask;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.KitGroup;
import io.fabric.sdk.android.KitInfo;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.Onboarding;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.PriorityThreadPoolExecutor;
import io.fabric.sdk.android.services.concurrency.Task;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class Fabric {
    static final Logger DEFAULT_LOGGER = new DefaultLogger();
    static volatile Fabric singleton;
    private WeakReference<Activity> activity;
    private ActivityLifecycleManager activityLifecycleManager;
    private final Context context;
    final boolean debuggable;
    private final ExecutorService executorService;
    private final IdManager idManager;
    private final InitializationCallback<Fabric> initializationCallback;
    private AtomicBoolean initialized;
    private final InitializationCallback<?> kitInitializationCallback;
    private final Map<Class<? extends Kit>, Kit> kits;
    final Logger logger;
    private final Handler mainHandler;

    Fabric(Context context, Map<Class<? extends Kit>, Kit> map, PriorityThreadPoolExecutor priorityThreadPoolExecutor, Handler handler, Logger logger, boolean bl, InitializationCallback initializationCallback, IdManager idManager) {
        this.context = context.getApplicationContext();
        this.kits = map;
        this.executorService = priorityThreadPoolExecutor;
        this.mainHandler = handler;
        this.logger = logger;
        this.debuggable = bl;
        this.initializationCallback = initializationCallback;
        this.initialized = new AtomicBoolean(false);
        this.kitInitializationCallback = this.createKitInitializationCallback(map.size());
        this.idManager = idManager;
        this.setCurrentActivity(this.extractActivity(context));
    }

    private static void addToKitMap(Map<Class<? extends Kit>, Kit> map, Collection<? extends Kit> object) {
        object = object.iterator();
        while (object.hasNext()) {
            Kit kit = (Kit)object.next();
            map.put(kit.getClass(), kit);
            if (!(kit instanceof KitGroup)) continue;
            Fabric.addToKitMap(map, ((KitGroup)((Object)kit)).getKits());
        }
    }

    private Activity extractActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity)context;
        }
        return null;
    }

    public static <T extends Kit> T getKit(Class<T> class_) {
        return (T)Fabric.singleton().kits.get(class_);
    }

    private static Map<Class<? extends Kit>, Kit> getKitMap(Collection<? extends Kit> collection) {
        HashMap<Class<? extends Kit>, Kit> hashMap = new HashMap<Class<? extends Kit>, Kit>(collection.size());
        Fabric.addToKitMap(hashMap, collection);
        return hashMap;
    }

    public static Logger getLogger() {
        if (singleton == null) {
            return DEFAULT_LOGGER;
        }
        return Fabric.singleton.logger;
    }

    private void init() {
        this.activityLifecycleManager = new ActivityLifecycleManager(this.context);
        this.activityLifecycleManager.registerCallbacks(new ActivityLifecycleManager.Callbacks(){

            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                Fabric.this.setCurrentActivity(activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Fabric.this.setCurrentActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Fabric.this.setCurrentActivity(activity);
            }
        });
        this.initializeKits(this.context);
    }

    public static boolean isDebuggable() {
        if (singleton == null) {
            return false;
        }
        return Fabric.singleton.debuggable;
    }

    private static void setFabric(Fabric fabric) {
        singleton = fabric;
        fabric.init();
    }

    static Fabric singleton() {
        if (singleton == null) {
            throw new IllegalStateException("Must Initialize Fabric before using singleton()");
        }
        return singleton;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Fabric with(Context context, Kit ... arrkit) {
        if (singleton == null) {
            synchronized (Fabric.class) {
                if (singleton == null) {
                    Fabric.setFabric(new Builder(context).kits(arrkit).build());
                }
            }
        }
        return singleton;
    }

    void addAnnotatedDependencies(Map<Class<? extends Kit>, Kit> map, Kit kit) {
        Class<?>[] arrclass = kit.dependsOnAnnotation;
        if (arrclass != null) {
            for (Class<?> class_ : arrclass.value()) {
                if (class_.isInterface()) {
                    for (Kit kit2 : map.values()) {
                        if (!class_.isAssignableFrom(kit2.getClass())) continue;
                        kit.initializationTask.addDependency(kit2.initializationTask);
                    }
                    continue;
                }
                if (map.get(class_) == null) {
                    throw new UnmetDependencyException("Referenced Kit was null, does the kit exist?");
                }
                kit.initializationTask.addDependency(map.get(class_).initializationTask);
            }
        }
    }

    InitializationCallback<?> createKitInitializationCallback(final int n) {
        return new InitializationCallback(){
            final CountDownLatch kitInitializedLatch;
            {
                this.kitInitializedLatch = new CountDownLatch(n);
            }

            @Override
            public void failure(Exception exception) {
                Fabric.this.initializationCallback.failure(exception);
            }

            public void success(Object object) {
                this.kitInitializedLatch.countDown();
                if (this.kitInitializedLatch.getCount() == 0L) {
                    Fabric.this.initialized.set(true);
                    Fabric.this.initializationCallback.success(Fabric.this);
                }
            }
        };
    }

    public ActivityLifecycleManager getActivityLifecycleManager() {
        return this.activityLifecycleManager;
    }

    public Activity getCurrentActivity() {
        if (this.activity != null) {
            return (Activity)this.activity.get();
        }
        return null;
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public String getIdentifier() {
        return "io.fabric.sdk.android:fabric";
    }

    public Collection<Kit> getKits() {
        return this.kits.values();
    }

    Future<Map<String, KitInfo>> getKitsFinderFuture(Context object) {
        object = new FabricKitsFinder(object.getPackageCodePath());
        return this.getExecutorService().submit(object);
    }

    public String getVersion() {
        return "1.3.15.167";
    }

    /*
     * Enabled aggressive block sorting
     */
    void initializeKits(Context object) {
        Object object2 = this.getKitsFinderFuture((Context)object);
        Object object3 = this.getKits();
        object2 = new Onboarding((Future<Map<String, KitInfo>>)object2, (Collection<Kit>)object3);
        object3 = new ArrayList<Kit>((Collection<Kit>)object3);
        Collections.sort(object3);
        ((Kit)object2).injectParameters((Context)object, this, InitializationCallback.EMPTY, this.idManager);
        Object object4 = object3.iterator();
        while (object4.hasNext()) {
            ((Kit)object4.next()).injectParameters((Context)object, this, this.kitInitializationCallback, this.idManager);
        }
        ((Kit)object2).initialize();
        object = Fabric.getLogger().isLoggable("Fabric", 3) ? new StringBuilder("Initializing ").append(this.getIdentifier()).append(" [Version: ").append(this.getVersion()).append("], with the following kits:\n") : null;
        object3 = object3.iterator();
        while (object3.hasNext()) {
            object4 = (Kit)object3.next();
            ((Kit)object4).initializationTask.addDependency(((Onboarding)object2).initializationTask);
            this.addAnnotatedDependencies(this.kits, (Kit)object4);
            ((Kit)object4).initialize();
            if (object == null) continue;
            ((StringBuilder)object).append(((Kit)object4).getIdentifier()).append(" [Version: ").append(((Kit)object4).getVersion()).append("]\n");
        }
        if (object != null) {
            Fabric.getLogger().d("Fabric", ((StringBuilder)object).toString());
        }
    }

    public Fabric setCurrentActivity(Activity activity) {
        this.activity = new WeakReference<Activity>(activity);
        return this;
    }

    public static class Builder {
        private String appIdentifier;
        private String appInstallIdentifier;
        private final Context context;
        private boolean debuggable;
        private Handler handler;
        private InitializationCallback<Fabric> initializationCallback;
        private Kit[] kits;
        private Logger logger;
        private PriorityThreadPoolExecutor threadPoolExecutor;

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Fabric build() {
            if (this.threadPoolExecutor == null) {
                this.threadPoolExecutor = PriorityThreadPoolExecutor.create();
            }
            if (this.handler == null) {
                this.handler = new Handler(Looper.getMainLooper());
            }
            if (this.logger == null) {
                this.logger = this.debuggable ? new DefaultLogger(3) : new DefaultLogger();
            }
            if (this.appIdentifier == null) {
                this.appIdentifier = this.context.getPackageName();
            }
            if (this.initializationCallback == null) {
                this.initializationCallback = InitializationCallback.EMPTY;
            }
            Map map = this.kits == null ? new HashMap() : Fabric.getKitMap(Arrays.asList(this.kits));
            IdManager idManager = new IdManager(this.context, this.appIdentifier, this.appInstallIdentifier, map.values());
            return new Fabric(this.context, map, this.threadPoolExecutor, this.handler, this.logger, this.debuggable, this.initializationCallback, idManager);
        }

        public Builder kits(Kit ... arrkit) {
            if (this.kits != null) {
                throw new IllegalStateException("Kits already set.");
            }
            this.kits = arrkit;
            return this;
        }
    }

}

