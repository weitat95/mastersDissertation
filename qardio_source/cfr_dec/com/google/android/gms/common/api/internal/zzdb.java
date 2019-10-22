/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 */
package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.zzcf;
import com.google.android.gms.common.api.internal.zzdc;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class zzdb
extends Fragment
implements zzcf {
    private static WeakHashMap<FragmentActivity, WeakReference<zzdb>> zzfue = new WeakHashMap();
    private int zzcbc = 0;
    private Map<String, LifecycleCallback> zzfuf = new ArrayMap<String, LifecycleCallback>();
    private Bundle zzfug;

    static /* synthetic */ int zza(zzdb zzdb2) {
        return zzdb2.zzcbc;
    }

    public static zzdb zza(FragmentActivity fragmentActivity) {
        WeakReference<zzdb> weakReference;
        block7: {
            block6: {
                zzdb zzdb2;
                weakReference = zzfue.get(fragmentActivity);
                if (weakReference != null && (weakReference = (zzdb)weakReference.get()) != null) {
                    return weakReference;
                }
                try {
                    zzdb2 = (zzdb)fragmentActivity.getSupportFragmentManager().findFragmentByTag("SupportLifecycleFragmentImpl");
                    if (zzdb2 == null) break block6;
                    weakReference = zzdb2;
                }
                catch (ClassCastException classCastException) {
                    throw new IllegalStateException("Fragment with tag SupportLifecycleFragmentImpl is not a SupportLifecycleFragmentImpl", classCastException);
                }
                if (!zzdb2.isRemoving()) break block7;
            }
            weakReference = new zzdb();
            fragmentActivity.getSupportFragmentManager().beginTransaction().add((Fragment)((Object)weakReference), "SupportLifecycleFragmentImpl").commitAllowingStateLoss();
        }
        zzfue.put(fragmentActivity, new WeakReference<Object>(weakReference));
        return weakReference;
    }

    static /* synthetic */ Bundle zzb(zzdb zzdb2) {
        return zzdb2.zzfug;
    }

    @Override
    public final void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        super.dump(string2, fileDescriptor, printWriter, arrstring);
        Iterator<LifecycleCallback> iterator = this.zzfuf.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().dump(string2, fileDescriptor, printWriter, arrstring);
        }
    }

    @Override
    public final void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        Iterator<LifecycleCallback> iterator = this.zzfuf.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().onActivityResult(n, n2, intent);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzcbc = 1;
        this.zzfug = bundle;
        Iterator<Map.Entry<String, LifecycleCallback>> iterator = this.zzfuf.entrySet().iterator();
        while (iterator.hasNext()) {
            void var2_3;
            Map.Entry<String, LifecycleCallback> entry = iterator.next();
            LifecycleCallback lifecycleCallback = entry.getValue();
            if (bundle != null) {
                Bundle bundle2 = bundle.getBundle(entry.getKey());
            } else {
                Object var2_6 = null;
            }
            lifecycleCallback.onCreate((Bundle)var2_3);
        }
        return;
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
        this.zzcbc = 5;
        Iterator<LifecycleCallback> iterator = this.zzfuf.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().onDestroy();
        }
    }

    @Override
    public final void onResume() {
        super.onResume();
        this.zzcbc = 3;
        Iterator<LifecycleCallback> iterator = this.zzfuf.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().onResume();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle != null) {
            for (Map.Entry<String, LifecycleCallback> entry : this.zzfuf.entrySet()) {
                Bundle bundle2 = new Bundle();
                entry.getValue().onSaveInstanceState(bundle2);
                bundle.putBundle(entry.getKey(), bundle2);
            }
        }
    }

    @Override
    public final void onStart() {
        super.onStart();
        this.zzcbc = 2;
        Iterator<LifecycleCallback> iterator = this.zzfuf.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().onStart();
        }
    }

    @Override
    public final void onStop() {
        super.onStop();
        this.zzcbc = 4;
        Iterator<LifecycleCallback> iterator = this.zzfuf.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().onStop();
        }
    }

    @Override
    public final <T extends LifecycleCallback> T zza(String string2, Class<T> class_) {
        return (T)((LifecycleCallback)class_.cast(this.zzfuf.get(string2)));
    }

    @Override
    public final void zza(String string2, LifecycleCallback lifecycleCallback) {
        if (!this.zzfuf.containsKey(string2)) {
            this.zzfuf.put(string2, lifecycleCallback);
            if (this.zzcbc > 0) {
                new Handler(Looper.getMainLooper()).post((Runnable)new zzdc(this, lifecycleCallback, string2));
            }
            return;
        }
        throw new IllegalArgumentException(new StringBuilder(String.valueOf(string2).length() + 59).append("LifecycleCallback with tag ").append(string2).append(" already added to this fragment.").toString());
    }

    @Override
    public final /* synthetic */ Activity zzajn() {
        return this.getActivity();
    }
}

