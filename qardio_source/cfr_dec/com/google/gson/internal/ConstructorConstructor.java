/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson.internal;

import com.google.gson.InstanceCreator;
import com.google.gson.JsonIOException;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.UnsafeAllocator;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public final class ConstructorConstructor {
    private final Map<Type, InstanceCreator<?>> instanceCreators;

    public ConstructorConstructor(Map<Type, InstanceCreator<?>> map) {
        this.instanceCreators = map;
    }

    private <T> ObjectConstructor<T> newDefaultConstructor(Class<? super T> object) {
        try {
            object = ((Class)object).getDeclaredConstructor(new Class[0]);
            if (!((AccessibleObject)object).isAccessible()) {
                ((AccessibleObject)object).setAccessible(true);
            }
            object = new ObjectConstructor<T>((Constructor)object){
                final /* synthetic */ Constructor val$constructor;
                {
                    this.val$constructor = constructor;
                }

                @Override
                public T construct() {
                    Object t;
                    try {
                        t = this.val$constructor.newInstance(null);
                    }
                    catch (InstantiationException instantiationException) {
                        throw new RuntimeException("Failed to invoke " + this.val$constructor + " with no args", instantiationException);
                    }
                    catch (InvocationTargetException invocationTargetException) {
                        throw new RuntimeException("Failed to invoke " + this.val$constructor + " with no args", invocationTargetException.getTargetException());
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        throw new AssertionError(illegalAccessException);
                    }
                    return t;
                }
            };
            return object;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
    }

    private <T> ObjectConstructor<T> newDefaultImplementationConstructor(final Type type, Class<? super T> class_) {
        if (Collection.class.isAssignableFrom(class_)) {
            if (SortedSet.class.isAssignableFrom(class_)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return (T)new TreeSet();
                    }
                };
            }
            if (EnumSet.class.isAssignableFrom(class_)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        if (type instanceof ParameterizedType) {
                            Type type2 = ((ParameterizedType)type).getActualTypeArguments()[0];
                            if (type2 instanceof Class) {
                                return (T)EnumSet.noneOf((Class)type2);
                            }
                            throw new JsonIOException("Invalid EnumSet type: " + type.toString());
                        }
                        throw new JsonIOException("Invalid EnumSet type: " + type.toString());
                    }
                };
            }
            if (Set.class.isAssignableFrom(class_)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return (T)new LinkedHashSet();
                    }
                };
            }
            if (Queue.class.isAssignableFrom(class_)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return (T)new ArrayDeque();
                    }
                };
            }
            return new ObjectConstructor<T>(){

                @Override
                public T construct() {
                    return (T)new ArrayList();
                }
            };
        }
        if (Map.class.isAssignableFrom(class_)) {
            if (ConcurrentNavigableMap.class.isAssignableFrom(class_)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return (T)new ConcurrentSkipListMap();
                    }
                };
            }
            if (ConcurrentMap.class.isAssignableFrom(class_)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return (T)new ConcurrentHashMap();
                    }
                };
            }
            if (SortedMap.class.isAssignableFrom(class_)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return (T)new TreeMap();
                    }
                };
            }
            if (type instanceof ParameterizedType && !String.class.isAssignableFrom(TypeToken.get(((ParameterizedType)type).getActualTypeArguments()[0]).getRawType())) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return (T)new LinkedHashMap();
                    }
                };
            }
            return new ObjectConstructor<T>(){

                @Override
                public T construct() {
                    return (T)new LinkedTreeMap();
                }
            };
        }
        return null;
    }

    private <T> ObjectConstructor<T> newUnsafeAllocator(final Type type, final Class<? super T> class_) {
        return new ObjectConstructor<T>(){
            private final UnsafeAllocator unsafeAllocator = UnsafeAllocator.create();

            @Override
            public T construct() {
                Object t;
                try {
                    t = this.unsafeAllocator.newInstance(class_);
                }
                catch (Exception exception) {
                    throw new RuntimeException("Unable to invoke no-args constructor for " + type + ". " + "Register an InstanceCreator with Gson for this type may fix this problem.", exception);
                }
                return t;
            }
        };
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public <T> ObjectConstructor<T> get(TypeToken<T> object) {
        ObjectConstructor objectConstructor;
        Type type = ((TypeToken)object).getType();
        Class class_ = ((TypeToken)object).getRawType();
        object = this.instanceCreators.get(type);
        if (object != null) {
            return new ObjectConstructor<T>((InstanceCreator)object, type){
                final /* synthetic */ Type val$type;
                final /* synthetic */ InstanceCreator val$typeCreator;
                {
                    this.val$typeCreator = instanceCreator;
                    this.val$type = type;
                }

                @Override
                public T construct() {
                    return this.val$typeCreator.createInstance(this.val$type);
                }
            };
        }
        object = this.instanceCreators.get(class_);
        if (object != null) {
            return new ObjectConstructor<T>((InstanceCreator)object, type){
                final /* synthetic */ InstanceCreator val$rawTypeCreator;
                final /* synthetic */ Type val$type;
                {
                    this.val$rawTypeCreator = instanceCreator;
                    this.val$type = type;
                }

                @Override
                public T construct() {
                    return this.val$rawTypeCreator.createInstance(this.val$type);
                }
            };
        }
        object = objectConstructor = this.newDefaultConstructor(class_);
        if (objectConstructor != null) return object;
        object = this.newDefaultImplementationConstructor(type, class_);
        if (object == null) return this.newUnsafeAllocator(type, class_);
        return object;
    }

    public String toString() {
        return this.instanceCreators.toString();
    }

}

