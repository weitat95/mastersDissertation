/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.util.LongSparseArray
 */
package android.support.v7.app;

import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.util.LongSparseArray;
import java.lang.reflect.Field;
import java.util.Map;

class ResourcesFlusher {
    private static Field sDrawableCacheField;
    private static boolean sDrawableCacheFieldFetched;
    private static Field sResourcesImplField;
    private static boolean sResourcesImplFieldFetched;
    private static Class sThemedResourceCacheClazz;
    private static boolean sThemedResourceCacheClazzFetched;
    private static Field sThemedResourceCache_mUnthemedEntriesField;
    private static boolean sThemedResourceCache_mUnthemedEntriesFieldFetched;

    static boolean flush(Resources resources) {
        if (Build.VERSION.SDK_INT >= 24) {
            return ResourcesFlusher.flushNougats(resources);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            return ResourcesFlusher.flushMarshmallows(resources);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            return ResourcesFlusher.flushLollipops(resources);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean flushLollipops(Resources object) {
        if (!sDrawableCacheFieldFetched) {
            try {
                sDrawableCacheField = Resources.class.getDeclaredField("mDrawableCache");
                sDrawableCacheField.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)"ResourcesFlusher", (String)"Could not retrieve Resources#mDrawableCache field", (Throwable)noSuchFieldException);
            }
            sDrawableCacheFieldFetched = true;
        }
        if (sDrawableCacheField != null) {
            Object var1_3 = null;
            try {
                object = (Map)sDrawableCacheField.get(object);
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e((String)"ResourcesFlusher", (String)"Could not retrieve value from Resources#mDrawableCache", (Throwable)illegalAccessException);
                object = var1_3;
            }
            if (object != null) {
                object.clear();
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean flushMarshmallows(Resources resources) {
        Object object;
        boolean bl = true;
        if (!sDrawableCacheFieldFetched) {
            try {
                sDrawableCacheField = Resources.class.getDeclaredField("mDrawableCache");
                sDrawableCacheField.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)"ResourcesFlusher", (String)"Could not retrieve Resources#mDrawableCache field", (Throwable)noSuchFieldException);
            }
            sDrawableCacheFieldFetched = true;
        }
        Object object2 = object = null;
        if (sDrawableCacheField != null) {
            try {
                object2 = sDrawableCacheField.get((Object)resources);
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e((String)"ResourcesFlusher", (String)"Could not retrieve value from Resources#mDrawableCache", (Throwable)illegalAccessException);
                object2 = object;
            }
        }
        if (object2 == null) {
            return false;
        }
        if (object2 == null) return false;
        if (!ResourcesFlusher.flushThemedResourcesCache(object2)) return false;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean flushNougats(Resources object) {
        Object object2;
        boolean bl = true;
        if (!sResourcesImplFieldFetched) {
            try {
                sResourcesImplField = Resources.class.getDeclaredField("mResourcesImpl");
                sResourcesImplField.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)"ResourcesFlusher", (String)"Could not retrieve Resources#mResourcesImpl field", (Throwable)noSuchFieldException);
            }
            sResourcesImplFieldFetched = true;
        }
        if (sResourcesImplField == null) {
            return false;
        }
        Object object3 = null;
        try {
            object = sResourcesImplField.get(object);
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)"ResourcesFlusher", (String)"Could not retrieve value from Resources#mResourcesImpl", (Throwable)illegalAccessException);
            object = object3;
        }
        if (object == null) return false;
        if (!sDrawableCacheFieldFetched) {
            try {
                sDrawableCacheField = object.getClass().getDeclaredField("mDrawableCache");
                sDrawableCacheField.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)"ResourcesFlusher", (String)"Could not retrieve ResourcesImpl#mDrawableCache field", (Throwable)noSuchFieldException);
            }
            sDrawableCacheFieldFetched = true;
        }
        object3 = object2 = null;
        if (sDrawableCacheField != null) {
            try {
                object3 = sDrawableCacheField.get(object);
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e((String)"ResourcesFlusher", (String)"Could not retrieve value from ResourcesImpl#mDrawableCache", (Throwable)illegalAccessException);
                object3 = object2;
            }
        }
        if (object3 == null) return false;
        if (!ResourcesFlusher.flushThemedResourcesCache(object3)) return false;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean flushThemedResourcesCache(Object object) {
        if (!sThemedResourceCacheClazzFetched) {
            try {
                sThemedResourceCacheClazz = Class.forName("android.content.res.ThemedResourceCache");
            }
            catch (ClassNotFoundException classNotFoundException) {
                Log.e((String)"ResourcesFlusher", (String)"Could not find ThemedResourceCache class", (Throwable)classNotFoundException);
            }
            sThemedResourceCacheClazzFetched = true;
        }
        if (sThemedResourceCacheClazz == null) {
            return false;
        }
        if (!sThemedResourceCache_mUnthemedEntriesFieldFetched) {
            try {
                sThemedResourceCache_mUnthemedEntriesField = sThemedResourceCacheClazz.getDeclaredField("mUnthemedEntries");
                sThemedResourceCache_mUnthemedEntriesField.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)"ResourcesFlusher", (String)"Could not retrieve ThemedResourceCache#mUnthemedEntries field", (Throwable)noSuchFieldException);
            }
            sThemedResourceCache_mUnthemedEntriesFieldFetched = true;
        }
        if (sThemedResourceCache_mUnthemedEntriesField == null) {
            return false;
        }
        Object var1_3 = null;
        try {
            object = (LongSparseArray)sThemedResourceCache_mUnthemedEntriesField.get(object);
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)"ResourcesFlusher", (String)"Could not retrieve value from ThemedResourceCache#mUnthemedEntries", (Throwable)illegalAccessException);
            object = var1_3;
        }
        if (object != null) {
            object.clear();
            return true;
        }
        return false;
    }
}

