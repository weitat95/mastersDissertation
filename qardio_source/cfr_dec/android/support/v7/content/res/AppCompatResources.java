/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.XmlResourceParser
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.util.SparseArray
 *  android.util.TypedValue
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.v7.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatColorStateListInflater;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;

public final class AppCompatResources {
    private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal();
    private static final Object sColorStateCacheLock;
    private static final WeakHashMap<Context, SparseArray<ColorStateListCacheEntry>> sColorStateCaches;

    static {
        sColorStateCaches = new WeakHashMap(0);
        sColorStateCacheLock = new Object();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void addColorStateListToCache(Context context, int n, ColorStateList colorStateList) {
        Object object = sColorStateCacheLock;
        synchronized (object) {
            SparseArray sparseArray;
            SparseArray sparseArray2 = sparseArray = sColorStateCaches.get((Object)context);
            if (sparseArray == null) {
                sparseArray2 = new SparseArray();
                sColorStateCaches.put(context, (SparseArray<ColorStateListCacheEntry>)sparseArray2);
            }
            sparseArray2.append(n, (Object)new ColorStateListCacheEntry(colorStateList, context.getResources().getConfiguration()));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static ColorStateList getCachedColorStateList(Context context, int n) {
        Object object = sColorStateCacheLock;
        synchronized (object) {
            SparseArray<ColorStateListCacheEntry> sparseArray = sColorStateCaches.get((Object)context);
            if (sparseArray == null) return null;
            if (sparseArray.size() <= 0) return null;
            ColorStateListCacheEntry colorStateListCacheEntry = (ColorStateListCacheEntry)sparseArray.get(n);
            if (colorStateListCacheEntry == null) return null;
            if (colorStateListCacheEntry.configuration.equals(context.getResources().getConfiguration())) {
                return colorStateListCacheEntry.value;
            }
            sparseArray.remove(n);
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ColorStateList getColorStateList(Context context, int n) {
        ColorStateList colorStateList;
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getColorStateList(n);
        }
        ColorStateList colorStateList2 = colorStateList = AppCompatResources.getCachedColorStateList(context, n);
        if (colorStateList != null) return colorStateList2;
        colorStateList2 = AppCompatResources.inflateColorStateList(context, n);
        if (colorStateList2 == null) return ContextCompat.getColorStateList(context, n);
        AppCompatResources.addColorStateListToCache(context, n, colorStateList2);
        return colorStateList2;
    }

    public static Drawable getDrawable(Context context, int n) {
        return AppCompatDrawableManager.get().getDrawable(context, n);
    }

    private static TypedValue getTypedValue() {
        TypedValue typedValue;
        TypedValue typedValue2 = typedValue = TL_TYPED_VALUE.get();
        if (typedValue == null) {
            typedValue2 = new TypedValue();
            TL_TYPED_VALUE.set(typedValue2);
        }
        return typedValue2;
    }

    private static ColorStateList inflateColorStateList(Context context, int n) {
        if (AppCompatResources.isColorInt(context, n)) {
            return null;
        }
        Resources resources = context.getResources();
        XmlResourceParser xmlResourceParser = resources.getXml(n);
        try {
            context = AppCompatColorStateListInflater.createFromXml(resources, (XmlPullParser)xmlResourceParser, context.getTheme());
            return context;
        }
        catch (Exception exception) {
            Log.e((String)"AppCompatResources", (String)"Failed to inflate ColorStateList, leaving it to the framework", (Throwable)exception);
            return null;
        }
    }

    private static boolean isColorInt(Context context, int n) {
        context = context.getResources();
        TypedValue typedValue = AppCompatResources.getTypedValue();
        context.getValue(n, typedValue, true);
        return typedValue.type >= 28 && typedValue.type <= 31;
    }

    private static class ColorStateListCacheEntry {
        final Configuration configuration;
        final ColorStateList value;

        ColorStateListCacheEntry(ColorStateList colorStateList, Configuration configuration) {
            this.value = colorStateList;
            this.configuration = configuration;
        }
    }

}

