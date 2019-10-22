/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.util.Log
 *  android.view.View
 *  android.view.Window
 */
package butterknife;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import butterknife.Unbinder;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ButterKnife {
    static final Map<Class<?>, Constructor<? extends Unbinder>> BINDINGS;
    private static boolean debug;

    static {
        debug = false;
        BINDINGS = new LinkedHashMap();
    }

    private ButterKnife() {
        throw new AssertionError((Object)"No instances.");
    }

    public static Unbinder bind(Activity activity) {
        return ButterKnife.createBinding((Object)activity, activity.getWindow().getDecorView());
    }

    public static Unbinder bind(View view) {
        return ButterKnife.createBinding((Object)view, view);
    }

    public static Unbinder bind(Object object, View view) {
        return ButterKnife.createBinding(object, view);
    }

    private static Unbinder createBinding(Object object, View view) {
        GenericDeclaration genericDeclaration = object.getClass();
        if (debug) {
            Log.d((String)"ButterKnife", (String)("Looking up binding for " + ((Class)genericDeclaration).getName()));
        }
        if ((genericDeclaration = ButterKnife.findBindingConstructorForClass(genericDeclaration)) == null) {
            return Unbinder.EMPTY;
        }
        try {
            object = (Unbinder)((Constructor)genericDeclaration).newInstance(new Object[]{object, view});
            return object;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException("Unable to invoke " + genericDeclaration, illegalAccessException);
        }
        catch (InstantiationException instantiationException) {
            throw new RuntimeException("Unable to invoke " + genericDeclaration, instantiationException);
        }
        catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getCause();
            if (throwable instanceof RuntimeException) {
                throw (RuntimeException)throwable;
            }
            if (throwable instanceof Error) {
                throw (Error)throwable;
            }
            throw new RuntimeException("Unable to create binding instance.", throwable);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Constructor<? extends Unbinder> findBindingConstructorForClass(Class<?> class_) {
        Constructor<Unbinder> constructor = BINDINGS.get(class_);
        if (constructor != null) {
            if (!debug) return constructor;
            Log.d((String)"ButterKnife", (String)"HIT: Cached in binding map.");
            return constructor;
        }
        String string2 = class_.getName();
        if (string2.startsWith("android.") || string2.startsWith("java.")) {
            if (!debug) return null;
            Log.d((String)"ButterKnife", (String)"MISS: Reached framework class. Abandoning search.");
            return null;
        }
        try {
            Constructor<?> constructor2 = class_.getClassLoader().loadClass(string2 + "_ViewBinding").getConstructor(class_, View.class);
            constructor = constructor2;
            if (debug) {
                Log.d((String)"ButterKnife", (String)"HIT: Loaded binding class and constructor.");
                constructor = constructor2;
            }
        }
        catch (ClassNotFoundException classNotFoundException) {
            if (debug) {
                Log.d((String)"ButterKnife", (String)("Not found. Trying superclass " + class_.getSuperclass().getName()));
            }
            constructor = ButterKnife.findBindingConstructorForClass(class_.getSuperclass());
        }
        BINDINGS.put(class_, constructor);
        return constructor;
        catch (NoSuchMethodException noSuchMethodException) {
            throw new RuntimeException("Unable to find binding constructor for " + string2, noSuchMethodException);
        }
    }

    public static interface Action<T extends View> {
    }

    public static interface Setter<T extends View, V> {
    }

}

