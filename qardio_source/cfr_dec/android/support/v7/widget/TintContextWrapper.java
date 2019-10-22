/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.AssetManager
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.widget.TintResources;
import android.support.v7.widget.VectorEnabledTintResources;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TintContextWrapper
extends ContextWrapper {
    private static final Object CACHE_LOCK = new Object();
    private static ArrayList<WeakReference<TintContextWrapper>> sCache;
    private final Resources mResources;
    private final Resources.Theme mTheme;

    private TintContextWrapper(Context context) {
        super(context);
        if (VectorEnabledTintResources.shouldBeUsed()) {
            this.mResources = new VectorEnabledTintResources((Context)this, context.getResources());
            this.mTheme = this.mResources.newTheme();
            this.mTheme.setTo(context.getTheme());
            return;
        }
        this.mResources = new TintResources((Context)this, context.getResources());
        this.mTheme = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean shouldWrap(Context context) {
        return !(context instanceof TintContextWrapper) && !(context.getResources() instanceof TintResources) && !(context.getResources() instanceof VectorEnabledTintResources) && (Build.VERSION.SDK_INT < 21 || VectorEnabledTintResources.shouldBeUsed());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static Context wrap(Context var0) {
        if (TintContextWrapper.shouldWrap(var0 /* !! */ ) == false) return var0 /* !! */ ;
        var3_1 = TintContextWrapper.CACHE_LOCK;
        // MONITORENTER : var3_1
        if (TintContextWrapper.sCache != null) {
            var1_2 = TintContextWrapper.sCache.size() - 1;
        } else {
            TintContextWrapper.sCache = new ArrayList<E>();
            do {
                var0 /* !! */  = new TintContextWrapper(var0 /* !! */ );
                TintContextWrapper.sCache.add(new WeakReference<Context>(var0 /* !! */ ));
                // MONITOREXIT : var3_1
                return var0 /* !! */ ;
                break;
            } while (true);
        }
        do {
            if (var1_2 < 0) break;
            var2_3 /* !! */  = TintContextWrapper.sCache.get(var1_2);
            if (var2_3 /* !! */  == null || var2_3 /* !! */ .get() == null) {
                TintContextWrapper.sCache.remove(var1_2);
            }
            --var1_2;
        } while (true);
        var1_2 = TintContextWrapper.sCache.size() - 1;
        do {
            if (var1_2 < 0) ** continue;
            var2_3 /* !! */  = TintContextWrapper.sCache.get(var1_2);
            var2_3 /* !! */  = var2_3 /* !! */  != null ? (TintContextWrapper)var2_3 /* !! */ .get() : null;
            if (var2_3 /* !! */  != null && var2_3 /* !! */ .getBaseContext() == var0 /* !! */ ) {
                // MONITOREXIT : var3_1
                return var2_3 /* !! */ ;
            }
            --var1_2;
        } while (true);
    }

    public AssetManager getAssets() {
        return this.mResources.getAssets();
    }

    public Resources getResources() {
        return this.mResources;
    }

    public Resources.Theme getTheme() {
        if (this.mTheme == null) {
            return super.getTheme();
        }
        return this.mTheme;
    }

    public void setTheme(int n) {
        if (this.mTheme == null) {
            super.setTheme(n);
            return;
        }
        this.mTheme.applyStyle(n, true);
    }
}

