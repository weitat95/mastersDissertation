/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 */
package com.google.android.gms.dynamic;

import android.os.IBinder;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

public final class zzn<T>
extends IObjectWrapper.zza {
    private final T mWrappedObject;

    private zzn(T t) {
        this.mWrappedObject = t;
    }

    public static <T> T zzx(IObjectWrapper object) {
        int n = 0;
        if (object instanceof zzn) {
            return ((zzn)object).mWrappedObject;
        }
        IBinder iBinder = object.asBinder();
        Field[] arrfield = iBinder.getClass().getDeclaredFields();
        object = null;
        for (Field field : arrfield) {
            if (field.isSynthetic()) continue;
            ++n;
            object = field;
        }
        if (n == 1) {
            if (!((AccessibleObject)object).isAccessible()) {
                ((AccessibleObject)object).setAccessible(true);
                try {
                    object = ((Field)object).get((Object)iBinder);
                }
                catch (NullPointerException nullPointerException) {
                    throw new IllegalArgumentException("Binder object is null.", nullPointerException);
                }
                catch (IllegalAccessException illegalAccessException) {
                    throw new IllegalArgumentException("Could not access the field in remoteBinder.", illegalAccessException);
                }
                return (T)object;
            }
            throw new IllegalArgumentException("IObjectWrapper declared field not private!");
        }
        int n2 = arrfield.length;
        throw new IllegalArgumentException(new StringBuilder(64).append("Unexpected number of IObjectWrapper declared fields: ").append(n2).toString());
    }

    public static <T> IObjectWrapper zzz(T t) {
        return new zzn<T>(t);
    }
}

