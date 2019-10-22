/*
 * Decompiled with CFR 0.147.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.ReflectiveGenericLifecycleObserver;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

class Lifecycling {
    private static Map<Class, Constructor<? extends GenericLifecycleObserver>> sCallbackCache;
    private static Constructor<? extends GenericLifecycleObserver> sREFLECTIVE;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        try {
            sREFLECTIVE = ReflectiveGenericLifecycleObserver.class.getConstructor(Object.class);
        }
        catch (NoSuchMethodException noSuchMethodException) {}
        sCallbackCache = new HashMap<Class, Constructor<? extends GenericLifecycleObserver>>();
    }

    static String getAdapterName(String string2) {
        return string2.replace(".", "_") + "_LifecycleAdapter";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static GenericLifecycleObserver getCallback(Object object) {
        if (object instanceof GenericLifecycleObserver) {
            return (GenericLifecycleObserver)object;
        }
        try {
            Class<?> class_ = object.getClass();
            Constructor<? extends GenericLifecycleObserver> constructor = sCallbackCache.get(class_);
            if (constructor != null) {
                return constructor.newInstance(object);
            }
            constructor = Lifecycling.getGeneratedAdapterConstructor(class_);
            if (constructor == null) {
                sCallbackCache.put(class_, sREFLECTIVE);
                return new ReflectiveGenericLifecycleObserver(object);
            }
            sCallbackCache.put(class_, constructor);
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance(object);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException);
        }
        catch (InstantiationException instantiationException) {
            throw new RuntimeException(instantiationException);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Constructor<? extends GenericLifecycleObserver> getGeneratedAdapterConstructor(Class<?> class_) {
        String string2 = class_.getPackage().getName();
        Object object = class_.getCanonicalName();
        if (object == null) return null;
        if (!string2.isEmpty()) {
            object = ((String)object).substring(string2.length() + 1);
        }
        object = Lifecycling.getAdapterName((String)object);
        try {
            if (!string2.isEmpty()) {
                object = string2 + "." + (String)object;
            }
            return Class.forName((String)object).getDeclaredConstructor(class_);
        }
        catch (ClassNotFoundException classNotFoundException) {
            if ((class_ = class_.getSuperclass()) != null) return Lifecycling.getGeneratedAdapterConstructor(class_);
            return null;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new RuntimeException(noSuchMethodException);
        }
    }
}

