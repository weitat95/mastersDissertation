/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.fabric.sdk.android;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.FabricContext;
import io.fabric.sdk.android.InitializationCallback;
import io.fabric.sdk.android.InitializationTask;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.Task;
import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.concurrent.ExecutorService;

public abstract class Kit<Result>
implements Comparable<Kit> {
    Context context;
    final DependsOn dependsOnAnnotation;
    Fabric fabric;
    IdManager idManager;
    InitializationCallback<Result> initializationCallback;
    InitializationTask<Result> initializationTask = new InitializationTask(this);

    public Kit() {
        this.dependsOnAnnotation = this.getClass().getAnnotation(DependsOn.class);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int compareTo(Kit kit) {
        block6: {
            block5: {
                if (this.containsAnnotatedDependency(kit)) break block5;
                if (kit.containsAnnotatedDependency(this)) {
                    return -1;
                }
                if (!this.hasAnnotatedDependency() || kit.hasAnnotatedDependency()) break block6;
            }
            return 1;
        }
        if (!this.hasAnnotatedDependency() && kit.hasAnnotatedDependency()) {
            return -1;
        }
        return 0;
    }

    boolean containsAnnotatedDependency(Kit kit) {
        if (this.hasAnnotatedDependency()) {
            Class<?>[] arrclass = this.dependsOnAnnotation.value();
            int n = arrclass.length;
            for (int i = 0; i < n; ++i) {
                if (!arrclass[i].isAssignableFrom(kit.getClass())) continue;
                return true;
            }
        }
        return false;
    }

    protected abstract Result doInBackground();

    public Context getContext() {
        return this.context;
    }

    protected Collection<Task> getDependencies() {
        return this.initializationTask.getDependencies();
    }

    public Fabric getFabric() {
        return this.fabric;
    }

    protected IdManager getIdManager() {
        return this.idManager;
    }

    public abstract String getIdentifier();

    public String getPath() {
        return ".Fabric" + File.separator + this.getIdentifier();
    }

    public abstract String getVersion();

    boolean hasAnnotatedDependency() {
        return this.dependsOnAnnotation != null;
    }

    final void initialize() {
        this.initializationTask.executeOnExecutor(this.fabric.getExecutorService(), new Void[]{null});
    }

    void injectParameters(Context context, Fabric fabric, InitializationCallback<Result> initializationCallback, IdManager idManager) {
        this.fabric = fabric;
        this.context = new FabricContext(context, this.getIdentifier(), this.getPath());
        this.initializationCallback = initializationCallback;
        this.idManager = idManager;
    }

    protected void onCancelled(Result Result2) {
    }

    protected void onPostExecute(Result Result2) {
    }

    protected boolean onPreExecute() {
        return true;
    }
}

