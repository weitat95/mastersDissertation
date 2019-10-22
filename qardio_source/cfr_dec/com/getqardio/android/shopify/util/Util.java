/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.util;

import com.getqardio.android.shopify.util.BiFunction;
import com.getqardio.android.shopify.util.Function;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class Util {
    private Util() {
    }

    public static String checkNotBlank(String string2, Object object) {
        if (string2 == null) {
            throw new NullPointerException(String.valueOf(object));
        }
        if (string2.isEmpty()) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
        return string2;
    }

    public static <I, T extends Collection<I>> T checkNotEmpty(T t, Object object) {
        if (t == null) {
            throw new NullPointerException(String.valueOf(object));
        }
        if (t.isEmpty()) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
        return t;
    }

    public static <T> T checkNotNull(T t, Object object) {
        if (t == null) {
            throw new NullPointerException(String.valueOf(object));
        }
        return t;
    }

    public static <T> List<T> filter(List<T> object, Function<T, Boolean> function) {
        Util.checkNotNull(object, "source == null");
        Util.checkNotNull(function, "condition == null");
        ArrayList arrayList = new ArrayList();
        object = object.iterator();
        while (object.hasNext()) {
            Object e = object.next();
            if (!function.apply(e).booleanValue()) continue;
            arrayList.add(e);
        }
        return arrayList;
    }

    public static <T> T firstItem(List<T> list) {
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public static <T, R> R firstItem(List<T> list, Function<T, R> function) {
        Util.checkNotNull(function, "transformer == null");
        if (list != null && !list.isEmpty()) {
            return function.apply(list.get(0));
        }
        return null;
    }

    public static <T, R> R fold(R r, Collection<T> object, BiFunction<R, T, R> biFunction) {
        Util.checkNotNull(object, "source == null");
        Util.checkNotNull(biFunction, "accumulator == null");
        object = object.iterator();
        while (object.hasNext()) {
            r = biFunction.apply(r, object.next());
        }
        return r;
    }

    public static <T, R> List<R> mapItems(Collection<T> object, Function<T, R> function) {
        Util.checkNotNull(object, "source == null");
        Util.checkNotNull(function, "transformer == null");
        ArrayList<R> arrayList = new ArrayList<R>();
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(function.apply(object.next()));
        }
        return arrayList;
    }

    public static <T> T minItem(Collection<T> collection, T t, Comparator<T> comparator) {
        Util.checkNotNull(collection, "source == null");
        Util.checkNotNull(comparator, "comparator == null");
        if (collection.isEmpty()) {
            return t;
        }
        t = null;
        Iterator<T> iterator = collection.iterator();
        collection = t;
        while (iterator.hasNext()) {
            t = iterator.next();
            if (collection == null) {
                collection = t;
                continue;
            }
            if (comparator.compare(collection, (Collection<T>)t) < 0) continue;
            collection = t;
        }
        return (T)collection;
    }
}

