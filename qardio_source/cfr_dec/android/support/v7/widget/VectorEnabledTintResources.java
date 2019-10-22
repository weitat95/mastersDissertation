/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.DisplayMetrics
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.DisplayMetrics;
import java.lang.ref.WeakReference;

public class VectorEnabledTintResources
extends Resources {
    private final WeakReference<Context> mContextRef;

    public VectorEnabledTintResources(Context context, Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        this.mContextRef = new WeakReference<Context>(context);
    }

    public static boolean shouldBeUsed() {
        return AppCompatDelegate.isCompatVectorFromResourcesEnabled() && Build.VERSION.SDK_INT <= 20;
    }

    public Drawable getDrawable(int n) throws Resources.NotFoundException {
        Context context = (Context)this.mContextRef.get();
        if (context != null) {
            return AppCompatDrawableManager.get().onDrawableLoadedFromResources(context, this, n);
        }
        return super.getDrawable(n);
    }

    final Drawable superGetDrawable(int n) {
        return super.getDrawable(n);
    }
}

