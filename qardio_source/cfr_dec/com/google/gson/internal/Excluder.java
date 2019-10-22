/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson.internal;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Excluder
implements TypeAdapterFactory,
Cloneable {
    public static final Excluder DEFAULT = new Excluder();
    private List<ExclusionStrategy> deserializationStrategies;
    private int modifiers = 136;
    private boolean requireExpose;
    private List<ExclusionStrategy> serializationStrategies = Collections.emptyList();
    private boolean serializeInnerClasses = true;
    private double version = -1.0;

    public Excluder() {
        this.deserializationStrategies = Collections.emptyList();
    }

    private boolean isAnonymousOrLocal(Class<?> class_) {
        return !Enum.class.isAssignableFrom(class_) && (class_.isAnonymousClass() || class_.isLocalClass());
    }

    private boolean isInnerClass(Class<?> class_) {
        return class_.isMemberClass() && !this.isStatic(class_);
    }

    private boolean isStatic(Class<?> class_) {
        return (class_.getModifiers() & 8) != 0;
    }

    private boolean isValidSince(Since since) {
        return since == null || !(since.value() > this.version);
    }

    private boolean isValidUntil(Until until) {
        return until == null || !(until.value() <= this.version);
    }

    private boolean isValidVersion(Since since, Until until) {
        return this.isValidSince(since) && this.isValidUntil(until);
    }

    protected Excluder clone() {
        try {
            Excluder excluder = (Excluder)super.clone();
            return excluder;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError(cloneNotSupportedException);
        }
    }

    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
        Class<T> class_ = typeToken.getRawType();
        final boolean bl = this.excludeClass(class_, true);
        final boolean bl2 = this.excludeClass(class_, false);
        if (!bl && !bl2) {
            return null;
        }
        return new TypeAdapter<T>(){
            private TypeAdapter<T> delegate;

            private TypeAdapter<T> delegate() {
                TypeAdapter<T> typeAdapter = this.delegate;
                if (typeAdapter != null) {
                    return typeAdapter;
                }
                this.delegate = typeAdapter = gson.getDelegateAdapter(Excluder.this, typeToken);
                return typeAdapter;
            }

            @Override
            public T read(JsonReader jsonReader) throws IOException {
                if (bl2) {
                    jsonReader.skipValue();
                    return null;
                }
                return this.delegate().read(jsonReader);
            }

            @Override
            public void write(JsonWriter jsonWriter, T t) throws IOException {
                if (bl) {
                    jsonWriter.nullValue();
                    return;
                }
                this.delegate().write(jsonWriter, t);
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean excludeClass(Class<?> class_, boolean bl) {
        if (this.version != -1.0 && !this.isValidVersion(class_.getAnnotation(Since.class), class_.getAnnotation(Until.class))) {
            return true;
        }
        if (!this.serializeInnerClasses && this.isInnerClass(class_)) {
            return true;
        }
        if (this.isAnonymousOrLocal(class_)) {
            return true;
        }
        List<ExclusionStrategy> list = bl ? this.serializationStrategies : this.deserializationStrategies;
        list = list.iterator();
        do {
            if (list.hasNext()) continue;
            return false;
        } while (!((ExclusionStrategy)list.next()).shouldSkipClass(class_));
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean excludeField(Field object, boolean bl) {
        List<ExclusionStrategy> list;
        if ((this.modifiers & ((Field)object).getModifiers()) != 0) {
            return true;
        }
        if (this.version != -1.0 && !this.isValidVersion(((Field)object).getAnnotation(Since.class), ((Field)object).getAnnotation(Until.class))) {
            return true;
        }
        if (((Field)object).isSynthetic()) {
            return true;
        }
        if (this.requireExpose && ((list = ((Field)object).getAnnotation(Expose.class)) == null || (bl ? !list.serialize() : !list.deserialize()))) {
            return true;
        }
        if (!this.serializeInnerClasses && this.isInnerClass(((Field)object).getType())) {
            return true;
        }
        if (this.isAnonymousOrLocal(((Field)object).getType())) {
            return true;
        }
        list = bl ? this.serializationStrategies : this.deserializationStrategies;
        if (!list.isEmpty()) {
            object = new FieldAttributes((Field)object);
            list = list.iterator();
            while (list.hasNext()) {
                if (!((ExclusionStrategy)list.next()).shouldSkipField((FieldAttributes)object)) continue;
                return true;
            }
        }
        return false;
    }

}

