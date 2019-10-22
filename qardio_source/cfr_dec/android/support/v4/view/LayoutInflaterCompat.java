/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.LayoutInflater$Factory
 *  android.view.LayoutInflater$Factory2
 */
package android.support.v4.view;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import java.lang.reflect.Field;

public final class LayoutInflaterCompat {
    static final LayoutInflaterCompatBaseImpl IMPL = Build.VERSION.SDK_INT >= 21 ? new LayoutInflaterCompatApi21Impl() : new LayoutInflaterCompatBaseImpl();
    private static boolean sCheckedField;
    private static Field sLayoutInflaterFactory2Field;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static void forceSetFactory2(LayoutInflater layoutInflater, LayoutInflater.Factory2 factory2) {
        if (!sCheckedField) {
            try {
                sLayoutInflaterFactory2Field = LayoutInflater.class.getDeclaredField("mFactory2");
                sLayoutInflaterFactory2Field.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)"LayoutInflaterCompatHC", (String)("forceSetFactory2 Could not find field 'mFactory2' on class " + LayoutInflater.class.getName() + "; inflation may have unexpected results."), (Throwable)noSuchFieldException);
            }
            sCheckedField = true;
        }
        if (sLayoutInflaterFactory2Field == null) return;
        try {
            sLayoutInflaterFactory2Field.set((Object)layoutInflater, (Object)factory2);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)"LayoutInflaterCompatHC", (String)("forceSetFactory2 could not set the Factory2 on LayoutInflater " + (Object)layoutInflater + "; inflation may have unexpected results."), (Throwable)illegalAccessException);
            return;
        }
    }

    public static void setFactory2(LayoutInflater layoutInflater, LayoutInflater.Factory2 factory2) {
        IMPL.setFactory2(layoutInflater, factory2);
    }

    static class LayoutInflaterCompatApi21Impl
    extends LayoutInflaterCompatBaseImpl {
        LayoutInflaterCompatApi21Impl() {
        }

        @Override
        public void setFactory2(LayoutInflater layoutInflater, LayoutInflater.Factory2 factory2) {
            layoutInflater.setFactory2(factory2);
        }
    }

    static class LayoutInflaterCompatBaseImpl {
        LayoutInflaterCompatBaseImpl() {
        }

        public void setFactory2(LayoutInflater layoutInflater, LayoutInflater.Factory2 factory2) {
            layoutInflater.setFactory2(factory2);
            LayoutInflater.Factory factory = layoutInflater.getFactory();
            if (factory instanceof LayoutInflater.Factory2) {
                LayoutInflaterCompat.forceSetFactory2(layoutInflater, (LayoutInflater.Factory2)factory);
                return;
            }
            LayoutInflaterCompat.forceSetFactory2(layoutInflater, factory2);
        }
    }

}

