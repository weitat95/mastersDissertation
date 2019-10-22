/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.mixpanel.android.viewcrawler;

import android.view.View;
import com.mixpanel.android.util.MPLog;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class Caller {
    private final Object[] mMethodArgs;
    private final String mMethodName;
    private final Class<?> mMethodResultType;
    private final Class<?> mTargetClass;
    private final Method mTargetMethod;

    public Caller(Class<?> class_, String string2, Object[] arrobject, Class<?> class_2) throws NoSuchMethodException {
        this.mMethodName = string2;
        this.mMethodArgs = arrobject;
        this.mMethodResultType = class_2;
        this.mTargetMethod = this.pickMethod(class_);
        if (this.mTargetMethod == null) {
            throw new NoSuchMethodException("Method " + class_.getName() + "." + this.mMethodName + " doesn't exit");
        }
        this.mTargetClass = this.mTargetMethod.getDeclaringClass();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private static Class<?> assignableArgType(Class<?> class_) {
        void var1_2;
        if (class_ == Byte.class) {
            Class<Byte> class_2 = Byte.TYPE;
            return var1_2;
        } else {
            if (class_ == Short.class) {
                return Short.TYPE;
            }
            if (class_ == Integer.class) {
                return Integer.TYPE;
            }
            if (class_ == Long.class) {
                return Long.TYPE;
            }
            if (class_ == Float.class) {
                return Float.TYPE;
            }
            if (class_ == Double.class) {
                return Double.TYPE;
            }
            if (class_ == Boolean.class) {
                return Boolean.TYPE;
            }
            Class<?> class_3 = class_;
            if (class_ != Character.class) return var1_2;
            return Character.TYPE;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private Method pickMethod(Class<?> arrmethod) {
        int n;
        Class[] arrclass = new Class[this.mMethodArgs.length];
        for (n = 0; n < this.mMethodArgs.length; ++n) {
            arrclass[n] = this.mMethodArgs[n].getClass();
        }
        arrmethod = arrmethod.getMethods();
        int n2 = arrmethod.length;
        n = 0;
        while (n < n2) {
            Method method = arrmethod[n];
            Object object = method.getName();
            Class<?>[] arrclass2 = method.getParameterTypes();
            if (((String)object).equals(this.mMethodName) && arrclass2.length == this.mMethodArgs.length && Caller.assignableArgType(this.mMethodResultType).isAssignableFrom(Caller.assignableArgType(method.getReturnType()))) {
                boolean bl = true;
                for (int i = 0; i < arrclass2.length && bl; ++i) {
                    object = Caller.assignableArgType(arrclass[i]);
                    bl = Caller.assignableArgType(arrclass2[i]).isAssignableFrom((Class<?>)object);
                }
                if (bl) {
                    return method;
                }
            }
            ++n;
        }
        return null;
    }

    public Object applyMethod(View view) {
        return this.applyMethodWithArguments(view, this.mMethodArgs);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object applyMethodWithArguments(View object, Object[] arrobject) {
        Class<?> class_ = object.getClass();
        if (!this.mTargetClass.isAssignableFrom(class_)) return null;
        try {
            void var2_6;
            return this.mTargetMethod.invoke(object, (Object[])var2_6);
        }
        catch (IllegalAccessException illegalAccessException) {
            MPLog.e("MixpanelABTest.Caller", "Method " + this.mTargetMethod.getName() + " appears not to be public", illegalAccessException);
            return null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            MPLog.e("MixpanelABTest.Caller", "Method " + this.mTargetMethod.getName() + " called with arguments of the wrong type", illegalArgumentException);
            return null;
        }
        catch (InvocationTargetException invocationTargetException) {
            MPLog.e("MixpanelABTest.Caller", "Method " + this.mTargetMethod.getName() + " threw an exception", invocationTargetException);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean argsAreApplicable(Object[] arrobject) {
        Class<?>[] arrclass = this.mTargetMethod.getParameterTypes();
        if (arrobject.length == arrclass.length) {
            int n = 0;
            do {
                if (n >= arrobject.length) {
                    return true;
                }
                Class<?> class_ = Caller.assignableArgType(arrclass[n]);
                if (arrobject[n] == null) {
                    if (class_ == Byte.TYPE || class_ == Short.TYPE || class_ == Integer.TYPE || class_ == Long.TYPE || class_ == Float.TYPE || class_ == Double.TYPE || class_ == Boolean.TYPE || class_ == Character.TYPE) break;
                } else if (!class_.isAssignableFrom(Caller.assignableArgType(arrobject[n].getClass()))) {
                    return false;
                }
                ++n;
            } while (true);
        }
        return false;
    }

    public Object[] getArgs() {
        return this.mMethodArgs;
    }

    public String toString() {
        return "[Caller " + this.mMethodName + "(" + this.mMethodArgs + ")]";
    }
}

