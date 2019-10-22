/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson.reflect;

import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.internal.$Gson$Types;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TypeToken<T> {
    final int hashCode;
    final Class<? super T> rawType;
    final Type type;

    protected TypeToken() {
        this.type = TypeToken.getSuperclassTypeParameter(this.getClass());
        this.rawType = $Gson$Types.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }

    TypeToken(Type type) {
        this.type = $Gson$Types.canonicalize($Gson$Preconditions.checkNotNull(type));
        this.rawType = $Gson$Types.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }

    public static <T> TypeToken<T> get(Class<T> class_) {
        return new TypeToken<T>(class_);
    }

    public static TypeToken<?> get(Type type) {
        return new TypeToken<T>(type);
    }

    static Type getSuperclassTypeParameter(Class<?> type) {
        if ((type = type.getGenericSuperclass()) instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        return $Gson$Types.canonicalize(((ParameterizedType)type).getActualTypeArguments()[0]);
    }

    public final boolean equals(Object object) {
        return object instanceof TypeToken && $Gson$Types.equals(this.type, ((TypeToken)object).type);
    }

    public final Class<? super T> getRawType() {
        return this.rawType;
    }

    public final Type getType() {
        return this.type;
    }

    public final int hashCode() {
        return this.hashCode;
    }

    public final String toString() {
        return $Gson$Types.typeToString(this.type);
    }
}

