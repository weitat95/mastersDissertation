/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.bumptech.glide.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.manager.ActivityFragmentLifecycle;
import com.bumptech.glide.manager.ApplicationLifecycle;
import com.bumptech.glide.manager.EmptyRequestManagerTreeNode;
import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.RequestManagerFragment;
import com.bumptech.glide.manager.RequestManagerTreeNode;
import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.bumptech.glide.util.Util;
import java.util.HashMap;
import java.util.Map;

public class RequestManagerRetriever
implements Handler.Callback {
    private static final RequestManagerRetriever INSTANCE = new RequestManagerRetriever();
    private volatile RequestManager applicationManager;
    private final Handler handler;
    final Map<android.app.FragmentManager, RequestManagerFragment> pendingRequestManagerFragments = new HashMap<android.app.FragmentManager, RequestManagerFragment>();
    final Map<FragmentManager, SupportRequestManagerFragment> pendingSupportRequestManagerFragments = new HashMap<FragmentManager, SupportRequestManagerFragment>();

    RequestManagerRetriever() {
        this.handler = new Handler(Looper.getMainLooper(), (Handler.Callback)this);
    }

    @TargetApi(value=17)
    private static void assertNotDestroyed(Activity activity) {
        if (Build.VERSION.SDK_INT >= 17 && activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
        }
    }

    public static RequestManagerRetriever get() {
        return INSTANCE;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private RequestManager getApplicationManager(Context context) {
        if (this.applicationManager == null) {
            synchronized (this) {
                if (this.applicationManager == null) {
                    this.applicationManager = new RequestManager(context.getApplicationContext(), new ApplicationLifecycle(), new EmptyRequestManagerTreeNode());
                }
            }
        }
        return this.applicationManager;
    }

    @TargetApi(value=11)
    RequestManager fragmentGet(Context context, android.app.FragmentManager object) {
        RequestManagerFragment requestManagerFragment = this.getRequestManagerFragment((android.app.FragmentManager)object);
        RequestManager requestManager = requestManagerFragment.getRequestManager();
        object = requestManager;
        if (requestManager == null) {
            object = new RequestManager(context, requestManagerFragment.getLifecycle(), requestManagerFragment.getRequestManagerTreeNode());
            requestManagerFragment.setRequestManager((RequestManager)object);
        }
        return object;
    }

    @TargetApi(value=11)
    public RequestManager get(Activity activity) {
        if (Util.isOnBackgroundThread() || Build.VERSION.SDK_INT < 11) {
            return this.get(activity.getApplicationContext());
        }
        RequestManagerRetriever.assertNotDestroyed(activity);
        return this.fragmentGet((Context)activity, activity.getFragmentManager());
    }

    @TargetApi(value=17)
    public RequestManager get(Fragment fragment) {
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
        }
        if (Util.isOnBackgroundThread() || Build.VERSION.SDK_INT < 17) {
            return this.get(fragment.getActivity().getApplicationContext());
        }
        android.app.FragmentManager fragmentManager = fragment.getChildFragmentManager();
        return this.fragmentGet((Context)fragment.getActivity(), fragmentManager);
    }

    public RequestManager get(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("You cannot start a load on a null Context");
        }
        if (Util.isOnMainThread() && !(context instanceof Application)) {
            if (context instanceof FragmentActivity) {
                return this.get((FragmentActivity)context);
            }
            if (context instanceof Activity) {
                return this.get((Activity)context);
            }
            if (context instanceof ContextWrapper) {
                return this.get(((ContextWrapper)context).getBaseContext());
            }
        }
        return this.getApplicationManager(context);
    }

    public RequestManager get(FragmentActivity fragmentActivity) {
        if (Util.isOnBackgroundThread()) {
            return this.get(fragmentActivity.getApplicationContext());
        }
        RequestManagerRetriever.assertNotDestroyed(fragmentActivity);
        return this.supportFragmentGet((Context)fragmentActivity, fragmentActivity.getSupportFragmentManager());
    }

    @TargetApi(value=17)
    RequestManagerFragment getRequestManagerFragment(android.app.FragmentManager fragmentManager) {
        RequestManagerFragment requestManagerFragment;
        RequestManagerFragment requestManagerFragment2 = requestManagerFragment = (RequestManagerFragment)fragmentManager.findFragmentByTag("com.bumptech.glide.manager");
        if (requestManagerFragment == null) {
            requestManagerFragment2 = requestManagerFragment = this.pendingRequestManagerFragments.get((Object)fragmentManager);
            if (requestManagerFragment == null) {
                requestManagerFragment2 = new RequestManagerFragment();
                this.pendingRequestManagerFragments.put(fragmentManager, requestManagerFragment2);
                fragmentManager.beginTransaction().add((Fragment)requestManagerFragment2, "com.bumptech.glide.manager").commitAllowingStateLoss();
                this.handler.obtainMessage(1, (Object)fragmentManager).sendToTarget();
            }
        }
        return requestManagerFragment2;
    }

    SupportRequestManagerFragment getSupportRequestManagerFragment(FragmentManager fragmentManager) {
        SupportRequestManagerFragment supportRequestManagerFragment;
        SupportRequestManagerFragment supportRequestManagerFragment2 = supportRequestManagerFragment = (SupportRequestManagerFragment)fragmentManager.findFragmentByTag("com.bumptech.glide.manager");
        if (supportRequestManagerFragment == null) {
            supportRequestManagerFragment2 = supportRequestManagerFragment = this.pendingSupportRequestManagerFragments.get(fragmentManager);
            if (supportRequestManagerFragment == null) {
                supportRequestManagerFragment2 = new SupportRequestManagerFragment();
                this.pendingSupportRequestManagerFragments.put(fragmentManager, supportRequestManagerFragment2);
                fragmentManager.beginTransaction().add(supportRequestManagerFragment2, "com.bumptech.glide.manager").commitAllowingStateLoss();
                this.handler.obtainMessage(2, (Object)fragmentManager).sendToTarget();
            }
        }
        return supportRequestManagerFragment2;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public boolean handleMessage(Message object) {
        void var3_4;
        boolean bl = true;
        Object var3_3 = null;
        Object var4_9 = null;
        switch (object.what) {
            default: {
                bl = false;
                object = var4_9;
                break;
            }
            case 1: {
                android.app.FragmentManager fragmentManager = (android.app.FragmentManager)object.obj;
                object = fragmentManager;
                RequestManagerFragment requestManagerFragment = this.pendingRequestManagerFragments.remove((Object)fragmentManager);
                break;
            }
            case 2: {
                FragmentManager fragmentManager = (FragmentManager)object.obj;
                object = fragmentManager;
                SupportRequestManagerFragment supportRequestManagerFragment = this.pendingSupportRequestManagerFragments.remove(fragmentManager);
            }
        }
        if (bl && var3_4 == null && Log.isLoggable((String)"RMRetriever", (int)5)) {
            Log.w((String)"RMRetriever", (String)("Failed to remove expected request manager fragment, manager: " + (Object)object));
        }
        return bl;
    }

    RequestManager supportFragmentGet(Context context, FragmentManager object) {
        SupportRequestManagerFragment supportRequestManagerFragment = this.getSupportRequestManagerFragment((FragmentManager)object);
        RequestManager requestManager = supportRequestManagerFragment.getRequestManager();
        object = requestManager;
        if (requestManager == null) {
            object = new RequestManager(context, supportRequestManagerFragment.getLifecycle(), supportRequestManagerFragment.getRequestManagerTreeNode());
            supportRequestManagerFragment.setRequestManager((RequestManager)object);
        }
        return object;
    }
}

